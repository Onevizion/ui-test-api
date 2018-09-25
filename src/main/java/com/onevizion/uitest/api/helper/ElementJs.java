package com.onevizion.uitest.api.helper;

import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

@Component
public class ElementJs extends Js {

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

    public void dragAndDropPrepare() {
        execJs("getSeleniumDataTransfer();");
    }

    public void dragAndDropDragStart(WebElement element) {
        dragAndDropDragStart(element, 0, 0);
    }

    public void dragAndDropDragStart(WebElement element, int x, int y) {
        execJs3("var event = new DragEvent('dragstart', {'dataTransfer': seleniumDataTransfer, 'bubbles':true, 'cancelable':true, 'composed':true, 'clientX':" + x + ", 'clientY':" + y + "});" + 
                "arguments[0].dispatchEvent(event);", element);
    }

    public void dragAndDropDragEnd(WebElement element) {
        dragAndDropDragEnd(element, 0, 0);
    }

    public void dragAndDropDragEnd(WebElement element, int x, int y) {
        execJs3("var event = new DragEvent('dragend', {'dataTransfer': seleniumDataTransfer, 'bubbles':true, 'cancelable':true, 'composed':true, 'clientX':" + x + ", 'clientY':" + y + "});" + 
                "arguments[0].dispatchEvent(event);", element);
    }

    public void dragAndDropDrop(WebElement element) {
        dragAndDropDrop(element, 0, 0);
    }

    public void dragAndDropDrop(WebElement element, int x, int y) {
        execJs3("var event = new DragEvent('drop', {'dataTransfer': seleniumDataTransfer, 'bubbles':true, 'cancelable':true, 'composed':true, 'clientX':" + x + ", 'clientY':" + y + "});" + 
                "arguments[0].dispatchEvent(event);", element);
    }

    public void dragAndDropDragEnter(WebElement element) {
        dragAndDropDragEnter(element, 0, 0);
    }

    public void dragAndDropDragEnter(WebElement element, int x, int y) {
        execJs3("var event = new DragEvent('dragenter', {'dataTransfer': seleniumDataTransfer, 'bubbles':true, 'cancelable':true, 'composed':true, 'clientX':" + x + ", 'clientY':" + y + "});" + 
                "arguments[0].dispatchEvent(event);", element);
    }

    public void dragAndDropDragOver(WebElement element) {
        dragAndDropDragOver(element, 0, 0);
    }

    public void dragAndDropDragOver(WebElement element, int x, int y) {
        execJs3("var event = new DragEvent('dragover', {'dataTransfer': seleniumDataTransfer, 'bubbles':true, 'cancelable':true, 'composed':true, 'clientX':" + x + ", 'clientY':" + y + "});" + 
                "arguments[0].dispatchEvent(event);", element);
    }

    public void dragAndDropDragLeave(WebElement element) {
        dragAndDropDragLeave(element, 0, 0);
    }

    public void dragAndDropDragLeave(WebElement element, int x, int y) {
        execJs3("var event = new DragEvent('dragleave', {'dataTransfer': seleniumDataTransfer, 'bubbles':true, 'cancelable':true, 'composed':true, 'clientX':" + x + ", 'clientY':" + y + "});" + 
                "arguments[0].dispatchEvent(event);", element);
    }

}