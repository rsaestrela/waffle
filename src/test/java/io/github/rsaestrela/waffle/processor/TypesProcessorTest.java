package io.github.rsaestrela.waffle.processor;


import io.github.rsaestrela.waffle.model.Attribute;
import io.github.rsaestrela.waffle.model.Type;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import static org.testng.Assert.*;

public class TypesProcessorTest {

    private static final String NAMESPACE = "io.rsaestrela.github";

    private TypesProcessor victim;

    @BeforeTest
    public void init() throws IOException {
        victim = new TypesProcessor(NAMESPACE);
    }

    @Test
    public void shouldSuccessfullyProcessTypes() {

        Attribute attribute = new Attribute();
        attribute.setAttributeName("title");
        attribute.setType("string");
        attribute.setValidator(false);

        Attribute attribute2 = new Attribute();
        attribute2.setAttributeName("genre");
        attribute2.setType("Genre");
        attribute2.setValidator(false);

        Attribute attribute3 = new Attribute();
        attribute3.setAttributeName("label");
        attribute3.setType("Label");
        attribute3.setValidator(false);

        Attribute attribute4 = new Attribute();
        attribute4.setAttributeName("weight");
        attribute4.setType("integer");
        attribute4.setValidator(false);

        Type type1 = new Type();
        type1.setName("Track");
        type1.setAttributes(Arrays.asList(attribute, attribute2));

        Type type2 = new Type();
        type2.setName("Person");
        type2.setAttributes(Arrays.asList(attribute3, attribute4));

        Type type3 = new Type();
        type3.setName("Genre");

        Type type4 = new Type();
        type4.setName("Label");

        try {
            Set<TypeOutputClass> typeOutputClasses =
                    victim.process(Arrays.asList(type1, type2, type3, type4));
            assertEquals(typeOutputClasses.size(), 4);
            assertTrue(typeOutputClasses.stream()
                    .map(TypeOutputClass::getNamespace)
                    .allMatch(ns -> ns.equals(NAMESPACE + ".waffle.type")));
            assertTrue(typeOutputClasses.stream()
                    .map(TypeOutputClass::getTypeName)
                    .collect(Collectors.toList())
                    .containsAll(
                        Arrays.asList("Track", "Person", "Genre", "Label")
                    )
            );
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void shouldFailValidationTypeMissing() {

        Attribute attribute = new Attribute();
        attribute.setAttributeName("title");
        attribute.setType("string");
        attribute.setValidator(false);

        Attribute attribute2 = new Attribute();
        attribute2.setAttributeName("genre");
        attribute2.setType("Genre");
        attribute2.setValidator(false);

        Type type1 = new Type();
        type1.setName("Track");
        type1.setAttributes(Arrays.asList(attribute, attribute2));

        try {
            victim.process(Collections.singletonList(type1));
            fail("should not have passed");
        } catch (Exception e) {
            assertEquals(e.getMessage(),
                    "WAFFLE Genre is not defined as a type");
        }
    }

    @Test
    public void shouldFailValidationDefinedMoreOnce() {

        Attribute attribute = new Attribute();
        attribute.setAttributeName("title");
        attribute.setType("string");
        attribute.setValidator(false);

        Attribute attribute2 = new Attribute();
        attribute2.setAttributeName("artist");
        attribute2.setType("string");
        attribute2.setValidator(false);

        Type type1 = new Type();
        type1.setName("Track");
        type1.setAttributes(Arrays.asList(attribute, attribute2));

        Type type2 = new Type();
        type2.setName("Track");

        try {
            victim.process(Arrays.asList(type1, type2));
            fail("should not have passed");
        } catch (Exception e) {
            assertEquals(e.getMessage(),
                    "WAFFLE Track is defined more than once");
        }
    }

    @Test
    public void shouldFailValidationMoreThanOnceAttribute() {

        Attribute attribute = new Attribute();
        attribute.setAttributeName("title");
        attribute.setType("string");
        attribute.setValidator(false);

        Attribute attribute2 = new Attribute();
        attribute2.setAttributeName("title");
        attribute2.setType("string");
        attribute2.setValidator(false);

        Type type1 = new Type();
        type1.setName("Track");
        type1.setAttributes(Arrays.asList(attribute, attribute2));

        try {
            victim.process(Arrays.asList(type1));
            fail("should not have passed");
        } catch (Exception e) {
            assertEquals(e.getMessage(),
                "WAFFLE title is defined more than once in Track");
        }
    }

}