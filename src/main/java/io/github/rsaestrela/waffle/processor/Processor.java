package io.github.rsaestrela.waffle.processor;


import io.github.rsaestrela.waffle.exception.WaffleTypesException;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public abstract class Processor<T, O extends OutputClass> {

    protected static final String DOT = ".";
    protected static final String TYPE_PACKAGE = ".waffle.type";

    private final String namespace;

    public Processor(String namespace) {
        this.namespace = namespace;
    }

    public String getNamespace() {
        return namespace;
    }

    protected abstract Set<O> process(List<T> types) throws WaffleTypesException;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Processor)) {
            return false;
        }
        Processor<?, ?> processor = (Processor<?, ?>) o;
        return Objects.equals(namespace, processor.namespace);
    }

    @Override
    public int hashCode() {
        return Objects.hash(namespace);
    }

}
