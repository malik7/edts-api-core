package id.co.edts.apicore.query.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import id.co.edts.apicore.constant.EntityType;
import id.co.edts.apicore.query.SearchCriteria;
import id.co.edts.apicore.query.SearchSpecificationBuilder;
import id.co.edts.apicore.utils.DateUtil;
import lombok.Data;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class CriteriaField {
    private String q;
    private Map<String, FilterField> filter = new HashMap<>();
    private Map<String, Sort.Direction> order;
    private Integer pageNumber = 0;
    private Integer pageSize = 10;

    @JsonIgnore
    public Pageable getPageable() {
        List<Sort.Order> listOrder = new ArrayList<>();

        if(ObjectUtils.isNotEmpty(this.order)) {
            this.order.forEach((k, v) -> listOrder.add(new Sort.Order(v, k)));
        }

        return PageRequest.of(this.pageNumber, this.pageSize, Sort.by(listOrder));
    }

    public <T> SearchSpecificationBuilder<T> getSearchSpecificationBuilder(String[] filterColumns) {
        SearchSpecificationBuilder<T> specificationAnd = new SearchSpecificationBuilder<>(false);

        var filter = this.getFilter();
        for (String key : filter.keySet()) {
            var filterField = filter.get(key);
            if(ObjectUtils.isEmpty(filterField)) {
                continue;
            }

            if(new FilterField().equals(filterField)) {
                continue;
            }

            castFilterFieldValue(filterField);

            if(filterField.getOperator() == SearchCriteria.QueryOperator.bw) {
                specificationAnd = setSpecificationQueryBetween(specificationAnd, filterField, key);
            } else {
                specificationAnd = specificationAnd
                        .with(new SearchCriteria(key, filterField.getValue(), filterField.getOperator()));
            }
        }

        if (StringUtils.isNotBlank(this.getQ())) {
            SearchSpecificationBuilder<T> specificationOr = new SearchSpecificationBuilder<>(true);
            for (var column : filterColumns) {
                specificationOr = specificationOr
                        .with(new SearchCriteria(column, this.getQ(), SearchCriteria.QueryOperator.contains));
            }

            if (!specificationOr.isEmpty()) {
                specificationAnd = specificationAnd.and(specificationOr);
            }
        }

        return specificationAnd;
    }

    private void castFilterFieldValue(FilterField filterField) {
        if(filterField.getOperator() == SearchCriteria.QueryOperator.bw) {
            return;
        }

        if(filterField.getOperator() == SearchCriteria.QueryOperator.in) {

            var valueListObject = (ArrayList) filterField.getValue();
            List valueList = new ArrayList();
            valueListObject.forEach(i ->valueList.add(castValueFromObject(filterField.getType(), i)));

            switch (filterField.getType()){
                case "string": filterField.setValue(valueList.toArray(String[]::new));break;
                case "long": filterField.setValue(valueList.toArray(Long[]::new));break;
                case "double": filterField.setValue(valueList.toArray(Double[]::new));break;
                case "int": filterField.setValue(valueList.toArray(Integer[]::new));break;
                case "bigDecimal": filterField.setValue(valueList.toArray(BigDecimal[]::new));break;
                default : filterField.setValue(valueList.toArray(Enum[]::new));break;
            }
            return;
        }

        filterField.setValue(castValueFromObject(filterField.getType(), filterField.getValue()));
    }

    private SearchSpecificationBuilder setSpecificationQueryBetween(
            SearchSpecificationBuilder specificationAnd, FilterField filterField, String key
    ) {
        var valueListObject = (ArrayList) filterField.getValue();
        if(valueListObject.size() != 2) {
            throw new RuntimeException("Error value list must size 2");
        }

        var valueFrom = castValueFromObject(filterField.getType(), valueListObject.get(0));
        var valueTo = castValueFromObject(filterField.getType(), valueListObject.get(1));

        specificationAnd = specificationAnd
                .with(new SearchCriteria(key, valueFrom, SearchCriteria.QueryOperator.gte));
        specificationAnd = specificationAnd
                .with(new SearchCriteria(key, valueTo, SearchCriteria.QueryOperator.lte));

        return specificationAnd;
    }

    private Object castValueFromObject(String type, Object value) {
        var cls = getClassDataType(type);
        String dateFormat = "yyyy-MM-dd HH:mm:ss";

        if(cls.isEnum()){
            var enumString = getEnumFromString(cls, String.valueOf(value));
            return enumString;
        }
        if(type.equalsIgnoreCase("date")) {
            String tempFrom = String.valueOf(value);
            value = DateUtil.constructDate(tempFrom, dateFormat);
        } else if(type.equalsIgnoreCase("localDate")) {
            String tempFrom = String.valueOf(value);
            var date = DateUtil.constructDate(tempFrom, dateFormat);
            if(date != null) {
                value = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            }
        } else if (type.equalsIgnoreCase("bigDecimal")) {
            value = new BigDecimal(String.valueOf(value));
        }else if(type.equalsIgnoreCase("long")){
            value = Long.parseLong(String.valueOf(value));
        }else if(type.equalsIgnoreCase("double")){
            value = Double.parseDouble(String.valueOf(value));
        } else {
            value = cls.cast(value);
        }

        return value;
    }

    private Class getClassDataType(String type) {
        var className = EntityType.getClassNameByType(type);

        Class cls = null;
        try {
            cls = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(
                    "Error data type in value getSearchSpecificationBuilder(). Not Found Data Types = " + className
            );
        }

        return cls;
    }

    public static <T extends Enum<T>> T getEnumFromString(Class<T> c, String string) {
        if( c != null && string != null ) {
            try {
                return Enum.valueOf(c, string.trim().toUpperCase());
            } catch(IllegalArgumentException ignored) {
            }
        }
        return null;
    }

}
