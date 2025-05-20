package com.fatec.techcollie.service;

import com.fatec.techcollie.builder.AuditingLogRequestBuilder;
import com.fatec.techcollie.jwt.AuthenticatedUserProvider;
import com.fatec.techcollie.logging.LogService;
import com.fatec.techcollie.model.Post;
import com.fatec.techcollie.model.User;
import com.fatec.techcollie.model.enums.Action;
import com.fatec.techcollie.model.enums.UserRole;
import com.fatec.techcollie.repository.PostRepository;

import com.fatec.techcollie.service.exception.InternalServerErrorException;
import com.fatec.techcollie.service.exception.NotFoundException;
import com.fatec.techcollie.web.dto.post.PostBasicDTO;
import com.fatec.techcollie.web.dto.post.PostResponseDTO;
import com.fatec.techcollie.web.mapper.PostMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final LogService logService;
    private final AuditingLogRequestBuilder builder = new AuditingLogRequestBuilder();
    private final UserService userService;

    @Transactional
    public Post insert(Post post) {
        try {
            post.setContent(post.getContent()
                    .trim());
            Post postInDb = postRepository.save(post);
            post.setUser(userService.getById(post.getUser().getId())); // carrega o usuário completo

            logService.insertIntoLog(builder.withAction(Action.INSERT)
                    .withEmail(post.getUser().getEmail())
                    .withRecordId(post.getId().toString())
                    .withTableName(Post.class).build());

            return postInDb;
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerErrorException("Algo deu errado durante o processamento da solicitação");
        }
    }

    public Post getById(UUID id){
        return postRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Post não encontrado"));
    }

    public Page<PostBasicDTO> getByUser(User user, Pageable pageable){
        Page<Post> allPosts = postRepository.findAllByUser(user, pageable);

        return allPosts
                .map(PostMapper::toBasicDTO);
    }

    public Page<PostResponseDTO> getAll(Pageable pageable, String content){
        Post examplePost = new Post();
        examplePost.setContent(content);

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase()
                .withIgnoreNullValues();

        Example<Post> example = Example.of(examplePost, matcher);

        Page<Post> allPosts = postRepository.findAll(example, pageable);

        return allPosts
                .map(PostMapper::toResponseDTO);
    }

    @Transactional
    public void delete(UUID id) {
        User authenticatedUser = userService.getById(AuthenticatedUserProvider.getAuthenticatedId());
        Post post = getById(id);

        if(!authenticatedUser.equals(post.getUser()) && authenticatedUser.getRole().equals(UserRole.MINIMUM_ACCESS)){
            throw new AccessDeniedException("Permissões insuficientes para realizar esta ação");
        }
        postRepository.delete(post);

        logService.insertIntoLog(builder.withAction(Action.DELETE)
                .withEmail(authenticatedUser.getEmail())
                .withRecordId(post.getId().toString())
                .withTableName(Post.class).build());
    }
    @Transactional
    public void update(Post post, UUID postId){
        Post postOfRequest = getById(postId);
        User authenticatedUser = userService.getById(AuthenticatedUserProvider.getAuthenticatedId());

        if(!postOfRequest.getUser().equals(authenticatedUser)){
            throw new AccessDeniedException("Permissões insuficientes para realizar esta ação");
        }

        postOfRequest.setContent(post.getContent());

        logService.insertIntoLog(builder.withAction(Action.UPDATE)
                .withEmail(authenticatedUser.getEmail())
                .withRecordId(postOfRequest.getId().toString())
                .withTableName(Post.class).build());
    }

}