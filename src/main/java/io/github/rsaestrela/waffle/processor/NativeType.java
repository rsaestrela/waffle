package io.github.rsaestrela.waffle.processor;


import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum NativeType {

    //TODO add all major types
    STRING("string", "java.lang.String"),
    LONG("long", "java.lang.Long"),
    INTERGER("integer", "java.lang.Integer");

    private String descriptor;
    private String name;

    NativeType(String descriptor, String name) {
        this.descriptor = descriptor;
        this.name = name;
    }

    public String getDescriptor() {
        return descriptor;
    }

    public void setDescriptor(String descriptor) {
        this.descriptor = descriptor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Map<String, String> natives() {
        return Stream.of(NativeType.values()).collect(Collectors.toMap(NativeType::getDescriptor, NativeType::getName));
    }
}
