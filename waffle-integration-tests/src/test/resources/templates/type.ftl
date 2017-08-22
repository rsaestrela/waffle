//waffle!
package ${clazz.namespace}.type;

import java.util.Objects;
import java.lang.annotation.*;

public class ${clazz.typeName} {
<#assign members = clazz.typeMembers>
<#list members?keys as attribute>

    private ${members[attribute]} ${attribute};
</#list>

    public ${clazz.typeName}() {}
<#list members?keys as attribute>

<#if members[attribute] == "java.lang.Boolean">
    public java.lang.Boolean is${attribute?cap_first}() {
        return ${attribute};
    }
<#else>
    public ${members[attribute]} get${attribute?cap_first}() {
        return ${attribute};
    }
</#if>

    public void set${attribute?cap_first}(${members[attribute]} ${attribute}) {
        this.${attribute} = ${attribute};
    }
</#list>

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ${clazz.typeName})) {
            return false;
        }
        ${clazz.typeName} that = (${clazz.typeName}) o;
        return <#list members?keys as member>Objects.equals(${member}, that.${member})<#sep> && </#list>;
    }

    @Override
    public int hashCode() {
        return Objects.hash(<#list members?keys as member>${member}<#sep>, </#list>);
    }

}



