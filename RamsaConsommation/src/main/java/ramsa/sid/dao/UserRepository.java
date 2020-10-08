package ramsa.sid.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ramsa.sid.entities.LoginModel;

@Repository
public interface UserRepository extends JpaRepository<LoginModel, Integer> {
	@Query(value ="SELECT s from LoginModel s where  s.username Like :username")
		public LoginModel FindByusername(@Param("username") String username);
}
