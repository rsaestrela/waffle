package io.github.rsaestrela.waffle.processor.validation;


public interface CheckedValidator<I, E extends Exception> {

    void isValidOrThrow(I i) throws E;

}
