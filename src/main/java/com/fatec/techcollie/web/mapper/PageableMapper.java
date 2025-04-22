package com.fatec.techcollie.web.mapper;

import com.fatec.techcollie.web.dto.page.PageableDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PageableMapper {

    public static PageableDTO toDTO(Page<?> page){
        return new PageableDTO(page);
    }
}
