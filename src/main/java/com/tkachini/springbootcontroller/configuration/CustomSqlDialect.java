package com.tkachini.springbootcontroller.configuration;

import org.hibernate.dialect.PostgresPlusDialect;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.type.StandardBasicTypes;

public class CustomSqlDialect extends PostgresPlusDialect {
    public CustomSqlDialect() {
        super();
        registerFunction("not_regexp", new SQLFunctionTemplate(StandardBasicTypes.BOOLEAN, "?1 !~ ?2"));
    }
}
