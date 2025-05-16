package com.fatec.techcollie.web.dto.user;

public record UserPostSimplifiedResponseDTO(
        Integer id,
        String username,
        String profilePictureUrl
) {
}
