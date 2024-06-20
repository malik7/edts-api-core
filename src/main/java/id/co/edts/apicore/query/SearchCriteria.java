package id.co.edts.apicore.query;

import lombok.Data;

@Data
public class SearchCriteria<T> {

    private String key;
    private T value;
    private QueryOperator operation;

    public SearchCriteria(String key, T value, QueryOperator operation) {
        this.key = key;
        this.operation = operation;
        this.value = value;
    }

    public static enum QueryOperator {
        eq,
        not_eq,
        gt,
        gte,
        in,
        lt,
        lte,
        contains,
        is_empty,
        is_not_empty,
        bw,
        ;
    }
}
