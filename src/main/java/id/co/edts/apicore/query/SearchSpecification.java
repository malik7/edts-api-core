package id.co.edts.apicore.query;

import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

public class SearchSpecification<ENTITY> implements Specification<ENTITY> {
    private SearchCriteria searchCriteria;

    public SearchSpecification(SearchCriteria searchCriteria) {
        this.searchCriteria = searchCriteria;
    }

    public Predicate toPredicate(Root<ENTITY> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        String key = this.searchCriteria.getKey();
        SearchCriteria.QueryOperator operation = this.searchCriteria.getOperation();
        var value = this.searchCriteria.getValue();
        switch (operation) {
            case eq:
                return equal(root, cb);
            case not_eq:
                return notEqual(root, cb);
            case lt:
                return lessThan(root, cb);
            case lte:
                return lessThanOrEqualTo(root, cb);
            case gt:
                return greaterThan(root, cb);
            case gte:
                return greaterThanOrEqualTo(root, cb);
            case in:
                return getInclauseExpression(root, cb);
            case contains:
            return cb.like(cb.upper(generateExpression(root, key).as(String.class)), wrapLikeQuery((String) value));
            case is_empty:
                return cb.isNull(generateExpression(root, key));
            case is_not_empty:
                return cb.isNotNull(generateExpression(root, key));
        }
        return null;
    }

    protected String wrapLikeQuery(String txt) {
        return "%" + txt.toUpperCase() + '%';
    }

    private Predicate getInclauseExpression(Root<ENTITY> root, CriteriaBuilder cb) {

        if (this.searchCriteria.getValue() instanceof String[]) {
            CriteriaBuilder.In<String> inClause = cb.in((Expression) generateExpression(root, this.searchCriteria.getKey()));
            String[] list = (String[]) this.searchCriteria.getValue();
            for (String x : list)
                inClause.value(x);
            return (Predicate) inClause;
        }

        if (this.searchCriteria.getValue() instanceof Integer[]) {
            CriteriaBuilder.In<Integer> inClause = cb.in((Expression) generateExpression(root, this.searchCriteria.getKey()));
            Integer[] list = (Integer[]) this.searchCriteria.getValue();
            for (Integer x : list)
                inClause.value(x);
            return (Predicate) inClause;
        }

        if (this.searchCriteria.getValue() instanceof Long[]) {
            CriteriaBuilder.In<Long> inClause = cb.in((Expression) generateExpression(root, this.searchCriteria.getKey()));
            Long[] list = (Long[]) this.searchCriteria.getValue();
            for (Long x : list)
                inClause.value(x);
            return (Predicate) inClause;
        }

        if (this.searchCriteria.getValue() instanceof Double[]) {
            CriteriaBuilder.In<Double> inClause = cb.in((Expression) generateExpression(root, this.searchCriteria.getKey()));
            Double[] list = (Double[]) this.searchCriteria.getValue();
            for (Double x : list)
                inClause.value(x);
            return (Predicate) inClause;
        }

        if (this.searchCriteria.getValue() instanceof BigDecimal[]) {
            CriteriaBuilder.In<BigDecimal> inClause = cb.in((Expression) generateExpression(root, this.searchCriteria.getKey()));
            BigDecimal[] list = (BigDecimal[]) this.searchCriteria.getValue();
            for (BigDecimal x : list)
                inClause.value(x);
            return (Predicate) inClause;
        }

        if (this.searchCriteria.getValue() instanceof Enum[]) {
            CriteriaBuilder.In<Enum> inClause = cb.in((Expression) generateExpression(root, this.searchCriteria.getKey()));
            Enum[] list = (Enum[]) this.searchCriteria.getValue();
            for (Enum x : list)
                inClause.value(x);
            return (Predicate) inClause;
        }

        if (this.searchCriteria.getValue() instanceof BigDecimal[]) {
            CriteriaBuilder.In<BigDecimal> inClause = cb.in((Expression) generateExpression(root, this.searchCriteria.getKey()));
            BigDecimal[] list = (BigDecimal[]) this.searchCriteria.getValue();
            for (BigDecimal x : list)
                inClause.value(x);
            return (Predicate) inClause;
        }

        throw new RuntimeException("Error predicate getInclauseExpression(). Invalid Data Types = "+this.searchCriteria.getValue().getClass().getSimpleName());
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            Double d = Double.parseDouble(strNum);
            Integer i = Integer.parseInt(strNum);
            Long l = Long.parseLong(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    private Predicate equal(Root<ENTITY> root, CriteriaBuilder cb) {
        if (isNumeric(this.searchCriteria.getValue().toString())) {
            return cb.equal(generateExpression(root, this.searchCriteria.getKey()), this.searchCriteria.getValue());
        }
        return cb.equal(cb.upper(generateExpression(root, this.searchCriteria.getKey()).as(String.class)), this.searchCriteria.getValue().toString().toUpperCase());
    }

    private Predicate notEqual(Root<ENTITY> root, CriteriaBuilder cb) {
        if (isNumeric(this.searchCriteria.getValue().toString())) {
            return cb.notEqual(generateExpression(root, this.searchCriteria.getKey()), this.searchCriteria.getValue());
        }
        return cb.notEqual(cb.upper(generateExpression(root, this.searchCriteria.getKey()).as(String.class)), this.searchCriteria.getValue().toString().toUpperCase());
    }

    private Predicate lessThan(Root<ENTITY> root, CriteriaBuilder cb) {

        if (this.searchCriteria.getValue() instanceof Integer) {
            return cb.lessThan(generateExpression(root, this.searchCriteria.getKey()), (Integer) this.searchCriteria.getValue());
        }

        if (this.searchCriteria.getValue() instanceof Long) {
            return cb.lessThan(generateExpression(root, this.searchCriteria.getKey()), (Long) this.searchCriteria.getValue());
        }

        if (this.searchCriteria.getValue() instanceof Date) {
            return cb.lessThan(generateExpression(root, this.searchCriteria.getKey()), (Date) this.searchCriteria.getValue());
        }

        if (this.searchCriteria.getValue() instanceof LocalDate) {
            return cb.lessThan(generateExpression(root, this.searchCriteria.getKey()), (LocalDate) this.searchCriteria.getValue());
        }

        if (this.searchCriteria.getValue() instanceof Double) {
            return cb.lessThan(generateExpression(root, this.searchCriteria.getKey()), (Double) this.searchCriteria.getValue());
        }

        if (this.searchCriteria.getValue() instanceof Float) {
            return cb.lessThan(generateExpression(root, this.searchCriteria.getKey()), (Float) this.searchCriteria.getValue());
        }

        if (this.searchCriteria.getValue() instanceof BigDecimal) {
            return cb.lessThan(generateExpression(root, this.searchCriteria.getKey()), (BigDecimal) this.searchCriteria.getValue());
        }

        throw new RuntimeException("Error predicate lessThan(). Invalid Data Types = "+this.searchCriteria.getValue().getClass().getSimpleName());
    }

    private Predicate lessThanOrEqualTo(Root<ENTITY> root, CriteriaBuilder cb) {

        if (this.searchCriteria.getValue() instanceof Integer) {
            return cb.lessThanOrEqualTo(generateExpression(root, this.searchCriteria.getKey()), (Integer) this.searchCriteria.getValue());
        }

        if (this.searchCriteria.getValue() instanceof Long) {
            return cb.lessThanOrEqualTo(generateExpression(root, this.searchCriteria.getKey()), (Long) this.searchCriteria.getValue());
        }

        if (this.searchCriteria.getValue() instanceof Date) {
            return cb.lessThanOrEqualTo(generateExpression(root, this.searchCriteria.getKey()), (Date) this.searchCriteria.getValue());
        }

        if (this.searchCriteria.getValue() instanceof LocalDate) {
            return cb.lessThanOrEqualTo(generateExpression(root, this.searchCriteria.getKey()), (LocalDate) this.searchCriteria.getValue());
        }

        if (this.searchCriteria.getValue() instanceof Double) {
            return cb.lessThanOrEqualTo(generateExpression(root, this.searchCriteria.getKey()), (Double) this.searchCriteria.getValue());
        }

        if (this.searchCriteria.getValue() instanceof Float) {
            return cb.lessThanOrEqualTo(generateExpression(root, this.searchCriteria.getKey()), (Float) this.searchCriteria.getValue());
        }

        if (this.searchCriteria.getValue() instanceof BigDecimal) {
            return cb.lessThanOrEqualTo(generateExpression(root, this.searchCriteria.getKey()), (BigDecimal) this.searchCriteria.getValue());
        }

        throw new RuntimeException("Error predicate lessThanOrEqualTo(). Invalid Data Types = "+this.searchCriteria.getValue().getClass().getSimpleName());
    }

    private Predicate greaterThan(Root<ENTITY> root, CriteriaBuilder cb) {

        if (this.searchCriteria.getValue() instanceof Integer) {
            return cb.greaterThan(generateExpression(root, this.searchCriteria.getKey()), (Integer) this.searchCriteria.getValue());
        }

        if (this.searchCriteria.getValue() instanceof Long) {
            return cb.greaterThan(generateExpression(root, this.searchCriteria.getKey()), (Long) this.searchCriteria.getValue());
        }

        if (this.searchCriteria.getValue() instanceof Date) {
            return cb.greaterThan(generateExpression(root, this.searchCriteria.getKey()), (Date) this.searchCriteria.getValue());
        }

        if (this.searchCriteria.getValue() instanceof LocalDate) {
            return cb.greaterThan(generateExpression(root, this.searchCriteria.getKey()), (LocalDate) this.searchCriteria.getValue());
        }

        if (this.searchCriteria.getValue() instanceof Double) {
            return cb.greaterThan(generateExpression(root, this.searchCriteria.getKey()), (Double) this.searchCriteria.getValue());
        }

        if (this.searchCriteria.getValue() instanceof Float) {
            return cb.greaterThan(generateExpression(root, this.searchCriteria.getKey()), (Float) this.searchCriteria.getValue());
        }

        if (this.searchCriteria.getValue() instanceof BigDecimal) {
            return cb.greaterThan(generateExpression(root, this.searchCriteria.getKey()), (BigDecimal) this.searchCriteria.getValue());
        }


        throw new RuntimeException("Error predicate greaterThan(). Invalid Data Types = "+this.searchCriteria.getValue().getClass().getSimpleName());
    }

    private Predicate greaterThanOrEqualTo(Root<ENTITY> root, CriteriaBuilder cb) {

        if (this.searchCriteria.getValue() instanceof Integer) {
            return cb.greaterThanOrEqualTo(generateExpression(root, this.searchCriteria.getKey()), (Integer) this.searchCriteria.getValue());
        }

        if (this.searchCriteria.getValue() instanceof Long) {
            return cb.greaterThanOrEqualTo(generateExpression(root, this.searchCriteria.getKey()), (Long) this.searchCriteria.getValue());
        }

        if (this.searchCriteria.getValue() instanceof Date) {
            return cb.greaterThanOrEqualTo(generateExpression(root, this.searchCriteria.getKey()), (Date) this.searchCriteria.getValue());
        }

        if (this.searchCriteria.getValue() instanceof LocalDate) {
            return cb.greaterThanOrEqualTo(generateExpression(root, this.searchCriteria.getKey()), (LocalDate) this.searchCriteria.getValue());
        }

        if (this.searchCriteria.getValue() instanceof Double) {
            return cb.greaterThanOrEqualTo(generateExpression(root, this.searchCriteria.getKey()), (Double) this.searchCriteria.getValue());
        }

        if (this.searchCriteria.getValue() instanceof Float) {
            return cb.greaterThanOrEqualTo(generateExpression(root, this.searchCriteria.getKey()), (Float) this.searchCriteria.getValue());
        }

        if (this.searchCriteria.getValue() instanceof BigDecimal) {
            return cb.greaterThanOrEqualTo(generateExpression(root, this.searchCriteria.getKey()), (BigDecimal) this.searchCriteria.getValue());
        }

        throw new RuntimeException("Error predicate greaterThanOrEqualTo(). Invalid Data Types = "+this.searchCriteria.getValue().getClass().getSimpleName());
    }

    private <Y> Path<Y> generateExpression(Root<ENTITY> root, String keys){
        String[] arrayKey = keys.split("\\.");
        Path<Y> result = null;
        if(arrayKey.length > 0) {
            for (String key : arrayKey) {
                if (result == null)
                    result = root.get(key);
                else
                    result = result.get(key);
            }
        }else{
            result = root.get(keys);
        }
        return result;
    }

}
