package BiddingJWT.JWT.utility;


import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class Utility {

    public static String JWT_HEADER = "Authorization";

    public static String PUBLIC_API = "/login/**";

    public static String getUserFromAuthContext()
    {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }

}
