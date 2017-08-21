package io.github.rsaestrela.waffle.processor;


import io.github.rsaestrela.waffle.exception.WaffleTypesException;
import io.github.rsaestrela.waffle.model.ServiceDefinition;
import io.github.rsaestrela.waffle.model.Type;
import io.github.rsaestrela.waffle.processor.validation.CheckedValidator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class TypesProcessor extends Processor<TypeOutputClass, WaffleTypesException> {

    private static final Map<String, String> NATIVES = NativeType.natives();

    private final CheckedValidator<List<Type>, WaffleTypesException> validator;

    public TypesProcessor(ServiceDefinition serviceDefinition, CheckedValidator<List<Type>, WaffleTypesException> validator) {
        super(serviceDefinition);
        this.validator = validator;
    }

    @Override
    public List<TypeOutputClass> process() throws WaffleTypesException {
        String namespace = serviceDefinition.getNamespace();
        List<Type> types = serviceDefinition.getTypes();
        validator.isValidOrThrow(types);
        List<TypeOutputClass> typeOutputClasses = new ArrayList<>();
        types.forEach(t -> {
            TypeOutputClass typeOutputClass = new TypeOutputClass();
            typeOutputClass.setNamespace(namespace + TYPE_PACKAGE);
            typeOutputClass.setTypeName(t.getName());
            Map<String, String> typeMembers = new HashMap<>();
            t.getAttributes().forEach(a -> {
                String type = a.getType();
                String attributeName = a.getAttributeName();
                if (NATIVES.containsKey(type)) {
                    typeMembers.put(attributeName, NATIVES.get(type));
                } else {
                    typeMembers.put(attributeName, String.format("%s%s%s%s", namespace, TYPE_PACKAGE, DOT, type));
                }
            });
            typeOutputClass.setTypeMembers(typeMembers);
            typeOutputClasses.add(typeOutputClass);
        });
        return typeOutputClasses;
    }

}
