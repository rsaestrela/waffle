package io.github.rsaestrela.waffle.processor;


import io.github.rsaestrela.waffle.model.ServiceDefinition;

import java.util.List;
import java.util.Objects;

public abstract class Processor<O extends OutputClass, E extends Throwable> {

    protected static final String TYPE_PACKAGE = ".type";
    protected static final String DOT = ".";
    protected final ServiceDefinition serviceDefinition;

    public Processor(ServiceDefinition serviceDefinition) {
        this.serviceDefinition = serviceDefinition;
    }

    public abstract List<O> process() throws E;

    public ServiceDefinition getServiceDefinition() {
        return serviceDefinition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Processor)) {
            return false;
        }
        Processor<?, ?> processor = (Processor<?, ?>) o;
        return Objects.equals(serviceDefinition, processor.serviceDefinition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serviceDefinition);
    }
}
