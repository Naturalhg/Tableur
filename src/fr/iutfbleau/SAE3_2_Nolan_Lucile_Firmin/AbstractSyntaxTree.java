package fr.iutfbleau.SAE3_2_Nolan_Lucile_Firmin;
import java.util.*;

/**
* La classe AbstractSyntaxTree permet de créer un arbre de syntaxe abstraite
* d'une formule en notation préfixe et d'en calculer le résultat.
*
* On peut recalculer le résultat de l'arbre, récupérer sa racine ou son résultat.
* Le résultat est stocké sous forme de String. Il est soit un nombre flottant, 
* soit un texte "!SYNTAX" qui indique une formule incorrecte 
* (pas en notation préfixe ou introduisant une référence circulaire),
* soit un texte "!SYNTAX" qui indique une formule incalculable 
* (division par 0 ou référence à une cellule incorrecte ou incalculable).
*
* @see Node
*/
public class AbstractSyntaxTree{
	/**
	* Le nœud racine de l'arbre
	*/
	private Node racine = null;
	
	/**
	* Le résultat du calcul
	*/
	private String resultat = "";

	
	/**
	* Crée un arbre contenant et pouvant calculer une formule en notation préfixe contenue dans une File f
	*
	* @param c le CellContent qui contient la formule représentée par cet arbre
	* @param f la File contenant une formule en notation préfixe
	*/
	public AbstractSyntaxTree(CellContent c, File f){
		// Si la formule est vide, le resultat est un String vide, sinon
		if(!f.isEmpty() && f != null){
			try{
				// On crée la racine puis tous les nœuds fils
				this.racine = new Node(c, f);

				// Si la formule est n'est pas correcte, on stocke une erreur de syntaxe comme résultat
				if(!f.isEmpty()) {
					throw new IllegalArgumentException("!SYNTAX");
				}
				
				// Si la formule est correcte et calculable, on stocke son résultat
				this.resultat = this.racine.getResultat() + "";
				
			} catch(IllegalArgumentException i) {
				// Si la formule est n'est pas correcte ou pas calculable, 
				// on stocke l'erreur associée (syntaxe ou calcul) comme résultat
				this.resultat = i.getMessage();
			}
		}	
	}

	
	/**
	* Crée un arbre qui contient une formule vide.
	*/
	public AbstractSyntaxTree(){
		this.racine = null;
	}

	
	/**
	* Renvoie le nœud racine de l'arbre.
	*
	* @return le nœud racine de l'arbre
	*/
	public Node getRacine(){
		return this.racine;
	}

	
	/**
	* Renvoie le résultat du calcul de la formule contenue dans l'arbre.
	*
	* Renvoie soit un nombre flottant, 
	* soit un texte "!SYNTAX" qui indique une formule incorrecte 
	* (pas en notation préfixe ou introduisant une référence circulaire),
	* soit un texte "!SYNTAX" qui indique une formule incalculable 
	* (division par 0 ou référence à une cellule incorrecte ou incalculable).
	*
	* @return le résultat du calcul de la formule contenue dans l'arbre
	*/
	public String getResultat() {
		return this.resultat;
	}

	
	/**
	* Recalcule le résultat du calcul de la formule contenue dans l'arbre.
	*/
	public void recalculate() {
		try{
			// On recalcule la formule
			this.racine.recalculate();
			
			// Si la formule est correcte et calculable, on stocke son résultat
			this.resultat = this.racine.getResultat() + "";
			
		} catch(IllegalArgumentException i) {
			// Si la formule est n'est pas correcte ou pas calculable, 
			// on stocke l'erreur associée (syntaxe ou calcul) comme résultat
			this.resultat = i.getMessage();
		}
	}
}