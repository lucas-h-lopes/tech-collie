package com.fatec.techcollie.repository;

import com.fatec.techcollie.model.Post;
import com.fatec.techcollie.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID> {

    Page<Post> findAllByUser(User user, Pageable pageable);
}
