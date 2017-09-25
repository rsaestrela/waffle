//waffle!
package waffle.${clazz.namespace}.operation;

import java.util.Objects;
import java.lang.annotation.*;
import waffle.${clazz.namespace}.*;

public interface ${clazz.interfaceName} {

<#assign signatures = clazz.interfaceSignatures>
<#list signatures as signature>
    ${signature.returnType} ${signature.method}(<#list signature.parameters?keys as param>${signature.parameters[param]} ${param}<#sep>, </#list>);

</#list>
}



