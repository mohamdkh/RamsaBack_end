package ramsa.sid.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ramsa.sid.entities.SecteurHydraulique;

@Repository

public interface SecteurHydroRepository extends JpaRepository<SecteurHydraulique, Long>{

	@Query(value ="SELECT DISTINCT Etage from SecteurHydraulique")
	public List<String> ListEtages();
	@Query(value ="SELECT s from SecteurHydraulique s where s.Etage Like:etage")
	public List<SecteurHydraulique> ListSecteursHydrauliques(@Param("etage") String etage);
	@Query(value ="SELECT DISTINCT id from SecteurHydraulique  where ( Etage Like:etage AND Nom Like:nomSecteur)")
	public List<Long> ListIDSecteurHydraulique(@Param("etage") String etage,@Param("nomSecteur") String nomSecteur);
	@Transactional
	 @Modifying
	  @Query(value ="UPDATE  SecteurHydraulique s Set s.dateDesactivation=:dateDesactivation  where  s.Nom Like:secteur")
	public void UpdateStatusHydro( @Param("secteur")  String secteur,@Param("dateDesactivation")  Date date);
}
