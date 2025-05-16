package com.fatec.techcollie.repository.projection.impl;

import com.fatec.techcollie.model.Post;

import com.fatec.techcollie.repository.projection.PostProjection;


import java.time.LocalDateTime;
import java.util.UUID;

public class PostProjectionImplementation implements PostProjection {

    private final UUID id;
    private final String text;
    private final LocalDateTime createdAt;

    public PostProjectionImplementation(Post post){
        this.id = post.getId();
        this.text = post.getContent();
        this.createdAt = post.getCreatedAt();
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    @Override
    public String getContent() {
        return this.text;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }
}
