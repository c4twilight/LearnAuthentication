package com.example.LearnAuthentication.service;

import com.example.LearnAuthentication.entity.Contributor;

import java.util.List;
import java.util.Optional;

public interface ContributorService {

    Long createContributor(Contributor contributor);
    List<Contributor> getAllContributors();
    Optional<Contributor> getContributorById(Long id);
}
