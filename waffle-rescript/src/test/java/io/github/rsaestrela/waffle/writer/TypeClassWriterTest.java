package io.github.rsaestrela.waffle.writer;


import io.github.rsaestrela.waffle.Waffle;
import io.github.rsaestrela.waffle.processor.NativeType;
import io.github.rsaestrela.waffle.processor.TypeOutputClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

public class TypeClassWriterTest {

    private static final String NAMESPACE = "my.app.society";

    private TypeClassWriter victim;

    @BeforeMethod
    public void init() throws IOException {
        initMocks(this);
        victim = new TypeClassWriter();
    }

    @Test
    public void shouldSuccessfullyWriteTypeClassWithPrimitives() {
        TypeOutputClass typeOutputClass = new TypeOutputClass();
        typeOutputClass.setTypeName("Person");
        typeOutputClass.setNamespace(NAMESPACE);
        Map<String, String> typeMembers = new HashMap<>();
        typeMembers.put("name", NativeType.STRING.getNativePackage());
        typeMembers.put("age", NativeType.INTEGER.getNativePackage());
        typeMembers.put("owns", NativeType.LONG.getNativePackage());
        typeMembers.put("gender", NativeType.CHAR.getNativePackage());
        typeMembers.put("weight", NativeType.FLOAT.getNativePackage());
        typeMembers.put("bf", NativeType.DOUBLE.getNativePackage());
        typeMembers.put("healthy", NativeType.BOOLEAN.getNativePackage());
        typeOutputClass.setTypeMembers(typeMembers);
        Resource resource = null;
        try {
            resource = victim.writeTypeClass(typeOutputClass);
        } catch (Exception e) {
            fail(e.getMessage());
        } finally {
            assertTrue(Waffle.rollback(Collections.singletonList(resource)));
        }
    }
}