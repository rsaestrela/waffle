package io.github.rsaestrela.waffle.model;

import java.util.Objects;

public class Attribute {

    private String attributeName;
    private String type;
    private boolean validator;

    public Attribute() {
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isValidator() {
        return validator;
    }

    public void setValidator(boolean validator) {
        this.validator = validator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Attribute)) {
            return false;
        }
        Attribute attribute = (Attribute) o;
        return validator == attribute.validator &&
                Objects.equals(attributeName, attribute.attributeName) &&
                Objects.equals(type, attribute.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attributeName, type, validator);
    }
}
