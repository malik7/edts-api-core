package id.co.edts.apicore.utils;

import id.co.edts.apicore.annotation.entity.*;
import id.co.edts.apicore.constant.EntityType;

import java.lang.reflect.Field;
import java.util.*;

public class EntityUtil {

    public static <T> String[] getFilterColumn(Class<T> clazz) {
        List<String> result = new ArrayList<>();
        getFilterColumn(result, clazz);
        return result.stream().toArray(String[]::new);
    }

    private static <T> void getFilterColumn(List<String> result, Class<T> clazz){
        var fields = clazz.getDeclaredFields();
        for(Field field : fields){
            var annotate = field.getAnnotation(EnableGlobalFilter.class);
            if(annotate != null) result.add(field.getName());
        }

        var clazzSuper = clazz.getSuperclass();
        if(clazzSuper != null) getFilterColumn(result, clazzSuper);
    }

    public static <T> Map constructMetadata(Class<T> clazz) {
        Map result = new HashMap();
        constructMetadata(result, clazz);
        return result;
    }

    private static <T> void constructMetadata(Map result, Class<T> clazz){
        var fields = clazz.getDeclaredFields();
        for(Field field : fields){
            var anotateIgnoreMetadata = field.getAnnotation(IgnoreMetadata.class);
            if (anotateIgnoreMetadata != null) {
                continue;
            }
            var annotateFilter = field.getAnnotation(EnableFilter.class);
            var annotateSort = field.getAnnotation(EnableSort.class);
            var metadataName = field.getAnnotation(MetadataName.class);
            var orderHeader = field.getAnnotation(OrderingHeader.class);

            Map obj = new HashMap();
            obj.put("type", EntityType.getTypeByClassName(field.getType()));
            obj.put("filter", annotateFilter != null);
            obj.put("order", annotateSort != null);
            obj.put("operator", EntityType.getOperatorByClassName(field.getType()));
            obj.put("name", metadataName == null ? "" : metadataName.columnName());
            obj.put("orderHeader", orderHeader == null ? "" : orderHeader.numOrder());

            var e = annotateFilter != null && annotateFilter.enumClass().length > 0
                    ? annotateFilter.enumClass()[0] : null;
            if(e != null){
                obj.put("value", EnumSet.allOf(e));
            }

            result.put(field.getName(), obj);
        }

        var clazzSuper = clazz.getSuperclass();
        if(clazzSuper != null) constructMetadata(result, clazzSuper);
    }
}
