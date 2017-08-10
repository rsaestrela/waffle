package io.github.rsaestrela.waffle.model;


public enum NativeType {

    //TODO
    STRING("string", String.class),
    LONG("long", Long.class),
    INTERGER("integer", Integer.class);

    private String descriptor;
    private Class clazz;

    NativeType(String descriptor, Class clazz) {
        this.descriptor = descriptor;
        this.clazz = clazz;
    }

    public String getDescriptor() {
        return descriptor;
    }

    public void setDescriptor(String descriptor) {
        this.descriptor = descriptor;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }
}
