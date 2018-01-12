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
		$.ajax({
			url: '/cocktails/recherche',
			method: 'GET',
			data: {'q': encodeURI(q)}
		}).done(function(cocktails) {
			afficherSuggestions(cocktails);
		});
	};
	
	var commander = function() {
		var cocktails = cocktailsChoisis();
		$.ajax({
			url: '/cocktails/commande',
			method: 'POST',
			contentType : 'application/json; charset=utf-8',
			data: JSON.stringify(cocktails)
		}).done(function(prix) {
			afficherPrix(prix);
		});
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