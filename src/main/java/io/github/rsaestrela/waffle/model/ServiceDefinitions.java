package io.github.rsaestrela.waffle.model;


import java.util.List;
import java.util.Objects;

public class ServiceDefinitions {

    private List<ServiceDefinition> serviceDefinitions;

    public List<ServiceDefinition> getServiceDefinitions() {
        return serviceDefinitions;
    }

    public void setServiceDefinitions(List<ServiceDefinition> serviceDefinitions) {
        this.serviceDefinitions = serviceDefinitions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ServiceDefinitions that = (ServiceDefinitions) o;
        return Objects.equals(serviceDefinitions, that.serviceDefinitions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serviceDefinitions);
    }
}
