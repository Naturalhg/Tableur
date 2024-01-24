package fr.iutfbleau.SAE3_2_Nolan_Lucile_Firmin;
import javax.swing.*;
import java.awt.*;

/**
* Zone de texte qui permet de modifier le contenu d'une cellule et de gérer les focus sur les cellules
*
* @see Cellule
* @see Fenetre
*/

public class Entree extends JTextField{
	/**
	* La cellule en cours de modification
	*/
	private Cellule cell = null;
	
	/**
	* true si une cellule est séléctionnée
	*/
	private boolean actif = false;

	
	/**
	* Crée un champ de texte vide
	* et qui n'est pas modifiable
	*/
	public Entree(){
		this.setEditable(false);
		this.addActionListener(new EntreeListener(this));

		// On gère la couleur du curseur, de la sélection et de la bordure
		this.setCaretColor(Fenetre.VIOLET_FONCE);;
		this.setSelectionColor(Fenetre.VIOLET_PASTEL);
		this.setBorder(BorderFactory.createLineBorder(Color.GRAY));
	}

	
	/**
	* Remplit le champ avec la formule d'une Cellule c,
	* ajoute une bordure bleue
	* et rend le texte modifiable
	*
	* @param c La cellule dont on veut modifier la formule
	*/
	public void activer(Cellule c){
		// Si on est déjà en cours de modification,
		if(this.actif){
			// on désélectionne l'ancienne cellule sans la mettre à jour
			this.cell.setSelectionne(false);
		}
		else{
			// Sinon on se met en mode "modifiable"
			this.actif = true;
			this.setBorder(BorderFactory.createLineBorder(Fenetre.VIOLET));
			this.setEditable(true);
		}

		// On met le curseur d'écriture dans le champ
		this.requestFocus();

		// On affiche la formule de la cellule à modifier
		this.cell = c;
		this.setText(c.getFormule());
	}
	

	/**
	* Vide le champ, enlève la bordure,
	* met à jourla Cellule modifiée
	* et rend le texte non modifiable
	*/
	public void desactiver(){
		// Si on est en cours de modification
		if(this.actif){
			
			// On désélectionne l'ancienne cellule
			this.actif= false;
			this.cell.setSelectionne(false);
			this.cell = null;

			// On vide le champ et on se met en mode "non modifiable"
			this.setText("");
			this.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			this.setEditable(false);
		}
	}
	
	
	/**
	* Renvoie la Cellule en cours de modification
	* 
	* @return la Cellule en cours de modification
	*/
	public Cellule getCell(){
		return this.cell;
	}
}