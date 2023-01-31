package com.app_products.app_products.Security.JWT;

import com.app_products.app_products.Security.Models.UserMain;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.boot.jdbc.UnsupportedDataSourcePropertyException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.SignatureException;
import java.util.Date;

@Component
public class JwtProvider {

    private final static Logger LOGGER = LoggerFactory.getLogger(JwtProvider.class);

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private int expiration;

    public String generateToken(Authentication authentication){
        UserMain userMain = (UserMain) authentication.getPrincipal();
        return Jwts.builder().setSubject(userMain.getUsername()).setIssuedAt(new Date()).setExpiration(new Date(new Date().getTime() + expiration*1000)).signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    public String getUsernameByToken(String token){
        return Jwts.parser().setSigningKey(secret).parseClaimsJwt(token).getBody().getSubject();
    }

    public boolean validateToken(String token){
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJwt(token);
            return true;
        } catch (MalformedJwtException e){
            LOGGER.error("Token is malformed.");
        } catch (UnsupportedJwtException e){
            LOGGER.error("Token is unsupported.");
        } catch (ExpiredJwtException e){
            LOGGER.error("Token is expired.");
        } catch (IllegalArgumentException e){
            LOGGER.error("Token is empty.");
        } catch (Exception e){
            LOGGER.error("An error has occurred: " + e.getMessage());
        }
        return false;
    }
}
