package io.github.rsaestrela.waffle.processor;


import java.util.List;
import java.util.Map;
import java.util.Objects;

public class OperationOutputInterface extends OutputClass {

    private String interfaceName;
    private List<InterfaceSignature> interfaceSignatures;

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public List<InterfaceSignature> getInterfaceSignatures() {
        return interfaceSignatures;
    }

    public void setInterfaceSignatures(List<InterfaceSignature> interfaceSignatures) {
        this.interfaceSignatures = interfaceSignatures;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OperationOutputInterface)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        OperationOutputInterface that = (OperationOutputInterface) o;
        return Objects.equals(interfaceName, that.interfaceName) &&
                Objects.equals(interfaceSignatures, that.interfaceSignatures);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), interfaceName, interfaceSignatures);
    }

    public static class InterfaceSignature {

        private String returnType;
        private String method;
        private Map<String, String> parameters;

        public String getReturnType() {
            return returnType;
        }

        public void setReturnType(String returnType) {
            this.returnType = returnType;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public Map<String, String> getParameters() {
            return parameters;
        }

        public void setParameters(Map<String, String> parameters) {
            this.parameters = parameters;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof InterfaceSignature)) {
                return false;
            }
            InterfaceSignature that = (InterfaceSignature) o;
            return Objects.equals(returnType, that.returnType) &&
                    Objects.equals(method, that.method) &&
                    Objects.equals(parameters, that.parameters);
        }

        @Override
        public int hashCode() {
            return Objects.hash(returnType, method, parameters);
        }
    }

}
