package com.fatec.techcollie.web.dto.post;

import com.fatec.techcollie.web.dto.user.UserBasicDTO;

import java.time.LocalDateTime;
import java.util.UUID;

public record PostResponseDTO(
        UUID id,
        String text,
        LocalDateTime createdAt,
        UserBasicDTO user
) {
}
