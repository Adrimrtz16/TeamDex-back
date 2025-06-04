package service;

import dto.AuthLoginRequest;
import dto.AuthRequest;
import dto.AuthResponse;
import dto.TeamRequest;
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

    private final UserRepository userRepository; //llamo a donde se almacenan los usuarios
    private final PasswordEncoder passwordEncoder; //llamo al codificador de contraseñas
    private final JwtUtil jwtUtil; //llamo a jwt
    private final CurrentUserProvider currentUserProvider;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, CurrentUserProvider currentUserProvider ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.currentUserProvider = currentUserProvider;
    }

    public ResponseEntity<AuthResponse> login(AuthLoginRequest request) { //le paso por parametro lo que me envian por post
        Optional<User> user = userRepository.findByUsername(request.getUsername()); //cojo el nombre que me han pasado por parametro y uso la funcion para buscarlo
        if (user.isPresent() && passwordEncoder.matches(request.getPassword(),(user.get().getPassword())) ) { //si el usuario existe y la contraseña que me han pasado por parametro coincide con la del usuario
            String token = jwtUtil.generateToken(user.get()); //me genera un token y me lo manda por respuesta

            return ResponseEntity.ok(new AuthResponse(token));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AuthResponse("Credenciales incorrectas")); //si no coincide, me devuelve un error 401 Unauthorized
    }//y me dice que las credenciales son incorrectas

    public void register(AuthRequest request) { //le paso por parametro los datos
        if (userRepository.findByUsername(request.getUsername()).isPresent()) { //busco el nombre para ver si ya existe el usuario
            throw new RuntimeException("El usuario ya existe"); //si existe salta un error
        }
        User user = new User(); //sino crea un nuevo usuario y asigna los datos
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setProfilePictureUrl(request.getProfilePictureUrl());

        Role role = (request.getRole() == null || request.getRole().describeConstable().isEmpty()) ? Role.USER : request.getRole();
        user.setRole(role);

        userRepository.save(user);
    }

    public List<AuthRequest> getAllUsers() {
        User currentUser = currentUserProvider.getCurrentUser();
        if (currentUser == null) {
            throw new RuntimeException("No estás autenticado");
        }
        if (currentUser.getRole() == Role.ADMIN) {
            return userRepository.findAll().stream().map(user ->
                    new AuthRequest(
                            user.getId(),
                            user.getUsername(),
                            user.getPassword(),
                            user.getEmail(),
                            user.getRole(),
                            user.getProfilePictureUrl(),
                            user.getBio(),
                            user.getTeams() != null ? user.getTeams().stream().map(team ->
                                    new TeamRequest(
                                            team.getId(),
                                            team.getPokemon1(),
                                            team.getPokemon2(),
                                            team.getPokemon3(),
                                            team.getPokemon4(),
                                            team.getPokemon5(),
                                            team.getPokemon6(),
                                            team.getName(),
                                            team.getUser().getId()
                                    )
                            ).collect(Collectors.toList()) : List.of()
                    )
            ).collect(Collectors.toList());
        } else if (currentUser.getRole() == Role.USER && !currentUser.getUsername().equals("anonymous")) {
            return userRepository.findByUsername(currentUser.getUsername()).stream().map(user ->
                    new AuthRequest(
                            user.getId(),
                            user.getUsername(),
                            user.getPassword(),
                            user.getEmail(),
                            user.getRole(),
                            user.getProfilePictureUrl(),
                            user.getBio(),
                            user.getTeams() != null ? user.getTeams().stream().map(team ->
                                    new TeamRequest(
                                            team.getId(),
                                            team.getPokemon1(),
                                            team.getPokemon2(),
                                            team.getPokemon3(),
                                            team.getPokemon4(),
                                            team.getPokemon5(),
                                            team.getPokemon6(),
                                            team.getName(),
                                            team.getUser().getId()
                                    )
                            ).collect(Collectors.toList()) : List.of()
                    )
            ).collect(Collectors.toList());
        } else {
            throw new RuntimeException("No tienes autorización para mostrar los usuarios");
        }
    }

    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("usuario no encontrado"));
        User currentUser = currentUserProvider.getCurrentUser();
        if (currentUser == null) {
            throw new RuntimeException("No estás autenticado");
        }
        if (currentUser.getRole() == Role.ADMIN) {
            userRepository.delete(user);
        } else {
            throw new RuntimeException("No tienes autorización para borrar este usuario");
        }
    }

    public User updateUser(Long userId, AuthRequest authRequest) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("No existe un usuario con ese id"));
        User currentUser = currentUserProvider.getCurrentUser();
        if (currentUser == null) {
            throw new RuntimeException("No estás autenticado");
        }

        if (existingUser.getId().equals(currentUser.getId()) || currentUser.getRole() == Role.ADMIN) {

            existingUser.setUsername(authRequest.getUsername());
            if (authRequest.getPassword() != null && !authRequest.getPassword().isEmpty()) {
                existingUser.setPassword(passwordEncoder.encode(authRequest.getPassword()));
            }
            existingUser.setEmail(authRequest.getEmail());

            if (currentUser.getRole() == Role.ADMIN) {
                existingUser.setRole(authRequest.getRole());
            }

            return userRepository.save(existingUser);
        }
        throw new RuntimeException("No tienes autorización para actualizar este usuario");
    }

    /*public void sendPasswordResetToken(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("No existe usuario con ese email"));

        String token = UUID.randomUUID().toString();
        Date expiration = new Date(System.currentTimeMillis() + 1000 * 60 * 30); // 30 min

        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setToken(token);
        resetToken.setUser(user);
        resetToken.setExpirationDate(expiration);
        passwordResetTokenRepository.save(resetToken);

        String link = "https://revoluxburger-frontend.vercel.app/reset-password?token=" + token; //cambia esto por tu URL real
        String body = "Hola " + user.getUsername() + ",\n\n" +
                "Para restablecer tu contraseña, haz clic en el siguiente enlace:\n" + link + "\n\n" +
                "Este enlace expirará en 30 minutos.";

        emailService.sendEmail(user.getEmail(), "Restablecer contraseña", body);
    }

    public void resetPassword(String token, String nuevaPassword) {
        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Token inválido"));

        if (resetToken.isExpired()) {
            throw new RuntimeException("El token ha expirado");
        }

        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(nuevaPassword));
        userRepository.save(user);

        passwordResetTokenRepository.delete(resetToken); // borra token usado
    }
    */

}
