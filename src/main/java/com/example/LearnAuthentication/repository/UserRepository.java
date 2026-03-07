package com.example.LearnAuthentication.repository;

import com.example.LearnAuthentication.entity.UserInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserInfo, Long> {
    UserInfo findByUsername(String username);
    UserInfo findFirstById(Long id);
    boolean existsByUsername(String username);
}
