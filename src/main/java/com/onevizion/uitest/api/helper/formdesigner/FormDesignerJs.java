package com.onevizion.uitest.api.helper.formdesigner;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;
import com.onevizion.uitest.api.helper.Js;
import com.onevizion.uitest.api.vo.FormDesignerField;

@Component
class FormDesignerJs extends Js {

    List<FormDesignerField> getElementsOnForm() {
        String resultStr = (String) execJs2("" + 
                "var formDesigner = getFormDesigner();" + 
                "var result = \"\";" + 
                "result = result + \"[\";" + 
                "for (var key in  formDesigner.selectedValues) {" + 
                "    result = result + \"{\";" + 
                "    result = result + \"\\\"id\\\": \\\"\" + formDesigner.selectedValues[key].getId() + \"\\\", \";" + 
                "    result = result + \"\\\"name\\\": \\\"\" + formDesigner.selectedValues[key].getName() + \"\\\", \";" + 
                "    result = result + \"\\\"label\\\": \\\"\" + formDesigner.selectedValues[key].getLbl() + \"\\\", \";" + 
                "    result = result + \"\\\"prefix\\\": \\\"\" + formDesigner.selectedValues[key].getPrefLbl() + \"\\\", \";" + 
                "    result = result + \"\\\"row\\\": \\\"\" + formDesigner.selectedValues[key].getRow() + \"\\\", \";" + 
                "    result = result + \"\\\"col\\\": \\\"\" + formDesigner.selectedValues[key].getCol() + \"\\\"\";" + 
                "    result = result + \"},\";" + 
                "}" + 
                "result = result.substring(0, result.length - 1);" + 
                "result = result + \"]\";" + 
                "return result;");

        List<FormDesignerField> result = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            result = mapper.readValue(resultStr, new TypeReference<List<FormDesignerField>>(){});
        } catch (IOException e) {
            throw new SeleniumUnexpectedException(e);
        }

        return result;
    }

}