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
                "var result = \"\";" + 
                "result = result + \"[\";" + 
                "for (var field of formDesigner.getFields()) {" + 
                "    result = result + \"{\";" + 
                "    result = result + \"\\\"id\\\": \\\"\" + field.getId() + \"\\\", \";" + 
                "    result = result + \"\\\"name\\\": \\\"\" + field.getName() + \"\\\", \";" + 
                "    result = result + \"\\\"label\\\": \\\"\" + field.getLbl() + \"\\\", \";" + 
                "    result = result + \"\\\"prefix\\\": \\\"\" + field.getPrefix() + \"\\\", \";" + 
                "    result = result + \"\\\"row\\\": \\\"\" + field.getRow() + \"\\\", \";" + 
                "    result = result + \"\\\"col\\\": \\\"\" + field.getCol() + \"\\\"\";" + 
                "    result = result + \"},\";" + 
                "}" + 
                "if (result.length > 1) {" + 
                "    result = result.substring(0, result.length - 1);" + 
                "}" + 
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