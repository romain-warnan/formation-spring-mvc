$(document).ready(function() {
	
	var context = this;
	var $champ = $('#champ-recherche');
	var $suggestions = $('#suggestions');
	var $commande = $('#commande');
	var $bouton = $('#bouton-commander');
	var $prix = $('#prix');
	
	var initialiser = context => {
		$suggestions.hide();
		$champ.keyup(() => rechercher($champ.val()));
		$bouton.click(commander);
	}
	
	var rechercher = function(q){
		$.ajax({
			url: '/cocktails/recherche',
			method: 'GET',
			data: {'q': encodeURI(q)}
		})
		.done(afficherSuggestions);
	};
	
	var commander = function() {
		var cocktails = cocktailsChoisis();
		$.ajax({
			url: '/cocktails/commande',
			method: 'POST',
			contentType : 'application/json; charset=utf-8',
			data: JSON.stringify(cocktails)
		})
		.done(afficherPrix);
	};
	
	var cocktailsChoisis = function() {
		var cocktails = [];
		$('#commande li.hidden').each((index, item) => {
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
		$.each(cocktails, (index, cocktail) => {
			$suggestions.append($('<li>').append(cocktail.nom));
			$suggestions.append($('<li class="hidden">').append(cocktail.id));
		});
		$suggestions.find('li').each((index, item) => {
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