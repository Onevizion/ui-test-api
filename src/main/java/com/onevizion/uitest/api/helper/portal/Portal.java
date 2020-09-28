package com.onevizion.uitest.api.helper.portal;

import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Portal {

    @Autowired
    private PortalWait portalWait;

    @Autowired
    private PortalJs portalJs;

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