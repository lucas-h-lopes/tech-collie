package com.fatec.techcollie.web.dto.page;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

@Getter
public class PageableDTO {

    private final List<?> content;
    private final boolean isFirst;
    private final boolean isLast;
    private final Integer totalPages;
    private final long totalElements;
    private final Integer size;
    @JsonProperty("page")
    private final Integer number;
    @JsonProperty("elementsInPage")
    private final Integer numberOfElements;

    public PageableDTO(Page<?> page){
        this.content = page.getContent();
        this.isFirst = page.isFirst();
        this.isLast = page.isLast();
        this.totalPages = page.getTotalPages();
        this.totalElements = page.getTotalElements();
        this.size = page.getSize();
        this.number = page.getNumber();
        this.numberOfElements = page.getNumberOfElements();

    }
}
