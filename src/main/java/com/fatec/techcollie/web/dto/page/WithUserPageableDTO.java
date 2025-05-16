package com.fatec.techcollie.web.dto.page;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fatec.techcollie.web.dto.user.UserBasicDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Getter @Setter
@JsonPropertyOrder({"user", "content", "isFirst", "isLast", "totalPages", "totalElements", "size", "number", "numberOfElements"})
public class WithUserPageableDTO extends PageableDTO{

    private UserBasicDTO user;

    public WithUserPageableDTO(Page<?> page, UserBasicDTO userDto){
        super(page);
        this.user = userDto;
    }
}
