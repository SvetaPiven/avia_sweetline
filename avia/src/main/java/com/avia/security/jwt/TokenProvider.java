package com.avia.security.jwt;

import com.avia.security.config.JwtConfiguration;

import com.avia.security.provider.UserDetailsProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.jsonwebtoken.Claims.SUBJECT;
import static java.util.Calendar.MILLISECOND;

@Component
@RequiredArgsConstructor
public class TokenProvider {

    private final JwtConfiguration jwtConfiguration;
    private final UserDetailsProvider userDetailsProvider;

    public static final SignatureAlgorithm ALGORITHM = SignatureAlgorithm.HS512;

    public static final String CREATE_VALUE = "created";

    //    public String generateToken(UserDetails userDetails) {
//        Date now = new Date();
//        Date expiryDate = new Date(now.getTime() + jwtConfiguration.getExpiration() * 1000);
//
//
//        return Jwts.builder()
//                .setSubject(userDetails.getUsername())
//                .setIssuedAt(now)
//                .setExpiration(expiryDate)
//                .signWith(SignatureAlgorithm.HS256, jwtConfiguration.getSecret())
//                .compact();
//    }
    private String generateToken(Map<String, Object> claims) {

        return Jwts
                .builder()
                .setHeader(generateJWTHeaders())
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(ALGORITHM, "secret")
                .compact();
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(SUBJECT, userDetails.getUsername());
        claims.put(CREATE_VALUE, generateCurrentDate());
        claims.put("ROLES", getEncryptedRoles(userDetails));
        return generateToken(claims);
    }

    private List<String> getEncryptedRoles(UserDetails userDetails) {
        return userDetails.getAuthorities().
                stream()
                .map(GrantedAuthority::getAuthority)
              //  .map(s -> s.replace("ROLE_", ""))
                .map(String::toLowerCase).toList();
    }

    private Map<String, Object> generateJWTHeaders() {
        Map<String, Object> jwtHeaders = new LinkedHashMap<>();
        jwtHeaders.put("typ", "JWT");
        jwtHeaders.put("alg", ALGORITHM.getValue());

        return jwtHeaders;
    }

    private Date generateExpirationDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(MILLISECOND,60000000);
        return calendar.getTime();
    }


    public String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
//        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
//            return bearerToken.substring(7);
//        }
        return bearerToken;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtConfiguration.getSecret()).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

//    public boolean validateToken(String token, UserDetails userDetails) {
//        final String username = getUsernameFromToken(token);
//        return username.equals(userDetails.getUsername());
//    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimsFromToken(token).getExpiration();
    }

    private Claims getClaimsFromToken(String token) {
        return Jwts
                .parser()
                .setSigningKey(jwtConfiguration.getSecret())
                .parseClaimsJws(token)
                .getBody();
    }

    public Boolean isTokenExpired(String token) {
        final Date expiration = this.getExpirationDateFromToken(token);
        return expiration.before(this.generateCurrentDate());
    }

    private Date generateCurrentDate() {
        return new Date();
    }

    public String getUsernameFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getSubject();
    }

//    public Date getExpirationDateFromToken(String token) {
//        Claims claims = getClaimsFromToken(token);
//        return claims.getExpiration();
//    }

//    private boolean isTokenExpired(String token) {
//        Date expiration = getExpirationDateFromToken(token);
//        return expiration.before(new Date());
//    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtConfiguration.getSecret()).parseClaimsJws(token).getBody();
        String username = claims.getSubject();
        UserDetails userDetails = userDetailsProvider.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
}