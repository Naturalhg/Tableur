package fr.iutfbleau.SAE3_2_Nolan_Lucile_Firmin;
import java.util.*;

/**
* La classe Tableau permet de regrouper le contenu de plusieurs cellules.
* Il permet de récupérer ce contenu grâce au nom de la cellule, au format 'Lettre de la colonne' + 'Numéro de la ligne'. 
* Il ne peut pas contenir plus de 26 colonnes de cellules.
*
* @see CellContent
*/
public class Tableau{
	/**
	* Le dictionnaire associant les CellContents aux codes des cellules
	*/
	private Map<String, CellContent> cells = new HashMap<String, CellContent>();

	
	/**
	* Crée row X col CellContent et les associe à un code
	* @param row le nombre de lignes du tableur
	* @param col le nombre de colonnes du tableur
	* @throws IllegalArgumentException si on tente de crée un tableau vide ou de plus de 26 colonnes
	*/
	public Tableau(int row, int col){
		// Le tableau ne peut pas faire plus de 26 colonnes
		if(col > 26){
			throw new IllegalArgumentException("Col cannot be > 26");
		}
		// Le tableau ne peut pas être vide
		if(row < 1 || col < 1){
			throw new IllegalArgumentException("Size values cannot be less than 1");
		}

		// Création des variables pour le CellContent et le code associé
		CellContent c;
		String code;

		// Création du tableau ligne par ligne
		for(int i = 1; i<=row; i++){
			
			// Et colonne par colonne
			for(int j = 0; j<col; j++){

				// Création du code au format 'Lettre de la colonne' + 'Numéro de la ligne'
				code = String.valueOf((char)(j + 65)) + i;
				
				// Création des contenus de cellules en les associant à leur code
				c = new CellContent(this);
				this.cells.put(code, c);
			}
		}
	}

	/**
	* Renvoie le CellContent associé au code donn, ou null si ce code ne correspond à aucun  CellContent
	* @param code l'ID de la cellule
	* @return le CellContent associé au code donné, ou null si ce code ne correspond à aucun  CellContent
	*/
	public CellContent getCellByID(String code){
		return this.cells.get(code);
	}
}