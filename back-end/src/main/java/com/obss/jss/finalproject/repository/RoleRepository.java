package com.obss.jss.finalproject.repository;

import org.springframework.stereotype.Repository;
import com.obss.jss.finalproject.model.Role;
import com.obss.jss.finalproject.model.RoleType;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface RoleRepository extends JpaRepository <Role, Long>
{
	Optional<Role> findByName(RoleType name);
}
