package io.github.rsaestrela.waffle.processor.validation;


import io.github.rsaestrela.waffle.exception.WaffleOperationsException;
import io.github.rsaestrela.waffle.model.Operation;
import io.github.rsaestrela.waffle.model.RequestParameter;
import io.github.rsaestrela.waffle.model.Response;
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

public class OperationsValidatorTest {

    private CheckedValidator2<List<Type>, List<Operation>, WaffleOperationsException> victim;

    @BeforeMethod
    public void init() throws IOException {
        victim = new OperationsValidator();
    }

    @Test
    public void shouldFailValidationDuplicatedOperation() {

        Response response1 = new Response();
        response1.setType("Money");

        Response response2 = new Response();
        response2.setType("Money");

        RequestParameter requestParameter1 = new RequestParameter();
        requestParameter1.setType("integer");
        requestParameter1.setName("quantity");

        RequestParameter requestParameter2 = new RequestParameter();
        requestParameter2.setType("long");
        requestParameter2.setName("accountId");

        Operation operation1 = new Operation();
        operation1.setResponse(response1);
        operation1.setName("getMoney");
        operation1.setRequestParameters(Arrays.asList(requestParameter1, requestParameter2));

        Operation operation2 = new Operation();
        operation2.setResponse(response2);
        operation2.setName("getMoney");
        operation2.setRequestParameters(Arrays.asList(requestParameter1, requestParameter2));

        try {
            victim.isValidOrThrow(new ArrayList<>(), Arrays.asList(operation1, operation2));
            fail();
        } catch (WaffleOperationsException e) {
            assertEquals(e.getMessage(), "WAFFLE duplicated operation getMoney");
        }
    }

    @Test
    public void shouldFailValidationAbsentTypeRequest() {

        Response response1 = new Response();
        response1.setType("Money");

        Response response2 = new Response();
        response2.setType("Money");

        RequestParameter requestParameter1 = new RequestParameter();
        requestParameter1.setType("Cash");
        requestParameter1.setName("quantity");

        RequestParameter requestParameter2 = new RequestParameter();
        requestParameter2.setType("long");
        requestParameter2.setName("accountId");

        Operation operation1 = new Operation();
        operation1.setResponse(response1);
        operation1.setName("getMoney");
        operation1.setRequestParameters(Arrays.asList(requestParameter1, requestParameter2));

        Operation operation2 = new Operation();
        operation2.setResponse(response2);
        operation2.setName("getMoney");
        operation2.setRequestParameters(Collections.singletonList(requestParameter2));

        try {
            victim.isValidOrThrow(new ArrayList<>(), Arrays.asList(operation1, operation2));
            fail();
        } catch (WaffleOperationsException e) {
            assertEquals(e.getMessage(), "WAFFLE Cash is not defined as a type");
        }

    }

    @Test
    public void shouldFailValidationAbsentTypeResponse() {

        Response response1 = new Response();
        response1.setType("Money");

        Response response2 = new Response();
        response2.setType("Dollar");

        RequestParameter requestParameter1 = new RequestParameter();
        requestParameter1.setType("integer");
        requestParameter1.setName("quantity");

        RequestParameter requestParameter2 = new RequestParameter();
        requestParameter2.setType("long");
        requestParameter2.setName("accountId");

        Type type1 = new Type();
        type1.setName("Money");
        type1.setAttributes(new ArrayList<>());

        Operation operation1 = new Operation();
        operation1.setResponse(response1);
        operation1.setName("getMoney");
        operation1.setRequestParameters(Arrays.asList(requestParameter1, requestParameter2));

        Operation operation2 = new Operation();
        operation2.setResponse(response2);
        operation2.setName("getMoney");
        operation2.setRequestParameters(Arrays.asList(requestParameter1, requestParameter2));

        try {
            victim.isValidOrThrow(Collections.singletonList(type1), Arrays.asList(operation1, operation2));
            fail();
        } catch (WaffleOperationsException e) {
            assertEquals(e.getMessage(), "WAFFLE Dollar is not defined as a type");
        }
    }

    @Test
    public void shouldFailValidationDuplicatedParameter() {

        Response response1 = new Response();
        response1.setType("integer");

        Response response2 = new Response();
        response2.setType("string");

        RequestParameter requestParameter1 = new RequestParameter();
        requestParameter1.setType("integer");
        requestParameter1.setName("accountId");

        RequestParameter requestParameter2 = new RequestParameter();
        requestParameter2.setType("long");
        requestParameter2.setName("accountId");

        Type type1 = new Type();
        type1.setName("Money");
        type1.setAttributes(new ArrayList<>());

        Operation operation1 = new Operation();
        operation1.setResponse(response1);
        operation1.setName("getMoney");
        operation1.setRequestParameters(Arrays.asList(requestParameter1, requestParameter2));

        Operation operation2 = new Operation();
        operation2.setResponse(response2);
        operation2.setName("getMoney");
        operation2.setRequestParameters(Arrays.asList(requestParameter1, requestParameter2));

        try {
            victim.isValidOrThrow(Collections.singletonList(type1), Arrays.asList(operation1, operation2));
            fail();
        } catch (WaffleOperationsException e) {
            assertEquals(e.getMessage(), "WAFFLE accountId is defined more than once on getMoney");
        }
    }

    @Test
    public void shouldPassValidation() {

        Response response1 = new Response();
        response1.setType("Money");

        Response response2 = new Response();
        response2.setType("Cash");

        RequestParameter requestParameter1 = new RequestParameter();
        requestParameter1.setType("integer");
        requestParameter1.setName("quantity");

        RequestParameter requestParameter2 = new RequestParameter();
        requestParameter2.setType("long");
        requestParameter2.setName("accountId");

        Operation operation1 = new Operation();
        operation1.setResponse(response1);
        operation1.setName("getMoney");
        operation1.setRequestParameters(Collections.singletonList(requestParameter1));

        Operation operation2 = new Operation();
        operation2.setResponse(response2);
        operation2.setName("getCash");
        operation2.setRequestParameters(Collections.singletonList(requestParameter2));

        Type type1 = new Type();
        type1.setName("Money");

        Type type2 = new Type();
        type2.setName("Cash");

        try {
            victim.isValidOrThrow(Arrays.asList(type1, type2), Arrays.asList(operation1, operation2));
        } catch (WaffleOperationsException e) {
            fail();
        }
    }

}
