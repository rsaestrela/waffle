package io.github.rsaestrela.waffle.processor.validation;


import io.github.rsaestrela.waffle.exception.WaffleRuntimeException;
import io.github.rsaestrela.waffle.exception.WaffleTypesException;
import io.github.rsaestrela.waffle.model.Attribute;
import io.github.rsaestrela.waffle.model.Type;
import io.github.rsaestrela.waffle.processor.NativeType;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class TypesValidator implements CheckedValidator<List<Type>, WaffleTypesException> {

    private static final Map<String, String> NATIVES = NativeType.natives();

    @Override
    public void isValidOrThrow(List<Type> types) throws WaffleTypesException {
        List<String> definedTypes = types.stream().map(Type::getName).collect(Collectors.toList());
        try {
            noAbsentTypes(types, definedTypes);
            noDuplicateTypes(definedTypes);
            noDuplicateTypeAttributes(types);
        } catch (WaffleRuntimeException wre) {
            throw new WaffleTypesException(wre.getMessage());
        }
    }

    private void noAbsentTypes(List<Type> types, List<String> definedTypes) {
        types.forEach(t -> {
            List<Attribute> typeAttributes = t.getAttributes();
            typeAttributes.forEach(a -> {
                if (!definedTypes.contains(a.getType()) && !NATIVES.containsKey(a.getType())) {
                    throw new WaffleRuntimeException(String.format("WAFFLE %s is not defined as a type", a.getType()));
                }
            });
        });
    }

    private void noDuplicateTypes(List<String> definedTypes) {
        Set<String> distinctTypes = new HashSet<>();
        definedTypes.forEach(dt -> {
            if (!distinctTypes.add(dt)) {
                throw new WaffleRuntimeException(String.format("WAFFLE %s is defined more than once", dt));
            }
        });
    }

    private void noDuplicateTypeAttributes(List<Type> types) {
        types.forEach(t -> {
            List<String> typeAttributes = t.getAttributes().stream()
                    .map(Attribute::getAttributeName).collect(Collectors.toList());
            Set<String> distinctAttributes = new HashSet<>();
            typeAttributes.forEach(ta -> {
                if (!distinctAttributes.add(ta)) {
                    throw new WaffleRuntimeException(
                            String.format("WAFFLE %s is defined more than once in %s", ta, t.getName())
                    );
                }
            });
        });
    }

}
