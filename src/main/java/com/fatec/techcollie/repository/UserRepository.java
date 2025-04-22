package com.fatec.techcollie.repository;

import com.fatec.techcollie.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer>, QueryByExampleExecutor<User> {
}
