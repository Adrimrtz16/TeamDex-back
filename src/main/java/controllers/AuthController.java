package controllers;

import dto.AuthLoginRequest;
import dto.AuthRequest;
import dto.AuthResponse;
import entity.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import service.AuthService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    @CrossOrigin(origins = {"http://localhost:5173", "https://team-dex-front.vercel.app"})
    public ResponseEntity<AuthResponse> login(@RequestBody AuthLoginRequest request) {
        System.out.println("Login request received: " + request);

        return authService.login(request);
    }
    @PostMapping("/register")
    @CrossOrigin(origins = {"http://localhost:5173", "https://team-dex-front.vercel.app"})
    public ResponseEntity<Map<String,String>> register(@RequestBody AuthRequest request) {
        authService.register(request);
        Map<String,String> body = new HashMap<>();
        body.put("message", "Usuario registrado correctamente");
        return ResponseEntity.ok(body);
    }

    @GetMapping("/{id}")
    @CrossOrigin(origins = {"http://localhost:5173", "https://team-dex-front.vercel.app"})
    public Object getUserById(@PathVariable Long id) {
        return authService.getUserById(id);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody AuthRequest authRequest) {
        return authService.updateUser(id, authRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        authService.deleteUser(id);
    }

    /*@PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        authService.sendPasswordResetToken(request.getEmail());
        return ResponseEntity.ok("Correo de recuperación enviado");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequest request) {
        authService.resetPassword(request.getToken(), request.getNewPassword());
        return ResponseEntity.ok("Contraseña actualizada correctamente");
    }*/


}
