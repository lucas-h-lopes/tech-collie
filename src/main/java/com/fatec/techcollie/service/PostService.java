package com.fatec.techcollie.service;

import com.fatec.techcollie.builder.AuditingLogRequestBuilder;
import com.fatec.techcollie.jwt.AuthenticatedUserProvider;
import com.fatec.techcollie.logging.AuditingLogRequest;
import com.fatec.techcollie.logging.LogService;
import com.fatec.techcollie.model.Post;
import com.fatec.techcollie.model.enums.Action;
import com.fatec.techcollie.repository.PostRepository;
import com.fatec.techcollie.service.exception.InternalServerErrorException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final LogService logService;
    private final AuditingLogRequestBuilder builder;
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
                            .withRecordId(post.getId())
                            .withTableName(Post.class)
                            .build()
            );

            return postInDb;
        } catch (Exception e) {
            throw new InternalServerErrorException("Algo deu errado durante o processamento da solicitação");
        }
    }
}