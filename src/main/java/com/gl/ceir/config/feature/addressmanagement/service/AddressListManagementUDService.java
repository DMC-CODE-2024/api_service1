package com.gl.ceir.config.feature.addressmanagement.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.gl.ceir.config.feature.addressmanagement.FeaturesEnum;
import com.gl.ceir.config.feature.addressmanagement.audit_trail.AuditTrailService;
import com.gl.ceir.config.feature.common.LocalDateTimeDeserializer;
import com.gl.ceir.config.feature.common.LocalDateTimeSerializer;
import com.gl.ceir.config.feature.operatorseries.model.GenricResponse;
import com.gl.ceir.config.model.app.AddressEntity;
import com.gl.ceir.config.model.app.CommuneDb;
import com.gl.ceir.config.model.app.DistrictDb;
import com.gl.ceir.config.model.app.ProvinceDb;
import com.gl.ceir.config.repository.app.AddressListMgmtRepository;
import com.gl.ceir.config.repository.app.CommuneRepository;
import com.gl.ceir.config.repository.app.DistrictRepository;
import com.gl.ceir.config.repository.app.ProvinceRepository;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
public class AddressListManagementUDService {
    private final Logger logger = LogManager.getLogger(this.getClass());

    private final AddressListMgmtRepository addressListMgmtRepository;
    private final CommuneRepository communeRepository;
    private final DistrictRepository districtRepository;
    private final AuditTrailService auditTrailService;
    private final ProvinceRepository provinceRepository;

    public AddressListManagementUDService(ProvinceRepository provinceRepository, AuditTrailService auditTrailService, AddressListMgmtRepository addressListMgmtRepository, CommuneRepository communeRepository, DistrictRepository districtRepository) {
        this.provinceRepository = provinceRepository;
        this.auditTrailService = auditTrailService;
        this.communeRepository = communeRepository;
        this.addressListMgmtRepository = addressListMgmtRepository;
        this.districtRepository = districtRepository;
    }

    @Transactional
    public GenricResponse delete(AddressEntity addressEntity) {
        String requestType = "ADDRESS_MGMT_DELETE";
        boolean isIdExist = addressListMgmtRepository.existsById(addressEntity.getId());
        logger.info("isIdExist" + isIdExist);
        if (isIdExist) {
            AddressEntity communeList = addressListMgmtRepository.findByProvinceAndDistrictAndCommune(addressEntity.getProvince(), addressEntity.getDistrict(), addressEntity.getCommune());
            logger.info("communeList " + communeList);
            if (Objects.nonNull(communeList)) {
                logger.info("inside communeList block");
                communeRepository.deleteByCommune(addressEntity.getCommune());
                logger.info("Commune Successfully deleted from commune");

                AddressEntity districtList = addressListMgmtRepository.findByProvinceAndDistrict(addressEntity.getProvince(), addressEntity.getDistrict());
                if (Objects.nonNull(districtList)) {
                    districtRepository.deleteByDistrict(addressEntity.getDistrict());
                    logger.info("District Successfully deleted from district");
                }
            }
            addressListMgmtRepository.deleteById(addressEntity.getId());

            auditTrailService.auditTrailOperation(addressEntity.getAuditTrailModel(), FeaturesEnum.getFeatureName(requestType), FeaturesEnum.getSubFeatureName(requestType));

            return new GenricResponse("Record deleted", 1);
        }

        return new GenricResponse("Unable to delete record", 0);
    }

    /*    public GenricResponse update(AddressEntity addressEntity) {
            Optional<AddressEntity> byId = addressListMgmtRepository.findById(addressEntity.getId());
            logger.info("response {}", byId);
            Optional<DistrictEntity> byProvinceAndDistrict = Optional.empty();
            Optional<CommuneEntity> byCommune = Optional.empty();
            if (byId.isPresent()) {
                Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).create();
                AddressEntity result = gson.fromJson(gson.toJson(byId.get()), AddressEntity.class);

                if (Objects.nonNull(result.getProvince()) && Objects.nonNull(result.getDistrict())) {
                    try {
                        byProvinceAndDistrict = districtRepository.findByProvinceAndDistrict(result.getProvince(), result.getDistrict());
                        logger.info("byProvinceAndDistrict {}", byProvinceAndDistrict);
                        if (byProvinceAndDistrict.isPresent()) {
                            logger.info("district {} and commune {}", byProvinceAndDistrict.get().getId(), result.getCommune());
                            byCommune = communeRepository.findByCommuneAndDistrictId(byId.get().getCommune(), Math.toIntExact(byProvinceAndDistrict.get().getId()));
                            logger.info("byCommune {}", byCommune);
                        }
                    } catch (Exception e) {
                        logger.info("duplicate records found");
                    }

                }
                String lang = addressEntity.getLanguage();
                if (lang.equals("en")) {


                    provinceEnUpdate(byId.get().getProvince(), result.getProvince());

                    byProvinceAndDistrict.ifPresent(districtEntity -> districtEnUpdate(result, districtEntity));

                    byCommune.ifPresent(communeEntity -> communeEnUpdate(result, communeEntity));
                    result.setCommune(addressEntity.getCommune());
                    result.setDistrict(addressEntity.getDistrict());
                    result.setProvince(addressEntity.getProvince());

                } else if (lang.equals("km")) {

                    provinceKmUpdate(byId.get().getProvince(), result.getProvinceKm());

                    byProvinceAndDistrict.ifPresent(districtEntity -> districtKmUpdate(result, districtEntity));

                    byCommune.ifPresent(communeEntity -> communeKmUpdate(result, communeEntity));
                    result.setCommuneKm(addressEntity.getCommuneKm());
                    result.setDistrictKm(addressEntity.getDistrictKm());
                    result.setProvinceKm(addressEntity.getProvinceKm());
                }

                logger.info("payload to save {}", result);
                updateAddressListMgmt(result);

                String requestType = "ADDRESS_MGMT_UPDATE";
                logger.info("requestType [" + requestType + "]");
                auditTrailService.auditTrailOperation(addressEntity.getAuditTrailModel(), FeaturesEnum.getFeatureName(requestType), FeaturesEnum.getSubFeatureName(requestType));


                return new GenricResponse("Record successfully updated", 1);

            }
            return new GenricResponse("No record found for id" + addressEntity.getId(), 0);
        }*/
    public GenricResponse update(AddressEntity addressEntity) throws JsonProcessingException {
        Optional<AddressEntity> byId = addressListMgmtRepository.findById(addressEntity.getId());
        logger.info("response {}", byId);
        Optional<DistrictDb> byDistrict = Optional.empty();
        Optional<ProvinceDb> byProvince = Optional.empty();
        Optional<CommuneDb> byCommune = Optional.empty();
        if (byId.isPresent()) {
  /*      Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).create();
        AddressEntity result = gson.fromJson(gson.toJson(byId.get()), AddressEntity.class);
*/
            ObjectMapper objectMapper = new ObjectMapper();
            // Register custom serializers and deserializers
            SimpleModule module = new SimpleModule();
            module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
            module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
            objectMapper.registerModule(module);
            String json = objectMapper.writeValueAsString(byId.get());
            AddressEntity result = objectMapper.readValue(json, AddressEntity.class);


            if (Objects.nonNull(result.getProvince()) && Objects.nonNull(result.getDistrict())) {
                try {
                    byDistrict = districtRepository.findByDistrict(result.getDistrict());
                    byProvince = provinceRepository.findByProvince(result.getProvince());
                    logger.info("byProvinceAndDistrict {}", byProvince, byDistrict);
                    if (byDistrict.isPresent() && byProvince.isPresent()) {
                        logger.info("district {} and commune {}", byDistrict.get().getId(), byProvince.get().getId(), result.getCommune());
                        byCommune = communeRepository.findByCommuneAndDistrictId(byId.get().getCommune(), Math.toIntExact(byDistrict.get().getId()));
                        logger.info("byCommune {}", byCommune);
                    }
                } catch (Exception e) {
                    logger.info("duplicate records found");
                }

            }
            // Update both English and Khmer fields
            provinceEnUpdate(byId.get().getProvince(), result.getProvince());
            provinceKmUpdate(byId.get().getProvince(), result.getProvinceKm());

            byDistrict.ifPresent(districtEntity -> {
                districtEnUpdate(result, districtEntity);
                districtKmUpdate(result, districtEntity);
            });

            byCommune.ifPresent(communeEntity -> {
                communeEnUpdate(result, communeEntity);
                communeKmUpdate(result, communeEntity);
            });

            result.setCommune(addressEntity.getCommune());
            result.setDistrict(addressEntity.getDistrict());
            result.setProvince(addressEntity.getProvince());

            result.setCommuneKm(addressEntity.getCommuneKm());
            result.setDistrictKm(addressEntity.getDistrictKm());
            result.setProvinceKm(addressEntity.getProvinceKm());


            logger.info("payload to save {}", result);
            updateAddressListMgmt(result);

            String requestType = "ADDRESS_MGMT_UPDATE";
            logger.info("requestType [" + requestType + "]");
            auditTrailService.auditTrailOperation(addressEntity.getAuditTrailModel(), FeaturesEnum.getFeatureName(requestType), FeaturesEnum.getSubFeatureName(requestType));


            return new GenricResponse("Record successfully updated", 1);

        }
        return new GenricResponse("No record found for id" + addressEntity.getId(), 0);
    }

    private void updateAddressListMgmt(AddressEntity addressEntity) {
        try {
            addressListMgmtRepository.save(addressEntity);
            logger.info("address_list_mgmt record has been updated for id {}", addressEntity.getId());
        } catch (Exception e) {
            logger.info("exception occured while updating data {}", e.getMessage());
        }
    }


    private void provinceEnUpdate(String oldVal, String newVal) {
        logger.info("oldVal {} and newVal {}", oldVal, newVal);
        int rowAffectedCount = provinceRepository.updateEnColumns(oldVal, newVal);
        if (rowAffectedCount > 0) logger.info("province_db en record has been updated for province {}", oldVal);

    }

    private void provinceKmUpdate(String oldVal, String newVal) {
        int rowAffectedCount = provinceRepository.updateKmColumns(oldVal, newVal);
        if (rowAffectedCount > 0) logger.info("province_db km record has been updated for province {}", oldVal);
    }

    private void districtEnUpdate(AddressEntity addressEntity, DistrictDb district) {

        int rowAffected = districtRepository.updateEnColumns(addressEntity, district.getId());
        if (rowAffected > 0) logger.info("district_db en record has been updated for id {}", district.getId());


    }

    private void districtKmUpdate(AddressEntity addressEntity, DistrictDb district) {
        int rowAffected = districtRepository.updateKmColumns(addressEntity, district.getId());
        if (rowAffected > 0) logger.info("district_db km record has been updated for id {}", district.getId());

    }

    private void communeEnUpdate(AddressEntity addressEntity, CommuneDb commune) {
        int rowAffected = communeRepository.updateEnColumns(addressEntity, commune.getId());
        if (rowAffected > 0) logger.info("commune_db en record has been updated for id {}", commune.getId());
    }

    private void communeKmUpdate(AddressEntity addressEntity, CommuneDb commune) {

        int rowAffected = communeRepository.updateKmColumns(addressEntity, commune.getId());
        if (rowAffected > 0) logger.info("commune_db km record has been updated for id {}", commune.getId());

    }
}

