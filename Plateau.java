import java.io.IOException;
import java.util.IllegalFormatCodePointException;
import java.util.Scanner;

public class Plateau{

	private int[][] grille;
	private int joueur;
	static final int jaune = 1;
	static final int rouge = 2;
	
	
	public Plateau(){
		grille = new int[6][7];
		joueur = jaune;
	}
	
	public void imprime(){
		
		System.out.println();
		for (int i=0; i<6; i++){
			for (int j=0; j<7; j++){
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
	
	public void joue(int j) throws IllegalArgumentException{
		if (j<0 || j>6)
		    throw new IllegalArgumentException("Le numero de colonne doit etre compris entre 0 et 6 !");

		int i = 0;

		while (i<6 && grille[i][j]==0)
			i++;
		
		if (i>0)//colonne non pleine
			grille[i-1][j] = joueur;
		
		suivant();
	
	}

	public boolean estPlein(){
        //test si le jeu est fini (grille pleine)
        int j = 0;
        while (j<7 && grille[0][j]!=0)
            j++;
        return j==7;
    }
	
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
		while (!gagne && j<7){
			jr = gagneColonne(j);
			gagne = (jr!=-1);
			j++;
		}

		//recherche sur les diagonales suivant les 1eres diagonales
		i = 3;
		while (!gagne && i<6){
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
		while (!gagne && i<6){
			jr = gagneDiagonaleGauche(i,6);
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

	public int deuxJoueurs(){
        Scanner lecture = new Scanner(System.in);
        System.out.println("Joueur "+joueur+", saisissez une colonne a jouer");
        joue(lecture.nextInt());
        imprime();
        return gagne();
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
