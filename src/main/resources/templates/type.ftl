//waffle!
package ${clazz.namespace}.type;

import java.util.Objects;

public class ${clazz.typeName} {
<#assign members = clazz.typeMembers>
<#list members?keys as attribute>

    private ${members[attribute]} ${attribute};
</#list>
<#list members?keys as attribute>

    public ${members[attribute]} get${attribute?cap_first}() {
        return ${attribute};
    }

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
        return Objects.equals(typeName, that.typeName) &&
                Objects.equals(typeMembers, that.typeMembers);
    }
}



