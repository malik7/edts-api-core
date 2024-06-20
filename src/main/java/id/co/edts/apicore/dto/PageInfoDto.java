package id.co.edts.apicore.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class PageInfoDto implements Serializable {
    private static final long serialVersionUID = 5125599960933767813L;

    private long totalElements;
    private int totalPages;
    private int pageNumber;
    private int pageSize;

    public PageInfoDto(long totalElements, int totalPages, int pageNumber, int pageSize) {
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }
}
