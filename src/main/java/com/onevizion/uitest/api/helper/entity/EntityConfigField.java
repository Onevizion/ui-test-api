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
import com.onevizion.uitest.api.helper.AssertElement;
import com.onevizion.uitest.api.helper.Checkbox;
import com.onevizion.uitest.api.helper.ElementHelper;
import com.onevizion.uitest.api.helper.ElementWaitHelper;
import com.onevizion.uitest.api.helper.GridHelper;
import com.onevizion.uitest.api.helper.Js;
import com.onevizion.uitest.api.helper.PsSelector;
import com.onevizion.uitest.api.helper.Tab;
import com.onevizion.uitest.api.helper.Wait;
import com.onevizion.uitest.api.helper.Window;
import com.onevizion.uitest.api.helper.configfield.ConfigField;
import com.onevizion.uitest.api.helper.jquery.JqueryWait;
import com.onevizion.uitest.api.vo.ConfigFieldType;
import com.onevizion.uitest.api.vo.entity.ConfigFieldVo;

@Component
public class EntityConfigField {

    @Resource
    private Window window;

    @Resource
    private Wait wait;

    @Resource
    private AssertElement assertElement;

    @Resource
    private Js js;

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
    private Checkbox checkbox;

    @Resource
    private PsSelector psSelector;

    @Resource
    private Tab tab;

    @Resource
    private ElementHelper elementHelper;

    public void add(ConfigFieldVo configFieldVo) {
        window.openModal(By.id(AbstractSeleniumCore.BUTTON_ADD_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

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
            window.openModal(By.name("btnrgbNegColor"));
            seleniumSettings.getWebDriver().findElement(By.className("dhxcp_color_selector")).click();
            window.closeModal(By.className("dhx_button_save"));
            window.openModal(By.name("btnrgbPosColor"));
            seleniumSettings.getWebDriver().findElement(By.className("dhxcp_color_selector")).click();
            window.closeModal(By.className("dhx_button_save"));

            if ((configFieldVo.getConfigFieldNumber().getParensForNegative().equals("YES") && !checkbox.isCheckedByName("parensForNegative"))
                    || (configFieldVo.getConfigFieldNumber().getParensForNegative().equals("NO") && checkbox.isCheckedByName("parensForNegative"))) {
                checkbox.clickByName("parensForNegative");
            }
            if ((configFieldVo.getConfigFieldNumber().getSeparateThousands().equals("YES") && !checkbox.isCheckedByName("separateThousands"))
                    || (configFieldVo.getConfigFieldNumber().getSeparateThousands().equals("NO") && checkbox.isCheckedByName("separateThousands"))) {
                checkbox.clickByName("separateThousands");
            }

            setSqlToCodeMirror("btnDefSQL", configFieldVo.getConfigFieldNumber().getDefValueSql());
        } else if (ConfigFieldType.DATE.equals(configFieldVo.getConfigFieldType())) {
            assertElement.AssertText("fieldSize", "10");

            setSqlToCodeMirror("btnDefSQL", configFieldVo.getConfigFieldDate().getDefValueSql());
        } else if (ConfigFieldType.CHECKBOX.equals(configFieldVo.getConfigFieldType())) {
            assertElement.AssertText("fieldSize", "1");

            setSqlToCodeMirror("btnDefSQL", configFieldVo.getConfigFieldCheckbox().getDefValueSql());
        } else if (ConfigFieldType.DROP_DOWN.equals(configFieldVo.getConfigFieldType())) {
            psSelector.selectSpecificValue(By.id("btntableName"), By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE + AbstractSeleniumCore.getGridIdx()), 3L, configFieldVo.getConfigFieldDropDown().getTable(), 2L);

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
            psSelector.selectSpecificValue(By.id("btntableName"), By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE + AbstractSeleniumCore.getGridIdx()), 3L, configFieldVo.getConfigFieldSelector().getTable(), 2L);

            setSqlToCodeMirror("btnDefSQL", configFieldVo.getConfigFieldSelector().getDefValueSql());
        } else if (ConfigFieldType.LATITUDE.equals(configFieldVo.getConfigFieldType())) {
            assertElement.AssertText("fieldSize", "15");

            setSqlToCodeMirror("btnDefSQL", configFieldVo.getConfigFieldLatitude().getDefValueSql());
        } else if (ConfigFieldType.LONGITUDE.equals(configFieldVo.getConfigFieldType())) {
            assertElement.AssertText("fieldSize", "15");

            setSqlToCodeMirror("btnDefSQL", configFieldVo.getConfigFieldLongitude().getDefValueSql());
        } else if (ConfigFieldType.ELECTRONIC_FILE.equals(configFieldVo.getConfigFieldType())) {
            elementHelper.clickById(AbstractSeleniumCore.BUTTON_APPLY_ID);
            wait.waitReloadForm("reloaded=1");
            wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
            wait.waitFormLoad();

            tab.goToTab(2L); //Image Settings

            if ((configFieldVo.getConfigFieldEfile().getExtractMetadata().equals("YES") && !checkbox.isCheckedByName("imageExtractMetadata"))
                    || (configFieldVo.getConfigFieldEfile().getExtractMetadata().equals("NO") && checkbox.isCheckedByName("imageExtractMetadata"))) {
                checkbox.clickByName("imageExtractMetadata");
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
            if ((configFieldVo.getConfigFieldEfile().getRotate().equals("YES") && !checkbox.isCheckedByName("imageRotate"))
                    || (configFieldVo.getConfigFieldEfile().getRotate().equals("NO") && checkbox.isCheckedByName("imageRotate"))) {
                checkbox.clickByName("imageRotate");
            }
            if ((configFieldVo.getConfigFieldEfile().getLogBlobChanges().equals("YES") && !checkbox.isCheckedByName("logBlobChanges"))
                    || (configFieldVo.getConfigFieldEfile().getLogBlobChanges().equals("NO") && checkbox.isCheckedByName("logBlobChanges"))) {
                checkbox.clickByName("logBlobChanges");
            }
            if ((configFieldVo.getConfigFieldEfile().getUploadToAws().equals("YES") && !checkbox.isCheckedByName("uploadToS3Directly"))
                    || (configFieldVo.getConfigFieldEfile().getUploadToAws().equals("NO") && checkbox.isCheckedByName("uploadToS3Directly"))) {
                checkbox.clickByName("uploadToS3Directly");
            }
            psSelector.selectSpecificValue(By.id("btnautocaptionClientFile"), By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE + AbstractSeleniumCore.getGridIdx()), 1L, configFieldVo.getConfigFieldEfile().getAutocaptionTemplate(), 1L);

            tab.goToTab(1L); //General
        } else if (ConfigFieldType.TRACKOR_SELECTOR.equals(configFieldVo.getConfigFieldType())) {
            setSqlToCodeMirror("btnSQL", configFieldVo.getConfigFieldTrackorSelector().getSql());

            new Select(seleniumSettings.getWebDriver().findElement(By.name("ObjectTrackorType"))).selectByVisibleText(configFieldVo.getConfigFieldTrackorSelector().getTrackorType());
            seleniumSettings.getWebDriver().findElement(By.name("shortNameLabel")).clear();
            seleniumSettings.getWebDriver().findElement(By.name("shortNameLabel")).sendKeys(configFieldVo.getConfigFieldTrackorSelector().getShortName());
            if ((configFieldVo.getConfigFieldTrackorSelector().getMyThingsFilter().equals("YES") && !checkbox.isCheckedByName("useInMyThingsFilter"))
                    || (configFieldVo.getConfigFieldTrackorSelector().getMyThingsFilter().equals("NO") && checkbox.isCheckedByName("useInMyThingsFilter"))) {
                checkbox.clickByName("useInMyThingsFilter");
            }
            if ((configFieldVo.getConfigFieldTrackorSelector().getMyThingsMarker().equals("YES") && !checkbox.isCheckedByName("myThingsMarket"))
                    || (configFieldVo.getConfigFieldTrackorSelector().getMyThingsMarker().equals("NO") && checkbox.isCheckedByName("myThingsMarket"))) {
                checkbox.clickByName("myThingsMarket");
            }

            psSelector.selectSpecificValue(By.id("btnobjCf"), By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE + AbstractSeleniumCore.getGridIdx()), 1L, configFieldVo.getConfigFieldTrackorSelector().getDisplayField(), 1L);

            setSqlToCodeMirror("btnDefSQL", configFieldVo.getConfigFieldTrackorSelector().getDefValueSql());
        } else if (ConfigFieldType.TRACKOR_DROP_DOWN.equals(configFieldVo.getConfigFieldType())) {
            setSqlToCodeMirror("btnSQL", configFieldVo.getConfigFieldTrackorDropDown().getSql());

            new Select(seleniumSettings.getWebDriver().findElement(By.name("ObjectTrackorType"))).selectByVisibleText(configFieldVo.getConfigFieldTrackorDropDown().getTrackorType());
            seleniumSettings.getWebDriver().findElement(By.name("shortNameLabel")).clear();
            seleniumSettings.getWebDriver().findElement(By.name("shortNameLabel")).sendKeys(configFieldVo.getConfigFieldTrackorDropDown().getShortName());
            if ((configFieldVo.getConfigFieldTrackorDropDown().getMyThingsFilter().equals("YES") && !checkbox.isCheckedByName("useInMyThingsFilter"))
                    || (configFieldVo.getConfigFieldTrackorDropDown().getMyThingsFilter().equals("NO") && checkbox.isCheckedByName("useInMyThingsFilter"))) {
                checkbox.clickByName("useInMyThingsFilter");
            }
            if ((configFieldVo.getConfigFieldTrackorDropDown().getMyThingsMarker().equals("YES") && !checkbox.isCheckedByName("myThingsMarket"))
                    || (configFieldVo.getConfigFieldTrackorDropDown().getMyThingsMarker().equals("NO") && checkbox.isCheckedByName("myThingsMarket"))) {
                checkbox.clickByName("myThingsMarket");
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

            psSelector.selectSpecificValue(By.id("btntableName"), By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE + AbstractSeleniumCore.getGridIdx()), 3L, configFieldVo.getConfigFieldMultiSelector().getTable(), 2L);

            setSqlToCodeMirror("btnDefSQL", configFieldVo.getConfigFieldMultiSelector().getDefValueSql());
        } else if (ConfigFieldType.DATE_TIME.equals(configFieldVo.getConfigFieldType())) {
            assertElement.AssertText("fieldSize", "10");

            if ((configFieldVo.getConfigFieldDateTime().getShowSeconds().equals("YES") && !checkbox.isCheckedByName("showSeconds"))
                    || (configFieldVo.getConfigFieldDateTime().getShowSeconds().equals("NO") && checkbox.isCheckedByName("showSeconds"))) {
                checkbox.clickByName("showSeconds");
            }

            setSqlToCodeMirror("btnDefSQL", configFieldVo.getConfigFieldDateTime().getDefValueSql());
        } else if (ConfigFieldType.TIME.equals(configFieldVo.getConfigFieldType())) {
            assertElement.AssertText("fieldSize", "10");

            if ((configFieldVo.getConfigFieldTime().getShowSeconds().equals("YES") && !checkbox.isCheckedByName("showSeconds"))
                    || (configFieldVo.getConfigFieldTime().getShowSeconds().equals("NO") && checkbox.isCheckedByName("showSeconds"))) {
                checkbox.clickByName("showSeconds");
            }

            setSqlToCodeMirror("btnDefSQL", configFieldVo.getConfigFieldTime().getDefValueSql());
        } else {
            throw new SeleniumUnexpectedException("Not support ConfigFieldType. ConfigFieldType=" + configFieldVo.getConfigFieldType());
        }

        seleniumSettings.getWebDriver().findElement(By.name("description")).sendKeys(configFieldVo.getDescription());

        seleniumSettings.getWebDriver().findElement(By.name("comments")).sendKeys(configFieldVo.getComment());

        if ((configFieldVo.getMandatory().equals("YES") && !checkbox.isCheckedByName("mandatory"))
                || (configFieldVo.getMandatory().equals("NO") && checkbox.isCheckedByName("mandatory"))) {
            checkbox.clickByName("mandatory");
        }

        if ((configFieldVo.getTwoColumns().equals("YES") && !checkbox.isCheckedByName("twoColsSpan"))
                || (configFieldVo.getTwoColumns().equals("NO") && checkbox.isCheckedByName("twoColsSpan"))) {
            checkbox.clickByName("twoColsSpan");
        }

        if ((configFieldVo.getLockable().equals("YES") && !checkbox.isCheckedByName("lockable"))
                || (configFieldVo.getLockable().equals("NO") && checkbox.isCheckedByName("lockable"))) {
            checkbox.clickByName("lockable");
        }

        if ((configFieldVo.getMultiple().equals("YES") && !checkbox.isCheckedByName("multiple"))
                || (configFieldVo.getMultiple().equals("NO") && checkbox.isCheckedByName("multiple"))) {
            checkbox.clickByName("multiple");
        }

        if ((configFieldVo.getReadOnly().equals("YES") && !checkbox.isCheckedByName("readOnly"))
                || (configFieldVo.getReadOnly().equals("NO") && checkbox.isCheckedByName("readOnly"))) {
            checkbox.clickByName("readOnly");
        }

        if ((configFieldVo.getCalcTotal().equals("YES") && !checkbox.isCheckedByName("calcTotal"))
                || (configFieldVo.getCalcTotal().equals("NO") && checkbox.isCheckedByName("calcTotal"))) {
            checkbox.clickByName("calcTotal");
        }

        if ((configFieldVo.getNotCloneValue().equals("YES") && !checkbox.isCheckedByName("notCloneFieldValue"))
                || (configFieldVo.getNotCloneValue().equals("NO") && checkbox.isCheckedByName("notCloneFieldValue"))) {
            checkbox.clickByName("notCloneFieldValue");
        }

        if ((configFieldVo.getNotCloneLock().equals("YES") && !checkbox.isCheckedByName("notCloneLocks"))
                || (configFieldVo.getNotCloneLock().equals("NO") && checkbox.isCheckedByName("notCloneLocks"))) {
            checkbox.clickByName("notCloneLocks");
        }

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitGridLoad(AbstractSeleniumCore.getGridIdx(), AbstractSeleniumCore.getGridIdx());
    }

    public void edit(ConfigFieldVo configFieldVo) {
        window.openModal(By.id(AbstractSeleniumCore.BUTTON_EDIT_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

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
            window.openModal(By.name("btnrgbNegColor"));
            seleniumSettings.getWebDriver().findElement(By.className("dhxcp_color_selector")).click();
            window.closeModal(By.className("dhx_button_save"));
            window.openModal(By.name("btnrgbPosColor"));
            seleniumSettings.getWebDriver().findElement(By.className("dhxcp_color_selector")).click();
            window.closeModal(By.className("dhx_button_save"));

            if ((configFieldVo.getConfigFieldNumber().getParensForNegative().equals("YES") && !checkbox.isCheckedByName("parensForNegative"))
                    || (configFieldVo.getConfigFieldNumber().getParensForNegative().equals("NO") && checkbox.isCheckedByName("parensForNegative"))) {
                checkbox.clickByName("parensForNegative");
            }
            if ((configFieldVo.getConfigFieldNumber().getSeparateThousands().equals("YES") && !checkbox.isCheckedByName("separateThousands"))
                    || (configFieldVo.getConfigFieldNumber().getSeparateThousands().equals("NO") && checkbox.isCheckedByName("separateThousands"))) {
                checkbox.clickByName("separateThousands");
            }

            setSqlToCodeMirror("btnDefSQL", configFieldVo.getConfigFieldNumber().getDefValueSql());
        } else if (ConfigFieldType.DATE.equals(configFieldVo.getConfigFieldType())) {
            assertElement.AssertText("fieldSize", "10");

            setSqlToCodeMirror("btnDefSQL", configFieldVo.getConfigFieldDate().getDefValueSql());
        } else if (ConfigFieldType.CHECKBOX.equals(configFieldVo.getConfigFieldType())) {
            assertElement.AssertText("fieldSize", "1");

            setSqlToCodeMirror("btnDefSQL", configFieldVo.getConfigFieldCheckbox().getDefValueSql());
        } else if (ConfigFieldType.DROP_DOWN.equals(configFieldVo.getConfigFieldType())) {
            psSelector.selectSpecificValue(By.id("btntableName"), By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE + AbstractSeleniumCore.getGridIdx()), 3L, configFieldVo.getConfigFieldDropDown().getTable(), 2L);

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
            psSelector.selectSpecificValue(By.id("btntableName"), By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE + AbstractSeleniumCore.getGridIdx()), 3L, configFieldVo.getConfigFieldSelector().getTable(), 2L);

            setSqlToCodeMirror("btnDefSQL", configFieldVo.getConfigFieldSelector().getDefValueSql());
        } else if (ConfigFieldType.LATITUDE.equals(configFieldVo.getConfigFieldType())) {
            assertElement.AssertText("fieldSize", "15");

            setSqlToCodeMirror("btnDefSQL", configFieldVo.getConfigFieldLatitude().getDefValueSql());
        } else if (ConfigFieldType.LONGITUDE.equals(configFieldVo.getConfigFieldType())) {
            assertElement.AssertText("fieldSize", "15");

            setSqlToCodeMirror("btnDefSQL", configFieldVo.getConfigFieldLongitude().getDefValueSql());
        } else if (ConfigFieldType.ELECTRONIC_FILE.equals(configFieldVo.getConfigFieldType())) {
            tab.goToTab(2L); //Image Settings

            if ((configFieldVo.getConfigFieldEfile().getExtractMetadata().equals("YES") && !checkbox.isCheckedByName("imageExtractMetadata"))
                    || (configFieldVo.getConfigFieldEfile().getExtractMetadata().equals("NO") && checkbox.isCheckedByName("imageExtractMetadata"))) {
                checkbox.clickByName("imageExtractMetadata");
            }
            new Select(seleniumSettings.getWebDriver().findElement(By.name("imageLatConfigFieldId"))).selectByVisibleText(configFieldVo.getConfigFieldEfile().getImageLatitude());
            new Select(seleniumSettings.getWebDriver().findElement(By.name("imageLongConfigFieldId"))).selectByVisibleText(configFieldVo.getConfigFieldEfile().getImageLongitude());
            new Select(seleniumSettings.getWebDriver().findElement(By.name("imageTimeConfigFieldId"))).selectByVisibleText(configFieldVo.getConfigFieldEfile().getImageTimeSnapshot());
            new Select(seleniumSettings.getWebDriver().findElement(By.name("imageResizeMode"))).selectByVisibleText(configFieldVo.getConfigFieldEfile().getResizeMode());
            seleniumSettings.getWebDriver().findElement(By.name("imageWidth")).clear();
            seleniumSettings.getWebDriver().findElement(By.name("imageWidth")).sendKeys(configFieldVo.getConfigFieldEfile().getResizeWidth());
            seleniumSettings.getWebDriver().findElement(By.name("imageHeight")).clear();
            seleniumSettings.getWebDriver().findElement(By.name("imageHeight")).sendKeys(configFieldVo.getConfigFieldEfile().getResizeHeight());
            if ((configFieldVo.getConfigFieldEfile().getRotate().equals("YES") && !checkbox.isCheckedByName("imageRotate"))
                    || (configFieldVo.getConfigFieldEfile().getRotate().equals("NO") && checkbox.isCheckedByName("imageRotate"))) {
                checkbox.clickByName("imageRotate");
            }
            if ((configFieldVo.getConfigFieldEfile().getLogBlobChanges().equals("YES") && !checkbox.isCheckedByName("logBlobChanges"))
                    || (configFieldVo.getConfigFieldEfile().getLogBlobChanges().equals("NO") && checkbox.isCheckedByName("logBlobChanges"))) {
                checkbox.clickByName("logBlobChanges");
            }
            if ((configFieldVo.getConfigFieldEfile().getUploadToAws().equals("YES") && !checkbox.isCheckedByName("uploadToS3Directly"))
                    || (configFieldVo.getConfigFieldEfile().getUploadToAws().equals("NO") && checkbox.isCheckedByName("uploadToS3Directly"))) {
                checkbox.clickByName("uploadToS3Directly");
            }
            psSelector.selectSpecificValue(By.id("btnautocaptionClientFile"), By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE + AbstractSeleniumCore.getGridIdx()), 1L, configFieldVo.getConfigFieldEfile().getAutocaptionTemplate(), 1L);

            tab.goToTab(1L); //General
        } else if (ConfigFieldType.TRACKOR_SELECTOR.equals(configFieldVo.getConfigFieldType())) {
            setSqlToCodeMirror("btnSQL", configFieldVo.getConfigFieldTrackorSelector().getSql());

            new Select(seleniumSettings.getWebDriver().findElement(By.name("ObjectTrackorType"))).selectByVisibleText(configFieldVo.getConfigFieldTrackorSelector().getTrackorType());
            seleniumSettings.getWebDriver().findElement(By.name("shortNameLabel")).clear();
            seleniumSettings.getWebDriver().findElement(By.name("shortNameLabel")).sendKeys(configFieldVo.getConfigFieldTrackorSelector().getShortName());
            if ((configFieldVo.getConfigFieldTrackorSelector().getMyThingsFilter().equals("YES") && !checkbox.isCheckedByName("useInMyThingsFilter"))
                    || (configFieldVo.getConfigFieldTrackorSelector().getMyThingsFilter().equals("NO") && checkbox.isCheckedByName("useInMyThingsFilter"))) {
                checkbox.clickByName("useInMyThingsFilter");
            }
            if ((configFieldVo.getConfigFieldTrackorSelector().getMyThingsMarker().equals("YES") && !checkbox.isCheckedByName("myThingsMarket"))
                    || (configFieldVo.getConfigFieldTrackorSelector().getMyThingsMarker().equals("NO") && checkbox.isCheckedByName("myThingsMarket"))) {
                checkbox.clickByName("myThingsMarket");
            }

            psSelector.selectSpecificValue(By.id("btnobjCf"), By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE + AbstractSeleniumCore.getGridIdx()), 1L, configFieldVo.getConfigFieldTrackorSelector().getDisplayField(), 1L);

            setSqlToCodeMirror("btnDefSQL", configFieldVo.getConfigFieldTrackorSelector().getDefValueSql());
        } else if (ConfigFieldType.TRACKOR_DROP_DOWN.equals(configFieldVo.getConfigFieldType())) {
            setSqlToCodeMirror("btnSQL", configFieldVo.getConfigFieldTrackorDropDown().getSql());

            new Select(seleniumSettings.getWebDriver().findElement(By.name("ObjectTrackorType"))).selectByVisibleText(configFieldVo.getConfigFieldTrackorDropDown().getTrackorType());
            seleniumSettings.getWebDriver().findElement(By.name("shortNameLabel")).clear();
            seleniumSettings.getWebDriver().findElement(By.name("shortNameLabel")).sendKeys(configFieldVo.getConfigFieldTrackorDropDown().getShortName());
            if ((configFieldVo.getConfigFieldTrackorDropDown().getMyThingsFilter().equals("YES") && !checkbox.isCheckedByName("useInMyThingsFilter"))
                    || (configFieldVo.getConfigFieldTrackorDropDown().getMyThingsFilter().equals("NO") && checkbox.isCheckedByName("useInMyThingsFilter"))) {
                checkbox.clickByName("useInMyThingsFilter");
            }
            if ((configFieldVo.getConfigFieldTrackorDropDown().getMyThingsMarker().equals("YES") && !checkbox.isCheckedByName("myThingsMarket"))
                    || (configFieldVo.getConfigFieldTrackorDropDown().getMyThingsMarker().equals("NO") && checkbox.isCheckedByName("myThingsMarket"))) {
                checkbox.clickByName("myThingsMarket");
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

            psSelector.selectSpecificValue(By.id("btntableName"), By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE + AbstractSeleniumCore.getGridIdx()), 3L, configFieldVo.getConfigFieldMultiSelector().getTable(), 2L);

            setSqlToCodeMirror("btnDefSQL", configFieldVo.getConfigFieldMultiSelector().getDefValueSql());
        } else if (ConfigFieldType.DATE_TIME.equals(configFieldVo.getConfigFieldType())) {
            assertElement.AssertText("fieldSize", "10");

            if ((configFieldVo.getConfigFieldDateTime().getShowSeconds().equals("YES") && !checkbox.isCheckedByName("showSeconds"))
                    || (configFieldVo.getConfigFieldDateTime().getShowSeconds().equals("NO") && checkbox.isCheckedByName("showSeconds"))) {
                checkbox.clickByName("showSeconds");
            }

            setSqlToCodeMirror("btnDefSQL", configFieldVo.getConfigFieldDateTime().getDefValueSql());
        } else if (ConfigFieldType.TIME.equals(configFieldVo.getConfigFieldType())) {
            assertElement.AssertText("fieldSize", "10");

            if ((configFieldVo.getConfigFieldTime().getShowSeconds().equals("YES") && !checkbox.isCheckedByName("showSeconds"))
                    || (configFieldVo.getConfigFieldTime().getShowSeconds().equals("NO") && checkbox.isCheckedByName("showSeconds"))) {
                checkbox.clickByName("showSeconds");
            }

            setSqlToCodeMirror("btnDefSQL", configFieldVo.getConfigFieldTime().getDefValueSql());
        } else {
            throw new SeleniumUnexpectedException("Not support ConfigFieldType. ConfigFieldType=" + configFieldVo.getConfigFieldType());
        }

        seleniumSettings.getWebDriver().findElement(By.name("description")).clear();
        seleniumSettings.getWebDriver().findElement(By.name("description")).sendKeys(configFieldVo.getDescription());

        seleniumSettings.getWebDriver().findElement(By.name("comments")).clear();
        seleniumSettings.getWebDriver().findElement(By.name("comments")).sendKeys(configFieldVo.getComment());

        if ((configFieldVo.getMandatory().equals("YES") && !checkbox.isCheckedByName("mandatory"))
                || (configFieldVo.getMandatory().equals("NO") && checkbox.isCheckedByName("mandatory"))) {
            checkbox.clickByName("mandatory");
        }

        if ((configFieldVo.getTwoColumns().equals("YES") && !checkbox.isCheckedByName("twoColsSpan"))
                || (configFieldVo.getTwoColumns().equals("NO") && checkbox.isCheckedByName("twoColsSpan"))) {
            checkbox.clickByName("twoColsSpan");
        }

        boolean removeLockable = false;
        if ((configFieldVo.getLockable().equals("YES") && !checkbox.isCheckedByName("lockable"))
                || (configFieldVo.getLockable().equals("NO") && checkbox.isCheckedByName("lockable"))) {
            if (configFieldVo.getLockable().equals("NO") && checkbox.isCheckedByName("lockable")) {
                removeLockable = true;
            }
            checkbox.clickByName("lockable");
        }

        if ((configFieldVo.getMultiple().equals("YES") && !checkbox.isCheckedByName("multiple"))
                || (configFieldVo.getMultiple().equals("NO") && checkbox.isCheckedByName("multiple"))) {
            checkbox.clickByName("multiple");
        }

        if ((configFieldVo.getReadOnly().equals("YES") && !checkbox.isCheckedByName("readOnly"))
                || (configFieldVo.getReadOnly().equals("NO") && checkbox.isCheckedByName("readOnly"))) {
            checkbox.clickByName("readOnly");
        }

        if ((configFieldVo.getCalcTotal().equals("YES") && !checkbox.isCheckedByName("calcTotal"))
                || (configFieldVo.getCalcTotal().equals("NO") && checkbox.isCheckedByName("calcTotal"))) {
            checkbox.clickByName("calcTotal");
        }

        if ((configFieldVo.getNotCloneValue().equals("YES") && !checkbox.isCheckedByName("notCloneFieldValue"))
                || (configFieldVo.getNotCloneValue().equals("NO") && checkbox.isCheckedByName("notCloneFieldValue"))) {
            checkbox.clickByName("notCloneFieldValue");
        }

        if ((configFieldVo.getNotCloneLock().equals("YES") && !checkbox.isCheckedByName("notCloneLocks"))
                || (configFieldVo.getNotCloneLock().equals("NO") && checkbox.isCheckedByName("notCloneLocks"))) {
            checkbox.clickByName("notCloneLocks");
        }

        if (removeLockable) {
            window.closeModalWithAlert(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE), AbstractSeleniumCore.MESSAGE_DELETE_LOCKABLE);
        } else {
            window.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        }
        wait.waitGridLoad(AbstractSeleniumCore.getGridIdx(), AbstractSeleniumCore.getGridIdx());
    }

    public void testOnForm(ConfigFieldVo configFieldVo) {
        window.openModal(By.id(AbstractSeleniumCore.BUTTON_EDIT_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        if (ConfigFieldType.ROLLUP.equals(configFieldVo.getConfigFieldType())) {
            jqueryWait.waitJQueryLoad();
        }

        assertElement.AssertSelect("TrackorTypeName", configFieldVo.getTrackorTypeLabel());
        assertElement.AssertSelect("dataType", configFieldVo.getConfigFieldType().getLabel());
        assertElement.AssertText("LabelName", configFieldVo.getLabel());
        assertElement.AssertText("configFieldName", configFieldVo.getName());

        assertElement.AssertSelect("componentsPackageId", configFieldVo.getCompPackage());

        assertElement.AssertSelect("colorId", configFieldVo.getColor());

        assertElement.AssertText("fieldWidth", configFieldVo.getWidth());

        if (ConfigFieldType.TEXT.equals(configFieldVo.getConfigFieldType())) {
            assertElement.AssertText("fieldSize", configFieldVo.getConfigFieldText().getLength());

            checkSqlInCodeMirror("btnDefSQL", configFieldVo.getConfigFieldText().getDefValueSql());
        } else if (ConfigFieldType.NUMBER.equals(configFieldVo.getConfigFieldType())) {
            assertElement.AssertText("fieldSize", configFieldVo.getConfigFieldNumber().getLength());

            assertElement.AssertText("prefix", configFieldVo.getConfigFieldNumber().getPrefix());
            assertElement.AssertText("suffix", configFieldVo.getConfigFieldNumber().getSuffix());
            assertElement.AssertText("numDecimals", configFieldVo.getConfigFieldNumber().getDecimal());
            assertElement.AssertText("negativeColor", configFieldVo.getConfigFieldNumber().getNegativeColor());
            assertElement.AssertText("positiveColor", configFieldVo.getConfigFieldNumber().getPositiveColor());

            assertElement.AssertCheckBoxNew("parensForNegative", configFieldVo.getConfigFieldNumber().getParensForNegative());
            assertElement.AssertCheckBoxNew("separateThousands", configFieldVo.getConfigFieldNumber().getSeparateThousands());

            checkSqlInCodeMirror("btnDefSQL", configFieldVo.getConfigFieldNumber().getDefValueSql());
        } else if (ConfigFieldType.DATE.equals(configFieldVo.getConfigFieldType())) {
            assertElement.AssertText("fieldSize", "10");

            checkSqlInCodeMirror("btnDefSQL", configFieldVo.getConfigFieldDate().getDefValueSql());
        } else if (ConfigFieldType.CHECKBOX.equals(configFieldVo.getConfigFieldType())) {
            assertElement.AssertText("fieldSize", "1");

            checkSqlInCodeMirror("btnDefSQL", configFieldVo.getConfigFieldCheckbox().getDefValueSql());
        } else if (ConfigFieldType.DROP_DOWN.equals(configFieldVo.getConfigFieldType())) {
            assertElement.AssertRadioPsSelector("tableName", "btntableName", AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + 0L, configFieldVo.getConfigFieldDropDown().getTable(), 2L, true);

            checkSqlInCodeMirror("btnDefSQL", configFieldVo.getConfigFieldDropDown().getDefValueSql());
        } else if (ConfigFieldType.MEMO.equals(configFieldVo.getConfigFieldType())) {
            assertElement.AssertText("fieldSize", configFieldVo.getConfigFieldMemo().getLength());
            assertElement.AssertText("linesQty", configFieldVo.getConfigFieldMemo().getLines());

            checkSqlInCodeMirror("btnDefSQL", configFieldVo.getConfigFieldMemo().getDefValueSql());
        } else if (ConfigFieldType.WIKI.equals(configFieldVo.getConfigFieldType())) {
            assertElement.AssertText("fieldSize", configFieldVo.getConfigFieldWiki().getLength());
            assertElement.AssertText("linesQty", configFieldVo.getConfigFieldWiki().getLines());

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
            assertElement.AssertRadioPsSelector("tableName", "btntableName", AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + 0L, configFieldVo.getConfigFieldSelector().getTable(), 2L, true);

            checkSqlInCodeMirror("btnDefSQL", configFieldVo.getConfigFieldSelector().getDefValueSql());
        } else if (ConfigFieldType.LATITUDE.equals(configFieldVo.getConfigFieldType())) {
            assertElement.AssertText("fieldSize", "15");

            checkSqlInCodeMirror("btnDefSQL", configFieldVo.getConfigFieldLatitude().getDefValueSql());
        } else if (ConfigFieldType.LONGITUDE.equals(configFieldVo.getConfigFieldType())) {
            assertElement.AssertText("fieldSize", "15");

            checkSqlInCodeMirror("btnDefSQL", configFieldVo.getConfigFieldLongitude().getDefValueSql());
        } else if (ConfigFieldType.ELECTRONIC_FILE.equals(configFieldVo.getConfigFieldType())) {
            tab.goToTab(2L); //Image Settings

            assertElement.AssertCheckBoxNew("imageExtractMetadata", configFieldVo.getConfigFieldEfile().getExtractMetadata());
            assertElement.AssertSelect("imageLatConfigFieldId", configFieldVo.getConfigFieldEfile().getImageLatitude());
            assertElement.AssertSelect("imageLongConfigFieldId", configFieldVo.getConfigFieldEfile().getImageLongitude());
            assertElement.AssertSelect("imageTimeConfigFieldId", configFieldVo.getConfigFieldEfile().getImageTimeSnapshot());
            assertElement.AssertSelect("imageResizeMode", configFieldVo.getConfigFieldEfile().getResizeMode());
            assertElement.AssertText("imageWidth", configFieldVo.getConfigFieldEfile().getResizeWidth());
            assertElement.AssertText("imageHeight", configFieldVo.getConfigFieldEfile().getResizeHeight());
            assertElement.AssertCheckBoxNew("imageRotate", configFieldVo.getConfigFieldEfile().getRotate());
            assertElement.AssertCheckBoxNew("logBlobChanges", configFieldVo.getConfigFieldEfile().getLogBlobChanges());
            assertElement.AssertCheckBoxNew("uploadToS3Directly", configFieldVo.getConfigFieldEfile().getUploadToAws());
            assertElement.AssertRadioPsSelector("autocaptionClientFile", "btnautocaptionClientFile", AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + 0L, configFieldVo.getConfigFieldEfile().getAutocaptionTemplate(), 1L, true);

            tab.goToTab(1L); //General
        } else if (ConfigFieldType.TRACKOR_SELECTOR.equals(configFieldVo.getConfigFieldType())) {
            checkSqlInCodeMirror("btnSQL", configFieldVo.getConfigFieldTrackorSelector().getSql());

            assertElement.AssertSelect("ObjectTrackorType", configFieldVo.getConfigFieldTrackorSelector().getTrackorType());
            assertElement.AssertText("shortNameLabel", configFieldVo.getConfigFieldTrackorSelector().getShortName());
            assertElement.AssertCheckBoxNew("useInMyThingsFilter", configFieldVo.getConfigFieldTrackorSelector().getMyThingsFilter());
            assertElement.AssertCheckBoxNew("myThingsMarket", configFieldVo.getConfigFieldTrackorSelector().getMyThingsMarker());

            assertElement.AssertRadioPsSelector("objCf", "btnobjCf", AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + 0L, configFieldVo.getConfigFieldTrackorSelector().getDisplayField(), 1L, true);

            checkSqlInCodeMirror("btnDefSQL", configFieldVo.getConfigFieldTrackorSelector().getDefValueSql());
        } else if (ConfigFieldType.TRACKOR_DROP_DOWN.equals(configFieldVo.getConfigFieldType())) {
            checkSqlInCodeMirror("btnSQL", configFieldVo.getConfigFieldTrackorDropDown().getSql());

            assertElement.AssertSelect("ObjectTrackorType", configFieldVo.getConfigFieldTrackorDropDown().getTrackorType());
            assertElement.AssertText("shortNameLabel", configFieldVo.getConfigFieldTrackorDropDown().getShortName());
            assertElement.AssertCheckBoxNew("useInMyThingsFilter", configFieldVo.getConfigFieldTrackorDropDown().getMyThingsFilter());
            assertElement.AssertCheckBoxNew("myThingsMarket", configFieldVo.getConfigFieldTrackorDropDown().getMyThingsMarker());

            checkSqlInCodeMirror("btnDefSQL", configFieldVo.getConfigFieldTrackorDropDown().getDefValueSql());
        } else if (ConfigFieldType.CALCULATED.equals(configFieldVo.getConfigFieldType())) {
            checkSqlInCodeMirror("btnSQL", configFieldVo.getConfigFieldCalculated().getSql());
        } else if (ConfigFieldType.HYPERLINK.equals(configFieldVo.getConfigFieldType())) {
            assertElement.AssertText("fieldSize", configFieldVo.getConfigFieldHyperlink().getLength());
        } else if (ConfigFieldType.ROLLUP.equals(configFieldVo.getConfigFieldType())) {
            //TODO filter

            assertElement.AssertSelect("lstRollupXitorTypeID", configFieldVo.getConfigFieldRollup().getTrackorType());
        } else if (ConfigFieldType.MULTI_SELECTOR.equals(configFieldVo.getConfigFieldType())) {
            assertElement.AssertText("linesQty", configFieldVo.getConfigFieldMultiSelector().getLines());

            assertElement.AssertRadioPsSelector("tableName", "btntableName", AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + 0L, configFieldVo.getConfigFieldMultiSelector().getTable(), 2L, true);

            checkSqlInCodeMirror("btnDefSQL", configFieldVo.getConfigFieldMultiSelector().getDefValueSql());
        } else if (ConfigFieldType.DATE_TIME.equals(configFieldVo.getConfigFieldType())) {
            assertElement.AssertText("fieldSize", "10");

            assertElement.AssertCheckBoxNew("showSeconds", configFieldVo.getConfigFieldDateTime().getShowSeconds());

            checkSqlInCodeMirror("btnDefSQL", configFieldVo.getConfigFieldDateTime().getDefValueSql());
        } else if (ConfigFieldType.TIME.equals(configFieldVo.getConfigFieldType())) {
            assertElement.AssertText("fieldSize", "10");

            assertElement.AssertCheckBoxNew("showSeconds", configFieldVo.getConfigFieldTime().getShowSeconds());

            checkSqlInCodeMirror("btnDefSQL", configFieldVo.getConfigFieldTime().getDefValueSql());
        } else {
            throw new SeleniumUnexpectedException("Not support ConfigFieldType. ConfigFieldType=" + configFieldVo.getConfigFieldType());
        }

        assertElement.AssertText("description", configFieldVo.getDescription());
        assertElement.AssertText("comments", configFieldVo.getComment());

        assertElement.AssertCheckBoxNew("mandatory", configFieldVo.getMandatory());
        assertElement.AssertCheckBoxNew("twoColsSpan", configFieldVo.getTwoColumns());
        assertElement.AssertCheckBoxNew("lockable", configFieldVo.getLockable());
        assertElement.AssertCheckBoxNew("multiple", configFieldVo.getMultiple());

        assertElement.AssertCheckBoxNew("readOnly", configFieldVo.getReadOnly());
        assertElement.AssertCheckBoxNew("calcTotal", configFieldVo.getCalcTotal());
        assertElement.AssertCheckBoxNew("notCloneFieldValue", configFieldVo.getNotCloneValue());
        assertElement.AssertCheckBoxNew("notCloneLocks", configFieldVo.getNotCloneLock());

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

    public void testInGrid(Long gridId, Long rowIndex, ConfigFieldVo configFieldVo) {
        Map<Long, String> gridVals = new HashMap<Long, String>();

        gridVals.put(js.getColumnIndexByLabel(gridId, "Field Name"), configFieldVo.getName());
        gridVals.put(js.getColumnIndexByLabel(gridId, "Field Label"), configFieldVo.getLabel());

        if (ConfigFieldType.CHECKBOX.equals(configFieldVo.getConfigFieldType())) {
            gridVals.put(js.getColumnIndexByLabel(gridId, "Max Length"), "1");
        } else if (ConfigFieldType.DATE.equals(configFieldVo.getConfigFieldType())) {
            gridVals.put(js.getColumnIndexByLabel(gridId, "Max Length"), "10");
        } else if (ConfigFieldType.DATE_TIME.equals(configFieldVo.getConfigFieldType())) {
            gridVals.put(js.getColumnIndexByLabel(gridId, "Max Length"), "10");
        } else if (ConfigFieldType.TIME.equals(configFieldVo.getConfigFieldType())) {
            gridVals.put(js.getColumnIndexByLabel(gridId, "Max Length"), "10");
        } else if (ConfigFieldType.LATITUDE.equals(configFieldVo.getConfigFieldType())) {
            gridVals.put(js.getColumnIndexByLabel(gridId, "Max Length"), "15");
        } else if (ConfigFieldType.LONGITUDE.equals(configFieldVo.getConfigFieldType())) {
            gridVals.put(js.getColumnIndexByLabel(gridId, "Max Length"), "15");
        } else if (ConfigFieldType.HYPERLINK.equals(configFieldVo.getConfigFieldType())) {
            gridVals.put(js.getColumnIndexByLabel(gridId, "Max Length"), configFieldVo.getConfigFieldHyperlink().getLength());
        } else if (ConfigFieldType.MEMO.equals(configFieldVo.getConfigFieldType())) {
            gridVals.put(js.getColumnIndexByLabel(gridId, "Max Length"), configFieldVo.getConfigFieldMemo().getLength());
        } else if (ConfigFieldType.WIKI.equals(configFieldVo.getConfigFieldType())) {
            gridVals.put(js.getColumnIndexByLabel(gridId, "Max Length"), configFieldVo.getConfigFieldWiki().getLength());
        } else if (ConfigFieldType.TEXT.equals(configFieldVo.getConfigFieldType())) {
            gridVals.put(js.getColumnIndexByLabel(gridId, "Max Length"), configFieldVo.getConfigFieldText().getLength());
        } else if (ConfigFieldType.NUMBER.equals(configFieldVo.getConfigFieldType())) {
            gridVals.put(js.getColumnIndexByLabel(gridId, "Max Length"), configFieldVo.getConfigFieldNumber().getLength());
        } else {
            gridVals.put(js.getColumnIndexByLabel(gridId, "Max Length"), "");
        }

        if (ConfigFieldType.DROP_DOWN.equals(configFieldVo.getConfigFieldType())) {
            gridVals.put(js.getColumnIndexByLabel(gridId, "Table Name"), configFieldVo.getConfigFieldDropDown().getTable());
        } else if (ConfigFieldType.SELECTOR.equals(configFieldVo.getConfigFieldType())) {
            gridVals.put(js.getColumnIndexByLabel(gridId, "Table Name"), configFieldVo.getConfigFieldSelector().getTable());
        } else if (ConfigFieldType.MULTI_SELECTOR.equals(configFieldVo.getConfigFieldType())) {
            gridVals.put(js.getColumnIndexByLabel(gridId, "Table Name"), configFieldVo.getConfigFieldMultiSelector().getTable());
        } else {
            gridVals.put(js.getColumnIndexByLabel(gridId, "Table Name"), "");
        }

        gridVals.put(js.getColumnIndexByLabel(gridId, "Data Type"), configFieldVo.getConfigFieldType().getLabel());

        if (ConfigFieldType.MEMO.equals(configFieldVo.getConfigFieldType())) {
            gridVals.put(js.getColumnIndexByLabel(gridId, "Lines Qty"), configFieldVo.getConfigFieldMemo().getLines());
        } else if (ConfigFieldType.WIKI.equals(configFieldVo.getConfigFieldType())) {
            gridVals.put(js.getColumnIndexByLabel(gridId, "Lines Qty"), configFieldVo.getConfigFieldWiki().getLines());
        } else if (ConfigFieldType.MULTI_SELECTOR.equals(configFieldVo.getConfigFieldType())) {
            gridVals.put(js.getColumnIndexByLabel(gridId, "Lines Qty"), configFieldVo.getConfigFieldMultiSelector().getLines());
        } else {
            gridVals.put(js.getColumnIndexByLabel(gridId, "Lines Qty"), "");
        }

        gridVals.put(js.getColumnIndexByLabel(gridId, "Field Width (px)"), configFieldVo.getWidth());
        gridVals.put(js.getColumnIndexByLabel(gridId, "Description"), configFieldVo.getDescription());
        gridVals.put(js.getColumnIndexByLabel(gridId, "TRACKOR Type"), configFieldVo.getTrackorTypeLabel());
        gridVals.put(js.getColumnIndexByLabel(gridId, "Multiple Lines"), configFieldVo.getMultiple());
        gridVals.put(js.getColumnIndexByLabel(gridId, "Read Only"), configFieldVo.getReadOnly());

        if (ConfigFieldType.TRACKOR_SELECTOR.equals(configFieldVo.getConfigFieldType())) {
            gridVals.put(js.getColumnIndexByLabel(gridId, "Selector's Trackor Type"), configFieldVo.getConfigFieldTrackorSelector().getTrackorType());
            gridVals.put(js.getColumnIndexByLabel(gridId, "Short Name"), configFieldVo.getConfigFieldTrackorSelector().getShortName());
            gridVals.put(js.getColumnIndexByLabel(gridId, "Use in \"My Things\" filter"), configFieldVo.getConfigFieldTrackorSelector().getMyThingsFilter());
        } else if (ConfigFieldType.TRACKOR_DROP_DOWN.equals(configFieldVo.getConfigFieldType())) {
            gridVals.put(js.getColumnIndexByLabel(gridId, "Selector's Trackor Type"), configFieldVo.getConfigFieldTrackorDropDown().getTrackorType());
            gridVals.put(js.getColumnIndexByLabel(gridId, "Short Name"), configFieldVo.getConfigFieldTrackorDropDown().getShortName());
            gridVals.put(js.getColumnIndexByLabel(gridId, "Use in \"My Things\" filter"), configFieldVo.getConfigFieldTrackorDropDown().getMyThingsFilter());
        } else if (ConfigFieldType.ROLLUP.equals(configFieldVo.getConfigFieldType())) {
            gridVals.put(js.getColumnIndexByLabel(gridId, "Selector's Trackor Type"), configFieldVo.getConfigFieldRollup().getTrackorType());
            gridVals.put(js.getColumnIndexByLabel(gridId, "Short Name"), "");
            gridVals.put(js.getColumnIndexByLabel(gridId, "Use in \"My Things\" filter"), "NO");
        } else {
            gridVals.put(js.getColumnIndexByLabel(gridId, "Selector's Trackor Type"), "");
            gridVals.put(js.getColumnIndexByLabel(gridId, "Short Name"), "");
            gridVals.put(js.getColumnIndexByLabel(gridId, "Use in \"My Things\" filter"), "NO");
        }

        if (ConfigFieldType.ELECTRONIC_FILE.equals(configFieldVo.getConfigFieldType())) {
            gridVals.put(js.getColumnIndexByLabel(gridId, "Log BLOB changes"), configFieldVo.getConfigFieldEfile().getLogBlobChanges());
        } else {
            gridVals.put(js.getColumnIndexByLabel(gridId, "Log BLOB changes"), "NO");
        }

        gridVals.put(js.getColumnIndexByLabel(gridId, "Mandatory"), configFieldVo.getMandatory());
        gridVals.put(js.getColumnIndexByLabel(gridId, "Two Columns Span"), configFieldVo.getTwoColumns());
        gridVals.put(js.getColumnIndexByLabel(gridId, "Lockable"), configFieldVo.getLockable());

        if (ConfigFieldType.CALCULATED.equals(configFieldVo.getConfigFieldType())) {
            gridVals.put(js.getColumnIndexByLabel(gridId, "SQL Query"), configFieldVo.getConfigFieldCalculated().getSql());
        } else if (ConfigFieldType.DB_DROP_DOWN.equals(configFieldVo.getConfigFieldType())) {
            gridVals.put(js.getColumnIndexByLabel(gridId, "SQL Query"), configFieldVo.getConfigFieldDbDropDown().getSql());
        } else if (ConfigFieldType.DB_SELECTOR.equals(configFieldVo.getConfigFieldType())) {
            gridVals.put(js.getColumnIndexByLabel(gridId, "SQL Query"), configFieldVo.getConfigFieldDbSelector().getSql());
        } else if (ConfigFieldType.TRACKOR_DROP_DOWN.equals(configFieldVo.getConfigFieldType())) {
            gridVals.put(js.getColumnIndexByLabel(gridId, "SQL Query"), configFieldVo.getConfigFieldTrackorDropDown().getSql());
        } else if (ConfigFieldType.TRACKOR_SELECTOR.equals(configFieldVo.getConfigFieldType())) {
            gridVals.put(js.getColumnIndexByLabel(gridId, "SQL Query"), configFieldVo.getConfigFieldTrackorSelector().getSql());
        } else {
            gridVals.put(js.getColumnIndexByLabel(gridId, "SQL Query"), "");
        }

        if (ConfigFieldType.TRACKOR_SELECTOR.equals(configFieldVo.getConfigFieldType())) {
            gridVals.put(js.getColumnIndexByLabel(gridId, "Selector's Display Field"), configFieldVo.getConfigFieldTrackorSelector().getDisplayField());
        } else {
            gridVals.put(js.getColumnIndexByLabel(gridId, "Selector's Display Field"), "");
        }

        gridVals.put(js.getColumnIndexByLabel(gridId, "Calc Total"), configFieldVo.getCalcTotal());
        gridVals.put(js.getColumnIndexByLabel(gridId, "Comments"), configFieldVo.getComment());
        gridVals.put(js.getColumnIndexByLabel(gridId, "Don't Clone Field Value"), configFieldVo.getNotCloneValue());

        gridHelper.checkGridRowByRowIndexAndColIndex(gridId, rowIndex, gridVals);
    }

    private void setSqlToCodeMirror(String btnId, String sql) {
        window.openModal(By.id(btnId));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();
        wait.waitCodeMirrorLoad("SQL");
        wait.waitCodeMirrorHistorySize("SQL", 1L, 0L); //Wait until CodeMirror value is populated from window.dialogArguments['SQL'] js variable
        js.setValueToCodeMirror("SQL", sql);
        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
    }

    private void checkSqlInCodeMirror(String btnId, String sql) {
        window.openModal(By.id(btnId));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
        wait.waitFormLoad();
        wait.waitCodeMirrorLoad("SQL");
        wait.waitCodeMirrorHistorySize("SQL", 1L, 0L); //Wait until CodeMirror value is populated from window.dialogArguments['SQL'] js variable
        assertElement.AssertCodeMirror("SQL", sql);
        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

}