package io.github.rsaestrela.waffle;


import io.github.rsaestrela.waffle.exception.WaffleException;
import org.testng.annotations.Test;

public class WaffleRescriptSuccessTest {

    @Test(enabled = false)
    public void shouldLoadDefinitions() throws WaffleException {
        Waffle.rescript(new String[]{"example.yml"});
    }

}