$(document).ready(function() {
	
	var context = this;
	var $champ = $('#champ-recherche');
	var $suggestions = $('#suggestions');
	var $commande = $('#commande');
	var $bouton = $('#bouton-commander');
	var $prix = $('#prix');
	
	var initialiser = function(context){
		$suggestions.hide();
		$champ.keyup(function(){
			rechercher($champ.val());
		});
		$bouton.click(function() {
			commander();
		});
	};
	
	var rechercher = function(q){
		// Récupérer la liste des cocktails correspondants à la recherche "q"
		// Appeler la méthode "afficherSuggestions" avec cette liste
	};
	
	var commander = function() {
		// Récupérer la liste des cocktails sélectionnés avec la fonction "cocktailsChoisis"
		// La poster vers le serveur
		// Récupérer en retour le prix total de la commande
		// Appeler la méthode "afficherPrix" avec cette valeur
	};
	
	var cocktailsChoisis = function() {
		var cocktails = [];
		$('#commande li.hidden').each(function(index, item) {
			cocktails.push({
				id: $(item).text()
			});
		});
		return cocktails;
	};
	
	var afficherSuggestions = function(cocktails) {
		if(cocktails.length > 0){
			suggerer(cocktails);
			$suggestions.show();
		}
		else {
			$suggestions.hide();
		}
	};
	
	var afficherPrix = function(prix){
		$prix.text(': ' + prix + ' €');
	};
	
	var suggerer = function(cocktails){
		$suggestions.empty();
		$.each(cocktails, function(index, cocktail) {
			$suggestions.append($('<li>').append(cocktail.nom));
			$suggestions.append($('<li class="hidden">').append(cocktail.id));
		});
		$suggestions.find('li').each(function(index, item) {
			$item = $(item);
			$item.click(function() {
				var $libelle = $(this);
				var $id = $libelle.next();
				choisir($id.text(), $libelle.text());
			});
		})
	};
	
	var choisir = function(id, nom){
		var $item = $('<li>').append(nom);
		$item.click(function() {
			$(this).next().remove();
			$(this).remove();
		});
		$commande.append($item);
		$commande.append($('<li class="hidden">').append(id));
		$suggestions.empty();
		$suggestions.hide();
		$champ.val('');
		$champ.focus();
	};
	
	initialiser(context);
});