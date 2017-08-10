package io.github.rsaestrela.waffle.model;

import java.util.List;
import java.util.Objects;

public class Operation {

    private String name;
    private String method;
    private List<RequestParameter> requestParameters;
    private OperationResponse operationResponse;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public List<RequestParameter> getRequestParameters() {
        return requestParameters;
    }

    public void setRequestParameters(List<RequestParameter> requestParameters) {
        this.requestParameters = requestParameters;
    }

    public OperationResponse getOperationResponse() {
        return operationResponse;
    }

    public void setOperationResponse(OperationResponse operationResponse) {
        this.operationResponse = operationResponse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Operation operation = (Operation) o;
        return Objects.equals(name, operation.name) &&
                Objects.equals(method, operation.method) &&
                Objects.equals(requestParameters, operation.requestParameters) &&
                Objects.equals(operationResponse, operation.operationResponse);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, method, requestParameters, operationResponse);
    }
}

