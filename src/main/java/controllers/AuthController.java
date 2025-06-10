package controllers;

import dto.AuthLoginRequest;
import dto.AuthRequest;
import dto.AuthResponse;
import dto.PublicUserRequest;
import dto.PublicUserWithTeamsRequest;
import dto.BioRequest;
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

    @GetMapping("/public")
    @CrossOrigin(origins = {"http://localhost:5173", "https://team-dex-front.vercel.app"})
    public List<PublicUserRequest> getFirst20PublicUsers() {
        return authService.getFirst20PublicUsers();
    }

    @GetMapping("/userWithTeams/{id}")
    @CrossOrigin(origins = {"http://localhost:5173", "https://team-dex-front.vercel.app"})
    public PublicUserWithTeamsRequest getUserWithTeamsById(@PathVariable Long id) {
        return (PublicUserWithTeamsRequest) authService.getUserWithTeamsById(id);
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

    @GetMapping("/me")
    @CrossOrigin(origins = {"http://localhost:5173", "https://team-dex-front.vercel.app"})
    public Object getMe() {
        return authService.getMe();
    }

    @PutMapping("/update/bio/{id}")
    @CrossOrigin(origins = {"http://localhost:5173", "https://team-dex-front.vercel.app"})
    public Object updateBio(@PathVariable Long id, @RequestBody BioRequest request) {
        authService.updateBio(id, request.getBio());
        Map<String, String> response = new HashMap<>();
        response.put("message", "Biografía actualizada correctamente");
        return response;
    }

}
