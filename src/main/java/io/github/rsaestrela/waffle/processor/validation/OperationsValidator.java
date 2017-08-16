package io.github.rsaestrela.waffle.processor.validation;


import io.github.rsaestrela.waffle.exception.WaffleRuntimeException;
import io.github.rsaestrela.waffle.exception.WaffleTypesException;
import io.github.rsaestrela.waffle.model.Operation;
import io.github.rsaestrela.waffle.model.RequestParameter;
import io.github.rsaestrela.waffle.model.Type;
import io.github.rsaestrela.waffle.processor.NativeType;

import java.util.*;
import java.util.stream.Collectors;

public class OperationsValidator implements CheckedValidator2<List<Type>, List<Operation>, WaffleTypesException> {

    private static final Map<String, String> NATIVES = NativeType.natives();

    @Override
    public void isValidOrThrow(List<Type> types, List<Operation> operations) throws WaffleTypesException {
        try {
            noDuplicatedOperations(operations);
            noAbsentTypes(types, operations);
            noDuplicatedParameters(operations);
        } catch (WaffleRuntimeException wre) {
            throw new WaffleTypesException(wre.getMessage());
        }
    }

    private void noDuplicatedOperations(List<Operation> operations) {
        Set<String> noDuplicates = new HashSet<>();
        operations.forEach(o -> {
            if (!noDuplicates.add(o.signatureHash())) {
                throw new WaffleRuntimeException(String.format("WAFFLE duplicated operation %s", o.getName()));
            }
        });
    }

    private void noAbsentTypes(List<Type> types, List<Operation> operations) {
        List<String> definedTypes = types.stream().map(Type::getName).collect(Collectors.toList());
        List<String> requestTypes = operations.stream().map(Operation::getRequestParameters)
                .flatMap(Collection::stream).map(RequestParameter::getType).collect(Collectors.toList());
        requestTypes.forEach(rt -> {
            if (!definedTypes.contains(rt) && !NATIVES.containsKey(rt)) {
                throw new WaffleRuntimeException(String.format("WAFFLE %s is not defined as a type", rt));
            }
        });
    }

    private void noDuplicatedParameters(List<Operation> operations) {
        operations.forEach(o -> {
            Set<String> noDuplicates = new HashSet<>();
            o.getRequestParameters().forEach(rp -> {
                if (!noDuplicates.add(rp.getName())) {
                    throw new WaffleRuntimeException(String.format("WAFFLE %s is defined more than once", rp.getName()));
                }
            });
        });
    }

}
