package io.github.rsaestrela.waffle.processor.validation;


public interface CheckedValidator2<I, H, E extends Exception> {

    void isValidOrThrow(I i, H h) throws E;

}
