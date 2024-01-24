package fr.iutfbleau.SAE3_2_Nolan_Lucile_Firmin;
import java.util.*;

/**
* Noeud d'un arbre binaire de recherche qui contient un symbole ou un terme d'une formule, 
* pouvant contenir un fils droit et un fils gauche avec des symboles ou termes eux aussi
*
* @see AbstractSyntaxTree
* @see CellContent
*/

public class Node {
	/**
	* Le symbole ou le terme de la formule qui est représenté que représente ce noeud
	*/
	private String val;

	/**
	* Résultat final du calcul de la formule du sous-arbre dont ce noeud est la racine
	*/
	private Float res = null;

	/**
	* Fils gauche du noeud
	*/
	private Node gauche = null;
	
	/**
	* Fils droit du noeud
	*/
	private Node droite = null;

	/**
	* le CellContent qui contient la formule représentée par l'arbre dont fait partie ce noeud
	*/
	private CellContent cont;
	

	/**
	* Initialise un noeud avec un sous-arbre soit un symbole
	* 
	* @param c le CellContent qui contient la formule représentée par l'arbre dont fait partie ce noeud
	* @param f la file qui contient le reste de la formule
	*
	* @throws IllegalArgumentException quand la formule n'est pas correcte ou pas calculable
	*/
	public Node(CellContent c, File f) throws IllegalArgumentException{
		// initialisation du contenu de la cellule
		this.cont = c;

		// on défile pour récupérer le dernier symbole à ajouter
		String v = f.dequeue();

		// ajout de ce symbole au noeud actuel
		this.val = v;

		// si le dernier élément est un symbole,
		if(v.equals("+") || v.equals("*") || v.equals("/") || v.equals("-")) {
			try{	
				// on essaye d'ajouter un fils gauche et un fils droit avec le reste de la file
				this.setLeft(f);
				this.setRight(f);
			} catch(NoSuchElementException n){
				// erreur de syntaxe (pas correct ou pas calculable)
				throw new IllegalArgumentException("!SYNTAX");
			}
		}
	}
	

	/**
	* Ajoute un noeud à gauche à partir du reste de la formule contenu dans une file
	*
	* @param f formule ou sous-formule à ajouter
	*/
	public void setLeft(File f){
		this.gauche = new Node(this.cont, f);
	}
	
	
	/**
	* Ajoute un noeud à droite à partir du reste de la formule contenu dans une file
	*
	* @param f formule ou sous-formule à ajouter
	*/
	public void setRight(File f){
		this.droite = new Node(this.cont, f);
	}
	

	/**
	* Renvoie la valeur de la feuille actuelle
	*
	* @return la valeur de la feuille
	*/
	public Float getResultat(){
		// si la valeur du noeud est vide
		if(this.val.equals("") || this.val == null){
			// on renvoie null au lieu d'une chaîne de caractere vide
			return null;
		}
		// si le résultat calculé du noeud est vide
		if(this.res==null){
			// on met le resultat à null
			this.setRes();
		}
		return this.res;
	}

	/**
	* Effectue récursivement le calcul de la formule en fonction de
	* ses enfants et met à jour le résultat du noeud actuel
	*
	* @return le résultat du calcul des sous-arbres du noeud ou 
	* sa propre valeur si c'est une feuille
	* @throws IllegalArgumentException quand la formule n'est pas correcte ou pas calculable :
	* 	- division par 0
	* 	- référence à une cellule vide ou contenant une erreur
	* 	- référence à une cellule qui n'existe pas
	*	- création d'un cycle
	*/
	private void setRes() throws IllegalArgumentException {
		
		// si la valeur du noeud est une opérande
		
		if(this.val.equals("+")){
			// on calcule le résultat de l'addition des deux sous-arbres
			this.res = this.gauche.getResultat() + this.droite.getResultat();
		}

		else if(this.val.equals("*")){
			// on calcule le résultat de la multiplication des deux sous-arbres
			this.res = this.gauche.getResultat() * this.droite.getResultat();
		}

		else if(this.val.equals("/")){
			// si le diviseur est égal à 0
			if(this.droite.getResultat() == 0.0){
				// on lève une exception par rapport à une erreur de calcul
				throw new IllegalArgumentException("!CALCUL");
			}
			// on calcule le résultat de la division des deux sous-arbres
			this.res = this.gauche.getResultat() / this.droite.getResultat();
		}

		else if(this.val.equals("-")){
			// on calcule le résultat de la soustraction des deux sous-arbres
			this.res = (this.gauche.getResultat() - this.droite.getResultat());
		}

		// sinon
		else{
			// on essaye de convertir la valeur en float
			try{
				this.res = Float.parseFloat(this.val);
			}catch(NumberFormatException n){
				// on essaye de récupérer la valeur d'une référence de cellule
				try{
					// récupération de la cellule grâce à son nom
					// si la cellule n'existe, ça lance une NullPointerException
					CellContent cellc = this.cont.getTableau().getCellByID(this.val);
					
					// ajout de la cellule en tant qu'observeur
					// si cela crée un cycle, ça lance une IllegalStateException
					// ou une IllegalArgumentException si on essaie de faire référence à la cellule elle-même
					this.cont.addObserving(cellc);

					// on récupère le résultat de la formule de la cellule en question
					String s = cellc.getResultat();

					// si le résultat est vide
					if(s == null || s.equals("")){
						// on lève une exception par rapport à une référence de cellule vide
						throw new IllegalArgumentException("!CALCUL");	
					}
					// sinon on convertit le résultat de la cellule référencée
					// si le résultat de la cellule référencée n'est pas un nombre,
					// ça lève une NumberFormatException
					this.res = Float.parseFloat(cellc.getResultat());
				}
				catch(NullPointerException e) {
					// on lève une exception par rapport à une référence à une cellule inexistante
					throw new IllegalArgumentException("!SYNTAX");
				}
				catch(NumberFormatException e) {
					// on lève une exception par rapport à une erreur de calcul
					throw new IllegalArgumentException("!CALCUL");
				}
				catch(IllegalStateException e) {
					// on lève une exception par rapport à une détection de cycle
					throw new IllegalArgumentException("!SYNTAX");
				}
			}
		}
	}
	
	/**
	* Permet de recalculer le résultat d'une cellule en parcourant
	* toute sa formule contenue dans l'arbre afin de mettre à jour
	* le résultat de la cellule quand une modification a lieu par 
	* rapport aux cellules qu'elle observe
	*/
	public void recalculate(){
		// si le noeud est une feuille
		if(this.isLeaf()){
			// on met à jour le résultat de la feuille
			this.setRes();
		}
		// sinon
		else{
			// on recalcule le résultat du sous-arbre gauche
			this.gauche.recalculate();

			// on recalcule le résultat du sous-arbre droit
			this.droite.recalculate();

			// on met à jour le résultat du noeud actuel
			this.setRes();
		}
	}


	/**
	* Renvoie vrai si le noeud n'a aucun fils 
	* et est donc une valeur entière ou une 
	* référence à une cellule et false sinon
	*
	* @return true si le noeud est une feuille et false sinon
	*/
	public boolean isLeaf(){
		return this.gauche == null && this.droite == null;
	}
}