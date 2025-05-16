package com.fatec.techcollie.service;

import com.fatec.techcollie.builder.AuditingLogRequestBuilder;
import com.fatec.techcollie.jwt.AuthenticatedUserProvider;
import com.fatec.techcollie.logging.LogService;
import com.fatec.techcollie.model.Post;
import com.fatec.techcollie.model.User;
import com.fatec.techcollie.model.enums.Action;
import com.fatec.techcollie.repository.PostRepository;
import com.fatec.techcollie.repository.projection.PostProjection;
import com.fatec.techcollie.repository.projection.impl.PostProjectionImplementation;
import com.fatec.techcollie.service.exception.InternalServerErrorException;
import com.fatec.techcollie.service.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final LogService logService;
    private final AuditingLogRequestBuilder builder = new AuditingLogRequestBuilder();
    private final AuthenticatedUserProvider provider;

    @Transactional
    public Post insert(Post post) {
        try {
            post.setContent(post.getContent()
                    .trim());
            Post postInDb = postRepository.save(post);

            String authenticatedEmail = provider.getAuthenticatedEmail();

            logService.insertIntoLog(
                    builder.withAction(Action.INSERT)
                            .withEmail(authenticatedEmail)
                            .withRecordId(post.getId().toString())
                            .withTableName(Post.class)
                            .build()
            );

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

    public Page<PostProjection> getByUser(User user, Pageable pageable){
        Page<Post> allPosts = postRepository.findAllByUser(user, pageable);

        return allPosts
                .map(PostProjectionImplementation::new);
    }
}