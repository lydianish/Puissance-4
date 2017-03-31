import java.util.*;

public class Plateau{

	private int joueur1;			//joueur 1
	private int joueur2;			//joueur 2
	private int joueurCourant;		//joueurCourant
	private int[][] grille;			//grille du jeu
	private int[] niveauCol;		//tableau des niveaux pour chaque colonne
	private boolean pret = false;		//booleen permettant la transition entre les deux methodes synchronisées avance et imprime
	private int ligneCourante = 0, colonneCourante = 0;	//indice de la ligne et de la colonne courante
	private int ligneGagnante = 0, colonneGagnante = 0;	//indice de la ligne et de la colonne gagnant
	private String directionGagnante = "";			//direction dans laquelle le joueur a gagne si c'est le cas, l, c, d1 ou d2		

	//CONSTRUCTEUR
	public Plateau(){
		joueur1  = 1;			//joueur 1
		joueur2  = 2;			//joueur 2
		joueurCourant  = joueur1;	//joueurCourant
		grille = new int[6][7];		//grille du jeu
		niveauCol = new int[7];		//tableau des niveaux pour chaque colonne
	}

	//ACCESSEURS & MODIFICATEURS & METHODES
	/** Methode qui renvoie le joueur courant 
	* @return le numero du joueur courant (1 pour le joueur1 & 2 pour le joueur2)
	*/
	public int getJoueurCourant(){
		return joueurCourant;
	}

	/** Methode qui modifie le joueur courant, j devient le joueur courant 
	* @param j colonne à jouer 
	*/
	public void setJoueurCourant(int j){
		joueurCourant = j;
	}

	/** Methode qui renvoie le joueur suivant 
	* @return le numero du joueur suivant
	*/
	public int getJoueurSuivant(){
		int js = 0;
		if(joueurCourant == joueur1){
			js = joueur2;
		}else{
			if(joueurCourant == joueur2){
				js = joueur1;
			}
		}
		return js;
	}


	//JOUEURS
	/** Methode qui fait passer la main au joueur suivant 
	*/
	public void joueurSuivant(){
		if(joueurCourant == joueur1){
			joueurCourant = joueur2;
		}else{
			if(joueurCourant == joueur2){
				joueurCourant = joueur1;
			}
		}
	}

	//NIVEAUX DES COLONNES
	/** Methode qui renvoie le tableau des niveaux de colonnes
	* @return le tableau contenant les niveaux pour chaque colonne 
	*/
	public int[] getNiveauCol(){
		return niveauCol;
	}

	/** Methode qui renvoie le niveau de la colonne i  
	* @param i indice de la colonne dont on veut connaitre le niveau(le nombre de pions qu'il contient)
	* @return lniveau de la colonne i 
	*/
	public int getNiveauCol(int i){
		return niveauCol[i];
	}

	/** Methode qui modifie le niveau de la colonne en val  
	* @param i la colonne à modifier
	* @param val le nombre de pion qu'on veut affecter à la ligne i 
	*/
	public void setNiveauCol(int i, int val){
		niveauCol[i] = val;
	}

	/** Methode renvoie vrai si la colonne i est pleine  
	* @param i la colonne à tester 
	* @return vrai si la colonne i est pleine
	*/
	public boolean estColPleine(int i){
		return niveauCol[i]==6;
	}


	//GRILLE
	/** Methode qui renvoie la grille de jeu 
	* @return la grille de jeu 
	*/
	public int[][] getGrille(){
		return grille;
	}
	
	/** Methode qui modifie la grille en g  
	* @param g la grille à copier dans celle du plateau 
	*/
	public void setGrille(int[][] g){
		int d1 = g.length, d2 = g[0].length;
		grille = new int[d1][d2];
		for(int i = 0; i <= d1-1; i++){
			for(int j= 0; j <= d2-1; j++){
				grille[i][j] = g[i][j];
			}
		}
		
	}
	/** Methode qui renvoie le nombre de lignes de la grille 
	* @return le nombre de lignes de la grille
	*/
	public int getLigne(){
		return grille.length;
	}

	/** Methode qui renvoie le nombre de colonnes de la grille 
	* @return le nombre de colonnes de la grille
	*/
	public int getColonne(){
		return grille[0].length;
	}

 	/** Methode qui renvoie vrai si la grille est pleine  
	* @return vrai si le plateau est plein
	*/
	public boolean estPlein(){
		boolean plein = true;
		for(int i = 5; i >= 0; i--){
			for(int j = 0; j <= 6; j++){
				plein = plein && (grille[i][j] != 0);
				if(plein == false){
					return plein;
				}
			}
		}
		return plein;
	}

	/** Methode qui initialise le plateau  
	*/
	public void initialise(){
		for(int i = 5; i >= 0; i--){
			for(int j = 0; j <= 6; j++){
				grille[i][j] = 0;
			}
		}
		niveauCol = new int[7];
	}

	/** Methode qui renvoie vrai si la caseIJ  est occupée  
	* @param i ligne de la case à tester
	* @param j colonne de la case à tester
	* @return vrai si la Case ij est occupée
	*/
	public boolean estOccupee(int i, int j){
		return (grille[i][j] != 0) ;
	}

	/** Methode qui renvoie si elle existe le numero du pion qui est dans la caseIJ 
	* @param i ligne de la case du pion à retourner
	* @param j colonne de la case du pion à retourner 
	* @return le pion contenu dans la Case ij si elle existe
	*/
	public int pion(int i, int j){
		return grille[i][j];
	}


	//--- Modificateurs du champs PRET ---
	public boolean getPret(){
		return pret;
	}

	public void setPret(boolean b){
		pret = b;
	}


	//--- Modificateurs du champs LIGNECOURANTE ---
	public int getLigneCourante(){
		return ligneCourante;
	}

	public void setLigneCourante(int b){
		ligneCourante = b;
	}


	//--- Modificateurs du champs COLONNECOURANTE ---
	public int getColonneCourante(){
		return colonneCourante;
	}

	public void setColonneCourante(int b){
		colonneCourante = b;
	}

	//--- Modificateurs du champs LIGNEGAGNANTE ---
	public int getLigneGagnante(){
		return ligneGagnante;
	}

	public void setLigneGagnante(int b){
		ligneGagnante = b;
	}


	//--- Modificateurs du champs COLONNEGAGNANTE ---
	public int getColonneGagnante(){
		return colonneGagnante;
	}

	public void setColonneGagnante(int b){
		colonneGagnante = b;
	}

	//--- Modificateurs du champs DIRECTIONGAGNANTE ---
	public String getDirectionGagnante(){
		return directionGagnante;
	}

	public void setDirectionGagnante(String b){
		directionGagnante = b;
	}


	//LIGNE
	/** Methode qui renvoie le nombre de pion voisins de celui du joueur de la case i,j sur la ligne
	* @param i ligne de la case à jouer
	* @param j colonne de la case à jouer 
	* @return le nombre de pion voisin du pion de la case ij  sur la ligne
	*/
	public int avanceLigne(int i, int j){
		int nb = 0;
		if(grille[i][j] != 0){
			int j1 = j + 1;
			boolean rep = true;
			while((j1 <= 6) && (grille[i][j1] != 0)){
				rep = rep && (grille[i][j1] == grille[i][j]);
				j1++;
				if(rep){
					nb++;
				}else{
					return nb;
				}
			}
			return nb;
		}else{
			return nb;
		}
	}


	public int reculeLigne(int i, int j){
		int nb = 0;
		if(grille[i][j] != 0){
			int j1 = j - 1;
			boolean rep = true;
			while((j1 >= 0) && (grille[i][j1] != 0)){
				rep = rep && (grille[i][j1] == grille[i][j]);
				j1--;
				if(rep){
					nb++;
				}else{
					return nb;
				}
			}
			return nb;
		}else{
			return nb;
		}
	}

	//COLONNE
	/** Methode qui renvoie le nombre de pion du joueur de la case i,j pour le joueur courant sur la colonne 
	* @param i ligne de la case à jouer
	* @param j colonne de la case à jouer
	* @return le nombre de pion voisin du pion de la case ij  sur la colonne
	*/
	public int avanceColonne(int i, int j){
		int nb = 0;
		if(grille[i][j] != 0){
			int i1 = i + 1;
			boolean rep = true;
			while((i1 <= 5) && (grille[i1][j] != 0)){
				rep = rep && (grille[i1][j] == grille[i][j]);
				i1++;
				if(rep){
					nb++;
				}else{
					return nb;
				}
			}
			return nb;
		}else{
			return nb;
		}
	}


	public int reculeColonne(int i, int j){
		int nb = 0;
		if(grille[i][j] != 0){
			int i1 = i - 1;
			boolean rep = true;
			while((i1 >= 0) && (grille[i1][j] != 0)){
				rep = rep && (grille[i1][j] == grille[i][j]);
				i1--;
				if(rep){
					nb++;
				}else{
					return nb;
				}
			}
			return nb;
		}else{
			return nb;
		}
	}

	//DIAGONALE
	/** Methode qui renvoie le nombre de pion du joueur de la case i,j pour le joueur courant sur la diagonale 
	* @param i ligne de la case à jouer
	* @param j colonne de la case à jouer
	* @return le nombre de pion voisin du pion de la case ij  sur les deux diagonales
	*/
	//1ERE DIAGONALE (coin gauche superieur (0,0) au coin droit inferieur (5,6))
	public int avanceDiag1(int i, int j){
		int nb = 0;
		if(grille[i][j] != 0){
			int i1 = i + 1;
			int j1 = j + 1;
			boolean rep = true;
			while((i1 <= 5) && (j1 <= 6) && (grille[i1][j1] != 0)){
				rep = rep && (grille[i1][j1] == grille[i][j]);
				i1++;
				j1++;
				if(rep){
					nb++;
				}else{
					return nb;
				}
			}
			return nb;
		}else{
			return nb;
		}
	}


	public int reculeDiag1(int i, int j){
		int nb = 0;
		if(grille[i][j] != 0){
			int i1 = i - 1;
			int j1 = j - 1;
			boolean rep = true;
			while((i1 >= 0) && (j1 >= 0) && (grille[i1][j1] != 0)){
				rep = rep && (grille[i1][j1] == grille[i][j]);
				i1--;
				j1--;
				if(rep){
					nb++;
				}else{
					return nb;
				}
			}
			return nb;
		}else{
			return nb;
		}
	}

	//2EME DIAGONALE (coin gauche inferieur (5,0) au coin droit superieur (0,6))
	public int avanceDiag2(int i, int j){
		int nb = 0;
		if(grille[i][j] != 0){
			int i1 = i - 1;
			int j1 = j + 1;
			boolean rep = true;
			while((i1 >= 0) && (j1 <= 6) && (grille[i1][j1] != 0)){
				rep = rep && (grille[i1][j1] == grille[i][j]);
				i1--;
				j1++;
				if(rep){
					nb++;
				}else{
					return nb;
				}
			}
			return nb;
		}else{
			return nb;
		}
	}


	public int reculeDiag2(int i, int j){
		int nb = 0;
		if(grille[i][j] != 0){
			int i1 = i + 1;
			int j1 = j - 1;
			boolean rep = true;
			while((i1 <= 5) && (j1 >= 0) && (grille[i1][j1] != 0)){
				rep = rep && (grille[i1][j1] == grille[i][j]);
				i1++;
				j1--;
				if(rep){
					nb++;
				}else{
					return nb;
				}
			}
			return nb;
		}else{
			return nb;
		}
	}

	/** Methode qui fait avancer le jeu 
	* @param i colonne à jouer
	*/
	public synchronized void avance(int i) throws InterruptedException {
		if(!pret){
			try{
				joue(i);
			}catch(ColonnePleineException e){
				System.out.println(e.getMessage());
			}
			pret = true;
			notifyAll();
		}else{
			wait();
		}
		
	}

	/** Methode qui afficle le jeu 
	*/
	public synchronized void imprime() throws InterruptedException{
		if(pret){
			String g = "-- ";
			for(int k= 0; k < getColonne(); k++){
				System.out.print(g);
			}
			System.out.println();
			for(int i = 0; i < getLigne(); i++){
				for(int j= 0; j < getColonne(); j++){
					if(grille[i][j] == joueur1){
						System.out.print("" + joueur1);
					}else{
						if(grille[i][j] == joueur2){
							System.out.print("" + joueur2);
						}else{
							System.out.print(" ");
						}
					}
					System.out.print(" |");
				}
				System.out.print("\n");
				for(int h = 0; h < getColonne(); h++){
					System.out.print(g);
				}
				System.out.print("\n");
			}
			pret = false;
			notifyAll();
		}else{
			wait();
		}
	}

	/** Methode qui modifie le plateau pour que le joueur courant ait joué dans le plateau à la colonne i
	* @param i colonne de la case à jouer
	* @return le nombre de pion dans la colonne i
	*/
	public int joue(int i) throws ColonnePleineException{
		if(!estColPleine(i)){
			setNiveauCol(i, getNiveauCol(i)+1);
			grille[5 - (getNiveauCol(i)-1)][i] = joueurCourant;
			ligneCourante = 6 - getNiveauCol(i);
			colonneCourante = i;
			//System.out.println("ligne et colonne courantes (" + ligneCourante + " " + colonneCourante + ")");
			joueurSuivant();
		}else{
			throw new ColonnePleineException("La colonne : " + i + " est déjà pleine");
		}
		return getNiveauCol(i);
	}


	/** Methode qui rend 1 si le joueur courant a gagne, -1 sinon
	* regarde si le dernier pion inserer a fait gagne le joueur courant
	* @return i si le joueur courant a gagné, -1  sinon 
	*/
	public int gagne(){
		int res = -1;
		int i = ligneCourante;
		int k = colonneCourante;
		int g = grille[i][k];
		if((1 + avanceLigne(i,k) + reculeLigne(i,k)) >= 4){
			//System.out.println("nb joueur courant ligne : " + (1 + avanceLigne(i,k) + reculeLigne(i,k)));
			ligneGagnante = i;
			colonneGagnante = k;
			directionGagnante = "l";
			return g;
		}else{
			if((1 + avanceColonne(i,k) + reculeColonne(i,k)) >= 4){
				//System.out.println("nb joueur courant colonne : " + (1 + avanceColonne(i,k) + reculeColonne(i,k)));
				ligneGagnante = i;
				colonneGagnante = k;
				directionGagnante = "c";
				return g;
			}else{
				if((1 + avanceDiag1(i,k) + reculeDiag1(i,k)) >= 4){
					//System.out.println("nb joueur courant diagonale1 : " + (1 + avanceDiag1(i,k) + reculeDiag1(i,k)));
					ligneGagnante = i;
					colonneGagnante = k;
					directionGagnante = "d1";
					return g;
				}else{
					if((1 + avanceDiag2(i,k) + reculeDiag2(i,k)) >= 4){
						//System.out.println("nb joueur courant diagonale2 : " + (1 + avanceDiag2(i,k) + reculeDiag2(i,k)));
						ligneGagnante = i;
						colonneGagnante = k;
						directionGagnante = "d2";
						return g;
					}
				}
			}
		}
		return res;
	}
	

	/** Methode qui renvoie le vecteur gagnant (La caseIJ a permit au joueur courant de gagné la manche) 
	* @return le vecteur gagnant(pour le joueur qui a gagné)
	*/
	public LinkedList<Integer> vecteurGagnant(){
		LinkedList<Integer> vg = new LinkedList<Integer>();
		vg.add(ligneGagnante);
		vg.add(colonneGagnante);		//ajout de la case gagnante i et j

		int g = grille[ligneGagnante][colonneGagnante];

		if(directionGagnante.equals("l")){		//gain selon la ligne
			//on avance d'abord
			int jla = colonneGagnante + 1;
			while((jla <= 6) && (grille[ligneGagnante][jla] == g) && (vg.size() <= 8)){
				vg.addLast(ligneGagnante);
				vg.addLast(jla);	//ajout du voisin de la case gagnante i et j
				jla = jla + 1;
			}
			if(vg.size() <= 8){
				//on recule
				int jlr = colonneGagnante - 1;
				while((jlr >= 0) && (grille[ligneGagnante][jlr] == g) && (vg.size() <= 8)){
					vg.addFirst(jlr);		//ajout du voisin de la case gagnante i et j
					vg.addFirst(ligneGagnante);
					jlr = jlr - 1;
				}
			}
		}else{
			if(directionGagnante.equals("c")){		//gain selon la colonne
				//on avance d'abord
				int ila = ligneGagnante + 1;
				while((ila <= 5) && (grille[ila][colonneGagnante] == g) && (vg.size() <= 8) ){
					vg.addLast(ila);
					vg.addLast(colonneGagnante);			//ajout du voisin de la case gagnante i et j
					ila = ila + 1;
				}
				if(vg.size() <= 8){
					//on recule
					int ilr = ligneGagnante - 1;
					while((ilr >= 0) && (grille[ilr][colonneGagnante] == g) && (vg.size() <= 8)){
						vg.addFirst(colonneGagnante);
						vg.addFirst(ilr);		//ajout du voisin de la case gagnante i et j
						ilr = ilr - 1;
					}
				}
			}else{
				if(directionGagnante.equals("d1")){		//gain selon la diagonale 1
					//on avance d'abord
					int ila = ligneGagnante + 1;
					int jla = colonneGagnante + 1;
					while((ila <= 5) && (jla <= 6) && (grille[ila][jla] == g) && (vg.size() <= 8)){
						vg.addLast(ila);
						vg.addLast(jla);	//ajout du voisin de la case gagnante i et j
						ila = ila + 1;
						jla = jla + 1;
					}
					if(vg.size() <= 8){
						//on recule
						int ilr = ligneGagnante - 1;
						int jlr = colonneGagnante - 1;
						while((ilr >= 0) && (jlr >= 0) && (grille[ilr][jlr] == g) && (vg.size() <= 8)){
							vg.addFirst(jlr);
							vg.addFirst(ilr);		//ajout du voisin de la case gagnante i et j
							ilr = ilr - 1;
							jlr = jlr - 1;
						}
					}
				}else{
					if(directionGagnante.equals("d1")){		//gain selon la diagonale 2
						//on avance d'abord
						int ila = ligneGagnante - 1;
						int jla = colonneGagnante + 1;
						while((ila >= 0) && (jla <= 6) && (grille[ila][jla] == g) && (vg.size() <= 8)){
							vg.addLast(ila);
							vg.addLast(jla);		//ajout du voisin de la case gagnante i et j
							ila = ila - 1;
							jla = jla + 1;
						}
						if(vg.size() <= 8){
							//on recule
							int ilr = ligneGagnante + 1;
							int jlr = colonneGagnante - 1;
							while((ilr <= 5) && (jlr >= 0) && (grille[ilr][jlr] == g) && (vg.size() <= 8)){
								vg.addFirst(jlr);
								vg.addFirst(ilr);		//ajout du voisin de la case gagnante i et j
								ilr = ilr + 1;
								jlr = jlr - 1;
							}
						}
					}
				}
			}
		}
		return vg;
	}

	/** Methode qui affiche le vecteur gagnant 
	*/
	public void afficheVecteurGagnant(){
		String s = "";
		LinkedList<Integer> v = vecteurGagnant();
		int i = 0;
		while(i < v.size()){
			s = s + "(" + v.get(i) + ", " + v.get(i+1) + ") ";
			i = i + 2;
		}
		System.out.println("Le vecteur gagnant est : " + s);
	}
	
}
