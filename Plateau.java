import java.awt.*;
import java.lang.reflect.Array;
import java.util.*;
import java.util.List;

public class Plateau
{

	public static final int joueur1 = 1;			//joueur 1
    public static final int joueur2 = 2;			//joueur 2
    private  final String couleur1 = "JAUNE" ;
    private  final String couleur2 = "ROUGE" ;
    public static final int attenteMin = 10;
    public static final int attenteMax = 100;
    public static final int profondeurMoy = 4;
    public static final int profondeurMax = 8;

    //Joueur
    private int joueurCourant;
    private int joueurGagnant;
    //Grille
    private static int nbl = 6;
    private static int nbc = 7;
    private static List<Integer> indicesColonnes ;//liste pour stocker les indices de colonnes, permettra de parcourir les colonnes dans un ordre aléatoire

    private int[][] grille;			//grille du jeu
	private int[] niveauCol;		//tableau du nb de pions dans chaque colonne
	private int ligneCourante, colonneCourante;	//indice de la ligne et de la colonne courante
    private int[][] vecteurGagnant; //la 1ere est le vecteur des 4 i et le 2eme le vecteur de 4 j qui ont permis de ganger
    private int indiceVecteurGagant = -1;//l'indice de la derniere case ajoutee dans le vecteur gagnant
    //Affichage
    private int attente = attenteMin; //nombre de millisecondes entre chaque etape de la chute du pion
    //Intelligence artificielle
    private int profondeur = 3;
    private int colonneOrdi = -1;

    //CONSTRUCTEUR
	public Plateau(){
		joueurCourant  = joueur1;
		grille = new int[nbl][nbc];
		niveauCol = new int[nbc];
        vecteurGagnant = new int[2][4];
        initIndicesColonnes();
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
        return niveauCol[j]==nbl;
    }

    /**Methode qui renvoie vrai ssi la grille est pleine*/
    public boolean grillePleine(){
        int j = 0;
        while(j<nbc && colonnePleine(j)){
            j++;
        }
        return j==nbc;
    }

    /**Methode qui renvoie la hauteur de la grille (nombre de lignes)*/
    public int getHauteur(){return nbl;}

    /**Methode qui renvoie la largeur de la grille (nombre de colonnes)*/
    public int getLargeur(){return nbc;}

    //MODIFICATEURS

    /**Methode qui permet de gerer la vitesse de chute d'un pion
     * @param attente le temps d'attente en millisecondes*/
    public void setAttente(int attente) {this.attente = attente;}

    /**Methode qui permet de gerer la profondeur de l'arbre dans les algo min-max er alpha-beta
     * @param profondeur represente le nombre de coups que l'ordinateur peut anticiper*/
    public void setProfondeur(int profondeur){this.profondeur=profondeur;}

	//AUTRES METHODES
    /**Methode qui renvoie une copie du plateau courant*/
    public Plateau copie(){
        Plateau p = new Plateau();
        p.joueurCourant = this.joueurCourant;
        p.joueurGagnant = this. joueurGagnant;
        p.nbc = this.nbc;
        p.nbl = this.nbl;
        p.grille = this.grille.clone();
        p.niveauCol = this.niveauCol.clone();
        p.ligneCourante = this.ligneCourante;
        p.colonneCourante = this. colonneCourante;
        p.vecteurGagnant = this.vecteurGagnant.clone();
        p.indiceVecteurGagant = this.indiceVecteurGagant;
        p.attente = this.attente;
        p.profondeur = this.profondeur;
        p.colonneOrdi = this.colonneOrdi;
        return p;
    }

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
                    if (joueurGagnant==joueur1)
                        g2.setColor(Color.yellow.darker());
                    else
                        g2.setColor(Color.red.darker());
                    g2.setStroke(new BasicStroke(15));
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
			joueurGagnant = grille[i][k];
			return g;
		}else{
            effacerVecteurGagnant();
            addVecteurGagnant(i,k);
            if((1 + avanceColonne(i,k) + reculeColonne(i,k)) >= 4){
				joueurGagnant =  grille[i][k];
				return g;
			}else{
                effacerVecteurGagnant();
                addVecteurGagnant(i,k);
                if((1 + avanceDiag1(i,k) + reculeDiag1(i,k)) >= 4){
					joueurGagnant =  grille[i][k];
					return g;
				}else{
                    effacerVecteurGagnant();
                    addVecteurGagnant(i,k);
                    if((1 + avanceDiag2(i,k) + reculeDiag2(i,k)) >= 4){
						joueurGagnant =  grille[i][k];
						return g;
					}
				}
			}
		}
		return res;
	}


	/**Methode qui permet a un joueur de jouer un pion
     * @param j la colonne a jouer
     * @param joueur le numero du joueur
     * @throws ExceptionColonneInvalide si j n'est pas un indice valide de colonne
     * @throws ExceptionColonnePleine si la colonne j est deja pleine*/
	public void joue(int j, int joueur) throws ExceptionColonneInvalide,ExceptionColonnePleine {
        if (colonneInvalide(j))
            throw new ExceptionColonneInvalide(j);
        if (niveauCol[j]==nbl)
            throw new ExceptionColonnePleine(j);
        joueurCourant = joueur;
        colonneCourante = j;
        ligneCourante = nbl - 1 - niveauCol[j];
        grille[ligneCourante][colonneCourante] = joueurCourant;
        niveauCol[j]++;
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
        int j = -1;
        boolean trouve = false;
        while (!trouve) {
            j = (int) (Math.random() * (nbc));
            trouve = !colonnePleine(j);
        }
        return j;//entier au hasard entre 0 et nbc-1
    }

    /**Methode qui permet de jouer contre l'ordinateur, niveau moyen
     * @return la colonne a jouer*/
    public int joueOrdiMoyen(){
        minmaxOrdi(0);
        return colonneOrdi;
    }
    /**Methode qui permet de jouer contre l'ordinateur, niveau difficile
     * @return la colonne a jouer*/
    public int joueOrdiDifficile(){
        alphabetaOrdi(0,Integer.MIN_VALUE,Integer.MAX_VALUE);
        return colonneOrdi;
    }

    private int minmaxOrdi(int niveau){
        if (niveau==profondeur)
            return valeur();
        Plateau p = this.copie();
        int val = Integer.MIN_VALUE;
        int valrec;
        Collections.shuffle(indicesColonnes);
        for (int j : indicesColonnes){
            try{
                p.joue(j,joueur2);//l'ordinateur joue les coups possibles
                if (p.gagne()==joueur2){//l'ordi a gagne, pas besoin d'evaluer les branches en dessous
                    colonneOrdi = j;
                    val = Integer.MAX_VALUE;
                    p.effacerDernier();
                    return val;
                }
            }catch (Exception e){continue;}//colonne pleine
            valrec = p.minmaxAdv(niveau+1);
            if (valrec>val){
                val = valrec;
                colonneOrdi = j;
            }
            p.effacerDernier();//on remet le plateau a la configuration initiale
        }
        return val;
    }
    private int minmaxAdv(int niveau){
        if (niveau==profondeur)
            return valeur();
        Plateau p = this.copie();
        int val = Integer.MAX_VALUE;
        int valrec;
        Collections.shuffle(indicesColonnes);
        for (int j : indicesColonnes){
            try{
                p.joue(j,joueur1);//l'adversaire joue les coups possibles
                if (p.gagne()==joueur1){//l'adv a gagne, pas besoin d'evaluer les branches en dessous
                    colonneOrdi = j;
                    val = Integer.MIN_VALUE;
                    p.effacerDernier();
                    return val;
                }
            }catch (Exception e){continue;}//colonne pleine
            valrec = p.minmaxOrdi(niveau+1);
            if (valrec<val){
                val = valrec;
                colonneOrdi = j;
             }
            p.effacerDernier();//on remet le plateau a la configuration initiale
        }
        return val;
    }

    /* permet de faire une simulation de jeu a partir de la configuration courante pour l'ordinateur
     * @param niveau : profondeur de l'arbre engendre par l'algorithme alpha beta
     * @param alpha : borne inferieure de l'intervalle de confiance [alpha,beta]
     * @param beta : borne superieure de l'intervalle de confiance [alpha,beta]
     * @return la valeur gagnee par l'ordinateur pour la configuration courante
     */
    private int alphabetaOrdi(int niveau, int a, int b){
        if(niveau == profondeur)
            return valeur();

        Plateau cop = this.copie();
        int alpha = a, beta = b;
        int valsuiv;
        Collections.shuffle(indicesColonnes);
        for (int j : indicesColonnes){
            try{
                cop.joue(j, joueur2);
                if (cop.gagne()==joueur2){//l'ordi a gagne, pas besoin d'evaluer les branches en dessous
                    colonneOrdi = j;
                    cop.effacerDernier();
                    return Integer.MAX_VALUE;
                }
            }catch(Exception e){//colonne pleine
                continue;
            }
            valsuiv = cop.alphabetaAdv(niveau + 1, alpha, beta);
            //alpha = max(alpha,valsuiv);
            if (valsuiv > alpha){
                alpha = valsuiv;
                colonneOrdi = j;
            }
            cop.effacerDernier();
            if (alpha>=beta)
                return beta;
        }
        return alpha;
        //return eval;

    }
    /* permet de faire une simulation de jeu a partir de la configuration courante pour l'adversaire
     * @param niveau : profondeur de l'arbre engendré par l'algorithme alpha beta
     * @param alpha : borne inferieure de l'intervalle de confiance [alpha,beta]
     * @param beta : borne superieure de l'intervalle de confiance [alpha,beta]
     * @return la valeur gagnee par l'adversaire pour la configuration courante
     */
    private int alphabetaAdv(int niveau, int a, int b){
        if(niveau == profondeur)
            return valeur();
        Plateau cop = this.copie();
        int alpha = a, beta = b;
        int valsuiv;
        Collections.shuffle(indicesColonnes);
        for (int j : indicesColonnes){
            try{
                cop.joue(j, joueur1);
                if (cop.gagne()==joueur1){//l'adv a gagne, pas besoin d'evaluer les branches en dessous
                    colonneOrdi = j;
                    cop.effacerDernier();
                    return Integer.MIN_VALUE;
                }
            }catch(Exception e){
                continue;
            }
            valsuiv = cop.alphabetaOrdi(niveau + 1, alpha, beta);
            //beta = min(beta,valsuiv);
            if (valsuiv < beta){
                beta = valsuiv;
                colonneOrdi = j;
            }
            cop.effacerDernier();
            if (alpha>=beta)
                return alpha;
        }
        return beta;
    }

    /*---METHODES PRIVEES---*/

    //Methode qui fait passer la main au joueur suivant
    private void joueurSuivant(){
        joueurCourant = joueur1 + joueur2 - joueurCourant;
    }

    //Methode qui permet a l'odinateur de savoir si la configuration courante de la grille lui est favorable
    private int valeur(){
        int g = gagne();
        if (g==joueur2)//l'ordinateur a gagne
            return Integer.MAX_VALUE;
        if (g==-1)//personne n'a gagne
            return 0;
        return Integer.MIN_VALUE;//l'adversaire a gagne
    }

    //Methode qui initialise la liste des numeros de colonnes (0 a nbc-1)
    private static void initIndicesColonnes(){
        indicesColonnes = new ArrayList<Integer>();
        for (int j = 0;j<nbc;j++)
            indicesColonnes.add(j);
    }

    //Methode pour effacer le dernier pion joue dans le plateau
    private void effacerDernier() {
        grille[ligneCourante][colonneCourante] = 0;
        niveauCol[colonneCourante]--;
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
