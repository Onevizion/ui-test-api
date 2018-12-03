package com.onevizion.uitest.api.helper.grid.sort;

import java.util.List;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;
import org.testng.Assert;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;
import com.onevizion.uitest.api.helper.Element;
import com.onevizion.uitest.api.helper.ElementWait;
import com.onevizion.uitest.api.helper.Js;
import com.onevizion.uitest.api.helper.Wait;
import com.onevizion.uitest.api.vo.SortType;

@Component
public class GridSort {

    private static final String COLUMN_SORT_TYPE_NA = "na";

    @Resource
    private GridSortJs gridSortJs;

    @Resource
    private Js js;

    @Resource
    private Element element;

    @Resource
    private ElementWait elementWait;

    @Resource
    private Wait wait;

    @Resource
    private SeleniumSettings seleniumSettings;

    public void sortColumn(SortType sortType, String columnLabel) {
        sortColumn(AbstractSeleniumCore.getGridIdx(), sortType, columnLabel);
    }

    public void sortColumn(Long gridId, SortType sortType, String columnLabel) {
        checkColumnSortAvailable(gridId, columnLabel);

        Long columnFirstRowIndex = js.getColumnFirstRowIndex(gridId, columnLabel);

        Long columnIndex = js.getColumnIndexByLabel(gridId, columnLabel);
        String columnId = js.getGridColIdByIndex(gridId, columnIndex);

        WebElement elem = seleniumSettings.getWebDriver().findElement(By.className("hdr")).findElement(By.tagName("tbody"))
                .findElements(By.tagName("tr")).get(1).findElements(By.tagName("td")).get(columnFirstRowIndex.intValue()).findElement(By.tagName("div"));

        Long scrollLeft = js.getGridScrollLeft(gridId, columnIndex);

        js.gridScrollLeft(gridId, scrollLeft);
        elementWait.waitElementVisible(elem);
        element.moveToElement(elem);
        elem.click();

        List<WebElement> menus = seleniumSettings.getWebDriver().findElements(By.className("contextSort"));
        for (WebElement menu : menus) {
            if (menu.isDisplayed()) {
                List<WebElement> menuItems = menu.findElements(By.className("ic_container"));
                for (WebElement menuItem : menuItems) {
                    String menuItemText = menuItem.getAttribute("innerText");
                    if (sortType.getName().equals(menuItemText)) {
                        menuItem.click();
                    }
                }
            }
        }

        wait.waitGridLoad(gridId, gridId);

        //List<Object> elements = (List<Object>) js.getGridSort(gridId); //TODO
        //Assert.assertEquals((Long) elements.get(0), columnIndex, "Sorting working is not correct"); //TODO
        //Assert.assertEquals((String) elements.get(1), "asc", "Sorting working is not correct"); //TODO

        String sortColumnId = js.getGridSortColumnIdByGridId(gridId);
        Assert.assertEquals(sortColumnId, columnId);
        String gridSortType = js.getGridSortTypeByGridId(gridId);
        if (SortType.ASC.equals(sortType)) {
            Assert.assertEquals(gridSortType, "0");
        } else if (SortType.DESC.equals(sortType)) {
            Assert.assertEquals(gridSortType, "1");
        } else {
            throw new SeleniumUnexpectedException("Not support SortType. SortType=" + sortType);
        }
    }

    public void sortColumn(SortType sortType, String columnLabel, String columnLabel2) {
        sortColumn(AbstractSeleniumCore.getGridIdx(), sortType, columnLabel, columnLabel2);
    }

    public void sortColumn(Long gridId, SortType sortType, String columnLabel, String columnLabel2) {
        checkColumnSortAvailable(gridId, columnLabel, columnLabel2);

        Long columnSecondRowIndex = js.getColumnSecondRowIndex(gridId, columnLabel, columnLabel2);

        Long columnIndex = js.getColumnIndexByLabel(gridId, columnLabel, columnLabel2);
        String columnId = js.getGridColIdByIndex(gridId, columnIndex);

        WebElement elem = seleniumSettings.getWebDriver().findElement(By.className("hdr")).findElement(By.tagName("tbody"))
                .findElements(By.tagName("tr")).get(2).findElements(By.tagName("td")).get(columnSecondRowIndex.intValue()).findElement(By.tagName("div"));

        Long scrollLeft = js.getGridScrollLeft(gridId, columnIndex);

        js.gridScrollLeft(gridId, scrollLeft);
        elementWait.waitElementVisible(elem);
        element.moveToElement(elem);
        elem.click();

        List<WebElement> menus = seleniumSettings.getWebDriver().findElements(By.className("contextSort"));
        for (WebElement menu : menus) {
            if (menu.isDisplayed()) {
                List<WebElement> menuItems = menu.findElements(By.className("ic_container"));
                for (WebElement menuItem : menuItems) {
                    String menuItemText = menuItem.getAttribute("innerText");
                    if (sortType.getName().equals(menuItemText)) {
                        menuItem.click();
                    }
                }
            }
        }

        wait.waitGridLoad(gridId, gridId);

        //List<Object> elements = (List<Object>) js.getGridSort(gridId); //TODO
        //Assert.assertEquals((Long) elements.get(0), columnIndex, "Sorting working is not correct"); //TODO
        //Assert.assertEquals((String) elements.get(1), "asc", "Sorting working is not correct"); //TODO

        String sortColumnId = js.getGridSortColumnIdByGridId(gridId);
        Assert.assertEquals(sortColumnId, columnId);
        String gridSortType = js.getGridSortTypeByGridId(gridId);
        if (SortType.ASC.equals(sortType)) {
            Assert.assertEquals(gridSortType, "0");
        } else if (SortType.DESC.equals(sortType)) {
            Assert.assertEquals(gridSortType, "1");
        } else {
            throw new SeleniumUnexpectedException("Not support SortType. SortType=" + sortType);
        }
    }

    public void checkColumnSortAvailable(String columnLabel) {
        checkColumnSortAvailable(AbstractSeleniumCore.getGridIdx(), columnLabel);
    }

    public void checkColumnSortAvailable(Long gridId, String columnLabel) {
        Long columnIndex = js.getColumnIndexByLabel(gridId, columnLabel);
        String columnSortType = gridSortJs.getColumnSortType(gridId, columnIndex);
        Assert.assertNotEquals(columnSortType, COLUMN_SORT_TYPE_NA);
    }

    public void checkColumnSortAvailable(String columnLabel, String columnLabel2) {
        checkColumnSortAvailable(AbstractSeleniumCore.getGridIdx(), columnLabel, columnLabel2);
    }

    public void checkColumnSortAvailable(Long gridId, String columnLabel, String columnLabel2) {
        Long columnIndex = js.getColumnIndexByLabel(gridId, columnLabel, columnLabel2);
        String columnSortType = gridSortJs.getColumnSortType(gridId, columnIndex);
        Assert.assertNotEquals(columnSortType, COLUMN_SORT_TYPE_NA);
    }

    public void checkColumnSortNotAvailable(String columnLabel) {
        checkColumnSortNotAvailable(AbstractSeleniumCore.getGridIdx(), columnLabel);
    }

    public void checkColumnSortNotAvailable(Long gridId, String columnLabel) {
        Long columnIndex = js.getColumnIndexByLabel(gridId, columnLabel);
        String columnSortType = gridSortJs.getColumnSortType(gridId, columnIndex);
        Assert.assertEquals(columnSortType, COLUMN_SORT_TYPE_NA);
    }

    public void checkColumnSortNotAvailable(String columnLabel, String columnLabel2) {
        checkColumnSortNotAvailable(AbstractSeleniumCore.getGridIdx(), columnLabel, columnLabel2);
    }

    public void checkColumnSortNotAvailable(Long gridId, String columnLabel, String columnLabel2) {
        Long columnIndex = js.getColumnIndexByLabel(gridId, columnLabel, columnLabel2);
        String columnSortType = gridSortJs.getColumnSortType(gridId, columnIndex);
        Assert.assertEquals(columnSortType, COLUMN_SORT_TYPE_NA);
    }

}