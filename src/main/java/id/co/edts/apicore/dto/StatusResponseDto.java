package id.co.edts.apicore.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StatusResponseDto implements Serializable {
    private static final long serialVersionUID = -2038446265050395593L;

    @JsonProperty(index = 1)
    private String code;

    @JsonProperty(index = 2)
    private String codeSystem;

    @JsonProperty(index = 3)
    private String message;

    @JsonProperty(index = 4)
    private String messageError;
}
