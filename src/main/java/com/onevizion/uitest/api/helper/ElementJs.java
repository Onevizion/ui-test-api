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

    public void dragAndDropDragStartTop(WebElement source) {
        execJs3("var x = arguments[0].getBoundingClientRect().left + 1;" + 
                "var y = arguments[0].getBoundingClientRect().top + 1;" + 
                "var event = new DragEvent('dragstart', {'dataTransfer': seleniumDataTransfer, 'bubbles':true, 'cancelable':true, 'composed':true, 'clientX':x, 'clientY':y});" + 
                "arguments[0].dispatchEvent(event);", source);
    }

    public void dragAndDropDragStartBottom(WebElement source) {
        execJs3("var x = arguments[0].getBoundingClientRect().left + 1;" + 
                "var y = arguments[0].getBoundingClientRect().bottom - 1;" + 
                "var event = new DragEvent('dragstart', {'dataTransfer': seleniumDataTransfer, 'bubbles':true, 'cancelable':true, 'composed':true, 'clientX':x, 'clientY':y});" + 
                "arguments[0].dispatchEvent(event);", source);
    }

    public void dragAndDropDragEnterTop(WebElement target) {
        execJs3("var x = arguments[0].getBoundingClientRect().left + 1;" + 
                "var y = arguments[0].getBoundingClientRect().top + 1;" + 
                "var event = new DragEvent('dragenter', {'dataTransfer': seleniumDataTransfer, 'bubbles':true, 'cancelable':true, 'composed':true, 'clientX':x, 'clientY':y});" + 
                "arguments[0].dispatchEvent(event);", target);
    }

    public void dragAndDropDragEnterBottom(WebElement target) {
        execJs3("var x = arguments[0].getBoundingClientRect().left + 1;" + 
                "var y = arguments[0].getBoundingClientRect().bottom - 1;" + 
                "var event = new DragEvent('dragenter', {'dataTransfer': seleniumDataTransfer, 'bubbles':true, 'cancelable':true, 'composed':true, 'clientX':x, 'clientY':y});" + 
                "arguments[0].dispatchEvent(event);", target);
    }

    public void dragAndDropDragOverTop(WebElement target) {
        execJs3("var x = arguments[0].getBoundingClientRect().left + 1;" + 
                "var y = arguments[0].getBoundingClientRect().top + 1;" + 
                "var event = new DragEvent('dragover', {'dataTransfer': seleniumDataTransfer, 'bubbles':true, 'cancelable':true, 'composed':true, 'clientX':x, 'clientY':y});" + 
                "arguments[0].dispatchEvent(event);", target);
    }

    public void dragAndDropDragOverBottom(WebElement target) {
        execJs3("var x = arguments[0].getBoundingClientRect().left + 1;" + 
                "var y = arguments[0].getBoundingClientRect().bottom - 1;" + 
                "var event = new DragEvent('dragover', {'dataTransfer': seleniumDataTransfer, 'bubbles':true, 'cancelable':true, 'composed':true, 'clientX':x, 'clientY':y});" + 
                "arguments[0].dispatchEvent(event);", target);
    }

    public void dragAndDropDragLeave(WebElement target) {
        dragAndDropDragLeave(target, 0, 0);
    }

    private void dragAndDropDragLeave(WebElement target, int x, int y) {
        execJs3("var x = " + x + ";" + 
                "var y = " + y + ";" + 
                "var event = new DragEvent('dragleave', {'dataTransfer': seleniumDataTransfer, 'bubbles':true, 'cancelable':true, 'composed':true, 'clientX':x, 'clientY':y});" + 
                "arguments[0].dispatchEvent(event);", target);
    }

    public String dragAndDropDropTop(WebElement target) {
        return execJs4(
                "var x = arguments[0].getBoundingClientRect().left + 1;" + 
                "var y = arguments[0].getBoundingClientRect().top + 1;" + 
                "var event = new DragEvent('drop', {'dataTransfer': seleniumDataTransfer, 'bubbles':true, 'cancelable':true, 'composed':true, 'clientX':x, 'clientY':y});" + 
                "arguments[0].dispatchEvent(event);" + 
                "return x + ' ' + y;", target);
    }

    public String dragAndDropDropBottom(WebElement target) {
        return execJs4(
                "var x = arguments[0].getBoundingClientRect().left + 1;" + 
                "var y = arguments[0].getBoundingClientRect().bottom - 1;" + 
                "var event = new DragEvent('drop', {'dataTransfer': seleniumDataTransfer, 'bubbles':true, 'cancelable':true, 'composed':true, 'clientX':x, 'clientY':y});" + 
                "arguments[0].dispatchEvent(event);" + 
                "return x + ' ' + y;", target);
    }

    public void dragAndDropDragEndTop(WebElement source, String coord) {
        execJs3("var x = " + coord.split(" ")[0] + ";" + 
                "var y = " + coord.split(" ")[1] + ";" + 
                "var event = new DragEvent('dragend', {'dataTransfer': seleniumDataTransfer, 'bubbles':true, 'cancelable':true, 'composed':true, 'clientX':x, 'clientY':y});" + 
                "arguments[0].dispatchEvent(event);", source);
    }

    public void dragAndDropDragEndBottom(WebElement source, String coord) {
        execJs3("var x = " + coord.split(" ")[0] + ";" + 
                "var y = " + coord.split(" ")[1] + ";" + 
                "var event = new DragEvent('dragend', {'dataTransfer': seleniumDataTransfer, 'bubbles':true, 'cancelable':true, 'composed':true, 'clientX':x, 'clientY':y});" + 
                "arguments[0].dispatchEvent(event);", source);
    }

}