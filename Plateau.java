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
	
	public void joue(int j){
		
		int i = 0;
		
		while (i<6 && grille[i][j]==0)
			i++;
		
		if (i>0)//colonne non pleine
			grille[i-1][j] = joueur;
		
		suivant();
	
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
		
		
		//if (gagne)
			return jr;
		//return -1;
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
		boolean gagne = false;
		int k = 1, i = 0;
		while (!gagne && i<4){
			k = i+1;
			
			while(k<i+4 && grille[k][j]==grille[k-1][j])
				k++;
			
			gagne = (k==i+4 && grille[k-1][j]!=0);
			i=k;
		}
		if (gagne)
			return grille[k-1][j];
		return -1;
	}
	
	/*renvoie le joueur qui a gagne sur la 1ere diagonale partant de la case i,j,
	*-1 s'il n y en a pas*/
	private int gagneDiagonaleDroite(int i, int j){
	
		return -1;
	}
	
	/*renvoie le joueur qui a gagne sur la 2eme diagonale partant de la case i,j,
	*-1 s'il n y en a pas*/
	private int gagneDiagonaleGauche(int i, int j){
	
		return -1;
	}
	
	/*changer le joueur courant*/
	private void suivant(){
		if (joueur==jaune)
			joueur = rouge;
		else
			joueur = jaune;
	} 
	

}
