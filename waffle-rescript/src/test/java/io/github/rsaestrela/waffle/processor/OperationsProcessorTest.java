package io.github.rsaestrela.waffle.processor;


import io.github.rsaestrela.waffle.exception.WaffleOperationsException;
import io.github.rsaestrela.waffle.model.*;
import io.github.rsaestrela.waffle.processor.validation.CheckedValidator;
import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.*;

public class OperationsProcessorTest {

    private static final String NAMESPACE = "io.rsaestrela.github";

    @Mock
    private CheckedValidator<ServiceDefinition, WaffleOperationsException> validator;

    private OperationsProcessor victim;

    @BeforeMethod
    public void init() throws IOException {
        initMocks(this);
    }

    @Test
    public void shouldSuccessfullyProcessOperations() {

        Response response1 = new Response();
        response1.setType("Track");

        RequestParameter requestParameter1 = new RequestParameter();
        requestParameter1.setName("id");
        requestParameter1.setType("integer");

        Operation operation1 = new Operation();
        operation1.setName("getTrackById");
        operation1.setResponse(response1);
        operation1.setRequestParameters(Collections.singletonList(requestParameter1));

        Response response2 = new Response();
        response2.setType("Person");

        RequestParameter requestParameter2 = new RequestParameter();
        requestParameter2.setName("firstName");
        requestParameter2.setType("string");

        RequestParameter requestParameter3 = new RequestParameter();
        requestParameter3.setName("lastName");
        requestParameter3.setType("string");

        Operation operation2 = new Operation();
        operation2.setName("getPersonByFirstAndLastName");
        operation2.setResponse(response2);
        operation2.setRequestParameters(Arrays.asList(requestParameter2, requestParameter3));

        Type type1 = new Type();
        type1.setName("Track");
        type1.setAttributes(new ArrayList<>());

        Type type2 = new Type();
        type2.setName("Person");
        type2.setAttributes(new ArrayList<>());

        ServiceDefinition serviceDefinition = new ServiceDefinition();
        serviceDefinition.setNamespace(NAMESPACE);
        serviceDefinition.setOperations(Arrays.asList(operation1, operation2));
        serviceDefinition.setTypes(Arrays.asList(type1, type2));
        serviceDefinition.setInterfaceName("WorldService");

        try {
            //todo improve asserts
            victim = new OperationsProcessor(serviceDefinition);
            List<OperationOutputInterface> operationOutputInterfaces = victim.process();
            assertEquals(operationOutputInterfaces.size(), 1);
            OperationOutputInterface operationOutputInterface = operationOutputInterfaces.get(0);
            assertEquals(operationOutputInterface.getInterfaceSignatures().size(), 2);
            assertEquals(operationOutputInterface.getInterfaceSignatures().get(0).getParameters().size(), 1);
            assertEquals(operationOutputInterface.getInterfaceSignatures().get(1).getParameters().size(), 2);
            assertTrue(operationOutputInterfaces.stream()
                    .map(OperationOutputInterface::getNamespace)
                    .allMatch(ns -> ns.equals(NAMESPACE + ".waffle.operation")));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

}