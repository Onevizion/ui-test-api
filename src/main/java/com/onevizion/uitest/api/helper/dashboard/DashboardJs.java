package com.onevizion.uitest.api.helper.dashboard;

import java.util.List;

import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.helper.Js;

@Component
class DashboardJs extends Js {

    @SuppressWarnings("unchecked")
    List<String> getDashlets() {
        return (List<String>) execJs2("return Object.keys(dashboard.dashlets);");
    }

    Long getDashletAxesCount(String dashletId) {
        return Long.parseLong(execJs("return dashboard.dashlets[" + dashletId + "].axes.length;"));
    }

    Long getDashletAxisFieldsCount(String dashletId, int asixIdx) {
        return Long.parseLong(execJs("return dashboard.dashlets[" + dashletId + "].axes[" + asixIdx + "].fields.length;"));
    }

    Long getDashletAxisFieldChartType(String dashletId, int asixIdx, int fieldIdx) {
        return Long.parseLong(execJs("return dashboard.dashlets[" + dashletId + "].axes[" + asixIdx + "].fields[" + fieldIdx + "].chartType;"));
    }

    Long getDashletAxisFieldCalcMethod(String dashletId, int asixIdx, int fieldIdx) {
        return Long.parseLong(execJs("return dashboard.dashlets[" + dashletId + "].axes[" + asixIdx + "].fields[" + fieldIdx + "].calcMethodId;"));
    }

    Long getDashletSeriesCount(String dashletId) {
        return Long.parseLong(execJs("return dashboard.dashlets[" + dashletId + "].chart.highChartsConfig.series.length;"));
    }

    Long getDashletSerieDataCount(String dashletId, int serieIdx) {
        return Long.parseLong(execJs("return dashboard.dashlets[" + dashletId + "].chart.highChartsConfig.series[" + serieIdx + "].data.length;"));
    }

    String getDashletSerieDataX(String dashletId, int serieIdx) {
        return execJs("var elements = dashboard.dashlets[" + dashletId + "].chart.highChartsConfig.series[" + serieIdx + "].data;"
                + "var str = elements[0].x;"
                + "for (var i = 1; i < elements.length; i++) {"
                + "    str += ',' + elements[i].x;"
                + "}"
                + "return str;");
    }

    String getDashletSerieDataY(String dashletId, int serieIdx) {
        return execJs("var elements = dashboard.dashlets[" + dashletId + "].chart.highChartsConfig.series[" + serieIdx + "].data;"
                + "var str = elements[0].y;"
                + "for (var i = 1; i < elements.length; i++) {"
                + "    str += ',' + elements[i].y;"
                + "}"
                + "return str;");
    }

}