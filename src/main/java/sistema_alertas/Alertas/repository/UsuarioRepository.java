package sistema_alertas.Alertas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sistema_alertas.Alertas.model.Usuario;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    boolean existsByCedula(String cedula);
    Optional<Usuario> findByCedula(String cedula); 
    
}
