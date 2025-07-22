package com.crm.user;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	boolean existsByEmail(String email);

	User findByEmail(String email);

	List<User> findUsersByRole(String string);

	List<User> findByRole(String string);

	@Query("SELECT u FROM User u WHERE LOWER(u.role) = LOWER(:role) ORDER BY u.createdOn DESC")
	Page<User> findByRoleOrderByCreatedOnDesc(String role, Pageable pageable);

	@Query("SELECT u FROM User u WHERE LOWER(u.role) = LOWER(:role) AND u.userId = :id ORDER BY u.createdOn DESC")
	List<User> findByRoleAndUserIdOrderByCreatedOnDesc(String role, @Param("id") long id);

	User findSalesById(Long long1);

	Page<User> findByRoleAndUserIdOrderByCreatedOnDesc(String role, long id, Pageable pageable);

	@Query("SELECT u FROM User u WHERE LOWER(u.role) = LOWER(:role) AND u.id = :id")
	List<User> findByRoleWhereUserId(@Param("role") String role, @Param("id") long id);

	@Query("SELECT COUNT(u) FROM User u WHERE u.role = :role AND u.userId = :id")
	long findByRoleAndUserId(String role, @Param("id") long id);

	@Query("SELECT COUNT(u) FROM User u WHERE u.role = :role")
	long findCountByRole(String role);
	
	@Query("SELECT u FROM User u WHERE u.userId = :id")
	List<User> findUsersByUserId(@Param("id") long id);

	@Query("SELECT u FROM User u WHERE LOWER(u.role) = LOWER(:role) AND u.userId = :id")
	List<User> getByRoleAndUserId(@Param("role") String role, @Param("id") long id);
}
