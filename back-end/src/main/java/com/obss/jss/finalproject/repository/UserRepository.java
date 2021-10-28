package com.obss.jss.finalproject.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.obss.jss.finalproject.model.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long>
{
	@Query(value="from User u")
	List<User> findAllUsers();

	Boolean existsByUsername(String username);

	User findByUsername(String username);

	Page<User> findByUsernameLike(String username, Pageable pageable);
}
