package com.onevizion.uitest.api.vo.api.v3;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Color implements Comparable<Color> {

    private String name;
    private String label;
    private String orderNumber;
    private String color;
    private String fontColor;

    @JsonProperty("name")
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("label")
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @JsonProperty("order_number")
    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    @JsonProperty("color")
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @JsonProperty("font_color")
    public String getFontColor() {
        return fontColor;
    }

    public void setFontColor(String fontColor) {
        this.fontColor = fontColor;
    }

    @Override
    public int compareTo(Color o) {
        return this.name.compareTo(o.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, fontColor, label, name, orderNumber);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Color)) {
            return false;
        }
        Color other = (Color) obj;
        return Objects.equals(color, other.color) && Objects.equals(fontColor, other.fontColor)
                && Objects.equals(label, other.label) && Objects.equals(name, other.name)
                && Objects.equals(orderNumber, other.orderNumber);
    }

    @Override
    public String toString() {
        return "Color [name=" + name + ", label=" + label + ", orderNumber=" + orderNumber + ", color=" + color
                + ", fontColor=" + fontColor + "]";
    }

}