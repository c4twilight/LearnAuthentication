package com.example.LearnAuthentication.controller;

import com.example.LearnAuthentication.dto.ProjectSearchAndFilterRequest;
import com.example.LearnAuthentication.entity.Project;
import com.example.LearnAuthentication.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import java.util.List;

@RestController
@RequestMapping("/api/v1/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @Operation(summary = "Create a new project", description = "Provide project details to create a new entry.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created the project"),
            @ApiResponse(responseCode = "400", description = "Invalid request payload"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/create")
    public ResponseEntity<Long> createProject(@RequestBody Project project) {
        Long projectId = projectService.createProject(project);
        return ResponseEntity.ok(projectId);
    }

    @Operation(summary = "Get all projects", description = "Retrieve a list of all projects.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of projects"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<Project>> getAllProjects() {
        List<Project> projects = projectService.getAllProjects();
        return ResponseEntity.ok(projects);
    }

    @Operation(summary = "Get a project by ID", description = "Retrieve details of a specific project by providing its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the project"),
            @ApiResponse(responseCode = "404", description = "Project not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable Long id) {
        Project project = projectService.getProjectById(id)
                .orElseThrow(() -> new RuntimeException("Project not found for ID: " + id));
        return ResponseEntity.ok(project);
    }

    @Operation(summary = "Search and filter projects with pagination", description = "Search and filter projects based on the provided criteria.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the filtered project list"),
            @ApiResponse(responseCode = "400", description = "Invalid search/filter criteria"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/pages")
    public ResponseEntity<Page<Project>> searchAndFilterProjects(@RequestBody ProjectSearchAndFilterRequest searchRequest) {
        Page<Project> projects = projectService.searchAndFilterProject(searchRequest);
        return ResponseEntity.ok(projects);
    }
}
