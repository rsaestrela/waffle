package io.github.rsaestrela.waffle.loader;


import io.github.rsaestrela.waffle.exception.WaffleDefinitionsException;
import io.github.rsaestrela.waffle.model.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import static org.testng.Assert.assertTrue;

public class ServiceDefinitionsLoaderTest {

    private ServiceDefinitionsLoader victim;

    private ServiceDefinition serviceDefinition1;

    private ServiceDefinition serviceDefinition2;

    @BeforeTest
    public void init() throws IOException {
        serviceDefinition1 = newServiceDefinition();
        serviceDefinition2 = newServiceDefinition();
        storeServiceDefinition(serviceDefinition1);
        storeServiceDefinition(serviceDefinition2);
        victim = new ServiceDefinitionsLoader();
    }

    @Test
    public void shouldLoadDefinitions() throws WaffleDefinitionsException {
        Set<String> definitionFiles = new HashSet<>();
        definitionFiles.add(serviceDefinition1.getInterfaceName() + ".yml");
        definitionFiles.add(serviceDefinition2.getInterfaceName() + ".yml");
        ServiceDefinitions serviceDefinitions = victim.load(definitionFiles);
        assertTrue(serviceDefinitions.getServiceDefinitions().containsAll(
                Arrays.asList(serviceDefinition1, serviceDefinition2)
        ));
    }

    private void storeServiceDefinition(ServiceDefinition serviceDefinition) throws IOException {
        Yaml yaml = new Yaml();
        FileWriter writer = new FileWriter(
                new File("target/classes/" + serviceDefinition.getInterfaceName() + ".yml")
        );
        yaml.dump(serviceDefinition, writer);
        writer.flush();
    }

    private ServiceDefinition newServiceDefinition() {

        Author author1 = new Author();
        author1.setEmail(randomString());
        author1.setName(randomString());
        Author author2 = new Author();
        author2.setEmail(randomString());
        author2.setName(randomString());

        List<Author> authors = new ArrayList<>();
        authors.add(author1);
        authors.add(author2);

        Attribute attribute = new Attribute();
        attribute.setAttributeName(randomString());
        attribute.setType(randomString());
        attribute.setValidator(false);
        Attribute attribute2 = new Attribute();
        attribute2.setAttributeName(randomString());
        attribute2.setType(randomString());
        attribute2.setValidator(false);

        Attribute attribute3 = new Attribute();
        attribute3.setAttributeName(randomString());
        attribute3.setType(randomString());
        attribute3.setValidator(false);
        Attribute attribute4 = new Attribute();
        attribute4.setAttributeName(randomString());
        attribute4.setType(randomString());
        attribute4.setValidator(false);

        Type type1 = new Type();
        type1.setName(randomString());
        type1.setAttributes(Arrays.asList(attribute, attribute2));
        Type type2 = new Type();
        type2.setName(randomString());
        type2.setAttributes(Arrays.asList(attribute3, attribute4));

        RequestParameter requestParameter1 = new RequestParameter();
        requestParameter1.setMandatory(true);
        requestParameter1.setName(randomString());
        requestParameter1.setType(randomString());
        RequestParameter requestParameter2 = new RequestParameter();
        requestParameter2.setMandatory(false);
        requestParameter2.setName(randomString());
        requestParameter2.setType(randomString());
        RequestParameter requestParameter3 = new RequestParameter();
        requestParameter3.setMandatory(true);
        requestParameter3.setName(randomString());
        requestParameter3.setType(randomString());

        Response response1 = new Response();
        response1.setName(randomString());
        response1.setType(randomString());
        Response response2 = new Response();
        response2.setName(randomString());
        response2.setType(randomString());

        Operation operation1 = new Operation();
        operation1.setMethod(randomString());
        operation1.setName(randomString());
        operation1.setRequestParameters(Arrays.asList(requestParameter1, requestParameter2));
        operation1.setResponse(response1);
        Operation operation2 = new Operation();
        operation2.setMethod(randomString());
        operation2.setName(randomString());
        operation2.setRequestParameters(Arrays.asList(requestParameter1, requestParameter3));
        operation2.setResponse(response2);

        ServiceDefinition serviceDefinition = new ServiceDefinition();
        serviceDefinition.setAuthors(authors);
        serviceDefinition.setDescription(randomString());
        serviceDefinition.setInterfaceName(randomString());
        serviceDefinition.setNamespace(randomString());
        serviceDefinition.setOwnership(randomString());
        serviceDefinition.setVersion(randomString());
        serviceDefinition.setTypes(Arrays.asList(type1, type2));
        serviceDefinition.setOperations(Arrays.asList(operation1, operation2));
        return serviceDefinition;
    }
    
    private static String randomString() {
        Random random = new Random();
        int size = random.nextInt(20);
        return RandomStringUtils.randomAlphabetic(size);
    }

}