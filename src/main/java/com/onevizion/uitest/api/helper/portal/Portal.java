package com.onevizion.uitest.api.helper.portal;

import javax.annotation.Resource;

import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

@Component
public class Portal {

    @Resource
    private PortalWait portalWait;

    @Resource
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