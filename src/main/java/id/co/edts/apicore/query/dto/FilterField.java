package id.co.edts.apicore.query.dto;

import id.co.edts.apicore.query.SearchCriteria;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FilterField {
    private SearchCriteria.QueryOperator operator;
    private String type;
    private Object value;
}
