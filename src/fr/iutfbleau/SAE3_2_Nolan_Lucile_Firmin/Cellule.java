package fr.iutfbleau.SAE3_2_Nolan_Lucile_Firmin;
import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
* La classe Cellule représente l'affichage graphique d'une cellule dans le tableau.
* Cette Cellule pourra changer d'état visuellement si elle est sélectionnée.
* Elle contient évidemment un CellContent représentant son contenu, cependant, 
* elle s'occupe uniquement d'afficher les résultats déjà calculés.
*
* @see CellContent
* @see Fenetre
*/

public class Cellule extends JButton implements Observer{

	/**
	* Le résultat qui va être affiché dans la cellule
	*/
	private String affichage = "";

	/**
	* true si la cellule est sélectionnée
	*/
	private boolean surbrillance = false;

	/**
	* CellContent qui aura le contenu concret de la cellule (formule et résultat)
	*/
	private CellContent contenu;

	/**
	* true si la formule contient une erreur (elle est incorrecte ou incalculable)
	*/
	private boolean erreur = false;

	/**
	* Crée une cellule avec un CellContent pour représenter sa formule
	*
	* @param content le contenu de la cellule
	*/
  	public Cellule(CellContent content) {
		// héritage de JButton
		super();

		// On ajoute le CellContent à la cellule
		this.contenu = content;

		// La cellule devient Observer du contenu
		this.contenu.addObserver(this);

		// Le résultat à afficher est récupéré du contenu
		this.setAffichage(content.getResultat());

		// Ajout des espaces autour de la Cellule
		this.setMargin(new Insets(0,1,0,1));
		
		this.setOpaque(true);

		// Le fond d'une Cellule est blanc
		this.setBackground(Color.WHITE);

		// Le texte d'une Cellule est noir
		this.setForeground(Color.BLACK);
		
		this.setFocusPainted(false);
	}


	/**
	* Active ou désactive la surbrillance de la cellule
	* si celle-ci est sélectionnée ou non
	*
	* @param fluo indication de la sélection de la cellule
	*/
	public void setSelectionne(boolean fluo) {
		this.surbrillance = fluo;

		// si la surbrillance est activée, le bord de la cellule devient violet
		if(fluo){
			this.setBackground(Fenetre.VIOLET);
			this.setForeground(Color.WHITE);
		}

		// sinon, le fond devient blanc et la couleur du texte change
		else{
			this.setBackground(Color.WHITE);
			// si la cellule contient une erreur, le texte devient rouge
			if(this.erreur){
				this.setForeground(Color.RED);
			}
			// sinon, le texte devient noir
			else{
				this.setForeground(Color.BLACK);
			}
		}
	}

	/**
	* Renvoie l'état actuel de la cellule :
	* - sélectionnée ou non
	*
	* @return l'état de la cellule
	*/
	public boolean getSelectionne() {
		return this.surbrillance;
	}

	/**
	* Ecrit le résultat de la formule dans la cellule sur
	* le tableur
	*
	* @param resultat le résultat à écrire
	*/
	public void setAffichage(String resultat) {
		float f;
		int i;
		// On essaye de convertir le résultat en float
		try{
			f = Float.parseFloat(resultat);
			
			// conversion réussie, pas d'erreur
			this.erreur = false;
			this.setForeground(Color.BLACK);
			
			i = (int) f;
			// si le résultat est un entier, on écrit uniquement l'entier
			if(i == Float.parseFloat(resultat)){
				this.affichage = i + "";
			}
			// sinon, on écrit le résultat complet car il est décimal
			else{
				this.affichage = resultat;				
			}
		// Si ce n'est pas possible, on affiche le résultat en rouge
		}catch(NumberFormatException n){
			this.erreur = true;
			this.setForeground(Color.RED);
			this.affichage = resultat;	
		}
		// affichage du résultat
		this.setText(this.affichage);
	}

	/**
	* Renvoie le résultat de la formule de la cellule
	* sous forme de texte
	*
	* @return le résultat de la formule
	*/
	public String getAffichage() {
		return this.affichage;
	}

	/**
	* Renvoie le résultat de la formule de la cellule
	*
	* @return le résultat de la formule
	*/
	public float getResultat() {
		return Float.parseFloat(this.affichage);
	}

	/**
	* Ecrit le résultat de la formule dans la cellule sur
	* le tableur
	*
	* @param formule le résultat à écrire
	*/
	public void setFormule(String formule) {
		this.contenu.setFormule(formule);
		this.setAffichage(this.contenu.getResultat());
	}

	
	/**
	* Renvoie le résultat de la formule de la cellule
	* sous forme de texte
	*
	* @return le résultat de la formule
	*/
	public String getFormule() {
		return this.contenu.getFormule();
	}

	/**
	* Met à jour l'affichage de la cellule si
	* un Observer a changé son contenu
	*
	* @param o l'Observer qui a changé son contenu
	* @param arg l'argument de l'Observer
	*/
	public void update(Observable o, Object arg){
		this.setAffichage(this.contenu.getResultat());
	}
}