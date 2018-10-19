package com.onevizion.uitest.api.helper.html.input.file;

import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.helper.Js;

@Component
class HtmlInputFileJs extends Js {

    void showOnBplImport(String id) {
        execJs("document.getElementById('" + id + "').style.display = 'inline';");
    }

    void hideOnBplImport(String id) {
        execJs("document.getElementById('" + id + "').style.display = 'none';");
    }

    void showInputForFile(String parentId, String id) {
        execJs("document.getElementById('" + parentId + "').style.overflow = 'visible';"
                + "document.getElementById('" + id + "').style.opacity = 1;"
                + "document.getElementById('" + id + "').style.borderWidth = '0';"
                + "document.getElementById('" + id + "').style.transform = 'none';");
    }

    void showInputForFileTb(String frameId, String inputId) {
        execJs("document.getElementById('" + frameId + "').parentNode.style.visibility = 'visible';"
                + "document.getElementById('" + frameId + "').parentNode.style.zIndex = 1000000;"
                + "document.getElementById('" + frameId + "').parentNode.style.top = '0px';"
                + "document.getElementById('" + frameId + "').parentNode.style.left = '0px';"
                + "document.getElementById('" + frameId + "').contentWindow.document.getElementById('" + inputId + "').style.top = '0px';");
    }

    void hideInputForFileTb(String frameId, String inputId) {
        execJs("document.getElementById('" + frameId + "').parentNode.style.visibility = 'hidden';"
                + "document.getElementById('" + frameId + "').parentNode.style.zIndex = -100000;"
                + "document.getElementById('" + frameId + "').parentNode.style.top = null;"
                + "document.getElementById('" + frameId + "').parentNode.style.left = null;"
                + "document.getElementById('" + frameId + "').contentWindow.document.getElementById('" + inputId + "').style.top = '-100px';");
    }

    Object getFrameForFileTbGrid(Long gridIndex) {
        return execJs2("var elems = document.getElementsByName('ifrmHideFormGrid" + gridIndex + "');"
                + "var elem = null;"
                + "for (var i = 0; i < elems.length; i++) {"
                + "    if (elems[i].contentWindow.document.getElementById('formg" + gridIndex + "') != null &&"
                + "            elems[i].contentWindow.document.getElementById('formg" + gridIndex + "') != undefined) {"
                + "        elem = elems[i];"
                + "    }"
                + "}"
                + "return elem;");
    }

    void showInputForFileTbGrid(Long gridIndex, WebElement element, String inputId) {
        execJs3("arguments[0].parentNode.style.visibility = 'visible';"
                + "arguments[0].parentNode.style.zIndex = 100000;"
                + "arguments[0].parentNode.style.top = '0px';"
                + "arguments[0].parentNode.style.left = '0px';"
                + "arguments[0].parentNode.style.position = 'absolute';"
                + "arguments[0].contentWindow.document.getElementById('formg" + gridIndex + "').style.display = 'inline';"
                + "arguments[0].contentWindow.document.getElementById('" + inputId + "').style.top = '0px';", element);
    }

    void hideInputForFileTbGrid(Long gridIndex, WebElement element, String inputId) {
        execJs3("arguments[0].parentNode.style.visibility = 'hidden';"
                + "arguments[0].parentNode.style.zIndex = -100000;"
                + "arguments[0].parentNode.style.top = null;"
                + "arguments[0].parentNode.style.left = null;"
                + "arguments[0].parentNode.style.position = null;"
                + "arguments[0].contentWindow.document.getElementById('formg" + gridIndex + "').style.display = 'none';"
                + "arguments[0].contentWindow.document.getElementById('" + inputId + "').style.top = '-100px';", element);
    }

}