<#assign stringutils=statics['com.mysql.util.StringUtil']>
package ${packageName}.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.sql.Timestamp;

/**
 * ${classInfo.classComment}
 * @author ${authorName} ${.now?string('yyyy-MM-dd')}
 */
public class ${classInfo.className} implements Serializable {

    private static final long serialVersionUID = 1L;
<#if classInfo.fieldList?exists && classInfo.fieldList?size gt 0>
<#list classInfo.fieldList as fieldItem >

    /**
     * ${fieldItem.columnName}  ${fieldItem.fieldComment}
     */
    private ${fieldItem.fieldClass} ${fieldItem.fieldName};
</#list>
<#list classInfo.fieldList as fieldItem >

    public ${fieldItem.fieldClass} get${fieldItem.fieldName?substring(0,1)?upper_case}${fieldItem.fieldName?substring(1)}() {
        return ${fieldItem.fieldName};
    }

    public void set${fieldItem.fieldName?substring(0,1)?upper_case}${fieldItem.fieldName?substring(1)}(${fieldItem.fieldClass} ${fieldItem.fieldName}) {
        this.${fieldItem.fieldName} = ${fieldItem.fieldName};
    }

</#list>
</#if>


}