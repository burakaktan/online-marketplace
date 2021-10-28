package com.obss.jss.finalproject.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.obss.jss.finalproject.dto.JwtResponse;
import com.obss.jss.finalproject.dto.LoginRequest;
import com.obss.jss.finalproject.dto.MessageResponse;
import com.obss.jss.finalproject.dto.SignupRequest;
import com.obss.jss.finalproject.model.Role;
import com.obss.jss.finalproject.model.RoleType;
import com.obss.jss.finalproject.model.User;
import com.obss.jss.finalproject.security.JwtUtils;
import com.obss.jss.finalproject.security.MyUserDetails;
import com.obss.jss.finalproject.service.RoleService;
import com.obss.jss.finalproject.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController
{
	//eski hali
//	 private final AuthenticationManager authenticationManager;
//
//	  private final UserService userService;
//
//	  private final RoleService roleService;
//
//	  private final PasswordEncoder encoder;
//
//	  private final JwtUtils jwtUtils;
	@Autowired
	 private AuthenticationManager authenticationManager;
	@Autowired
	  private UserService userService;
	@Autowired
	  private RoleService roleService;
	@Autowired
	  private PasswordEncoder encoder;
	@Autowired
	  private JwtUtils jwtUtils;

	  @PostMapping("/signin")
	  public JwtResponse authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

	    Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
	                                                                                                               loginRequest.getPassword()));

	    SecurityContextHolder.getContext().setAuthentication(authentication);
	    String jwt = jwtUtils.generateJwtToken(authentication);

	    MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
	    List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList());
	    return new JwtResponse(jwt,userDetails.getId(),userDetails.getUsername(),roles);
	  }

	  @PostMapping("/signup")
	  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
	    if (userService.existsByUsername(signUpRequest.getUsername())) {
	      return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
	    }

	    // Create new user's account
	    
	  //  System.out.println("instance oncesi");
	    User user = new User(signUpRequest.getUsername(),encoder.encode(signUpRequest.getPassword()) );
	 //   System.out.println("instance sonrasi");

	    Set<String> strRoles = signUpRequest.getRole();
	    Set<Role> roles = new HashSet<>();
	    Boolean admin = signUpRequest.getAdmin();
	    if(admin==null)admin=false;
	    System.out.println("KARDES ADMIN VALUE : " + admin);
	    if (strRoles == null) {
	    	if(!admin)
	    	{
	    		Role userRole = roleService.findByName(RoleType.ROLE_USER);
	    		roles.add(userRole);
	    	}
	    	else
	    	{
	    		Role userRole = roleService.findByName(RoleType.ROLE_ADMIN);
	    		roles.add(userRole);
	    	}
	      
	    } else {
	      strRoles.forEach(role -> {
	        switch (role) {
	          case "admin":
	            Role adminRole = roleService.findByName(RoleType.ROLE_ADMIN);
	            roles.add(adminRole);

	            break;

	          default:
	            Role userRole = roleService.findByName(RoleType.ROLE_USER);
	            roles.add(userRole);
	        }
	      });
	    }

	    user.setRoles(roles);
	    
	  //  System.out.println("neredeyse bitti  " + user.getUsername() +"  " + user.getPassword());
	    userService.createNewUser(user);

	    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	  }
}
