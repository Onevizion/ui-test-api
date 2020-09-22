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

    int getDashletAxesCount(String dashletId) {
        return Integer.parseInt(execJs("return dashboard.dashlets[" + dashletId + "].axes.length;"));
    }

    int getDashletAxisFieldsCount(String dashletId, int asixIdx) {
        return Integer.parseInt(execJs("return dashboard.dashlets[" + dashletId + "].axes[" + asixIdx + "].fields.length;"));
    }

    Long getDashletAxisFieldChartType(String dashletId, int asixIdx, int fieldIdx) {
        return Long.parseLong(execJs("return dashboard.dashlets[" + dashletId + "].axes[" + asixIdx + "].fields[" + fieldIdx + "].chartType;"));
    }

    Long getDashletAxisFieldCalcMethod(String dashletId, int asixIdx, int fieldIdx) {
        return Long.parseLong(execJs("return dashboard.dashlets[" + dashletId + "].axes[" + asixIdx + "].fields[" + fieldIdx + "].calcMethodId;"));
    }

    int getDashletSeriesCount(String dashletId) {
        return Integer.parseInt(execJs("return dashboard.dashlets[" + dashletId + "].chart.highChartsConfig.series.length;"));
    }

    String getDashletSerieDataX(String dashletId) {
        return execJs("var elements = dashboard.dashlets[" + dashletId + "].chart.highChartsConfig.series[0].data;"
                + "if (elements.length == 0) {"
                + "    return '';"
                + "} else {"
                + "    if (typeof elements[0].x !== 'undefined' && typeof elements[0].name !== 'undefined') {"
                + "        throw new Error('x and name exists');"
                + "    }"
                + "    if (typeof elements[0].x == 'undefined' && typeof elements[0].name == 'undefined') {"
                + "        throw new Error('x and name not exists');"
                + "    }"
                + "    if (typeof elements[0].x !== 'undefined') {"
                + "        var str = elements[0].x;"
                + "        for (var i = 1; i < elements.length; i++) {"
                + "            str = str + ',' + elements[i].x;"
                + "        }"
                + "        return str;"
                + "    } else {"
                + "        var str = elements[0].name;"
                + "        for (var i = 1; i < elements.length; i++) {"
                + "            str = str + ',' + elements[i].name;"
                + "        }"
                + "        return str;"
                + "    }"
                + "}");
    }

    String getDashletSerieDataY(String dashletId, int serieIdx) {
        return execJs("var elements = dashboard.dashlets[" + dashletId + "].chart.highChartsConfig.series[" + serieIdx + "].data;"
                + "if (elements.length == 0) {"
                + "    return '';"
                + "} else {"
                + "    var str = elements[0].y;"
                + "    for (var i = 1; i < elements.length; i++) {"
                + "        str = str + ',' + elements[i].y;"
                + "    }"
                + "return str;"
                + "}");
    }

}