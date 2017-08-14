package io.github.rsaestrela.waffle.processor;


import java.util.Map;

public class TypeOutputClass extends OutputClass {

    private String namespace;
    private String typeName;
    private Map<String, String> typeMembers;

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

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
}
