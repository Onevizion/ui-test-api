package com.onevizion.uitest.api.helper.filter.logic;

import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.helper.AssertElement;
import com.onevizion.uitest.api.helper.Element;

@Component
public class FilterLogic {

    private static final String FILTER_LOGIC_DRAG_AND_DROP = "filterLogicTarget";
    private static final String FILTER_LOGIC_MANUAL = "filterLogicTargetEditInput";
    private static final String FILTER_LOGIC_OPER_AND = "filterLogicOperationAnd";

    @Autowired
    private SeleniumSettings seleniumSettings;

    @Autowired
    private Element element;

    @Autowired
    private AssertElement assertElement;

    public void setFilterLogic(String filterLogicString) {
        element.doubleClickByClass(FILTER_LOGIC_DRAG_AND_DROP);
        seleniumSettings.getWebDriver().findElement(By.id(FILTER_LOGIC_MANUAL)).clear();

        element.clickByClass(FILTER_LOGIC_OPER_AND);

        element.doubleClickByClass(FILTER_LOGIC_DRAG_AND_DROP);
        seleniumSettings.getWebDriver().findElement(By.id(FILTER_LOGIC_MANUAL)).sendKeys(filterLogicString);
    }

    public void checkFilterLogic(String filterLogicString) {
        element.doubleClickByClass(FILTER_LOGIC_DRAG_AND_DROP);
        assertElement.assertTextById(FILTER_LOGIC_MANUAL, filterLogicString);
    }

}