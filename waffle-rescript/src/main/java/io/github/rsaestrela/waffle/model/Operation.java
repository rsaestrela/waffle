package io.github.rsaestrela.waffle.model;

import java.util.List;
import java.util.Objects;

public class Operation {

    private String name;
    private String method;
    private List<RequestParameter> requestParameters;
    private Response response;

    public Operation() {
    }

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

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public String signatureHash() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(response.getType()).append(name);
        requestParameters.forEach(p -> stringBuilder.append(p.getType()).append(p.getName()));
        return stringBuilder.toString();
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
                Objects.equals(response, operation.response);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, method, requestParameters, response);
    }
}

