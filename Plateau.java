import java.awt.*;
import java.util.*;

public class Plateau
{

	public static final int joueur1 = 1;			//joueur 1
	public static final int joueur2 = 2;			//joueur 2
    private String couleur1 = "JAUNE" ;
    private String couleur2 = "ROUGE" ;
    private int joueurCourant;
    private int joueurGagnant;
    private int nbl = 6;
    private int nbc = 7;
    private int[][] grille;			//grille du jeu
	private int[] niveauCol;		//tableau des niveaux pour chaque colonne
	private int ligneCourante, colonneCourante;	//indice de la ligne et de la colonne courante
    private int[][] vecteurGagnant; //la 1ere est le vecteur des 4 i et le 2eme le vecteur de 4 j qui ont permis de ganger
    private int indiceVecteurGagant = -1;//l'indice de la derniere case ajoutee dans le vecteur gagnant
    private int attente = 10; //nombre de millisecondes entre chaque etape de la chute du pion

    //CONSTRUCTEUR
	public Plateau(){
		joueurCourant  = joueur1;	//joueurCourant
		grille = new int[nbl][nbc];		//grille du jeu
		niveauCol = new int[nbc];		//tableau des niveaux pour chaque colonne
        vecteurGagnant = new int[2][4];
	}

	//ACCESSEURS

    /**Methode qui renvoie le numero du joueur courant*/
    public int getJoueurCourant(){return joueurCourant;}

    /**Methode qui renvoie le numero du joueur qui a gagne*/
    public int getJoueurGagnant(){return joueurGagnant;}

    /**Methode qui renvoie le numero de l'autre joueur (celui qui n'est pas le joueur courant*/
	public int autreJoueur(){
        return joueur1 + joueur2 - joueurCourant;
	}

    /**Methode qui revoie le nom (couleur) du joueur courant*/
    public String getCouleurJoueur() {
        if (joueurCourant==joueur1)
            return couleur1;
        return couleur2;
    }

    /**Methode qui revoie le nom (couleur) du joueur qui a gagne*/
    public String getCouleurGagnant() {
        if (joueurGagnant==joueur1)
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

    /**Methode qui renvoie la hauteur de la grille (nombre de lignes)*/
    public int getHauteur(){return nbl;}

    /**Methode qui renvoie la largeur de la grille (nombre de colonnes)*/
    public int getLargeur(){return nbc;}


    //MODIFICATEURS

    /**Methode qui permet de gerer la vitesse de chute d'un pion
     * @param attente le temps d'attente en millisecondes*/
    public void setAttente(int attente) {this.attente = attente;}


	//AUTRES METHODES
    /**Methode pour reinitialiser le plateau de jeu*/
    public void effacer(){
        grille = new int[nbl][nbc];
        niveauCol = new int[nbc];
        joueurCourant = joueur1;
        effacerVecteurGagnant();
    }

    /**Methode pour dessiner le plateau de jeu dans un panneau
     * (dessine les cercles rouges, jaunes et blancs
     * @param g le parametre de type Graphics qui permet dessiner
     * @param largeur la largeur du panneau en nombre de pixels
     * @param hauteur la hauteur du panneau en nombre de pixels*/
    public void affiche(Graphics g, int largeur, int hauteur){
        int height = hauteur/nbl;
        int width = largeur/nbc;
        Graphics2D g2 = (Graphics2D)g;
        for (int i=0; i<nbl; i++){
            for (int j=0; j<nbc; j++){
                if (dansVecteurGagnant(i,j)){
                    //CHOIX DE LA COULEUR - A REVOIR
                    //g.setColor(Color.getHSBColor(0.5f,0.265f,0.933f));
                    //g.fillRect(j*width,i*height,width,height);
                    if (joueurGagnant==joueur1)
                        g2.setColor(Color.yellow.darker());
                    else
                        g2.setColor(Color.red.darker());
                    g2.setStroke(new BasicStroke(10));
                    g2.drawOval(j*width+width/10,i*height+height/10,4*width/5,4*height/5);


                }
                if (grille[i][j]==joueur1){
                    g.setColor(Color.yellow);
                }
                else {
                    if (grille[i][j]==joueur2){
                        g.setColor(Color.red);
                    }
                    else
                    {
                        g.setColor(Color.cyan);
                    }
                }
                g.fillOval(j*width+width/10,i*height+height/10,4*width/5,4*height/5);
            }
        }

    }


    /**Methode qui permet de savoir si la partie est gagnee apres le dernier pion joue
     * @return i si le joueur i a gagne, -1 si personne n'a gagne*/
	public int gagne(){
		int res = -1;
		int i = ligneCourante;
		int k = colonneCourante;
		int g = grille[i][k];
        effacerVecteurGagnant();
        addVecteurGagnant(i,k);
		if((1 + avanceLigne(i,k) + reculeLigne(i,k)) >= 4){
			joueurGagnant = autreJoueur();//quand il a joue le pion, il a passe la main
			return g;
		}else{
            effacerVecteurGagnant();
            addVecteurGagnant(i,k);
            if((1 + avanceColonne(i,k) + reculeColonne(i,k)) >= 4){
				joueurGagnant = autreJoueur();
				return g;
			}else{
                effacerVecteurGagnant();
                addVecteurGagnant(i,k);
                if((1 + avanceDiag1(i,k) + reculeDiag1(i,k)) >= 4){
					joueurGagnant = autreJoueur();
					return g;
				}else{
                    effacerVecteurGagnant();
                    addVecteurGagnant(i,k);
                    if((1 + avanceDiag2(i,k) + reculeDiag2(i,k)) >= 4){
						joueurGagnant = autreJoueur();
						return g;
					}
				}
			}
		}
		return res;
	}

    /**Methode permettant au joueur courant de jouer la colonne j en simulant la chute du pion
     * @param j la colonne a jouer
     * @param affichage le Thread qui gere l'affichage (pour pouvoir lui mettre un sleep)*/
    public void joue(int j, Thread affichage){
        boolean descentePossible=true;
        int i = 0;
        while (descentePossible){
            descentePossible = jouePaP(i,j);
            i++;
            try {
                affichage.sleep(attente);
            }
            catch (InterruptedException e){}
        }
        niveauCol[j]++;
    }


    /**Methode qui permet de jouer contre l'ordinateur, niveau facile (l'ordi joue au hasard)
     * @return la colonne a jouer*/
    public int joueOrdiFacile(){
        return (int)(Math.random() * (nbc));//entier au hasard entre 0 et nbc-1
    }

    /**Methode qui permet de jouer contre l'ordinateur, niveau moyen
     * @return la colonne a jouer*/
    public int joueOrdiMoyen(){
        //A REFAIRE!!!
        return (int)(Math.random() * (nbc));//entier au hasard entre 0 et nbc-1
    }
    /**Methode qui permet de jouer contre l'ordinateur, niveau difficile
     * @return la colonne a jouer*/
    public int joueOrdiDifficile(){
        //A REFAIRE!!!
        return (int)(Math.random() * (nbc));//entier au hasard entre 0 et nbc-1
    }

    /*---METHODES PRIVEES---*/

    //Methode qui fait passer la main au joueur suivant
    private void joueurSuivant(){
        joueurCourant = joueur1 + joueur2 - joueurCourant;
    }

    /*Methode qui permet de jouer un pas d'un tour (d'ou le nom "pas a pas")
  * C'est a dire de placer un pion dans une case et de l'effacer dans la case du dessus
  * (utilisee plutot dans la version graphique pour simuler la chute du pion)
  * @param i l'indice de ligne de la case : precondition i est valide
  * @param j l'indice de colonne de la case : precondition j est valide et non pleine
  * @return vrai ssi le pion peut toujours descendre dans la colonne j*/
    private boolean jouePaP(int i, int j){

        if (i<nbl && grille[i][j]==0){
            grille[i][j] = joueurCourant;
            if (i-1>=0)//on efface le precedent
                grille[i-1][j] = 0;
        }

        colonneCourante = j;
        ligneCourante = i;

        if (i+1<nbl && grille[i+1][j]==0)//on peut toujours descendre
            return true;

        joueurSuivant();//sinon on ne peut plus descendre, il faut changer de joueur
        return false;

    }

    //LIGNE
    //renvoie le nombre de pion voisins de celui du joueur de la case i,j sur la ligne
    private int avanceLigne(int i, int j){
        int nb = 0;
        if(grille[i][j] != 0){
            int j1 = j + 1;
            boolean rep = true;
            while(nb<=4 && (j1 <= nbc-1) && (grille[i][j1] != 0)){
                rep = rep && (grille[i][j1] == grille[i][j]);
                if(rep){
                    addVecteurGagnant(i,j1);
                    j1++;
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


    private int reculeLigne(int i, int j){
        int nb = 0;
        if(grille[i][j] != 0){
            int j1 = j - 1;
            boolean rep = true;
            while(nb<=4 && (j1 >= 0) && (grille[i][j1] != 0)){
                rep = rep && (grille[i][j1] == grille[i][j]);
                if(rep){
                    addVecteurGagnant(i,j1);
                    j1--;
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
    private int avanceColonne(int i, int j){
        int nb = 0;
        if(grille[i][j] != 0){
            int i1 = i + 1;
            boolean rep = true;
            while(nb<=4 && (i1 <= nbl-1) && (grille[i1][j] != 0)){
                rep = rep && (grille[i1][j] == grille[i][j]);
                if(rep){
                    addVecteurGagnant(i1,j);
                    i1++;
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


    private int reculeColonne(int i, int j){
        int nb = 0;
        if(grille[i][j] != 0){
            int i1 = i - 1;
            boolean rep = true;
            while(nb<=4 && (i1 >= 0) && (grille[i1][j] != 0)){
                rep = rep && (grille[i1][j] == grille[i][j]);
                if(rep){
                    addVecteurGagnant(i1,j);
                    i1--;
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
    private int avanceDiag1(int i, int j){
        int nb = 0;
        if(grille[i][j] != 0){
            int i1 = i + 1;
            int j1 = j + 1;
            boolean rep = true;
            while(nb<=4 && (i1 <= nbl-1) && (j1 <= nbc-1) && (grille[i1][j1] != 0)){
                rep = rep && (grille[i1][j1] == grille[i][j]);
                if(rep){
                    addVecteurGagnant(i1,j1);
                    i1++;
                    j1++;
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


    private int reculeDiag1(int i, int j){
        int nb = 0;
        if(grille[i][j] != 0){
            int i1 = i - 1;
            int j1 = j - 1;
            boolean rep = true;
            while(nb<=4 && (i1 >= 0) && (j1 >= 0) && (grille[i1][j1] != 0)){
                rep = rep && (grille[i1][j1] == grille[i][j]);
                if(rep){
                    addVecteurGagnant(i1,j1);
                    i1--;
                    j1--;
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
    private int avanceDiag2(int i, int j){
        int nb = 0;
        if(grille[i][j] != 0){
            int i1 = i - 1;
            int j1 = j + 1;
            boolean rep = true;
            while(nb<=4 && (i1 >= 0) && (j1 <= nbc-1) && (grille[i1][j1] != 0)){
                rep = rep && (grille[i1][j1] == grille[i][j]);
                if(rep){
                    addVecteurGagnant(i1,j1);
                    i1--;
                    j1++;
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


    private int reculeDiag2(int i, int j){
        int nb = 0;
        if(grille[i][j] != 0){
            int i1 = i + 1;
            int j1 = j - 1;
            boolean rep = true;
            while(nb<=4 && (i1 <= nbl-1) && (j1 >= 0) && (grille[i1][j1] != 0)){
                rep = rep && (grille[i1][j1] == grille[i][j]);
                if(rep){
                    addVecteurGagnant(i1,j1);
                    i1++;
                    j1--;
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

    //methode pour effacer le vecteur gagnant
    private void effacerVecteurGagnant(){
        indiceVecteurGagant = -1;
    }

    //Methode pour ajouter la case i,j a la liste des vecteurs qui ont permis de gagner
    private void addVecteurGagnant(int i, int j){
        if (indiceVecteurGagant<3){
            indiceVecteurGagant++;
            vecteurGagnant[0][indiceVecteurGagant]=i;
            vecteurGagnant[1][indiceVecteurGagant]=j;
        }
    }

    //Methode qui renvoie vrai ssi la case i,j est dans le vecteur gagnant
    private boolean dansVecteurGagnant(int i, int j){
        boolean trouve = false;
        if (indiceVecteurGagant==3){//si on a gagne
            int k = 0;
            while (k<4 && !trouve) {
                trouve = (vecteurGagnant[0][k] == i && vecteurGagnant[1][k] == j);
                k++;
            }
        }
        return trouve;
    }

}
