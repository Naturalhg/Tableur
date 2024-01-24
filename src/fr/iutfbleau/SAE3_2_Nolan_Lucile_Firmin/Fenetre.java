package fr.iutfbleau.SAE3_2_Nolan_Lucile_Firmin;
import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
* La classe Fenêtre représente une fenêtre d'une applicaton de tableur.
* Elle affiche une Entrée en haut et un tableau de Cellules en dessous.
* Le contenu des Cellules est géré par des CellListeners et des CellContents, contenus dans un Tableau.
* L'Entrée est gérée par un EntreeListener.
* Elle est activée quand l'utilisateur sélectionne une Cellule, ce qui lui donne accès à la formule de la Cellule.
*
* @see Cellule
* @see Tableau
* @see Entree
*/
public class Fenetre extends JFrame {
	/**
	* Le champ de texte en haut de la fenetre
	*/
	private Entree field = new Entree();

	
	// Les trois couleurs utilisées dans l'application	
	
	/**
	* Un violet clair pour la séléction de texte
	*/
	public final static Color VIOLET_PASTEL = new Color(226,199,255);
	
	/**
	* Un violet normal pour mettre en avant un composant graphique
	*/
	public final static Color VIOLET = new Color(172,123,227);

	/**
	* Un violet foncé pour du texte
	*/
	public final static Color VIOLET_FONCE = new Color(101,55,154);

	
	/**
	* Crée une fenetre avec une Entree en haut 
	* et un tableau de row par col Cellules en bas, 
	* gérées par les CellsContents d'un Tableau.
	*
	* @param t le Tableau regroupant les contenus des Cellules
	* @param row le nombre de lignes du tableur
	* @param col le nombre de colonnes du tableur
	*/
	public Fenetre(Tableau t, int row, int col){
		// Réglages de la fenêtre
		super("Petit tableur");
		this.setLayout(new GridBagLayout());
		this.setSize(500, 500);
		this.setLocation(0,0);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// On crée la grille de cellules
		JPanel pane = this.setCellules(t, row, col);

		// On ajoute le champ de texte et la grille à la fenêtre
		this.addComponents(pane);
	}


	/**
	* Renvoie le champ de texte en haut de la fenêtre
	*
	* @return element Entree en haut de la fenêtre 
	*/
	public Entree getEntree(){
		return this.field;
	}


	/**
	* Crée row x col Celulles
	* et renvoie un JPanel conenant ces cellules sous forme de grille 
	* avec en-têtes de ligne et de colonne
	*
	* @param t le Tableau regroupant les contenus des Cellules
	* @param row le nombre de lignes du tableur
	* @param col le nombre de colonnes du tableur
	* @return la grille des Cellules crées
	* @throws 
	*/
	private JPanel setCellules(Tableau t, int row, int col){
		if(col > 26){
			throw new IllegalArgumentException("Col cannot be > 26");
		}
		if(row < 1 || col < 1){
			throw new IllegalArgumentException("Size values cannot be less than 1");
		}
		
		//Création de la grille qui contiendra les cellules
		JPanel pane = new JPanel(new GridLayout(row+1,col+1,0,0));
		
		// Création de variables pour la suite
		Cellule cellule;
		JLabel l;
		int cpt = 0;
		CellListener listener = new CellListener(this.field);

		// Ajout d'un espace vide en haut à gauche
		pane.add(new JLabel(""));

		// Ajout des lettres en-têtes de colonne
		for(int j = 0; j<col; j++){
			l = new JLabel(String.valueOf((char)(j + 65)), SwingConstants.CENTER);
			l.setForeground(Fenetre.VIOLET_FONCE);
			pane.add(l);
		}

		// Parcours de la grille
		for(int i = 1; i<10; i++){
			for(int j = 0; j<9; j++){
				// Ajout des chiffres en-têtes de ligne 
				if(j==0){
					l = new JLabel((i)+"", SwingConstants.CENTER);
					l.setForeground(new Color(101,55,154));
					pane.add(l);
				}

				// Création des cellules avec leur Listener et ajout au tableau des cellules
				cellule = new Cellule(t.getCellByID(String.valueOf((char)(j + 65)) + i));
				cellule.addActionListener(listener);
				pane.add(cellule);
			}
		}
		return pane;
	}


	/**
	* Ajoute le champ de texte et la grille de Cellules à la fenêtre
	*
	* @param pane la grille de cellules à afficher
	*/
	private void addComponents(JPanel pane){
		GridBagConstraints gbc = new GridBagConstraints();

		// Ajout de la zone de texte en haut, centrée
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.weightx = 0f;
		gbc.weighty = 0f;
		gbc.insets = new Insets(5,5,5,5);
		this.add(this.field, gbc);

		// Ajout de la grille de Cellules centrée
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.weightx = 0.1f;
		gbc.weighty = 0.1f;
		gbc.insets = new Insets(5,5,5,5);
		this.add(pane, gbc);
	}
}
