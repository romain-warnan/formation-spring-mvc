package fr.insee.bar.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class AccueilController {

	@Value("${name}")
	private String name;

	@RequestMapping("/")
	@ResponseStatus(HttpStatus.MOVED_PERMANENTLY)
	public String welcome() {
		return "redirect:/accueil";
	}

	@RequestMapping(value = "/accueil")
	public String hello(Model model) {
		model.addAttribute("message", this.message());
		return "accueil";
	}

	private String message() {
		return "Hello world " + name;
	}
}
