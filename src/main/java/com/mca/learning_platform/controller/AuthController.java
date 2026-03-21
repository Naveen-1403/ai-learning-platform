package com.mca.learning_platform.controller;

import com.mca.learning_platform.model.User;
import com.mca.learning_platform.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // புதிய மாணவர்களை பதிவு செய்யும் API
    @PostMapping("/api/auth/register")
    public String registerUser(@RequestParam String username, @RequestParam String password) {
        // ஏற்கனவே இந்த பெயரில் யாராவது இருக்கிறார்களா என்று செக் செய்கிறோம்
        if (userRepository.findByUsername(username) != null) {
            return "redirect:/register.html?error=exists";
        }
        
        // புதிய யூசரை உருவாக்கி டேட்டாபேஸில் சேமிக்கிறோம்
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password)); // பாஸ்வர்டை பாதுகாப்பாக மாற்றுகிறோம்
        userRepository.save(user);
        
        // வெற்றிகரமாக முடிந்ததும் லாகின் பக்கத்திற்கு அனுப்புகிறோம்
        return "redirect:/login.html?success=registered";
    }
}