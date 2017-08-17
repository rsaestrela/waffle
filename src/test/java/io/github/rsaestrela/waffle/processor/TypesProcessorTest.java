package io.github.rsaestrela.waffle.processor;


import io.github.rsaestrela.waffle.exception.WaffleTypesException;
import io.github.rsaestrela.waffle.model.Attribute;
import io.github.rsaestrela.waffle.model.ServiceDefinition;
import io.github.rsaestrela.waffle.model.Type;
import io.github.rsaestrela.waffle.processor.validation.CheckedValidator;
import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.*;

public class TypesProcessorTest {

    private static final String NAMESPACE = "io.rsaestrela.github";

    @Mock
    private CheckedValidator<List<Type>, WaffleTypesException> validator;

    private TypesProcessor victim;

    @BeforeMethod
    public void init() throws IOException {
        initMocks(this);
        victim = new TypesProcessor(NAMESPACE, validator);
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
        type3.setAttributes(new ArrayList<>());

        Type type4 = new Type();
        type4.setName("Label");
        type4.setAttributes(new ArrayList<>());

        try {
            ServiceDefinition serviceDefinition = new ServiceDefinition();
            serviceDefinition.setTypes(Arrays.asList(type1, type2, type3, type4));
            List<TypeOutputClass> typeOutputClasses = victim.process(serviceDefinition);
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
    public void shouldReThrowValidationException() throws WaffleTypesException {
        doThrow(new WaffleTypesException("this is a test")).when(validator).isValidOrThrow(any());
        try {
            ServiceDefinition serviceDefinition = new ServiceDefinition();
            serviceDefinition.setTypes(new ArrayList<>());
            victim.process(serviceDefinition);
            fail();
        } catch (WaffleTypesException e) {
            assertEquals(e.getMessage(), "this is a test");
        } catch (Exception e) {
            fail();
        }
    }

}