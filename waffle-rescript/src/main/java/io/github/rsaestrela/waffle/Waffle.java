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
import io.github.rsaestrela.waffle.processor.validation.CheckedValidator;
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
import java.util.stream.Collectors;

public class Waffle {

    private static final List<CheckedValidator> VALIDATORS = Arrays.asList(new TypesValidator(), new OperationsValidator());
    private static final ClassCompiler COMPILER = new ClassCompiler(ToolProvider.getSystemJavaCompiler());
    private static final TypeClassWriter TYPE_CLASS_WRITER = new TypeClassWriter();
    private static final OperationInterfaceClassWriter OPERATION_INTERFACE_CLASS_WRITER = new OperationInterfaceClassWriter();

    public static void rescript(String[] serviceDefinitionsFiles) throws WaffleException {
        List<Resource> resources = new ArrayList<>();
        try {
            ServiceDefinitions serviceDefinitions = new ServiceDefinitionsLoader()
                    .load(new HashSet<>(Arrays.asList(serviceDefinitionsFiles)));
            for (ServiceDefinition serviceDefinition: serviceDefinitions.getServiceDefinitions()) {
                validateServiceDefinition(serviceDefinition);
                TypesProcessor typesProcessor = new TypesProcessor(serviceDefinition);
                List<TypeOutputClass> typeOutputClasses = typesProcessor.process();
                for (TypeOutputClass typeOutputClass: typeOutputClasses) {
                    resources.add(TYPE_CLASS_WRITER.writeTypeClass(typeOutputClass));
                }
                OperationsProcessor operationsProcessor = new OperationsProcessor(serviceDefinition);
                List<OperationOutputInterface> operationOutputInterfaces = operationsProcessor.process();
                for (OperationOutputInterface operationOutputInterface: operationOutputInterfaces) {
                    resources.add(OPERATION_INTERFACE_CLASS_WRITER.writeOperationInterfaceClass(operationOutputInterface));
                }

                List<String> files = resources.stream().map(r -> r.getFile().getPath()).collect(Collectors.toList());
                COMPILER.compile(files.toArray(new String[files.size()]));
            }
        } catch (Exception e) {
            rollback(resources);
            throw e;
        }
    }

    private static void validateServiceDefinition(ServiceDefinition serviceDefinition) throws WaffleException {
        for (CheckedValidator validator : VALIDATORS) {
            validator.isValidOrThrow(serviceDefinition);
        }
    }

    public static boolean rollback(List<Resource> resources) {
        return resources.stream().allMatch(r -> r.getFile().delete());
    }

}
