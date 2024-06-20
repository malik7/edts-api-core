package id.co.edts.apicore.utils;

import id.co.edts.apicore.constant.ApiConstant;
import id.co.edts.apicore.dto.PageableResponseDto;
import id.co.edts.apicore.dto.ResponseDto;
import org.springframework.stereotype.Component;

@Component
public class ResponseUtil {
    public static <T> PageableResponseDto<T> success(PageableResponseDto response) {
        response.setCode(ApiConstant.SUCCESS_ERROR_CODE);
        response.setCodeSystem(ApiConstant.DEFAULT_SOURCE_SYSTEM);
        return response;
    }

    public static <T> ResponseDto<T> success(ResponseDto response) {
        response.setCode(ApiConstant.SUCCESS_ERROR_CODE);
        response.setCodeSystem(ApiConstant.DEFAULT_SOURCE_SYSTEM);
        return response;
    }

    public static <T> ResponseDto<T> failed(ResponseDto response, String statusCode, String errorMsg) {
        response.setCode(statusCode);
        response.setCodeSystem(ApiConstant.DEFAULT_SOURCE_SYSTEM);
        response.setMessageError(errorMsg);
        response.setMessage(errorMsg);
        return response;
    }
}
