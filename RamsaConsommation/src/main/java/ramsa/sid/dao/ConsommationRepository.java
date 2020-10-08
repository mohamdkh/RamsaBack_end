package ramsa.sid.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ramsa.sid.entities.ConsommationSectorielle;

@Repository

public interface ConsommationRepository extends JpaRepository<ConsommationSectorielle, Long>{
	@Query(value ="SELECT s from ConsommationSectorielle s where (s.SecteursCom LIKE:secteurCom AND s.annee=:annee AND s.type Like:type AND s.periode Like:periode)")
	  public List<ConsommationSectorielle> ListConsommationparType(@Param("secteurCom") String secteurCom,@Param("type") String type,
			  														@Param("periode") String periode,@Param("annee") int annee);
	@Query(value ="SELECT s from ConsommationSectorielle s where s.SecteursCom LIKE:secteurCom")
	  public List<ConsommationSectorielle> ListConsommation(@Param("secteurCom") String secteurCom);
	@Query(value ="select DISTINCT annee  from ConsommationSectorielle ORDER BY annee ")
	public List<String> listAnnee();
	@Query(value ="SELECT sum(TotalConsom)*	:taux FROM ConsommationSectorielle where annee=:annee AND periode like:periode And type=:type And SecteursCom Like:secteurCom ")
	public double SumOfConsumptionSecteur(@Param("secteurCom") String secteurCom,@Param("periode")  String periode,@Param("annee")  int annee,
									@Param("type")  String type,@Param("taux")  double tauxAffectation);
	@Query(value ="SELECT sum(NbrConsom) FROM ConsommationSectorielle where annee=:annee AND periode like:periode And type=:type And SecteursCom Like:secteurCom ")
	public double SumOfClients(@Param("secteurCom") String secteurCom,@Param("periode")  String periode,@Param("annee")  int annee,
									@Param("type")  String type);

}
