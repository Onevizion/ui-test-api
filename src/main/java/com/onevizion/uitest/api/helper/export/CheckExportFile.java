package com.onevizion.uitest.api.helper.export;

import java.util.List;

public interface CheckExportFile {

    void checkExportCsvFile(String processId, String fileName);

    void checkExportCsvFile(String processId, String fileName, List<Integer> uniqueColumns);

    void checkExportExcelFile(String processId, String fileName);

    void checkExportExcelFile(String processId, String fileName, List<Integer> uniqueColumns);

}