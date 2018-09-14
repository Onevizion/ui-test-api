package com.onevizion.uitest.api.helper;

import java.util.function.Supplier;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;
import com.onevizion.uitest.api.helper.jquery.JqueryWait;
import com.onevizion.uitest.api.helper.view.View;

@Component
public class WaitHelper {

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private JsHelper jsHelper;

    @Resource
    private GridHelper gridHelper;

    @Resource
    private FilterHelper filterHelper;

    @Resource
    private View view;

    @Resource
    private HtmlSelectHelper htmlSelectHelper;

    @Resource //TODO bug in Grid-115098 load views/filters before load grid
    private JqueryWait jqueryWait; //TODO bug in Grid-115098 load views/filters before load grid

    public void waitWebElement(final By elementLocator) {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting for WebElement [" + elementLocator.toString() + "] is failed.")
            .ignoring(NoSuchElementException.class)
            .ignoring(NullPointerException.class)
            .ignoring(WebDriverException.class)
            .until(
                new ExpectedCondition<WebElement>() {
                    public WebElement apply(WebDriver webdriver) {
                        return seleniumSettings.getWebDriver().findElement(elementLocator);
                    }
                });
    }

    public void waitIframeGridLoad(final Long gridId) {
        waitWebElement(By.id(AbstractSeleniumCore.GRID_ID_BASE + gridId));
        waitWebElement(By.id(AbstractSeleniumCore.LOADING_ID_BASE + gridId));
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting for grid with id=[" + gridId + "] is failed")
            .until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver webdriver) {
                    return !seleniumSettings.getWebDriver().findElement(By.id(AbstractSeleniumCore.LOADING_ID_BASE + gridId)).isDisplayed();
                }
            });
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting for grid with id=[" + gridId + "] is failed")
            .until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver webdriver) {
                    return jsHelper.isGridLoaded(gridId).equals("1");
                }
            });
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting for grid with id=[" + gridId + "] is failed")
            .until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver webdriver) {
                    return jsHelper.isGridDataLoaded(gridId);
                }
            });
    }

    public void waitGridLoad(final Long gridId, final Long parentGridId) {
        jqueryWait.waitJQueryLoad(); //TODO bug in Grid-115098 load views/filters before load grid
        waitWebElement(By.id(AbstractSeleniumCore.GRID_ID_BASE + gridId));
        waitWebElement(By.id(AbstractSeleniumCore.LOADING_ID_BASE + gridId));

        Long parentGridIdNewTemp = gridId;
        //get parentGridId from js instead of java parameter
        if (jsHelper.getIsSubGrid(gridId)) {
            parentGridIdNewTemp = jsHelper.getParentGridIdx(gridId);
        }

        final Long parentGridIdNew = parentGridIdNewTemp;

        waitWebElement(By.id(AbstractSeleniumCore.GRID_ID_BASE + parentGridIdNew));
        waitWebElement(By.id(AbstractSeleniumCore.LOADING_ID_BASE + parentGridIdNew));
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting for grid with id=[" + parentGridIdNew + "] is failed")
            .until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver webdriver) {
                    return !seleniumSettings.getWebDriver().findElement(By.id(AbstractSeleniumCore.LOADING_ID_BASE + parentGridIdNew)).isDisplayed();
                }
            });
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting for grid with id=[" + gridId + "] is failed")
            .until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver webdriver) {
                    return jsHelper.isGridLoaded(gridId).equals("1");
                }
            });
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting for grid with id=[" + gridId + "] is failed")
            .until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver webdriver) {
                    return jsHelper.isGridDataLoaded(gridId);
                }
            });
    }

    public void waitGridRowsCount(final Long gridId, final Long rowsCount) {
        Supplier<Long> actualValueSupplier = ()-> {
            return gridHelper.getGridRowsCount(gridId);
        };

        Supplier<String> messageSupplier = ()-> {
            return "Waiting rows count for grid with id=[" + gridId + "] expectedVal=[" + rowsCount + "] actualVal=[" + actualValueSupplier.get() + "] is failed";
        };

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage(messageSupplier)
            .until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver webdriver) {
                    return rowsCount.equals(actualValueSupplier.get());
                }
            });
    }

    public void waitFiltersCount(final Long gridIdx, final int filtersCount) {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting filters count=[" + filtersCount + "] for grid with idx=[" + gridIdx + "] is failed")
            .until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver webdriver) {
                    return filtersCount == filterHelper.getFiltersCount(gridIdx);
                }
            });
    }

    public void waitViewsCount(final Long gridIdx, final int viewsCount) {
        Supplier<Integer> actualValueSupplier = ()-> {
            return view.getViewsCount(gridIdx);
        };

        Supplier<String> messageSupplier = ()-> {
            return "Waiting views count for grid with idx=[" + gridIdx + "] expectedVal=[" + viewsCount + "] actualVal=[" + actualValueSupplier.get().intValue() + "] is failed";
        };

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage(messageSupplier)
            .until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver webdriver) {
                    return viewsCount == actualValueSupplier.get().intValue();
                }
            });
    }

    public void waitNewDropDownCount(final By elemenLocator, final int afterCount) {
        waitWebElement(elemenLocator);

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("DropDown=[" + elemenLocator.toString() + "] have wrong count. Expected count=[" + afterCount + "]")
            .ignoring(StaleElementReferenceException.class)
            .until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver webdriver) {
                    int size = seleniumSettings.getWebDriver().findElement(elemenLocator).findElement(By.className("scrollContent")).findElements(By.className("newDropDownRow")).size();
                    return size == afterCount;
                }
            });
    }

    public void waitDropDownCount(final By elemenLocator, final int afterCount) {
        waitWebElement(elemenLocator);

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
        .withMessage("DropDown=[" + elemenLocator.toString() + "] have wrong count. Expected count=[" + afterCount + "]" + " but Actual count=[" + new Select(seleniumSettings.getWebDriver().findElement(elemenLocator)).getOptions().size() + "]")
        .until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver webdriver) {
                return new Select(seleniumSettings.getWebDriver().findElement(elemenLocator)).getOptions().size() == afterCount;
            }
        });
    }

    public void waitIsWindowClosed() {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting for closing modal window.")
            .until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver webdriver) {
                    return !jsHelper.isWindowClosed();
                }
            });
    }

    public void waitFormLoad() {
        waitWebElement(By.id(AbstractSeleniumCore.LOADING_ID_BASE));
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout()).withMessage("Waiting for form is failed.")
            .ignoring(StaleElementReferenceException.class)
            .until(
                new ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver webdriver) {
                        return !seleniumSettings.getWebDriver().findElement(By.id(AbstractSeleniumCore.LOADING_ID_BASE)).isDisplayed();
                    }
                });
    }

    public void waitAlert() {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout()).withMessage(
                "Timed out after " + seleniumSettings.getDefaultTimeout() + " seconds waiting for alert.").until(
                new ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver webdriver) {
                        return seleniumSettings.getWebDriver().switchTo().alert().getText() != null;
                    }
                });
    }

    public void waitGridCurrentTid(final Long gridIndex, final Long previosTid) {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Loading items in ListBox failed.")
            .ignoring(StaleElementReferenceException.class)
            .until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver webdriver) {
                    return !previosTid.equals(jsHelper.getGridCurTid(gridIndex));
                }
            });
    }

    public void waitBpDocHelpLoad(final String expectedValue, final boolean isPresent) {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout()).withMessage("BpDocHelp loading failed.")
        .until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver webdriver) {
                if (isPresent) {
                    return seleniumSettings.getWebDriver().getPageSource().toUpperCase().contains(expectedValue);
                } else {
                    return !seleniumSettings.getWebDriver().getPageSource().toUpperCase().contains(expectedValue);
                }
            }
        });
    }

    public void waitElemsArrCount(final Long afterCount) {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("ElemsArr have wrong count. Expected count=[" + afterCount + "] but Actual count=[" + jsHelper.getElemsArrLength() + "]")
            .until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver webdriver) {
                    return afterCount.equals(jsHelper.getElemsArrLength());
                }
            });
    }

    public void waitInputLoad(final WebElement webElement) {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout()).withMessage("Loading input failed.")
        .until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver webdriver) {
                return !webElement.getAttribute("value").equals("loading...");
            }
        });
    }

    public void waitListBoxLoadCnt(final Select select, final int cnt) {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout()).withMessage("Loading items in ListBox failed. Expected count=[" + cnt + "] but Actual count=[" + select.getOptions().size() + "]")
        .ignoring(StaleElementReferenceException.class)
        .until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver webdriver) {
                return select.getOptions().size() == cnt;
            }
        });
    }

    /*new void to support new duallist box*/
    public void waitListBoxLoadCnt(final WebElement select, final int cnt) {
        Supplier<String> supplier = ()-> "Loading items in ListBox failed. Expected count=[" + cnt + "] but Actual count=[" + select.findElements(By.tagName("div")).size() + "]";

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
        .withMessage(supplier)
        .ignoring(StaleElementReferenceException.class)
        .until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver webdriver) {
                return select.findElements(By.tagName("div")).size() == cnt;
            }
        });
    }

    public void waitListBoxLoad(final Select select) {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout()).withMessage("Loading items in ListBox failed.")
        .ignoring(StaleElementReferenceException.class)
        .until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver webdriver) {
                return !select.getOptions().get(0).getText().equals("loading...");
            }
        });
    }

    /*new void to support new duallist box*/
    public void waitListBoxLoad(final WebElement select) {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout()).withMessage("Loading items in ListBox failed.")
        .ignoring(StaleElementReferenceException.class)
        .until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver webdriver) {
                return !select.findElements(By.tagName("div")).get(0).getText().equals("<span style=\"color:\">loading...</span>");
            }
        });
    }

    public void waitListBoxLoad2(final Select select) {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout()).withMessage("Loading items in ListBox failed.")
        .ignoring(StaleElementReferenceException.class)
        .until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver webdriver) {
                return select.getOptions().size() > 1;
            }
        });
    }

    public void waitListBoxLoad3(final Select select) {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout()).withMessage("Loading items in ListBox failed.")
        .ignoring(StaleElementReferenceException.class)
        .until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver webdriver) {
                return select.getOptions().size() > 0;
            }
        });
    }

    /*new void to support new duallist box*/
    public void waitListBoxLoad3(final WebElement select) {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout()).withMessage("Loading items in ListBox failed.")
        .ignoring(StaleElementReferenceException.class)
        .until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver webdriver) {
                return select.findElements(By.tagName("div")).size() > 0;
            }
        });
    }

    public void waitListBoxLoad4(final Select select, final String text) {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout()).withMessage("Loading items in ListBox failed.")
        .ignoring(StaleElementReferenceException.class)
        .until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver webdriver) {
                for (WebElement option :select.getOptions()) {
                    if (htmlSelectHelper.getOptionText(option).equals(text)) {
                        return true;
                    }
                }
                return false;
            }
        });
    }

    public void waitReloadForm(final String str) {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout()).withMessage("Waiting for form is failed.")
            .ignoring(StaleElementReferenceException.class)
            .until(
                new ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver webdriver) {
                        return seleniumSettings.getWebDriver().getCurrentUrl().contains(str);
                    }
                });
    }

    public void waitTabLoad(final Long tabIndex) {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting for tab with index=[" + tabIndex + "] is failed").until(
                new ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver webdriver) {
                        return !seleniumSettings.getWebDriver().findElement(By.id("divPage" + tabIndex)).getAttribute("loaded").equals("0");
                    }
                });
    }

    public void waitConfigTabLoad(final Long tabIndex) {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting for tab with index=[" + tabIndex + "] is failed").until(
                new ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver webdriver) {
                        return !seleniumSettings.getWebDriver().findElement(By.id("divPage" + tabIndex)).getAttribute("innerHTML").contains("Loading Tab. Please wait...");
                    }
                });
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting for tab with index=[" + tabIndex + "] is failed").until(
                new ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver webdriver) {
                        return !seleniumSettings.getWebDriver().findElement(By.id("divPage" + tabIndex)).getAttribute("loaded").equals("0");
                    }
                });
    }

    public void waitGridRowEditorLoad() {
        waitWebElement(By.id(AbstractSeleniumCore.LOADING_ID_BASE));
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
                .withMessage("Waiting for Grid Row Editor is failed").until(
                        new ExpectedCondition<Boolean>() {
                            public Boolean apply(WebDriver webdriver) {
                                return !seleniumSettings.getWebDriver().findElement(By.id(AbstractSeleniumCore.LOADING_ID_BASE)).isDisplayed();
                            }
                        });
        waitWebElement(By.id(AbstractSeleniumCore.SAVING_ID_BASE));
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
                .withMessage("Waiting for Grid Row Editor is failed").until(
                        new ExpectedCondition<Boolean>() {
                            public Boolean apply(WebDriver webdriver) {
                                return !seleniumSettings.getWebDriver().findElement(By.id(AbstractSeleniumCore.SAVING_ID_BASE)).isDisplayed();
                            }
                        });
    }

    public void waitLoadingLoad() {
        waitWebElement(By.id(AbstractSeleniumCore.LOADING_ID_BASE));
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
                .withMessage("Waiting loading is failed")
                .ignoring(StaleElementReferenceException.class)
                .until(
                        new ExpectedCondition<Boolean>() {
                            public Boolean apply(WebDriver webdriver) {
                                return !seleniumSettings.getWebDriver().findElement(By.id(AbstractSeleniumCore.LOADING_ID_BASE)).isDisplayed();
                            }
                        });
    }

    public void waitLoadingLoad(final Long gridId) {
        waitWebElement(By.id(AbstractSeleniumCore.LOADING_ID_BASE + gridId));
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
                .withMessage("Waiting loading is failed")
                .ignoring(StaleElementReferenceException.class)
                .until(
                        new ExpectedCondition<Boolean>() {
                            public Boolean apply(WebDriver webdriver) {
                                return !seleniumSettings.getWebDriver().findElement(By.id(AbstractSeleniumCore.LOADING_ID_BASE + gridId)).isDisplayed();
                            }
                        });
    }

    public void waitSavingLoad() {
        waitWebElement(By.id(AbstractSeleniumCore.SAVING_ID_BASE));
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
                .withMessage("Waiting saving is failed")
                .ignoring(StaleElementReferenceException.class)
                .until(
                        new ExpectedCondition<Boolean>() {
                            public Boolean apply(WebDriver webdriver) {
                                return !seleniumSettings.getWebDriver().findElement(By.id(AbstractSeleniumCore.SAVING_ID_BASE)).isDisplayed();
                            }
                        });
    }

    public void waitSavingLoad(final Long gridId) {
        waitWebElement(By.id(AbstractSeleniumCore.SAVING_ID_BASE + gridId));
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
                .withMessage("Waiting saving is failed")
                .ignoring(StaleElementReferenceException.class)
                .until(
                        new ExpectedCondition<Boolean>() {
                            public Boolean apply(WebDriver webdriver) {
                                return !seleniumSettings.getWebDriver().findElement(By.id(AbstractSeleniumCore.SAVING_ID_BASE + gridId)).isDisplayed();
                            }
                        });
    }

    public void waitSplitGridRightLoad(final Long gridId) {
        waitWebElement(By.id(AbstractSeleniumCore.LOADING_SPLIT_GRID_RIGHT_ID_BASE + gridId));
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
                .withMessage("Waiting for split grid right with id=[" + gridId + "] is failed").until(
                        new ExpectedCondition<Boolean>() {
                            public Boolean apply(WebDriver webdriver) {
                                return !seleniumSettings.getWebDriver().findElement(By.id(AbstractSeleniumCore.LOADING_SPLIT_GRID_RIGHT_ID_BASE + gridId)).isDisplayed();
                            }
                        });
    }

    public void waitGridCellValue(final Long gridId, final Long columnIndex, final Long rowIndex, final String val) {
        Supplier<String> actualValueSupplier = ()-> {
            String value = jsHelper.getGridCellValueByRowIndexAndColIndex(gridId, rowIndex, columnIndex);
            if ("&nbsp;".equals(value)) {
                value = "";
            }
            value = value.replaceAll("^<[aA].*?>", "");
            value = value.replaceAll("</[aA]>$", "");
            value = value.replaceAll("<[iI][mM][gG].*?>", "");
            value = value.replaceAll("<[sS][pP][aA][nN].*?>", "");
            value = value.replaceAll("</[sS][pP][aA][nN]>", "");
            value = value.replaceAll("<[sS][vV][gG].*?>.*</[sS][vV][gG]>", "");
            value = value.replaceAll("^<[aA].*?>", "");
            value = value.replaceAll("</[aA]>$", "");
            value = StringUtils.substringBefore(value, "\n");
            return value;
        };

        Supplier<String> messageSupplier = ()-> {
            return "Waiting for Grid Cell gridId=[" + gridId + "] columnIndex=[" + columnIndex + "] rowIndex=[" + rowIndex + "] expectedVal=[" + val + "] actualVal=[" + actualValueSupplier.get() + "] is failed";
        };

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage(messageSupplier)
            .ignoring(SeleniumUnexpectedException.class)
            .until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver webdriver) {
                    return val.equals(actualValueSupplier.get());
                }
            });
    }

    public void waitGridCellTxtValue(final Long gridId, final Long columnIndex, final Long rowIndex, final String val) {
        Supplier<String> actualValueSupplier = ()-> {
            String columnType = jsHelper.getGridColumnType(gridId, columnIndex);
            String value = jsHelper.getGridCellValueTxtByRowIndexAndColIndex(gridId, rowIndex, columnIndex);
            if ("&nbsp;".equals(value)) {
                value = "";
            }
            value = value.replaceAll("^<[aA].*?>", "");
            value = value.replaceAll("</[aA]>$", "");
            value = StringUtils.substringBefore(value, "\n");
            if ("RichMemo".equals(columnType)) {
                value = value.replaceAll(AbstractSeleniumCore.SPECIAL_CHARACTERS_ENCODED_1, AbstractSeleniumCore.SPECIAL_CHARACTERS_1);
                value = value.replaceAll(AbstractSeleniumCore.SPECIAL_CHARACTERS_ENCODED_2, AbstractSeleniumCore.SPECIAL_CHARACTERS_2);
                value = value.replaceAll(AbstractSeleniumCore.SPECIAL_CHARACTERS_ENCODED_3, AbstractSeleniumCore.SPECIAL_CHARACTERS_3);
                value = value.replaceAll(AbstractSeleniumCore.SPECIAL_CHARACTERS_ENCODED_4, AbstractSeleniumCore.SPECIAL_CHARACTERS_4);
            }
            return value;
        };

        Supplier<String> messageSupplier = ()-> {
            return "Waiting for Grid Cell gridId=[" + gridId + "] columnIndex=[" + columnIndex + "] rowIndex=[" + rowIndex + "] expectedVal=[" + val + "] actualVal=[" + actualValueSupplier.get() + "] is failed";
        };

        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage(messageSupplier)
            .ignoring(SeleniumUnexpectedException.class)
            .until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver webdriver) {
                    return val.equals(actualValueSupplier.get());
                }
            });
    }

    public void waitDxtmlxWindowOpened(final String windowName) {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("")
            .until(
                new ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver webdriver) {
                        return jsHelper.isDxtmlxWindowOpened(windowName);
                    }
                 });
    }

    public void waitCodeMirrorLoad(final String elementId) {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting for Code Mirror for element with id=["+elementId+"] is failed")
            .until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver webdriver) {
                    return jsHelper.isCodeMirrorLoaded(elementId);
                }
            });
    }

    public void waitCodeMirrorHistorySize(final String elementId, final Long undo, final Long redo) {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting for Code Mirror for element with id=["+elementId+"] is failed")
            .until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver webdriver) {
                    Long actUndo = jsHelper.getCodeMirrorUndoSize(elementId);
                    Long actRedo = jsHelper.getCodeMirrorRedoSize(elementId);
                    return actUndo == undo && actRedo == redo;
                }
            });
    }

    public void waitFCKEditorValue(final String name, final String value) {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting for FCK Editor name=[" + name + "] value=[" + value + "] is failed")
            .until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver webdriver) {
                    return value.equals(jsHelper.getValueFromFCKEditor(name));
                }
            });
    }

    public void waitDropGridVerificationFinish() {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting for DropGrid Verification is failed")
            .until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver webdriver) {
                    return jsHelper.isDropGridVerificationFinish();
                }
            });
    }

    public void waitBplImportFileSubmitDone() {
        new WebDriverWait(seleniumSettings.getWebDriver(), seleniumSettings.getDefaultTimeout())
            .withMessage("Waiting for DropGrid Verification is failed")
            .until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver webdriver) {
                    return jsHelper.bplImportFileSubmitDone();
                }
            });
    }

}