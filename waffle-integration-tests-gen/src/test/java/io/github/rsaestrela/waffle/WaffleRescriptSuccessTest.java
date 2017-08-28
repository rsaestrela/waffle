package io.github.rsaestrela.waffle;


import org.testng.annotations.Test;

import static org.testng.Assert.fail;

public class WaffleRescriptSuccessTest {

    @Test
    public void typeGenerationMultipleNativeTypes() {
        try {
            Waffle.rescript(new String[]{"success/1.yml"});
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void operationGenerationMultipleOperationsWithNatives() {
        try {
            Waffle.rescript(new String[]{"success/2.yml"});
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void typeGenerationCustomResponseOnOperation() {
        try {
            Waffle.rescript(new String[]{"success/3.yml"});
        } catch (Exception e) {
            fail();
        }
    }

}