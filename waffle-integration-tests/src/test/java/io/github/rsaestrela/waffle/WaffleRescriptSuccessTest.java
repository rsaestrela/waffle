package io.github.rsaestrela.waffle;


import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.fail;

public class WaffleRescriptSuccessTest {

    @Test
    public void typeGenerationMultipleNativeTypes() {
        try {
            Waffle.rescript(new String[]{"success/1.yml"});
            classExists("io.github.rsaestrela.waffle.integration.type.MultipleNatives");
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void operationGenerationMultipleOperationsWithNatives() {
        try {
            Waffle.rescript(new String[]{"success/2.yml"});
            classExists("io.github.rsaestrela.waffle.integration.operation.SuccessTest2");
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void typeGenerationCustomResponseOnOperation() {
        try {
            Waffle.rescript(new String[]{"success/3.yml"});
            classExists("io.github.rsaestrela.waffle.integration.operation.SuccessTest3");
            classExists("io.github.rsaestrela.waffle.integration.type.NativesResponse");
        } catch (Exception e) {
            fail();
        }
    }

    private void classExists(String qualifiedClassName) throws ClassNotFoundException {
        assertNotNull(Class.forName(qualifiedClassName));
    }

}