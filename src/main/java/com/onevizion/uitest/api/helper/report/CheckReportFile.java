package com.onevizion.uitest.api.helper.report;

import java.util.List;

public interface CheckReportFile {

    void checkReportCsvFile(String processId, String fileName);

    void checkReportCsvFile(String processId, String fileName, List<Integer> uniqueColumns);

    void checkReportExcelFile(String processId, String fileName);

    void checkReportExcelFile(String processId, String fileName, List<Integer> uniqueColumns);

}