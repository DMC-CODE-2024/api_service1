package com.gl.ceir.config.strategy;

import com.opencsv.bean.ColumnPositionMappingStrategy;

public class ConsignmentCsvMappingStrategy<T> extends ColumnPositionMappingStrategy<T>{

	private static final String[] HEADER = new String[]{"Txn Id", "consignmentStatus", "supplierName", "taxPaidStatus", "fileName", "createdOn", "modifiedOn"};

    public String[] generateHeader() {
        return HEADER;
    }
}
