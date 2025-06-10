package service;

import dto.*;
import entity.User;
import enums.Role;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import repository.UserRepository;
import security.CurrentUserProvider;
import security.JwtUtil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final CurrentUserProvider currentUserProvider;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, CurrentUserProvider currentUserProvider ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.currentUserProvider = currentUserProvider;
    }

    public ResponseEntity<AuthResponse> login(AuthLoginRequest request) {
        Optional<User> user = userRepository.findByUsername(request.getUsername());
        if (user.isPresent() && passwordEncoder.matches(request.getPassword(),(user.get().getPassword())) ) {
            String token = jwtUtil.generateToken(user.get());

            return ResponseEntity.ok(new AuthResponse(token));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AuthResponse("Credenciales incorrectas"));
    }

    public void register(AuthRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("El usuario ya existe");
        }
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setProfilePictureUrl(request.getProfilePictureUrl());
        user.setBio("Voy a ser el mejor entrenador Pokémon del mundo! Wolfie no es nadie a mi lado.");
        Role role = (request.getRole() == null) ? Role.USER : request.getRole();
        user.setRole(role);
        userRepository.save(user);
    }

    public Object getUserById(Long id) {
        User currentUser = currentUserProvider.getCurrentUser();

        User target = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (currentUser == null) {
            return mapToPublic(target);
        }

        if (currentUser.getRole() == Role.ADMIN) {
            return mapToFull(target);
        }

        if (currentUser.getRole() == Role.USER && currentUser.getId().equals(id)) {
            return mapToFull(target);
        }

        if (currentUser.getRole() == Role.USER) {
            return mapToPublic(target);
        }

        throw new RuntimeException("No tienes autorización");
    }

    public Object getUserWithTeamsById(Long id) {
        User target = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return mapToPublicWithTeams(target);

    }

    public List<PublicUserRequest> getFirst20PublicUsers() {
        return userRepository.findAll().stream()
                .limit(20)
                .map(this::mapToPublic)
                .collect(Collectors.toList());
    }

    public Object getMe() {
        User currentUser = currentUserProvider.getCurrentUser();
        if (currentUser == null) {
            throw new RuntimeException("No autenticado");
        }
        return mapToFull(currentUser);
    }

    // Metodo para mapear un usuario a AuthRequest (para respuestas completas)
    private AuthRequest mapToFull(User user) {
        List<TeamRequest> equipos = user.getTeams() != null
                ? user.getTeams().stream()
                .map(team -> new TeamRequest(
                        team.getId(),
                        team.getPokemon1(),
                        team.getPokemon2(),
                        team.getPokemon3(),
                        team.getPokemon4(),
                        team.getPokemon5(),
                        team.getPokemon6(),
                        team.getName(),
                        team.getUser().getId()
                ))
                .collect(Collectors.toList())
                : List.of();
        return new AuthRequest(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getRole(),
                user.getProfilePictureUrl(),
                user.getBio(),
                equipos
        );
    }

    // Metodo para mapear un usuario a PublicUserRequest (parcialmente)
    private PublicUserRequest mapToPublic(User user) {
        return new PublicUserRequest(
                user.getId(),
                user.getUsername(),
                user.getProfilePictureUrl(),
                user.getBio()
        );
    }

    // Metodo para mapear un usuario a PublicUserWithTeamsRequest (con equipos)
    private PublicUserWithTeamsRequest mapToPublicWithTeams(User user) {
        List<TeamRequest> equipos = user.getTeams() != null
                ? user.getTeams().stream()
                .map(team -> new TeamRequest(
                        team.getId(),
                        team.getPokemon1(),
                        team.getPokemon2(),
                        team.getPokemon3(),
                        team.getPokemon4(),
                        team.getPokemon5(),
                        team.getPokemon6(),
                        team.getName(),
                        team.getUser().getId()
                ))
                .collect(Collectors.toList())
                : List.of();
        return new PublicUserWithTeamsRequest(
                user.getId(),
                user.getUsername(),
                user.getProfilePictureUrl(),
                user.getBio(),
                equipos
        );
    }

    public void updateBio(Long id, String bio) {
        User currentUser = currentUserProvider.getCurrentUser();
        if (currentUser == null || !currentUser.getId().equals(id)) {
            throw new RuntimeException("Usuario no autenticado o no autorizado");
        }
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        user.setBio(bio);
        userRepository.save(user);
    }
}
