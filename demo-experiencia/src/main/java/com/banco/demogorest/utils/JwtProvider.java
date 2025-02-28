package com.banco.demogorest.utils;

import com.banco.demogorest.persistence.entity.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
@Slf4j
public class JwtProvider {

    @Value("${jwt.secret-key}")
    private String secret;

    @Value("${jwt.expiration}")
    private Integer expiration;

    public String generateToken(User user) {
        Date issuedAt = new Date(System.currentTimeMillis());
        Date expirationDate = new Date(System.currentTimeMillis() + (expiration * 1000L));

        return Jwts.builder()
                .subject(user.getUsername())
                .claim("id", user.getId())
                .signWith(secretKey(), Jwts.SIG.HS256)
                .issuedAt(issuedAt)
                .expiration(expirationDate)
                .compact();
    }

    public String getSubject(String token) {
        return Jwts.parser()
                .verifyWith(secretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validate(String token) {
        try {
            getSubject(token);

            return true;
        } catch (ExpiredJwtException e) {
            log.warn("token expired");
        } catch (UnsupportedJwtException e) {
            log.error("token unsupported");
        } catch (MalformedJwtException e) {
            log.error("token malformed");
        } catch (SignatureException e) {
            log.error("bad signature");
        } catch (IllegalArgumentException e) {
            log.error("illegal args");
        }
        return false;
    }

    private SecretKey secretKey() {
        byte[] secretBytes = this.secret.getBytes();
        return Keys.hmacShaKeyFor(secretBytes);
    }

}
