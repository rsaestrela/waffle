package io.github.rsaestrela.waffle.processor;


import java.util.Objects;

public abstract class OutputClass {

    private String namespace;

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OutputClass)) {
            return false;
        }
        OutputClass that = (OutputClass) o;
        return Objects.equals(namespace, that.namespace);
    }

    @Override
    public int hashCode() {
        return Objects.hash(namespace);
    }
}
