package io.github.rsaestrela.waffle;

import io.github.rsaestrela.waffle.exception.WaffleDefinitionsException;
import io.github.rsaestrela.waffle.loader.ServiceDefinitionsLoader;
import io.github.rsaestrela.waffle.model.ServiceDefinitions;

import java.util.Arrays;
import java.util.HashSet;

public class Waffle {

    public static void main(String[] args) {
        try {
            ServiceDefinitions serviceDefinitions = new ServiceDefinitionsLoader().load(new HashSet<>(Arrays.asList(args)));
        } catch (WaffleDefinitionsException e) {
            e.printStackTrace();
        }
    }

}
