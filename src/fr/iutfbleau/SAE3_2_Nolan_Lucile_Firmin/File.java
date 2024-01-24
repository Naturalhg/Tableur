package fr.iutfbleau.SAE3_2_Nolan_Lucile_Firmin;
import java.util.NoSuchElementException;

/**
* La classe File permet de contenir plusieurs String ordonnés de manière LIFO.
* Il est possible d'ajouter un objet au début de la File,
* de supprimer et récupérer le dernier objet, et de vérifier si la File est vide.
*
* On crée une File à partir d'une formule dont les composant sont séparés par des espaces.
*
* @see Maillon
*/
public class File{
	/**
	* Le plus récent Maillon de la File
	*/
	private Maillon youngest = null;
	
	/**
	* Le plus ancien Maillon de la File, donc le prochain qui sera retiré
	*/
	private Maillon oldest = null;

	/**
	* Crée un File à partir d'un calcul
	*
	* Chaque élément du texte est considéré comme un symbole (-, /, * ou +) 
	* ou, s'il n'est pas un symbole, comme un terme.
	* Un nombre décimal écrit avec une virgule au lieu d'un point sera transformé en nombre syntaxiquement correct
	*
	* @param formule la formule à mettre dans une file
	*/
	public File(String formule){
		// On crée des variables pour stocker les éléments du calcul
		String terme = "";
		char c;

		// On parcourt la formule caractère par caractère
		for(int i = 0; i<formule.length(); i++) {
			c = formule.charAt(i);

			// Si le caractère n'est pas un espace
			if(c != ' '){
				// Si le caractère est une virgule
				if(c == ','){
					// On le transforme en point
					terme = terme + '.';
				}
				// Sinon
				else{
					// On ajoute le caractère au prochain terme
					terme = terme + c;
				}
			}

			// Si le caractère est un espace
			else{
				// Si le terme n'est pas vide, on l'enfile
				// et on commence un nouveau terme
				if(!terme.equals("")){
					this.enqueue(terme);
					terme = "";
				}
			}
		}

		// Une fois le calcul parcouru, on ajoute le dernier terme 
		// s'il n'est pas vide
		if(!terme.equals("")){
			this.enqueue(terme);
		}
	}
	
	/**
	* Retire le dernier objet de la file et renvoie sa valeur
	*
	* @return la valeur du dernier objet de la file 
	* @throws NoSuchElementException quand on essaie de dequeue une File vide
	*/
	public String dequeue() throws NoSuchElementException{
		// si la File est vide on lance une exception
		if(this.isEmpty()){
			throw new NoSuchElementException();
		}
		
		// sinon on retire le dernier élément
		String s = this.oldest.getVal(); 
		this.oldest = this.oldest.getNext();

		// et on renvoie sa valeur
		return s;
	}

	
	/**
	* Ajoute une valeur au début de la file
	*
	* @param s la valeur à ajouter
	*/
	public void enqueue(String s) {
		// On crée le nouveau maillon à ajouter
		Maillon m = new Maillon(s);

		// Si le Maillon est ajouté à une File vide, c'est aussi le plus ancien
		if(this.isEmpty()){
			this.oldest = m;
		}
		// Sinon c'est le Maillon après l'ancien Maillon le plus jeune
		else{
			this.youngest.setNext(m);
		}
		
		// Le nouveau maillon devient le premier de la file
		this.youngest = m;
	}

	
	/**
	* Permet de savoir si la file est vide
	*
	* @return true si la file est vide et false sinon
	*/
	public boolean isEmpty() {
		return this.oldest == null;
	}
}