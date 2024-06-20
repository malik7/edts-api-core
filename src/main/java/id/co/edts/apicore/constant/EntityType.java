package id.co.edts.apicore.constant;

import lombok.Getter;

@Getter
public enum EntityType {
    ENUM(
            "ist.enum","enum",
            "eq", "not_eq", "in"
    ),

    STRING(
            "java.lang.String","string",
            "eq", "not_eq", "contains", "in", "is_empty", "is_not_empty"
    ),
    DATE("java.util.Date", "date", "gt", "gte", "lt", "lte", "bw", "is_empty", "is_not_empty"),
    BOOLEAN(
            "java.lang.Boolean", "boolean",
            "eq", "not_eq", "is_empty", "is_not_empty"
    ),
    LOCAL_DATE("java.time.LocalDate", "localDate", "gt", "gte", "lt", "lte", "bw"),
    LONG(
            "java.lang.Long","long",
            "eq", "not_eq", "in", "gt", "gte", "lt", "lte", "bw"
    ),
    DOUBLE(
            "java.lang.Double","double",
            "eq", "not_eq", "in", "gt", "gte", "lt", "lte", "bw"
    ),
    INTEGER(
            "java.lang.Integer","int",
            "eq", "not_eq", "in", "gt", "gte", "lt", "lte", "bw"
    ),
    BIGDECIMAL(
            "java.math.BigDecimal","bigDecimal",
            "eq", "not_eq", "in", "gt", "gte", "lt", "lte", "bw"
    ),


    ;

    private String className;
    private String type;
    private String[] operator;

    EntityType(String className, String type, String... operator) {
        this.className = className;
        this.type = type;
        this.operator = operator;
    }

    public static String getTypeByClassName(Class clazz) {
        var className = clazz.getName();
        if(clazz.isEnum()){
            return clazz.getCanonicalName();
        }

        var entityType = EntityType.values();
        for (EntityType entity : entityType) {
            if (entity.getClassName().equals(className)) {
                return entity.getType();
            }
        }
        return null;
    }

    public static String[] getOperatorByClassName(Class clazz) {
        var className = clazz.getName();
        if(clazz.isEnum()){
            className = ENUM.getClassName();
        }
        var entityType = EntityType.values();
        for (EntityType entity : entityType) {
            if (entity.getClassName().equals(className)) {
                return entity.getOperator();
            }
        }
        return null;
    }

    public static String getClassNameByType(String type) {
        var entityType = EntityType.values();
        for (EntityType entity : entityType) {
            if (entity.getType().equals(type)) {
                return entity.getClassName();
            }
        }
        return type;
    }

    public static <T extends Enum<T>> T getEnum(Class<T> c, String string) {
        if( c != null && string != null ) {
            try {
                return Enum.valueOf(c, string.trim().toUpperCase());
            } catch(IllegalArgumentException ex) {
            }
        }
        return null;
    }
}
