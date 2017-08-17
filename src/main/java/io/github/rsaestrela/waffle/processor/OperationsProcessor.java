package io.github.rsaestrela.waffle.processor;


import io.github.rsaestrela.waffle.exception.WaffleOperationsException;
import io.github.rsaestrela.waffle.exception.WaffleTypesException;
import io.github.rsaestrela.waffle.model.Operation;
import io.github.rsaestrela.waffle.model.ServiceDefinition;
import io.github.rsaestrela.waffle.model.Type;
import io.github.rsaestrela.waffle.processor.validation.CheckedValidator2;

import java.util.*;

public final class OperationsProcessor extends Processor<OperationOutputInterface, WaffleOperationsException> {

    protected static final String OP_PACKAGE = ".waffle.operation";
    private static final Map<String, String> NATIVES = NativeType.natives();

    private final CheckedValidator2<List<Type>, List<Operation>, WaffleOperationsException> validator;

    private final List<Type> types;

    public OperationsProcessor(String namespace,
                               CheckedValidator2<List<Type>, List<Operation>, WaffleOperationsException> validator,
                               List<Type> types) {
        super(namespace);
        this.validator = validator;
        this.types = types;
    }

    @Override
    public List<OperationOutputInterface> process(ServiceDefinition serviceDefinition) throws WaffleOperationsException {
        List<Operation> operations = serviceDefinition.getOperations();
        validator.isValidOrThrow(types, operations);
        OperationOutputInterface operationOutputInterface = new OperationOutputInterface();
        operationOutputInterface.setInterfaceName(serviceDefinition.getInterfaceName());
        operationOutputInterface.setNamespace(getNamespace() + OP_PACKAGE);
        List<OperationOutputInterface.InterfaceSignature> interfaceSignatures = new ArrayList<>();
        operations.forEach(o -> {
            OperationOutputInterface.InterfaceSignature interfaceSignature = new OperationOutputInterface.InterfaceSignature();
            interfaceSignature.setMethod(o.getName());
            interfaceSignature.setReturnType(getNamespace() + TYPE_PACKAGE + DOT + o.getResponse().getType());
            Map<String, String> parameters = new HashMap<>();
            o.getRequestParameters().forEach(rp -> {
                String type = rp.getType();
                if (NATIVES.containsKey(type)) {
                    parameters.put(rp.getName(), NATIVES.get(type));
                } else {
                    parameters.put(rp.getName(), String.format("%s%s%s%s", getNamespace(), OP_PACKAGE, DOT, type)
                    );
                }
            });
            interfaceSignature.setParameters(parameters);
            interfaceSignatures.add(interfaceSignature);
        });
        operationOutputInterface.setInterfaceSignatures(interfaceSignatures);
        return Collections.singletonList(operationOutputInterface);
    }
}
