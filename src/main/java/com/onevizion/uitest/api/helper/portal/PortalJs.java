package com.onevizion.uitest.api.helper.portal;

import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.helper.Js;

@Component
class PortalJs extends Js {

    Boolean isPortalExists(String name) {
        return Boolean.valueOf(execJs("return (typeof portalMap !== 'undefined'"
                + "&& typeof portalMap[0] !== 'undefined'"
                + "&& portalMap[0].portal !== 'undefined'"
                + "&& portalMap[0].name !== 'undefined'"
                + "&& portalMap[0].name == '" + name + "'"
                + "&& portalMap[0].frames !== 'undefined');"));
    }

    WebElement getIframe(String frameName) {
        return (WebElement) execJs2("return portalMap[0].portal.cells('" + frameName.toLowerCase() + "').getFrame();");
    }

    String getFrameType(int frameNumber) {
        return execJs("return portalMap[0].frames[" + frameNumber + "].type;");
    }

}