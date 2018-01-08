package com.likg.uiautotest.kw.domain;

/**
 * 页面元素
 */
public class PageElement {

    // 元素名称
    private String elementName;

    // 描述
    private String elementDesc;

    // 定位方式
    private String locationType;

    // 定位值
    private String locationValue;

    public String getElementName() {
        return elementName;
    }

    public void setElementName(String elementName) {
        this.elementName = elementName;
    }

    public String getElementDesc() {
        return elementDesc;
    }

    public void setElementDesc(String elementDesc) {
        this.elementDesc = elementDesc;
    }

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    public String getLocationValue() {
        return locationValue;
    }

    public void setLocationValue(String locationValue) {
        this.locationValue = locationValue;
    }
}
