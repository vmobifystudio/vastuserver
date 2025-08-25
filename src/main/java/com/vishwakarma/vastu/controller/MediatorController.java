package com.vishwakarma.vastu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MediatorController {

	@RequestMapping("/")
	public String getHomePage(Model model, @RequestParam(required=false) String message) {
		model.addAttribute("message", message);
		return "redirect:/home";
	}
}
