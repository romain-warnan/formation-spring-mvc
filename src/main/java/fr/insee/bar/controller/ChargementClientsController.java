package fr.insee.bar.controller;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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
	public String chargement(Employe employe, Model model) throws BarDroitException {
		employeService.verifierResponsable(employe);
		return "chargement-clients";
	}

	@PostMapping("/chargement")
	public String chargementPost(MultipartFile file, RedirectAttributes redirectAttributes) {
		long n = clientService.chargement(file);
		redirectAttributes.addFlashAttribute("message", String.format("%d clients ont été ajoutés avec succès à partir du fichier %s", n, file.getOriginalFilename()));
		return "redirect:/clients";
	}

	@GetMapping("/telechargement")
	public HttpEntity<FileSystemResource> telechargement() {
		File fichier = clientService.fichier();
		return new HttpEntity<FileSystemResource>(new FileSystemResource(fichier), httpHeaders(fichier));
	}

	private HttpHeaders httpHeaders(File file) {
		HttpHeaders header = new HttpHeaders();
		header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName());
		header.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		header.setContentLength(file.length());
		return HttpHeaders.readOnlyHttpHeaders(header);
	}
}