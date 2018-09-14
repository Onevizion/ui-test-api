package com.onevizion.uitest.api.helper.dashboard;

import java.util.List;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.SeleniumSettings;

@Component
public class Dashboard {

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private DashboardWait dashboardWait;

    @Resource
    private DashboardJs dashboardJs;

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

}