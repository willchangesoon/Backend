package com.es3.gateway.security;

import com.es3.gateway.domain.common.Role;
import com.es3.gateway.exception.ErrorCode;
import com.es3.gateway.exception.TokenException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtProvider {

    @Value("${jwt.secret-key}")
    private String SECRET_KEY;

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    //validate token
    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token);
        }catch (ExpiredJwtException e) {
            log.error("Token expired", e);
            throw new TokenException(ErrorCode.EXPIRED_TOKEN);
        } catch (UnsupportedJwtException | MalformedJwtException e) {
            log.error("Invalid JWT - JWT Exception", e);
        } catch (SecurityException | IllegalArgumentException e) {
            log.error("Invalid JWT - Runtime Exception", e);
        }
        return true;
    }

    //claims
    public Claims getClaims(String token) {
        return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload();
    }

    //userInfo
    public String getUserId(String token) {
        return getClaims(token).getSubject();
    }

    public String getEmail(String token) {
        return (String) getClaims(token).get("email");
    }

    public Role getRoles(String token) {
        return Role.valueOf((String) getClaims(token).get("roles"));
    }
}
