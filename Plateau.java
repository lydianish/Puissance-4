import java.awt.*;
import java.util.*;

public class Plateau
{

	private static final int joueur1 = 1;			//joueur 1
	private static final int joueur2 = 2;			//joueur 2
    private String couleur1 = "JAUNE" ;
    private String couleur2 = "ROUGE" ;
	private int joueurCourant;		//joueurCourant
	private int[][] grille;			//grille du jeu
	private int[] niveauCol;		//tableau des niveaux pour chaque colonne
	private boolean pret = false;		//booleen permettant la transition entre les deux methodes synchronisées avance et imprime
	private int ligneCourante = 0, colonneCourante = 0;	//indice de la ligne et de la colonne courante
	private int ligneGagnante = 0, colonneGagnante = 0;	//indice de la ligne et de la colonne gagnant
	private String directionGagnante = "";			//direction dans laquelle le joueur a gagne si c'est le cas, l, c, d1 ou d2		

    private int nbl = 6;
    private int nbc = 7;
	//Constructeur
	public Plateau(){
		//joueur1  = 1;			//joueur 1
		//joueur2  = 2;			//joueur 2
		joueurCourant  = joueur1;	//joueurCourant
		grille = new int[nbl][nbc];		//grille du jeu
		niveauCol = new int[nbc];		//tableau des niveaux pour chaque colonne
	}

	//Accesseurs
	//renvoie le joueur courant
	public int getJoueurCourant(){
		return joueurCourant;
	}

	//modifie le joueur courant, j devient le joueur courant
	public void setJoueurCourant(int j){
		joueurCourant = j;
	}

	//renvoie le joueur suivant
	public int getJoueurSuivant(){
        return joueur1 + joueur2 - joueurCourant;
	}

	//Methodes
	//JOUEURS
	//fait passer la main au joueur suivant
	public void joueurSuivant(){
		joueurCourant = joueur1 + joueur2 - joueurCourant;
	}

	//NIVEAUX DES COLONNES
	//renvoie le tableau des niveaux de colonnes
	public int[] getNiveauCol(){
		return niveauCol;
	}
	//renvoie le niveau de la colonne i 
	public int getNiveauCol(int i){
		return niveauCol[i];
	}
	//modifie le niveau de la colonne en val
	public void setNiveauCol(int i, int val){
		niveauCol[i] = val;
	}
	//renvoie vrai si la colonne i est pleine
	public boolean estColPleine(int i){
		return niveauCol[i]==nbc-1;
	}


	//GRILLE
	//renvoie la grille de jeu
	public int[][] getGrille(){
		return grille;
	}
	//modifie la grille en g
	public void setGrille(int[][] g){
		int d1 = g.length, d2 = g[0].length;
		grille = new int[d1][d2];
		for(int i = 0; i <= d1-1; i++){
			for(int j= 0; j <= d2-1; j++){
				grille[i][j] = g[i][j];
			}
		}
		
	}
	//revoie le nombre de lignes de la grille
	public int getLigne(){
		return grille.length;
	}
	//revoie le nombre de colonnes de la grille
	public int getColonne(){
		return grille[0].length;
	}
	//renvoie vrai si la grille est pleine
	public boolean estPlein(){
		boolean plein = true;
		for(int i = nbl-1; i >= 0; i--){
			for(int j = 0; j <= nbc-1; j++){
				plein = plein && (grille[i][j] != 0);
				if(plein == false){
					return plein;
				}
			}
		}
		return plein;
	}
	//initialise le plateau
	public void initialise(){
		for(int i = nbl-1; i >= 0; i--){
			for(int j = 0; j <= nbc-1; j++){
				grille[i][j] = 0;
			}
		}
		niveauCol = new int[7];
	}
	//renvoie vrai si la caseIJ  est occupée
	public boolean estOccupee(int i, int j){
		return (grille[i][j] != 0) ;
	}
	//renvoie si elle existe le numero du pion qui est dans la caseIJ
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
	//renvoie le nombre de pion voisins de celui du joueur de la case i,j sur la ligne
	public int avanceLigne(int i, int j){
		int nb = 0;
		if(grille[i][j] != 0){
			int j1 = j + 1;
			boolean rep = true;
			while((j1 <= nbc-1) && (grille[i][j1] != 0)){
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
	//renvoie le nombre de pion du joueur de la case i,j pour le joueur courant sur la colonne
	public int avanceColonne(int i, int j){
		int nb = 0;
		if(grille[i][j] != 0){
			int i1 = i + 1;
			boolean rep = true;
			while((i1 <= nbl-1) && (grille[i1][j] != 0)){
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
	//renvoie le nombre de pion du joueur de la case i,j pour le joueur courant sur la diagonale
	//1ERE DIAGONALE
	public int avanceDiag1(int i, int j){
		int nb = 0;
		if(grille[i][j] != 0){
			int i1 = i + 1;
			int j1 = j + 1;
			boolean rep = true;
			while((i1 <= nbl-1) && (j1 <= nbc-1) && (grille[i1][j1] != 0)){
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

	//2EME DIAGONALE
	public int avanceDiag2(int i, int j){
		int nb = 0;
		if(grille[i][j] != 0){
			int i1 = i - 1;
			int j1 = j + 1;
			boolean rep = true;
			while((i1 >= 0) && (j1 <= nbc-1) && (grille[i1][j1] != 0)){
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
			while((i1 <= nbl-1) && (j1 >= 0) && (grille[i1][j1] != 0)){
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

	//fait avancer le jeu
	/*public synchronized void avance(int i) throws InterruptedException {
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
*/
	//afficle le jeu
/*	public synchronized void imprime() throws InterruptedException{
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
*/
	//modifie le plateau pour que le joueur courant ait joué dans le plateau à la colonne i
/*	public int joue(int i) throws ColonnePleineException{
		if(!estColPleine(i)){
			setNiveauCol(i, getNiveauCol(i)+1);
			grille[nbl-1 - (getNiveauCol(i)-1)][i] = joueurCourant;
			ligneCourante = nbc-1 - getNiveauCol(i);
			colonneCourante = i;
			System.out.println("ligne et colonne courantes (" + ligneCourante + " " + colonneCourante + ")");
			joueurSuivant();
		}else{
			throw new ColonnePleineException("La colonne : " + i + " est déjà pleine");
		}
		return getNiveauCol(i);
	}
*/

	//rend i si le jouer i a gagne, -1 sinon
	/*
	// gagne(i) : 1ere VERSION //parcours toute la grille depuis le debut
	public int gagne(){
		int res = -1;
		for(int i = nbl-1; i>=0; i--){
			for(int k = 0; k <= nbc-1; k++){
				int g = grille[i][k];
				if((1 + avanceLigne(i,k) + reculeLigne(i,k)) >= 4){
					//System.out.println("nb joueur courant ligne : " + (1 + avanceLigne(i,k) + reculeLigne(i,k)));
					return g;
				}else{
					if((1 + avanceColonne(i,k) + reculeColonne(i,k)) >= 4){
						//System.out.println("nb joueur courant colonne : " + (1 + avanceColonne(i,k) + reculeColonne(i,k)));
						return g;
					}else{
						if((1 + avanceDiag1(i,k) + reculeDiag1(i,k)) >= 4){
							//System.out.println("nb joueur courant diagonale1 : " + (1 + avanceDiag1(i,k) + reculeDiag1(i,k)));
							return g;
						}else{
							if((1 + avanceDiag2(i,k) + reculeDiag2(i,k)) >= 4){
								//System.out.println("nb joueur courant diagonale2 : " + (1 + avanceDiag2(i,k) + reculeDiag2(i,k)));
								return g;
							}
						}
					}
				}
			}
		}
		return res;
	}
	*/
	
	//gagne(i) : 2eme VERSION // regarde si le dernier pion inserer a fait gagne le joueur courant
	/**/

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


	//La caseIJ a permit au joueur courant de gagné la manche
	//renvoie le vecteur gagnant
	/*
	//V1
	public LinkedList<ArrayList<Integer>> vecteurGagnant(){
		LinkedList<ArrayList<Integer>> vg = new LinkedList<ArrayList<Integer>>();
		ArrayList<Integer> c1 = new ArrayList<Integer>();
		//int l = nbc-1-ligneGagnante;
		c1.add(ligneGagnante);
		c1.add(colonneGagnante);
		vg.add(c1);		//ajout de la case gagnante i et j
		int g = grille[ligneGagnante][colonneGagnante];
		if(directionGagnante.equals("l")){		//gain selon la ligne
			//on avance d'abord
			int jla = colonneGagnante + 1;
			while((jla <= nbc-1) && (grille[ligneGagnante][jla] == g) && (vg.size() <= 3)){
				ArrayList<Integer> ci = new ArrayList<Integer>();
				ci.add(ligneGagnante);
				ci.add(jla);
				vg.addLast(ci);		//ajout du voisin de la case gagnante i et j
				jla = jla + 1;
			}
			if(vg.size() <= 3){
				//on recule
				int jlr = colonneGagnante - 1;
				while((jlr >= 0) && (grille[ligneGagnante][jlr] == g) && (vg.size() <= 3)){
					ArrayList<Integer> ci = new ArrayList<Integer>();
					ci.add(ligneGagnante);
					ci.add(jlr);
					vg.addFirst(ci);		//ajout du voisin de la case gagnante i et j
					jlr = jlr - 1;
				}
			}
		}else{
			if(directionGagnante.equals("c")){		//gain selon la colonne
				//on avance d'abord
				int ila = ligneGagnante + 1;
				while((ila <= nbl-1) && (grille[ila][colonneGagnante] == g) && (vg.size() <= 3) ){
					ArrayList<Integer> ci = new ArrayList<Integer>();
					ci.add(ila);
					ci.add(colonneGagnante);
					vg.addLast(ci);		//ajout du voisin de la case gagnante i et j
					ila = ila + 1;
				}
				if(vg.size() <= 3){
					//on recule
					int ilr = ligneGagnante - 1;
					while((ilr >= 0) && (grille[ilr][colonneGagnante] == g) && (vg.size() <= 3)){
						ArrayList<Integer> ci = new ArrayList<Integer>();
						ci.add(ilr);
						ci.add(colonneGagnante);
						vg.addFirst(ci);		//ajout du voisin de la case gagnante i et j
						ilr = ilr - 1;
					}
				}
			}else{
				if(directionGagnante.equals("d1")){		//gain selon la diagonale 1
					//on avance d'abord
					int ila = ligneGagnante + 1;
					int jla = colonneGagnante + 1;
					while((ila <= nbl-1) && (jla <= nbc-1) && (grille[ila][jla] == g) && (vg.size() <= 3)){
						ArrayList<Integer> ci = new ArrayList<Integer>();
						ci.add(ila);
						ci.add(jla);
						vg.addLast(ci);		//ajout du voisin de la case gagnante i et j
						ila = ila + 1;
						jla = jla + 1;
					}
					if(vg.size() <= 3){
						//on recule
						int ilr = ligneGagnante - 1;
						int jlr = colonneGagnante - 1;
						while((ilr >= 0) && (jlr >= 0) && (grille[ilr][jlr] == g) && (vg.size() <= 3)){
							ArrayList<Integer> ci = new ArrayList<Integer>();
							ci.add(ilr);
							ci.add(jlr);
							vg.addFirst(ci);		//ajout du voisin de la case gagnante i et j
							ilr = ilr - 1;
							jlr = jlr - 1;
						}
					}
				}else{
					if(directionGagnante.equals("d1")){		//gain selon la diagonale 2
						//on avance d'abord
						int ila = ligneGagnante - 1;
						int jla = colonneGagnante + 1;
						while((ila >= 0) && (jla <= nbc-1) && (grille[ila][jla] == g) && (vg.size() <= 3)){
							ArrayList<Integer> ci = new ArrayList<Integer>();
							ci.add(ila);
							ci.add(jla);
							vg.addLast(ci);		//ajout du voisin de la case gagnante i et j
							ila = ila - 1;
							jla = jla + 1;
						}
						if(vg.size() <= 3){
							//on recule
							int ilr = ligneGagnante + 1;
							int jlr = colonneGagnante - 1;
							while((ilr <= nbl-1) && (jlr >= 0) && (grille[ilr][jlr] == g) && (vg.size() <= 3)){
								ArrayList<Integer> ci = new ArrayList<Integer>();
								ci.add(ilr);
								ci.add(jlr);
								vg.addFirst(ci);		//ajout du voisin de la case gagnante i et j
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

	//affiche le vecteur gagnant
	public void afficheVecteurGagnant(){
		String s = "";
		LinkedList<ArrayList<Integer>> v = vecteurGagnant();
		for(int i = 1; i<= v.size(); i++){
			ArrayList<Integer> courant = v.getFirst();
			s = s + "(" + courant.get(0) + ", " + courant.get(1) + ") ";
		}
		System.out.println("Le vecteur gagnant est : " + s);
	}


	//V2
	/*public LinkedList<Integer> vecteurGagnant(){
		LinkedList<Integer> vg = new LinkedList<Integer>();
		vg.add(ligneGagnante);
		vg.add(colonneGagnante);		//ajout de la case gagnante i et j

		int g = grille[ligneGagnante][colonneGagnante];

		if(directionGagnante.equals("l")){		//gain selon la ligne
			//on avance d'abord
			int jla = colonneGagnante + 1;
			while((jla <= nbc-1) && (grille[ligneGagnante][jla] == g) && (vg.size() <= 3)){
				vg.addLast(ligneGagnante);
				vg.addLast(jla);	//ajout du voisin de la case gagnante i et j
				jla = jla + 1;
			}
			if(vg.size() <= 3){
				//on recule
				int jlr = colonneGagnante - 1;
				while((jlr >= 0) && (grille[ligneGagnante][jlr] == g) && (vg.size() <= 3)){
					vg.addFirst(jlr);		//ajout du voisin de la case gagnante i et j
					vg.addFirst(ligneGagnante);
					jlr = jlr - 1;
				}
			}
		}else{
			if(directionGagnante.equals("c")){		//gain selon la colonne
				//on avance d'abord
				int ila = ligneGagnante + 1;
				while((ila <= nbl-1) && (grille[ila][colonneGagnante] == g) && (vg.size() <= 3) ){
					vg.addLast(ila);
					vg.addLast(colonneGagnante);			//ajout du voisin de la case gagnante i et j
					ila = ila + 1;
				}
				if(vg.size() <= 3){
					//on recule
					int ilr = ligneGagnante - 1;
					while((ilr >= 0) && (grille[ilr][colonneGagnante] == g) && (vg.size() <= 3)){
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
					while((ila <= nbl-1) && (jla <= nbc-1) && (grille[ila][jla] == g) && (vg.size() <= 3)){
						vg.addLast(ila);
						vg.addLast(jla);	//ajout du voisin de la case gagnante i et j
						ila = ila + 1;
						jla = jla + 1;
					}
					if(vg.size() <= 3){
						//on recule
						int ilr = ligneGagnante - 1;
						int jlr = colonneGagnante - 1;
						while((ilr >= 0) && (jlr >= 0) && (grille[ilr][jlr] == g) && (vg.size() <= 3)){
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
						while((ila >= 0) && (jla <= nbc-1) && (grille[ila][jla] == g) && (vg.size() <= 3)){
							vg.addLast(ila);
							vg.addLast(jla);		//ajout du voisin de la case gagnante i et j
							ila = ila - 1;
							jla = jla + 1;
						}
						if(vg.size() <= 3){
							//on recule
							int ilr = ligneGagnante + 1;
							int jlr = colonneGagnante - 1;
							while((ilr <= nbl-1) && (jlr >= 0) && (grille[ilr][jlr] == g) && (vg.size() <= 3)){
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
		for(int i = 1; i<= vg.size(); i++){
			System.out.println(" " + vg.get(i-1));
		}
		return vg;
	}

	//affiche le vecteur gagnant
	public void afficheVecteurGagnant(){
		String s = "";
		LinkedList<Integer> v = vecteurGagnant();
		System.out.println("taille du vecteur  : " + v.size());
		int i = 0;
		while(i < v.size()){
			s = s + "(" + v.get(i) + ", " + v.get(i+1) + ") ";
			i = i + 2;
		}
		System.out.println("Le vecteur gagnant est : " + s);
	}
	*/

    /**Methode qui permet de jouer un pas d'un tour (d'ou le nom "pas a pas")
     * C'est a dire de placer un pion dans une case et de l'effacer dans la case du dessus
     * (utilisee plutot dans la version graphique pour simuler la chute du pion)
     * @param i l'indice de ligne de la case : precondition i est valide
     * @param j l'indice de colonne de la case : precondition j est valide et non pleine
     * @return vrai ssi le pion peut toujours descendre dans la colonne j*/
    public boolean jouePaP(int i, int j){

        if (i<nbl && grille[i][j]==0){
            grille[i][j] = joueurCourant;
            if (i-1>=0)//on efface le precedent
                grille[i-1][j] = 0;
        }

        setColonneCourante(j);
        setLigneCourante(i);

        if (i+1<nbl && grille[i+1][j]==0)//on peut toujours descendre
            return true;

        joueurSuivant();//sinon on ne peut plus descendre, il faut changer de joueur
        return false;

    }


    public int getHauteur(){return nbl;}

    public int getLargeur(){return nbc;}

    /**Methode pour dessiner le plateau de jeu dans un panneau
     * (dessine les cercles rouges, jaunes et blancs
     * @param g le parametre de type Graphics qui permet dessiner
     * @param largeur la largeur du panneau en nombre de pixels
     * @param hauteur la hauteur du panneau en nombre de pixels*/
    public void affiche(Graphics g, int largeur, int hauteur){
        int height = hauteur/nbl;
        int width = largeur/nbc;

        for (int i=0; i<nbl; i++){
            for (int j=0; j<nbc; j++){
                if (grille[i][j]==joueur1){
                    g.setColor(Color.yellow);
                }
                else {
                    if (grille[i][j]==joueur2){
                        g.setColor(Color.red);
                    }
                    else
                    {
                        g.setColor(Color.white);
                    }
                }
                g.fillOval(j*width+width/10,i*height+height/10,4*width/5,4*height/5);
            }
        }

    }


    /**Methode qui revoie le nom (couleur) du joueur courant*/
    public String getCouleurJoueur() {
        if (joueurCourant==joueur1)
            return couleur1;
        return couleur2;
    }

    /**Methode qui renvoie vrai ssi j n'est pas un indice valide de colonne*/
    public boolean colonneInvalide(int j){
        return (j<0 || j>nbc-1);
    }

    /**Methode qui renvoie vrai ssi la colonne j est pleine*/
    public boolean colonnePleine(int j){
        return grille[0][j]!=0;
    }


    /**Methode pour reinitialiser le plateau de jeu*/
    public void effacer(){
        grille = new int[nbl][nbc];
        joueurCourant = joueur1;
    }

}
