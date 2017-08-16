package io.github.rsaestrela.waffle;

import io.github.rsaestrela.waffle.exception.WaffleException;
import io.github.rsaestrela.waffle.loader.ServiceDefinitionsLoader;
import io.github.rsaestrela.waffle.model.ServiceDefinition;
import io.github.rsaestrela.waffle.model.ServiceDefinitions;
import io.github.rsaestrela.waffle.processor.TypeOutputClass;
import io.github.rsaestrela.waffle.processor.TypesProcessor;
import io.github.rsaestrela.waffle.processor.validation.TypesValidator;
import io.github.rsaestrela.waffle.writer.Resource;
import io.github.rsaestrela.waffle.writer.TypeClassWriter;

import java.util.*;

public class Waffle {

    public static void main(String[] args) throws WaffleException {
        String namespace = "namespace";
        TypesProcessor typesProcessor = new TypesProcessor(namespace, new TypesValidator());
        TypeClassWriter typeClassWriter = new TypeClassWriter();
        try {
            ServiceDefinitions serviceDefinitions = new ServiceDefinitionsLoader().load(new HashSet<>(Arrays.asList(args)));
            for (ServiceDefinition serviceDefinition: serviceDefinitions.getServiceDefinitions()) {
                Set<TypeOutputClass> typeOutputClasses = typesProcessor.process(serviceDefinition.getTypes());
                for (TypeOutputClass typeOutputClass: typeOutputClasses) {
                    typeClassWriter.writeTypeClass(typeOutputClass);
                }
            }
        } catch (Exception e) {
            rollback(new ArrayList<>());
            throw e;
        }
    }

    public static boolean rollback(List<Resource> resources) {
        return resources.stream().allMatch(r -> r.getFile().delete());
    }

}
