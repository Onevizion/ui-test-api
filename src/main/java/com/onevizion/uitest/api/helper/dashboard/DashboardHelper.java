package com.onevizion.uitest.api.helper.dashboard;

import java.util.List;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.SeleniumSettings;

@Component
public class DashboardHelper {

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private DashboardWaitHelper dashboardWaitHelper;

    @Resource
    private DashboardJsHelper dashboardJsHelper;

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
        dashboardWaitHelper.waitDashboardPageLoaded();
    }

    public void waitDashboardLoad() {
        dashboardWaitHelper.waitDashboardLoad();
    }

    public List<String> getDashlets() {
        return dashboardJsHelper.getDashlets();
    }

    public Long getDashletAxesCount(String dashletId) {
        return dashboardJsHelper.getDashletAxesCount(dashletId);
    }

    public Long getDashletAxisFieldsCount(String dashletId, int asixIdx) {
        return dashboardJsHelper.getDashletAxisFieldsCount(dashletId, asixIdx);
    }

    public Long getDashletAxisFieldChartType(String dashletId, int asixIdx, int fieldIdx) {
        return dashboardJsHelper.getDashletAxisFieldChartType(dashletId, asixIdx, fieldIdx);
    }

    public Long getDashletAxisFieldCalcMethod(String dashletId, int asixIdx, int fieldIdx) {
        return dashboardJsHelper.getDashletAxisFieldCalcMethod(dashletId, asixIdx, fieldIdx);
    }

    public Long getDashletSeriesCount(String dashletId) {
        return dashboardJsHelper.getDashletSeriesCount(dashletId);
    }

    public Long getDashletSerieDataCount(String dashletId, int serieIdx) {
        return dashboardJsHelper.getDashletSerieDataCount(dashletId, serieIdx);
    }

    public String getDashletSerieDataX(String dashletId, int serieIdx) {
        return dashboardJsHelper.getDashletSerieDataX(dashletId, serieIdx);
    }

    public String getDashletSerieDataY(String dashletId, int serieIdx) {
        return dashboardJsHelper.getDashletSerieDataY(dashletId, serieIdx);
    }

}