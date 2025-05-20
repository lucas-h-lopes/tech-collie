package com.fatec.techcollie.web.mapper;

import com.fatec.techcollie.jwt.AuthenticatedUserProvider;
import com.fatec.techcollie.model.Post;
import com.fatec.techcollie.model.User;
import com.fatec.techcollie.web.dto.post.PostBasicDTO;
import com.fatec.techcollie.web.dto.post.PostCreateDTO;
import com.fatec.techcollie.web.dto.post.PostResponseDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PostMapper {

    public static Post toPost(PostCreateDTO dto){
        User user = new User();
        user.setId(AuthenticatedUserProvider.getAuthenticatedId());

        Post post = new Post();
        post.setContent(dto.text());
        post.setUser(user);
        return post;
    }

    public static PostResponseDTO toResponseDTO(Post post){
        return new PostResponseDTO(
                post.getId(), post.getContent(), post.getCreatedAt(), UserMapper.toBasicUser(post.getUser())
        );
    }

    public static PostBasicDTO toBasicDTO(Post post){
        return new PostBasicDTO(post.getId(), post.getContent(), post.getCreatedAt());
    }
}
