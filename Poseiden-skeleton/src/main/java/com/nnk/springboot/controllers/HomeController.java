package com.nnk.springboot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.security.RolesAllowed;

@Controller
public class HomeController
{
	@GetMapping("/home")
	public String home()
	{
		return "home";
	}

	/*@RequestMapping("/admin/home")
	@RolesAllowed("ADMIN")
	public String adminHome(Model model)
	{
		return "redirect:/bidList/list";
	}*/
}
