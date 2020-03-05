package com.onevizion.uitest.api.helper;

import org.springframework.stereotype.Component;

@Component
class RelationSelectorJs extends Js {

    Boolean isReadyRelationSelector(Long gridIdx) {
        return Boolean.valueOf(execJs("return gridArr[" + gridIdx + "].isReadyRelationSelector == true;"));
    }

    Boolean isReadyMutationObserver(Long gridIdx) {
        return Boolean.valueOf(execJs("return newGui.dropDownOptions['lbParentsChildren" + gridIdx + "'].isReadyMutationObserver == true;"));
    }

    void setIsReadyToFalse(Long gridIdx) {
        execJs("gridArr[" + gridIdx + "].isReadyRelationSelector = false;");
        execJs("newGui.dropDownOptions['lbParentsChildren" + gridIdx + "'].isReadyMutationObserver = false;");
    }

}