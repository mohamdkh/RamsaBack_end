package ramsa.sid.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ramsa.sid.entities.TypeRelevee;

@Repository
public interface TypeReleveeRepository extends JpaRepository<TypeRelevee,Integer>{
	

}
