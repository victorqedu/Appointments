package com.caido.appointments.Util;

import com.caido.appointments.config.SecurityConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.SecretKey;

public class JWT {
    public static String getClaimByNameFromToken(String token, String claimName) {
        Claims claims;
        try {
            SecretKey secret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SecurityConstants.JWT_KEY));
            claims = Jwts.parserBuilder().setSigningKey(secret).build().parseClaimsJws(token).getBody();
        } catch (Exception ex) {
            Logger.getLogger(JWT.class.getName()).log(Level.SEVERE, null, ex);
            claims = null;
        }
        return claims.get(claimName).toString();
    }    
}
