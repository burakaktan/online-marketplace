package com.obss.jss.finalproject.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.obss.jss.finalproject.model.User;
import com.obss.jss.finalproject.security.MyUserDetails;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
  private UserService userService;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    final User user = userService.findByUsername(username);

    return MyUserDetails.build(user);
  }
}
