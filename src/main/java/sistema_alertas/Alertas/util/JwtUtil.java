package sistema_alertas.Alertas.util;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    private final long EXPIRATION_TIME = 1000 * 60 * 60 * 5; 

    public String generarToken(String cedula, int rol, Long id) {
        return Jwts.builder()
                .setSubject(cedula)
                .claim("rol", rol)
                .claim("id", id)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    public Claims extraerClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validarToken(String token, String cedula) {
        try {
            String cedulaExtraida = extraerClaims(token).getSubject();
            return cedulaExtraida.equals(cedula) && !estaExpirado(token);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean estaExpirado(String token) {
        return extraerClaims(token).getExpiration().before(new Date());
    }

    public String extraerCedula(String token) {
        return extraerClaims(token).getSubject();
    }
}