package io.github.rsaestrela.waffle.model;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ServiceDefinition {

    private String interfaceName;
    private String version;
    private String description;
    private String namespace;
    private String ownership;
    private List<Author> authors;
    private List<Type> types;
    private List<Operation> operations;

    public ServiceDefinition() {
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getOwnership() {
        return ownership;
    }

    public void setOwnership(String ownership) {
        this.ownership = ownership;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public List<Type> getTypes() {
        if (types == null) {
            types = new ArrayList<>();
        }
        return types;
    }

    public void setTypes(List<Type> types) {
        this.types = types;
    }

    public List<Operation> getOperations() {
        if (operations == null) {
            operations = new ArrayList<>();
        }
        return operations;
    }

    public void setOperations(List<Operation> operations) {
        this.operations = operations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ServiceDefinition that = (ServiceDefinition) o;
        return Objects.equals(interfaceName, that.interfaceName) &&
                Objects.equals(version, that.version) &&
                Objects.equals(description, that.description) &&
                Objects.equals(namespace, that.namespace) &&
                Objects.equals(ownership, that.ownership) &&
                Objects.equals(authors, that.authors) &&
                Objects.equals(types, that.types) &&
                Objects.equals(operations, that.operations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(interfaceName, version, description, namespace, ownership, authors, types, operations);
    }
}
