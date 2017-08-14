package io.github.rsaestrela.waffle.processor.validation;


import io.github.rsaestrela.waffle.exception.WaffleTypesException;
import io.github.rsaestrela.waffle.model.Attribute;
import io.github.rsaestrela.waffle.model.Type;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

public class TypesValidatorTest {

    private CheckedValidator<List<Type>, WaffleTypesException> victim;

    @BeforeMethod
    public void init() throws IOException {
        victim = new TypesValidator();
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
            victim.isValidOrThrow(Collections.singletonList(type1));
            fail("should not have passed");
        } catch (Exception e) {
            assertEquals(e.getMessage(),
                    "WAFFLE Genre is not defined as a type");
        }
    }

    @Test
    public void shouldFailValidationTypeDefinedMoreOnce() {

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
        type2.setAttributes(new ArrayList<>());

        try {
            victim.isValidOrThrow(Arrays.asList(type1, type2));
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
            victim.isValidOrThrow(Collections.singletonList(type1));
            fail("should not have passed");
        } catch (Exception e) {
            assertEquals(e.getMessage(),
                    "WAFFLE title is defined more than once in Track");
        }
    }
}
