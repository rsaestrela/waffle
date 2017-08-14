package io.github.rsaestrela.waffle.writer;


import io.github.rsaestrela.waffle.exception.WaffleClassWriterException;
import io.github.rsaestrela.waffle.processor.NativeType;
import io.github.rsaestrela.waffle.processor.TypeOutputClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.MockitoAnnotations.initMocks;

public class TypeClassWriterTest {

    private static final String NAMESPACE = "my.app.society";

    private TypeClassWriter victim;

    @BeforeMethod
    public void init() throws IOException {
        initMocks(this);
        victim = new TypeClassWriter();
    }

    @Test
    public void shouldSuccessfullyProcessTypes() {
        //todo finish
        TypeOutputClass typeOutputClass = new TypeOutputClass();
        typeOutputClass.setTypeName("Person");
        typeOutputClass.setNamespace(NAMESPACE);
        Map<String, String> typeMembers = new HashMap<>();
        typeMembers.put("name", NativeType.STRING.getNativePackage());
        typeMembers.put("age", NativeType.INTEGER.getNativePackage());
        typeMembers.put("gender", NAMESPACE + "." + "Gender");
        typeOutputClass.setTypeMembers(typeMembers);
        try {
            victim.writeTypeClass(typeOutputClass);
        } catch (WaffleClassWriterException e) {
            e.printStackTrace();
        }
    }
}