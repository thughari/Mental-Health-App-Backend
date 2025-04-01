package com.hack4job.apigateway.util;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

@Component
@Slf4j
public class JWTUtil {


    private String secretkey = "a83a21574ff88ebdabdf091fef42c894bfae0281f898c84bc5d820c710571aed9770636464dfedb0cb56c9b69a67c5b29cdc8c89ba0bbb523d1dc9be85995a6c7462cf9ad19078aec628e9e85ee10f6847ae9592a9d470adbba58a5abcf6d484e830f72bd8b6444d1b4a2866e1a3a81e52d20a4c12c4e9cddc08ed7ac8f09bd8c93027a246566f75cc27098fdae652cffb224f87862a7fab5bd7f84f45b1f755fd53eef4036825616df341fd1d6cbae1c3b1083094d3fbc78a925f51aa043731d8122e40f071782eeae970736d42f9998153e9f2510d0f681c71a1b7a0ef56123df69225f4862d84f5f0d4d9876d85319518d89243deb7f2582317ba205f0808";
    
    private final Set<String> revokedTokens = new HashSet<>();
//    public JWTUtil() {
//
//        try {
//            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
//            SecretKey sk = keyGen.generateKey();
//            secretkey = Base64.getEncoder().encodeToString(sk.getEncoded());
//        } catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException(e);
//        }
//    }
    
    public void validateToken(String token) {
    	if(!isTokenRevoked(token)) {
    		Jws<Claims> response = Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(token);
    		log.info(response.getPayload().getSubject());
    	} else {
    		log.info("toke access is revoked! for "+extractUserName(token));
    	}
    }

    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretkey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUserName(String token) {
        // extract the username from jwt token
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

  
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    
    public void revokeToken(String token) {
        revokedTokens.add(token);
    }

    private boolean isTokenRevoked(String token) {
        return revokedTokens.contains(token);
    }

}