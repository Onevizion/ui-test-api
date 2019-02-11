package com.onevizion.uitest.api.helper.dashboard;

import java.util.List;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;
import com.onevizion.uitest.api.helper.ElementJs;
import com.onevizion.uitest.api.vo.DashAxisType;
import com.onevizion.uitest.api.vo.DashColumnCalcMethodType;
import com.onevizion.uitest.api.vo.DashColumnChartType;

@Component
public class Dashboard {

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private DashboardWait dashboardWait;

    @Resource
    private DashboardJs dashboardJs;

    @Resource
    private ElementJs elementJs;

    public String getDashletXAxisLabel(int dashletIdx) {
        return seleniumSettings.getWebDriver().findElements(By.className("lm_stack")).get(dashletIdx).findElement(By.className("highcharts-xaxis")).getAttribute("textContent");
    }

    public String getDashletYAxisLabel(int dashletIdx, int yAxisIdx) {
        return seleniumSettings.getWebDriver().findElements(By.className("lm_stack")).get(dashletIdx).findElements(By.className("highcharts-yaxis")).get(yAxisIdx).getAttribute("textContent");
    }

    public String getDashletLabel(int dashletIdx) {
        return seleniumSettings.getWebDriver().findElements(By.className("lm_stack")).get(dashletIdx).findElement(By.className("lm_title")).getAttribute("textContent");
    }

    public String getDashletLegendItemText(int dashletIdx, int itemIdx) {
        return seleniumSettings.getWebDriver().findElements(By.className("lm_stack")).get(dashletIdx).findElements(By.className("highcharts-legend-item")).get(itemIdx).getAttribute("textContent");
    }

    public void waitDashboardPageLoaded() {
        dashboardWait.waitDashboardPageLoaded();
    }

    public void waitDashboardLoad() {
        dashboardWait.waitDashboardLoad();
    }

    public List<String> getDashlets() {
        return dashboardJs.getDashlets();
    }

    public Long getDashletAxesCount(String dashletId) {
        return dashboardJs.getDashletAxesCount(dashletId);
    }

    public Long getDashletAxisFieldsCount(String dashletId, int asixIdx) {
        return dashboardJs.getDashletAxisFieldsCount(dashletId, asixIdx);
    }

    public Long getDashletAxisFieldChartType(String dashletId, int asixIdx, int fieldIdx) {
        return dashboardJs.getDashletAxisFieldChartType(dashletId, asixIdx, fieldIdx);
    }

    public Long getDashletAxisFieldCalcMethod(String dashletId, int asixIdx, int fieldIdx) {
        return dashboardJs.getDashletAxisFieldCalcMethod(dashletId, asixIdx, fieldIdx);
    }

    public Long getDashletSeriesCount(String dashletId) {
        return dashboardJs.getDashletSeriesCount(dashletId);
    }

    public Long getDashletSerieDataCount(String dashletId, int serieIdx) {
        return dashboardJs.getDashletSerieDataCount(dashletId, serieIdx);
    }

    public String getDashletSerieDataX(String dashletId, int serieIdx) {
        return dashboardJs.getDashletSerieDataX(dashletId, serieIdx);
    }

    public String getDashletSerieDataY(String dashletId, int serieIdx) {
        return dashboardJs.getDashletSerieDataY(dashletId, serieIdx);
    }

    public void changeDashletName(String name) {
        seleniumSettings.getWebDriver().findElement(By.id("dashlet_options_")).click();

        WebElement dialog = getDialog();
        dialog.findElement(By.className("in_input")).clear();
        dialog.findElement(By.className("in_input")).sendKeys(name);
        dialog.findElement(By.id("buttonOk")).click();
    }

    public void changeAxisName(String oldName, String newName) {
        WebElement axis = getAxis(oldName);
        String axisType = axis.findElement(By.className("tet_subtitle")).getText();
        if (DashAxisType.X.getName().equals(axisType)) {
            axis.findElement(By.id("btn_edit")).click();
            axis.findElement(By.className("tet_input")).clear();
            axis.findElement(By.className("tet_input")).sendKeys(newName);
            axis.findElement(By.className("btn_label")).click();
        } else if (DashAxisType.Y.getName().equals(axisType)) {
            axis.findElement(By.className("pc_buttons")).findElement(By.className("btn_input")).click();
            List<WebElement> allMenu = seleniumSettings.getWebDriver().findElements(By.className("context_menu"));
            for (WebElement menu : allMenu) {
                if (menu.isDisplayed()) {
                    List<WebElement> menuButtons = menu.findElements(By.className("ic_container"));
                    for (WebElement menuButton : menuButtons) {
                        if ("Edit".equals(menuButton.getText())) {
                            menuButton.click();
                            axis.findElement(By.className("tet_input")).clear();
                            axis.findElement(By.className("tet_input")).sendKeys(newName);
                            axis.findElement(By.className("btn_label")).click();
                        }
                    }
                }
            }
        } else {
            throw new SeleniumUnexpectedException("Not support DashAxisType [" + axisType + "]");
        }
    }

    public void changeColumnChartType(String axisName, String columnName, DashColumnChartType dashColumnChartType) {
        WebElement axis = getAxis(axisName);
        WebElement column = getColumnFromAxis(axis, columnName);
        elementJs.click(column.findElement(By.className("ia_edit")).findElement(By.className("btn_input")));
        //column.findElement(By.className("ia_edit")).click(); //Element <div class="ia_edit">...</div> is not clickable at point (340, 355). Other element would receive the click: <div class="item_placeholder_bottom"></div>
        //column.findElement(By.className("ia_edit")).findElement(By.className("button")).click();
        //column.findElement(By.className("ia_edit")).findElement(By.className("btn_input")).click();

        WebElement dialog = getDialog();
        dialog.findElements(By.className("sw_buttons")).get(0).findElement(By.id(dashColumnChartType.getIdx().toString())).click();
        dialog.findElement(By.id("buttonOk")).click();
    }

    public void changeColumnCalculationMethod(String axisName, String columnName, DashColumnCalcMethodType dashColumnCalcMethodType) {
        WebElement axis = getAxis(axisName);
        WebElement column = getColumnFromAxis(axis, columnName);
        elementJs.click(column.findElement(By.className("ia_edit")).findElement(By.className("btn_input")));
        //column.findElement(By.className("ia_edit")).click(); //Element <div class="ia_edit">...</div> is not clickable at point (340, 355). Other element would receive the click: <div class="item_placeholder_bottom"></div>
        //column.findElement(By.className("ia_edit")).findElement(By.className("button")).click();
        //column.findElement(By.className("ia_edit")).findElement(By.className("btn_input")).click();

        WebElement dialog = getDialog();
        dialog.findElements(By.className("sw_buttons")).get(1).findElement(By.id(dashColumnCalcMethodType.getIdx().toString())).click();
        dialog.findElement(By.id("buttonOk")).click();
    }

    public void saveDashlet() {
        seleniumSettings.getWebDriver().findElement(By.id("ed_ok_")).click();
        AbstractSeleniumCore.sleep(2000L);
        waitDashboardPageLoaded();
        waitDashboardLoad();
    }

    public void moveColumnToAxisX(String columnName) {
        WebElement source = getColumnFromDatasource(columnName);

        elementJs.dragAndDropPrepare();

        elementJs.dragAndDropDragStart(source);
        AbstractSeleniumCore.sleep(100L);

        //in all elements except source and target
        //dragenter
        //dragover
        //dragleave

        WebElement target = seleniumSettings.getWebDriver().findElement(By.className("placeholder_column"));

        elementJs.dragAndDropDragEnter(target);
        AbstractSeleniumCore.sleep(100L);
        elementJs.dragAndDropDragOver(target);
        AbstractSeleniumCore.sleep(100L);
        elementJs.dragAndDropDrop(target);
        AbstractSeleniumCore.sleep(100L);

        elementJs.dragAndDropDragEnd(source);
        AbstractSeleniumCore.sleep(100L);
    }

    public void createNewAxisY(String columnName) {
        WebElement source = getColumnFromDatasource(columnName);

        elementJs.dragAndDropPrepare();

        elementJs.dragAndDropDragStart(source);
        AbstractSeleniumCore.sleep(100L);

        //in all elements except source and target
        //dragenter
        //dragover
        //dragleave

        WebElement target = seleniumSettings.getWebDriver().findElement(By.className("ed_placeholder"));

        elementJs.dragAndDropDragEnter(target);
        AbstractSeleniumCore.sleep(100L);
        elementJs.dragAndDropDragOver(target);
        AbstractSeleniumCore.sleep(100L);
        elementJs.dragAndDropDrop(target);
        AbstractSeleniumCore.sleep(100L);

        elementJs.dragAndDropDragEnd(source);
        AbstractSeleniumCore.sleep(100L);
    }

    private WebElement getColumnFromDatasource(String columnName) {
        WebElement result = null;

        List<WebElement> columns = seleniumSettings.getWebDriver().findElement(By.className("ed_datasource")).findElements(By.className("item_axis"));
        for (WebElement column : columns) {
            if (columnName.equals(column.getText())) {
                if (result != null) {
                    throw new SeleniumUnexpectedException("Column [" + columnName + "] found many times");
                }
                result = column;
            }
        }

        if (result == null) {
            throw new SeleniumUnexpectedException("Column [" + columnName + "] not found");
        }

        return result;
    }

    private WebElement getColumnFromAxis(WebElement axis, String columnName) {
        WebElement result = null;

        List<WebElement> columns = axis.findElements(By.className("dashlet_field"));
        for (WebElement column : columns) {
            WebElement columnTitle = column.findElement(By.className("ia_title"));
            if (columnName.equals(columnTitle.getText())) {
                if (result != null) {
                    throw new SeleniumUnexpectedException("Column [" + columnName + "] found many times");
                }
                result = column;
            }
        }

        if (result == null) {
            throw new SeleniumUnexpectedException("Column [" + columnName + "] not found");
        }

        return result;
    }

    private WebElement getAxis(String axisName) {
        WebElement result = null;

        List<WebElement> allAxis = seleniumSettings.getWebDriver().findElement(By.className("ed_axes")).findElements(By.className("entity_component"));
        for (WebElement axis : allAxis) {
            WebElement axisTitle = axis.findElement(By.className("tet_title"));
            if (axisName.equals(axisTitle.getText())) {
                if (result != null) {
                    throw new SeleniumUnexpectedException("Axis [" + axisName + "] found many times");
                }
                result = axis;
            }
        }

        if (result == null) {
            throw new SeleniumUnexpectedException("Axis [" + axisName + "] not found");
        }

        return result;
    }

    private WebElement getDialog() {
        return seleniumSettings.getWebDriver().findElement(By.className("md_dialog"));
    }

}