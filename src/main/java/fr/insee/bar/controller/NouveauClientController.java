package fr.insee.bar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import fr.insee.bar.model.Employe;
import fr.insee.bar.service.EmployeService;

@Controller
public class NouveauClientController {

	@Autowired
	private EmployeService employeService; 
	
	@GetMapping("/nouveau-client")
	public String nouveauClient(Employe employe, Model model) {
		if(employeService.estResponsable(employe)) {
			return "nouveau-client";
		}
		model.addAttribute(employe);
		return "redirect:/clients";
	}
}
