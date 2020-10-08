package ramsa.sid.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ramsa.sid.entities.SecteurCommercial;

@Repository

public interface SecteurComRepository extends JpaRepository<SecteurCommercial, Long> {
	@Query(value ="SELECT s from SecteurCommercial s where s.id_etage =:id and s.status Like 'active'")
  public List<SecteurCommercial> ListSecteurComPerHydro(@Param("id") Long id);
	@Query(value ="SELECT s from SecteurCommercial s where s.NomSecteurCom like:secteurCom")
	  public List<SecteurCommercial> ListSecteurCom(@Param("secteurCom") String secteurCom);
	@Query(value ="SELECT DISTINCT NomSecteurCom from SecteurCommercial  where status like:status")
	  public List<String> ListSecteurPerStatus(@Param("status") String status);
	
	@Transactional
	 @Modifying
	  @Query(value ="UPDATE  SecteurCommercial s Set s.dateDesactivation=:dateDesactivation  where  s.NomSecteurCom Like:secteur")
	public void DesactiveSecteur( @Param("secteur")  String secteur,@Param("dateDesactivation")  Date date);
	@Transactional
	 @Modifying
	  @Query(value ="UPDATE  SecteurCommercial s Set s.status=:status  where  s.status='underway' AND s.NomSecteurCom Like:secteur")
	public void UpdateStatusCommercial( @Param("secteur")  String secteur,@Param("status") String status);
	@Transactional
	 @Modifying
	  @Query(value ="DELETE  FROM SecteurCommercial s where  (s.NomSecteurCom Like:secteur AND s.status like 'unfinished')")
	public void DeleteIntialSecteurCom( @Param("secteur")  String secteur);
		
}
