package fr.insee.bar.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import fr.insee.bar.dao.CocktailDao;
import fr.insee.bar.model.Cocktail;

@Controller
@RequestMapping("/cocktails")
public class CocktailsController {

	@Autowired
	private CocktailDao cocktailDao;

	@GetMapping("/recherche")
	@ResponseBody
	public List<Cocktail> recherche(@RequestParam("q") String q) {
		return cocktailDao.search(q);
	}

	@PostMapping("/commande")
	@ResponseBody
	public Double commande(@RequestBody List<Cocktail> cocktails) {
		double prix = cocktails.stream()
			.map(cocktailDao::findByExample)
			.mapToDouble(Cocktail::getPrix)
			.sum();
		return prix;
	}
}
