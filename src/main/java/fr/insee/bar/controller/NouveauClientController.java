package fr.insee.bar.controller;

import fr.insee.bar.dao.ClientDao;
import fr.insee.bar.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/client")
public class NouveauClientController {

	@Autowired
	private ClientDao clientDao;

	@GetMapping("/nouveau")
	public String nouveauClient(Model model) {
		model.addAttribute("client", new Client());
		return "nouveau-client";
	}

	@PostMapping("/nouveau")
	public String nouveauClientPost(@ModelAttribute Client client, RedirectAttributes redirectAttributes) {
		clientDao.insert(client);
		redirectAttributes.addFlashAttribute("nouveauClient", client);
		return "redirect:/clients";
	}
}
