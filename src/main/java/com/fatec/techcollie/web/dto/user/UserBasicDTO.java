package com.fatec.techcollie.web.dto.user;

public record UserBasicDTO(
        Integer id,
        String username,
        String profilePictureUrl
) {
}
