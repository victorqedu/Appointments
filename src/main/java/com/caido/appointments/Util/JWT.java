package com.caido.appointments.Util;

import com.caido.appointments.config.SecurityConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.SecretKey;

public class JWT {
    public static String getClaimByNameFromToken(String token, String claimName) {
        System.out.println("getClaimByNameFromToken for token "+token+" and claim "+claimName);
        Claims claims;
        try {
            SecretKey secret = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));
            claims = Jwts.parserBuilder().setSigningKey(secret).build().parseClaimsJws(token).getBody();
            System.out.println("getClaimByNameFromToken for token "+token+" and claim "+claimName+"  val is "+claims.get(claimName).toString());
            return claims.get(claimName).toString();
        } catch (Exception ex) {
            Logger.getLogger(JWT.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }    
}
