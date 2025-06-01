package service;

import dto.TeamRequest;
import entity.Team;
import entity.User;
import enums.Role;
import org.apache.coyote.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.TeamRepository;
import repository.UserRepository;
import security.CurrentUserProvider;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TeamService {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final CurrentUserProvider currectuserProvider;

    @Autowired
    public TeamService(TeamRepository teamRepository, UserRepository userRepository, CurrentUserProvider currectuserProvider) {
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
        this.currectuserProvider = currectuserProvider;
    }

    public List<TeamRequest> getAllTeams() {
        return teamRepository.findAll().stream()
                .map(this::toTeamRequest)
                .collect(Collectors.toList());
    }

    public TeamRequest getTeamById(Long id) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));
        return toTeamRequest(team);
    }

    public TeamRequest createTeam(TeamRequest request) {
        User user = currectuserProvider.getCurrentUser();
        if(user != null) {
            request.setUserId(user.getId());
        } else {
            throw new RuntimeException("Usuario no autenticado");
        }
        Team team = new Team(
                null,
                request.getPokemon1(),
                request.getPokemon2(),
                request.getPokemon3(),
                request.getPokemon4(),
                request.getPokemon5(),
                request.getPokemon6(),
                user
        );
        Team saved = teamRepository.save(team);
        return toTeamRequest(saved);
    }

    public TeamRequest updateTeam(Long id, TeamRequest request) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));
        team.setPokemon1(request.getPokemon1());
        team.setPokemon2(request.getPokemon2());
        team.setPokemon3(request.getPokemon3());
        team.setPokemon4(request.getPokemon4());
        team.setPokemon5(request.getPokemon5());
        team.setPokemon6(request.getPokemon6());
        Team updated = teamRepository.save(team);
        return toTeamRequest(updated);
    }

    public void deleteTeam(Long id) {
        if (!teamRepository.existsById(id)) {
            throw new RuntimeException("Equipo no encontrado");
        }
        User user = currectuserProvider.getCurrentUser();
        if(user.getRole()== Role.ADMIN || user.getTeams().stream().anyMatch(team -> team.getId().equals(id))) {
            teamRepository.deleteById(id);
        } else {
            throw new RuntimeException("Usuario no autorizado para eliminar este equipo");
        }
    }

    private TeamRequest toTeamRequest(Team team) {
        return new TeamRequest(
                team.getId(),
                team.getPokemon1(),
                team.getPokemon2(),
                team.getPokemon3(),
                team.getPokemon4(),
                team.getPokemon5(),
                team.getPokemon6(),
                team.getUser().getId()
        );
    }
}