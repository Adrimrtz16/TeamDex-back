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

    @GetMapping("/get")
    @CrossOrigin(origins = {"http://localhost:5173", "https://team-dex-front.vercel.app"})
    public List<TeamRequest> getAllTeams() {
        return teamService.getAllTeams();
    }

    @GetMapping("/{id}")
    public TeamRequest getTeamById(@PathVariable Long id) {
        return teamService.getTeamById(id);
    }

    @PostMapping("/create")
    @CrossOrigin(origins = {"http://localhost:5173", "https://team-dex-front.vercel.app"})
    public TeamRequest createTeam(@RequestBody TeamRequest request) {
        return teamService.createTeam(request);
    }

    @PutMapping("/update/{id}")
    @CrossOrigin(origins = {"http://localhost:5173", "https://team-dex-front.vercel.app"})
    public TeamRequest updateTeam(@PathVariable Long id, @RequestBody TeamRequest request) {
        return teamService.updateTeam(id, request);
    }

    @DeleteMapping("/delete/{id}")
    @CrossOrigin(origins = {"http://localhost:5173", "https://team-dex-front.vercel.app"})
    public void deleteTeam(@PathVariable Long id) {
        teamService.deleteTeam(id);
    }
}