package com.onevizion.guitest.helper.export;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.testng.Assert;

//import com.aspose.cells.Cells;
//import com.aspose.cells.Workbook;
import com.onevizion.guitest.AbstractSeleniumCore;
import com.onevizion.guitest.SeleniumSettings;
//import com.onevizion.guitest.db.ExportDbHelper;
import com.onevizion.guitest.exception.SeleniumUnexpectedException;
import com.onevizion.guitest.helper.AssertHelper;
import com.onevizion.guitest.helper.CheckboxHelper;
import com.onevizion.guitest.helper.ElementHelper;
import com.onevizion.guitest.helper.ElementWaitHelper;
import com.onevizion.guitest.helper.GridHelper;
import com.onevizion.guitest.helper.WaitHelper;
import com.onevizion.guitest.helper.WindowHelper;
import com.onevizion.guitest.vo.entity.ExportRun;

//import au.com.bytecode.opencsv.CSVReader;

@Component
public class ExportHelper {

    //private final static Logger logger = LoggerFactory.getLogger(ExportHelper.class);

    @Resource
    private WindowHelper windowHelper;

    @Resource
    private WaitHelper waitHelper;

    @Resource
    private ElementHelper elementHelper;

    @Resource
    private ElementWaitHelper elementWaitHelper;

    @Resource
    private ExportJsHelper exportJsHelper;

    @Resource
    private ExportWaitHelper exportWaitHelper;

    //@Resource
    //private ExportDbHelper exportDbHelper;

    @Resource
    private GridHelper gridHelper;

    @Resource
    private AssertHelper assertHelper;

    @Resource
    private CheckboxHelper checkboxHelper;

    @Resource
    private SeleniumSettings seleniumSettings;

    public void export(Long gridIndex, ExportRun exportRun, List<Integer> uniqueColumns) {
        runExport(gridIndex, exportRun);
        String processId = waitExportDone();
        if (StringUtils.isNotEmpty(exportRun.getFilePath())) {
            if ("Grid to CSV".equals(exportRun.getMode())) {
                checkExportCsvFile(processId, exportRun.getFilePath(), uniqueColumns);
            } else if ("Grid to Excel".equals(exportRun.getMode())) {
                checkExportExcelFile(processId, exportRun.getFilePath(), uniqueColumns);
            } else {
                throw new SeleniumUnexpectedException("Not support Mode. Mode=" + exportRun.getMode());
            }
        }
        checkExport(gridIndex, exportRun);
        deleteExport(gridIndex, processId);
    }

    public void export(Long gridIndex, ExportRun exportRun) {
        runExport(gridIndex, exportRun);
        String processId = waitExportDone();
        if (StringUtils.isNotEmpty(exportRun.getFilePath())) {
            if ("Grid to CSV".equals(exportRun.getMode())) {
                checkExportCsvFile(processId, exportRun.getFilePath());
            } else if ("Grid to Excel".equals(exportRun.getMode())) {
                checkExportExcelFile(processId, exportRun.getFilePath());
            } else {
                throw new SeleniumUnexpectedException("Not support Mode. Mode=" + exportRun.getMode());
            }
        }
        checkExport(gridIndex, exportRun);
        deleteExport(gridIndex, processId);
    }

    private void runExport(Long gridIndex, ExportRun exportRun) {
        seleniumSettings.getWebDriver().findElement(By.id(AbstractSeleniumCore.BUTTON_EXPORT_ID_BASE + gridIndex)).click(); //TODO move AbstractSelenium.BUTTON_EXPORT_ID_BASE
        windowHelper.openModal(getRunExportElement());
        waitHelper.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        waitHelper.waitFormLoad();
        new Select(seleniumSettings.getWebDriver().findElement(By.name("GridExportMode"))).selectByVisibleText(exportRun.getMode());
        new Select(seleniumSettings.getWebDriver().findElement(By.name("GridExportDelivery"))).selectByVisibleText(exportRun.getDelivery());
        seleniumSettings.getWebDriver().findElement(By.name("GridExportComment")).sendKeys(exportRun.getComments());
        windowHelper.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
    }

    private String waitExportDone() {
        exportWaitHelper.waitExport();

        exportWaitHelper.waitExportDone();

        WebElement panel = seleniumSettings.getWebDriver().findElement(By.id("processEventList"));
        WebElement link = panel.findElement(By.className("ge_link"));
        String processId = link.getAttribute("onclick").replace("showExpDetails(", "").replace(")", "");

        elementHelper.moveToElementById("drop_down_overflow_" + processId);

        elementWaitHelper.waitElementById("ge_delete_" + processId);
        elementWaitHelper.waitElementVisibleById("ge_delete_" + processId);
        elementWaitHelper.waitElementDisplayById("ge_delete_" + processId);

        seleniumSettings.getWebDriver().findElement(By.id("ge_delete_" + processId)).click();

        waitHelper.waitAlert();
        seleniumSettings.getWebDriver().switchTo().alert().accept();

        WebElement processListButton = seleniumSettings.getWebDriver().findElement(By.id("topPanelProcessContainer")).findElement(By.className("btn_input"));
        processListButton.click();

        return processId;
    }

    private void checkExport(Long gridIndex, ExportRun exportRun) {
        seleniumSettings.getWebDriver().findElement(By.id(AbstractSeleniumCore.BUTTON_EXPORT_ID_BASE + gridIndex)).click();
        windowHelper.openModal(getExportHistoryElement());
        waitHelper.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        waitHelper.waitGridLoad(AbstractSeleniumCore.getGridIdx(), AbstractSeleniumCore.getGridIdx());
        Long gridRows = gridHelper.getGridRowsCount(AbstractSeleniumCore.getGridIdx());
        Assert.assertEquals(gridRows, new Long(1L), "Grid have wrong rows count");

        windowHelper.openModal(By.id(AbstractSeleniumCore.BUTTON_EDIT_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        waitHelper.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        waitHelper.waitFormLoad();
        assertHelper.AssertLink("gridPageName", exportRun.getGridPageName() + " ");
        assertHelper.AssertLink("trackorTypeName", exportRun.getTrackorTypeName());
        assertHelper.AssertLink("statusName", exportRun.getStatusName() + " ");
        assertHelper.AssertLink("exportTypeName", exportRun.getMode() + " ");
        assertHelper.AssertLink("exportDeliveryName", exportRun.getDelivery());
        assertHelper.AssertLink("un", seleniumSettings.getTestUser());
        assertHelper.AssertTextById("errorMessage", exportRun.getErrorMessage());
        assertHelper.AssertTextById("comments", exportRun.getComments());
        windowHelper.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));

        windowHelper.closeModal(By.id(AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + AbstractSeleniumCore.getGridIdx()));
    }

    private void deleteExport(Long gridIndex, String processId) {
        seleniumSettings.getWebDriver().findElement(By.id(AbstractSeleniumCore.BUTTON_EXPORT_ID_BASE + gridIndex)).click();
        windowHelper.openModal(getExportHistoryElement());
        waitHelper.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        waitHelper.waitGridLoad(AbstractSeleniumCore.getGridIdx(), AbstractSeleniumCore.getGridIdx());
        Long gridRows = gridHelper.getGridRowsCount(AbstractSeleniumCore.getGridIdx());
        Assert.assertEquals(gridRows, new Long(1L), "Grid have wrong rows count");

        checkboxHelper.clickById("lblcb" + processId);
        elementWaitHelper.waitElementEnabledById(AbstractSeleniumCore.BUTTON_DELETE_ID_BASE + AbstractSeleniumCore.getGridIdx());
        elementHelper.click(seleniumSettings.getWebDriver().findElement(By.id(AbstractSeleniumCore.BUTTON_DELETE_ID_BASE + AbstractSeleniumCore.getGridIdx())));
        waitHelper.waitAlert();
        seleniumSettings.getWebDriver().switchTo().alert().accept();
        waitHelper.waitGridLoad(AbstractSeleniumCore.getGridIdx(), AbstractSeleniumCore.getGridIdx());
        gridRows = gridHelper.getGridRowsCount(AbstractSeleniumCore.getGridIdx());
        Assert.assertEquals(gridRows, new Long(0L), "Grid have wrong rows count");

        windowHelper.closeModal(By.id(AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + AbstractSeleniumCore.getGridIdx()));
    }

    private void checkExportCsvFile(String processId, String fileName) {
        throw new SeleniumUnexpectedException("broken code - need fix");
        /*List<String[]> expectedDataAll;
        try {
            CSVReader expectedCsvReader = new CSVReader(new InputStreamReader((new ClassPathResource("com\\onevizion\\guitest\\export\\" + fileName)).getInputStream()));
            expectedDataAll = expectedCsvReader.readAll();
            expectedCsvReader.close();
        } catch (IOException e) {
            throw new SeleniumUnexpectedException("Can't open CSV file", e);
        }

        List<String[]> actualDataAll;
        try {
            CSVReader actualCsvReader = new CSVReader(new InputStreamReader(exportDbHelper.getExecutedExportFile(Long.valueOf(processId))));
            actualDataAll = actualCsvReader.readAll();
            actualCsvReader.close();
        } catch (IOException e) {
            throw new SeleniumUnexpectedException("Can't open CSV file", e);
        }

        Assert.assertEquals(actualDataAll.size(), expectedDataAll.size(), "Wrong rows count");
        Assert.assertEquals(actualDataAll.size() > 0, true, "Wrong rows count. Rows count should be more than 0");

        for (int row = 0; row < expectedDataAll.size(); row++) {
            Assert.assertEquals(actualDataAll.get(row).length, expectedDataAll.get(row).length, "Wrong cols count in row[" + row + "]");
            Assert.assertEquals(actualDataAll.get(row).length > 0, true, "Wrong cols count in row[" + row + "]. Cols count should be more than 0");
        }

        for (int row = 0; row < expectedDataAll.size(); row++) {
            for (int col = 0; col < expectedDataAll.get(row).length; col++) {
                //logger.info("Loop by expected file. Expected row[" + i + "] col[" + j + "] value[" + expectedDataAll.get(i)[j] + "]. Actual row[" + i + "] col[" + j + "] value[" + actualDataAll.get(i)[j] + "]");
                Assert.assertEquals(actualDataAll.get(row)[col], expectedDataAll.get(row)[col], "Export File wrong on row=[" + row + "] col=[" + col + "]");
            }
        }

        for (int row = 0; row < actualDataAll.size(); row++) {
            for (int col = 0; col < actualDataAll.get(row).length; col++) {
                //logger.info("Loop by actual file. Expected row[" + i + "] col[" + j + "] value[" + expectedDataAll.get(i)[j] + "]. Actual row[" + i + "] col[" + j + "] value[" + actualDataAll.get(i)[j] + "]");
                Assert.assertEquals(actualDataAll.get(row)[col], expectedDataAll.get(row)[col], "Export File wrong on row=[" + row + "] col=[" + col + "]");
            }
        }*/
    }

    private void checkExportCsvFile(String processId, String fileName, List<Integer> uniqueColumns) {
        throw new SeleniumUnexpectedException("broken code - need fix");
        /*List<String[]> expectedDataAll;
        try {
            CSVReader expectedCsvReader = new CSVReader(new InputStreamReader((new ClassPathResource("com\\onevizion\\guitest\\export\\" + fileName)).getInputStream()));
            expectedDataAll = expectedCsvReader.readAll();
            expectedCsvReader.close();
        } catch (IOException e) {
            throw new SeleniumUnexpectedException("Can't open CSV file", e);
        }

        List<String[]> actualDataAll;
        try {
            CSVReader actualCsvReader = new CSVReader(new InputStreamReader(exportDbHelper.getExecutedExportFile(Long.valueOf(processId))));
            actualDataAll = actualCsvReader.readAll();
            actualCsvReader.close();
        } catch (IOException e) {
            throw new SeleniumUnexpectedException("Can't open CSV file", e);
        }

        Assert.assertEquals(actualDataAll.size(), expectedDataAll.size(), "Wrong rows count");
        Assert.assertEquals(actualDataAll.size() > 0, true, "Wrong rows count. Rows count should be more than 0");

        for (int row = 0; row < expectedDataAll.size(); row++) {
            Assert.assertEquals(actualDataAll.get(row).length, expectedDataAll.get(row).length, "Wrong cols count in row[" + row + "]");
            Assert.assertEquals(actualDataAll.get(row).length > 0, true, "Wrong cols count in row[" + row + "]. Cols count should be more than 0");
        }

        List<String[]> expectedKeys = new ArrayList<String[]>();
        for (int row = 0; row < expectedDataAll.size(); row++) {
            String[] num = new String[uniqueColumns.size()];
            for (int i = 0; i < uniqueColumns.size(); i++) {
                num[i] = expectedDataAll.get(row)[uniqueColumns.get(i)];
            }
            expectedKeys.add(num);
        }

        for (int actualRow = 0; actualRow < actualDataAll.size(); actualRow++) {
            String[] num = new String[uniqueColumns.size()];
            for (int i = 0; i < uniqueColumns.size(); i++) {
                num[i] = actualDataAll.get(actualRow)[uniqueColumns.get(i)];
            }

            int row = -1;
            int expectedRow = -1;
            for (String[] expectedKey : expectedKeys) {
                row = row + 1;

                boolean equals = true;
                for (int i = 0; i < expectedKey.length; i++) {
                    if (!expectedKey[i].equals(num[i])) {
                        equals = false;
                    }
                }

                if (equals) {
                    Assert.assertEquals(expectedRow, -1, "Row duplicated");
                    expectedRow = row;
                }
            }

            for (int col = 0; col < actualDataAll.get(actualRow).length; col++) {
                Assert.assertEquals(actualDataAll.get(actualRow)[col], expectedDataAll.get(expectedRow)[col], "Export File wrong on row=[" + actualRow + "] col=[" + col + "]");
            }
        }*/
    }

    private void checkExportExcelFile(String processId, String fileName) {
        throw new SeleniumUnexpectedException("broken code - need fix");
        /*Workbook expectedWorkbook;
        try {
            expectedWorkbook = new Workbook((new ClassPathResource("com\\onevizion\\guitest\\export\\" + fileName)).getInputStream());
        } catch (Exception e) {
            throw new SeleniumUnexpectedException("Can't open Excel file", e);
        }

        Workbook actualWorkbook;
        try {
            actualWorkbook = new Workbook(exportDbHelper.getExecutedExportFile(Long.valueOf(processId)));
        } catch (Exception e) {
            throw new SeleniumUnexpectedException("Can't open Excel file", e);
        }

        Cells expectedCells = expectedWorkbook.getWorksheets().get(0).getCells();
        Cells actualCells = actualWorkbook.getWorksheets().get(0).getCells();

        Assert.assertEquals(actualCells.getRows().getCount(), expectedCells.getRows().getCount(), "Wrong rows count");
        Assert.assertEquals(actualCells.getRows().getCount() > 0, true, "Wrong rows count. Rows count should be more than 0");

        Assert.assertEquals(actualCells.getColumns().getCount(), expectedCells.getColumns().getCount(), "Wrong columns count");
        Assert.assertEquals(actualCells.getColumns().getCount() > 0, true, "Wrong columns count. Columns count should be more than 0");

        for (int row = 0; row < expectedCells.getRows().getCount(); row++) {
            for (int col = 0; col < expectedCells.getColumns().getCount(); col++) {
                //logger.info("Loop by expected file. Expected cell[" + i + "] value[" + expectedCells.get(i).getStringValue() + "]. Actual cell[" + i + "] value[" + actualCells.get(i).getStringValue() + "]");
                Assert.assertEquals(actualCells.get(row, col).getStringValue(), expectedCells.get(row, col).getStringValue(), "Export File wrong on row=[" + row + "] col=[" + col + "]");
            }
        }

        for (int row = 0; row < actualCells.getRows().getCount(); row++) {
            for (int col = 0; col < actualCells.getColumns().getCount(); col++) {
                //logger.info("Loop by actual file. Expected cell[" + i + "] value[" + expectedCells.get(i).getStringValue() + "]. Actual cell[" + i + "] value[" + actualCells.get(i).getStringValue() + "]");
                Assert.assertEquals(actualCells.get(row, col).getStringValue(), expectedCells.get(row, col).getStringValue(), "Export File wrong on row=[" + row + "] col=[" + col + "]");
            }
        }*/
    }

    private void checkExportExcelFile(String processId, String fileName, List<Integer> uniqueColumns) {
        throw new SeleniumUnexpectedException("broken code - need fix");
        /*Workbook expectedWorkbook;
        try {
            expectedWorkbook = new Workbook((new ClassPathResource("com\\onevizion\\guitest\\export\\" + fileName)).getInputStream());
        } catch (Exception e) {
            throw new SeleniumUnexpectedException("Can't open Excel file", e);
        }

        Workbook actualWorkbook;
        try {
            actualWorkbook = new Workbook(exportDbHelper.getExecutedExportFile(Long.valueOf(processId)));
        } catch (Exception e) {
            throw new SeleniumUnexpectedException("Can't open Excel file", e);
        }

        Cells expectedCells = expectedWorkbook.getWorksheets().get(0).getCells();
        Cells actualCells = actualWorkbook.getWorksheets().get(0).getCells();

        Assert.assertEquals(actualCells.getRows().getCount(), expectedCells.getRows().getCount(), "Wrong rows count");
        Assert.assertEquals(actualCells.getRows().getCount() > 0, true, "Wrong rows count. Rows count should be more than 0");

        Assert.assertEquals(actualCells.getColumns().getCount(), expectedCells.getColumns().getCount(), "Wrong columns count");
        Assert.assertEquals(actualCells.getColumns().getCount() > 0, true, "Wrong columns count. Columns count should be more than 0");

        List<String[]> expectedKeys = new ArrayList<String[]>();
        for (int row = 0; row < expectedCells.getRows().getCount(); row++) {
            String[] num = new String[uniqueColumns.size()];
            for (int i = 0; i < uniqueColumns.size(); i++) {
                num[i] = expectedCells.get(row, uniqueColumns.get(i)).getStringValue();
            }
            expectedKeys.add(num);
        }

        for (int actualRow = 0; actualRow < actualCells.getRows().getCount(); actualRow++) {
            String[] num = new String[uniqueColumns.size()];
            for (int i = 0; i < uniqueColumns.size(); i++) {
                num[i] = actualCells.get(actualRow, uniqueColumns.get(i)).getStringValue();
            }

            int row = -1;
            int expectedRow = -1;
            for (String[] expectedKey : expectedKeys) {
                row = row + 1;

                boolean equals = true;
                for (int i = 0; i < expectedKey.length; i++) {
                    if (!expectedKey[i].equals(num[i])) {
                        equals = false;
                    }
                }

                if (equals) {
                    Assert.assertEquals(expectedRow, -1, "Row duplicated");
                    expectedRow = row;
                }
            }

            for (int col = 0; col < actualCells.getColumns().getCount(); col++) {
                Assert.assertEquals(actualCells.get(actualRow, col).getStringValue(), expectedCells.get(expectedRow, col).getStringValue(), "Export File wrong on row=[" + actualRow + "] col=[" + col + "]");
            }
        }*/
    }

    private WebElement getRunExportElement() {
        WebElement runExportElement = null;

        int i = 0;
        do {
            try {
                List<WebElement> elements = seleniumSettings.getWebDriver().findElements(By.className("sub_item_text"));
                for (WebElement element : elements) {
                    if ("Run Export".equals(element.getText())) {
                        runExportElement = element;
                    }
                }
            } catch (StaleElementReferenceException e) {
                
            }
            i++;
        } while (i < 5);

        Assert.assertNotNull(runExportElement, "Run Export button not found");
        return runExportElement;
    }

    private WebElement getExportHistoryElement() {
        WebElement exportHistoryElement = null;

        int i = 0;
        do {
            try {
                List<WebElement> elements = seleniumSettings.getWebDriver().findElements(By.className("sub_item_text"));
                for (WebElement element : elements) {
                    if ("Export History".equals(element.getText())) {
                        exportHistoryElement = element;
                    }
                }
            } catch (StaleElementReferenceException e) {
                
            }
            i++;
        } while (i < 5);

        Assert.assertNotNull(exportHistoryElement, "Export History button not found");
        return exportHistoryElement;
    }

}