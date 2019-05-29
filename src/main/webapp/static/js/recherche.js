$(document).ready(() => {
	
	const context = this;
	const $champ = $('#champ-recherche')
	const $suggestions = $('#suggestions')
	const $commande = $('#commande')
	const $bouton = $('#bouton-commander')
	const $prix = $('#prix')
	const $message = $('#message')
	
	const initialiser = context => {
		$suggestions.hide()
		$champ.keyup(() => rechercher($champ.val()))
		$bouton.click(commander)
	}
	
	const rechercher = q => {
		// Récupérer la liste des cocktails correspondants à la recherche "q"
		// Appeler la méthode "afficherSuggestions" avec cette liste
	};
	
	const commander = () => {
		// Récupérer la liste des cocktails sélectionnés avec la fonction "cocktailsChoisis"
		// La poster vers le serveur
		// Récupérer en retour le prix total de la commande
		// Appeler la méthode "afficherPrix" avec cette valeur
	};
	
	const effacerErreur = () => {
		$message.empty()
		$message.removeClass("error")
	}
	
	const afficherErreur = message => {
		$message.text(message)
		$message.addClass("error")
	}
	
	const afficherSuggestions = cocktails => {
		if(cocktails.length > 0){
			suggerer(cocktails)
			$suggestions.show()
		}
		else {
			$suggestions.hide()
		}
	}
	
	const afficherPrix = prix => $prix.text(': ' + prix + ' €')
	
	const suggerer = cocktails => {
		$suggestions.empty()
		$.each(cocktails, (index, cocktail) => {
			$suggestions.append($('<li>').append(cocktail.nom))
			$suggestions.append($('<li class="hidden">').append(cocktail.id))
		})
		$suggestions.find('li').each((index, item) => {
			$item = $(item)
			$item.click(function() {
				const $libelle = $(this)
				const $id = $libelle.next()
				choisir($id.text(), $libelle.text())
			})
		})
	}
	
	const choisir = (id, nom) => {
		effacerErreur()
		const $item = $('<li>').append(nom)
		$item.click(function() {
			$(this).next().remove()
			$(this).remove()
		})
		$commande.append($item)
		$commande.append($('<li class="hidden">').append(id))
		$suggestions.empty()
		$suggestions.hide()
		$champ.val('')
		$champ.focus()
	}
	
	initialiser(context)
})
