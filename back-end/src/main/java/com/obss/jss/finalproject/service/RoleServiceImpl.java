package com.obss.jss.finalproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.obss.jss.finalproject.exception.RoleNotFoundException;
import com.obss.jss.finalproject.model.Role;
import com.obss.jss.finalproject.model.RoleType;
import com.obss.jss.finalproject.repository.RoleRepository;

@Service
public class RoleServiceImpl implements RoleService
{
	@Autowired
	RoleRepository roleRepository;
	
	@Override
	public Role findByName(RoleType name) {
		return roleRepository.findByName(name).orElseThrow(RoleNotFoundException::new);
	}

}
