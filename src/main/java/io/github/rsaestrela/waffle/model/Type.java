package io.github.rsaestrela.waffle.model;


import java.util.List;
import java.util.Objects;

public class Type {

    private String name;
    private List<Attribute> attributes;

    public Type() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Type)) {
            return false;
        }
        Type type = (Type) o;
        return Objects.equals(name, type.name) &&
                Objects.equals(attributes, type.attributes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, attributes);
    }
}
