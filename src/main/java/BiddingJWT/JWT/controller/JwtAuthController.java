package BiddingJWT.JWT.controller;
import BiddingJWT.JWT.config.JwtUtllity;
import BiddingJWT.JWT.service.BidderAuthClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin("*")
@RestController
@RequestMapping("/auth")
public class JwtAuthController {

    private final BidderAuthClient bidderAuthClient;
    private final JwtUtllity jwtUtllity;

    public JwtAuthController(BidderAuthClient bidderAuthClient,
                             JwtUtllity jwtUtllity) {
        this.bidderAuthClient = bidderAuthClient;
        this.jwtUtllity = jwtUtllity;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String,String> req){

        Map user = bidderAuthClient.validateUser(
                req.get("username"),
                req.get("password")
        );

        if(user == null){
            return ResponseEntity.status(401).body("Invalid credentials");
        }

        String token = jwtUtllity.generateToken(
                (String) user.get("username"),
                (String) user.get("role"),
                (Integer) user.get("id")
        );

        return ResponseEntity.ok(Map.of(
                "token", token,
                "user", user
        ));
    }
}
