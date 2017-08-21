package io.github.rsaestrela.waffle.processor.validation;


import io.github.rsaestrela.waffle.exception.WaffleException;

public interface CheckedValidator<I, E extends WaffleException> {

    void isValidOrThrow(I i) throws E;

}
