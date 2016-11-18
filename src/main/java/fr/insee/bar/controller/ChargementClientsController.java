package fr.insee.bar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.insee.bar.exception.BarDroitException;
import fr.insee.bar.model.Employe;
import fr.insee.bar.service.ClientService;
import fr.insee.bar.service.EmployeService;

@Controller
@RequestMapping("/client")
public class ChargementClientsController {

	@Autowired
	private EmployeService employeService;

	@Autowired
	private ClientService clientService;

	@GetMapping("/chargement")
	public String nouveauClient(Employe employe, Model model) throws BarDroitException {
		employeService.verifierResponsable(employe);
		return "chargement-clients";
	}

	@PostMapping("/chargement")
	public String nouveauClientPost(MultipartFile file, RedirectAttributes redirectAttributes) {
		long n = clientService.chargement(file);
		redirectAttributes.addFlashAttribute("message", String.format("%d clients ont été ajoutés avec succès à partir du fichier %s", n, file.getOriginalFilename()));
		return "redirect:/clients";
	}
}
