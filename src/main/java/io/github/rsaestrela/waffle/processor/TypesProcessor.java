package io.github.rsaestrela.waffle.processor;


import io.github.rsaestrela.waffle.exception.WaffleTypesException;
import io.github.rsaestrela.waffle.model.Attribute;
import io.github.rsaestrela.waffle.model.Type;

import java.util.*;
import java.util.stream.Collectors;

public final class TypesProcessor extends Processor<Type, TypeOutputClass> {

    private static final Map<String, String> NATIVES = NativeType.natives();

    public TypesProcessor(String namespace) {
        super(namespace);
    }

    @Override
    protected Set<TypeOutputClass> process(List<Type> types) {
        sanitize(types);
        validateTypes(types);
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

    //todo Serialization improvement
    private void sanitize(List<Type> types) {
        types.forEach(t -> {
            if (t.getAttributes() == null) {
                t.setAttributes(new ArrayList<>());
            }
        });
    }

    //todo Different methods
    private void validateTypes(List<Type> types) {
        // Absent types
        List<String> definedTypes = types.stream()
                .map(Type::getName).collect(Collectors.toList());
        types.forEach(t -> {
        List<Attribute> typeAttributes = t.getAttributes();
            typeAttributes.forEach(a -> {
                if (!definedTypes.contains(a.getType()) &&
                        !NATIVES.containsKey(a.getType())) {
                    throw new WaffleTypesException(
                        String.format("WAFFLE %s is not defined as a type", a.getType())
                    );
                }
            });
        });
        // No duplicated types
        Set<String> distinctTypes = new HashSet<>();
        definedTypes.forEach(dt -> {
            if (!distinctTypes.add(dt)) {
                throw new WaffleTypesException(
                    String.format("WAFFLE %s is defined more than once", dt)
                );
            }
        });
        // No duplicated type attributes
        types.forEach(t -> {
            List<String> typeAttributes = t.getAttributes().stream()
                    .map(Attribute::getAttributeName).collect(Collectors.toList());
            Set<String> distinctAttributes = new HashSet<>();
            typeAttributes.forEach(ta -> {
                if (!distinctAttributes.add(ta)) {
                    throw new WaffleTypesException(
                        String.format("WAFFLE %s is defined more than once in %s",
                                ta, t.getName())
                    );
                }
            });
        });
    }
}
