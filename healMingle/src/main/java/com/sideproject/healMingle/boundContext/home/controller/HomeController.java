package com.sideproject.healMingle.boundContext.home.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

	@GetMapping("/")
	public String main(){
		return "usr/home/main";
	}
}
