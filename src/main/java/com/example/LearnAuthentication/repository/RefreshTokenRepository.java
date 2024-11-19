package com.example.LearnAuthentication.repository;

import com.example.LearnAuthentication.entity.RefreshToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Integer> {

    Optional<RefreshToken> findByToken(String token);

    Optional<RefreshToken> findByUserInfoId(long id);
    //Optional<RefreshToken> deleteByUserId(String Id);

}
