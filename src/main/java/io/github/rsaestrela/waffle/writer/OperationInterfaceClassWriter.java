package io.github.rsaestrela.waffle.writer;


import io.github.rsaestrela.waffle.exception.WaffleClassWriterException;
import io.github.rsaestrela.waffle.processor.OperationOutputInterface;

import java.util.List;

public class OperationInterfaceClassWriter extends ClassWriter<OperationOutputInterface> {

    private static final String CLASS_OPERATION = "operation";
    private static final String CLASS_TEMPLATE = "operation.ftl";

    public OperationInterfaceClassWriter()  {
        super(CLASS_OPERATION);
    }

    public List<Resource> writeOperationInterfaceClass(OperationOutputInterface operationOutputInterface)
            throws WaffleClassWriterException {
        return write(operationOutputInterface.getInterfaceName(), CLASS_TEMPLATE, operationOutputInterface);
    }
}
