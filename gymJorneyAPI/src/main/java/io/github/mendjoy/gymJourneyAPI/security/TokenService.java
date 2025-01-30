package io.github.mendjoy.gymJourneyAPI.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import io.github.mendjoy.gymJourneyAPI.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secretKey;

    private Instant getExpirationTime(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-3"));
    }

    private String getIssuer(){
        return "gymJourneyAPI";
    }
    public String generateToken(User user){

        try {

            Algorithm algorithm = Algorithm.HMAC256(secretKey);

            return JWT.create()
                      .withIssuer(getIssuer())
                      .withSubject(user.getEmail())
                      .withExpiresAt(getExpirationTime())
                      .sign(algorithm);

        }catch (JWTCreationException exception){
            throw new RuntimeException("Erro durante a autenticação");
        }
    }

    public String validateToken(String token){

        try {

            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            return JWT.require(algorithm)
                      .withIssuer(getIssuer())
                      .build()
                      .verify(token)
                      .getSubject();

        }catch (JWTVerificationException exception){
            return null;
        }
    }
}
