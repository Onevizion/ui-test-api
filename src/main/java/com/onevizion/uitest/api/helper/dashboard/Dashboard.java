package com.onevizion.uitest.api.helper.dashboard;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;
import org.testng.Assert;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;
import com.onevizion.uitest.api.helper.ElementJs;
import com.onevizion.uitest.api.helper.Js;
import com.onevizion.uitest.api.vo.DashAxisType;
import com.onevizion.uitest.api.vo.DashColumn;
import com.onevizion.uitest.api.vo.DashColumnCalcMethodType;
import com.onevizion.uitest.api.vo.DashColumnChartType;
import com.onevizion.uitest.api.vo.DashColumnType;
import com.onevizion.uitest.api.vo.DashDisplayModeType;

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

    @Resource
    private Js js;

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

    public String getDashletSerieDataXText(String dashletId, int serieIdx) {
        return dashboardJs.getDashletSerieDataXText(dashletId, serieIdx);
    }

    public String getDashletSerieDataY(String dashletId, int serieIdx) {
        return dashboardJs.getDashletSerieDataY(dashletId, serieIdx);
    }

    public void addGroupInAxis(String axisName, String columnName) {
        WebElement axis = getAxis(axisName);
        String axisType = axis.findElement(By.className("tet_subtitle")).getText();
        if (DashAxisType.X.getName().equals(axisType)) {
            throw new SeleniumUnexpectedException("Not support Add Group for X-Axis");
        } else if (DashAxisType.Y.getName().equals(axisType)) {
            axis.findElement(By.className("pc_buttons")).findElement(By.className("btn_input")).click();
            List<WebElement> allMenu = seleniumSettings.getWebDriver().findElements(By.className("context_menu"));
            for (WebElement menu : allMenu) {
                if (menu.isDisplayed()) {
                    List<WebElement> menuButtons = menu.findElements(By.className("ic_container"));
                    for (WebElement menuButton : menuButtons) {
                        if ("Add Group".equals(menuButton.getText())) {
                            menuButton.click();
                            selectGroupingFieldByVisibleText(axisName, columnName);
                        }
                    }
                }
            }
        } else {
            throw new SeleniumUnexpectedException("Not support DashAxisType [" + axisType + "]");
        }
    }

    private void selectGroupingFieldByVisibleText(String axisName, String groupingFieldName) {
        WebElement axis = getAxis(axisName);
        List<WebElement> allGroup = axis.findElement(By.className("pc_groups")).findElements(By.className("entity_component"));
        WebElement field = null;
        for(WebElement group : allGroup) {
            if(!group.findElement(By.className("drop_list")).getAttribute("class").contains(" closed")) {
                List<WebElement> allItem = group.findElements(By.className("item_select"));
                for(WebElement item : allItem) {
                    if(item.getText().equals(groupingFieldName)) {
                        field = item;
                        break;
                    }
                }

                if(field == null) {
                    throw new SeleniumUnexpectedException("Grouping Field [" + groupingFieldName + "] for Y-Axis [" + axisName + "] not found");
                }

                field.click();
                group.findElement(By.className("tes_editor_save")).click();
                break;
            }
        }
    }

    public void openEditDashletForm(String dashletName) {
        WebElement dashlet = getDashletInViewMode(dashletName);
        dashlet.findElement(By.className("lm_settings")).findElements(By.className("btn_input")).get(1).click();

        WebElement editButton = null;
        List<WebElement> allMenu = seleniumSettings.getWebDriver().findElements(By.className("context_menu"));
        for (WebElement menu : allMenu) {
            if (menu.isDisplayed()) {
                List<WebElement> menuButtons = menu.findElements(By.className("ic_container"));
                for (WebElement menuButton : menuButtons) {
                    if ("Edit".equals(menuButton.getText())) {
                        if (editButton != null) {
                            throw new SeleniumUnexpectedException("Edit button for dashlet [" + dashletName + "] found many times");
                        }
                        editButton = menuButton;
                    }
                }
            }
        }

        if (editButton == null) {
            throw new SeleniumUnexpectedException("Edit button for dashlet [" + dashletName + "] not found");
        }

        editButton.click();
    }

    public void changeDashletName(String name) {
        seleniumSettings.getWebDriver().findElement(By.id("dashlet_options_")).click();

        WebElement dialog = getDialog();
        dialog.findElement(By.className("in_input")).clear();
        dialog.findElement(By.className("in_input")).sendKeys(name);
        dialog.findElement(By.id("buttonOk")).click();
    }

    public void changeDashletDisplayMode(DashDisplayModeType dashDisplayModeType) {
        seleniumSettings.getWebDriver().findElement(By.id("dashlet_options_")).click();

        WebElement dialog = getDialog(); 
        dialog.findElement(By.id(dashDisplayModeType.getIdx().toString())).click(); 
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

    public void changeColumnChartType(String axisName, String groupName, String columnName, DashColumnChartType dashColumnChartType) {
        WebElement axis = getAxis(axisName);
        WebElement group = getGroupFromAxis(axis, groupName);
        WebElement column = getColumnFromGroup(group, columnName);
        elementJs.click(column.findElement(By.className("ia_edit")).findElement(By.className("btn_input")));

        WebElement dialog = getDialog();
        dialog.findElements(By.className("sw_buttons")).get(0).findElement(By.id(dashColumnChartType.getIdx().toString())).click();
        dialog.findElement(By.id("buttonOk")).click();
    }

    public void changeColumnCalculationMethod(String axisName, String groupName, String columnName, DashColumnCalcMethodType dashColumnCalcMethodType) {
        WebElement axis = getAxis(axisName);
        WebElement group = getGroupFromAxis(axis, groupName);
        WebElement column = getColumnFromGroup(group, columnName);
        elementJs.click(column.findElement(By.className("ia_edit")).findElement(By.className("btn_input")));

        WebElement dialog = getDialog();
        dialog.findElements(By.className("sw_buttons")).get(1).findElement(By.id(dashColumnCalcMethodType.getIdx().toString())).click();
        dialog.findElement(By.id("buttonOk")).click();
    }

    public void removeColumnFromAxis(String axisName, String columnName){
        WebElement axis = getAxis(axisName);
        WebElement column = getColumnFromAxis(axis, columnName);
        elementJs.click(column.findElement(By.className("ia_title_delete")).findElement(By.className("btn_input")));
    }

    public void saveDashlet() {
        seleniumSettings.getWebDriver().findElement(By.id("ed_ok_")).click();
        AbstractSeleniumCore.sleep(2000L);
        waitDashboardPageLoaded();
        waitDashboardLoad();
    }

    public void closeDashlet() {
        seleniumSettings.getWebDriver().findElement(By.id("ed_cancel_")).click();
        waitDashboardPageLoaded();
        waitDashboardLoad();
    }

    public void moveColumnToAxisX(String columnName) {
        WebElement source = getColumnFromDatasource(columnName);
        WebElement target = seleniumSettings.getWebDriver().findElement(By.className("placeholder_column"));

        elementJs.dragAndDropPrepare();
        elementJs.dragAndDropDragStartTop(source);

        //in all elements except source and target
        //dragenter
        //dragover
        //dragleave

        elementJs.dragAndDropDragEnterTop(target);
        elementJs.dragAndDropDragOverTop(target);
        elementJs.dragAndDropDragOverBottom(target);
        String coord = elementJs.dragAndDropDropBottom(target);
        elementJs.dragAndDropDragEndBottom(source, coord);
    }

    public void createNewAxisY(String columnName) {
        WebElement source = getColumnFromDatasource(columnName);
        WebElement target = seleniumSettings.getWebDriver().findElement(By.className("ed_placeholder"));

        elementJs.dragAndDropPrepare();
        elementJs.dragAndDropDragStartTop(source);

        //in all elements except source and target
        //dragenter
        //dragover
        //dragleave

        elementJs.dragAndDropDragEnterTop(target);
        elementJs.dragAndDropDragOverTop(target);
        elementJs.dragAndDropDragOverBottom(target);
        String coord = elementJs.dragAndDropDropBottom(target);
        elementJs.dragAndDropDragEndBottom(source, coord);
    }

    public void moveColumnToAxisY(String axisName, String columnName) {
        WebElement source = getColumnFromDatasource(columnName);

        elementJs.dragAndDropPrepare();
        elementJs.dragAndDropDragStartTop(source);

        List<WebElement> items = seleniumSettings.getWebDriver().findElements(By.className("droppable_item"));
        for (WebElement item : items) {
            WebElement list = js.getParentElement(js.getParentElement(item));
            elementJs.dragAndDropDragEnterTop(list);
            elementJs.dragAndDropDragEnterTop(item);
            elementJs.dragAndDropDragOverTop(item);
            elementJs.dragAndDropDragOverBottom(item);
            elementJs.dragAndDropDragLeave(item);
            elementJs.dragAndDropDragLeave(list);
        }

        WebElement axis = getAxis(axisName);
        List<WebElement> columns = axis.findElements(By.className("droppable_item"));
        WebElement target = columns.get(columns.size() - 1);

        elementJs.dragAndDropDragEnterTop(target);
        elementJs.dragAndDropDragOverTop(target);
        elementJs.dragAndDropDragOverBottom(target);
        String coord = elementJs.dragAndDropDropBottom(target);
        elementJs.dragAndDropDragEndBottom(source, coord);
    }

    public void moveColumnToGroup(String axisName, String groupName, String columnName) {
        WebElement source = getColumnFromDatasource(columnName);

        elementJs.dragAndDropPrepare();
        elementJs.dragAndDropDragStartTop(source);

        List<WebElement> items = seleniumSettings.getWebDriver().findElements(By.className("droppable_item"));
        for (WebElement item : items) {
            WebElement list = js.getParentElement(js.getParentElement(item));
            elementJs.dragAndDropDragEnterTop(list);
            elementJs.dragAndDropDragEnterTop(item);
            elementJs.dragAndDropDragOverTop(item);
            elementJs.dragAndDropDragOverBottom(item);
            elementJs.dragAndDropDragLeave(item);
            elementJs.dragAndDropDragLeave(list);
        }

        WebElement target = null;

        WebElement axis = getAxis(axisName);
        WebElement group = getGroupFromAxis(axis, groupName);

        seleniumSettings.getWebDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        int count = group.findElements(By.className("placeholder_column")).size();
        seleniumSettings.getWebDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        if (count > 0) {
            target = group.findElement(By.className("placeholder_column"));
        } else {
            List<WebElement> columns = axis.findElements(By.className("droppable_item"));
            target = columns.get(columns.size() - 1);
        }

        elementJs.dragAndDropDragEnterTop(target);
        elementJs.dragAndDropDragOverTop(target);
        elementJs.dragAndDropDragOverBottom(target);
        String coord = elementJs.dragAndDropDropBottom(target);
        elementJs.dragAndDropDragEndBottom(source, coord);
    }

    public WebElement getDashletInViewMode(String dashletName) {
        WebElement result = null;

        List<WebElement> dashlets = seleniumSettings.getWebDriver().findElements(By.className("lm_stack"));
        for (WebElement dashlet : dashlets) {
            WebElement dashletTitle = dashlet.findElement(By.className("lm_title"));
            if (dashletName.equals(dashletTitle.getText())) {
                if (result != null) {
                    throw new SeleniumUnexpectedException("Dashlet [" + dashletName + "] found many times");
                }
                result = dashlet;
            }
        }

        if (result == null) {
            throw new SeleniumUnexpectedException("Dashlet [" + dashletName + "] not found");
        }

        return result;
    }

    public WebElement getDashletInEditMode() {
        return seleniumSettings.getWebDriver().findElement(By.className("ed_content"));
    }

    public List<DashColumn> getDatasourceColumns() {
        List<DashColumn> columns = new ArrayList<DashColumn>();

        List<WebElement> textColumns = seleniumSettings.getWebDriver().findElement(By.className("ed_datasource")).findElements(By.className("item_type_string"));
        for (WebElement textColumn : textColumns) {
            columns.add(new DashColumn(textColumn.getText(), DashColumnType.TEXT));
        }
        List<WebElement> dateColumns = seleniumSettings.getWebDriver().findElement(By.className("ed_datasource")).findElements(By.className("item_type_date"));
        for (WebElement dateColumn : dateColumns) {
            columns.add(new DashColumn(dateColumn.getText(), DashColumnType.DATE));
        }
        List<WebElement> numberColumns = seleniumSettings.getWebDriver().findElement(By.className("ed_datasource")).findElements(By.className("item_type_number"));
        for (WebElement numberColumn : numberColumns) {
            columns.add(new DashColumn(numberColumn.getText(), DashColumnType.NUMBER));
        }

        return columns;
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

    private WebElement getColumnFromGroup(WebElement group, String columnName) {
        WebElement result = null;

        List<WebElement> columns = group.findElements(By.className("dashlet_field"));
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

    private WebElement getGroupFromAxis(WebElement axis, String groupName) {
        WebElement result = null;

        List<WebElement> groups = axis.findElement(By.className("pc_groups")).findElements(By.className("entity_component"));
        for (WebElement group : groups) {
            WebElement groupTitle = group.findElement(By.className("tes_title"));
            if (groupName.equals(groupTitle.getText())) {
                if (result != null) {
                    throw new SeleniumUnexpectedException("Group [" + groupName + "] found many times");
                }
                result = group;
            }
        }

        if (result == null) {
            throw new SeleniumUnexpectedException("Group [" + groupName + "] not found");
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
            seleniumSettings.getWebDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
            int count = axis.findElements(By.className("tet_title")).size();
            seleniumSettings.getWebDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            if (count > 0) {
                WebElement axisTitle = axis.findElement(By.className("tet_title"));
                if (axisName.equals(axisTitle.getText())) {
                    if (result != null) {
                        throw new SeleniumUnexpectedException("Axis [" + axisName + "] found many times");
                    }
                    result = axis;
                }
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

    public void checkDashletViewDisplayMode(String dashletName, DashDisplayModeType dashDisplayModeType) {
        WebElement dashlet = getDashletInViewMode(dashletName);
        checkDashletDisplayMode(dashlet, dashDisplayModeType);
    }

    public void checkDashletEditDisplayMode(DashDisplayModeType dashDisplayModeType) {
        WebElement dashlet = getDashletInEditMode();
        checkDashletDisplayMode(dashlet, dashDisplayModeType);
    }

    private void checkDashletDisplayMode(WebElement dashlet, DashDisplayModeType dashDisplayModeType) {
        seleniumSettings.getWebDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        int chartCount = dashlet.findElements(By.className("lm_chart")).size();
        int tableCount = dashlet.findElements(By.className("table")).size();
        seleniumSettings.getWebDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        if (DashDisplayModeType.CHART.equals(dashDisplayModeType)) {
            Assert.assertEquals(chartCount, 1);
            Assert.assertEquals(tableCount, 0);
        } else if (DashDisplayModeType.TABLE.equals(dashDisplayModeType)) {
            Assert.assertEquals(chartCount, 0);
            Assert.assertEquals(tableCount, 1);
        } else {
            throw new SeleniumUnexpectedException("Not support DashDisplayModeType. DashDisplayModeType=" + dashDisplayModeType);
        }
    }

}