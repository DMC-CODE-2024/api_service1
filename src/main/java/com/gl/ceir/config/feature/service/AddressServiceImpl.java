package com.gl.ceir.config.feature.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gl.ceir.config.model.app.CommuneDb;
import com.gl.ceir.config.model.app.DistrictDb;
import com.gl.ceir.config.model.app.PoliceStationDb;
import com.gl.ceir.config.model.app.ProvinceDb;
import com.gl.ceir.config.repository.app.CommunePoliceRepository;
import com.gl.ceir.config.repository.app.CommuneRepository;
import com.gl.ceir.config.repository.app.DistrictRepository;
import com.gl.ceir.config.repository.app.ProvinceRepository;

@Service
public class AddressServiceImpl {

	@Autowired
	ProvinceRepository provinceRepository;
	
	@Autowired DistrictRepository districtRepository;
	
	@Autowired CommunePoliceRepository communePoliceRepository;
	
	@Autowired CommuneRepository communeRepository;
	
	public List<AddressResp> getAllProvince(String lang) {
	    List<AddressResp> list = new ArrayList<>();
	    List<ProvinceDb> provinceDbList = provinceRepository.findAll();
	    
	    for (ProvinceDb db : provinceDbList) {
	        AddressResp res = new AddressResp();
	        res.setId(db.getId());
	        if ("en".equalsIgnoreCase(lang) && db.getProvince() != null) {
	            res.setName(db.getProvince());
	        } else {
	            res.setName(db.getProvinceKm());
	        }
	        list.add(res);
	    }
	    return list;
	}
	
	public List<AddressResp> getProvince(String lang, long id) {
	    List<AddressResp> list = new ArrayList<>();
	    List<ProvinceDb> provinceDbList = provinceRepository.findById(id);  // Assuming findById returns a List<ProvinceDb>
	    
	    if (provinceDbList != null && !provinceDbList.isEmpty()) {
	        for (ProvinceDb db : provinceDbList) {
	            AddressResp res = new AddressResp();
	            res.setId(db.getId());
	            if ("en".equalsIgnoreCase(lang) && db.getProvince() != null) {
	                res.setName(db.getProvince());
	            } else {
	                res.setName(db.getProvinceKm());
	            }
	            list.add(res);
	        }
	    }
	    return list;
	}
	
	/*public AddressResp getProvinceById(String lang, long id) {
		 AddressResp res =null;
	    ProvinceDb provinceDbList = provinceRepository.findByIds(id);  // Assuming findById returns a List<ProvinceDb>
	    
	    if (provinceDbList!=null) {
	    	 res = new AddressResp();
	            res.setId(provinceDbList.getId());
	            if ("en".equalsIgnoreCase(lang) && provinceDbList.getProvince() != null) {
	                res.setName(provinceDbList.getProvince());
	            } else {
	                res.setName(provinceDbList.getProvinceKm());
	            }
	    }
	    return res;
	}*/
	
	
	public List<AddressResp> getDistricts(String lang, long id) {
	   
		List<AddressResp> list = new ArrayList<>();
	    
	    List<DistrictDb> DbList = districtRepository.findByProvinceId(id);
	    
	    if (DbList != null && !DbList.isEmpty()) {
	    	
	    	for (DistrictDb db : DbList) {
	    	    AddressResp res = new AddressResp();
	    	    res.setId(db.getId());
	    	    System.out.println("District ID: " + db.getId());
	    	    
	    	    if ("en".equalsIgnoreCase(lang) && db.getDistrict() != null) {
	    	        res.setName(db.getDistrict());
	    	        System.out.println("District Name (EN): " + db.getDistrict());
	    	    } else {
	    	        res.setName(db.getDistrictKm());
	    	        System.out.println("District Name (KM): " + db.getDistrictKm());
	    	    }
	    	    
	    	    list.add(res);
	    	}

	    }
	    
	    return list;
	}
	public AddressResp getDistrictsById(String lang, long id) {
		   
		AddressResp res =null;
	    DistrictDb DbList = districtRepository.findById(id);
	    
	    if (DbList != null) {
	    	res = new AddressResp();
    	    res.setId(DbList.getId());
    	    System.out.println("District ID: " + DbList.getId());
    	    
    	    if ("en".equalsIgnoreCase(lang) && DbList.getDistrict() != null) {
    	        res.setName(DbList.getDistrict());
    	        System.out.println("District Name (EN): " + DbList.getDistrict());
    	    } else {
    	        res.setName(DbList.getDistrictKm());
    	        System.out.println("District Name (KM): " + DbList.getDistrictKm());
    	    }
	    }
	    
	    return res;
	}
	
	
	public List<AddressResp> getCommune(String lang, int id) {
		   
		List<AddressResp> list = new ArrayList<>();
	    
	    List<CommuneDb> DbList = communeRepository.findByDistrictId(id);
	    
	    if (DbList != null && !DbList.isEmpty()) {
	    	
	    	for (CommuneDb db : DbList) {
	    	    AddressResp res = new AddressResp();
	    	    res.setId(db.getId());
	    	    System.out.println("Commune ID: " + db.getId());
	    	    
	    	    if ("en".equalsIgnoreCase(lang) && db.getCommune() != null) {
	    	        res.setName(db.getCommune());
	    	        System.out.println("Commune Name (EN): " + db.getCommune());
	    	    } else {
	    	        res.setName(db.getCommuneKm());
	    	        System.out.println("Commune Name (KM): " + db.getCommuneKm());
	    	    }
	    	    
	    	    list.add(res);
	    	}

	    }
	    
	    return list;
	}
	public AddressResp getCommuneById(String lang, int id) {
		   
		AddressResp res=null;
	    CommuneDb DbList = communeRepository.findById(id);
	    
	    if (DbList != null ) {
	    	
	    	res = new AddressResp();
    	    res.setId(DbList.getId());
    	    System.out.println("Commune ID: " + DbList.getId());
    	    
    	    if ("en".equalsIgnoreCase(lang) && DbList.getCommune() != null) {
    	        res.setName(DbList.getCommune());
    	        System.out.println("Commune Name (EN): " + DbList.getCommune());
    	    } else {
    	        res.setName(DbList.getCommuneKm());
    	        System.out.println("Commune Name (KM): " + DbList.getCommuneKm());
    	    }

	    }
	    
	    return res;
	}
	
	
	public List<AddressResp> getPoliceStation(String lang, int id) {
		   
		List<AddressResp> list = new ArrayList<>();
	    
	    List<PoliceStationDb> DbList = communePoliceRepository.findByCommuneId(id);
	    
	    if (DbList != null && !DbList.isEmpty()) {
	    	
	    	for (PoliceStationDb db : DbList) {
	    	    AddressResp res = new AddressResp();
	    	    res.setId(db.getId());
	    	    System.out.println("Police ID: " + db.getId());
	    	    
	    	    if ("en".equalsIgnoreCase(lang) && db.getPolice() != null) {
	    	        res.setName(db.getPolice());
	    	        System.out.println("Police Name (EN): " + db.getPolice());
	    	    } else {
	    	        res.setName(db.getPoliceKm());
	    	        System.out.println("Police Name (KM): " + db.getPoliceKm());
	    	    }
	    	    
	    	    list.add(res);
	    	}

	    }
	    
	    return list;
	}

	public AddressResp getPoliceStationById(String lang, int id) {
		   
		AddressResp res=null;
	    
	    PoliceStationDb DbList = communePoliceRepository.findById(id);
	    
	    if (DbList != null) {
	    	
	    	 res = new AddressResp();
    	    res.setId(DbList.getId());
    	    System.out.println("Police ID: " + DbList.getId());
    	    
    	    if ("en".equalsIgnoreCase(lang) && DbList.getPolice() != null) {
    	        res.setName(DbList.getPolice());
    	        System.out.println("Police Name (EN): " + DbList.getPolice());
    	    } else {
    	        res.setName(DbList.getPoliceKm());
    	        System.out.println("Police Name (KM): " + DbList.getPoliceKm());
    	    }
	    }
	    
	    return res;
	}


}
