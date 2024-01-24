package fr.iutfbleau.SAE3_2_Nolan_Lucile_Firmin;
import java.util.*;

/**
* La classe CellContent représente le contenu d'une cellule de tableur, et calcule le résultat de la formule grâce à un AbstractSyntaxTree.
*
* @see AbstractSyntaxTree
* @see Tableau
*/

public class CellContent extends Observable implements Observer{

	/**
	* L'arbre de syntaxe abstraite pour recalculer facilement le résultat de la formule 
	*/
	private AbstractSyntaxTree arbre = null;
	
	/**
	* La formule entrée par l'utilisateur
	*/
	private String formule = "";

	/**
	* Le tableau de cellules dont fait partie ce CellContent
	*/
	private Tableau tab;

	/**
	* La liste des CellContent observés par ce CellContent
	*/
	private List<CellContent> observing = new LinkedList<CellContent>();


	/**
	* Crée un nouveau CellContent
	*
	* @param t le tableau dont fait partie le CellContent
	*/
	public CellContent(Tableau t){
		this.tab = t;
	}

	
	/**
	* Stocke une formule en notation préfixe et la calcule grace à un arbre
	*
	* @param calcul la formule à stocker et analyser
	*/
	public void setFormule(String calcul) {
		this.formule = calcul;
		this.setArbre();
	}

	
	/**
	* Renvoie la formule contenue dans la cellule
	*
	* @return la formule de la cellule
	*/
	public String getFormule() {
		return this.formule;
	}

	
	/**
	* Renvoie le résultat de la formule de la cellule, 
	* qui est soit un nombre soit une erreur du type "!SYNTAX" ou "!CALCUL"
	*
	* @return le résultat de la formule
	*/
	public String getResultat() {
		// Si l'arbre de formule est vide, on renvoie une chaine de caractères vide
		if(this.arbre == null){
			return "";
		}

		// Sinon on renvoie le resultat de l'arbre
		return this.arbre.getResultat();
	}

	
	/**
	* Remplace l'arbre à partir de la formule,
	* récupère le résultat de l'abre pour en faire le résultat
	* et notifie les observer
	*/
	public void setArbre() {
		// On récupère l'ancien résultat
		String resAvant = this.getResultat();

		// On enlève tous les CellContent observés par ce CellContent
		this.clearObserving();
		
		// On crée le nouvel arbre avec une File tirée de la formule contenue
		this.arbre = new AbstractSyntaxTree(this, new File(this.formule));

		// On récupère le nouveau résultat
		String resApres = this.getResultat();

		// Si le résultat a changé et qu'on a des observers, on les notifie
		if(!resAvant.equals(resApres) && this.countObservers()>0){
			this.setChanged();
			this.notifyObservers();
		}
	}


	/**
	* Enlève tous les CellContent observés par ce CellContent
	*/
	public void clearObserving(){
		// On retire le CellContent de la liste des Observers des CellContents observés
		for(CellContent cc : this.observing){
			cc.deleteObserver(this);
		}

		// On vide la liste des CellContents observés
		this.observing.clear();
	}

	
	/**
	* Si une modification a eu lieu sur un CellContent 
	* référencé dans la formule, on recalcule le résultat de ce CellContent
	* et on notifie les
	*
	* @param o l'Observable qui a envoyé la notification
	* @param arg un argument passé à la méthode notifyObservers
	*/
	public void update(Observable o, Object arg){
		// On récupère l'ancien résultat
		String resAvant = this.getResultat();

		// On recalcule la formule via l'arbre
		this.arbre.recalculate();
		
		// On récupère le nouveau résultat
		String resApres = this.getResultat();

		// Si le résultat a changé et qu'on a des observers, on les notifie
		if(!resAvant.equals(resApres) && this.countObservers()>0){
			this.setChanged();
			this.notifyObservers();
		}
	}


	/**
	* Renvoie vrai si ce CellContent, ou l'un des CellContent qu'elle référence, 
	* référence directement ou indirectement du CellContent passé en argument
	*
	* @param cellc le CellContent dont on veut savoir si ce CellContent dépend
	* @return true si ce CellContent dépend de c et false sinon
	*/
	public boolean dependsOn(CellContent cellc) {
		// Si ce CellContent n'observe aucun CellContent, on renvoie false
		if(this.observing.size() == 0) {
			return false;
		}
		
		// On parcourt les CellContent observé par ce CellContent
		for (CellContent obs : this.observing) {
			// On vérifie si cellc en fait partie
			if(obs == cellc) {
				// On renvoie true si c'est le cas
				return true;
			}
		}

		// On parcourt les CellContent observé par ce CellContent
		for(CellContent obs : this.observing) {
			// On vérifie s'ils dépendent de cellc
			if(obs.dependsOn(cellc)){
				// On renvoie true si c'est le cas
				return true;
			}
		}

		// Sinon renvoie false
		return false;
	}


	/**
	* Renvoie le Tableau dont fait partie ce CellContent
	*
	* @return le Tableau dont ce CellContent fait partie
	*/
	public Tableau getTableau(){
		return this.tab;
	}


	/**
	* Ajoute ce CellContent aux observers de cellc, s'il n'est pas égal à cellc
	*
	* @param cellc le CellContent à observer
	* @throws IllegalStateException si cet ajout crée une dépendence circulaire
	* @throws IllegalArgumentException si ce CellContent est égal à cellc 
	*/
	public void addObserving(CellContent cellc){
		// Si on n'observe pas déjà cellc
		if(!this.observing.contains(cellc)){
			
			// Si cellc est égal à ce CellContent, on lance une erreur de syntaxe
			if(cellc.equals(this)){
				throw new IllegalArgumentException("!SYNTAX");
			}
			
			// Sinon on commence à observer c
			this.observing.add(cellc);
			cellc.addObserver(this);
		}

		// Si c nous observe déjà, on lance une erreur de syntaxe
		if(cellc.dependsOn(this)){
			throw new IllegalStateException("!SYNTAX");
		}		
	}
	
}