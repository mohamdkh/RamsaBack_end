package ramsa.sid.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ramsa.sid.entities.Rendement;

@Repository
public interface RendementRepository extends JpaRepository<Rendement, Integer>{
	@Query(value ="SELECT s from Rendement s where ( s.idSecteurHydrau =:id AND s.annee=:annee AND periode=:periode)")
	List<Rendement> ExistList(@Param("id") Long id_etage,@Param("annee") int annee,@Param("periode") String periode);
}
