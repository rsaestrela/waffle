package io.github.rsaestrela.waffle.processor;


import io.github.rsaestrela.waffle.model.ServiceDefinition;

import java.util.List;
import java.util.Objects;

public abstract class Processor<O extends OutputClass, E extends Throwable> {

    protected static final String TYPE_PACKAGE = ".waffle.type";
    protected static final String DOT = ".";

    private final String namespace;

    public Processor(String namespace) {
        this.namespace = namespace;
    }

    public String getNamespace() {
        return namespace;
    }

    public abstract List<O> process(ServiceDefinition serviceDefinition) throws E;

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
