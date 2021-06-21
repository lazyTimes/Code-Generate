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
        <#if fieldItem.fieldClass=="Date" || fieldItem.fieldClass=="Timestamp">
        /**
        * ${fieldItem.columnName}  ${fieldItem.fieldComment}
        */
        private ${fieldItem.fieldClass} ${fieldItem.fieldName};

        /**
        * ${fieldItem.columnName}  ${fieldItem.fieldComment} 开始时间
        */
        private ${fieldItem.fieldClass} ${fieldItem.fieldName}Start;

        /**
        * ${fieldItem.columnName}  ${fieldItem.fieldComment} 结束时间
        */
        private ${fieldItem.fieldClass} ${fieldItem.fieldName}End;

        <#else >
        /**
        * ${fieldItem.columnName}  ${fieldItem.fieldComment}
        */
        private ${fieldItem.fieldClass} ${fieldItem.fieldName};
        </#if>

    </#list>
    <#list classInfo.fieldList as fieldItem >
        <#if fieldItem.fieldClass=="Date" || fieldItem.fieldClass=="Timestamp">

        public ${fieldItem.fieldClass} get${fieldItem.fieldName?substring(0,1)?upper_case}${fieldItem.fieldName?substring(1)}() {
            return ${fieldItem.fieldName};
        }

        public void set${fieldItem.fieldName?substring(0,1)?upper_case}${fieldItem.fieldName?substring(1)}(${fieldItem.fieldClass} ${fieldItem.fieldName}) {
            this.${fieldItem.fieldName} = ${fieldItem.fieldName};
        }

        public ${fieldItem.fieldClass} get${fieldItem.fieldName?substring(0,1)?upper_case}${fieldItem.fieldName?substring(1)}Start() {
            return ${fieldItem.fieldName}Start;
        }

        public void set${fieldItem.fieldName?substring(0,1)?upper_case}${fieldItem.fieldName?substring(1)}Start(${fieldItem.fieldClass} ${fieldItem.fieldName}Start) {
            this.${fieldItem.fieldName}Start = ${fieldItem.fieldName}Start;
        }

        public ${fieldItem.fieldClass} get${fieldItem.fieldName?substring(0,1)?upper_case}${fieldItem.fieldName?substring(1)}End() {
            return ${fieldItem.fieldName}End;
        }

        public void set${fieldItem.fieldName?substring(0,1)?upper_case}${fieldItem.fieldName?substring(1)}End(${fieldItem.fieldClass} ${fieldItem.fieldName}End) {
            this.${fieldItem.fieldName}End = ${fieldItem.fieldName}End;
        }

        <#else >

        public ${fieldItem.fieldClass} get${fieldItem.fieldName?substring(0,1)?upper_case}${fieldItem.fieldName?substring(1)}() {
            return ${fieldItem.fieldName};
        }

        public void set${fieldItem.fieldName?substring(0,1)?upper_case}${fieldItem.fieldName?substring(1)}(${fieldItem.fieldClass} ${fieldItem.fieldName}) {
            this.${fieldItem.fieldName} = ${fieldItem.fieldName};
        }

        </#if>

    </#list>
</#if>


}