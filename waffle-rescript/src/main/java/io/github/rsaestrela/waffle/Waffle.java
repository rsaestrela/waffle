package io.github.rsaestrela.waffle;

import io.github.rsaestrela.waffle.compiler.ClassCompiler;
import io.github.rsaestrela.waffle.exception.WaffleException;
import io.github.rsaestrela.waffle.loader.ServiceDefinitionsLoader;
import io.github.rsaestrela.waffle.model.ServiceDefinition;
import io.github.rsaestrela.waffle.model.ServiceDefinitions;
import io.github.rsaestrela.waffle.processor.OperationOutputInterface;
import io.github.rsaestrela.waffle.processor.OperationsProcessor;
import io.github.rsaestrela.waffle.processor.TypeOutputClass;
import io.github.rsaestrela.waffle.processor.TypesProcessor;
import io.github.rsaestrela.waffle.processor.validation.OperationsValidator;
import io.github.rsaestrela.waffle.processor.validation.TypesValidator;
import io.github.rsaestrela.waffle.writer.OperationInterfaceClassWriter;
import io.github.rsaestrela.waffle.writer.Resource;
import io.github.rsaestrela.waffle.writer.TypeClassWriter;

import javax.tools.ToolProvider;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class Waffle {

    public static void main(String[] args) throws WaffleException {
        String namespace = "namespace";
        TypeClassWriter typeClassWriter = new TypeClassWriter();
        OperationInterfaceClassWriter operationInterfaceClassWriter = new OperationInterfaceClassWriter();
        ClassCompiler classCompiler = new ClassCompiler(ToolProvider.getSystemJavaCompiler());
        List<Resource> resources = new ArrayList<>();
        try {
            ServiceDefinitions serviceDefinitions = new ServiceDefinitionsLoader().load(new HashSet<>(Arrays.asList(args)));
            for (ServiceDefinition serviceDefinition: serviceDefinitions.getServiceDefinitions()) {
                TypesProcessor typesProcessor = new TypesProcessor(serviceDefinition, new TypesValidator());
                List<TypeOutputClass> typeOutputClasses = typesProcessor.process();
                for (TypeOutputClass typeOutputClass: typeOutputClasses) {
                    resources.add(typeClassWriter.writeTypeClass(typeOutputClass));
                }
                OperationsProcessor operationsProcessor = new OperationsProcessor(serviceDefinition, new OperationsValidator());
                List<OperationOutputInterface> operationOutputInterfaces = operationsProcessor.process();
                for (OperationOutputInterface operationOutputInterface: operationOutputInterfaces) {
                    resources.add(operationInterfaceClassWriter.writeOperationInterfaceClass(operationOutputInterface));
                }
                resources = classCompiler.compile(resources);
            }
        } catch (Exception e) {
            rollback(resources);
            throw e;
        }
    }

    public static boolean rollback(List<Resource> resources) {
        return resources.stream().allMatch(r -> r.getFile().delete());
    }

}
