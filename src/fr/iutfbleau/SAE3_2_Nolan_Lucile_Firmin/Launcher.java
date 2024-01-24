package fr.iutfbleau.SAE3_2_Nolan_Lucile_Firmin;

/**
* Créer le tableau de cellules et la fenêtre de l'application, puis les affichent
*
* @see Tableau
* @see Fenetre
*/

public class Launcher {

	/**
	* Lance l'application de tableur
	*/
	public void lancer() {
		// nombre de lignes et de colonnes du tableur
		int col = 9, row = 9;

		// création du tableau de cellules
		Tableau t = new Tableau(row, col);

		// création de la fenêtre en y ajoutant le tableau précédent
		Fenetre f = new Fenetre(t, row, col);

		// affiche le tout dans la fenêtre
		f.setVisible(true);
	}
}