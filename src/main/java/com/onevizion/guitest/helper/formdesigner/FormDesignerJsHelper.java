package com.onevizion.guitest.helper.formdesigner;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onevizion.guitest.exception.SeleniumUnexpectedException;
import com.onevizion.guitest.helper.JsHelper;
import com.onevizion.guitest.vo.FormDesignerField;

@Component
class FormDesignerJsHelper extends JsHelper {

    Boolean isReadyListBox() {
        //TODO firefox 59 bug
        //https://github.com/mozilla/geckodriver/issues/1067
        //https://bugzilla.mozilla.org/show_bug.cgi?id=1420923
        //return Boolean.valueOf(execJs("return window.listBox.isReady == true;"));
        return Boolean.valueOf(execJs("return getListBox().isReady == true;"));
    }

    List<FormDesignerField> getElementsOnForm() {
        String resultStr = (String) execJs2("" + 
                "var formDesigner = getFormDesigner();" + 
                "var result = \"\";" + 
                "result = result + \"[\";" + 
                "for (var key in  formDesigner.selectedValues) {" + 
                "    result = result + \"{\";" + 
                "    result = result + \"\\\"id\\\": \\\"\" + formDesigner.selectedValues[key].id + \"\\\", \";" + 
                "    result = result + \"\\\"name\\\": \\\"\" + formDesigner.selectedValues[key].name + \"\\\", \";" + 
                "    result = result + \"\\\"label\\\": \\\"\" + formDesigner.selectedValues[key].lbl + \"\\\", \";" + 
                "    result = result + \"\\\"prefix\\\": \\\"\" + formDesigner.selectedValues[key].prefLbl + \"\\\", \";" + 
                "    result = result + \"\\\"row\\\": \\\"\" + formDesigner.selectedValues[key].row + \"\\\", \";" + 
                "    result = result + \"\\\"col\\\": \\\"\" + formDesigner.selectedValues[key].col + \"\\\"\";" + 
                "    result = result + \"},\";" + 
                "}" + 
                "result = result.substring(0, result.length - 1);" + 
                "result = result + \"]\";" + 
                "return result;");

        List<FormDesignerField> result = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            result = mapper.readValue(resultStr, new TypeReference<List<FormDesignerField>>(){});
        } catch (JsonParseException e) {
            throw new SeleniumUnexpectedException(e);
        } catch (JsonMappingException e) {
            throw new SeleniumUnexpectedException(e);
        } catch (IOException e) {
            throw new SeleniumUnexpectedException(e);
        }

        return result;
    }

}