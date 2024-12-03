package com.gl.ceir.config.service.impl;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.gl.ceir.config.configuration.PropertiesReader;
import com.gl.ceir.config.configuration.SortDirection;
import com.gl.ceir.config.exceptions.ResourceServicesException;
import com.gl.ceir.config.model.app.FileDetails;
import com.gl.ceir.config.model.app.FilterRequest;
import com.gl.ceir.config.model.app.GenricResponse;
import com.gl.ceir.config.model.app.RuleEngineMapping;
import com.gl.ceir.config.model.app.SearchCriteria;
import com.gl.ceir.config.model.app.SystemConfigurationDb;
import com.gl.ceir.config.model.aud.AuditTrail;
import com.gl.ceir.config.model.constants.Datatype;
import com.gl.ceir.config.model.constants.Features;
import com.gl.ceir.config.model.constants.SearchOperation;
import com.gl.ceir.config.model.constants.SubFeatures;
import com.gl.ceir.config.model.file.RuleEngineMappingFileModel;
import com.gl.ceir.config.repository.app.RuleEngineMappingRepository;
import com.gl.ceir.config.repository.app.SystemConfigurationDbRepository;
import com.gl.ceir.config.repository.aud.AuditTrailRepository;
import com.gl.ceir.config.specificationsbuilder.GenericSpecificationBuilder;
import com.gl.ceir.config.util.CustomMappingStrategy;
import com.gl.ceir.config.util.InterpSetter;
import com.gl.ceir.config.util.Utility;
import com.opencsv.CSVWriter;
import com.opencsv.bean.MappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

@Service
public class RuleEngineMappingServiceImpl {

	private static final Logger logger = LogManager.getLogger(RuleEngineMappingServiceImpl.class);

	@Autowired
	RuleEngineMappingRepository ruleEngineMappingRepository;

	@Autowired
	PropertiesReader propertiesReader;

	@Autowired
	Utility utility;

	
	@Autowired
	InterpSetter interpSetter;

	@Autowired
	ConfigurationManagementServiceImpl configurationManagementServiceImpl;


	@Autowired
	AuditTrailRepository auditTrailRepository;
	
	@Autowired
	SystemConfigurationDbRepository systemConfigurationDbRepository;
	public Optional<RuleEngineMapping> findById(long id){
		try {
			return ruleEngineMappingRepository.findById(id);
			
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
			throw new ResourceServicesException(this.getClass().getName(), e.getMessage());
		}
	}

	public GenricResponse updateByRuleOrder(RuleEngineMapping ruleEngineMapping){
		try {
			List<RuleEngineMapping> ruleList = new ArrayList<RuleEngineMapping>();
			//ruleList =ruleEngineMappingRepository.findByNameAndUserTypeAndFeature(ruleEngineMapping.getName(),ruleEngineMapping.getUserType(),ruleEngineMapping.getFeature());
			ruleList =ruleEngineMappingRepository.findByFeature(ruleEngineMapping.getFeature());
			 Comparator<RuleEngineMapping> comp1 =(i1,i2) ->i1.getRuleOrder()-i2.getRuleOrder();
			 ruleList.sort(comp1);
			RuleEngineMapping maxValue=ruleList.get(ruleList.size() - 1);
			logger.info("ruleList ====="+ruleList);
			logger.info("max index value is======="+maxValue+"object at 0 index ="+ruleList.get(0));
            logger.info("object is equal or not ="+ruleList.get(0).equals(ruleEngineMapping));
			RuleEngineMapping ruleEngineMappingExisting =  ruleEngineMappingRepository.getById(ruleEngineMapping.getId());
			logger.info("ruleEngineMappingExisting ::::::::::::: " + ruleEngineMappingExisting);
			
			if(ruleList.isEmpty()) {
				logger.info("No record is found in db ");
		    	  return new GenricResponse(401, "NO_RECORD_TO_UPDATE","NO RECORD IS FOUND", "");	
			}
			else if(ruleList.size()==1 && ruleList.get(0).getRuleOrder()==ruleEngineMapping.getRuleOrder())
			{
				logger.info("there is only one record and updated order is same , update not allowed==");
				return new GenricResponse(402, "ORDER_IS_SAME","entered order is same, update not allowed", "");
			}
		
			else if(ruleList.size()==1 && ruleList.get(0).getRuleOrder()<ruleEngineMapping.getRuleOrder())
			{
				logger.info("entered rule is greater then last exist record updation not allowed==");
				return new GenricResponse(403, "RULE_is_GREATER","entered rule is greater then last exist record updation not allowed", "");
			}
			/*else if(ruleEngineMapping.getRuleOrder()>maxValue.getRuleOrder()+1 || ruleEngineMapping.getRuleOrder().equals(maxValue.getRuleOrder()+1))
			{
				logger.info("entered rule is greater then last exist record updation not allowed==");
				return new GenricResponse(404, "RULE_is_MAX","entered rule is greater then last exist record updation not allowed", "");
			}*/
			else if(ruleEngineMapping.getRuleOrder()>maxValue.getRuleOrder()+1 || ruleEngineMapping.getRuleOrder()>ruleList.size())
			{
				logger.info("entered rule is greater then last exist record updation not allowed==");
				return new GenricResponse(404, "RULE_is_MAX","entered rule is greater then last exist record updation not allowed", "");
			}
			else if(ruleEngineMapping.getRuleOrder().equals(ruleEngineMappingExisting.getRuleOrder())) {
				logger.info("rule is not changed , other value is changed");
			   	  ruleEngineMappingRepository.save(ruleEngineMapping); 
				  return new GenricResponse(0, "Rule feature mapping updated successfully", "");
			}
			else if(ruleEngineMappingExisting.getRuleOrder()<ruleEngineMapping.getRuleOrder() ) {
				logger.info("existing rule order is less then new");
				List<RuleEngineMapping> ruleBetweenNewExisting = new ArrayList<RuleEngineMapping>();
				ruleBetweenNewExisting =ruleEngineMappingRepository.findByNameAndUserTypeAndFeatureAndRuleOrderBetween(ruleEngineMapping.getName(),ruleEngineMapping.getUserType(),ruleEngineMapping.getFeature(),ruleEngineMappingExisting.getRuleOrder()+1,ruleEngineMapping.getRuleOrder());
				Comparator<RuleEngineMapping> comp =(i1,i2) ->  i1.getRuleOrder()-i2.getRuleOrder();
				ruleBetweenNewExisting.sort(comp);
			    List<Integer> ruleListDecrement = ruleBetweenNewExisting.stream().map(i ->  i.getRuleOrder()-1).collect(Collectors.toList());
			    List<RuleEngineMapping> ruleListToUpdate = new ArrayList<RuleEngineMapping>();
				int i =0;
				logger.info("ruleBetweenNewExisting=========="+ruleBetweenNewExisting);
			
				 for(RuleEngineMapping r: ruleBetweenNewExisting) { 
	  					
	  					r.setRuleOrder(ruleListDecrement.get(i));
	  					r.setModifiedBy(ruleEngineMapping.getModifiedBy());
	  					ruleListToUpdate.add(r); 
	  				    i++;
	  				  } 
				 logger.info("final list to update=========="+ruleListToUpdate);
				
				  auditTrailRepository.save( new AuditTrail( Long.valueOf(ruleEngineMapping.getUserId()),
		    			  ruleEngineMapping.getUserName(), Long.valueOf(ruleEngineMapping.getUserTypeId()),
							"SystemAdmin", Long.valueOf(ruleEngineMapping.getFeatureId()),
							Features.RULE_FEATURE_MAPPING, SubFeatures.UPDATE, "","NA",
							ruleEngineMapping.getRoleType(),ruleEngineMapping.getPublicIp(),ruleEngineMapping.getBrowser()));
				  ruleEngineMappingRepository.saveAll(ruleListToUpdate);
				  ruleEngineMappingRepository.save(ruleEngineMapping); 
				  return new GenricResponse(0);
				 


			}
			else if(ruleEngineMappingExisting.getRuleOrder()>ruleEngineMapping.getRuleOrder()) {
				logger.info("existing rule order is greater  then new rule.");
				List<RuleEngineMapping> ruleBetweenNewExisting = new ArrayList<RuleEngineMapping>();
				ruleBetweenNewExisting =ruleEngineMappingRepository.findByNameAndUserTypeAndFeatureAndRuleOrderBetween(ruleEngineMapping.getName(),ruleEngineMapping.getUserType(),ruleEngineMapping.getFeature(),ruleEngineMapping.getRuleOrder(),ruleEngineMappingExisting.getRuleOrder()-1);
				Comparator<RuleEngineMapping> comp =(i1,i2) ->  i1.getRuleOrder()-i2.getRuleOrder();
				ruleBetweenNewExisting.sort(comp);
			    List<Integer> ruleListDecrement = ruleBetweenNewExisting.stream().map(i ->  i.getRuleOrder()+1).collect(Collectors.toList());
			    List<RuleEngineMapping> ruleListToUpdate = new ArrayList<RuleEngineMapping>();
				int i =0;
				logger.info("ruleBetweenNewExisting=========="+ruleBetweenNewExisting);
			
				 for(RuleEngineMapping r: ruleBetweenNewExisting) { 
	  					
	  					r.setRuleOrder(ruleListDecrement.get(i));
	  					r.setModifiedBy(ruleEngineMapping.getModifiedBy());
	  					ruleListToUpdate.add(r); 
	  				    i++;
	  				  } 
				 logger.info("final list to update for existing rule order is greater  then new rule =========="+ruleListToUpdate);
				  auditTrailRepository.save( new AuditTrail( Long.valueOf(ruleEngineMapping.getUserId()),
		    			  ruleEngineMapping.getUserName(), Long.valueOf(ruleEngineMapping.getUserTypeId()),
							"SystemAdmin", Long.valueOf(ruleEngineMapping.getFeatureId()),
							Features.RULE_FEATURE_MAPPING, SubFeatures.UPDATE, "","NA",
							ruleEngineMapping.getRoleType(),ruleEngineMapping.getPublicIp(),ruleEngineMapping.getBrowser()));
				 ruleEngineMappingRepository.saveAll(ruleListToUpdate); 
    	    	 ruleEngineMappingRepository.save(ruleEngineMapping);
    	    	  return new GenricResponse(0);
			}
			
			
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
			throw new ResourceServicesException(this.getClass().getName(), e.getMessage());
		}
		return null;
	}
	
	
	public GenricResponse updateById(RuleEngineMapping ruleEngineMapping){
		try {
			RuleEngineMapping ruleEngineMappingOld =  ruleEngineMappingRepository.getById(ruleEngineMapping.getId());
			logger.info("ruleEngineMappingOld : " + ruleEngineMappingOld);
			logger.info("UserName while Updating Rule Mapping " +ruleEngineMapping.getUserName());
			ruleEngineMapping.setId(ruleEngineMappingOld.getId());
			ruleEngineMapping.setModifiedBy(ruleEngineMapping.getUserName());
		
			Optional<RuleEngineMapping> rule =Optional.ofNullable( ruleEngineMappingRepository.findByNameAndFeatureAndUserTypeAndRuleOrder(ruleEngineMapping.getName(),ruleEngineMapping.getFeature(),ruleEngineMapping.getUserType(),ruleEngineMapping.getRuleOrder()));
			logger.info(" rule name bases of feature , user  type ,order and rule name="+rule);
			List<RuleEngineMapping> ruleList = new ArrayList<RuleEngineMapping>();
			List<RuleEngineMapping> ruleListLower = new ArrayList<RuleEngineMapping>();
			ruleList =ruleEngineMappingRepository.findByNameAndUserTypeAndFeatureAndRuleOrderBetween(ruleEngineMapping.getName(),ruleEngineMapping.getUserType(),ruleEngineMapping.getFeature(),ruleEngineMappingOld.getRuleOrder(),ruleEngineMapping.getRuleOrder());
		//	ruleListLower =ruleEngineMappingRepository.findByNameAndUserTypeAndFeatureAndRuleOrderLessThan(ruleEngineMapping.getName(),ruleEngineMapping.getUserType(),ruleEngineMapping.getFeature(),ruleEngineMapping.getRuleOrder());
		 
		
			
			logger.info("-------------------------  ruleListLower "+ruleListLower);
			
			  Comparator<RuleEngineMapping> comp =(i1,i2) ->  i1.getRuleOrder()-i2.getRuleOrder(); ruleList.sort(comp); logger.
			  info(" ******************   sorted  @@@@@@@@="  +ruleList);
			  
			  List<Integer> ruleList1 = ruleList.stream().map(i ->  i.getRuleOrder()+1).collect(Collectors.toList());
			  List<Integer> ruleListDecrement = ruleListLower.stream().map(i ->  i.getRuleOrder()-1).collect(Collectors.toList());
			  logger.info("loop eith 1+++++++++++++++++++++++++"+ruleList1);
			  logger.info("loop decrement -----------------"+ruleListDecrement);
			 
		    logger.info("order come from form ="+ruleEngineMapping.getRuleOrder()+" && existing order in db=="+ruleEngineMappingOld.getRuleOrder());
			if(ruleEngineMapping.getRuleOrder()==ruleEngineMappingOld.getRuleOrder()) {
			logger.info("rule is equal no need to change rule's order ");		
			ruleEngineMappingRepository.save(ruleEngineMapping);
			return new GenricResponse(0);
			}
			
			
			
			  //new rule order is greater than existing else
			  if(ruleEngineMapping.getRuleOrder()>ruleEngineMappingOld.getRuleOrder())
			  
			  {
				  int i=0; 
		       
		          
			      List<RuleEngineMapping> ruleListToUpdate = new   ArrayList<RuleEngineMapping>();
				 
				  for (i=0;i<ruleList.size(); i++)
				  {
					  RuleEngineMapping r = new  RuleEngineMapping();
					   		r.setBrowser(ruleList.get(i).getBrowser());
					   		r.setCreatedOn(ruleList.get(i).getCreatedOn());
					   		r.setExistOrNot(ruleList.get(i).getExistOrNot());
					   		r.setFailedRuleActionGrace(ruleList.get(i).getFailedRuleActionGrace());
					   		r.setFailedRuleActionPostGrace(ruleList.get(i).getFailedRuleActionPostGrace());
					   		r.setFeature(ruleList.get(i).getFeature());
					   		r.setFeatureId(ruleList.get(i).getFeatureId());
					   		r.setGraceAction(ruleList.get(i).getGraceAction());
					   		r.setId(ruleList.get(i).getId());
					   		r.setModifiedBy(ruleList.get(i).getModifiedBy());
					   		r.setModifiedOn(ruleList.get(i).getModifiedOn());
					   		r.setName(ruleList.get(i).getName());
					   		r.setOutput(ruleList.get(i).getOutput());
					   		r.setPostGraceAction(ruleList.get(i).getPostGraceAction());
					   		ruleList.get(i).setPublicIp(ruleList.get(i).getPublicIp());
					   		r.setRoleType(ruleList.get(i).getRoleType());
					   		r.setUserName(ruleList.get(i).getUserName());
					   		r.setUserType(ruleList.get(i).getUserType());
					   		r.setUserTypeId(ruleList.get(i).getUserTypeId());
					   		r.setRuleOrder(ruleList.get(i).getRuleOrder()-1);
					        ruleListToUpdate.add(r); 
					        logger.info("loop rotated==================== "+i+" rule order size=="+r.getRuleOrder());	  
						  }
					logger.info("final list to be updated==================== "+ruleListToUpdate);	      
			        } 
				  
			  
			  //new rule order is less than existing
			  
			/*
			 * else if(ruleEngineMapping.getRuleOrder()<ruleEngineMappingOld.getRuleOrder())
			 * 
			 * { List<RuleEngineMapping> ruleListToUpdate = new
			 * ArrayList<RuleEngineMapping>(); for(RuleEngineMapping r: ruleList) {
			 * r.setRuleOrder(ruleList1.get(i)); ruleListToUpdate.add(r); i++; } }
			 * 
			 * else{ logger.info("111111111111111"); if(ruleList1.isEmpty()) {
			 * logger.info("increment list is empty="); return new GenricResponse(408,
			 * "ORDER_NOT_EXIST","you can update only rule's order which is already prsent in system"
			 * , "");
			 * 
			 * } else { List<RuleEngineMapping> ruleListToUpdate = new
			 * ArrayList<RuleEngineMapping>(); int i=0;
			 * 
			 * if(rule.isPresent() &&
			 * ruleEngineMapping.getExistOrNot().equalsIgnoreCase("yes") &&
			 * ruleEngineMapping.getRuleOrder()>=ruleEngineMappingOld.getRuleOrder()) {
			 * 
			 * logger.info("((((((((((((()))))))))))"+ruleListToUpdate);
			 * ruleEngineMappingRepository.save(ruleEngineMapping);
			 * ruleEngineMappingRepository.saveAll(ruleListToUpdate);
			 * 
			 * return new GenricResponse(0); }
			 * 
			 * } }
			 */
			 
			 
			
		

			
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
			throw new ResourceServicesException(this.getClass().getName(), e.getMessage());
		}
		return null;
	}
	

	public GenricResponse saveNewRule(RuleEngineMapping ruleEngineMapping){
		try {
		//	Optional<RuleEngineMapping> rule =Optional.ofNullable( ruleEngineMappingRepository.findByNameAndFeatureAndUserTypeAndRuleOrder(ruleEngineMapping.getName(),ruleEngineMapping.getFeature(),ruleEngineMapping.getUserType(),ruleEngineMapping.getRuleOrder()));
		//	logger.info(" rule name bases of feature , user  type ,order and rule name="+rule);
			// fetching list for rule name bases or userType,feature,rule name and rule order if order already exist
			/*
			 * RuleEngineMapping ruleEngineMappingExisting =
			 * ruleEngineMappingRepository.getById(ruleEngineMapping.getId());
			 * logger.info("ruleEngineMappingExisting ::::::::::::: " +
			 * ruleEngineMappingExisting);
			 */
			List<RuleEngineMapping> ruleList = new ArrayList<RuleEngineMapping>();
		
			ruleList =ruleEngineMappingRepository.findByNameAndUserTypeAndFeature(ruleEngineMapping.getName(),ruleEngineMapping.getUserType(),ruleEngineMapping.getFeature());
			logger.info("list sie="+ruleList.size());
			if(ruleList.size()!=0) {
			Comparator<RuleEngineMapping> comp =(i1,i2) ->i1.getRuleOrder()-i2.getRuleOrder();
			 ruleList.sort(comp);
			RuleEngineMapping maxValue=ruleList.get(ruleList.size() - 1);
			logger.info("last index="+maxValue.getRuleOrder());
			//Comparator<RuleEngineMapping> comp =(i1,i2) ->   i1.getRuleOrder()-i2.getRuleOrder();
	       logger.info("sorted total list="+ruleList);
	      
	       
	        if(ruleEngineMapping.getRuleOrder()==null) {
		    	  logger.info(" no rule order is entered ");
		    	  ruleEngineMapping.setRuleOrder(maxValue.getRuleOrder()+1);
		    	  auditTrailRepository.save( new AuditTrail( Long.valueOf(ruleEngineMapping.getUserId()),
    	    			  ruleEngineMapping.getUserName(), Long.valueOf(ruleEngineMapping.getUserTypeId()),
    						"SystemAdmin", Long.valueOf(ruleEngineMapping.getFeatureId()),
    						Features.RULE_FEATURE_MAPPING, SubFeatures.Save, "","NA",
    						ruleEngineMapping.getRoleType(),ruleEngineMapping.getPublicIp(),ruleEngineMapping.getBrowser()));
		    	  logger.info(" save request= "+ruleEngineMapping);
		    	  ruleEngineMappingRepository.save(ruleEngineMapping);
		    	  return new GenricResponse(0);
		      }
	        else if(ruleEngineMapping.getRuleOrder().equals(maxValue.getRuleOrder()+1)) {
	    	  logger.info("entered value is right and save in to db");
	    	  auditTrailRepository.save( new AuditTrail( Long.valueOf(ruleEngineMapping.getUserId()),
	    			  ruleEngineMapping.getUserName(), Long.valueOf(ruleEngineMapping.getUserTypeId()),
						"SystemAdmin", Long.valueOf(ruleEngineMapping.getFeatureId()),
						Features.RULE_FEATURE_MAPPING, SubFeatures.Save, "","NA",
						ruleEngineMapping.getRoleType(),ruleEngineMapping.getPublicIp(),ruleEngineMapping.getBrowser()));
	    	  
	    	  ruleEngineMappingRepository.save(ruleEngineMapping);
	    	  return new GenricResponse(0);
	      }
	      
				/*
				 * else if(ruleEngineMapping.getRuleOrder().equals(maxValue.getRuleOrder())) {
				 * logger.
				 * info("rule order is already exist , maximum  rule order and entered rule order is same ,operation not allowed "
				 * ); return new GenricResponse(401,
				 * "ORDER_NOT_ALLOWED","rule order is already exist , maximum  rule order and entered rule order is same ,operation not allowed"
				 * , ""); }
				 */
				/*
				 * else if(ruleEngineMapping.getRuleOrder()>maxValue.getRuleOrder()+1) {
				 * logger.info("rule is greter then existing order "); return new
				 * GenricResponse(401, "ORDER_NOT_ALLOWED","rule order is invalid", ""); }
				 */
	   
	        else if(ruleEngineMapping.getRuleOrder()>maxValue.getRuleOrder()+1) {
		    	  logger.info("rule is greter then existing order ");
		    	  logger.info("maxvalue Rule order is " +ruleEngineMapping.getRuleOrder());
		    	  logger.info("maxvalue is " +maxValue.getRuleOrder()+1);
		    	  logger.info("last index of Rule Expected ="+maxValue.getRuleOrder()+1);
		    	  
		    	  return new GenricResponse(401, "RULE_ORDER_INVAILD","rule order is invalid", maxValue.getRuleOrder()+1);
		      }
	      else if(ruleEngineMapping.getRuleOrder()<=maxValue.getRuleOrder()) {
	    	     
	    	      if(ruleEngineMapping.getExistOrNot().equals("no")) {
	    	    	  //rule is already exist go back to user confirmation then  come again
	    	    	  return new GenricResponse(402, "RULE_IS_EXIST","rule order is already exist go back to user confirmation", "");
	    	      }
	    	      else if(ruleEngineMapping.getExistOrNot().equals("yes")) {
	    	    	//rule is already exist and user approves to update the rule
	    	    	  List<RuleEngineMapping> ruleListToUpdate = new ArrayList<RuleEngineMapping>();
	    	    	  List<RuleEngineMapping> ruleListToUpdate1 = new ArrayList<RuleEngineMapping>();
	    	    
	    	    	  ruleListToUpdate=ruleEngineMappingRepository.findByNameAndUserTypeAndFeatureAndRuleOrderGreaterThanEqual(ruleEngineMapping.getName(),ruleEngineMapping.getUserType(),ruleEngineMapping.getFeature(),ruleEngineMapping.getRuleOrder());
	    	    	  Comparator<RuleEngineMapping> comp2 =(i1,i2) ->i1.getRuleOrder()-i2.getRuleOrder();
	    	    	  ruleListToUpdate.sort(comp2);
	    	    	  List<Integer> ruleList1 =  ruleListToUpdate.stream().map(i -> i.getRuleOrder()+1).collect(Collectors.toList());
	    	    	  logger.info(" rule list fetch from db bases of  user input===.= +"+ruleListToUpdate);
	    	    	  logger.info(" rule list which is incremet by 1===.= +"+ruleList1);
	    	    	  int i=0;
	    	    	  for(RuleEngineMapping r: ruleListToUpdate) { 
	  					
	  					r.setRuleOrder(ruleList1.get(i));
	  					ruleListToUpdate1.add(r); 
	  					i++;
	  				  } 
	    	    	  logger.info("final rule list which is need to be update.= +"+ruleListToUpdate1);
	    	    	  auditTrailRepository.save( new AuditTrail( Long.valueOf(ruleEngineMapping.getUserId()),
	    	    			  ruleEngineMapping.getUserName(), Long.valueOf(ruleEngineMapping.getUserTypeId()),
	    						"SystemAdmin", Long.valueOf(ruleEngineMapping.getFeatureId()),
	    						Features.RULE_FEATURE_MAPPING, SubFeatures.Save, "","NA",
	    						ruleEngineMapping.getRoleType(),ruleEngineMapping.getPublicIp(),ruleEngineMapping.getBrowser()));
	    	    	  ruleEngineMappingRepository.saveAll(ruleListToUpdate1); 
	    	    	  ruleEngineMappingRepository.save(ruleEngineMapping);
	    	    	  return new GenricResponse(0);
	    	      }
	      }
	      
		
		} 
			
			else{
				  logger.info("no rule order is exist ");
				  logger.info("list is empty=="+ruleList);
				 
		    	  ruleEngineMapping.setRuleOrder(1);
		    	  
		    	  auditTrailRepository.save( new AuditTrail( Long.valueOf(ruleEngineMapping.getUserId()),
    	    			  ruleEngineMapping.getUserName(), Long.valueOf(ruleEngineMapping.getUserTypeId()),
    						"SystemAdmin", Long.valueOf(ruleEngineMapping.getFeatureId()),
    						Features.RULE_FEATURE_MAPPING, SubFeatures.Save, "","NA",
    						ruleEngineMapping.getRoleType(),ruleEngineMapping.getPublicIp(),ruleEngineMapping.getBrowser()));
		    	  logger.info(" save request for fresh rule= "+ruleEngineMapping);
		    	  ruleEngineMappingRepository.save(ruleEngineMapping);
		    	  return new GenricResponse(0);
			}
		}catch (Exception e) {
			logger.info(e.getMessage(), e);
			throw new ResourceServicesException(this.getClass().getName(), e.getMessage());
		}
		return null;
	}
	
	
	public GenricResponse deleteRuleById(RuleEngineMapping ruleEngineMapping){
		try {
			
			
			  RuleEngineMapping ruleEngineMappingOld =  ruleEngineMappingRepository.getById(ruleEngineMapping.getId());
            List<RuleEngineMapping> ruleList = new ArrayList<RuleEngineMapping>();
            ruleList =ruleEngineMappingRepository.findByNameAndUserTypeAndFeature(ruleEngineMappingOld.getName(),ruleEngineMappingOld.getUserType(),ruleEngineMappingOld.getFeature());
            Comparator<RuleEngineMapping> comp =(i1,i2) ->i1.getRuleOrder()-i2.getRuleOrder();
			 ruleList.sort(comp);
			RuleEngineMapping maxValue=ruleList.get(ruleList.size() - 1);
			logger.info("last index="+maxValue.getRuleOrder());
            if(ruleList.isEmpty()) {
            	 logger.info(" no record found for delete ");
            	 return new GenricResponse(401, "NO_RECORD_FOUND","no record found for delete", "");
            }
            else if(ruleEngineMappingOld.getRuleOrder()>maxValue.getRuleOrder()) {
            	logger.info("if rule is greater then max rule order,reject delete ");
            	 return new GenricResponse(401, "MAX_RULE_NOTALLOWED","if rule is greater then max rule order,reject delete", "");
            }
            else if(maxValue.getRuleOrder().equals(ruleEngineMappingOld.getRuleOrder()))
            {	 logger.info(" no record found after delete this  record ,no need to reordring after delete");
            
            auditTrailRepository.save( new AuditTrail( Long.valueOf(ruleEngineMapping.getUserId()),
	    			  ruleEngineMapping.getUserName(), Long.valueOf(ruleEngineMapping.getUserTypeId()),
						"SystemAdmin", Long.valueOf(ruleEngineMapping.getFeatureId()),
						Features.RULE_FEATURE_MAPPING, SubFeatures.DELETE, "","NA",
						ruleEngineMapping.getRoleType(),ruleEngineMapping.getPublicIp(),ruleEngineMapping.getBrowser()));
            	ruleEngineMappingRepository.deleteById(ruleEngineMapping.getId());
            	return new GenricResponse(0);
            }
           
            else if(ruleEngineMappingOld.getRuleOrder()<maxValue.getRuleOrder()) {
            	List<RuleEngineMapping> ruleListToUpdate = new ArrayList<RuleEngineMapping>();
            	ruleListToUpdate =ruleEngineMappingRepository.findByNameAndUserTypeAndFeatureAndRuleOrderGreaterThan(ruleEngineMappingOld.getName(),ruleEngineMappingOld.getUserType(),ruleEngineMappingOld.getFeature(),ruleEngineMappingOld.getRuleOrder());
    			Comparator<RuleEngineMapping> comp1 =(i1,i2) ->   i1.getRuleOrder()-i2.getRuleOrder();
    			ruleListToUpdate.sort(comp1);
    			logger.info(" record need to reordering==="+ruleListToUpdate);
    			
    			List<Integer> ruleList1 =  ruleListToUpdate.stream().map(i -> i.getRuleOrder()-1).collect(Collectors.toList());
    		    logger.info("decremented rule list=="+ruleList1);
    		    List<RuleEngineMapping> finalRuleListToUpdate = new ArrayList<RuleEngineMapping>();
    			 int i=0;
    			for(RuleEngineMapping r: ruleListToUpdate) { 
    				
    				r.setRuleOrder(ruleList1.get(i));
    				finalRuleListToUpdate.add(r); 
    				i++;
    			  }
    			 logger.info("final list to update after delete this  value==="+finalRuleListToUpdate);
    			 
    			   auditTrailRepository.save( new AuditTrail( Long.valueOf(ruleEngineMapping.getUserId()),
    		    			  ruleEngineMapping.getUserName(), Long.valueOf(ruleEngineMapping.getUserTypeId()),
    							"SystemAdmin", Long.valueOf(ruleEngineMapping.getFeatureId()),
    							Features.RULE_FEATURE_MAPPING, SubFeatures.DELETE, "","NA",
    							ruleEngineMapping.getRoleType(),ruleEngineMapping.getPublicIp(),ruleEngineMapping.getBrowser()));
    			 ruleEngineMappingRepository.deleteById(ruleEngineMapping.getId());
    			 ruleEngineMappingRepository.saveAll(ruleListToUpdate);
    			 return new GenricResponse(0);
            }
             
           
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
			throw new ResourceServicesException(this.getClass().getName(), e.getMessage());
		}
		return null;
	}
	
	public GenricResponse deleteById(RuleEngineMapping ruleEngineMapping){
		try {
			
			
		
            List<RuleEngineMapping> ruleList = new ArrayList<RuleEngineMapping>();
            RuleEngineMapping ruleEngineMappingOld =  ruleEngineMappingRepository.getById(ruleEngineMapping.getId());
            logger.info(" &&&&&&&existing value=="+ruleEngineMappingOld);
        
			ruleList =ruleEngineMappingRepository.findByNameAndUserTypeAndFeatureAndRuleOrderGreaterThan(ruleEngineMappingOld.getName(),ruleEngineMappingOld.getUserType(),ruleEngineMappingOld.getFeature(),ruleEngineMappingOld.getRuleOrder());
			Comparator<RuleEngineMapping> comp =(i1,i2) ->   i1.getRuleOrder()-i2.getRuleOrder();
			ruleList.sort(comp);
			logger.info(" ******************   rule name bases of feature , user  type ,order and rule name="+ruleList);
			 
			List<Integer> ruleList1 =  ruleList.stream().map(i -> i.getRuleOrder()-1).collect(Collectors.toList());
		    logger.info("loop eith 1+++++++++++++++++++++++++"+ruleList1);
		    List<RuleEngineMapping> ruleListToUpdate = new ArrayList<RuleEngineMapping>();
			 int i=0;
			for(RuleEngineMapping r: ruleList) { 
				
				r.setRuleOrder(ruleList1.get(i));
				ruleListToUpdate.add(r); 
				i++;
			  }
			
			
			
			logger.info("((((((((((((()))))))))))"+ruleListToUpdate);
			ruleEngineMappingRepository.deleteById(ruleEngineMapping.getId());
			ruleEngineMappingRepository.saveAll(ruleListToUpdate);
			
			return new GenricResponse(0);
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
			throw new ResourceServicesException(this.getClass().getName(), e.getMessage());
		}
	}

	public Page<RuleEngineMapping> filterRuleEngineMapping(FilterRequest filterRequest, Integer pageNo, 
			Integer pageSize,String operation) {

		try {
			

			 String orderColumn =null;
			 Sort.Direction direction;
			 if(Objects.nonNull(filterRequest.getColumnName())) {
			 logger.info("column Name :: " + filterRequest.getColumnName());
			           orderColumn = "0".equalsIgnoreCase(filterRequest.getColumnName()) ? "createdOn":
			        		   "1".equalsIgnoreCase(filterRequest.getColumnName()) ? "modifiedOn":
			        			   "2".equalsIgnoreCase(filterRequest.getColumnName()) ? "name":
			        				   "3".equalsIgnoreCase(filterRequest.getColumnName()) ? "feature":
			        						   "4".equalsIgnoreCase(filterRequest.getColumnName()) ? "ruleOrder":
			        							   "5".equalsIgnoreCase(filterRequest.getColumnName()) ? "graceAction":
			        								   "6".equalsIgnoreCase(filterRequest.getColumnName()) ? "postGraceAction":
			        									   "7".equalsIgnoreCase(filterRequest.getColumnName()) ? "failedRuleActionGrace":
			        										   "8".equalsIgnoreCase(filterRequest.getColumnName()) ? "failedRuleActionPostGrace":
			        											   "9".equalsIgnoreCase(filterRequest.getColumnName()) ? "output"
								       :"modifiedOn";
			//Pageable pageable = PageRequest.of(pageNo, pageSize, new Sort(Sort.Direction.DESC, "modifiedOn"));
			

logger.info("direction and column name:  "+SortDirection.getSortDirection(filterRequest.getSort())+"---column name--"+orderColumn);
if("modifiedOn".equalsIgnoreCase(orderColumn)) {
direction=Sort.Direction.DESC;
}
else {
direction= SortDirection.getSortDirection(filterRequest.getSort());
}
if("modifiedOn".equalsIgnoreCase(orderColumn) && SortDirection.getSortDirection(filterRequest.getSort()).equals(Sort.Direction.ASC)) {
direction=Sort.Direction.ASC;
}
}
			 else {
				 orderColumn="modifiedOn";
				 direction=Sort.Direction.DESC;
			 }
			 
logger.info("final column :  "+orderColumn+"  direction--"+direction);
Pageable pageable = PageRequest.of(pageNo, pageSize,  Sort.by(direction,orderColumn));

			Page<RuleEngineMapping> page = ruleEngineMappingRepository.findAll( buildSpecification(filterRequest).build(), pageable );
			
			String operationType= "view".equalsIgnoreCase(operation) ? SubFeatures.VIEW_ALL : SubFeatures.EXPORT;
			auditTrailRepository.save( new AuditTrail( Long.valueOf(filterRequest.getUserId()),
					filterRequest.getUserName(), Long.valueOf(filterRequest.getUserTypeId()),
					"SystemAdmin", Long.valueOf(filterRequest.getFeatureId()),
					Features.RULE_FEATURE_MAPPING, operationType, "","NA",
					filterRequest.getRoleType(),filterRequest.getPublicIp(),filterRequest.getBrowser()));

			return page;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new ResourceServicesException(this.getClass().getName(), e.getMessage());
		}
	}


	private GenericSpecificationBuilder<RuleEngineMapping> buildSpecification(FilterRequest filterRequest){
		GenericSpecificationBuilder<RuleEngineMapping> cmsb = new GenericSpecificationBuilder<>(propertiesReader.dialect);

		if (Objects.nonNull(filterRequest.getStartDate()) && !filterRequest.getStartDate().isEmpty())
			cmsb.with(new SearchCriteria("createdOn", filterRequest.getStartDate(), SearchOperation.GREATER_THAN,
					Datatype.DATE));

		if (Objects.nonNull(filterRequest.getEndDate()) && !filterRequest.getEndDate().isEmpty())
			cmsb.with(new SearchCriteria("createdOn", filterRequest.getEndDate(), SearchOperation.LESS_THAN,
					Datatype.DATE));
		
		if(Objects.nonNull(filterRequest.getRuleName()) && !filterRequest.getRuleName().isEmpty())
			cmsb.with(new SearchCriteria("name", filterRequest.getRuleName(), SearchOperation.EQUALITY, Datatype.STRING));

		if(Objects.nonNull(filterRequest.getFeatureName()) && !filterRequest.getFeatureName().isEmpty())
			cmsb.with(new SearchCriteria("feature", filterRequest.getFeatureName(), SearchOperation.EQUALITY, Datatype.STRING));

/*		if(Objects.nonNull(filterRequest.getUserType()) && !filterRequest.getUserType().isEmpty() )
			cmsb.with(new SearchCriteria("userType", filterRequest.getUserType(), SearchOperation.EQUALITY, Datatype.STRING));
		*/

		if(Objects.nonNull(filterRequest.getGraceAction()) && !filterRequest.getGraceAction().isEmpty())
			cmsb.with(new SearchCriteria("graceAction", filterRequest.getGraceAction(), SearchOperation.EQUALITY, Datatype.STRING));
		

		if(Objects.nonNull(filterRequest.getPostGraceAction()) && !filterRequest.getPostGraceAction().isEmpty())
			cmsb.with(new SearchCriteria("postGraceAction", filterRequest.getPostGraceAction(), SearchOperation.EQUALITY, Datatype.STRING));
		

		if(Objects.nonNull(filterRequest.getFailedRuleActionGrace()) && !filterRequest.getFailedRuleActionGrace().isEmpty())
			cmsb.with(new SearchCriteria("failedRuleActionGrace", filterRequest.getFailedRuleActionGrace(), SearchOperation.EQUALITY, Datatype.STRING));
		

		if(Objects.nonNull(filterRequest.getFailedRuleActionPostGrace())  && !filterRequest.getFailedRuleActionPostGrace().isEmpty())
			cmsb.with(new SearchCriteria("failedRuleActionPostGrace", filterRequest.getFailedRuleActionPostGrace(), SearchOperation.EQUALITY, Datatype.STRING));
		

		if(Objects.nonNull(filterRequest.getOutput()) &&  !filterRequest.getOutput().isEmpty())
			cmsb.with(new SearchCriteria("output", filterRequest.getOutput(), SearchOperation.EQUALITY, Datatype.STRING));

		if(Objects.nonNull(filterRequest.getRuleOrder()) && !filterRequest.getRuleOrder().isEmpty())
			cmsb.with(new SearchCriteria("ruleOrder", filterRequest.getRuleOrder(), SearchOperation.LIKE, Datatype.STRING));
		
		if(Objects.nonNull(filterRequest.getSearchString()) && !filterRequest.getSearchString().isEmpty()){
			cmsb.orSearch(new SearchCriteria("ruleOrder", filterRequest.getSearchString(), SearchOperation.LIKE, Datatype.STRING));	
			cmsb.orSearch(new SearchCriteria("name", filterRequest.getSearchString(), SearchOperation.LIKE, Datatype.STRING));
			cmsb.orSearch(new SearchCriteria("feature", filterRequest.getSearchString(), SearchOperation.LIKE, Datatype.STRING));	
			cmsb.orSearch(new SearchCriteria("userType", filterRequest.getSearchString(), SearchOperation.LIKE, Datatype.STRING));

		}
		return cmsb;
	}

	private void setInterp(RuleEngineMapping ruleEngineMapping) {
		/*if(Objects.nonNull(consignmentMgmt.getExpectedArrivalPort()))
			consignmentMgmt.setExpectedArrivalPortInterp(interpSetter.setConfigInterp(Tags.CUSTOMS_PORT, consignmentMgmt.getExpectedArrivalPort()));
		 */
	}

	public FileDetails getFile(FilterRequest filter) {
		// TODO Auto-generated method stub

		logger.info("inside rule engine mapping ");
		logger.info("filter data:  "+filter);
		String fileName = null;
		Writer writer   = null;
		RuleEngineMappingFileModel uPFm = null;
		SystemConfigurationDb dowlonadDir=systemConfigurationDbRepository.getByTag("file.download-dir");
		SystemConfigurationDb dowlonadLink=systemConfigurationDbRepository.getByTag("file.download-link");
		DateTimeFormatter dtf  = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		Integer pageNo = 0;
		Integer pageSize = Integer.valueOf(systemConfigurationDbRepository.getByTag("file.max-file-record").getValue());
		String filePath  = dowlonadDir.getValue();
		StatefulBeanToCsvBuilder<RuleEngineMappingFileModel> builder = null;
		StatefulBeanToCsv<RuleEngineMappingFileModel> csvWriter      = null;
		List<RuleEngineMappingFileModel> fileRecords       = null;
		MappingStrategy<RuleEngineMappingFileModel> mapStrategy = new CustomMappingStrategy<>();	
		
		try {
			
			mapStrategy.setType(RuleEngineMappingFileModel.class);
			List<RuleEngineMapping> list = filterRuleEngineMapping(filter, pageNo, pageSize,"Export").getContent();
			logger.info("getting list : "+list);
			if( list.size()> 0 ) {
				fileName = LocalDateTime.now().format(dtf).replace(" ", "_")+"_RuleEngineMapping.csv";
			}else {
				fileName = LocalDateTime.now().format(dtf).replace(" ", "_")+"_RuleEngineMapping.csv";
			}
			logger.info(" file path plus file name: "+Paths.get(filePath+fileName));
			writer = Files.newBufferedWriter(Paths.get(filePath+fileName));
//			builder = new StatefulBeanToCsvBuilder<UserProfileFileModel>(writer);
//			csvWriter = builder.withQuotechar(CSVWriter.DEFAULT_QUOTE_CHARACTER).build();
//			
			builder = new StatefulBeanToCsvBuilder<>(writer);
			csvWriter = builder.withMappingStrategy(mapStrategy).withSeparator(',').withQuotechar(CSVWriter.NO_QUOTE_CHARACTER).build();

			if( list.size() > 0 ) {
				//List<SystemConfigListDb> systemConfigListDbs = configurationManagementServiceImpl.getSystemConfigListByTag("GRIEVANCE_CATEGORY");
				fileRecords = new ArrayList<RuleEngineMappingFileModel>(); 
				for( RuleEngineMapping ruleEngineMapping : list ) {
					uPFm = new RuleEngineMappingFileModel();
					uPFm.setCreatedOn(ruleEngineMapping.getCreatedOn().format(dtf));
					uPFm.setModifiedOn(ruleEngineMapping.getModifiedOn().format(dtf));
					uPFm.setName(ruleEngineMapping.getName());
					uPFm.setFeature(ruleEngineMapping.getFeature());
					uPFm.setRuleOrder(ruleEngineMapping.getRuleOrder());
					uPFm.setGraceAction(ruleEngineMapping.getGraceAction());
					uPFm.setPostGraceAction(ruleEngineMapping.getPostGraceAction());
					uPFm.setFailedRuleActionGrace(ruleEngineMapping.getFailedRuleActionGrace());
					uPFm.setFailedRuleActionPostGrace(ruleEngineMapping.getFailedRuleActionPostGrace());
					uPFm.setOutput(ruleEngineMapping.getOutput());
					fileRecords.add(uPFm);
				}
				logger.info("filePath::::"+uPFm);
				csvWriter.write(fileRecords);
			}
			logger.info("fileName::"+fileName);
			logger.info("filePath::::"+filePath);
			logger.info("link:::"+dowlonadLink.getValue());
			return new FileDetails(fileName, filePath,dowlonadLink.getValue().replace("$LOCAL_IP",propertiesReader.localIp)+fileName ); 
		
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new ResourceServicesException(this.getClass().getName(), e.getMessage());
		}finally {
			try {

				if( writer != null )
					writer.close();
			} catch (IOException e) {}
		}

	
	}
	
	//------------------Feature Dropdown---------------------
	//------------------Feature Dropdown---------------------
	  public List<RuleEngineMapping> getfeaturebyRuleName(String name) {
        try {
             logger.info("getfeature by Rule's Name" + name);
             return ruleEngineMappingRepository.findDistinctByName(name);
        } catch (Exception e) {
             logger.error(e.getMessage(), e);
             throw new ResourceServicesException(this.getClass().getName(), e.getMessage());
        }

   }
	//------------------userType by feature Dropdown---------------------
	  public List<RuleEngineMapping> getUserTypebyFeature(String featureName,String name) {
        try {
             logger.info("get UserType by feature Name" + featureName+" name=="+name);
             return ruleEngineMappingRepository.findByFeatureAndName(featureName,name);
        } catch (Exception e) {
             logger.error(e.getMessage(), e);
             throw new ResourceServicesException(this.getClass().getName(), e.getMessage());
        }

   }

}