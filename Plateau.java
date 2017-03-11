import java.io.IOException;
import java.util.IllegalFormatCodePointException;
import java.awt.*;
import java.util.Scanner;

/**Classe decrivant un plateau de jeu -
 * par convention, jaune = 1, rouge = 2 et case vide = 0*/
public class Plateau{

	private int[][] grille;
	private int joueur;
	private final int nbl = 6;
	private final int nbc = 7;
	static final int jaune = 1;
	static final int rouge = 2;
	
	/**Constructeur du plateau
     * - au depart la grille est vide et le premier joueur est le jaune*/
	public Plateau(){
		grille = new int[nbl][nbc];
		joueur = jaune;
	}

	/**Methode pour reinitialiser le plateau de jeu*/
	public void effacer(){
		grille = new int[nbl][nbc];
		joueur = jaune;
	}

	/**Mehode pour afficher le plateau de jeu dans la console*/
	public void imprime(){
		
		System.out.println();
		for (int i=0; i<nbl; i++){
			for (int j=0; j<nbc; j++){
				if (grille[i][j]==jaune)
					System.out.print('j');
				else {
					if (grille[i][j]==rouge)
						System.out.print('r');
					else
					 System.out.print('.');
				}
			}
			System.out.println();
		}
		System.out.println();
	
	}


    /**Methode pour dessiner le plateau de jeu dans un panneau
     * (dessine les cercles rouges, jaunes et blancs
     * @param g le parametre de type Graphics qui permet dessiner
     * @param largeur la largeur du panneau en nombre de pixels
     * @param hauteur la hauteur du panneau en nombre de pixels*/
	public void affiche(Graphics g, int largeur,int hauteur){
		int height = hauteur/nbl;
		int width = largeur/nbc;

		for (int i=0; i<nbl; i++){
			for (int j=0; j<nbc; j++){
				if (grille[i][j]==jaune){
					g.setColor(Color.yellow);
				}
				else {
					if (grille[i][j]==rouge){
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

	/**Methode qui permet de jouer un tour
     * (utilisee plutot dans la version texte)
     * @param j la colonne a jouer
     * @throws ExceptionColonneInvalide lorsque j n'est pas un indice valide de une colonne
     * @throws ExceptionColonnePleine lorsque la colonne j est deja remplie*/
	public void joue(int j) throws Exception{
		if (j<0 || j>nbc-1)
		    throw new ExceptionColonneInvalide(nbc);

		int i = 0;

		while (i<nbl && grille[i][j]==0)
			i++;

		if (i>0)//colonne non pleine
			grille[i-1][j] = joueur;
		else
		    throw new ExceptionColonnePleine(j);
		suivant();

	}

	/**Methode qui permet de jouer un pas d'un tour (d'ou le nom "pas a pas")
     * C'est a dire de placer un pion dans une case et de l'effacer dans la case du dessus
     * (utilisee plutot dans la version graphique pour simuler la chute du pion)
     * @param i l'indice de ligne de la case : precondition i est valide
     * @param j l'indice de colonne de la case : precondition j est valide et non pleine
     * @return vrai ssi le pion peut toujours descendre dans la colonne j*/
	public boolean jouePaP(int i, int j){

        if (i<nbl && grille[i][j]==0){
            grille[i][j] = joueur;
            if (i-1>=0)//on efface le precedent
                grille[i-1][j] = 0;
        }

        if (i+1<nbl && grille[i+1][j]==0)//on peut toujours descendre
            return true;

        suivant();//sinon on ne peut plus descendre, il faut changer de joueur
        return false;

    }

    /**Methode qui permet de savoir si le plateau de jeu est deja plein
     * @return true ssi le plateau est plein*/
	public boolean estPlein(){
        //test si le jeu est fini (grille pleine)
        int j = 0;
        while (j<nbc && grille[0][j]!=0)
            j++;
        return j==nbc;
    }

    /**Methode qui permet de savoir si la partie est gagnee
     * @return i si le joueur i a gagne, -1 si personne n'a gagne*/
	public int gagne(){

        boolean gagne = false;
		int jr = -1;
		//recherche sur les lignes partant du bas
		int i = 5;
		while (!gagne && i>=0){
			jr = gagneLigne(i);
			gagne = (jr!=-1);
			i--;
		}
		
		//recherche sur les colonnes partant de la gauche
        int j = 0;
		while (!gagne && j<nbc){
			jr = gagneColonne(j);
			gagne = (jr!=-1);
			j++;
		}

		//recherche sur les diagonales suivant les 1eres diagonales
		i = 3;
		while (!gagne && i<nbl){
			jr = gagneDiagonaleDroite(i,0);
			gagne = (jr!=-1);
			i++;
		}
		j = 1;
		while (!gagne && j<4){
			jr = gagneDiagonaleDroite(5,j);
			gagne = (jr!=-1);
			j++;
		}

		//recherche sur les diagonales suivant les 2emes diagonales
		i = 3;
		while (!gagne && i<nbl){
			jr = gagneDiagonaleGauche(i,nbl);
			gagne = (jr!=-1);
			i++;
		}
		j = 5;
		while (!gagne && j>2){
			jr = gagneDiagonaleGauche(5,j);
			gagne = (jr!=-1);
			j--;
		}
		return jr;

	}

	/**Methode permettant de lire les entrees dans la version texte a 2 joueurs,
     * de jouer et d'imprimer le plateau dans la console
     * @return la meme valeur de retour que 'gagne'*/
	public int deuxJoueurs(){
        Scanner lecture = new Scanner(System.in);
        System.out.println("Joueur "+joueur+", saisissez une colonne a jouer");
        try {
            joue(lecture.nextInt());
        }
        catch (Exception e){
           if(e instanceof ExceptionColonneInvalide)
            System.out.println("L'indice de la colonne doit etre comprise entre 0 et "+((ExceptionColonneInvalide)e).getIndiceMax());
           else{
               if(e instanceof ExceptionColonnePleine)
                   System.out.println("La colonne "+((ExceptionColonnePleine) e).getCol()+" est deja pleine");
           }
        }
        imprime();
        return gagne();
    }

    public int getHauteur(){return nbl;}

    public int getLargeur(){return nbc;}

    public int getJoueur() {return joueur;}

    /**Methode qui revoie le nom (couleur) du joueur courant*/
    public String getCouleurJoueur() {
        if (joueur==jaune)
            return "JAUNE";
        return "ROUGE";
    }

    /**Methode qui renvoie vrai ssi j n'est pas un indice valide de colonne*/
    public boolean colonneInvalide(int j){
        return (j<0 || j>nbc-1);
    }

    /**Methode qui renvoie vrai ssi la colonne j est pleine*/
    public boolean colonnePleine(int j){
        return grille[0][j]!=0;
    }

	/*renvoie le joueur qui a gagne sur la ligne i, -1 s'il n y en a pas*/
	private int gagneLigne(int i){
		
		boolean gagne = false;
		int k = 1, j = 0;
		while (!gagne && j<4){
			k = j+1;
			
			while(k<j+4 && grille[i][k]==grille[i][k-1])
				k++;
			
			gagne = (k==j+4 && grille[i][k-1]!=0);
			j=k;
		}
		if (gagne)
			return grille[i][k-1];
		return -1;
	}
	
	/*renvoie le joueur qui a gagne sur la colonne j, -1 s'il n y en a pas*/
	private int gagneColonne(int j){
		if(grille[5][j]!=0) {//si la derniere case est vide alors toute la colonne est vide
			boolean gagne = false;
			int k = 4, i = 5;
			while (!gagne && i > 2) {
				k = i - 1;

				while (k > i - 4 && grille[k][j] == grille[k + 1][j])
					k--;

				gagne = (k == i - 4 && grille[k + 1][j] != 0);
				i = k;
			}
			if (gagne)
				return grille[k + 1][j];
		}
		return -1;
	}
	
	/*renvoie le joueur qui a gagne sur la 1ere diagonale partant de la case i,j,
	*-1 s'il n y en a pas
	* precondition : 5 >= i >= 3 et 0 <= j <= 3*/
	private int gagneDiagonaleDroite(int i, int j){
		boolean gagne = false;
		int k = i, l = j;//indices de la premiere case
		int m = i-1, n = j+1;//indices des cases suivantes
		while (!gagne && k>=3 && l<=3){
			m = k - 1;//i decroit(on part vers le haut)
			n = l + 1;//j croit (on part vers la droite)
			while (m>k-4 && n<l+4 && grille[m][n]==grille[m+1][n-1]){
				m--;
				n++;
			}
			gagne = (m==k-4 && n==l+4 && grille[m+1][n-1]!=0);
			k = m;
			l = n;
		}
		if (gagne)
			return grille[m+1][n-1];
		return -1;
	}
	
	/*renvoie le joueur qui a gagne sur la 2eme diagonale partant de la case i,j,
	*-1 s'il n y en a pas
	* precondition : 5 >= i >= 3 et 6 >= j >= 3*/
	private int gagneDiagonaleGauche(int i, int j){
		boolean gagne = false;
		int k = i, l = j;//indices de la premiere case
		int m = i-1, n = j-1;//indices des cases suivantes
		while (!gagne && k>=3 && l>=3){
			m = k - 1;//i decroit(on part vers le haut)
			n = l - 1;//j decroit (on part vers la gauche)
			while (m>k-4 && n>l-4 && grille[m][n]==grille[m+1][n+1]){
				m--;
				n--;
			}
			gagne = (m==k-4 && n==l-4 && grille[m+1][n+1]!=0);
			k = m;
			l = n;
		}
		if (gagne)
			return grille[m+1][n+1];
		return -1;
	}
	
	/*changer le joueur courant*/
	private void suivant(){
		joueur = jaune + rouge - joueur;
	} 
	

}
