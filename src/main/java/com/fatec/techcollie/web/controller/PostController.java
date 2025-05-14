package com.fatec.techcollie.web.controller;

import com.fatec.techcollie.jwt.AuthenticatedUserProvider;
import com.fatec.techcollie.model.Post;
import com.fatec.techcollie.model.User;
import com.fatec.techcollie.service.PostService;
import com.fatec.techcollie.service.UserService;
import com.fatec.techcollie.web.dto.post.PostCreateDTO;
import com.fatec.techcollie.web.dto.post.PostResponseDTO;
import com.fatec.techcollie.web.mapper.PostMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final UserService userService;
    private final AuthenticatedUserProvider provider;

    @PostMapping
    public ResponseEntity<PostResponseDTO> insert (
            @RequestBody @Valid PostCreateDTO dto
            ){
        User user = userService.getByEmail(provider.getAuthenticatedEmail());
        Post post = postService.insert(PostMapper.toPostDTO(dto, user));

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(post.getId())
                .toUri();

        return ResponseEntity.created(uri)
                .body(PostMapper.toResponseDTO(post));
    }
}
