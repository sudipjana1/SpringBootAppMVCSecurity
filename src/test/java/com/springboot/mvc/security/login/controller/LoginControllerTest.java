package com.springboot.mvc.security.login.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.springboot.mvc.security.login.model.Role;
import com.springboot.mvc.security.login.model.User;
import com.springboot.mvc.security.login.service.RoleService;
import com.springboot.mvc.security.login.service.UserService;;


@RunWith(SpringRunner.class)
@WebMvcTest(controllers = LoginController.class)
@AutoConfigureMockMvc(secure=false)
public class LoginControllerTest{
	

    @MockBean
    private UserService userService;
    
    @MockBean
    private RoleService roleService;
	@Autowired
	private MockMvc mockMvc;
	
	private Role role;
	private User user;
	
	@Before
	public void setup() {
		//mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		role = new Role();
		role.setId(1);
		role.setRole("ADMIN");
		

        user = new User();
        user.setId(1);
        user.setActive(1);
        user.setName("testname");
        user.setLastName("testlname");
        user.setUsername("testuser");
        user.setPassword("password");
        user.setRoles(new HashSet<Role>(Arrays.asList(role)));
	}
	
	@Test
	public void verifyLoginPageLoad() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.get("/"))
		.andExpect(MockMvcResultMatchers.view().name("login"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(print());
	}
	
	@Test
	public void verifyRegistrationPageLoad() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.get("/registration"))
		.andExpect(MockMvcResultMatchers.view().name("registration"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(print());
	}
	
	
	@Test
	public void verifyRegistrationPagePost() throws Exception{
		Mockito.when(userService.saveUser(any())).thenReturn(user);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/registration")
				.param("username", "testuser")
				.param("password", "testpass")
				.param("name", "testname")
				.param("lastName", "testlname")
				.param("active", "1")
				.sessionAttr("user", new User()))
		.andExpect(MockMvcResultMatchers.view().name("registration"))
		.andExpect(model().attribute("successMessage", "User has been registered successfully"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(print());
	}

}
