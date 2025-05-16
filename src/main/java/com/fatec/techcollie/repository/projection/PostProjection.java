package com.fatec.techcollie.repository.projection;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.LocalDateTime;
import java.util.UUID;

@JsonPropertyOrder({"id", "content", "createdAt"})
public interface PostProjection {

    UUID getId();
    @JsonProperty("text")
    String getContent();
    LocalDateTime getCreatedAt();
}
