package sistema_alertas.Alertas.util;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();

        // ⚠️ Evitar interceptar el login
        if (
    path.contains("/api/usuarios/login") ||
    path.contains("/api/usuarios/generar") ||
    path.contains("/api/sugerencias")
) {
    filterChain.doFilter(request, response);
    return;
}

        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);

            try {
                Claims claims = jwtUtil.extraerClaims(token);
                String cedula = claims.getSubject();
                Integer rol = claims.get("rol", Integer.class);

                String nombreRol = switch (rol) {
                    case 0 -> "ROLE_DOCENTE";
                    case 1 -> "ROLE_ESTUDIANTE";
                    case 2 -> "ROLE_PSICO";
                    case 3 -> "ROLE_ADMIN";
                    default -> "ROLE_INVITADO";
                };

                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        new org.springframework.security.core.userdetails.User(
                                cedula, "", Collections.singletonList(new SimpleGrantedAuthority(nombreRol))),
                        null,
                        Collections.singletonList(new SimpleGrantedAuthority(nombreRol)));

                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
