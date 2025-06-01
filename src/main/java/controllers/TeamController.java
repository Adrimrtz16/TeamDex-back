package controllers;


import dto.TeamRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.TeamService;

import java.util.List;

@RestController
@RequestMapping("/teams")
public class TeamController {

    private final TeamService teamService;

    @Autowired
    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping
    public List<TeamRequest> getAllTeams() {
        return teamService.getAllTeams();
    }

    @GetMapping("/{id}")
    public TeamRequest getTeamById(@PathVariable Long id) {
        return teamService.getTeamById(id);
    }

    @PostMapping
    public TeamRequest createTeam(@RequestBody TeamRequest request) {
        return teamService.createTeam(request);
    }

    @PutMapping("/{id}")
    public TeamRequest updateTeam(@PathVariable Long id, @RequestBody TeamRequest request) {
        return teamService.updateTeam(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteTeam(@PathVariable Long id) {
        teamService.deleteTeam(id);
    }
}