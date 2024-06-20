package id.co.edts.apicore.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDto<T> extends StatusResponseDto implements Serializable {

    private static final long serialVersionUID = -832357979562246025L;

    @JsonProperty(index = 5)
    private T result;

}
