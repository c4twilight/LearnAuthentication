package com.example.LearnAuthentication.controller;

import com.example.LearnAuthentication.entity.Contributor;
import com.example.LearnAuthentication.service.ContributorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/contributors")
public class ContributorController {

    private final ContributorService contributorService;

    public ContributorController(ContributorService contributorService) {
        this.contributorService = contributorService;
    }

    @Operation(summary = "Create a new contributor", description = "Provide contributor details to create a new entry.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created contributor"),
            @ApiResponse(responseCode = "400", description = "Invalid request payload"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/create")
    public ResponseEntity<Long> createContributor(@RequestBody Contributor contributor) {
        Long contributorId = contributorService.createContributor(contributor);
        return ResponseEntity.ok(contributorId);
    }

    @Operation(summary = "Get all contributors", description = "Fetch all contributors from the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved contributors"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<Contributor>> getAllContributors() {
        List<Contributor> contributors = contributorService.getAllContributors();
        return ResponseEntity.ok(contributors);
    }

    @Operation(summary = "Get contributor by ID", description = "Provide an ID to fetch a specific contributor.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the contributor"),
            @ApiResponse(responseCode = "404", description = "Contributor not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Contributor> getContributorById(@PathVariable Long id) {
        Contributor contributor = contributorService.getContributorById(id)
                .orElseThrow(() -> new RuntimeException("Contributor not found for ID: " + id));
        return ResponseEntity.ok(contributor);
    }
}
