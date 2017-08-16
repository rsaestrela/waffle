package io.github.rsaestrela.waffle.processor;


import io.github.rsaestrela.waffle.exception.WaffleTypesException;
import io.github.rsaestrela.waffle.model.Operation;
import io.github.rsaestrela.waffle.model.Type;
import io.github.rsaestrela.waffle.processor.validation.CheckedValidator2;

import java.util.List;
import java.util.Map;
import java.util.Set;

public final class OperationsProcessor extends Processor<Operation, OperationOutputInterface> {

    private static final Map<String, String> NATIVES = NativeType.natives();

    private final CheckedValidator2<List<Type>, List<Operation>, WaffleTypesException> validator;

    private final List<Type> types;

    public OperationsProcessor(String namespace, CheckedValidator2<List<Type>, List<Operation>, WaffleTypesException> validator,
                               List<Type> types) {
        super(namespace);
        this.validator = validator;
        this.types = types;
    }

    @Override
    public Set<OperationOutputInterface> process(List<Operation> operations) throws WaffleTypesException {
        validator.isValidOrThrow(types, operations);
        return null;
    }
}
