package com.fatec.techcollie.web.mapper;

import com.fatec.techcollie.web.dto.page.PageableDTO;
import com.fatec.techcollie.web.dto.page.WithUserPageableDTO;
import com.fatec.techcollie.web.dto.user.UserBasicDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PageableMapper {

    public static PageableDTO toDTO(Page<?> page){
        return new PageableDTO(page);
    }

    public static WithUserPageableDTO toUserDTO(Page<?> page, UserBasicDTO dto){
        return new WithUserPageableDTO(page, dto);
    }
}
