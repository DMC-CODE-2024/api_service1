package com.gl.ceir.config.feature.addressmanagement.service;

import com.gl.ceir.config.model.app.CommuneDb;
import com.gl.ceir.config.model.app.DistrictDb;
import com.gl.ceir.config.repository.app.CommuneRepository;
import com.gl.ceir.config.repository.app.DistrictRepository;
import com.gl.ceir.config.service.impl.DistinctParamService;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class AddressListManagementDistinct {

    private final DistinctParamService distinctParamService;
    private final DistrictRepository districtRepository;
    private final CommuneRepository communeRepository;

    public AddressListManagementDistinct(CommuneRepository communeRepository, DistinctParamService distinctParamService, DistrictRepository districtRepository) {
        this.communeRepository = communeRepository;
        this.distinctParamService = distinctParamService;
        this.districtRepository = districtRepository;
    }


    public Map<String, List<?>> distinct(List<String> param, Class<?> entity) {
        return distinctParamService.distinct(param, entity);
    }

    public Optional<List<DistrictDb>> findByProvinceId(Long provinceId) {
        List<DistrictDb> sortList = districtRepository.findByProvinceId(provinceId);
        sortList.sort(Comparator.comparing(DistrictDb::getDistrict));
        return Optional.ofNullable(sortList);
    }

    public Optional<List<CommuneDb>> findByDistrict(String district) {
        Optional<DistrictDb> byDistrict = districtRepository.findByDistrictOrDistrictKm(district, district);
        if (byDistrict.isPresent()) {
            List<CommuneDb> sortList = communeRepository.findByDistrictId(Integer.parseInt(byDistrict.get().getId().toString()));
            sortList.sort(Comparator.comparing(CommuneDb::getCommune));
            return Optional.ofNullable(sortList);
        }
        return Optional.empty();
    }
}
