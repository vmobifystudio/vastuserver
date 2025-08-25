package com.vishwakarma.vastu.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.vishwakarma.vastu.model.User;

public interface UserRepository extends JpaRepository<User, Long>,JpaSpecificationExecutor<User> {
	
	//User findByUsername(String username);
	User findByEmail(String email);
	
	@Query("select distinct u from User u INNER JOIN u.userRoles role where role.id = :roleId order by u.id asc")
	List<User> findByRole(@Param("roleId") Long roleId);
	
	@Query("select distinct u from User u INNER JOIN u.userRoles role where role.id in :roleIds")
	List<User> findByRoles (@Param("roleIds") List<Long> roleIds, Pageable p);

	@Query("select distinct u from User u INNER JOIN u.userRoles role where role.id not in :roleIds")
	List<User> findByRolesNotIn (@Param("roleIds") List<Long> roleIds, Pageable p);
	
	@Query("select distinct u from User u INNER JOIN u.userRoles role where role.id in :roleIds order by u.id asc")
	List<User> findByRoles (@Param("roleIds") List<Long> roleIds);
	
	
}

