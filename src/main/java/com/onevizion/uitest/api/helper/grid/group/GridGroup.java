package com.onevizion.uitest.api.helper.grid.group;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;
import org.testng.Assert;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.helper.Js;
import com.onevizion.uitest.api.vo.ConfigFieldType;

@Component
public class GridGroup {

    public static final String EMPTY_GROUP_NAME = "Undefined";

    private static final String CLASS_GROUP = "group_row";

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private Js js;

    @Resource
    private GridGroupJs gridGroupJs;

    public void checkGridIsGrouped(Long gridIdx) {
        int count = getGroups(gridIdx).size();
        Assert.assertEquals(count > 0, true, "Grid is not grouped");
    }

    public void checkGridIsUngrouped(Long gridIdx) {
        int count = getGroups(gridIdx).size();
        Assert.assertEquals(count == 0, true, "Grid is grouped");
    }

    public void checkGroupsCount(Long gridIdx, int groupsCount) {
        int count = getGroups(gridIdx).size();
        Assert.assertEquals(count, groupsCount, "Wrong groups count");
    }

    public void checkGroupLabel(Long gridIdx, int position, String groupLabel) {
        String actualGroupLabel = getGroups(gridIdx).get(position - 1).getAttribute("innerText").trim();
        Assert.assertEquals(actualGroupLabel, groupLabel, "Wrong group label");
    }

    private List<WebElement> getGroups(Long gridIdx) {
        seleniumSettings.getWebDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        List<WebElement> groups = seleniumSettings.getWebDriver().findElements(By.xpath("//*[@id='" + AbstractSeleniumCore.GRID_ID_BASE + gridIdx + "']//*[@class='" + CLASS_GROUP + "'][label]"));
        seleniumSettings.getWebDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return groups;
    }

    public void checkRowsInGroup(Long gridIdx, ConfigFieldType configFieldType, String group, String columnLabel, List<String> columnVals) {
        String groupId = getGroupId(gridIdx, configFieldType, group);
        Long columnIndex = js.getColumnIndexByLabel(gridIdx, columnLabel);

        List<String> actualColumnVals = gridGroupJs.getColumnValsInGroup(gridIdx, columnIndex, groupId);
        Assert.assertEquals(actualColumnVals, columnVals);
    }

    private String getGroupId(Long gridIdx, ConfigFieldType configFieldType, String group) {
        if (EMPTY_GROUP_NAME.equals(group)) {
            return " ";
        }

        if (ConfigFieldType.CHECKBOX.equals(configFieldType)) {
            if ("Yes".equals(group)) {
                return "1";
            } else {
                return "0";
            }
        } else if (ConfigFieldType.DROP_DOWN.equals(configFieldType)) {
            return gridGroupJs.getGroupId(gridIdx, group);
        } else {
            return group;
        }
    }

}