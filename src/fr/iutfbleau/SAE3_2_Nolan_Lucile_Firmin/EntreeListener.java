package fr.iutfbleau.SAE3_2_Nolan_Lucile_Firmin;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

/**
* La classe EntreeListener permet de détecter le texte entré dans une Entree, 
* puis d'en faire la formule de la Cellule en cours de modification, ce qui permet de calculer cette formule
* et d'en  afficher le résultat dans la Cellule. 
* EntreeListener gére ensuite la désactivation de l'Entree et la désélection de la Cellule.
*
* @see Cellule
* @see Entree
*/
public class EntreeListener implements ActionListener{
	/**
	* L'Entree écoutée par ce EntreeListener
	*/
	Entree field;

	/**
	* Crée un EntreeListener qui écoute l'Entree e.
	*
	* @param e l'Entree qu'on écoute
	*/
	public EntreeListener(Entree e){
		this.field = e; 
	}


	/**
	* Lors de l'entrée d'un texte, remplace la formule de la Cellule liée à l'Entrée par le texte entré
	* puis désactive l'Entrée et déselectionne la Cellule.
	*
	* Quand l'utilisateur appuie sur la touche «entrée» pendant l'édition du champ de texte, 
	* le texte entré est transmis à la Cellule dont la formule est en cours de modification. 
	* Le texte est alors enregistré comme nouvelle formule de la Cellule puis calculé.
	* La Cellule affiche ensuite le nouveau résultat. 
	* Elle est désélectionnée et l'Entrée observée est désactivée.
	*
	* @param evenement l'action effectuée sur l'Entrée
	*/
	public void actionPerformed(ActionEvent evenement){
		// Récupération du tedxe entré
		String formule = evenement.getActionCommand();

		// Mise à jour de la formule de la Cellule, 
		// et donc calcul et affichage du nouveau résulat
		this.field.getCell().setFormule(formule);

		// Désactivation de l'Entree et déselection de la Cellule
		this.field.desactiver();
		
	}
}