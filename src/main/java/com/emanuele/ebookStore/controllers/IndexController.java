package com.emanuele.ebookStore.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class IndexController {

	@GetMapping("/")
    public ModelAndView index(){
		ModelAndView mv = new ModelAndView("index.jsp");
        return mv;
    }
}
