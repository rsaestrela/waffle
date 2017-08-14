package io.github.rsaestrela.waffle;

import io.github.rsaestrela.waffle.exception.WaffleClassWriterException;
import io.github.rsaestrela.waffle.exception.WaffleException;
import io.github.rsaestrela.waffle.exception.WaffleTypesException;
import io.github.rsaestrela.waffle.loader.ServiceDefinitionsLoader;
import io.github.rsaestrela.waffle.model.ServiceDefinitions;
import io.github.rsaestrela.waffle.processor.TypeOutputClass;
import io.github.rsaestrela.waffle.processor.TypesProcessor;
import io.github.rsaestrela.waffle.processor.validation.TypesValidator;
import io.github.rsaestrela.waffle.writer.TypeClassWriter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Waffle {

    public static void main(String[] args) {
        String namespace = "namespace";
        TypesProcessor typesProcessor = new TypesProcessor(namespace, new TypesValidator());
        TypeClassWriter typeClassWriter = new TypeClassWriter();
        try {
            ServiceDefinitions serviceDefinitions = new ServiceDefinitionsLoader().load(new HashSet<>(Arrays.asList(args)));
            serviceDefinitions.getServiceDefinitions().forEach(sd -> {
                try {
                    Set<TypeOutputClass> typeOutputClasses = typesProcessor.process(sd.getTypes());
                    for (TypeOutputClass typeOutputClass: typeOutputClasses) {
                        typeClassWriter.writeTypeClass(typeOutputClass);
                    }
                } catch (WaffleTypesException | WaffleClassWriterException e) {
                    e.printStackTrace();
                }
            });
        } catch (WaffleException we) {
            typeClassWriter.rollback();
        }
    }

}
