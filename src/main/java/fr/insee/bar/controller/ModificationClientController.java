package fr.insee.bar.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.insee.bar.dao.ClientDao;
import fr.insee.bar.exception.BarDroitException;
import fr.insee.bar.model.Client;
import fr.insee.bar.model.Employe;
import fr.insee.bar.validator.ClientValidator;

@Controller
@RequestMapping("/client")
public class ModificationClientController {

	@Autowired
	private ClientDao clientDao;

	@Autowired
	private ClientValidator clientValidator;

	@GetMapping("/modification/{client}")
	public String modificationClient(@PathVariable("client") Client client, Employe employe, Model model) throws BarDroitException {
		// employeService.verifierResponsable(employe);
		model.addAttribute("client", client);
		return "modification-client";
	}

	@PostMapping("/modification/{client}")
	public String modificationClientPost(@Valid Client client, BindingResult result, Model model, RedirectAttributes attributes) {
		clientValidator.validate(client, result);
		if (result.hasErrors()) {
			model.addAttribute("client", client);
			return "modification-client";
		}
		clientDao.update(client);
		attributes.addFlashAttribute("modification", true);
		return "redirect:/client/" + client.getId();
	}
}
