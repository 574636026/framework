package com.injedu.core.supports.excel.domain;

import java.util.ArrayList;
import java.util.List;

public class ExcelMappings {
    private String namespace;
    
    private List<ExcelMapping> excelMappings = new ArrayList<ExcelMapping>();

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public List<ExcelMapping> getExcelMappings() {
        return excelMappings;
    }

    public void setExcelMappings(List<ExcelMapping> excelMappings) {
        this.excelMappings = excelMappings;
    }
    
    
}
