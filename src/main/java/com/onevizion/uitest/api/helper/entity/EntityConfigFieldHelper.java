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
import com.onevizion.uitest.api.helper.configfield.ConfigField;
import com.onevizion.uitest.api.helper.jquery.JqueryWait;
import com.onevizion.uitest.api.vo.ConfigFieldType;
import com.onevizion.uitest.api.vo.entity.ConfigFieldVo;

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
    private ConfigField configField;

    @Resource
    private CheckboxHelper checkboxHelper;

    @Resource
    private PsSelectorHelper psSelectorHelper;

    @Resource
    private TabHelper tabHelper;

    @Resource
    private ElementHelper elementHelper;

    public void add(ConfigFieldVo configFieldVo) {
        windowHelper.openModal(By.id(AbstractSeleniumCore.BUTTON_ADD_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        waitHelper.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        waitHelper.waitFormLoad();

        if (ConfigFieldType.ROLLUP.equals(configFieldVo.getConfigFieldType())) {
            jqueryWait.waitJQueryLoad();
        }

        new Select(seleniumSettings.getWebDriver().findElement(By.name("TrackorTypeName"))).selectByVisibleText(configFieldVo.getTrackorTypeLabel());

        new Select(seleniumSettings.getWebDriver().findElement(By.name("dataType"))).selectByVisibleText(configFieldVo.getConfigFieldType().getLabel());

        seleniumSettings.getWebDriver().findElement(By.name("LabelName")).sendKeys(configFieldVo.getLabel());

        String val = new Select(seleniumSettings.getWebDriver().findElement(By.name("TrackorTypeName"))).getFirstSelectedOption().getAttribute("value");
        String cfPrefix = val.split(";")[1].replace(":", "").replace("-", "").replace(".", "");
        elementWaitHelper.waitElementAttributeByName("configFieldName", "value", cfPrefix + "_" + configFieldVo.getLabel().replace(" ", "_").replace("/", "").replace("-", "").toUpperCase());
        configField.waitFieldNameUpdated();

        seleniumSettings.getWebDriver().findElement(By.name("configFieldName")).clear();
        seleniumSettings.getWebDriver().findElement(By.name("configFieldName")).sendKeys(configFieldVo.getName());

        new Select(seleniumSettings.getWebDriver().findElement(By.name("componentsPackageId"))).selectByVisibleText(configFieldVo.getCompPackage());

        new Select(seleniumSettings.getWebDriver().findElement(By.name("colorId"))).selectByVisibleText(configFieldVo.getColor());

        seleniumSettings.getWebDriver().findElement(By.name("fieldWidth")).clear();
        seleniumSettings.getWebDriver().findElement(By.name("fieldWidth")).sendKeys(configFieldVo.getWidth());

        if (ConfigFieldType.TEXT.equals(configFieldVo.getConfigFieldType())) {
            seleniumSettings.getWebDriver().findElement(By.name("fieldSize")).clear();
            seleniumSettings.getWebDriver().findElement(By.name("fieldSize")).sendKeys(configFieldVo.getConfigFieldText().getLength());

            setSqlToCodeMirror("btnDefSQL", configFieldVo.getConfigFieldText().getDefValueSql());
        } else if (ConfigFieldType.NUMBER.equals(configFieldVo.getConfigFieldType())) {
            seleniumSettings.getWebDriver().findElement(By.name("fieldSize")).clear();
            seleniumSettings.getWebDriver().findElement(By.name("fieldSize")).sendKeys(configFieldVo.getConfigFieldNumber().getLength());

            seleniumSettings.getWebDriver().findElement(By.name("prefix")).clear();
            seleniumSettings.getWebDriver().findElement(By.name("prefix")).sendKeys(configFieldVo.getConfigFieldNumber().getPrefix());
            seleniumSettings.getWebDriver().findElement(By.name("suffix")).clear();
            seleniumSettings.getWebDriver().findElement(By.name("suffix")).sendKeys(configFieldVo.getConfigFieldNumber().getSuffix());
            seleniumSettings.getWebDriver().findElement(By.name("numDecimals")).clear();
            seleniumSettings.getWebDriver().findElement(By.name("numDecimals")).sendKeys(configFieldVo.getConfigFieldNumber().getDecimal());
            windowHelper.openModal(By.name("btnrgbNegColor"));
            seleniumSettings.getWebDriver().findElement(By.className("dhxcp_color_selector")).click();
            windowHelper.closeModal(By.className("dhx_button_save"));
            windowHelper.openModal(By.name("btnrgbPosColor"));
            seleniumSettings.getWebDriver().findElement(By.className("dhxcp_color_selector")).click();
            windowHelper.closeModal(By.className("dhx_button_save"));

            if ((configFieldVo.getConfigFieldNumber().getParensForNegative().equals("YES") && !checkboxHelper.isCheckedByName("parensForNegative"))
                    || (configFieldVo.getConfigFieldNumber().getParensForNegative().equals("NO") && checkboxHelper.isCheckedByName("parensForNegative"))) {
                checkboxHelper.clickByName("parensForNegative");
            }
            if ((configFieldVo.getConfigFieldNumber().getSeparateThousands().equals("YES") && !checkboxHelper.isCheckedByName("separateThousands"))
                    || (configFieldVo.getConfigFieldNumber().getSeparateThousands().equals("NO") && checkboxHelper.isCheckedByName("separateThousands"))) {
                checkboxHelper.clickByName("separateThousands");
            }

            setSqlToCodeMirror("btnDefSQL", configFieldVo.getConfigFieldNumber().getDefValueSql());
        } else if (ConfigFieldType.DATE.equals(configFieldVo.getConfigFieldType())) {
            assertHelper.AssertText("fieldSize", "10");

            setSqlToCodeMirror("btnDefSQL", configFieldVo.getConfigFieldDate().getDefValueSql());
        } else if (ConfigFieldType.CHECKBOX.equals(configFieldVo.getConfigFieldType())) {
            assertHelper.AssertText("fieldSize", "1");

            setSqlToCodeMirror("btnDefSQL", configFieldVo.getConfigFieldCheckbox().getDefValueSql());
        } else if (ConfigFieldType.DROP_DOWN.equals(configFieldVo.getConfigFieldType())) {
            psSelectorHelper.selectSpecificValue(By.id("btntableName"), By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE + AbstractSeleniumCore.getGridIdx()), 3L, configFieldVo.getConfigFieldDropDown().getTable(), 2L);

            setSqlToCodeMirror("btnDefSQL", configFieldVo.getConfigFieldDropDown().getDefValueSql());
        } else if (ConfigFieldType.MEMO.equals(configFieldVo.getConfigFieldType())) {
            seleniumSettings.getWebDriver().findElement(By.name("fieldSize")).clear();
            seleniumSettings.getWebDriver().findElement(By.name("fieldSize")).sendKeys(configFieldVo.getConfigFieldMemo().getLength());
            seleniumSettings.getWebDriver().findElement(By.name("linesQty")).clear();
            seleniumSettings.getWebDriver().findElement(By.name("linesQty")).sendKeys(configFieldVo.getConfigFieldMemo().getLines());

            setSqlToCodeMirror("btnDefSQL", configFieldVo.getConfigFieldMemo().getDefValueSql());
        } else if (ConfigFieldType.WIKI.equals(configFieldVo.getConfigFieldType())) {
            seleniumSettings.getWebDriver().findElement(By.name("fieldSize")).clear();
            seleniumSettings.getWebDriver().findElement(By.name("fieldSize")).sendKeys(configFieldVo.getConfigFieldWiki().getLength());
            seleniumSettings.getWebDriver().findElement(By.name("linesQty")).clear();
            seleniumSettings.getWebDriver().findElement(By.name("linesQty")).sendKeys(configFieldVo.getConfigFieldWiki().getLines());

            setSqlToCodeMirror("btnDefSQL", configFieldVo.getConfigFieldWiki().getDefValueSql());
        } else if (ConfigFieldType.DB_DROP_DOWN.equals(configFieldVo.getConfigFieldType())) {
            setSqlToCodeMirror("btnSQL", configFieldVo.getConfigFieldDbDropDown().getSql());
            setSqlToCodeMirror("btnDefSQL", configFieldVo.getConfigFieldDbDropDown().getDefValueSql());
        } else if (ConfigFieldType.DB_SELECTOR.equals(configFieldVo.getConfigFieldType())) {
            setSqlToCodeMirror("btnSQL", configFieldVo.getConfigFieldDbSelector().getSql());
            setSqlToCodeMirror("btnDefSQL", configFieldVo.getConfigFieldDbSelector().getDefValueSql());
        } else if (ConfigFieldType.SELECTOR.equals(configFieldVo.getConfigFieldType())) {
            psSelectorHelper.selectSpecificValue(By.id("btntableName"), By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE + AbstractSeleniumCore.getGridIdx()), 3L, configFieldVo.getConfigFieldSelector().getTable(), 2L);

            setSqlToCodeMirror("btnDefSQL", configFieldVo.getConfigFieldSelector().getDefValueSql());
        } else if (ConfigFieldType.LATITUDE.equals(configFieldVo.getConfigFieldType())) {
            assertHelper.AssertText("fieldSize", "15");

            setSqlToCodeMirror("btnDefSQL", configFieldVo.getConfigFieldLatitude().getDefValueSql());
        } else if (ConfigFieldType.LONGITUDE.equals(configFieldVo.getConfigFieldType())) {
            assertHelper.AssertText("fieldSize", "15");

            setSqlToCodeMirror("btnDefSQL", configFieldVo.getConfigFieldLongitude().getDefValueSql());
        } else if (ConfigFieldType.ELECTRONIC_FILE.equals(configFieldVo.getConfigFieldType())) {
            elementHelper.clickById(AbstractSeleniumCore.BUTTON_APPLY_ID);
            waitHelper.waitReloadForm("reloaded=1");
            waitHelper.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
            waitHelper.waitFormLoad();

            tabHelper.goToTab(2L); //Image Settings

            if ((configFieldVo.getConfigFieldEfile().getExtractMetadata().equals("YES") && !checkboxHelper.isCheckedByName("imageExtractMetadata"))
                    || (configFieldVo.getConfigFieldEfile().getExtractMetadata().equals("NO") && checkboxHelper.isCheckedByName("imageExtractMetadata"))) {
                checkboxHelper.clickByName("imageExtractMetadata");
            }
            new Select(seleniumSettings.getWebDriver().findElement(By.name("imageLatConfigFieldId"))).selectByVisibleText(configFieldVo.getConfigFieldEfile().getImageLatitude());
            new Select(seleniumSettings.getWebDriver().findElement(By.name("imageLongConfigFieldId"))).selectByVisibleText(configFieldVo.getConfigFieldEfile().getImageLongitude());
            new Select(seleniumSettings.getWebDriver().findElement(By.name("imageTimeConfigFieldId"))).selectByVisibleText(configFieldVo.getConfigFieldEfile().getImageTimeSnapshot());
            new Select(seleniumSettings.getWebDriver().findElement(By.name("imageResizeMode"))).selectByVisibleText(configFieldVo.getConfigFieldEfile().getResizeMode());
            seleniumSettings.getWebDriver().findElement(By.name("imageWidth")).clear();
            seleniumSettings.getWebDriver().findElement(By.name("imageWidth")).sendKeys(configFieldVo.getConfigFieldEfile().getResizeWidth());
            if (!configFieldVo.getConfigFieldEfile().getResizeHeight().equals(seleniumSettings.getWebDriver().findElement(By.name("imageHeight")).getAttribute("value"))) {
                seleniumSettings.getWebDriver().findElement(By.name("imageHeight")).clear();
                seleniumSettings.getWebDriver().findElement(By.name("imageHeight")).sendKeys(configFieldVo.getConfigFieldEfile().getResizeHeight());
            }
            if ((configFieldVo.getConfigFieldEfile().getRotate().equals("YES") && !checkboxHelper.isCheckedByName("imageRotate"))
                    || (configFieldVo.getConfigFieldEfile().getRotate().equals("NO") && checkboxHelper.isCheckedByName("imageRotate"))) {
                checkboxHelper.clickByName("imageRotate");
            }
            if ((configFieldVo.getConfigFieldEfile().getLogBlobChanges().equals("YES") && !checkboxHelper.isCheckedByName("logBlobChanges"))
                    || (configFieldVo.getConfigFieldEfile().getLogBlobChanges().equals("NO") && checkboxHelper.isCheckedByName("logBlobChanges"))) {
                checkboxHelper.clickByName("logBlobChanges");
            }
            if ((configFieldVo.getConfigFieldEfile().getUploadToAws().equals("YES") && !checkboxHelper.isCheckedByName("uploadToS3Directly"))
                    || (configFieldVo.getConfigFieldEfile().getUploadToAws().equals("NO") && checkboxHelper.isCheckedByName("uploadToS3Directly"))) {
                checkboxHelper.clickByName("uploadToS3Directly");
            }
            psSelectorHelper.selectSpecificValue(By.id("btnautocaptionClientFile"), By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE + AbstractSeleniumCore.getGridIdx()), 1L, configFieldVo.getConfigFieldEfile().getAutocaptionTemplate(), 1L);

            tabHelper.goToTab(1L); //General
        } else if (ConfigFieldType.TRACKOR_SELECTOR.equals(configFieldVo.getConfigFieldType())) {
            setSqlToCodeMirror("btnSQL", configFieldVo.getConfigFieldTrackorSelector().getSql());

            new Select(seleniumSettings.getWebDriver().findElement(By.name("ObjectTrackorType"))).selectByVisibleText(configFieldVo.getConfigFieldTrackorSelector().getTrackorType());
            seleniumSettings.getWebDriver().findElement(By.name("shortNameLabel")).clear();
            seleniumSettings.getWebDriver().findElement(By.name("shortNameLabel")).sendKeys(configFieldVo.getConfigFieldTrackorSelector().getShortName());
            if ((configFieldVo.getConfigFieldTrackorSelector().getMyThingsFilter().equals("YES") && !checkboxHelper.isCheckedByName("useInMyThingsFilter"))
                    || (configFieldVo.getConfigFieldTrackorSelector().getMyThingsFilter().equals("NO") && checkboxHelper.isCheckedByName("useInMyThingsFilter"))) {
                checkboxHelper.clickByName("useInMyThingsFilter");
            }
            if ((configFieldVo.getConfigFieldTrackorSelector().getMyThingsMarker().equals("YES") && !checkboxHelper.isCheckedByName("myThingsMarket"))
                    || (configFieldVo.getConfigFieldTrackorSelector().getMyThingsMarker().equals("NO") && checkboxHelper.isCheckedByName("myThingsMarket"))) {
                checkboxHelper.clickByName("myThingsMarket");
            }

            psSelectorHelper.selectSpecificValue(By.id("btnobjCf"), By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE + AbstractSeleniumCore.getGridIdx()), 1L, configFieldVo.getConfigFieldTrackorSelector().getDisplayField(), 1L);

            setSqlToCodeMirror("btnDefSQL", configFieldVo.getConfigFieldTrackorSelector().getDefValueSql());
        } else if (ConfigFieldType.TRACKOR_DROP_DOWN.equals(configFieldVo.getConfigFieldType())) {
            setSqlToCodeMirror("btnSQL", configFieldVo.getConfigFieldTrackorDropDown().getSql());

            new Select(seleniumSettings.getWebDriver().findElement(By.name("ObjectTrackorType"))).selectByVisibleText(configFieldVo.getConfigFieldTrackorDropDown().getTrackorType());
            seleniumSettings.getWebDriver().findElement(By.name("shortNameLabel")).clear();
            seleniumSettings.getWebDriver().findElement(By.name("shortNameLabel")).sendKeys(configFieldVo.getConfigFieldTrackorDropDown().getShortName());
            if ((configFieldVo.getConfigFieldTrackorDropDown().getMyThingsFilter().equals("YES") && !checkboxHelper.isCheckedByName("useInMyThingsFilter"))
                    || (configFieldVo.getConfigFieldTrackorDropDown().getMyThingsFilter().equals("NO") && checkboxHelper.isCheckedByName("useInMyThingsFilter"))) {
                checkboxHelper.clickByName("useInMyThingsFilter");
            }
            if ((configFieldVo.getConfigFieldTrackorDropDown().getMyThingsMarker().equals("YES") && !checkboxHelper.isCheckedByName("myThingsMarket"))
                    || (configFieldVo.getConfigFieldTrackorDropDown().getMyThingsMarker().equals("NO") && checkboxHelper.isCheckedByName("myThingsMarket"))) {
                checkboxHelper.clickByName("myThingsMarket");
            }

            setSqlToCodeMirror("btnDefSQL", configFieldVo.getConfigFieldTrackorDropDown().getDefValueSql());
        } else if (ConfigFieldType.CALCULATED.equals(configFieldVo.getConfigFieldType())) {
            setSqlToCodeMirror("btnSQL", configFieldVo.getConfigFieldCalculated().getSql());
        } else if (ConfigFieldType.HYPERLINK.equals(configFieldVo.getConfigFieldType())) {
            seleniumSettings.getWebDriver().findElement(By.name("fieldSize")).clear();
            seleniumSettings.getWebDriver().findElement(By.name("fieldSize")).sendKeys(configFieldVo.getConfigFieldHyperlink().getLength());
        } else if (ConfigFieldType.ROLLUP.equals(configFieldVo.getConfigFieldType())) {
            //TODO filter

            new Select(seleniumSettings.getWebDriver().findElement(By.name("lstRollupXitorTypeID"))).selectByVisibleText(configFieldVo.getConfigFieldRollup().getTrackorType());
        } else if (ConfigFieldType.MULTI_SELECTOR.equals(configFieldVo.getConfigFieldType())) {
            seleniumSettings.getWebDriver().findElement(By.name("linesQty")).clear();
            seleniumSettings.getWebDriver().findElement(By.name("linesQty")).sendKeys(configFieldVo.getConfigFieldMultiSelector().getLines());

            psSelectorHelper.selectSpecificValue(By.id("btntableName"), By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE + AbstractSeleniumCore.getGridIdx()), 3L, configFieldVo.getConfigFieldMultiSelector().getTable(), 2L);

            setSqlToCodeMirror("btnDefSQL", configFieldVo.getConfigFieldMultiSelector().getDefValueSql());
        } else if (ConfigFieldType.DATE_TIME.equals(configFieldVo.getConfigFieldType())) {
            assertHelper.AssertText("fieldSize", "10");

            if ((configFieldVo.getConfigFieldDateTime().getShowSeconds().equals("YES") && !checkboxHelper.isCheckedByName("showSeconds"))
                    || (configFieldVo.getConfigFieldDateTime().getShowSeconds().equals("NO") && checkboxHelper.isCheckedByName("showSeconds"))) {
                checkboxHelper.clickByName("showSeconds");
            }

            setSqlToCodeMirror("btnDefSQL", configFieldVo.getConfigFieldDateTime().getDefValueSql());
        } else if (ConfigFieldType.TIME.equals(configFieldVo.getConfigFieldType())) {
            assertHelper.AssertText("fieldSize", "10");

            if ((configFieldVo.getConfigFieldTime().getShowSeconds().equals("YES") && !checkboxHelper.isCheckedByName("showSeconds"))
                    || (configFieldVo.getConfigFieldTime().getShowSeconds().equals("NO") && checkboxHelper.isCheckedByName("showSeconds"))) {
                checkboxHelper.clickByName("showSeconds");
            }

            setSqlToCodeMirror("btnDefSQL", configFieldVo.getConfigFieldTime().getDefValueSql());
        } else {
            throw new SeleniumUnexpectedException("Not support ConfigFieldType. ConfigFieldType=" + configFieldVo.getConfigFieldType());
        }

        seleniumSettings.getWebDriver().findElement(By.name("description")).sendKeys(configFieldVo.getDescription());

        seleniumSettings.getWebDriver().findElement(By.name("comments")).sendKeys(configFieldVo.getComment());

        if ((configFieldVo.getMandatory().equals("YES") && !checkboxHelper.isCheckedByName("mandatory"))
                || (configFieldVo.getMandatory().equals("NO") && checkboxHelper.isCheckedByName("mandatory"))) {
            checkboxHelper.clickByName("mandatory");
        }

        if ((configFieldVo.getTwoColumns().equals("YES") && !checkboxHelper.isCheckedByName("twoColsSpan"))
                || (configFieldVo.getTwoColumns().equals("NO") && checkboxHelper.isCheckedByName("twoColsSpan"))) {
            checkboxHelper.clickByName("twoColsSpan");
        }

        if ((configFieldVo.getLockable().equals("YES") && !checkboxHelper.isCheckedByName("lockable"))
                || (configFieldVo.getLockable().equals("NO") && checkboxHelper.isCheckedByName("lockable"))) {
            checkboxHelper.clickByName("lockable");
        }

        if ((configFieldVo.getMultiple().equals("YES") && !checkboxHelper.isCheckedByName("multiple"))
                || (configFieldVo.getMultiple().equals("NO") && checkboxHelper.isCheckedByName("multiple"))) {
            checkboxHelper.clickByName("multiple");
        }

        if ((configFieldVo.getReadOnly().equals("YES") && !checkboxHelper.isCheckedByName("readOnly"))
                || (configFieldVo.getReadOnly().equals("NO") && checkboxHelper.isCheckedByName("readOnly"))) {
            checkboxHelper.clickByName("readOnly");
        }

        if ((configFieldVo.getCalcTotal().equals("YES") && !checkboxHelper.isCheckedByName("calcTotal"))
                || (configFieldVo.getCalcTotal().equals("NO") && checkboxHelper.isCheckedByName("calcTotal"))) {
            checkboxHelper.clickByName("calcTotal");
        }

        if ((configFieldVo.getNotCloneValue().equals("YES") && !checkboxHelper.isCheckedByName("notCloneFieldValue"))
                || (configFieldVo.getNotCloneValue().equals("NO") && checkboxHelper.isCheckedByName("notCloneFieldValue"))) {
            checkboxHelper.clickByName("notCloneFieldValue");
        }

        if ((configFieldVo.getNotCloneLock().equals("YES") && !checkboxHelper.isCheckedByName("notCloneLocks"))
                || (configFieldVo.getNotCloneLock().equals("NO") && checkboxHelper.isCheckedByName("notCloneLocks"))) {
            checkboxHelper.clickByName("notCloneLocks");
        }

        windowHelper.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        waitHelper.waitGridLoad(AbstractSeleniumCore.getGridIdx(), AbstractSeleniumCore.getGridIdx());
    }

    public void edit(ConfigFieldVo configFieldVo) {
        windowHelper.openModal(By.id(AbstractSeleniumCore.BUTTON_EDIT_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        waitHelper.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        waitHelper.waitFormLoad();

        if (ConfigFieldType.ROLLUP.equals(configFieldVo.getConfigFieldType())) {
            jqueryWait.waitJQueryLoad();
        }

        seleniumSettings.getWebDriver().findElement(By.name("LabelName")).clear();
        seleniumSettings.getWebDriver().findElement(By.name("LabelName")).sendKeys(configFieldVo.getLabel());

        seleniumSettings.getWebDriver().findElement(By.name("configFieldName")).clear();
        seleniumSettings.getWebDriver().findElement(By.name("configFieldName")).sendKeys(configFieldVo.getName());

        new Select(seleniumSettings.getWebDriver().findElement(By.name("componentsPackageId"))).selectByVisibleText(configFieldVo.getCompPackage());

        new Select(seleniumSettings.getWebDriver().findElement(By.name("colorId"))).selectByVisibleText(configFieldVo.getColor());

        seleniumSettings.getWebDriver().findElement(By.name("fieldWidth")).clear();
        seleniumSettings.getWebDriver().findElement(By.name("fieldWidth")).sendKeys(configFieldVo.getWidth());

        if (ConfigFieldType.TEXT.equals(configFieldVo.getConfigFieldType())) {
            seleniumSettings.getWebDriver().findElement(By.name("fieldSize")).clear();
            seleniumSettings.getWebDriver().findElement(By.name("fieldSize")).sendKeys(configFieldVo.getConfigFieldText().getLength());

            setSqlToCodeMirror("btnDefSQL", configFieldVo.getConfigFieldText().getDefValueSql());
        } else if (ConfigFieldType.NUMBER.equals(configFieldVo.getConfigFieldType())) {
            seleniumSettings.getWebDriver().findElement(By.name("fieldSize")).clear();
            seleniumSettings.getWebDriver().findElement(By.name("fieldSize")).sendKeys(configFieldVo.getConfigFieldNumber().getLength());

            seleniumSettings.getWebDriver().findElement(By.name("prefix")).clear();
            seleniumSettings.getWebDriver().findElement(By.name("prefix")).sendKeys(configFieldVo.getConfigFieldNumber().getPrefix());
            seleniumSettings.getWebDriver().findElement(By.name("suffix")).clear();
            seleniumSettings.getWebDriver().findElement(By.name("suffix")).sendKeys(configFieldVo.getConfigFieldNumber().getSuffix());
            seleniumSettings.getWebDriver().findElement(By.name("numDecimals")).clear();
            seleniumSettings.getWebDriver().findElement(By.name("numDecimals")).sendKeys(configFieldVo.getConfigFieldNumber().getDecimal());
            windowHelper.openModal(By.name("btnrgbNegColor"));
            seleniumSettings.getWebDriver().findElement(By.className("dhxcp_color_selector")).click();
            windowHelper.closeModal(By.className("dhx_button_save"));
            windowHelper.openModal(By.name("btnrgbPosColor"));
            seleniumSettings.getWebDriver().findElement(By.className("dhxcp_color_selector")).click();
            windowHelper.closeModal(By.className("dhx_button_save"));

            if ((configFieldVo.getConfigFieldNumber().getParensForNegative().equals("YES") && !checkboxHelper.isCheckedByName("parensForNegative"))
                    || (configFieldVo.getConfigFieldNumber().getParensForNegative().equals("NO") && checkboxHelper.isCheckedByName("parensForNegative"))) {
                checkboxHelper.clickByName("parensForNegative");
            }
            if ((configFieldVo.getConfigFieldNumber().getSeparateThousands().equals("YES") && !checkboxHelper.isCheckedByName("separateThousands"))
                    || (configFieldVo.getConfigFieldNumber().getSeparateThousands().equals("NO") && checkboxHelper.isCheckedByName("separateThousands"))) {
                checkboxHelper.clickByName("separateThousands");
            }

            setSqlToCodeMirror("btnDefSQL", configFieldVo.getConfigFieldNumber().getDefValueSql());
        } else if (ConfigFieldType.DATE.equals(configFieldVo.getConfigFieldType())) {
            assertHelper.AssertText("fieldSize", "10");

            setSqlToCodeMirror("btnDefSQL", configFieldVo.getConfigFieldDate().getDefValueSql());
        } else if (ConfigFieldType.CHECKBOX.equals(configFieldVo.getConfigFieldType())) {
            assertHelper.AssertText("fieldSize", "1");

            setSqlToCodeMirror("btnDefSQL", configFieldVo.getConfigFieldCheckbox().getDefValueSql());
        } else if (ConfigFieldType.DROP_DOWN.equals(configFieldVo.getConfigFieldType())) {
            psSelectorHelper.selectSpecificValue(By.id("btntableName"), By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE + AbstractSeleniumCore.getGridIdx()), 3L, configFieldVo.getConfigFieldDropDown().getTable(), 2L);

            setSqlToCodeMirror("btnDefSQL", configFieldVo.getConfigFieldDropDown().getDefValueSql());
        } else if (ConfigFieldType.MEMO.equals(configFieldVo.getConfigFieldType())) {
            seleniumSettings.getWebDriver().findElement(By.name("fieldSize")).clear();
            seleniumSettings.getWebDriver().findElement(By.name("fieldSize")).sendKeys(configFieldVo.getConfigFieldMemo().getLength());
            seleniumSettings.getWebDriver().findElement(By.name("linesQty")).clear();
            seleniumSettings.getWebDriver().findElement(By.name("linesQty")).sendKeys(configFieldVo.getConfigFieldMemo().getLines());

            setSqlToCodeMirror("btnDefSQL", configFieldVo.getConfigFieldMemo().getDefValueSql());
        } else if (ConfigFieldType.WIKI.equals(configFieldVo.getConfigFieldType())) {
            seleniumSettings.getWebDriver().findElement(By.name("fieldSize")).clear();
            seleniumSettings.getWebDriver().findElement(By.name("fieldSize")).sendKeys(configFieldVo.getConfigFieldWiki().getLength());
            seleniumSettings.getWebDriver().findElement(By.name("linesQty")).clear();
            seleniumSettings.getWebDriver().findElement(By.name("linesQty")).sendKeys(configFieldVo.getConfigFieldWiki().getLines());

            setSqlToCodeMirror("btnDefSQL", configFieldVo.getConfigFieldWiki().getDefValueSql());
        } else if (ConfigFieldType.DB_DROP_DOWN.equals(configFieldVo.getConfigFieldType())) {
            setSqlToCodeMirror("btnSQL", configFieldVo.getConfigFieldDbDropDown().getSql());
            setSqlToCodeMirror("btnDefSQL", configFieldVo.getConfigFieldDbDropDown().getDefValueSql());
        } else if (ConfigFieldType.DB_SELECTOR.equals(configFieldVo.getConfigFieldType())) {
            setSqlToCodeMirror("btnSQL", configFieldVo.getConfigFieldDbSelector().getSql());
            setSqlToCodeMirror("btnDefSQL", configFieldVo.getConfigFieldDbSelector().getDefValueSql());
        } else if (ConfigFieldType.SELECTOR.equals(configFieldVo.getConfigFieldType())) {
            psSelectorHelper.selectSpecificValue(By.id("btntableName"), By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE + AbstractSeleniumCore.getGridIdx()), 3L, configFieldVo.getConfigFieldSelector().getTable(), 2L);

            setSqlToCodeMirror("btnDefSQL", configFieldVo.getConfigFieldSelector().getDefValueSql());
        } else if (ConfigFieldType.LATITUDE.equals(configFieldVo.getConfigFieldType())) {
            assertHelper.AssertText("fieldSize", "15");

            setSqlToCodeMirror("btnDefSQL", configFieldVo.getConfigFieldLatitude().getDefValueSql());
        } else if (ConfigFieldType.LONGITUDE.equals(configFieldVo.getConfigFieldType())) {
            assertHelper.AssertText("fieldSize", "15");

            setSqlToCodeMirror("btnDefSQL", configFieldVo.getConfigFieldLongitude().getDefValueSql());
        } else if (ConfigFieldType.ELECTRONIC_FILE.equals(configFieldVo.getConfigFieldType())) {
            tabHelper.goToTab(2L); //Image Settings

            if ((configFieldVo.getConfigFieldEfile().getExtractMetadata().equals("YES") && !checkboxHelper.isCheckedByName("imageExtractMetadata"))
                    || (configFieldVo.getConfigFieldEfile().getExtractMetadata().equals("NO") && checkboxHelper.isCheckedByName("imageExtractMetadata"))) {
                checkboxHelper.clickByName("imageExtractMetadata");
            }
            new Select(seleniumSettings.getWebDriver().findElement(By.name("imageLatConfigFieldId"))).selectByVisibleText(configFieldVo.getConfigFieldEfile().getImageLatitude());
            new Select(seleniumSettings.getWebDriver().findElement(By.name("imageLongConfigFieldId"))).selectByVisibleText(configFieldVo.getConfigFieldEfile().getImageLongitude());
            new Select(seleniumSettings.getWebDriver().findElement(By.name("imageTimeConfigFieldId"))).selectByVisibleText(configFieldVo.getConfigFieldEfile().getImageTimeSnapshot());
            new Select(seleniumSettings.getWebDriver().findElement(By.name("imageResizeMode"))).selectByVisibleText(configFieldVo.getConfigFieldEfile().getResizeMode());
            seleniumSettings.getWebDriver().findElement(By.name("imageWidth")).clear();
            seleniumSettings.getWebDriver().findElement(By.name("imageWidth")).sendKeys(configFieldVo.getConfigFieldEfile().getResizeWidth());
            seleniumSettings.getWebDriver().findElement(By.name("imageHeight")).clear();
            seleniumSettings.getWebDriver().findElement(By.name("imageHeight")).sendKeys(configFieldVo.getConfigFieldEfile().getResizeHeight());
            if ((configFieldVo.getConfigFieldEfile().getRotate().equals("YES") && !checkboxHelper.isCheckedByName("imageRotate"))
                    || (configFieldVo.getConfigFieldEfile().getRotate().equals("NO") && checkboxHelper.isCheckedByName("imageRotate"))) {
                checkboxHelper.clickByName("imageRotate");
            }
            if ((configFieldVo.getConfigFieldEfile().getLogBlobChanges().equals("YES") && !checkboxHelper.isCheckedByName("logBlobChanges"))
                    || (configFieldVo.getConfigFieldEfile().getLogBlobChanges().equals("NO") && checkboxHelper.isCheckedByName("logBlobChanges"))) {
                checkboxHelper.clickByName("logBlobChanges");
            }
            if ((configFieldVo.getConfigFieldEfile().getUploadToAws().equals("YES") && !checkboxHelper.isCheckedByName("uploadToS3Directly"))
                    || (configFieldVo.getConfigFieldEfile().getUploadToAws().equals("NO") && checkboxHelper.isCheckedByName("uploadToS3Directly"))) {
                checkboxHelper.clickByName("uploadToS3Directly");
            }
            psSelectorHelper.selectSpecificValue(By.id("btnautocaptionClientFile"), By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE + AbstractSeleniumCore.getGridIdx()), 1L, configFieldVo.getConfigFieldEfile().getAutocaptionTemplate(), 1L);

            tabHelper.goToTab(1L); //General
        } else if (ConfigFieldType.TRACKOR_SELECTOR.equals(configFieldVo.getConfigFieldType())) {
            setSqlToCodeMirror("btnSQL", configFieldVo.getConfigFieldTrackorSelector().getSql());

            new Select(seleniumSettings.getWebDriver().findElement(By.name("ObjectTrackorType"))).selectByVisibleText(configFieldVo.getConfigFieldTrackorSelector().getTrackorType());
            seleniumSettings.getWebDriver().findElement(By.name("shortNameLabel")).clear();
            seleniumSettings.getWebDriver().findElement(By.name("shortNameLabel")).sendKeys(configFieldVo.getConfigFieldTrackorSelector().getShortName());
            if ((configFieldVo.getConfigFieldTrackorSelector().getMyThingsFilter().equals("YES") && !checkboxHelper.isCheckedByName("useInMyThingsFilter"))
                    || (configFieldVo.getConfigFieldTrackorSelector().getMyThingsFilter().equals("NO") && checkboxHelper.isCheckedByName("useInMyThingsFilter"))) {
                checkboxHelper.clickByName("useInMyThingsFilter");
            }
            if ((configFieldVo.getConfigFieldTrackorSelector().getMyThingsMarker().equals("YES") && !checkboxHelper.isCheckedByName("myThingsMarket"))
                    || (configFieldVo.getConfigFieldTrackorSelector().getMyThingsMarker().equals("NO") && checkboxHelper.isCheckedByName("myThingsMarket"))) {
                checkboxHelper.clickByName("myThingsMarket");
            }

            psSelectorHelper.selectSpecificValue(By.id("btnobjCf"), By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE + AbstractSeleniumCore.getGridIdx()), 1L, configFieldVo.getConfigFieldTrackorSelector().getDisplayField(), 1L);

            setSqlToCodeMirror("btnDefSQL", configFieldVo.getConfigFieldTrackorSelector().getDefValueSql());
        } else if (ConfigFieldType.TRACKOR_DROP_DOWN.equals(configFieldVo.getConfigFieldType())) {
            setSqlToCodeMirror("btnSQL", configFieldVo.getConfigFieldTrackorDropDown().getSql());

            new Select(seleniumSettings.getWebDriver().findElement(By.name("ObjectTrackorType"))).selectByVisibleText(configFieldVo.getConfigFieldTrackorDropDown().getTrackorType());
            seleniumSettings.getWebDriver().findElement(By.name("shortNameLabel")).clear();
            seleniumSettings.getWebDriver().findElement(By.name("shortNameLabel")).sendKeys(configFieldVo.getConfigFieldTrackorDropDown().getShortName());
            if ((configFieldVo.getConfigFieldTrackorDropDown().getMyThingsFilter().equals("YES") && !checkboxHelper.isCheckedByName("useInMyThingsFilter"))
                    || (configFieldVo.getConfigFieldTrackorDropDown().getMyThingsFilter().equals("NO") && checkboxHelper.isCheckedByName("useInMyThingsFilter"))) {
                checkboxHelper.clickByName("useInMyThingsFilter");
            }
            if ((configFieldVo.getConfigFieldTrackorDropDown().getMyThingsMarker().equals("YES") && !checkboxHelper.isCheckedByName("myThingsMarket"))
                    || (configFieldVo.getConfigFieldTrackorDropDown().getMyThingsMarker().equals("NO") && checkboxHelper.isCheckedByName("myThingsMarket"))) {
                checkboxHelper.clickByName("myThingsMarket");
            }

            setSqlToCodeMirror("btnDefSQL", configFieldVo.getConfigFieldTrackorDropDown().getDefValueSql());
        } else if (ConfigFieldType.CALCULATED.equals(configFieldVo.getConfigFieldType())) {
            setSqlToCodeMirror("btnSQL", configFieldVo.getConfigFieldCalculated().getSql());
        } else if (ConfigFieldType.HYPERLINK.equals(configFieldVo.getConfigFieldType())) {
            seleniumSettings.getWebDriver().findElement(By.name("fieldSize")).clear();
            seleniumSettings.getWebDriver().findElement(By.name("fieldSize")).sendKeys(configFieldVo.getConfigFieldHyperlink().getLength());
        } else if (ConfigFieldType.ROLLUP.equals(configFieldVo.getConfigFieldType())) {
            //TODO filter

            new Select(seleniumSettings.getWebDriver().findElement(By.name("lstRollupXitorTypeID"))).selectByVisibleText(configFieldVo.getConfigFieldRollup().getTrackorType());
        } else if (ConfigFieldType.MULTI_SELECTOR.equals(configFieldVo.getConfigFieldType())) {
            seleniumSettings.getWebDriver().findElement(By.name("linesQty")).clear();
            seleniumSettings.getWebDriver().findElement(By.name("linesQty")).sendKeys(configFieldVo.getConfigFieldMultiSelector().getLines());

            psSelectorHelper.selectSpecificValue(By.id("btntableName"), By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE + AbstractSeleniumCore.getGridIdx()), 3L, configFieldVo.getConfigFieldMultiSelector().getTable(), 2L);

            setSqlToCodeMirror("btnDefSQL", configFieldVo.getConfigFieldMultiSelector().getDefValueSql());
        } else if (ConfigFieldType.DATE_TIME.equals(configFieldVo.getConfigFieldType())) {
            assertHelper.AssertText("fieldSize", "10");

            if ((configFieldVo.getConfigFieldDateTime().getShowSeconds().equals("YES") && !checkboxHelper.isCheckedByName("showSeconds"))
                    || (configFieldVo.getConfigFieldDateTime().getShowSeconds().equals("NO") && checkboxHelper.isCheckedByName("showSeconds"))) {
                checkboxHelper.clickByName("showSeconds");
            }

            setSqlToCodeMirror("btnDefSQL", configFieldVo.getConfigFieldDateTime().getDefValueSql());
        } else if (ConfigFieldType.TIME.equals(configFieldVo.getConfigFieldType())) {
            assertHelper.AssertText("fieldSize", "10");

            if ((configFieldVo.getConfigFieldTime().getShowSeconds().equals("YES") && !checkboxHelper.isCheckedByName("showSeconds"))
                    || (configFieldVo.getConfigFieldTime().getShowSeconds().equals("NO") && checkboxHelper.isCheckedByName("showSeconds"))) {
                checkboxHelper.clickByName("showSeconds");
            }

            setSqlToCodeMirror("btnDefSQL", configFieldVo.getConfigFieldTime().getDefValueSql());
        } else {
            throw new SeleniumUnexpectedException("Not support ConfigFieldType. ConfigFieldType=" + configFieldVo.getConfigFieldType());
        }

        seleniumSettings.getWebDriver().findElement(By.name("description")).clear();
        seleniumSettings.getWebDriver().findElement(By.name("description")).sendKeys(configFieldVo.getDescription());

        seleniumSettings.getWebDriver().findElement(By.name("comments")).clear();
        seleniumSettings.getWebDriver().findElement(By.name("comments")).sendKeys(configFieldVo.getComment());

        if ((configFieldVo.getMandatory().equals("YES") && !checkboxHelper.isCheckedByName("mandatory"))
                || (configFieldVo.getMandatory().equals("NO") && checkboxHelper.isCheckedByName("mandatory"))) {
            checkboxHelper.clickByName("mandatory");
        }

        if ((configFieldVo.getTwoColumns().equals("YES") && !checkboxHelper.isCheckedByName("twoColsSpan"))
                || (configFieldVo.getTwoColumns().equals("NO") && checkboxHelper.isCheckedByName("twoColsSpan"))) {
            checkboxHelper.clickByName("twoColsSpan");
        }

        boolean removeLockable = false;
        if ((configFieldVo.getLockable().equals("YES") && !checkboxHelper.isCheckedByName("lockable"))
                || (configFieldVo.getLockable().equals("NO") && checkboxHelper.isCheckedByName("lockable"))) {
            if (configFieldVo.getLockable().equals("NO") && checkboxHelper.isCheckedByName("lockable")) {
                removeLockable = true;
            }
            checkboxHelper.clickByName("lockable");
        }

        if ((configFieldVo.getMultiple().equals("YES") && !checkboxHelper.isCheckedByName("multiple"))
                || (configFieldVo.getMultiple().equals("NO") && checkboxHelper.isCheckedByName("multiple"))) {
            checkboxHelper.clickByName("multiple");
        }

        if ((configFieldVo.getReadOnly().equals("YES") && !checkboxHelper.isCheckedByName("readOnly"))
                || (configFieldVo.getReadOnly().equals("NO") && checkboxHelper.isCheckedByName("readOnly"))) {
            checkboxHelper.clickByName("readOnly");
        }

        if ((configFieldVo.getCalcTotal().equals("YES") && !checkboxHelper.isCheckedByName("calcTotal"))
                || (configFieldVo.getCalcTotal().equals("NO") && checkboxHelper.isCheckedByName("calcTotal"))) {
            checkboxHelper.clickByName("calcTotal");
        }

        if ((configFieldVo.getNotCloneValue().equals("YES") && !checkboxHelper.isCheckedByName("notCloneFieldValue"))
                || (configFieldVo.getNotCloneValue().equals("NO") && checkboxHelper.isCheckedByName("notCloneFieldValue"))) {
            checkboxHelper.clickByName("notCloneFieldValue");
        }

        if ((configFieldVo.getNotCloneLock().equals("YES") && !checkboxHelper.isCheckedByName("notCloneLocks"))
                || (configFieldVo.getNotCloneLock().equals("NO") && checkboxHelper.isCheckedByName("notCloneLocks"))) {
            checkboxHelper.clickByName("notCloneLocks");
        }

        if (removeLockable) {
            windowHelper.closeModalWithAlert(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE), AbstractSeleniumCore.MESSAGE_DELETE_LOCKABLE);
        } else {
            windowHelper.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        }
        waitHelper.waitGridLoad(AbstractSeleniumCore.getGridIdx(), AbstractSeleniumCore.getGridIdx());
    }

    public void testOnForm(ConfigFieldVo configFieldVo) {
        windowHelper.openModal(By.id(AbstractSeleniumCore.BUTTON_EDIT_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        waitHelper.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        waitHelper.waitFormLoad();

        if (ConfigFieldType.ROLLUP.equals(configFieldVo.getConfigFieldType())) {
            jqueryWait.waitJQueryLoad();
        }

        assertHelper.AssertSelect("TrackorTypeName", configFieldVo.getTrackorTypeLabel());
        assertHelper.AssertSelect("dataType", configFieldVo.getConfigFieldType().getLabel());
        assertHelper.AssertText("LabelName", configFieldVo.getLabel());
        assertHelper.AssertText("configFieldName", configFieldVo.getName());

        assertHelper.AssertSelect("componentsPackageId", configFieldVo.getCompPackage());

        assertHelper.AssertSelect("colorId", configFieldVo.getColor());

        assertHelper.AssertText("fieldWidth", configFieldVo.getWidth());

        if (ConfigFieldType.TEXT.equals(configFieldVo.getConfigFieldType())) {
            assertHelper.AssertText("fieldSize", configFieldVo.getConfigFieldText().getLength());

            checkSqlInCodeMirror("btnDefSQL", configFieldVo.getConfigFieldText().getDefValueSql());
        } else if (ConfigFieldType.NUMBER.equals(configFieldVo.getConfigFieldType())) {
            assertHelper.AssertText("fieldSize", configFieldVo.getConfigFieldNumber().getLength());

            assertHelper.AssertText("prefix", configFieldVo.getConfigFieldNumber().getPrefix());
            assertHelper.AssertText("suffix", configFieldVo.getConfigFieldNumber().getSuffix());
            assertHelper.AssertText("numDecimals", configFieldVo.getConfigFieldNumber().getDecimal());
            assertHelper.AssertText("negativeColor", configFieldVo.getConfigFieldNumber().getNegativeColor());
            assertHelper.AssertText("positiveColor", configFieldVo.getConfigFieldNumber().getPositiveColor());

            assertHelper.AssertCheckBoxNew("parensForNegative", configFieldVo.getConfigFieldNumber().getParensForNegative());
            assertHelper.AssertCheckBoxNew("separateThousands", configFieldVo.getConfigFieldNumber().getSeparateThousands());

            checkSqlInCodeMirror("btnDefSQL", configFieldVo.getConfigFieldNumber().getDefValueSql());
        } else if (ConfigFieldType.DATE.equals(configFieldVo.getConfigFieldType())) {
            assertHelper.AssertText("fieldSize", "10");

            checkSqlInCodeMirror("btnDefSQL", configFieldVo.getConfigFieldDate().getDefValueSql());
        } else if (ConfigFieldType.CHECKBOX.equals(configFieldVo.getConfigFieldType())) {
            assertHelper.AssertText("fieldSize", "1");

            checkSqlInCodeMirror("btnDefSQL", configFieldVo.getConfigFieldCheckbox().getDefValueSql());
        } else if (ConfigFieldType.DROP_DOWN.equals(configFieldVo.getConfigFieldType())) {
            assertHelper.AssertRadioPsSelector("tableName", "btntableName", AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + 0L, configFieldVo.getConfigFieldDropDown().getTable(), 2L, true);

            checkSqlInCodeMirror("btnDefSQL", configFieldVo.getConfigFieldDropDown().getDefValueSql());
        } else if (ConfigFieldType.MEMO.equals(configFieldVo.getConfigFieldType())) {
            assertHelper.AssertText("fieldSize", configFieldVo.getConfigFieldMemo().getLength());
            assertHelper.AssertText("linesQty", configFieldVo.getConfigFieldMemo().getLines());

            checkSqlInCodeMirror("btnDefSQL", configFieldVo.getConfigFieldMemo().getDefValueSql());
        } else if (ConfigFieldType.WIKI.equals(configFieldVo.getConfigFieldType())) {
            assertHelper.AssertText("fieldSize", configFieldVo.getConfigFieldWiki().getLength());
            assertHelper.AssertText("linesQty", configFieldVo.getConfigFieldWiki().getLines());

            checkSqlInCodeMirror("btnDefSQL", configFieldVo.getConfigFieldWiki().getDefValueSql());
        } else if (ConfigFieldType.DB_DROP_DOWN.equals(configFieldVo.getConfigFieldType())) {
            if (!"XITOR_CLASS_ID".equals(configFieldVo.getName())) {
                checkSqlInCodeMirror("btnSQL", configFieldVo.getConfigFieldDbDropDown().getSql());
            }
            checkSqlInCodeMirror("btnDefSQL", configFieldVo.getConfigFieldDbDropDown().getDefValueSql());
        } else if (ConfigFieldType.DB_SELECTOR.equals(configFieldVo.getConfigFieldType())) {
            checkSqlInCodeMirror("btnSQL", configFieldVo.getConfigFieldDbSelector().getSql());
            checkSqlInCodeMirror("btnDefSQL", configFieldVo.getConfigFieldDbSelector().getDefValueSql());
        } else if (ConfigFieldType.SELECTOR.equals(configFieldVo.getConfigFieldType())) {
            assertHelper.AssertRadioPsSelector("tableName", "btntableName", AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + 0L, configFieldVo.getConfigFieldSelector().getTable(), 2L, true);

            checkSqlInCodeMirror("btnDefSQL", configFieldVo.getConfigFieldSelector().getDefValueSql());
        } else if (ConfigFieldType.LATITUDE.equals(configFieldVo.getConfigFieldType())) {
            assertHelper.AssertText("fieldSize", "15");

            checkSqlInCodeMirror("btnDefSQL", configFieldVo.getConfigFieldLatitude().getDefValueSql());
        } else if (ConfigFieldType.LONGITUDE.equals(configFieldVo.getConfigFieldType())) {
            assertHelper.AssertText("fieldSize", "15");

            checkSqlInCodeMirror("btnDefSQL", configFieldVo.getConfigFieldLongitude().getDefValueSql());
        } else if (ConfigFieldType.ELECTRONIC_FILE.equals(configFieldVo.getConfigFieldType())) {
            tabHelper.goToTab(2L); //Image Settings

            assertHelper.AssertCheckBoxNew("imageExtractMetadata", configFieldVo.getConfigFieldEfile().getExtractMetadata());
            assertHelper.AssertSelect("imageLatConfigFieldId", configFieldVo.getConfigFieldEfile().getImageLatitude());
            assertHelper.AssertSelect("imageLongConfigFieldId", configFieldVo.getConfigFieldEfile().getImageLongitude());
            assertHelper.AssertSelect("imageTimeConfigFieldId", configFieldVo.getConfigFieldEfile().getImageTimeSnapshot());
            assertHelper.AssertSelect("imageResizeMode", configFieldVo.getConfigFieldEfile().getResizeMode());
            assertHelper.AssertText("imageWidth", configFieldVo.getConfigFieldEfile().getResizeWidth());
            assertHelper.AssertText("imageHeight", configFieldVo.getConfigFieldEfile().getResizeHeight());
            assertHelper.AssertCheckBoxNew("imageRotate", configFieldVo.getConfigFieldEfile().getRotate());
            assertHelper.AssertCheckBoxNew("logBlobChanges", configFieldVo.getConfigFieldEfile().getLogBlobChanges());
            assertHelper.AssertCheckBoxNew("uploadToS3Directly", configFieldVo.getConfigFieldEfile().getUploadToAws());
            assertHelper.AssertRadioPsSelector("autocaptionClientFile", "btnautocaptionClientFile", AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + 0L, configFieldVo.getConfigFieldEfile().getAutocaptionTemplate(), 1L, true);

            tabHelper.goToTab(1L); //General
        } else if (ConfigFieldType.TRACKOR_SELECTOR.equals(configFieldVo.getConfigFieldType())) {
            checkSqlInCodeMirror("btnSQL", configFieldVo.getConfigFieldTrackorSelector().getSql());

            assertHelper.AssertSelect("ObjectTrackorType", configFieldVo.getConfigFieldTrackorSelector().getTrackorType());
            assertHelper.AssertText("shortNameLabel", configFieldVo.getConfigFieldTrackorSelector().getShortName());
            assertHelper.AssertCheckBoxNew("useInMyThingsFilter", configFieldVo.getConfigFieldTrackorSelector().getMyThingsFilter());
            assertHelper.AssertCheckBoxNew("myThingsMarket", configFieldVo.getConfigFieldTrackorSelector().getMyThingsMarker());

            assertHelper.AssertRadioPsSelector("objCf", "btnobjCf", AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + 0L, configFieldVo.getConfigFieldTrackorSelector().getDisplayField(), 1L, true);

            checkSqlInCodeMirror("btnDefSQL", configFieldVo.getConfigFieldTrackorSelector().getDefValueSql());
        } else if (ConfigFieldType.TRACKOR_DROP_DOWN.equals(configFieldVo.getConfigFieldType())) {
            checkSqlInCodeMirror("btnSQL", configFieldVo.getConfigFieldTrackorDropDown().getSql());

            assertHelper.AssertSelect("ObjectTrackorType", configFieldVo.getConfigFieldTrackorDropDown().getTrackorType());
            assertHelper.AssertText("shortNameLabel", configFieldVo.getConfigFieldTrackorDropDown().getShortName());
            assertHelper.AssertCheckBoxNew("useInMyThingsFilter", configFieldVo.getConfigFieldTrackorDropDown().getMyThingsFilter());
            assertHelper.AssertCheckBoxNew("myThingsMarket", configFieldVo.getConfigFieldTrackorDropDown().getMyThingsMarker());

            checkSqlInCodeMirror("btnDefSQL", configFieldVo.getConfigFieldTrackorDropDown().getDefValueSql());
        } else if (ConfigFieldType.CALCULATED.equals(configFieldVo.getConfigFieldType())) {
            checkSqlInCodeMirror("btnSQL", configFieldVo.getConfigFieldCalculated().getSql());
        } else if (ConfigFieldType.HYPERLINK.equals(configFieldVo.getConfigFieldType())) {
            assertHelper.AssertText("fieldSize", configFieldVo.getConfigFieldHyperlink().getLength());
        } else if (ConfigFieldType.ROLLUP.equals(configFieldVo.getConfigFieldType())) {
            //TODO filter

            assertHelper.AssertSelect("lstRollupXitorTypeID", configFieldVo.getConfigFieldRollup().getTrackorType());
        } else if (ConfigFieldType.MULTI_SELECTOR.equals(configFieldVo.getConfigFieldType())) {
            assertHelper.AssertText("linesQty", configFieldVo.getConfigFieldMultiSelector().getLines());

            assertHelper.AssertRadioPsSelector("tableName", "btntableName", AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + 0L, configFieldVo.getConfigFieldMultiSelector().getTable(), 2L, true);

            checkSqlInCodeMirror("btnDefSQL", configFieldVo.getConfigFieldMultiSelector().getDefValueSql());
        } else if (ConfigFieldType.DATE_TIME.equals(configFieldVo.getConfigFieldType())) {
            assertHelper.AssertText("fieldSize", "10");

            assertHelper.AssertCheckBoxNew("showSeconds", configFieldVo.getConfigFieldDateTime().getShowSeconds());

            checkSqlInCodeMirror("btnDefSQL", configFieldVo.getConfigFieldDateTime().getDefValueSql());
        } else if (ConfigFieldType.TIME.equals(configFieldVo.getConfigFieldType())) {
            assertHelper.AssertText("fieldSize", "10");

            assertHelper.AssertCheckBoxNew("showSeconds", configFieldVo.getConfigFieldTime().getShowSeconds());

            checkSqlInCodeMirror("btnDefSQL", configFieldVo.getConfigFieldTime().getDefValueSql());
        } else {
            throw new SeleniumUnexpectedException("Not support ConfigFieldType. ConfigFieldType=" + configFieldVo.getConfigFieldType());
        }

        assertHelper.AssertText("description", configFieldVo.getDescription());
        assertHelper.AssertText("comments", configFieldVo.getComment());

        assertHelper.AssertCheckBoxNew("mandatory", configFieldVo.getMandatory());
        assertHelper.AssertCheckBoxNew("twoColsSpan", configFieldVo.getTwoColumns());
        assertHelper.AssertCheckBoxNew("lockable", configFieldVo.getLockable());
        assertHelper.AssertCheckBoxNew("multiple", configFieldVo.getMultiple());

        assertHelper.AssertCheckBoxNew("readOnly", configFieldVo.getReadOnly());
        assertHelper.AssertCheckBoxNew("calcTotal", configFieldVo.getCalcTotal());
        assertHelper.AssertCheckBoxNew("notCloneFieldValue", configFieldVo.getNotCloneValue());
        assertHelper.AssertCheckBoxNew("notCloneLocks", configFieldVo.getNotCloneLock());

        windowHelper.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

    public void testInGrid(Long gridId, Long rowIndex, ConfigFieldVo configFieldVo) {
        Map<Long, String> gridVals = new HashMap<Long, String>();

        gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Field Name"), configFieldVo.getName());
        gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Field Label"), configFieldVo.getLabel());

        if (ConfigFieldType.CHECKBOX.equals(configFieldVo.getConfigFieldType())) {
            gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Max Length"), "1");
        } else if (ConfigFieldType.DATE.equals(configFieldVo.getConfigFieldType())) {
            gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Max Length"), "10");
        } else if (ConfigFieldType.DATE_TIME.equals(configFieldVo.getConfigFieldType())) {
            gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Max Length"), "10");
        } else if (ConfigFieldType.TIME.equals(configFieldVo.getConfigFieldType())) {
            gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Max Length"), "10");
        } else if (ConfigFieldType.LATITUDE.equals(configFieldVo.getConfigFieldType())) {
            gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Max Length"), "15");
        } else if (ConfigFieldType.LONGITUDE.equals(configFieldVo.getConfigFieldType())) {
            gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Max Length"), "15");
        } else if (ConfigFieldType.HYPERLINK.equals(configFieldVo.getConfigFieldType())) {
            gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Max Length"), configFieldVo.getConfigFieldHyperlink().getLength());
        } else if (ConfigFieldType.MEMO.equals(configFieldVo.getConfigFieldType())) {
            gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Max Length"), configFieldVo.getConfigFieldMemo().getLength());
        } else if (ConfigFieldType.WIKI.equals(configFieldVo.getConfigFieldType())) {
            gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Max Length"), configFieldVo.getConfigFieldWiki().getLength());
        } else if (ConfigFieldType.TEXT.equals(configFieldVo.getConfigFieldType())) {
            gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Max Length"), configFieldVo.getConfigFieldText().getLength());
        } else if (ConfigFieldType.NUMBER.equals(configFieldVo.getConfigFieldType())) {
            gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Max Length"), configFieldVo.getConfigFieldNumber().getLength());
        } else {
            gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Max Length"), "");
        }

        if (ConfigFieldType.DROP_DOWN.equals(configFieldVo.getConfigFieldType())) {
            gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Table Name"), configFieldVo.getConfigFieldDropDown().getTable());
        } else if (ConfigFieldType.SELECTOR.equals(configFieldVo.getConfigFieldType())) {
            gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Table Name"), configFieldVo.getConfigFieldSelector().getTable());
        } else if (ConfigFieldType.MULTI_SELECTOR.equals(configFieldVo.getConfigFieldType())) {
            gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Table Name"), configFieldVo.getConfigFieldMultiSelector().getTable());
        } else {
            gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Table Name"), "");
        }

        gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Data Type"), configFieldVo.getConfigFieldType().getLabel());

        if (ConfigFieldType.MEMO.equals(configFieldVo.getConfigFieldType())) {
            gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Lines Qty"), configFieldVo.getConfigFieldMemo().getLines());
        } else if (ConfigFieldType.WIKI.equals(configFieldVo.getConfigFieldType())) {
            gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Lines Qty"), configFieldVo.getConfigFieldWiki().getLines());
        } else if (ConfigFieldType.MULTI_SELECTOR.equals(configFieldVo.getConfigFieldType())) {
            gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Lines Qty"), configFieldVo.getConfigFieldMultiSelector().getLines());
        } else {
            gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Lines Qty"), "");
        }

        gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Field Width (px)"), configFieldVo.getWidth());
        gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Description"), configFieldVo.getDescription());
        gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "TRACKOR Type"), configFieldVo.getTrackorTypeLabel());
        gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Multiple Lines"), configFieldVo.getMultiple());
        gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Read Only"), configFieldVo.getReadOnly());

        if (ConfigFieldType.TRACKOR_SELECTOR.equals(configFieldVo.getConfigFieldType())) {
            gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Selector's Trackor Type"), configFieldVo.getConfigFieldTrackorSelector().getTrackorType());
            gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Short Name"), configFieldVo.getConfigFieldTrackorSelector().getShortName());
            gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Use in \"My Things\" filter"), configFieldVo.getConfigFieldTrackorSelector().getMyThingsFilter());
        } else if (ConfigFieldType.TRACKOR_DROP_DOWN.equals(configFieldVo.getConfigFieldType())) {
            gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Selector's Trackor Type"), configFieldVo.getConfigFieldTrackorDropDown().getTrackorType());
            gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Short Name"), configFieldVo.getConfigFieldTrackorDropDown().getShortName());
            gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Use in \"My Things\" filter"), configFieldVo.getConfigFieldTrackorDropDown().getMyThingsFilter());
        } else if (ConfigFieldType.ROLLUP.equals(configFieldVo.getConfigFieldType())) {
            gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Selector's Trackor Type"), configFieldVo.getConfigFieldRollup().getTrackorType());
            gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Short Name"), "");
            gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Use in \"My Things\" filter"), "NO");
        } else {
            gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Selector's Trackor Type"), "");
            gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Short Name"), "");
            gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Use in \"My Things\" filter"), "NO");
        }

        if (ConfigFieldType.ELECTRONIC_FILE.equals(configFieldVo.getConfigFieldType())) {
            gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Log BLOB changes"), configFieldVo.getConfigFieldEfile().getLogBlobChanges());
        } else {
            gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Log BLOB changes"), "NO");
        }

        gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Mandatory"), configFieldVo.getMandatory());
        gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Two Columns Span"), configFieldVo.getTwoColumns());
        gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Lockable"), configFieldVo.getLockable());

        if (ConfigFieldType.CALCULATED.equals(configFieldVo.getConfigFieldType())) {
            gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "SQL Query"), configFieldVo.getConfigFieldCalculated().getSql());
        } else if (ConfigFieldType.DB_DROP_DOWN.equals(configFieldVo.getConfigFieldType())) {
            gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "SQL Query"), configFieldVo.getConfigFieldDbDropDown().getSql());
        } else if (ConfigFieldType.DB_SELECTOR.equals(configFieldVo.getConfigFieldType())) {
            gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "SQL Query"), configFieldVo.getConfigFieldDbSelector().getSql());
        } else if (ConfigFieldType.TRACKOR_DROP_DOWN.equals(configFieldVo.getConfigFieldType())) {
            gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "SQL Query"), configFieldVo.getConfigFieldTrackorDropDown().getSql());
        } else if (ConfigFieldType.TRACKOR_SELECTOR.equals(configFieldVo.getConfigFieldType())) {
            gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "SQL Query"), configFieldVo.getConfigFieldTrackorSelector().getSql());
        } else {
            gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "SQL Query"), "");
        }

        if (ConfigFieldType.TRACKOR_SELECTOR.equals(configFieldVo.getConfigFieldType())) {
            gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Selector's Display Field"), configFieldVo.getConfigFieldTrackorSelector().getDisplayField());
        } else {
            gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Selector's Display Field"), "");
        }

        gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Calc Total"), configFieldVo.getCalcTotal());
        gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Comments"), configFieldVo.getComment());
        gridVals.put(jsHelper.getColumnIndexByLabel(gridId, "Don't Clone Field Value"), configFieldVo.getNotCloneValue());

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