package fr.insee.bar.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.insee.bar.dao.ClientDao;
import fr.insee.bar.model.Client;
import fr.insee.bar.model.Client.Titre;
import fr.insee.bar.validator.ClientValidator;

@Controller
@RequestMapping("/client")
public class NouveauClientController {

	@Autowired
	private ClientDao clientDao;

	@Autowired
	private ClientValidator clientValidator;

	@ModelAttribute("titres")
	private Titre[] titres() {
		return Titre.values();
	}

	@GetMapping("/nouveau")
	public String nouveauClient(Model model) {
		model.addAttribute("client", new Client());
		return "nouveau-client";
	}

	@PostMapping("/nouveau")
	public String nouveauClientPost(@Valid Client client, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
		clientValidator.validate(client, result);
		if (result.hasErrors()) {
			model.addAttribute("client", client);
			return "nouveau-client";
		}
		clientDao.insert(client);
		redirectAttributes.addFlashAttribute("nouveauClient", client);
		return "redirect:/clients";
	}
}
