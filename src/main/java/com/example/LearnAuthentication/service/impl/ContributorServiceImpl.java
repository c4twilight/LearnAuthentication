package com.example.LearnAuthentication.service.impl;
import com.example.LearnAuthentication.entity.Contributor;
import com.example.LearnAuthentication.repository.ContributorRepository;
import com.example.LearnAuthentication.service.ContributorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContributorServiceImpl implements ContributorService {

    @Autowired
    ContributorRepository contributorRepository;

    @Override
    public Long createContributor(Contributor contributor) {
        return contributorRepository.save(contributor).getId();
    }

    @Override
    public List<Contributor> getAllContributors() {
        return (List<Contributor>) contributorRepository.findAll();
    }

    @Override
    public Optional<Contributor> getContributorById(Long id) {
        return contributorRepository.findById(id);
    }
}