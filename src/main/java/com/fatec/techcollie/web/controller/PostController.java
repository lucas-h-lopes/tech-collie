package com.fatec.techcollie.web.controller;

import com.fatec.techcollie.model.Post;
import com.fatec.techcollie.service.PostService;
import com.fatec.techcollie.service.UserService;
import com.fatec.techcollie.web.dto.page.PageableDTO;
import com.fatec.techcollie.web.dto.post.PostCreateDTO;
import com.fatec.techcollie.web.dto.post.PostResponseDTO;
import com.fatec.techcollie.web.mapper.PageableMapper;
import com.fatec.techcollie.web.mapper.PostMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final UserService userService;

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MINIMUM_ACCESS')")
    @PostMapping
    public ResponseEntity<PostResponseDTO> insert (
            @RequestBody @Valid PostCreateDTO dto
            ){
        Post post = postService.insert(PostMapper.toPost(dto));

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(post.getId())
                .toUri();

        return ResponseEntity.created(uri)
                .body(PostMapper.toResponseDTO(post));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MINIMUM_ACCESS')")
    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDTO> getById(@PathVariable UUID id){
        Post post = postService.getById(id);
        return ResponseEntity
                .ok(PostMapper.toResponseDTO(post));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MINIMUM_ACCESS')")
    @GetMapping
    public ResponseEntity<PageableDTO> getAll(@PageableDefault(size = 5, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
                                              @RequestParam(required = false, name = "text") String text){
        Page<PostResponseDTO> allPosts = postService.getAll(pageable, text);
        return ResponseEntity.ok(PageableMapper.toDTO(allPosts));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MINIMUM_ACCESS')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        postService.delete(id);
        return ResponseEntity.noContent()
                .build();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MINIMUM_ACCESS')")
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable UUID id, @RequestBody @Valid PostCreateDTO dto){
        postService.update(PostMapper.toPost(dto), id);
        return ResponseEntity.noContent()
                .build();
    }
}
