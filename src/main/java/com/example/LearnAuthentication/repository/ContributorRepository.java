package com.example.LearnAuthentication.repository;

import com.example.LearnAuthentication.entity.Contributor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContributorRepository extends CrudRepository<Contributor, Long> {
}
