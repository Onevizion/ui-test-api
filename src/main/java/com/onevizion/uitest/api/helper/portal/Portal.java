package com.onevizion.uitest.api.helper.portal;
    
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;
import com.onevizion.uitest.api.helper.ElementWait;

@Component
public class Portal {

    @Autowired
    private SeleniumSettings seleniumSettings;

    @Autowired
    private ElementWait elementWait;

    @Autowired
    private PortalWait portalWait;

    @Autowired
    private PortalJs portalJs;

    public void select(String name) {
        WebElement currentPortal = seleniumSettings.getWebDriver().findElement(By.id("dropDownPortals")).findElement(By.className("dds_label"));
        String currentPortalName = currentPortal.getText();
        if (name.equals(currentPortalName)) {
            return;
        }

        seleniumSettings.getWebDriver().findElement(By.id("dropDownPortals")).findElement(By.className("dds_label")).click();
        //elementWait.waitElementById("dd_content_" + id);//TODO
        elementWait.waitElementVisible(seleniumSettings.getWebDriver().findElement(By.id("dropDownPortals")).findElement(By.className("dd_content")));
        elementWait.waitElementDisplay(seleniumSettings.getWebDriver().findElement(By.id("dropDownPortals")).findElement(By.className("dd_content")));

        seleniumSettings.getWebDriver().findElement(By.id("search_dropDownPortals")).clear();
        seleniumSettings.getWebDriver().findElement(By.id("search_dropDownPortals")).sendKeys(name);

        WebElement dropDownItem = null;
        List<WebElement> items = seleniumSettings.getWebDriver().findElement(By.id("dropDownPortals")).findElements(By.className("drop_down_item"));
        for (WebElement item : items) {
            if (name.equals(item.findElement(By.className("ddi_label")).getAttribute("textContent"))) {
                if (dropDownItem != null) {
                    throw new SeleniumUnexpectedException("Portal [" + name + "] found many times");
                }
                dropDownItem = item;
            }
        }
        if (dropDownItem == null) {
            throw new SeleniumUnexpectedException("Portal [" + name + "] not found");
        }
        dropDownItem.click();
    }

    public void waitPortal(String name, int framesCount) {
        portalWait.waitPortal(name);
        portalWait.waitFramesCount(framesCount);
        for (int i = 0; i < framesCount; i++) {
            String frameType = portalJs.getFrameType(i);
            if (!"4".equals(frameType)) {
                portalWait.waitFrame(i);
            }
        }
    }

    public WebElement getIframe(String frameName) {
        return portalJs.getIframe(frameName);
    }

}