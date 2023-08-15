package hu.webuni.orderservice.securityconfig;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SecurityService {

    private static final Logger logger = LoggerFactory.getLogger(SecurityService.class);
    private static final String BEARER = "Bearer ";
    private final JwtTokenService jwtTokenService;
    @Value("${redis.user.postfix}")
    private String redisUserPostfix;
    @Value("${redis.user.refresh.postfix}")
    private String redisUserRefreshPostfix;


    public UsernamePasswordAuthenticationToken validateAccessToken(String token) {
        token = tokenBearerRemover(token);
        if (token == null)
            return null;
        UserDetails principal = jwtTokenService.parseJwt(token);
        jwtTokenService.validateToken(token, principal, redisUserPostfix);

        return new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
    }

    private String tokenBearerRemover(String token) {
        logger.info("Token bearer remover on: {}",token);
        if (token != null && token.startsWith(BEARER)) {
            return token.substring(BEARER.length());
        } else {
            return null;
        }
    }

}
