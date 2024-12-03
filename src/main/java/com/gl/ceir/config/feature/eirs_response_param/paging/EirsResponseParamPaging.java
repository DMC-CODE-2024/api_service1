package com.gl.ceir.config.feature.eirs_response_param.paging;

import com.gl.ceir.config.configuration.PropertiesReader;
import com.gl.ceir.config.configuration.SortDirection;
import com.gl.ceir.config.exceptions.ResourceServicesException;
import com.gl.ceir.config.feature.addressmanagement.AuditTrailModel;
import com.gl.ceir.config.feature.specificationsbuilder.GenericSpecificationBuilder;
import com.gl.ceir.config.feature.specificationsbuilder.SearchCriteria;
import com.gl.ceir.config.model.app.EirsResponse;
import com.gl.ceir.config.model.constants.Datatype;
import com.gl.ceir.config.model.constants.SearchOperation;
import com.gl.ceir.config.repository.app.EirsResponseParamRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Component
public class EirsResponseParamPaging {
    private final Logger logger = LogManager.getLogger(this.getClass());
    @Autowired
    private PropertiesReader propertiesReader;
    private EirsResponseParamRepository eirsResponseParamRepository;

    public EirsResponseParamPaging(EirsResponseParamRepository eirsResponseParamRepository) {
        this.eirsResponseParamRepository = eirsResponseParamRepository;
    }

    public Page<EirsResponse> findAll(EirsResponse eirsResponse, int pageNo, int pageSize) {
        try {
            logger.info("request received : " + eirsResponse + " [pageNo] ...." + pageNo + " [pageSize]...." + pageSize);
            String sort = null, orderColumn = null;
            if (Objects.nonNull(eirsResponse.getAuditTrailModel())) {
                sort = Objects.nonNull(eirsResponse.getAuditTrailModel().getSort()) ? eirsResponse.getAuditTrailModel().getSort() : "desc";
                orderColumn = Objects.nonNull(eirsResponse.getAuditTrailModel().getColumnName()) ? eirsResponse.getAuditTrailModel().getColumnName() : "Modified On";
            } else {
                sort = "desc";
                orderColumn = "Modified On";
            }
            orderColumn = sortColumnName(orderColumn);
            Sort.Direction direction = SortDirection.getSortDirection(sort);
            logger.info("request received : " + eirsResponse + " [pageNo] ...." + pageNo + " [pageSize]...." + pageSize);

            logger.info("orderColumn is : " + orderColumn + " & direction is : " + direction);

            Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(direction, orderColumn));
            Page<EirsResponse> page = eirsResponseParamRepository.findAll(buildSpecification(eirsResponse).build(), pageable);
            logger.info("paging API response [" + page + "]");
            return page;

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ResourceServicesException(this.getClass().getName(), e.getMessage());
        }
    }

    public String sortColumnName(String columnName) {
        Map<String, String> map = new HashMap<>();
        if (Objects.nonNull(columnName) && !columnName.isEmpty()) {
            map.put("Created On", "createdOn");
            map.put("Description", "description");
            map.put("Modified On", "modifiedOn");
            map.put("Tag", "tag");
            map.put("Type", "type");
            map.put("Value", "value");
            map.put("Active", "active");
            map.put("Feature Name", "featureName");
            map.put("Remarks", "remarks");
            map.put("Language", "language");
        }
        return map.get(columnName);
    }

    private GenericSpecificationBuilder<EirsResponse> buildSpecification(EirsResponse eirsResponse) {
        logger.info("FilterRequest payload : [" + eirsResponse + "]");
        GenericSpecificationBuilder<EirsResponse> cmsb = new GenericSpecificationBuilder<>(propertiesReader.dialect);

        Optional<AuditTrailModel> optional = Optional.ofNullable(eirsResponse.getAuditTrailModel());
        if (optional.isPresent()) {
            cmsb.with(new SearchCriteria("createdOn", optional.get().getStartDate(), SearchOperation.GREATER_THAN, Datatype.DATE));
            cmsb.with(new SearchCriteria("createdOn", optional.get().getEndDate(), SearchOperation.LESS_THAN, Datatype.DATE));
        }
        cmsb.with(new SearchCriteria("description", eirsResponse.getDescription(), SearchOperation.LIKE, Datatype.STRING));
        cmsb.with(new SearchCriteria("tag", eirsResponse.getTag(), SearchOperation.LIKE, Datatype.STRING));
        cmsb.with(new SearchCriteria("type", eirsResponse.getType(), SearchOperation.EQUALITY, Datatype.INT));
        cmsb.with(new SearchCriteria("value", eirsResponse.getValue(), SearchOperation.LIKE, Datatype.STRING));
        cmsb.with(new SearchCriteria("active", eirsResponse.getActive(), SearchOperation.EQUALITY, Datatype.INT));
        cmsb.with(new SearchCriteria("featureName", eirsResponse.getFeatureName(), SearchOperation.LIKE, Datatype.STRING));
        cmsb.with(new SearchCriteria("remarks", eirsResponse.getRemarks(), SearchOperation.LIKE, Datatype.STRING));
        cmsb.with(new SearchCriteria("language", eirsResponse.getLanguage(), SearchOperation.LIKE, Datatype.STRING));
        return cmsb;
    }
}