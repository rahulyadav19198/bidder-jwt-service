package BiddingJWT.JWT.service;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class BidderAuthClient {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String BIDDER_LOGIN_URL = "http://localhost:8080/auth/login";

    public Map validateUser(String username, String password) {

        Map<String, String> request = Map.of(
                "username", username,
                "password", password
        );

        try {
            ResponseEntity<Map> response =
                    restTemplate.postForEntity(BIDDER_LOGIN_URL, request, Map.class);

            return response.getBody(); // contains username, role, id

        } catch (Exception e) {
            return null;
        }
    }
}
