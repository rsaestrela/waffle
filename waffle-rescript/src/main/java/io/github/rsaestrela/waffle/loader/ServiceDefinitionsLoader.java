package io.github.rsaestrela.waffle.loader;

import io.github.rsaestrela.waffle.exception.WaffleDefinitionsException;
import io.github.rsaestrela.waffle.model.ServiceDefinition;
import io.github.rsaestrela.waffle.model.ServiceDefinitions;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Set;

public class ServiceDefinitionsLoader {

    public ServiceDefinitions load(Set<String> definitionFiles) throws WaffleDefinitionsException {
        ServiceDefinitions serviceDefinitions = new ServiceDefinitions(new ArrayList<>());
        Yaml yaml = new Yaml();
        for (String definitionFile: definitionFiles) {
            try (InputStream is = ClassLoader.getSystemResourceAsStream(definitionFile)) {
                serviceDefinitions.getServiceDefinitions().add(sanitized(yaml.loadAs(is, ServiceDefinition.class)));
            } catch (Exception e) {
                throw new WaffleDefinitionsException(e);
            }
        }
        return serviceDefinitions;
    }

    private ServiceDefinition sanitized(ServiceDefinition serviceDefinition) {
        serviceDefinition.getTypes().forEach(t -> {
            if (t.getAttributes() == null) {
                t.setAttributes(new ArrayList<>());
            }
        });
        return serviceDefinition;
    }
}
