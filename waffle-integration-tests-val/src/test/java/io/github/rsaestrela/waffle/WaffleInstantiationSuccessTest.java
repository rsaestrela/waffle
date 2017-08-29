package io.github.rsaestrela.waffle;


import org.testng.annotations.Test;
import waffle.io.github.rsaestrela.integration.operation.SuccessTest2;
import waffle.io.github.rsaestrela.integration.operation.SuccessTest3;
import waffle.io.github.rsaestrela.integration.type.MultipleNatives;
import waffle.io.github.rsaestrela.integration.type.NativesResponse;

public class WaffleInstantiationSuccessTest {

    @Test
    public void typeGenerationMultipleNativeTypes() {
        MultipleNatives multipleNatives = new MultipleNatives();
        multipleNatives.setOneBoolean(true);
    }

    @Test
    public void operationGenerationMultipleOperationsWithNatives() {
        SuccessTest2 successTest2 = new SuccessTest2() {
            @Override
            public Byte testByte(Byte thisByte) {
                return null;
            }

            @Override
            public Character testChar(Character thisChar) {
                return null;
            }

            @Override
            public Short testShort(Short thisShort) {
                return null;
            }

            @Override
            public Long testLong(Long thisLong) {
                return null;
            }

            @Override
            public Float testFloat(Float thisFloat) {
                return null;
            }

            @Override
            public Double testDouble(Double thisDouble) {
                return null;
            }

            @Override
            public Integer testInteger(Integer thisInteger) {
                return null;
            }

            @Override
            public String testString(String thisString) {
                return null;
            }

            @Override
            public String testMultipleNatives(Integer thisString, Integer thisInteger, Double thisDouble) {
                return null;
            }
        };
    }

    @Test
    public void typeGenerationCustomResponseOnOperation() {
        SuccessTest3 successTest3 = () -> {
            NativesResponse nativesResponse = new NativesResponse();
            nativesResponse.setOneByte(new Byte("b"));
            nativesResponse.setOneChar('c');
            return nativesResponse;
        };
    }

}