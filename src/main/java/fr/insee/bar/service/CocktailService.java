package fr.insee.bar.service;

import java.util.List;

import org.springframework.stereotype.Service;

import fr.insee.bar.model.Cocktail;

@Service
public class CocktailService {

	public boolean commandeValide(List<Cocktail> cocktails) {
		return cocktails != null && cocktails.size() > 0;
	}

	// TODO public void verifierCommandeValide(List<Cocktail> cocktails) throws BarCommandeException;
}
