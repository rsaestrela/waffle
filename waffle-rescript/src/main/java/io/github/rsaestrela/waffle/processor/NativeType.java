package io.github.rsaestrela.waffle.processor;


import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum NativeType {

    BYTE("byte", "java.lang.Byte"),
    CHAR("char", "java.lang.Character"),
    SHORT("short", "java.lang.Short"),
    INTEGER("integer", "java.lang.Integer"),
    LONG("long", "java.lang.Long"),
    FLOAT("float", "java.lang.Float"),
    DOUBLE("double", "java.lang.Double"),
    STRING("string", "java.lang.String"),
    BOOLEAN("boolean", "java.lang.Boolean");

    private String descriptor;
    private String nativePackage;

    NativeType(String descriptor, String nativePackage) {
        this.descriptor = descriptor;
        this.nativePackage = nativePackage;
    }

    public String getDescriptor() {
        return descriptor;
    }

    public void setDescriptor(String descriptor) {
        this.descriptor = descriptor;
    }

    public String getNativePackage() {
        return nativePackage;
    }

    public void setNativePackage(String nativePackage) {
        this.nativePackage = nativePackage;
    }

    public static Map<String, String> natives() {
        return Stream.of(NativeType.values())
                .collect(Collectors.toMap(NativeType::getDescriptor, NativeType::getNativePackage));
    }
}
