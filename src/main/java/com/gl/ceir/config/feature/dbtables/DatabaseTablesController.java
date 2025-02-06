package com.gl.ceir.config.feature.dbtables;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class DatabaseTablesController {

    private static final Logger logger = LogManager.getLogger(DatabaseTablesController.class);

    @Autowired
    DatabaseTablesServiceImpl databaseTablesServiceImpl;

    @Tag(name = "DB Tables", description = "System Configuration Module API")
    @Operation(
            summary = "Fetch table row details from the data source",
            description = "fetch all entities from a data source and export the records into a CSV file.")
    @RequestMapping(path = "/db/table/data/V3", method = {RequestMethod.POST})
    public MappingJacksonValue getTableDataV3(@RequestBody TableFilterRequest filterRequest, @RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber, @RequestParam(value = "pageSize", defaultValue = "10") int pageSize, @RequestParam(value = "file", defaultValue = "0", required = false) int file) {
        logger.info("Datatable filter request:[" + filterRequest.toString() + "]");
        if (file == 0)
            return new MappingJacksonValue(databaseTablesServiceImpl.getTableDataV3(filterRequest, pageNumber, pageSize));
        else
            return new MappingJacksonValue(databaseTablesServiceImpl.getTableDataInFile(filterRequest, pageNumber, pageSize));
    }

    @Tag(name = "DB Tables", description = "System Configuration Module API")
    @Operation(
            summary = "Fetch table from the data source",
            description = "Fetch all tables name from datasource based on the received request")
    @RequestMapping(path = "/db/tables", method = {RequestMethod.POST})
    public MappingJacksonValue getAllTables(@RequestParam(value = "dbName") String dbName, @RequestParam(value = "userId") Long userId, @RequestParam(value = "userType") String userType, @RequestParam(value = "featureId") Long featureId, @RequestParam(value = "publicIp", defaultValue = "NA") String publicIp, @RequestParam(value = "browser", defaultValue = "NA") String browser) {
        return new MappingJacksonValue(databaseTablesServiceImpl.getTableNames(dbName, userId, featureId, userType, publicIp, browser));
    }

    @Tag(name = "DB Tables", description = "System Configuration Module API")
    @Operation(
            summary = "Fetch table headers from the data source",
            description = "Fetch all tables headers from datasource based on the received request")
    @RequestMapping(path = "/db/table/details", method = {RequestMethod.POST})
    public MappingJacksonValue getTableColumns(@RequestParam(value = "dbName") String dbName, @RequestParam(value = "tableName") String tableName) {
      return new MappingJacksonValue(databaseTablesServiceImpl.getTableColumnNames(dbName, tableName));
    }


    @Tag(name = "DB Tables", description = "System Configuration Module API")
    @Operation(
            summary = "Fetch Database name from the data source",
            description = "Fetch all database name")
    @RequestMapping(path = "/db/table/db", method = {RequestMethod.POST})
    public MappingJacksonValue getDBList() {
        List<String> dbList = databaseTablesServiceImpl.getDBList();
        return new MappingJacksonValue(dbList);
    }

    //@ApiOperation(value = "Get table data.", response = TableData.class)
    @Tag(name = "DB Tables", description = "System Configuration Module API")
    @Operation(
            summary = "Fetch table data",
            description = "Fetch table data from data source")
    @RequestMapping(path = "/db/table/data", method = {RequestMethod.POST})
    public MappingJacksonValue getTableData(@RequestBody TableFilterRequest filterRequest, @RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber, @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return new MappingJacksonValue(databaseTablesServiceImpl.getTableData(filterRequest, pageNumber, pageSize));
    }


}
