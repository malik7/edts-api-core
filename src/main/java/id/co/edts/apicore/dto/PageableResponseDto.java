package id.co.edts.apicore.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.Map;

@Data
public class PageableResponseDto<T> extends ResponseDto {
    @JsonProperty(index = 6)
    private PageInfoDto pageInfo;

    @JsonProperty(index = 7)
    private Map metaData;

    public PageableResponseDto(Map metaData, Page page){
        this.setResult(page.getContent());
        this.pageInfo = constructPageInfo(page);
        this.metaData = metaData;
    }

    public PageableResponseDto(Map metaData, Page page, T obj){
        this.setResult(obj);
        this.pageInfo = constructPageInfo(page);
        this.metaData = metaData;
    }

    public PageableResponseDto(Page page, T obj){
        this.setResult(obj);
        this.pageInfo = constructPageInfo(page);
    }

    public PageableResponseDto(PageInfoDto pageInfoDto, T obj){
        this.setResult(obj);
        this.setPageInfo(pageInfoDto);
    }

    private PageInfoDto constructPageInfo(Page page){
        return new PageInfoDto(page.getTotalElements(), page.getTotalPages(),
                page.getNumber(), page.getSize());
    }
}
