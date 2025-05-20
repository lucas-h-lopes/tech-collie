package com.fatec.techcollie.web.dto.post;

import java.time.LocalDateTime;
import java.util.UUID;

public record PostBasicDTO(
        UUID id,
        String text,
        LocalDateTime createdAt
) {
}
