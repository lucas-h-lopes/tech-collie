package com.fatec.techcollie.web.dto.user;

public record UserPostResponseDTO(
        Integer id,
        String username,
        String profilePictureUrl
) {
}
