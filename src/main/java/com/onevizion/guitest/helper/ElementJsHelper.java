package com.onevizion.guitest.helper;

import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

@Component
public class ElementJsHelper extends JsHelper {

    public void moveToElement(WebElement element) {
        execJs3("arguments[0].scrollIntoView();", element);
    }

    public void moveToElementByName(String name) {
        execJs("document.getElementsByName('" + name + "')[0].scrollIntoView();");
    }

    public void moveToElementById(String id) {
        execJs("document.getElementById('" + id + "').scrollIntoView();");
    }

    public void setFocusOnElement(WebElement element) {
        execJs3("arguments[0].focus();", element);
    }

    public void setFocusOnElementByName(String name) {
        execJs("document.getElementsByName('" + name + "')[0].focus();");
    }

    public void setFocusOnElementById(String id) {
        execJs("document.getElementById('" + id + "').focus();");
    }

    public Boolean isElementAnimatedFinish(WebElement element) {
        return Boolean.valueOf(execJs4("return $(arguments[0]).is(':animated') == false;", element));
    }

    public Boolean isElementAnimatedFinishByName(String name) {
        return Boolean.valueOf(execJs("return $(document.getElementsByName('" + name + "')[0]).is(':animated') == false;"));
    }

    public Boolean isElementAnimatedFinishById(String id) {
        return Boolean.valueOf(execJs("return $(document.getElementById('" + id + "')).is(':animated') == false;"));
    }

    public Boolean isElementVelocityAnimatedFinish(WebElement element) {
        return Boolean.valueOf(execJs4("return $(arguments[0]).is('.velocity-animating') == false;", element));
    }

    public Boolean isElementVelocityAnimatedFinishByName(String name) {
        return Boolean.valueOf(execJs("return $(document.getElementsByName('" + name + "')[0]).is('.velocity-animating') == false;"));
    }

    public Boolean isElementVelocityAnimatedFinishById(String id) {
        return Boolean.valueOf(execJs("return $(document.getElementById('" + id + "')).is('.velocity-animating') == false;"));
    }

    public void doubleClick(WebElement element) {
        execJs3("var event = new Event('click', {'bubbles':true, 'cancelable':true, 'composed':true}); arguments[0].dispatchEvent(event);" +
                "var event = new Event('click', {'bubbles':true, 'cancelable':true, 'composed':true}); arguments[0].dispatchEvent(event);" +
                "var event = new Event('dblclick', {'bubbles':true, 'cancelable':true, 'composed':true}); arguments[0].dispatchEvent(event);", element);
    }

    public void doubleClickByName(String name) {
        execJs2("var event = new Event('click', {'bubbles':true, 'cancelable':true, 'composed':true}); document.getElementsByName('" + name + "')[0].dispatchEvent(event);" +
                "var event = new Event('click', {'bubbles':true, 'cancelable':true, 'composed':true}); document.getElementsByName('" + name + "')[0].dispatchEvent(event);" +
                "var event = new Event('dblclick', {'bubbles':true, 'cancelable':true, 'composed':true}); document.getElementsByName('" + name + "')[0].dispatchEvent(event);");
    }

    public void doubleClickById(String id) {
        execJs2("var event = new Event('click', {'bubbles':true, 'cancelable':true, 'composed':true}); document.getElementById('" + id + "').dispatchEvent(event);" +
                "var event = new Event('click', {'bubbles':true, 'cancelable':true, 'composed':true}); document.getElementById('" + id + "').dispatchEvent(event);" +
                "var event = new Event('dblclick', {'bubbles':true, 'cancelable':true, 'composed':true}); document.getElementById('" + id + "').dispatchEvent(event);");
    }

}