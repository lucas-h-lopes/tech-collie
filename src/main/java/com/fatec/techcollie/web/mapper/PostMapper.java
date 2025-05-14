package com.fatec.techcollie.web.mapper;

import com.fatec.techcollie.model.Post;
import com.fatec.techcollie.model.User;
import com.fatec.techcollie.web.dto.post.PostCreateDTO;
import com.fatec.techcollie.web.dto.post.PostResponseDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PostMapper {

    public static Post toPostDTO(PostCreateDTO dto, User authenticatedUser){
        Post post = new Post();
        post.setContent(dto.content());
        post.setUser(authenticatedUser);
        return post;
    }

    public static PostResponseDTO toResponseDTO(Post post){
        return new PostResponseDTO(
                post.getId(), post.getContent(), post.getCreatedAt(), UserMapper.toUserPostResponseDTO(post.getUser())
        );
    }
}
