package io.github.rsaestrela.waffle.writer;


import io.github.rsaestrela.waffle.exception.WaffleClassWriterException;
import io.github.rsaestrela.waffle.processor.TypeOutputClass;

public class TypeClassWriter extends ClassWriter<TypeOutputClass> {

    private static final String CLASS_TYPE = "type";
    private static final String CLASS_TEMPLATE = "type.ftl";

    public TypeClassWriter()  {
        super(CLASS_TYPE);
    }

    public void writeTypeClass(TypeOutputClass typeOutputClass) throws WaffleClassWriterException {
        write(typeOutputClass.getTypeName(), CLASS_TEMPLATE, typeOutputClass);
    }
}
