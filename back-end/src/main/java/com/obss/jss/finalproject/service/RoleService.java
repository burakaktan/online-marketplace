package com.obss.jss.finalproject.service;

import com.obss.jss.finalproject.model.Role;
import com.obss.jss.finalproject.model.RoleType;

public interface RoleService
{
	Role findByName(RoleType name);
}
