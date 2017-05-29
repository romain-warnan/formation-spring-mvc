package fr.insee.bar.controller;

import fr.insee.bar.model.Client;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ClientsController {

	@GetMapping("/clients")
	public String clients(Model model, List<Client> clients) {
		model.addAttribute("clients", clients);
		return "clients";
	}
}
