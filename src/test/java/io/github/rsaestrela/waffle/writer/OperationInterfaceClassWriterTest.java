package io.github.rsaestrela.waffle.writer;


import io.github.rsaestrela.waffle.Waffle;
import io.github.rsaestrela.waffle.processor.OperationOutputInterface;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.*;

public class OperationInterfaceClassWriterTest {

    private static final String NAMESPACE = "my.app.society";

    private OperationInterfaceClassWriter victim;

    @BeforeMethod
    public void init() throws IOException {
        initMocks(this);
        victim = new OperationInterfaceClassWriter();
    }

    @Test
    public void shouldSuccessfullyWriteOperationsInterface() {

        OperationOutputInterface.InterfaceSignature interfaceSignature = new OperationOutputInterface.InterfaceSignature();
        interfaceSignature.setMethod("getStudentById");
        interfaceSignature.setReturnType("my.app.society.type.Student");
        Map<String, String> parameters1 = new HashMap<>();
        parameters1.put("id", "java.lang.Integer");
        interfaceSignature.setParameters(parameters1);

        OperationOutputInterface.InterfaceSignature interfaceSignature2 = new OperationOutputInterface.InterfaceSignature();
        interfaceSignature2.setMethod("getBookByAuthorAndYear");
        interfaceSignature2.setReturnType("my.app.society.type.Book");
        Map<String, String> parameters2 = new HashMap<>();
        parameters2.put("author", "my.app.society.type.Author");
        parameters2.put("year", "my.app.society.type.Year");
        interfaceSignature2.setParameters(parameters2);

        OperationOutputInterface operationOutputInterface = new OperationOutputInterface();
        operationOutputInterface.setInterfaceName("SchoolService");
        operationOutputInterface.setNamespace(NAMESPACE);
        operationOutputInterface.setInterfaceSignatures(Arrays.asList(interfaceSignature, interfaceSignature2));

        List<Resource> resources = null;
        try {
            resources = victim.writeOperationInterfaceClass(operationOutputInterface);
            ClassLoader classLoader = ClassLoader.getSystemClassLoader();
            assertNotNull(Class.forName(NAMESPACE + ".operation." + "SchoolService", true, classLoader));
        } catch (Exception e) {
            fail(e.getMessage());
        } finally {
            assertTrue(Waffle.rollback(resources));
        }
    }
}