package com.onevizion.uitest.api.helper.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.springframework.stereotype.Component;
import org.testng.Assert;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.SeleniumSettings;
import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;
import com.onevizion.uitest.api.helper.AssertElement;
import com.onevizion.uitest.api.helper.Checkbox;
import com.onevizion.uitest.api.helper.Element;
import com.onevizion.uitest.api.helper.ElementWait;
import com.onevizion.uitest.api.helper.Grid;
import com.onevizion.uitest.api.helper.Js;
import com.onevizion.uitest.api.helper.PsSelector;
import com.onevizion.uitest.api.helper.Tab;
import com.onevizion.uitest.api.helper.Wait;
import com.onevizion.uitest.api.helper.Window;
import com.onevizion.uitest.api.helper.configfield.ConfigField;
import com.onevizion.uitest.api.helper.jquery.Jquery;
import com.onevizion.uitest.api.vo.ConfigFieldType;
import com.onevizion.uitest.api.vo.entity.ConfigFieldVo;
import com.onevizion.uitest.api.vo.entity.ConfigFieldVoEfileMetadata;

@Component
public class EntityConfigField {

    private static final String TT_NAME = "TrackorTypeName";
    private static final String DATA_TYPE = "dataType";
    private static final String LABEL = "LabelName";
    private static final String NAME = "configFieldName";
    private static final String COMP_PACKAGE = "componentsPackageId";
    private static final String COLOR = "colorId";
    private static final String WIDTH = "fieldWidth";

    private static final String SIZE = "fieldSize";
    private static final String PREFIX = "prefix";
    private static final String SUFFIX = "suffix";
    private static final String DECIMAL = "numDecimals";
    private static final String PARENS_FOR_NEGATIVE = "parensForNegative";
    private static final String SEPARATE_THOUSANDS = "separateThousands";
    private static final String LINES = "linesQty";
    private static final String RESIZE_MODE = "imageResizeMode";
    private static final String RESIZE_WIDTH = "imageWidth";
    private static final String RESIZE_HEIGHT = "imageHeight";
    private static final String ROTATE = "imageRotate";
    private static final String LOG_BLOB_CHANGES = "logBlobChanges";
    private static final String UPLOAD_TO_AWS = "uploadToS3Directly";

    private static final String DESCRIPTION = "description";
    private static final String COMMENT = "comments";
    private static final String MANDATORY = "mandatory";
    private static final String TWO_COLUMNS = "twoColsSpan";
    private static final String LOCKABLE = "lockable";
    private static final String MULTIPLE = "multiple";
    private static final String READ_ONLY = "readOnly";
    private static final String CALC_TOTAL = "calcTotal";
    private static final String NOT_CLONE_VALUE = "notCloneFieldValue";
    private static final String NOT_CLONE_LOCK = "notCloneLocks";

    @Resource
    private Window window;

    @Resource
    private Wait wait;

    @Resource
    private AssertElement assertElement;

    @Resource
    private Js js;

    @Resource
    private Grid grid;

    @Resource
    private Jquery jquery;

    @Resource
    private SeleniumSettings seleniumSettings;

    @Resource
    private ElementWait elementWait;

    @Resource
    private ConfigField configField;

    @Resource
    private Checkbox checkbox;

    @Resource
    private PsSelector psSelector;

    @Resource
    private Tab tab;

    @Resource
    private Element element;

    @Resource
    private EntityConfigFieldEfileMetadata entityConfigFieldEfileMetadata;

    public void add(ConfigFieldVo configFieldVo) {
        window.openModal(By.id(AbstractSeleniumCore.BUTTON_ADD_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        if (ConfigFieldType.ROLLUP.equals(configFieldVo.getConfigFieldType())) {
            jquery.waitLoad();
        }

        new Select(seleniumSettings.getWebDriver().findElement(By.name(TT_NAME))).selectByVisibleText(configFieldVo.getTrackorTypeLabel());

        new Select(seleniumSettings.getWebDriver().findElement(By.name(DATA_TYPE))).selectByVisibleText(configFieldVo.getConfigFieldType().getLabel());

        seleniumSettings.getWebDriver().findElement(By.name(LABEL)).sendKeys(configFieldVo.getLabel());

        String val = new Select(seleniumSettings.getWebDriver().findElement(By.name(TT_NAME))).getFirstSelectedOption().getAttribute("value");
        String cfPrefix = val.split(";")[1].replace(":", "").replace("-", "").replace(".", "");
        elementWait.waitElementAttributeByName(NAME, "value", cfPrefix + "_" + configFieldVo.getLabel().replace(" ", "_").replace("/", "").replace("-", "").toUpperCase());
        configField.waitFieldNameUpdated();

        seleniumSettings.getWebDriver().findElement(By.name(NAME)).clear();
        seleniumSettings.getWebDriver().findElement(By.name(NAME)).sendKeys(configFieldVo.getName());

        new Select(seleniumSettings.getWebDriver().findElement(By.name(COMP_PACKAGE))).selectByVisibleText(configFieldVo.getCompPackage());

        new Select(seleniumSettings.getWebDriver().findElement(By.name(COLOR))).selectByVisibleText(configFieldVo.getColor());

        seleniumSettings.getWebDriver().findElement(By.name(WIDTH)).clear();
        seleniumSettings.getWebDriver().findElement(By.name(WIDTH)).sendKeys(configFieldVo.getWidth());

        if (ConfigFieldType.TEXT.equals(configFieldVo.getConfigFieldType())) {
            seleniumSettings.getWebDriver().findElement(By.name(SIZE)).clear();
            seleniumSettings.getWebDriver().findElement(By.name(SIZE)).sendKeys(configFieldVo.getConfigFieldText().getLength());

            setSqlToCodeMirror("btnDefSQL", configFieldVo.getConfigFieldText().getDefValueSql());
        } else if (ConfigFieldType.NUMBER.equals(configFieldVo.getConfigFieldType())) {
            seleniumSettings.getWebDriver().findElement(By.name(SIZE)).clear();
            seleniumSettings.getWebDriver().findElement(By.name(SIZE)).sendKeys(configFieldVo.getConfigFieldNumber().getLength());

            seleniumSettings.getWebDriver().findElement(By.name(PREFIX)).clear();
            seleniumSettings.getWebDriver().findElement(By.name(PREFIX)).sendKeys(configFieldVo.getConfigFieldNumber().getPrefix());
            seleniumSettings.getWebDriver().findElement(By.name(SUFFIX)).clear();
            seleniumSettings.getWebDriver().findElement(By.name(SUFFIX)).sendKeys(configFieldVo.getConfigFieldNumber().getSuffix());
            seleniumSettings.getWebDriver().findElement(By.name(DECIMAL)).clear();
            seleniumSettings.getWebDriver().findElement(By.name(DECIMAL)).sendKeys(configFieldVo.getConfigFieldNumber().getDecimal());
            window.openModal(By.name("btnrgbNegColor"));
            seleniumSettings.getWebDriver().findElement(By.className("dhxcp_color_selector")).click();
            window.closeModal(By.className("dhx_button_save"));
            window.openModal(By.name("btnrgbPosColor"));
            seleniumSettings.getWebDriver().findElement(By.className("dhxcp_color_selector")).click();
            window.closeModal(By.className("dhx_button_save"));

            if ((configFieldVo.getConfigFieldNumber().getParensForNegative().equals("YES") && !checkbox.isCheckedByName(PARENS_FOR_NEGATIVE))
                    || (configFieldVo.getConfigFieldNumber().getParensForNegative().equals("NO") && checkbox.isCheckedByName(PARENS_FOR_NEGATIVE))) {
                checkbox.clickByName(PARENS_FOR_NEGATIVE);
            }
            if ((configFieldVo.getConfigFieldNumber().getSeparateThousands().equals("YES") && !checkbox.isCheckedByName(SEPARATE_THOUSANDS))
                    || (configFieldVo.getConfigFieldNumber().getSeparateThousands().equals("NO") && checkbox.isCheckedByName(SEPARATE_THOUSANDS))) {
                checkbox.clickByName(SEPARATE_THOUSANDS);
            }

            setSqlToCodeMirror("btnDefSQL", configFieldVo.getConfigFieldNumber().getDefValueSql());
        } else if (ConfigFieldType.DATE.equals(configFieldVo.getConfigFieldType())) {
            assertElement.assertText(SIZE, "10");

            setSqlToCodeMirror("btnDefSQL", configFieldVo.getConfigFieldDate().getDefValueSql());
        } else if (ConfigFieldType.CHECKBOX.equals(configFieldVo.getConfigFieldType())) {
            assertElement.assertText(SIZE, "1");

            setSqlToCodeMirror("btnDefSQL", configFieldVo.getConfigFieldCheckbox().getDefValueSql());
        } else if (ConfigFieldType.DROP_DOWN.equals(configFieldVo.getConfigFieldType())) {
            psSelector.selectSpecificValue(By.id("btntableName"), By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE + AbstractSeleniumCore.getGridIdx()), 3L, configFieldVo.getConfigFieldDropDown().getTable(), 2L);

            setSqlToCodeMirror("btnDefSQL", configFieldVo.getConfigFieldDropDown().getDefValueSql());
        } else if (ConfigFieldType.MEMO.equals(configFieldVo.getConfigFieldType())) {
            seleniumSettings.getWebDriver().findElement(By.name(SIZE)).clear();
            seleniumSettings.getWebDriver().findElement(By.name(SIZE)).sendKeys(configFieldVo.getConfigFieldMemo().getLength());
            seleniumSettings.getWebDriver().findElement(By.name(LINES)).clear();
            seleniumSettings.getWebDriver().findElement(By.name(LINES)).sendKeys(configFieldVo.getConfigFieldMemo().getLines());

            setSqlToCodeMirror("btnDefSQL", configFieldVo.getConfigFieldMemo().getDefValueSql());
        } else if (ConfigFieldType.WIKI.equals(configFieldVo.getConfigFieldType())) {
            seleniumSettings.getWebDriver().findElement(By.name(SIZE)).clear();
            seleniumSettings.getWebDriver().findElement(By.name(SIZE)).sendKeys(configFieldVo.getConfigFieldWiki().getLength());
            seleniumSettings.getWebDriver().findElement(By.name(LINES)).clear();
            seleniumSettings.getWebDriver().findElement(By.name(LINES)).sendKeys(configFieldVo.getConfigFieldWiki().getLines());

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
            assertElement.assertText(SIZE, "15");

            setSqlToCodeMirror("btnDefSQL", configFieldVo.getConfigFieldLatitude().getDefValueSql());
        } else if (ConfigFieldType.LONGITUDE.equals(configFieldVo.getConfigFieldType())) {
            assertElement.assertText(SIZE, "15");

            setSqlToCodeMirror("btnDefSQL", configFieldVo.getConfigFieldLongitude().getDefValueSql());
        } else if (ConfigFieldType.ELECTRONIC_FILE.equals(configFieldVo.getConfigFieldType())) {
            element.clickById(AbstractSeleniumCore.BUTTON_APPLY_ID);
            wait.waitReloadForm("reloaded=1");
            wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
            wait.waitFormLoad();

            tab.goToTab(2L); //Image Settings

            new Select(seleniumSettings.getWebDriver().findElement(By.name(RESIZE_MODE))).selectByVisibleText(configFieldVo.getConfigFieldEfile().getResizeMode());
            if (!configFieldVo.getConfigFieldEfile().getResizeWidth().equals(seleniumSettings.getWebDriver().findElement(By.name(RESIZE_WIDTH)).getAttribute("value"))) {
                seleniumSettings.getWebDriver().findElement(By.name(RESIZE_WIDTH)).clear();
                seleniumSettings.getWebDriver().findElement(By.name(RESIZE_WIDTH)).sendKeys(configFieldVo.getConfigFieldEfile().getResizeWidth());
            }
            if (!configFieldVo.getConfigFieldEfile().getResizeHeight().equals(seleniumSettings.getWebDriver().findElement(By.name(RESIZE_HEIGHT)).getAttribute("value"))) {
                seleniumSettings.getWebDriver().findElement(By.name(RESIZE_HEIGHT)).clear();
                seleniumSettings.getWebDriver().findElement(By.name(RESIZE_HEIGHT)).sendKeys(configFieldVo.getConfigFieldEfile().getResizeHeight());
            }
            if ((configFieldVo.getConfigFieldEfile().getRotate().equals("YES") && !checkbox.isCheckedByName(ROTATE))
                    || (configFieldVo.getConfigFieldEfile().getRotate().equals("NO") && checkbox.isCheckedByName(ROTATE))) {
                checkbox.clickByName(ROTATE);
            }
            if ((configFieldVo.getConfigFieldEfile().getLogBlobChanges().equals("YES") && !checkbox.isCheckedByName(LOG_BLOB_CHANGES))
                    || (configFieldVo.getConfigFieldEfile().getLogBlobChanges().equals("NO") && checkbox.isCheckedByName(LOG_BLOB_CHANGES))) {
                checkbox.clickByName(LOG_BLOB_CHANGES);
            }
            if ((configFieldVo.getConfigFieldEfile().getUploadToAws().equals("YES") && !checkbox.isCheckedByName(UPLOAD_TO_AWS))
                    || (configFieldVo.getConfigFieldEfile().getUploadToAws().equals("NO") && checkbox.isCheckedByName(UPLOAD_TO_AWS))) {
                checkbox.clickByName(UPLOAD_TO_AWS);
            }
            psSelector.selectSpecificValue(By.id("btnautocaptionClientFile"), By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE + AbstractSeleniumCore.getGridIdx()), 1L, configFieldVo.getConfigFieldEfile().getAutocaptionTemplate(), 1L);

            tab.goToTab(3L); //File Metadata
            wait.waitGridLoad(3L, 3L);

            List<ConfigFieldVoEfileMetadata> metadatas = configFieldVo.getConfigFieldEfile().getMetadatas();
            for (ConfigFieldVoEfileMetadata metadata : metadatas) {
                entityConfigFieldEfileMetadata.add(metadata);
            }

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
            seleniumSettings.getWebDriver().findElement(By.name(SIZE)).clear();
            seleniumSettings.getWebDriver().findElement(By.name(SIZE)).sendKeys(configFieldVo.getConfigFieldHyperlink().getLength());
        } else if (ConfigFieldType.ROLLUP.equals(configFieldVo.getConfigFieldType())) {
            //TODO filter

            new Select(seleniumSettings.getWebDriver().findElement(By.name("lstRollupXitorTypeID"))).selectByVisibleText(configFieldVo.getConfigFieldRollup().getTrackorType());
        } else if (ConfigFieldType.MULTI_SELECTOR.equals(configFieldVo.getConfigFieldType())) {
            seleniumSettings.getWebDriver().findElement(By.name(LINES)).clear();
            seleniumSettings.getWebDriver().findElement(By.name(LINES)).sendKeys(configFieldVo.getConfigFieldMultiSelector().getLines());

            psSelector.selectSpecificValue(By.id("btntableName"), By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE + AbstractSeleniumCore.getGridIdx()), 3L, configFieldVo.getConfigFieldMultiSelector().getTable(), 2L);

            setSqlToCodeMirror("btnDefSQL", configFieldVo.getConfigFieldMultiSelector().getDefValueSql());
        } else if (ConfigFieldType.DATE_TIME.equals(configFieldVo.getConfigFieldType())) {
            assertElement.assertText(SIZE, "10");

            if ((configFieldVo.getConfigFieldDateTime().getShowSeconds().equals("YES") && !checkbox.isCheckedByName("showSeconds"))
                    || (configFieldVo.getConfigFieldDateTime().getShowSeconds().equals("NO") && checkbox.isCheckedByName("showSeconds"))) {
                checkbox.clickByName("showSeconds");
            }

            setSqlToCodeMirror("btnDefSQL", configFieldVo.getConfigFieldDateTime().getDefValueSql());
        } else if (ConfigFieldType.TIME.equals(configFieldVo.getConfigFieldType())) {
            assertElement.assertText(SIZE, "10");

            if ((configFieldVo.getConfigFieldTime().getShowSeconds().equals("YES") && !checkbox.isCheckedByName("showSeconds"))
                    || (configFieldVo.getConfigFieldTime().getShowSeconds().equals("NO") && checkbox.isCheckedByName("showSeconds"))) {
                checkbox.clickByName("showSeconds");
            }

            setSqlToCodeMirror("btnDefSQL", configFieldVo.getConfigFieldTime().getDefValueSql());
        } else {
            throw new SeleniumUnexpectedException("Not support ConfigFieldType. ConfigFieldType=" + configFieldVo.getConfigFieldType());
        }

        seleniumSettings.getWebDriver().findElement(By.name(DESCRIPTION)).sendKeys(configFieldVo.getDescription());

        seleniumSettings.getWebDriver().findElement(By.name(COMMENT)).sendKeys(configFieldVo.getComment());

        if ((configFieldVo.getMandatory().equals("YES") && !checkbox.isCheckedByName(MANDATORY))
                || (configFieldVo.getMandatory().equals("NO") && checkbox.isCheckedByName(MANDATORY))) {
            checkbox.clickByName(MANDATORY);
        }

        if ((configFieldVo.getTwoColumns().equals("YES") && !checkbox.isCheckedByName(TWO_COLUMNS))
                || (configFieldVo.getTwoColumns().equals("NO") && checkbox.isCheckedByName(TWO_COLUMNS))) {
            checkbox.clickByName(TWO_COLUMNS);
        }

        if ((configFieldVo.getLockable().equals("YES") && !checkbox.isCheckedByName(LOCKABLE))
                || (configFieldVo.getLockable().equals("NO") && checkbox.isCheckedByName(LOCKABLE))) {
            checkbox.clickByName(LOCKABLE);
        }

        if ((configFieldVo.getMultiple().equals("YES") && !checkbox.isCheckedByName(MULTIPLE))
                || (configFieldVo.getMultiple().equals("NO") && checkbox.isCheckedByName(MULTIPLE))) {
            checkbox.clickByName(MULTIPLE);
        }

        if ((configFieldVo.getReadOnly().equals("YES") && !checkbox.isCheckedByName(READ_ONLY))
                || (configFieldVo.getReadOnly().equals("NO") && checkbox.isCheckedByName(READ_ONLY))) {
            checkbox.clickByName(READ_ONLY);
        }

        if ((configFieldVo.getCalcTotal().equals("YES") && !checkbox.isCheckedByName(CALC_TOTAL))
                || (configFieldVo.getCalcTotal().equals("NO") && checkbox.isCheckedByName(CALC_TOTAL))) {
            checkbox.clickByName(CALC_TOTAL);
        }

        if ((configFieldVo.getNotCloneValue().equals("YES") && !checkbox.isCheckedByName(NOT_CLONE_VALUE))
                || (configFieldVo.getNotCloneValue().equals("NO") && checkbox.isCheckedByName(NOT_CLONE_VALUE))) {
            checkbox.clickByName(NOT_CLONE_VALUE);
        }

        if ((configFieldVo.getNotCloneLock().equals("YES") && !checkbox.isCheckedByName(NOT_CLONE_LOCK))
                || (configFieldVo.getNotCloneLock().equals("NO") && checkbox.isCheckedByName(NOT_CLONE_LOCK))) {
            checkbox.clickByName(NOT_CLONE_LOCK);
        }

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitGridLoad(AbstractSeleniumCore.getGridIdx(), AbstractSeleniumCore.getGridIdx());
    }

    public void edit(ConfigFieldVo configFieldVo) {
        window.openModal(By.id(AbstractSeleniumCore.BUTTON_EDIT_ID_BASE + AbstractSeleniumCore.getGridIdx()));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

        if (ConfigFieldType.ROLLUP.equals(configFieldVo.getConfigFieldType())) {
            jquery.waitLoad();
        }

        seleniumSettings.getWebDriver().findElement(By.name(LABEL)).clear();
        seleniumSettings.getWebDriver().findElement(By.name(LABEL)).sendKeys(configFieldVo.getLabel());

        seleniumSettings.getWebDriver().findElement(By.name(NAME)).clear();
        seleniumSettings.getWebDriver().findElement(By.name(NAME)).sendKeys(configFieldVo.getName());

        new Select(seleniumSettings.getWebDriver().findElement(By.name(COMP_PACKAGE))).selectByVisibleText(configFieldVo.getCompPackage());

        new Select(seleniumSettings.getWebDriver().findElement(By.name(COLOR))).selectByVisibleText(configFieldVo.getColor());

        seleniumSettings.getWebDriver().findElement(By.name(WIDTH)).clear();
        seleniumSettings.getWebDriver().findElement(By.name(WIDTH)).sendKeys(configFieldVo.getWidth());

        if (ConfigFieldType.TEXT.equals(configFieldVo.getConfigFieldType())) {
            seleniumSettings.getWebDriver().findElement(By.name(SIZE)).clear();
            seleniumSettings.getWebDriver().findElement(By.name(SIZE)).sendKeys(configFieldVo.getConfigFieldText().getLength());

            setSqlToCodeMirror("btnDefSQL", configFieldVo.getConfigFieldText().getDefValueSql());
        } else if (ConfigFieldType.NUMBER.equals(configFieldVo.getConfigFieldType())) {
            seleniumSettings.getWebDriver().findElement(By.name(SIZE)).clear();
            seleniumSettings.getWebDriver().findElement(By.name(SIZE)).sendKeys(configFieldVo.getConfigFieldNumber().getLength());

            seleniumSettings.getWebDriver().findElement(By.name(PREFIX)).clear();
            seleniumSettings.getWebDriver().findElement(By.name(PREFIX)).sendKeys(configFieldVo.getConfigFieldNumber().getPrefix());
            seleniumSettings.getWebDriver().findElement(By.name(SUFFIX)).clear();
            seleniumSettings.getWebDriver().findElement(By.name(SUFFIX)).sendKeys(configFieldVo.getConfigFieldNumber().getSuffix());
            seleniumSettings.getWebDriver().findElement(By.name(DECIMAL)).clear();
            seleniumSettings.getWebDriver().findElement(By.name(DECIMAL)).sendKeys(configFieldVo.getConfigFieldNumber().getDecimal());
            window.openModal(By.name("btnrgbNegColor"));
            seleniumSettings.getWebDriver().findElement(By.className("dhxcp_color_selector")).click();
            window.closeModal(By.className("dhx_button_save"));
            window.openModal(By.name("btnrgbPosColor"));
            seleniumSettings.getWebDriver().findElement(By.className("dhxcp_color_selector")).click();
            window.closeModal(By.className("dhx_button_save"));

            if ((configFieldVo.getConfigFieldNumber().getParensForNegative().equals("YES") && !checkbox.isCheckedByName(PARENS_FOR_NEGATIVE))
                    || (configFieldVo.getConfigFieldNumber().getParensForNegative().equals("NO") && checkbox.isCheckedByName(PARENS_FOR_NEGATIVE))) {
                checkbox.clickByName(PARENS_FOR_NEGATIVE);
            }
            if ((configFieldVo.getConfigFieldNumber().getSeparateThousands().equals("YES") && !checkbox.isCheckedByName(SEPARATE_THOUSANDS))
                    || (configFieldVo.getConfigFieldNumber().getSeparateThousands().equals("NO") && checkbox.isCheckedByName(SEPARATE_THOUSANDS))) {
                checkbox.clickByName(SEPARATE_THOUSANDS);
            }

            setSqlToCodeMirror("btnDefSQL", configFieldVo.getConfigFieldNumber().getDefValueSql());
        } else if (ConfigFieldType.DATE.equals(configFieldVo.getConfigFieldType())) {
            assertElement.assertText(SIZE, "10");

            setSqlToCodeMirror("btnDefSQL", configFieldVo.getConfigFieldDate().getDefValueSql());
        } else if (ConfigFieldType.CHECKBOX.equals(configFieldVo.getConfigFieldType())) {
            assertElement.assertText(SIZE, "1");

            setSqlToCodeMirror("btnDefSQL", configFieldVo.getConfigFieldCheckbox().getDefValueSql());
        } else if (ConfigFieldType.DROP_DOWN.equals(configFieldVo.getConfigFieldType())) {
            psSelector.selectSpecificValue(By.id("btntableName"), By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE + AbstractSeleniumCore.getGridIdx()), 3L, configFieldVo.getConfigFieldDropDown().getTable(), 2L);

            setSqlToCodeMirror("btnDefSQL", configFieldVo.getConfigFieldDropDown().getDefValueSql());
        } else if (ConfigFieldType.MEMO.equals(configFieldVo.getConfigFieldType())) {
            seleniumSettings.getWebDriver().findElement(By.name(SIZE)).clear();
            seleniumSettings.getWebDriver().findElement(By.name(SIZE)).sendKeys(configFieldVo.getConfigFieldMemo().getLength());
            seleniumSettings.getWebDriver().findElement(By.name(LINES)).clear();
            seleniumSettings.getWebDriver().findElement(By.name(LINES)).sendKeys(configFieldVo.getConfigFieldMemo().getLines());

            setSqlToCodeMirror("btnDefSQL", configFieldVo.getConfigFieldMemo().getDefValueSql());
        } else if (ConfigFieldType.WIKI.equals(configFieldVo.getConfigFieldType())) {
            seleniumSettings.getWebDriver().findElement(By.name(SIZE)).clear();
            seleniumSettings.getWebDriver().findElement(By.name(SIZE)).sendKeys(configFieldVo.getConfigFieldWiki().getLength());
            seleniumSettings.getWebDriver().findElement(By.name(LINES)).clear();
            seleniumSettings.getWebDriver().findElement(By.name(LINES)).sendKeys(configFieldVo.getConfigFieldWiki().getLines());

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
            assertElement.assertText(SIZE, "15");

            setSqlToCodeMirror("btnDefSQL", configFieldVo.getConfigFieldLatitude().getDefValueSql());
        } else if (ConfigFieldType.LONGITUDE.equals(configFieldVo.getConfigFieldType())) {
            assertElement.assertText(SIZE, "15");

            setSqlToCodeMirror("btnDefSQL", configFieldVo.getConfigFieldLongitude().getDefValueSql());
        } else if (ConfigFieldType.ELECTRONIC_FILE.equals(configFieldVo.getConfigFieldType())) {
            tab.goToTab(2L); //Image Settings

            new Select(seleniumSettings.getWebDriver().findElement(By.name(RESIZE_MODE))).selectByVisibleText(configFieldVo.getConfigFieldEfile().getResizeMode());
            if (!configFieldVo.getConfigFieldEfile().getResizeWidth().equals(seleniumSettings.getWebDriver().findElement(By.name(RESIZE_WIDTH)).getAttribute("value"))) {
                seleniumSettings.getWebDriver().findElement(By.name(RESIZE_WIDTH)).clear();
                seleniumSettings.getWebDriver().findElement(By.name(RESIZE_WIDTH)).sendKeys(configFieldVo.getConfigFieldEfile().getResizeWidth());
            }
            if (!configFieldVo.getConfigFieldEfile().getResizeHeight().equals(seleniumSettings.getWebDriver().findElement(By.name(RESIZE_HEIGHT)).getAttribute("value"))) {
                seleniumSettings.getWebDriver().findElement(By.name(RESIZE_HEIGHT)).clear();
                seleniumSettings.getWebDriver().findElement(By.name(RESIZE_HEIGHT)).sendKeys(configFieldVo.getConfigFieldEfile().getResizeHeight());
            }
            if ((configFieldVo.getConfigFieldEfile().getRotate().equals("YES") && !checkbox.isCheckedByName(ROTATE))
                    || (configFieldVo.getConfigFieldEfile().getRotate().equals("NO") && checkbox.isCheckedByName(ROTATE))) {
                checkbox.clickByName(ROTATE);
            }
            if ((configFieldVo.getConfigFieldEfile().getLogBlobChanges().equals("YES") && !checkbox.isCheckedByName(LOG_BLOB_CHANGES))
                    || (configFieldVo.getConfigFieldEfile().getLogBlobChanges().equals("NO") && checkbox.isCheckedByName(LOG_BLOB_CHANGES))) {
                checkbox.clickByName(LOG_BLOB_CHANGES);
            }
            if ((configFieldVo.getConfigFieldEfile().getUploadToAws().equals("YES") && !checkbox.isCheckedByName(UPLOAD_TO_AWS))
                    || (configFieldVo.getConfigFieldEfile().getUploadToAws().equals("NO") && checkbox.isCheckedByName(UPLOAD_TO_AWS))) {
                checkbox.clickByName(UPLOAD_TO_AWS);
            }
            psSelector.selectSpecificValue(By.id("btnautocaptionClientFile"), By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE + AbstractSeleniumCore.getGridIdx()), 1L, configFieldVo.getConfigFieldEfile().getAutocaptionTemplate(), 1L);

            tab.goToTab(3L); //File Metadata
            wait.waitGridLoad(3L, 3L);

            entityConfigFieldEfileMetadata.removeAll();

            List<ConfigFieldVoEfileMetadata> metadatas = configFieldVo.getConfigFieldEfile().getMetadatas();
            for (ConfigFieldVoEfileMetadata metadata : metadatas) {
                entityConfigFieldEfileMetadata.add(metadata);
            }

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
            seleniumSettings.getWebDriver().findElement(By.name(SIZE)).clear();
            seleniumSettings.getWebDriver().findElement(By.name(SIZE)).sendKeys(configFieldVo.getConfigFieldHyperlink().getLength());
        } else if (ConfigFieldType.ROLLUP.equals(configFieldVo.getConfigFieldType())) {
            //TODO filter

            new Select(seleniumSettings.getWebDriver().findElement(By.name("lstRollupXitorTypeID"))).selectByVisibleText(configFieldVo.getConfigFieldRollup().getTrackorType());
        } else if (ConfigFieldType.MULTI_SELECTOR.equals(configFieldVo.getConfigFieldType())) {
            seleniumSettings.getWebDriver().findElement(By.name(LINES)).clear();
            seleniumSettings.getWebDriver().findElement(By.name(LINES)).sendKeys(configFieldVo.getConfigFieldMultiSelector().getLines());

            psSelector.selectSpecificValue(By.id("btntableName"), By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE + AbstractSeleniumCore.getGridIdx()), 3L, configFieldVo.getConfigFieldMultiSelector().getTable(), 2L);

            setSqlToCodeMirror("btnDefSQL", configFieldVo.getConfigFieldMultiSelector().getDefValueSql());
        } else if (ConfigFieldType.DATE_TIME.equals(configFieldVo.getConfigFieldType())) {
            assertElement.assertText(SIZE, "10");

            if ((configFieldVo.getConfigFieldDateTime().getShowSeconds().equals("YES") && !checkbox.isCheckedByName("showSeconds"))
                    || (configFieldVo.getConfigFieldDateTime().getShowSeconds().equals("NO") && checkbox.isCheckedByName("showSeconds"))) {
                checkbox.clickByName("showSeconds");
            }

            setSqlToCodeMirror("btnDefSQL", configFieldVo.getConfigFieldDateTime().getDefValueSql());
        } else if (ConfigFieldType.TIME.equals(configFieldVo.getConfigFieldType())) {
            assertElement.assertText(SIZE, "10");

            if ((configFieldVo.getConfigFieldTime().getShowSeconds().equals("YES") && !checkbox.isCheckedByName("showSeconds"))
                    || (configFieldVo.getConfigFieldTime().getShowSeconds().equals("NO") && checkbox.isCheckedByName("showSeconds"))) {
                checkbox.clickByName("showSeconds");
            }

            setSqlToCodeMirror("btnDefSQL", configFieldVo.getConfigFieldTime().getDefValueSql());
        } else {
            throw new SeleniumUnexpectedException("Not support ConfigFieldType. ConfigFieldType=" + configFieldVo.getConfigFieldType());
        }

        seleniumSettings.getWebDriver().findElement(By.name(DESCRIPTION)).clear();
        seleniumSettings.getWebDriver().findElement(By.name(DESCRIPTION)).sendKeys(configFieldVo.getDescription());

        seleniumSettings.getWebDriver().findElement(By.name(COMMENT)).clear();
        seleniumSettings.getWebDriver().findElement(By.name(COMMENT)).sendKeys(configFieldVo.getComment());

        if ((configFieldVo.getMandatory().equals("YES") && !checkbox.isCheckedByName(MANDATORY))
                || (configFieldVo.getMandatory().equals("NO") && checkbox.isCheckedByName(MANDATORY))) {
            checkbox.clickByName(MANDATORY);
        }

        if ((configFieldVo.getTwoColumns().equals("YES") && !checkbox.isCheckedByName(TWO_COLUMNS))
                || (configFieldVo.getTwoColumns().equals("NO") && checkbox.isCheckedByName(TWO_COLUMNS))) {
            checkbox.clickByName(TWO_COLUMNS);
        }

        boolean removeLockable = false;
        if ((configFieldVo.getLockable().equals("YES") && !checkbox.isCheckedByName(LOCKABLE))
                || (configFieldVo.getLockable().equals("NO") && checkbox.isCheckedByName(LOCKABLE))) {
            if (configFieldVo.getLockable().equals("NO") && checkbox.isCheckedByName(LOCKABLE)) {
                removeLockable = true;
            }
            checkbox.clickByName(LOCKABLE);
        }

        if ((configFieldVo.getMultiple().equals("YES") && !checkbox.isCheckedByName(MULTIPLE))
                || (configFieldVo.getMultiple().equals("NO") && checkbox.isCheckedByName(MULTIPLE))) {
            checkbox.clickByName(MULTIPLE);
        }

        if ((configFieldVo.getReadOnly().equals("YES") && !checkbox.isCheckedByName(READ_ONLY))
                || (configFieldVo.getReadOnly().equals("NO") && checkbox.isCheckedByName(READ_ONLY))) {
            checkbox.clickByName(READ_ONLY);
        }

        if ((configFieldVo.getCalcTotal().equals("YES") && !checkbox.isCheckedByName(CALC_TOTAL))
                || (configFieldVo.getCalcTotal().equals("NO") && checkbox.isCheckedByName(CALC_TOTAL))) {
            checkbox.clickByName(CALC_TOTAL);
        }

        if ((configFieldVo.getNotCloneValue().equals("YES") && !checkbox.isCheckedByName(NOT_CLONE_VALUE))
                || (configFieldVo.getNotCloneValue().equals("NO") && checkbox.isCheckedByName(NOT_CLONE_VALUE))) {
            checkbox.clickByName(NOT_CLONE_VALUE);
        }

        if ((configFieldVo.getNotCloneLock().equals("YES") && !checkbox.isCheckedByName(NOT_CLONE_LOCK))
                || (configFieldVo.getNotCloneLock().equals("NO") && checkbox.isCheckedByName(NOT_CLONE_LOCK))) {
            checkbox.clickByName(NOT_CLONE_LOCK);
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
            jquery.waitLoad();
        }

        assertElement.assertSelect(TT_NAME, configFieldVo.getTrackorTypeLabel());
        assertElement.assertSelect(DATA_TYPE, configFieldVo.getConfigFieldType().getLabel());
        assertElement.assertText(LABEL, configFieldVo.getLabel());
        assertElement.assertText(NAME, configFieldVo.getName());

        assertElement.assertSelect(COMP_PACKAGE, configFieldVo.getCompPackage());

        assertElement.assertSelect(COLOR, configFieldVo.getColor());

        assertElement.assertText(WIDTH, configFieldVo.getWidth());

        if (ConfigFieldType.TEXT.equals(configFieldVo.getConfigFieldType())) {
            assertElement.assertText(SIZE, configFieldVo.getConfigFieldText().getLength());

            checkSqlInCodeMirror("btnDefSQL", configFieldVo.getConfigFieldText().getDefValueSql());
        } else if (ConfigFieldType.NUMBER.equals(configFieldVo.getConfigFieldType())) {
            assertElement.assertText(SIZE, configFieldVo.getConfigFieldNumber().getLength());

            assertElement.assertText(PREFIX, configFieldVo.getConfigFieldNumber().getPrefix());
            assertElement.assertText(SUFFIX, configFieldVo.getConfigFieldNumber().getSuffix());
            assertElement.assertText(DECIMAL, configFieldVo.getConfigFieldNumber().getDecimal());
            assertElement.assertText("negativeColor", configFieldVo.getConfigFieldNumber().getNegativeColor());
            assertElement.assertText("positiveColor", configFieldVo.getConfigFieldNumber().getPositiveColor());

            assertElement.assertCheckbox(PARENS_FOR_NEGATIVE, configFieldVo.getConfigFieldNumber().getParensForNegative());
            assertElement.assertCheckbox(SEPARATE_THOUSANDS, configFieldVo.getConfigFieldNumber().getSeparateThousands());

            checkSqlInCodeMirror("btnDefSQL", configFieldVo.getConfigFieldNumber().getDefValueSql());
        } else if (ConfigFieldType.DATE.equals(configFieldVo.getConfigFieldType())) {
            assertElement.assertText(SIZE, "10");

            checkSqlInCodeMirror("btnDefSQL", configFieldVo.getConfigFieldDate().getDefValueSql());
        } else if (ConfigFieldType.CHECKBOX.equals(configFieldVo.getConfigFieldType())) {
            assertElement.assertText(SIZE, "1");

            checkSqlInCodeMirror("btnDefSQL", configFieldVo.getConfigFieldCheckbox().getDefValueSql());
        } else if (ConfigFieldType.DROP_DOWN.equals(configFieldVo.getConfigFieldType())) {
            assertElement.assertRadioPsSelector("tableName", "btntableName", AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + 0L, configFieldVo.getConfigFieldDropDown().getTable(), 2L, true);

            checkSqlInCodeMirror("btnDefSQL", configFieldVo.getConfigFieldDropDown().getDefValueSql());
        } else if (ConfigFieldType.MEMO.equals(configFieldVo.getConfigFieldType())) {
            assertElement.assertText(SIZE, configFieldVo.getConfigFieldMemo().getLength());
            assertElement.assertText(LINES, configFieldVo.getConfigFieldMemo().getLines());

            checkSqlInCodeMirror("btnDefSQL", configFieldVo.getConfigFieldMemo().getDefValueSql());
        } else if (ConfigFieldType.WIKI.equals(configFieldVo.getConfigFieldType())) {
            assertElement.assertText(SIZE, configFieldVo.getConfigFieldWiki().getLength());
            assertElement.assertText(LINES, configFieldVo.getConfigFieldWiki().getLines());

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
            assertElement.assertRadioPsSelector("tableName", "btntableName", AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + 0L, configFieldVo.getConfigFieldSelector().getTable(), 2L, true);

            checkSqlInCodeMirror("btnDefSQL", configFieldVo.getConfigFieldSelector().getDefValueSql());
        } else if (ConfigFieldType.LATITUDE.equals(configFieldVo.getConfigFieldType())) {
            assertElement.assertText(SIZE, "15");

            checkSqlInCodeMirror("btnDefSQL", configFieldVo.getConfigFieldLatitude().getDefValueSql());
        } else if (ConfigFieldType.LONGITUDE.equals(configFieldVo.getConfigFieldType())) {
            assertElement.assertText(SIZE, "15");

            checkSqlInCodeMirror("btnDefSQL", configFieldVo.getConfigFieldLongitude().getDefValueSql());
        } else if (ConfigFieldType.ELECTRONIC_FILE.equals(configFieldVo.getConfigFieldType())) {
            tab.goToTab(2L); //Image Settings

            assertElement.assertSelect(RESIZE_MODE, configFieldVo.getConfigFieldEfile().getResizeMode());
            assertElement.assertText(RESIZE_WIDTH, configFieldVo.getConfigFieldEfile().getResizeWidth());
            assertElement.assertText(RESIZE_HEIGHT, configFieldVo.getConfigFieldEfile().getResizeHeight());
            assertElement.assertCheckbox(ROTATE, configFieldVo.getConfigFieldEfile().getRotate());
            assertElement.assertCheckbox(LOG_BLOB_CHANGES, configFieldVo.getConfigFieldEfile().getLogBlobChanges());
            assertElement.assertCheckbox(UPLOAD_TO_AWS, configFieldVo.getConfigFieldEfile().getUploadToAws());
            assertElement.assertRadioPsSelector("autocaptionClientFile", "btnautocaptionClientFile", AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + 0L, configFieldVo.getConfigFieldEfile().getAutocaptionTemplate(), 1L, true);

            tab.goToTab(3L); //File Metadata
            wait.waitGridLoad(3L, 3L);

            List<ConfigFieldVoEfileMetadata> metadatas = configFieldVo.getConfigFieldEfile().getMetadatas();
            Assert.assertEquals(grid.getGridRowsCount(3L), Long.valueOf(metadatas.size()));
            for (int i = 0; i < metadatas.size(); i++) {
                js.selectGridRow(3L, Long.valueOf(i));
                entityConfigFieldEfileMetadata.testInGrid(3L, Long.valueOf(i), metadatas.get(i));
                entityConfigFieldEfileMetadata.testOnForm(metadatas.get(i));
            }

            tab.goToTab(1L); //General
        } else if (ConfigFieldType.TRACKOR_SELECTOR.equals(configFieldVo.getConfigFieldType())) {
            checkSqlInCodeMirror("btnSQL", configFieldVo.getConfigFieldTrackorSelector().getSql());

            assertElement.assertSelect("ObjectTrackorType", configFieldVo.getConfigFieldTrackorSelector().getTrackorType());
            assertElement.assertText("shortNameLabel", configFieldVo.getConfigFieldTrackorSelector().getShortName());
            assertElement.assertCheckbox("useInMyThingsFilter", configFieldVo.getConfigFieldTrackorSelector().getMyThingsFilter());
            assertElement.assertCheckbox("myThingsMarket", configFieldVo.getConfigFieldTrackorSelector().getMyThingsMarker());

            assertElement.assertRadioPsSelector("objCf", "btnobjCf", AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + 0L, configFieldVo.getConfigFieldTrackorSelector().getDisplayField(), 1L, true);

            checkSqlInCodeMirror("btnDefSQL", configFieldVo.getConfigFieldTrackorSelector().getDefValueSql());
        } else if (ConfigFieldType.TRACKOR_DROP_DOWN.equals(configFieldVo.getConfigFieldType())) {
            checkSqlInCodeMirror("btnSQL", configFieldVo.getConfigFieldTrackorDropDown().getSql());

            assertElement.assertSelect("ObjectTrackorType", configFieldVo.getConfigFieldTrackorDropDown().getTrackorType());
            assertElement.assertText("shortNameLabel", configFieldVo.getConfigFieldTrackorDropDown().getShortName());
            assertElement.assertCheckbox("useInMyThingsFilter", configFieldVo.getConfigFieldTrackorDropDown().getMyThingsFilter());
            assertElement.assertCheckbox("myThingsMarket", configFieldVo.getConfigFieldTrackorDropDown().getMyThingsMarker());

            checkSqlInCodeMirror("btnDefSQL", configFieldVo.getConfigFieldTrackorDropDown().getDefValueSql());
        } else if (ConfigFieldType.CALCULATED.equals(configFieldVo.getConfigFieldType())) {
            checkSqlInCodeMirror("btnSQL", configFieldVo.getConfigFieldCalculated().getSql());
        } else if (ConfigFieldType.HYPERLINK.equals(configFieldVo.getConfigFieldType())) {
            assertElement.assertText(SIZE, configFieldVo.getConfigFieldHyperlink().getLength());
        } else if (ConfigFieldType.ROLLUP.equals(configFieldVo.getConfigFieldType())) {
            //TODO filter

            assertElement.assertSelect("lstRollupXitorTypeID", configFieldVo.getConfigFieldRollup().getTrackorType());
        } else if (ConfigFieldType.MULTI_SELECTOR.equals(configFieldVo.getConfigFieldType())) {
            assertElement.assertText(LINES, configFieldVo.getConfigFieldMultiSelector().getLines());

            assertElement.assertRadioPsSelector("tableName", "btntableName", AbstractSeleniumCore.BUTTON_CLOSE_ID_BASE + 0L, configFieldVo.getConfigFieldMultiSelector().getTable(), 2L, true);

            checkSqlInCodeMirror("btnDefSQL", configFieldVo.getConfigFieldMultiSelector().getDefValueSql());
        } else if (ConfigFieldType.DATE_TIME.equals(configFieldVo.getConfigFieldType())) {
            assertElement.assertText(SIZE, "10");

            assertElement.assertCheckbox("showSeconds", configFieldVo.getConfigFieldDateTime().getShowSeconds());

            checkSqlInCodeMirror("btnDefSQL", configFieldVo.getConfigFieldDateTime().getDefValueSql());
        } else if (ConfigFieldType.TIME.equals(configFieldVo.getConfigFieldType())) {
            assertElement.assertText(SIZE, "10");

            assertElement.assertCheckbox("showSeconds", configFieldVo.getConfigFieldTime().getShowSeconds());

            checkSqlInCodeMirror("btnDefSQL", configFieldVo.getConfigFieldTime().getDefValueSql());
        } else {
            throw new SeleniumUnexpectedException("Not support ConfigFieldType. ConfigFieldType=" + configFieldVo.getConfigFieldType());
        }

        assertElement.assertText(DESCRIPTION, configFieldVo.getDescription());
        assertElement.assertText(COMMENT, configFieldVo.getComment());

        assertElement.assertCheckbox(MANDATORY, configFieldVo.getMandatory());
        assertElement.assertCheckbox(TWO_COLUMNS, configFieldVo.getTwoColumns());
        assertElement.assertCheckbox(LOCKABLE, configFieldVo.getLockable());
        assertElement.assertCheckbox(MULTIPLE, configFieldVo.getMultiple());

        assertElement.assertCheckbox(READ_ONLY, configFieldVo.getReadOnly());
        assertElement.assertCheckbox(CALC_TOTAL, configFieldVo.getCalcTotal());
        assertElement.assertCheckbox(NOT_CLONE_VALUE, configFieldVo.getNotCloneValue());
        assertElement.assertCheckbox(NOT_CLONE_LOCK, configFieldVo.getNotCloneLock());

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

    public void testInGrid(Long gridId, Long rowIndex, ConfigFieldVo configFieldVo) {
        Map<Long, String> gridVals = new HashMap<>();

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

        grid.checkGridRowByRowIndexAndColIndex(gridId, rowIndex, gridVals);
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
        assertElement.assertCodeMirror("SQL", sql);
        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_CANCEL_ID_BASE));
    }

}