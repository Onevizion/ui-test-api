package com.onevizion.uitest.api.helper.entity;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;
import com.onevizion.uitest.api.helper.AssertHelper;
import com.onevizion.uitest.api.helper.CheckboxHelper;
import com.onevizion.uitest.api.helper.ElementHelper;
import com.onevizion.uitest.api.helper.ElementWaitHelper;
import com.onevizion.uitest.api.helper.GridHelper;
import com.onevizion.uitest.api.helper.JsHelper;
import com.onevizion.uitest.api.helper.PsSelectorHelper;
import com.onevizion.uitest.api.helper.TabHelper;
import com.onevizion.uitest.api.helper.WaitHelper;
import com.onevizion.uitest.api.helper.WindowHelper;
import com.onevizion.uitest.api.helper.configfield.ConfigFieldHelper;
import com.onevizion.uitest.api.helper.jquery.JqueryWait;
import com.onevizion.uitest.api.vo.ConfigFieldType;
import com.onevizion.uitest.api.vo.entity.ConfigField;

@Component
public class EntityConfigFieldHelper {

    @Resource
    private WindowHelper windowHelper;

    @Resource
    private WaitHelper waitHelper;

    @Resource
    private AssertHelper assertHelper;

    @Resource
    private JsHelper jsHelper;

    @Resource
    private GridHelper gridHelper;

    @Resource
    private JqueryWait jqueryWait;

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private ElementWaitHelper elementWaitHelper;

    @Resource
    private ConfigFieldHelper configFieldHelper;

    @Resource
    private CheckboxHelper checkboxHelper;

    @Resource
    private PsSelectorHelper psSelectorHelper;

    @Resource
    private TabHelper tabHelper;

    @Resource
    private ElementHelper elementHelper;

    public void add(ConfigField configField) {
        windowHelper.openModal(By.id(AbstractSeleniumCore.BUTTON_ADD_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        waitHelper.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        waitHelper.waitFormLoad();

        if (ConfigFieldType.ROLLUP.equals(configField.getConfigFieldType())) {
            jqueryWait.waitJQueryLoad();
        }

        new Select(seleniumSettings.getWebDriver().findElement(By.name("TrackorTypeName"))).selectByVisibleText(configField.getTrackorTypeLabel());

        new Select(seleniumSettings.getWebDriver().findElement(By.name("dataType"))).selectByVisibleText(configField.getConfigFieldType().getLabel());

        seleniumSettings.getWebDriver().findElement(By.name("LabelName")).sendKeys(configField.getLabel());

        String val = new Select(seleniumSettings.getWebDriver().findElement(By.name("TrackorTypeName"))).getFirstSelectedOption().getAttribute("value");
        String cfPrefix = val.split(";")[1].replace(":", "").replace("-", "").replace(".", "");
        elementWaitHelper.waitElementAttributeByName("configFieldName", "value", cfPrefix + "_" + configField.getLabel().replace(" ", "_").replace("/", "").replace("-", "").toUpperCase());
        configFieldHelper.waitFieldNameUpdated();

        seleniumSettings.getWebDriver().findElement(By.name("configFieldName")).clear();
        seleniumSettings.getWebDriver().findElement(By.name("configFieldName")).sendKeys(configField.getName());

        new Select(seleniumSettings.getWebDriver().findElement(By.name("componentsPackageId"))).selectByVisibleText(configField.getCompPackage());

        new Select(seleniumSettings.getWebDriver().findElement(By.name("colorId"))).selectByVisibleText(configField.getColor());

        seleniumSettings.getWebDriver().findElement(By.name("fieldWidth")).clear();
        seleniumSettings.getWebDriver().findElement(By.name("fieldWidth")).sendKeys(configField.getWidth());

        if (ConfigFieldType.TEXT.equals(configField.getConfigFieldType())) {
            seleniumSettings.getWebDriver().findElement(By.name("fieldSize")).clear();
            seleniumSettings.getWebDriver().findElement(By.name("fieldSize")).sendKeys(configField.getConfigFieldText().getLength());

            setSqlToCodeMirror("btnDefSQL", configField.getConfigFieldText().getDefValueSql());
        } else if (ConfigFieldType.NUMBER.equals(configField.getConfigFieldType())) {
            seleniumSettings.getWebDriver().findElement(By.name("fieldSize")).clear();
            seleniumSettings.getWebDriver().findElement(By.name("fieldSize")).sendKeys(configField.getConfigFieldNumber().getLength());

            seleniumSettings.getWebDriver().findElement(By.name("prefix")).clear();
            seleniumSettings.getWebDriver().findElement(By.name("prefix")).sendKeys(configField.getConfigFieldNumber().getPrefix());
            seleniumSettings.getWebDriver().findElement(By.name("suffix")).clear();
            seleniumSettings.getWebDriver().findElement(By.name("suffix")).sendKeys(configField.getConfigFieldNumber().getSuffix());
            seleniumSettings.getWebDriver().findElement(By.name("numDecimals")).clear();
            seleniumSettings.getWebDriver().findElement(By.name("numDecimals")).sendKeys(configField.getConfigFieldNumber().getDecimal());
            windowHelper.openModal(By.name("btnrgbNegColor"));
            seleniumSettings.getWebDriver().findElement(By.className("dhxcp_color_selector")).click();
            windowHelper.closeModal(By.className("dhx_button_save"));
            windowHelper.openModal(By.name("btnrgbPosColor"));
            seleniumSettings.getWebDriver().findElement(By.className("dhxcp_color_selector")).click();
            windowHelper.closeModal(By.className("dhx_button_save"));

            if ((configField.getConfigFieldNumber().getParensForNegative().equals("YES") && !checkboxHelper.isCheckedByName("parensForNegative"))
                    || (configField.getConfigFieldNumber().getParensForNegative().equals("NO") && checkboxHelper.isCheckedByName("parensForNegative"))) {
                checkboxHelper.clickByName("parensForNegative");
            }
            if ((configField.getConfigFieldNumber().getSeparateThousands().equals("YES") && !checkboxHelper.isCheckedByName("separateThousands"))
                    || (configField.getConfigFieldNumber().getSeparateThousands().equals("NO") && checkboxHelper.isCheckedByName("separateThousands"))) {
                checkboxHelper.clickByName("separateThousands");
            }

            setSqlToCodeMirror("btnDefSQL", configField.getConfigFieldNumber().getDefValueSql());
        } else if (ConfigFieldType.DATE.equals(configField.getConfigFieldType())) {
            assertHelper.AssertText("fieldSize", "10");

            setSqlToCodeMirror("btnDefSQL", configField.getConfigFieldDate().getDefValueSql());
        } else if (ConfigFieldType.CHECKBOX.equals(configField.getConfigFieldType())) {
            assertHelper.AssertText("fieldSize", "1");

            setSqlToCodeMirror("btnDefSQL", configField.getConfigFieldCheckbox().getDefValueSql());
        } else if (ConfigFieldType.DROP_DOWN.equals(configField.getConfigFieldType())) {
            psSelectorHelper.selectSpecificValue(By.id("btntableName"), By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE + AbstractSeleniumCore.getGridIdx()), 3L, configField.getConfigFieldDropDown().getTable(), 2L);

            setSqlToCodeMirror("btnDefSQL", configField.getConfigFieldDropDown().getDefValueSql());
        } else if (ConfigFieldType.MEMO.equals(configField.getConfigFieldType())) {
            seleniumSettings.getWebDriver().findElement(By.name("fieldSize")).clear();
            seleniumSettings.getWebDriver().findElement(By.name("fieldSize")).sendKeys(configField.getConfigFieldMemo().getLength());
            seleniumSettings.getWebDriver().findElement(By.name("linesQty")).clear();
            seleniumSettings.getWebDriver().findElement(By.name("linesQty")).sendKeys(configField.getConfigFieldMemo().getLines());

            setSqlToCodeMirror("btnDefSQL", configField.getConfigFieldMemo().getDefValueSql());
        } else if (ConfigFieldType.WIKI.equals(configField.getConfigFieldType())) {
            seleniumSettings.getWebDriver().findElement(By.name("fieldSize")).clear();
            seleniumSettings.getWebDriver().findElement(By.name("fieldSize")).sendKeys(configField.getConfigFieldWiki().getLength());
            seleniumSettings.getWebDriver().findElement(By.name("linesQty")).clear();
            seleniumSettings.getWebDriver().findElement(By.name("linesQty")).sendKeys(configField.getConfigFieldWiki().getLines());

            setSqlToCodeMirror("btnDefSQL", configField.getConfigFieldWiki().getDefValueSql());
        } else if (ConfigFieldType.DB_DROP_DOWN.equals(configField.getConfigFieldType())) {
            setSqlToCodeMirror("btnSQL", configField.getConfigFieldDbDropDown().getSql());
            setSqlToCodeMirror("btnDefSQL", configField.getConfigFieldDbDropDown().getDefValueSql());
        } else if (ConfigFieldType.DB_SELECTOR.equals(configField.getConfigFieldType())) {
            setSqlToCodeMirror("btnSQL", configField.getConfigFieldDbSelector().getSql());
            setSqlToCodeMirror("btnDefSQL", configField.getConfigFieldDbSelector().getDefValueSql());
        } else if (ConfigFieldType.SELECTOR.equals(configField.getConfigFieldType())) {
            psSelectorHelper.selectSpecificValue(By.id("btntableName"), By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE + AbstractSeleniumCore.getGridIdx()), 3L, configField.getConfigFieldSelector().getTable(), 2L);

            setSqlToCodeMirror("btnDefSQL", configField.getConfigFieldSelector().getDefValueSql());
        } else if (ConfigFieldType.LATITUDE.equals(configField.getConfigFieldType())) {
            assertHelper.AssertText("fieldSize", "15");

            setSqlToCodeMirror("btnDefSQL", configField.getConfigFieldLatitude().getDefValueSql());
        } else if (ConfigFieldType.LONGITUDE.equals(configField.getConfigFieldType())) {
            assertHelper.AssertText("fieldSize", "15");

            setSqlToCodeMirror("btnDefSQL", configField.getConfigFieldLongitude().getDefValueSql());
        } else if (ConfigFieldType.ELECTRONIC_FILE.equals(configField.getConfigFieldType())) {
            elementHelper.clickById(AbstractSeleniumCore.BUTTON_APPLY_ID);
            waitHelper.waitReloadForm("reloaded=1");
            waitHelper.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
            waitHelper.waitFormLoad();

            tabHelper.goToTab(2L); //Image Settings

            if ((configField.getConfigFieldEfile().getExtractMetadata().equals("YES") && !checkboxHelper.isCheckedByName("imageExtractMetadata"))
                    || (configField.getConfigFieldEfile().getExtractMetadata().equals("NO") && checkboxHelper.isCheckedByName("imageExtractMetadata"))) {
                checkboxHelper.clickByName("imageExtractMetadata");
            }
            new Select(seleniumSettings.getWebDriver().findElement(By.name("imageLatConfigFieldId"))).selectByVisibleText(configField.getConfigFieldEfile().getImageLatitude());
            new Select(seleniumSettings.getWebDriver().findElement(By.name("imageLongConfigFieldId"))).selectByVisibleText(configField.getConfigFieldEfile().getImageLongitude());
            new Select(seleniumSettings.getWebDriver().findElement(By.name("imageTimeConfigFieldId"))).selectByVisibleText(configField.getConfigFieldEfile().getImageTimeSnapshot());
            new Select(seleniumSettings.getWebDriver().findElement(By.name("imageResizeMode"))).selectByVisibleText(configField.getConfigFieldEfile().getResizeMode());
            seleniumSettings.getWebDriver().findElement(By.name("imageWidth")).clear();
            seleniumSettings.getWebDriver().findElement(By.name("imageWidth")).sendKeys(configField.getConfigFieldEfile().getResizeWidth());
            if (!configField.getConfigFieldEfile().getResizeHeight().equals(seleniumSettings.getWebDriver().findElement(By.name("imageHeight")).getAttribute("value"))) {
                seleniumSettings.getWebDriver().findElement(By.name("imageHeight")).clear();
                seleniumSettings.getWebDriver().findElement(By.name("imageHeight")).sendKeys(configField.getConfigFieldEfile().getResizeHeight());
            }
            if ((configField.getConfigFieldEfile().getRotate().equals("YES") && !checkboxHelper.isCheckedByName("imageRotate"))
                    || (configField.getConfigFieldEfile().getRotate().equals("NO") && checkboxHelper.isCheckedByName("imageRotate"))) {
                checkboxHelper.clickByName("imageRotate");
            }
            if ((configField.getConfigFieldEfile().getLogBlobChanges().equals("YES") && !checkboxHelper.isCheckedByName("logBlobChanges"))
                    || (configField.getConfigFieldEfile().getLogBlobChanges().equals("NO") && checkboxHelper.isCheckedByName("logBlobChanges"))) {
                checkboxHelper.clickByName("logBlobChanges");
            }
            if ((configField.getConfigFieldEfile().getUploadToAws().equals("YES") && !checkboxHelper.isCheckedByName("uploadToS3Directly"))
                    || (configField.getConfigFieldEfile().getUploadToAws().equals("NO") && checkboxHelper.isCheckedByName("uploadToS3Directly"))) {
                checkboxHelper.clickByName("uploadToS3Directly");
            }
            psSelectorHelper.selectSpecificValue(By.id("btnautocaptionClientFile"), By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE + AbstractSeleniumCore.getGridIdx()), 1L, configField.getConfigFieldEfile().getAutocaptionTemplate(), 1L);

            tabHelper.goToTab(1L); //General
        } else if (ConfigFieldType.TRACKOR_SELECTOR.equals(configField.getConfigFieldType())) {
            setSqlToCodeMirror("btnSQL", configField.getConfigFieldTrackorSelector().getSql());

            new Select(seleniumSettings.getWebDriver().findElement(By.name("ObjectTrackorType"))).selectByVisibleText(configField.getConfigFieldTrackorSelector().getTrackorType());
            seleniumSettings.getWebDriver().findElement(By.name("shortNameLabel")).clear();
            seleniumSettings.getWebDriver().findElement(By.name("shortNameLabel")).sendKeys(configField.getConfigFieldTrackorSelector().getShortName());
            if ((configField.getConfigFieldTrackorSelector().getMyThingsFilter().equals("YES") && !checkboxHelper.isCheckedByName("useInMyThingsFilter"))
                    || (configField.getConfigFieldTrackorSelector().getMyThingsFilter().equals("NO") && checkboxHelper.isCheckedByName("useInMyThingsFilter"))) {
                checkboxHelper.clickByName("useInMyThingsFilter");
            }
            if ((configField.getConfigFieldTrackorSelector().getMyThingsMarker().equals("YES") && !checkboxHelper.isCheckedByName("myThingsMarket"))
                    || (configField.getConfigFieldTrackorSelector().getMyThingsMarker().equals("NO") && checkboxHelper.isCheckedByName("myThingsMarket"))) {
                checkboxHelper.clickByName("myThingsMarket");
            }

            psSelectorHelper.selectSpecificValue(By.id("btnobjCf"), By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE + AbstractSeleniumCore.getGridIdx()), 1L, configField.getConfigFieldTrackorSelector().getDisplayField(), 1L);

            setSqlToCodeMirror("btnDefSQL", configField.getConfigFieldTrackorSelector().getDefValueSql());
        } else if (ConfigFieldType.TRACKOR_DROP_DOWN.equals(configField.getConfigFieldType())) {
            setSqlToCodeMirror("btnSQL", configField.getConfigFieldTrackorDropDown().getSql());

            new Select(seleniumSettings.getWebDriver().findElement(By.name("ObjectTrackorType"))).selectByVisibleText(configField.getConfigFieldTrackorDropDown().getTrackorType());
            seleniumSettings.getWebDriver().findElement(By.name("shortNameLabel")).clear();
            seleniumSettings.getWebDriver().findElement(By.name("shortNameLabel")).sendKeys(configField.getConfigFieldTrackorDropDown().getShortName());
            if ((configField.getConfigFieldTrackorDropDown().getMyThingsFilter().equals("YES") && !checkboxHelper.isCheckedByName("useInMyThingsFilter"))
                    || (configField.getConfigFieldTrackorDropDown().getMyThingsFilter().equals("NO") && checkboxHelper.isCheckedByName("useInMyThingsFilter"))) {
                checkboxHelper.clickByName("useInMyThingsFilter");
            }
            if ((configField.getConfigFieldTrackorDropDown().getMyThingsMarker().equals("YES") && !checkboxHelper.isCheckedByName("myThingsMarket"))
                    || (configField.getConfigFieldTrackorDropDown().getMyThingsMarker().equals("NO") && checkboxHelper.isCheckedByName("myThingsMarket"))) {
                checkboxHelper.clickByName("myThingsMarket");
            }

            setSqlToCodeMirror("btnDefSQL", configField.getConfigFieldTrackorDropDown().getDefValueSql());
        } else if (ConfigFieldType.CALCULATED.equals(configField.getConfigFieldType())) {
            setSqlToCodeMirror("btnSQL", configField.getConfigFieldCalculated().getSql());
        } else if (ConfigFieldType.HYPERLINK.equals(configField.getConfigFieldType())) {
            seleniumSettings.getWebDriver().findElement(By.name("fieldSize")).clear();
            seleniumSettings.getWebDriver().findElement(By.name("fieldSize")).sendKeys(configField.getConfigFieldHyperlink().getLength());
        } else if (ConfigFieldType.ROLLUP.equals(configField.getConfigFieldType())) {
            //TODO filter

            new Select(seleniumSettings.getWebDriver().findElement(By.name("lstRollupXitorTypeID"))).selectByVisibleText(configField.getConfigFieldRollup().getTrackorType());
        } else if (ConfigFieldType.MULTI_SELECTOR.equals(configField.getConfigFieldType())) {
            seleniumSettings.getWebDriver().findElement(By.name("linesQty")).clear();
            seleniumSettings.getWebDriver().findElement(By.name("linesQty")).sendKeys(configField.getConfigFieldMultiSelector().getLines());

            psSelectorHelper.selectSpecificValue(By.id("btntableName"), By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE + AbstractSeleniumCore.getGridIdx()), 3L, configField.getConfigFieldMultiSelector().getTable(), 2L);

            setSqlToCodeMirror("btnDefSQL", configField.getConfigFieldMultiSelector().getDefValueSql());
        } else if (ConfigFieldType.DATE_TIME.equals(configField.getConfigFieldType())) {
            assertHelper.AssertText("fieldSize", "10");

            if ((configField.getConfigFieldDateTime().getShowSeconds().equals("YES") && !checkboxHelper.isCheckedByName("showSeconds"))
                    || (configField.getConfigFieldDateTime().getShowSeconds().equals("NO") && checkboxHelper.isCheckedByName("showSeconds"))) {
                checkboxHelper.clickByName("showSeconds");
            }

            setSqlToCodeMirror("btnDefSQL", configField.getConfigFieldDateTime().getDefValueSql());
        } else if (ConfigFieldType.TIME.equals(configField.getConfigFieldType())) {
            assertHelper.AssertText("fieldSize", "10");

            if ((configField.getConfigFieldTime().getShowSeconds().equals("YES") && !checkboxHelper.isCheckedByName("showSeconds"))
                    || (configField.getConfigFieldTime().getShowSeconds().equals("NO") && checkboxHelper.isCheckedByName("showSeconds"))) {
                checkboxHelper.clickByName("showSeconds");
            }

            setSqlToCodeMirror("btnDefSQL", configField.getConfigFieldTime().getDefValueSql());
        } else {
            throw new SeleniumUnexpectedException("Not support ConfigFieldType. ConfigFieldType=" + configField.getConfigFieldType());
        }

        seleniumSettings.getWebDriver().findElement(By.name("description")).sendKeys(configField.getDescription());

        seleniumSettings.getWebDriver().findElement(By.name("comments")).sendKeys(configField.getComment());

        if ((configField.getMandatory().equals("YES") && !checkboxHelper.isCheckedByName("mandatory"))
                || (configField.getMandatory().equals("NO") && checkboxHelper.isCheckedByName("mandatory"))) {
            checkboxHelper.clickByName("mandatory");
        }

        if ((configField.getTwoColumns().equals("YES") && !checkboxHelper.isCheckedByName("twoColsSpan"))
                || (configField.getTwoColumns().equals("NO") && checkboxHelper.isCheckedByName("twoColsSpan"))) {
            checkboxHelper.clickByName("twoColsSpan");
        }

        if ((configField.getLockable().equals("YES") && !checkboxHelper.isCheckedByName("lockable"))
                || (configField.getLockable().equals("NO") && checkboxHelper.isCheckedByName("lockable"))) {
            checkboxHelper.clickByName("lockable");
        }

        if ((configField.getMultiple().equals("YES") && !checkboxHelper.isCheckedByName("multiple"))
                || (configField.getMultiple().equals("NO") && checkboxHelper.isCheckedByName("multiple"))) {
            checkboxHelper.clickByName("multiple");
        }

        if ((configField.getReadOnly().equals("YES") && !checkboxHelper.isCheckedByName("readOnly"))
                || (configField.getReadOnly().equals("NO") && checkboxHelper.isCheckedByName("readOnly"))) {
            checkboxHelper.clickByName("readOnly");
        }

        if ((configField.getCalcTotal().equals("YES") && !checkboxHelper.isCheckedByName("calcTotal"))
                || (configField.getCalcTotal().equals("NO") && checkboxHelper.isCheckedByName("calcTotal"))) {
            checkboxHelper.clickByName("calcTotal");
        }

        if ((configField.getNotCloneValue().equals("YES") && !checkboxHelper.isCheckedByName("notCloneFieldValue"))
                || (configField.getNotCloneValue().equals("NO") && checkboxHelper.isCheckedByName("notCloneFieldValue"))) {
            checkboxHelper.clickByName("notCloneFieldValue");
        }

        if ((configField.getNotCloneLock().equals("YES") && !checkboxHelper.isCheckedByName("notCloneLocks"))
                || (configField.getNotCloneLock().equals("NO") && checkboxHelper.isCheckedByName("notCloneLocks"))) {
            checkboxHelper.clickByName("notCloneLocks");
        }

        windowHelper.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        waitHelper.waitGridLoad(AbstractSeleniumCore.getGridIdx(), AbstractSeleniumCore.getGridIdx());
    }

    public void edit(ConfigField configField) {
        windowHelper.openModal(By.id(AbstractSeleniumCore.BUTTON_EDIT_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        waitHelper.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        waitHelper.waitFormLoad();

        if (ConfigFieldType.ROLLUP.equals(configField.getConfigFieldType())) {
            jqueryWait.waitJQueryLoad();
        }

        seleniumSettings.getWebDriver().findElement(By.name("LabelName")).clear();
        seleniumSettings.getWebDriver().findElement(By.name("LabelName")).sendKeys(configField.getLabel());

        seleniumSettings.getWebDriver().findElement(By.name("configFieldName")).clear();
        seleniumSettings.getWebDriver().findElement(By.name("configFieldName")).sendKeys(configField.getName());

        new Select(seleniumSettings.getWebDriver().findElement(By.name("componentsPackageId"))).selectByVisibleText(configField.getCompPackage());

        new Select(seleniumSettings.getWebDriver().findElement(By.name("colorId"))).selectByVisibleText(configField.getColor());

        seleniumSettings.getWebDriver().findElement(By.name("fieldWidth")).clear();
        seleniumSettings.getWebDriver().findElement(By.name("fieldWidth")).sendKeys(configField.getWidth());

        if (ConfigFieldType.TEXT.equals(configField.getConfigFieldType())) {
            seleniumSettings.getWebDriver().findElement(By.name("fieldSize")).clear();
            seleniumSettings.getWebDriver().findElement(By.name("fieldSize")).sendKeys(configField.getConfigFieldText().getLength());

            setSqlToCodeMirror("btnDefSQL", configField.getConfigFieldText().getDefValueSql());
        } else if (ConfigFieldType.NUMBER.equals(configField.getConfigFieldType())) {
            seleniumSettings.getWebDriver().findElement(By.name("fieldSize")).clear();
            seleniumSettings.getWebDriver().findElement(By.name("fieldSize")).sendKeys(configField.getConfigFieldNumber().getLength());

            seleniumSettings.getWebDriver().findElement(By.name("prefix")).clear();
            seleniumSettings.getWebDriver().findElement(By.name("prefix")).sendKeys(configField.getConfigFieldNumber().getPrefix());
            seleniumSettings.getWebDriver().findElement(By.name("suffix")).clear();
            seleniumSettings.getWebDriver().findElement(By.name("suffix")).sendKeys(configField.getConfigFieldNumber().getSuffix());
            seleniumSettings.getWebDriver().findElement(By.name("numDecimals")).clear();
            seleniumSettings.getWebDriver().findElement(By.name("numDecimals")).sendKeys(configField.getConfigFieldNumber().getDecimal());
            windowHelper.openModal(By.name("btnrgbNegColor"));
            seleniumSettings.getWebDriver().findElement(By.className("dhxcp_color_selector")).click();
            windowHelper.closeModal(By.className("dhx_button_save"));
            windowHelper.openModal(By.name("btnrgbPosColor"));
            seleniumSettings.getWebDriver().findElement(By.className("dhxcp_color_selector")).click();
            windowHelper.closeModal(By.className("dhx_button_save"));

            if ((configField.getConfigFieldNumber().getParensForNegative().equals("YES") && !checkboxHelper.isCheckedByName("parensForNegative"))
                    || (configField.getConfigFieldNumber().getParensForNegative().equals("NO") && checkboxHelper.isCheckedByName("parensForNegative"))) {
                checkboxHelper.clickByName("parensForNegative");
            }
            if ((configField.getConfigFieldNumber().getSeparateThousands().equals("YES") && !checkboxHelper.isCheckedByName("separateThousands"))
                    || (configField.getConfigFieldNumber().getSeparateThousands().equals("NO") && checkboxHelper.isCheckedByName("separateThousands"))) {
                checkboxHelper.clickByName("separateThousands");
            }

            setSqlToCodeMirror("btnDefSQL", configField.getConfigFieldNumber().getDefValueSql());
        } else if (ConfigFieldType.DATE.equals(configField.getConfigFieldType())) {
            assertHelper.AssertText("fieldSize", "10");

            setSqlToCodeMirror("btnDefSQL", configField.getConfigFieldDate().getDefValueSql());
        } else if (ConfigFieldType.CHECKBOX.equals(configField.getConfigFieldType())) {
            assertHelper.AssertText("fieldSize", "1");

            setSqlToCodeMirror("btnDefSQL", configField.getConfigFieldCheckbox().getDefValueSql());
        } else if (ConfigFieldType.DROP_DOWN.equals(configField.getConfigFieldType())) {
            psSelectorHelper.selectSpecificValue(By.id("btntableName"), By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE + AbstractSeleniumCore.getGridIdx()), 3L, configField.getConfigFieldDropDown().getTable(), 2L);

            setSqlToCodeMirror("btnDefSQL", configField.getConfigFieldDropDown().getDefValueSql());
        } else if (ConfigFieldType.MEMO.equals(configField.getConfigFieldType())) {
            seleniumSettings.getWebDriver().findElement(By.name("fieldSize")).clear();
            seleniumSettings.getWebDriver().findElement(By.name("fieldSize")).sendKeys(configField.getConfigFieldMemo().getLength());
            seleniumSettings.getWebDriver().findElement(By.name("linesQty")).clear();
            seleniumSettings.getWebDriver().findElement(By.name("linesQty")).sendKeys(configField.getConfigFieldMemo().getLines());

            setSqlToCodeMirror("btnDefSQL", configField.getConfigFieldMemo().getDefValueSql());
        } else if (ConfigFieldType.WIKI.equals(configField.getConfigFieldType())) {
            seleniumSettings.getWebDriver().findElement(By.name("fieldSize")).clear();
            seleniumSettings.getWebDriver().findElement(By.name("fieldSize")).sendKeys(configField.getConfigFieldWiki().getLength());
            seleniumSettings.getWebDriver().findElement(By.name("linesQty")).clear();
            seleniumSettings.getWebDriver().findElement(By.name("linesQty")).sendKeys(configField.getConfigFieldWiki().getLines());

            setSqlToCodeMirror("btnDefSQL", configField.getConfigFieldWiki().getDefValueSql());
        } else if (ConfigFieldType.DB_DROP_DOWN.equals(configField.getConfigFieldType())) {
            setSqlToCodeMirror("btnSQL", configField.getConfigFieldDbDropDown().getSql());
            setSqlToCodeMirror("btnDefSQL", configField.getConfigFieldDbDropDown().getDefValueSql());
        } else if (ConfigFieldType.DB_SELECTOR.equals(configField.getConfigFieldType())) {
            setSqlToCodeMirror("btnSQL", configField.getConfigFieldDbSelector().getSql());
            setSqlToCodeMirror("btnDefSQL", configField.getConfigFieldDbSelector().getDefValueSql());
        } else if (ConfigFieldType.SELECTOR.equals(configField.getConfigFieldType())) {
            psSelectorHelper.selectSpecificValue(By.id("btntableName"), By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE + AbstractSeleniumCore.getGridIdx()), 3L, configField.getConfigFieldSelector().getTable(), 2L);

            setSqlToCodeMirror("btnDefSQL", configField.getConfigFieldSelector().getDefValueSql());
        } else if (ConfigFieldType.LATITUDE.equals(configField.getConfigFieldType())) {
            assertHelper.AssertText("fieldSize", "15");

            setSqlToCodeMirror("btnDefSQL", configField.getConfigFieldLatitude().getDefValueSql());
        } else if (ConfigFieldType.LONGITUDE.equals(configField.getConfigFieldType())) {
            assertHelper.AssertText("fieldSize", "15");

            setSqlToCodeMirror("btnDefSQL", configField.getConfigFieldLongitude().getDefValueSql());
        } else if (ConfigFieldType.ELECTRONIC_FILE.equals(configField.getConfigFieldType())) {
            tabHelper.goToTab(2L); //Image Settings

            if ((configField.getConfigFieldEfile().getExtractMetadata().equals("YES") && !checkboxHelper.isCheckedByName("imageExtractMetadata"))
                    || (configField.getConfigFieldEfile().getExtractMetadata().equals("NO") && checkboxHelper.isCheckedByName("imageExtractMetadata"))) {
                checkboxHelper.clickByName("imageExtractMetadata");
            }
            new Select(seleniumSettings.getWebDriver().findElement(By.name("imageLatConfigFieldId"))).selectByVisibleText(configField.getConfigFieldEfile().getImageLatitude());
            new Select(seleniumSettings.getWebDriver().findElement(By.name("imageLongConfigFieldId"))).selectByVisibleText(configField.getConfigFieldEfile().getImageLongitude());
            new Select(seleniumSettings.getWebDriver().findElement(By.name("imageTimeConfigFieldId"))).selectByVisibleText(configField.getConfigFieldEfile().getImageTimeSnapshot());
            new Select(seleniumSettings.getWebDriver().findElement(By.name("imageResizeMode"))).selectByVisibleText(configField.getConfigFieldEfile().getResizeMode());
            seleniumSettings.getWebDriver().findElement(By.name("imageWidth")).clear();
            seleniumSettings.getWebDriver().findElement(By.name("imageWidth")).sendKeys(configField.getConfigFieldEfile().getResizeWidth());
            seleniumSettings.getWebDriver().findElement(By.name("imageHeight")).clear();
            seleniumSettings.getWebDriver().findElement(By.name("imageHeight")).sendKeys(configField.getConfigFieldEfile().getResizeHeight());
            if ((configField.getConfigFieldEfile().getRotate().equals("YES") && !checkboxHelper.isCheckedByName("imageRotate"))
                    || (configField.getConfigFieldEfile().getRotate().equals("NO") && checkboxHelper.isCheckedByName("imageRotate"))) {
                checkboxHelper.clickByName("imageRotate");
            }
            if ((configField.getConfigFieldEfile().getLogBlobChanges().equals("YES") && !checkboxHelper.isCheckedByName("logBlobChanges"))
                    || (configField.getConfigFieldEfile().getLogBlobChanges().equals("NO") && checkboxHelper.isCheckedByName("logBlobChanges"))) {
                checkboxHelper.clickByName("logBlobChanges");
            }
            if ((configField.getConfigFieldEfile().getUploadToAws().equals("YES") && !checkboxHelper.isCheckedByName("uploadToS3Directly"))
                    || (configField.getConfigFieldEfile().getUploadToAws().equals("NO") && checkboxHelper.isCheckedByName("uploadToS3Directly"))) {
                checkboxHelper.clickByName("uploadToS3Directly");
            }
            psSelectorHelper.selectSpecificValue(By.id("btnautocaptionClientFile"), By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE + AbstractSeleniumCore.getGridIdx()), 1L, configField.getConfigFieldEfile().getAutocaptionTemplate(), 1L);

            tabHelper.goToTab(1L); //General
        } else if (ConfigFieldType.TRACKOR_SELECTOR.equals(configField.getConfigFieldType())) {
            setSqlToCodeMirror("btnSQL", configField.getConfigFieldTrackorSelector().getSql());

            new Select(seleniumSettings.getWebDriver().findElement(By.name("ObjectTrackorType"))).selectByVisibleText(configField.getConfigFieldTrackorSelector().getTrackorType());
            seleniumSettings.getWebDriver().findElement(By.name("shortNameLabel")).clear();
            seleniumSettings.getWebDriver().findElement(By.name("shortNameLabel")).sendKeys(configField.getConfigFieldTrackorSelector().getShortName());
            if ((configField.getConfigFieldTrackorSelector().getMyThingsFilter().equals("YES") && !checkboxHelper.isCheckedByName("useInMyThingsFilter"))
                    || (configField.getConfigFieldTrackorSelector().getMyThingsFilter().equals("NO") && checkboxHelper.isCheckedByName("useInMyThingsFilter"))) {
                checkboxHelper.clickByName("useInMyThingsFilter");
            }
            if ((configField.getConfigFieldTrackorSelector().getMyThingsMarker().equals("YES") && !checkboxHelper.isCheckedByName("myThingsMarket"))
                    || (configField.getConfigFieldTrackorSelector().getMyThingsMarker().equals("NO") && checkboxHelper.isCheckedByName("myThingsMarket"))) {
                checkboxHelper.clickByName("myThingsMarket");
            }

            psSelectorHelper.selectSpecificValue(By.id("btnobjCf"), By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE + AbstractSeleniumCore.getGridIdx()), 1L, configField.getConfigFieldTrackorSelector().getDisplayField(), 1L);

            setSqlToCodeMirror("btnDefSQL", configField.getConfigFieldTrackorSelector().getDefValueSql());
        } else if (ConfigFieldType.TRACKOR_DROP_DOWN.equals(configField.getConfigFieldType())) {
            setSqlToCodeMirror("btnSQL", configField.getConfigFieldTrackorDropDown().getSql());

            new Select(seleniumSettings.getWebDriver().findElement(By.name("ObjectTrackorType"))).selectByVisibleText(configField.getConfigFieldTrackorDropDown().getTrackorType());
            seleniumSettings.getWebDriver().findElement(By.name("shortNameLabel")).clear();
            seleniumSettings.getWebDriver().findElement(By.name("shortNameLabel")).sendKeys(configField.getConfigFieldTrackorDropDown().getShortName());
            if ((configField.getConfigFieldTrackorDropDown().getMyThingsFilter().equals("YES") && !checkboxHelper.isCheckedByName("useInMyThingsFilter"))
                    || (configField.getConfigFieldTrackorDropDown().getMyThingsFilter().equals("NO") && checkboxHelper.isCheckedByName("useInMyThingsFilter"))) {
                checkboxHelper.clickByName("useInMyThingsFilter");
            }
            if ((configField.getConfigFieldTrackorDropDown().getMyThingsMarker().equals("YES") && !checkboxHelper.isCheckedByName("myThingsMarket"))
                    || (configField.getConfigFieldTrackorDropDown().getMyThingsMarker().equals("NO") && checkboxHelper.isCheckedByName("myThingsMarket"))) {
                checkboxHelper.clickByName("myThingsMarket");
            }

            setSqlToCodeMirror("btnDefSQL", configField.getConfigFieldTrackorDropDown().getDefValueSql());
        } else if (ConfigFieldType.CALCULATED.equals(configField.getConfigFieldType())) {
            setSqlToCodeMirror("btnSQL", configField.getConfigFieldCalculated().getSql());
        } else if (ConfigFieldType.HYPERLINK.equals(configField.getConfigFieldType())) {
            seleniumSettings.getWebDriver().findElement(By.name("fieldSize")).clear();
            seleniumSettings.getWebDriver().findElement(By.name("fieldSize")).sendKeys(configField.getConfigFieldHyperlink().getLength());
        } else if (ConfigFieldType.ROLLUP.equals(configField.getConfigFieldType())) {
            //TODO filter

            new Select(seleniumSettings.getWebDriver().findElement(By.name("lstRollupXitorTypeID"))).selectByVisibleText(configField.getConfigFieldRollup().getTrackorType());
        } else if (ConfigFieldType.MULTI_SELECTOR.equals(configField.getConfigFieldType())) {
            seleniumSettings.getWebDriver().findElement(By.name("linesQty")).clear();
            seleniumSettings.getWebDriver().findElement(By.name("linesQty")).sendKeys(configField.getConfigFieldMultiSelector().getLines());

            psSelectorHelper.selectSpecificValue(By.id("btntableName"), By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE + AbstractSeleniumCore.getGridIdx()), 3L, configField.getConfigFieldMultiSelector().getTable(), 2L);

            setSqlToCodeMirror("btnDefSQL", configField.getConfigFieldMultiSelector().getDefValueSql());
        } else if (ConfigFieldType.DATE_TIME.equals(configField.getConfigFieldType())) {
            assertHelper.AssertText("fieldSize", "10");

            if ((configField.getConfigFieldDateTime().getShowSeconds().equals("YES") && !checkboxHelper.isCheckedByName("showSeconds"))
                    || (configField.getConfigFieldDateTime().getShowSeconds().equals("NO") && checkboxHelper.isCheckedByName("showSeconds"))) {
                checkboxHelper.clickByName("showSeconds");
            }

            setSqlToCodeMirror("btnDefSQL", configField.getConfigFieldDateTime().getDefValueSql());
        } else if (ConfigFieldType.TIME.equals(configField.getConfigFieldType())) {
            assertHelper.AssertText("fieldSize", "10");

            if ((configField.getConfigFieldTime().getShowSeconds().equals("YES") && !checkboxHelper.isCheckedByName("showSeconds"))
                    || (configField.getConfigFieldTime().getShowSeconds().equals("NO") && checkboxHelper.isCheckedByName("showSeconds"))) {
                checkboxHelper.clickByName("showSeconds");
            }

            setSqlToCodeMirror("btnDefSQL", configField.getConfigFieldTime().getDefValueSql());
        } else {
            throw new SeleniumUnexpectedException("Not support ConfigFieldType. ConfigFieldType=" + configField.getConfigFieldType());
        }

        seleniumSettings.getWebDriver().findElement(By.name("description")).clear();
        seleniumSettings.getWebDriver().findElement(By.name("description")).sendKeys(configField.getDescription());

        seleniumSettings.getWebDriver().findElement(By.name("comments")).clear();
        seleniumSettings.getWebDriver().findElement(By.name("comments")).sendKeys(configField.getComment());

        if ((configField.getMandatory().equals("YES") && !checkboxHelper.isCheckedByName("mandatory"))
                || (configField.getMandatory().equals("NO") && checkboxHelper.isCheckedByName("mandatory"))) {
            checkboxHelper.clickByName("mandatory");
        }

        if ((configField.getTwoColumns().equals("YES") && !checkboxHelper.isCheckedByName("twoColsSpan"))
                || (configField.getTwoColumns().equals("NO") && checkboxHelper.isCheckedByName("twoColsSpan"))) {
            checkboxHelper.clickByName("twoColsSpan");
        }

        boolean removeLockable = false;
        if ((configField.getLockable().equals("YES") && !checkboxHelper.isCheckedByName("lockable"))
                || (configField.getLockable().equals("NO") && checkboxHelper.isCheckedByName("lockable"))) {
            if (configField.getLockable().equals("NO") && checkboxHelper.isCheckedByName("lockable")) {
                removeLockable = true;
            }
            checkboxHelper.clickByName("lockable");
        }

        if ((configField.getMultiple().equals("YES") && !checkboxHelper.isCheckedByName("multiple"))
                || (configField.getMultiple().equals("NO") && checkboxHelper.isCheckedByName("multiple"))) {
            checkboxHelper.clickByName("multiple");
        }

        if ((configField.getReadOnly().equals("YES") && !checkboxHelper.isCheckedByName("readOnly"))
                || (configField.getReadOnly().equals("NO") && checkboxHelper.isCheckedByName("readOnly"))) {
            checkboxHelper.clickByName("readOnly");
        }

        if ((configField.getCalcTotal().equals("YES") && !checkboxHelper.isCheckedByName("calcTotal"))
                || (configField.getCalcTotal().equals("NO") && checkboxHelper.isCheckedByName("calcTotal"))) {
            checkboxHelper.clickByName("calcTotal");
        }

        if ((configField.getNotCloneValue().equals("YES") && !checkboxHelper.isCheckedByName("notCloneFieldValue"))
                || (configField.getNotCloneValue().equals("NO") && checkboxHelper.isCheckedByName("notCloneFieldValue"))) {
            checkboxHelper.clickByName("notCloneFieldValue");
        }

        if ((configField.getNotCloneLock().equals("YES") && !checkboxHelper.isCheckedByName("notCloneLocks"))
                || (configField.getNotCloneLock().equals("NO") && checkboxHelper.isCheckedByName("notCloneLocks"))) {
            checkboxHelper.clickByName("notCloneLocks");
        }

        if (removeLockable) {
            windowHelper.closeModalWithAlert(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE), AbstractSeleniumCore.MESSAGE_DELETE_LOCKABLE);
        } else {
            windowHelper.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        }
        waitHelper.waitGridLoad(AbstractSeleniumCore.getGridIdx(), AbstractSeleniumCore.getGridIdx());
    }

    public void testOnForm(ConfigField configField) {
        windowHelper.openModal(By.id(AbstractSeleniumCore.BUTTON_EDIT_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        waitHelper.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        waitHelper.waitFormLoad();

        if (ConfigFieldType.ROLLUP.equals(configField.getConfigFieldType())) {
            jqueryWait.waitJQueryLoad();
        }

        assertHelper.AssertSelect("TrackorTypeName", configField.getTrackorTypeLabel());
        assertHelper.AssertSelect("dataType", configField.getConfigFieldType().getLabel());
        assertHelper.AssertText("LabelName", configField.getLabel());
        assertHelper.AssertText("configFieldName", configField.getName());

        assertHelper.AssertSelect("componentsPackageId", configField.getCompPackage());

        assertHelper.AssertSelect("colorId", configField.getColor());

        assertHelper.AssertText("fieldWidth", configField.getWidth());

        if (ConfigFieldType.TEXT.equals(configField.getConfigFieldType())) {
            assertHelper.AssertText("fieldSize", configField.getConfigFieldText().getLength());

            checkSqlInCodeMirror("btnDefSQL", configField.getConfigFieldText().getDefValueSql());
        } else if (ConfigFieldType.NUMBER.equals(configField.getConfigFieldType())) {
            assertHelper.AssertText("fieldSize", configField.getConfigFieldNumber().getLength());

            assertHelper.AssertText("prefix", configField.getConfigFieldNumber().getPrefix());
            assertHelper.AssertText("suffix", configField.getConfigFieldNumber().getSuffix());
            assertHelper.AssertText("numDecimals", configField.getConfigFieldNumber().getDecimal());
            assertHelper.AssertText("negativeColor", configField.getConfigFieldNumber().getNegativeColor());
            assertHelper.AssertText("positiveColor", configField.getConfigFieldNumber().getPositiveColor());

            assertHelper.AssertCheckBoxNew("parensForNegative", configField.getConfigFieldNumber().getParensForNegative());
            assertHelper.AssertCheckBoxNew("separateThousands", configField.getConfigFieldNumber().getSeparateThousands());

            checkSqlInCodeMirror("btnDefSQL", configField.getConfigFieldNumber().getDefValueSql());
        } else if (ConfigFieldType.DATE.equals(configField.getConfigFieldType())) {
            assertHelper.AssertText("fieldSize", "10");

            checkSqlInCodeMirror("btnDefSQL", configField.getConfigFieldDate().getDefValueSql());
        } else if (ConfigFieldType.CHECKBOX.equals(configField.getConfigFieldType())) {
            assertHelper.AssertText("fieldSize", "1");

            checkSqlInCodeMirror("btnDefSQL", configField.getConfigFieldCheckbox().getDefValueSql());
        } else if (ConfigFieldType.DROP_DOWN.equals(configField.getConfigFieldType())) {
            assertHelper.AssertRadioPsSelector("tableName", "btntableName", AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + 0L, configField.getConfigFieldDropDown().getTable(), 2L, true);

            checkSqlInCodeMirror("btnDefSQL", configField.getConfigFieldDropDown().getDefValueSql());
        } else if (ConfigFieldType.MEMO.equals(configField.getConfigFieldType())) {
            assertHelper.AssertText("fieldSize", configField.getConfigFieldMemo().getLength());
            assertHelper.AssertText("linesQty", configField.getConfigFieldMemo().getLines());

            checkSqlInCodeMirror("btnDefSQL", configField.getConfigFieldMemo().getDefValueSql());
        } else if (ConfigFieldType.WIKI.equals(configField.getConfigFieldType())) {
            assertHelper.AssertText("fieldSize", configField.getConfigFieldWiki().getLength());
            assertHelper.AssertText("linesQty", configField.getConfigFieldWiki().getLines());

            checkSqlInCodeMirror("btnDefSQL", configField.getConfigFieldWiki().getDefValueSql());
        } else if (ConfigFieldType.DB_DROP_DOWN.equals(configField.getConfigFieldType())) {
            if (!"XITOR_CLASS_ID".equals(configField.getName())) {
                checkSqlInCodeMirror("btnSQL", configField.getConfigFieldDbDropDown().getSql());
            }
            checkSqlInCodeMirror("btnDefSQL", configField.getConfigFieldDbDropDown().getDefValueSql());
        } else if (ConfigFieldType.DB_SELECTOR.equals(configField.getConfigFieldType())) {
            checkSqlInCodeMirror("btnSQL", configField.getConfigFieldDbSelector().getSql());
            checkSqlInCodeMirror("btnDefSQL", configField.getConfigFieldDbSelector().getDefValueSql());
        } else if (ConfigFieldType.SELECTOR.equals(configField.getConfigFieldType())) {
            assertHelper.AssertRadioPsSelector("tableName", "btntableName", AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + 0L, configField.getConfigFieldSelector().getTable(), 2L, true);

            checkSqlInCodeMirror("btnDefSQL", configField.getConfigFieldSelector().getDefValueSql());
        } else if (ConfigFieldType.LATITUDE.equals(configField.getConfigFieldType())) {
            assertHelper.AssertText("fieldSize", "15");

            checkSqlInCodeMirror("btnDefSQL", configField.getConfigFieldLatitude().getDefValueSql());
        } else if (ConfigFieldType.LONGITUDE.equals(configField.getConfigFieldType())) {
            assertHelper.AssertText("fieldSize", "15");

            checkSqlInCodeMirror("btnDefSQL", configField.getConfigFieldLongitude().getDefValueSql());
        } else if (ConfigFieldType.ELECTRONIC_FILE.equals(configField.getConfigFieldType())) {
            tabHelper.goToTab(2L); //Image Settings

            assertHelper.AssertCheckBoxNew("imageExtractMetadata", configField.getConfigFieldEfile().getExtractMetadata());
            assertHelper.AssertSelect("imageLatConfigFieldId", configField.getConfigFieldEfile().getImageLatitude());
            assertHelper.AssertSelect("imageLongConfigFieldId", configField.getConfigFieldEfile().getImageLongitude());
            assertHelper.AssertSelect("imageTimeConfigFieldId", configField.getConfigFieldEfile().getImageTimeSnapshot());
            assertHelper.AssertSelect("imageResizeMode", configField.getConfigFieldEfile().getResizeMode());
            assertHelper.AssertText("imageWidth", configField.getConfigFieldEfile().getResizeWidth());
            assertHelper.AssertText("imageHeight", configField.getConfigFieldEfile().getResizeHeight());
            assertHelper.AssertCheckBoxNew("imageRotate", configField.getConfigFieldEfile().getRotate());
            assertHelper.AssertCheckBoxNew("logBlobChanges", configField.getConfigFieldEfile().getLogBlobChanges());
            assertHelper.AssertCheckBoxNew("uploadToS3Directly", configField.getConfigFieldEfile().getUploadToAws());
            assertHelper.AssertRadioPsSelector("autocaptionClientFile", "btnautocaptionClientFile", AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + 0L, configField.getConfigFieldEfile().getAutocaptionTemplate(), 1L, true);

            tabHelper.goToTab(1L); //General
        } else if (ConfigFieldType.TRACKOR_SELECTOR.equals(configField.getConfigFieldType())) {
            checkSqlInCodeMirror("btnSQL", configField.getConfigFieldTrackorSelector().getSql());

            assertHelper.AssertSelect("ObjectTrackorType", configField.getConfigFieldTrackorSelector().getTrackorType());
            assertHelper.AssertText("shortNameLabel", configField.getConfigFieldTrackorSelector().getShortName());
            assertHelper.AssertCheckBoxNew("useInMyThingsFilter", configField.getConfigFieldTrackorSelector().getMyThingsFilter());
            assertHelper.AssertCheckBoxNew("myThingsMarket", configField.getConfigFieldTrackorSelector().getMyThingsMarker());

            assertHelper.AssertRadioPsSelector("objCf", "btnobjCf", AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + 0L, configField.getConfigFieldTrackorSelector().getDisplayField(), 1L, true);

            checkSqlInCodeMirror("btnDefSQL", configField.getConfigFieldTrackorSelector().getDefValueSql());
        } else if (ConfigFieldType.TRACKOR_DROP_DOWN.equals(configField.getConfigFieldType())) {
            checkSqlInCodeMirror("btnSQL", configField.getConfigFieldTrackorDropDown().getSql());

            assertHelper.AssertSelect("ObjectTrackorType", configField.getConfigFieldTrackorDropDown().getTrackorType());
            assertHelper.AssertText("shortNameLabel", configField.getConfigFieldTrackorDropDown().getShortName());
            assertHelper.AssertCheckBoxNew("useInMyThingsFilter", configField.getConfigFieldTrackorDropDown().getMyThingsFilter());
            assertHelper.AssertCheckBoxNew("myThingsMarket", configField.getConfigFieldTrackorDropDown().getMyThingsMarker());

            checkSqlInCodeMirror("btnDefSQL", configField.getConfigFieldTrackorDropDown().getDefValueSql());
        } else if (ConfigFieldType.CALCULATED.equals(configField.getConfigFieldType())) {
            checkSqlInCodeMirror("btnSQL", configField.getConfigFieldCalculated().getSql());
        } else if (ConfigFieldType.HYPERLINK.equals(configField.getConfigFieldType())) {
            assertHelper.AssertText("fieldSize", configField.getConfigFieldHyperlink().getLength());
        } else if (ConfigFieldType.ROLLUP.equals(configField.getConfigFieldType())) {
            //TODO filter

            assertHelper.AssertSelect("lstRollupXitorTypeID", configField.getConfigFieldRollup().getTrackorType());
        } else if (ConfigFieldType.MULTI_SELECTOR.equals(configField.getConfigFieldType())) {
            assertHelper.AssertText("linesQty", configField.getConfigFieldMultiSelector().getLines());

            assertHelper.AssertRadioPsSelector("tableName", "btntableName", AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + 0L, configField.getConfigFieldMultiSelector().getTable(), 2L, true);

            checkSqlInCodeMirror("btnDefSQL", configField.getConfigFieldMultiSelector().getDefValueSql());
        } else if (ConfigFieldType.DATE_TIME.equals(configField.getConfigFieldType())) {
            assertHelper.AssertText("fieldSize", "10");

            assertHelper.AssertCheckBoxNew("showSeconds", configField.getConfigFieldDateTime().getShowSeconds());

            checkSqlInCodeMirror("btnDefSQL", configField.getConfigFieldDateTime().getDefValueSql());
        } else if (ConfigFieldType.TIME.equals(configField.getConfigFieldType())) {
            assertHelper.AssertText("fieldSize", "10");

            assertHelper.AssertCheckBoxNew("showSeconds", configField.getConfigFieldTime().getShowSeconds());

            checkSqlInCodeMirror("btnDefSQL", configField.getConfigFieldTime().getDefValueSql());
        } else {
            throw new SeleniumUnexpectedException("Not support ConfigFieldType. ConfigFieldType=" + configField.getConfigFieldType());
        }

        assertHelper.AssertText("description", configField.getDescription());
        assertHelper.AssertText("comments", configField.getComment());

        assertHelper.AssertCheckBoxNew("mandatory", configField.getMandatory());
        assertHelper.AssertCheckBoxNew("twoColsSpan", configField.getTwoColumns());
        assertHelper.AssertCheckBoxNew("lockable", configField.getLockable());
        assertHelper.AssertCheckBoxNew("multiple", configField.getMultiple());

        assertHelper.AssertCheckBoxNew("readOnly", configField.getReadOnly());
        assertHelper.AssertCheckBoxNew("calcTotal", configField.getCalcTotal());
        assertHelper.AssertCheckBoxNew("notCloneFieldValue", configField.getNotCloneValue());
        assertHelper.AssertCheckBoxNew("notCloneLocks", configField.getNotCloneLock());

        windowHelper.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

    public void testInGrid(Long gridId, Long rowIndex, ConfigField configField) {
        Map<Long, String> gridVals = new HashMap<Long, String>();

        gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Field Name"), configField.getName());
        gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Field Label"), configField.getLabel());

        if (ConfigFieldType.CHECKBOX.equals(configField.getConfigFieldType())) {
            gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Max Length"), "1");
        } else if (ConfigFieldType.DATE.equals(configField.getConfigFieldType())) {
            gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Max Length"), "10");
        } else if (ConfigFieldType.DATE_TIME.equals(configField.getConfigFieldType())) {
            gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Max Length"), "10");
        } else if (ConfigFieldType.TIME.equals(configField.getConfigFieldType())) {
            gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Max Length"), "10");
        } else if (ConfigFieldType.LATITUDE.equals(configField.getConfigFieldType())) {
            gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Max Length"), "15");
        } else if (ConfigFieldType.LONGITUDE.equals(configField.getConfigFieldType())) {
            gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Max Length"), "15");
        } else if (ConfigFieldType.HYPERLINK.equals(configField.getConfigFieldType())) {
            gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Max Length"), configField.getConfigFieldHyperlink().getLength());
        } else if (ConfigFieldType.MEMO.equals(configField.getConfigFieldType())) {
            gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Max Length"), configField.getConfigFieldMemo().getLength());
        } else if (ConfigFieldType.WIKI.equals(configField.getConfigFieldType())) {
            gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Max Length"), configField.getConfigFieldWiki().getLength());
        } else if (ConfigFieldType.TEXT.equals(configField.getConfigFieldType())) {
            gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Max Length"), configField.getConfigFieldText().getLength());
        } else if (ConfigFieldType.NUMBER.equals(configField.getConfigFieldType())) {
            gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Max Length"), configField.getConfigFieldNumber().getLength());
        } else {
            gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Max Length"), "");
        }

        if (ConfigFieldType.DROP_DOWN.equals(configField.getConfigFieldType())) {
            gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Table Name"), configField.getConfigFieldDropDown().getTable());
        } else if (ConfigFieldType.SELECTOR.equals(configField.getConfigFieldType())) {
            gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Table Name"), configField.getConfigFieldSelector().getTable());
        } else if (ConfigFieldType.MULTI_SELECTOR.equals(configField.getConfigFieldType())) {
            gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Table Name"), configField.getConfigFieldMultiSelector().getTable());
        } else {
            gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Table Name"), "");
        }

        gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Data Type"), configField.getConfigFieldType().getLabel());

        if (ConfigFieldType.MEMO.equals(configField.getConfigFieldType())) {
            gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Lines Qty"), configField.getConfigFieldMemo().getLines());
        } else if (ConfigFieldType.WIKI.equals(configField.getConfigFieldType())) {
            gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Lines Qty"), configField.getConfigFieldWiki().getLines());
        } else if (ConfigFieldType.MULTI_SELECTOR.equals(configField.getConfigFieldType())) {
            gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Lines Qty"), configField.getConfigFieldMultiSelector().getLines());
        } else {
            gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Lines Qty"), "");
        }

        gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Field Width (px)"), configField.getWidth());
        gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Description"), configField.getDescription());
        gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "TRACKOR Type"), configField.getTrackorTypeLabel());
        gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Multiple Lines"), configField.getMultiple());
        gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Read Only"), configField.getReadOnly());

        if (ConfigFieldType.TRACKOR_SELECTOR.equals(configField.getConfigFieldType())) {
            gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Selector's Trackor Type"), configField.getConfigFieldTrackorSelector().getTrackorType());
            gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Short Name"), configField.getConfigFieldTrackorSelector().getShortName());
            gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Use in \"My Things\" filter"), configField.getConfigFieldTrackorSelector().getMyThingsFilter());
        } else if (ConfigFieldType.TRACKOR_DROP_DOWN.equals(configField.getConfigFieldType())) {
            gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Selector's Trackor Type"), configField.getConfigFieldTrackorDropDown().getTrackorType());
            gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Short Name"), configField.getConfigFieldTrackorDropDown().getShortName());
            gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Use in \"My Things\" filter"), configField.getConfigFieldTrackorDropDown().getMyThingsFilter());
        } else if (ConfigFieldType.ROLLUP.equals(configField.getConfigFieldType())) {
            gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Selector's Trackor Type"), configField.getConfigFieldRollup().getTrackorType());
            gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Short Name"), "");
            gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Use in \"My Things\" filter"), "NO");
        } else {
            gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Selector's Trackor Type"), "");
            gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Short Name"), "");
            gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Use in \"My Things\" filter"), "NO");
        }

        if (ConfigFieldType.ELECTRONIC_FILE.equals(configField.getConfigFieldType())) {
            gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Log BLOB changes"), configField.getConfigFieldEfile().getLogBlobChanges());
        } else {
            gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Log BLOB changes"), "NO");
        }

        gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Mandatory"), configField.getMandatory());
        gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Two Columns Span"), configField.getTwoColumns());
        gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Lockable"), configField.getLockable());

        if (ConfigFieldType.CALCULATED.equals(configField.getConfigFieldType())) {
            gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "SQL Query"), configField.getConfigFieldCalculated().getSql());
        } else if (ConfigFieldType.DB_DROP_DOWN.equals(configField.getConfigFieldType())) {
            gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "SQL Query"), configField.getConfigFieldDbDropDown().getSql());
        } else if (ConfigFieldType.DB_SELECTOR.equals(configField.getConfigFieldType())) {
            gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "SQL Query"), configField.getConfigFieldDbSelector().getSql());
        } else if (ConfigFieldType.TRACKOR_DROP_DOWN.equals(configField.getConfigFieldType())) {
            gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "SQL Query"), configField.getConfigFieldTrackorDropDown().getSql());
        } else if (ConfigFieldType.TRACKOR_SELECTOR.equals(configField.getConfigFieldType())) {
            gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "SQL Query"), configField.getConfigFieldTrackorSelector().getSql());
        } else {
            gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "SQL Query"), "");
        }

        if (ConfigFieldType.TRACKOR_SELECTOR.equals(configField.getConfigFieldType())) {
            gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Selector's Display Field"), configField.getConfigFieldTrackorSelector().getDisplayField());
        } else {
            gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Selector's Display Field"), "");
        }

        gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Calc Total"), configField.getCalcTotal());
        gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Comments"), configField.getComment());
        gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Don't Clone Field Value"), configField.getNotCloneValue());

        gridHelper.checkGridRowByRowIndexAndColIndex(gridId, rowIndex, gridVals);
    }

    private void setSqlToCodeMirror(String btnId, String sql) {
        windowHelper.openModal(By.id(btnId));
        waitHelper.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        waitHelper.waitFormLoad();
        waitHelper.waitCodeMirrorLoad("SQL");
        waitHelper.waitCodeMirrorHistorySize("SQL", 1L, 0L); //Wait until CodeMirror value is populated from window.dialogArguments['SQL'] js variable
        jsHelper.setValueToCodeMirror("SQL", sql);
        windowHelper.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
    }

    private void checkSqlInCodeMirror(String btnId, String sql) {
        windowHelper.openModal(By.id(btnId));
        waitHelper.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
        waitHelper.waitFormLoad();
        waitHelper.waitCodeMirrorLoad("SQL");
        waitHelper.waitCodeMirrorHistorySize("SQL", 1L, 0L); //Wait until CodeMirror value is populated from window.dialogArguments['SQL'] js variable
        assertHelper.AssertCodeMirror("SQL", sql);
        windowHelper.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

}