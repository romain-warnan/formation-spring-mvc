package fr.insee.bar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.insee.bar.dao.ClientDao;
import fr.insee.bar.model.Client;

@Controller
@RequestMapping("/client")
public class ModificationClientController {

	@Autowired
	private ClientDao clientDao;

	@GetMapping("/modification/{client}")
	public String modificationClient(@PathVariable("client") Client client, Model model) {
		model.addAttribute("client", client);
		return "modification-client";
	}

	@PostMapping("/modification/{client}")
	public String modificationClientPost(@ModelAttribute Client client, RedirectAttributes attributes) {
		clientDao.update(client);
		attributes.addFlashAttribute("modification", true);
		return "redirect:/client/" + client.getId();
	}
}
