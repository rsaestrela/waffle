package io.github.rsaestrela.waffle.processor;


import io.github.rsaestrela.waffle.exception.WaffleTypesException;
import io.github.rsaestrela.waffle.model.Type;
import io.github.rsaestrela.waffle.processor.validation.CheckedValidator;

import java.util.*;

public final class TypesProcessor extends Processor<Type, TypeOutputClass> {

    private static final Map<String, String> NATIVES = NativeType.natives();

    private final CheckedValidator<List<Type>, WaffleTypesException> validator;

    public TypesProcessor(String namespace, CheckedValidator<List<Type>, WaffleTypesException> validator) {
        super(namespace);
        this.validator = validator;
    }

    @Override
    public Set<TypeOutputClass> process(List<Type> types) throws WaffleTypesException {
        validator.isValidOrThrow(types);
        Set<TypeOutputClass> typeOutputClasses = new HashSet<>();
        types.forEach(t -> {
            TypeOutputClass typeOutputClass = new TypeOutputClass();
            typeOutputClass.setNamespace(getNamespace() + TYPE_PACKAGE);
            typeOutputClass.setTypeName(t.getName());
            Map<String, String> typeMembers = new HashMap<>();
            t.getAttributes().forEach(a -> {
                String type = a.getType();
                if (NATIVES.containsKey(a.getType())) {
                    typeMembers.put(a.getAttributeName(), NATIVES.get(type));
                } else {
                    typeMembers.put(
                        a.getAttributeName(),
                        String.format("%s%s%s%s", getNamespace(), TYPE_PACKAGE, DOT, type)
                    );

                }
            });
            typeOutputClass.setTypeMembers(typeMembers);
            typeOutputClasses.add(typeOutputClass);
        });
        return typeOutputClasses;
    }

}
