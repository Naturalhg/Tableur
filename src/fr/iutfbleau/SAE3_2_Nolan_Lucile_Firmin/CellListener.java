package fr.iutfbleau.SAE3_2_Nolan_Lucile_Firmin;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

/**
* La classe CellListener permet de détecter les clics sur une Cellule.
* Elle gére ensuite la sélection de la Cellule et l'activation de l'Entree associée.
*
* @see Cellule
* @see Entree
*/
public class CellListener implements ActionListener{
	/**
	* L'Entree liée aux Cellules écoutées
	*/
	Entree field;

	
	/**
	* Crée un CellListener qui permet de lier des Cellules à l'Entree e
	*
	* @param e l'Entree à lier aux Cellules écoutées
	*/
	public CellListener(Entree e){
		this.field = e; 
	}


	/**
	* Lors du clic sur une Cellule observée, gère sa selection 
	* et l'activation de l'Entrée associée.
	*
	* Si la Cellule cliquée n'était pas encore sélectionnée, 
	* elle est désormais sélectinnée et l'Entrée est activée avec cette Cellule. 
	*
	* Le curseur de saisie de texte est automatiquement placé dans 
	* le champ de texte de l'Entrée. 
	* Sinon, la Cellule est déselectionnée et l'Entrée désactivée.
	*
	* @param evenement l'action effectuée sur la Cellule
	*/
	public void actionPerformed(ActionEvent evenement){
		// Récupération de la Cellule sur lequel le clic a été effectué
		Cellule cell = (Cellule) evenement.getSource();

		// Si la Cellule était déjà selectionnée, on la désélectionne et on désactive l'Entree
		if (cell.getSelectionne()){
			cell.setSelectionne(false);
			this.field.desactiver();
		}
			
		// Sinon on sélectionne la Cellule et on active l'Entree
		else{
			cell.setSelectionne(true);
			this.field.activer(cell);
		}
	}

}