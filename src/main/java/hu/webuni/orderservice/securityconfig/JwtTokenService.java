package hu.webuni.orderservice.securityconfig;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class JwtTokenService {

    private final RedisService redisService;
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenService.class);
    private static final String AUTH = "auth";
    private String issuer = "WebshopApp";
    @Value("${redis.user.postfix}")
    private String redisUserPostfix;
    @Value("${redis.user.refresh.postfix}")
    private String redisUserRefreshPostfix;
    @Value("${jwt.secret}")
    private String secret;
    private Algorithm signerAlg;
    private Algorithm validatorAlg;

    @Value("${hu.webuni.tokenlib.keypaths.private:#{null}}")
    private String pathToPemWithPrivateKey;
    @Value("${hu.webuni.tokenlib.keypaths.public:#{null}}")
    private String pathToPemWithPublicKey;

    @PostConstruct
    public void init() throws Exception {
        if(pathToPemWithPrivateKey != null) {
            signerAlg = Algorithm.ECDSA512(null, (ECPrivateKey) PemUtils.getPrivateKey(pathToPemWithPrivateKey));
        }

        if(pathToPemWithPublicKey != null) {
            validatorAlg = Algorithm.ECDSA512((ECPublicKey) PemUtils.getPublicKey(pathToPemWithPublicKey), null);
        }
    }

    public UserDetails parseJwt(String jwtToken) {

        DecodedJWT decodedJwt = JWT.require(validatorAlg)
                .withIssuer(issuer)
                .build()
                .verify(jwtToken);
        return new User(decodedJwt.getSubject(), "dummy",
                decodedJwt.getClaim(AUTH).asList(String.class)
                        .stream().map(SimpleGrantedAuthority::new).toList());

    }

    public DecodedJWT getAllClaimsFromToken(String token) {
        return JWT.require(validatorAlg)
                .withIssuer(issuer)
                .build()
                .verify(token);
    }

    public void validateToken(String token, UserDetails userDetails, String keySuffix) {
        logger.info("Getting all claims from token.");
        final String username = userDetails.getUsername();
        Map<String, Claim> claims = getAllClaimsFromToken(token).getClaims();
        String redisKey = username + keySuffix;
        String redisResponse = redisService.getValueFromRedis(redisKey);
        List<String> errors = new ArrayList<>();

        if ((redisResponse == null) || (redisResponse.isEmpty())) {
            errors.add("Token doesn't exist in REDIS");
        }
        if (!claims.get("iss").asString().equals(issuer)) {
            errors.add("Issuer is not matched");
        }
        if (!claims.get("jti").asString().equals(redisResponse)) {
            errors.add("Token is not matched");
        }
        if (!claims.get("exp").asDate().after((new Date()))) {
            errors.add("Token is expired");
        }
        if (!errors.isEmpty()) {
            throw new BadCredentialsException(
                    "Error during validating token {%s}".formatted(errors.toString())
            );
        }
    }

}
