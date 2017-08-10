package io.github.rsaestrela.waffle.model;


import java.util.Objects;

public class Type {

    private String name;
    private String type;
    private boolean validator;
    private boolean nativeType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public boolean isNativeType() {
        return nativeType;
    }

    public void setNativeType(boolean nativeType) {
        this.nativeType = nativeType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Type type1 = (Type) o;
        return validator == type1.validator &&
                nativeType == type1.nativeType &&
                Objects.equals(name, type1.name) &&
                Objects.equals(type, type1.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, validator, nativeType);
    }
}
