package com.fatec.techcollie.web.dto.post;

import com.fatec.techcollie.web.dto.user.UserPostResponseDTO;

import java.time.LocalDateTime;
import java.util.UUID;

public record PostResponseDTO(
        UUID id,
        String content,
        LocalDateTime createdAt,
        UserPostResponseDTO author
) {
}
