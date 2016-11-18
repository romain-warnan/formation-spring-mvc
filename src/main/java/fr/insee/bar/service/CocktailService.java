package fr.insee.bar.service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.insee.bar.dao.CocktailDao;
import fr.insee.bar.exception.BarCommandeException;
import fr.insee.bar.model.Cocktail;

@Service
public class CocktailService {

	@Autowired
	private CocktailDao cocktailDao;

	public boolean commandeValide(List<Cocktail> cocktails) {
		return cocktails != null && cocktails.size() > 0;
	}

	public void verifierCommandeValide(List<Cocktail> cocktails) throws BarCommandeException {
		if (!this.commandeValide(cocktails)) {
			throw new BarCommandeException("La commande ne contient aucun cocktail.");
		}
	}

	public Optional<Cocktail> hasard() {
		Random random = new Random(System.currentTimeMillis());
		short id = (short) random.nextInt(200);
		return cocktailDao.find(id);
	}
}
