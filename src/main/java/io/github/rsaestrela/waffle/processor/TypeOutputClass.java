package io.github.rsaestrela.waffle.processor;


import java.util.Map;
import java.util.Objects;

public class TypeOutputClass extends OutputClass {

    private String typeName;
    private Map<String, String> typeMembers;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Map<String, String> getTypeMembers() {
        return typeMembers;
    }

    public void setTypeMembers(Map<String, String> typeMembers) {
        this.typeMembers = typeMembers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TypeOutputClass)) {
            return false;
        }
        TypeOutputClass that = (TypeOutputClass) o;
        return Objects.equals(typeName, that.typeName) &&
                Objects.equals(typeMembers, that.typeMembers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(typeName, typeMembers);
    }
}
