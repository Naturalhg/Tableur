package fr.iutfbleau.SAE3_2_Nolan_Lucile_Firmin;

/**
* La classe Maillon représente un élément d'un liste chaînée.
* Chaque Maillon connaît le Maillon qui le suit dans la liste, ou null s'il est le dernier élément.
* Il est possible de changer ou récupérer la valeur associée à un Maillon,
* ou le Maillon suivant un Maillon.
*
* @see File
*/
public class Maillon{
	/**
	* Le Maillon suivant ce Maillon
	*/
	private Maillon next = null;

	/**
	* La valeur stockée dans ce Maillon
	*/
	private String val;


	/**
	* Crée un nouveau Maillon contenant la valeur s
	*
	* @param s la valeur stockée dans ce Maillon
	*/
	public Maillon(String s){
		this.val = s;
	}


	/**
	* Ajoute un Maillon m après ce Maillon
	*
	* @param m le Maillon suivant ce Maillon
	*/
	public void setNext(Maillon m){
		this.next = m;
	}

	
	/**
	* Renvoie un Maillon suivant ce Maillon
	*
	* @return le Maillon suivant ce Maillon
	*/
	public Maillon getNext(){
		return this.next;
	}


	/**
	* Change la valeur stockée dans ce Maillon
	*
	* @param s la valeur à stocker dans ce Maillon
	*/
	public void setVal(String s){
		this.val = s;
	}


	/**
	* Renvoie la valeur stockée dans ce Maillon
	*
	* @return la valeur stockée dans ce Maillon
	*/
	public String getVal(){
		return this.val;
	}
}