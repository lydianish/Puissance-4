import javafx.scene.layout.Border;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**Classe decrivant le panneau ou sera dessine le plateau de jeu*/
public class Panneau extends JPanel implements MouseListener{

    private Plateau jeu;
    private int hauteur, largeur;
    private int col=-1;//la colonne a jouer : col=-1 <=> personne ne joue
    private String message;
    private JLabel texte;
    private boolean pret = true; //pret=true => qq'un peut joeur, pret = false => qq'un est en train de jouer
    private int gagne = -1;

    /**Constructeur du panneau
     * @param jeu le plateau
     * @param largeur la largeur en nombre de pixels
     * @param hauteur la hauteur en nombre de pixels
     * @param texte l'endroit ou seront affiches les messages (au dessus du plateau)*/
	public Panneau (Plateau jeu, int largeur, int hauteur, JLabel texte){
		super();
		setBackground(Color.blue);
 		setPreferredSize( new Dimension(largeur, hauteur ) );
		this.jeu = jeu;
		this.hauteur = hauteur;
		this.largeur = largeur;
        this.texte = texte;
        setMessage();
      	addMouseListener(this);
        aff.start();
        avc.start();
	}

	/**Methode qui permet de jouer un tour pas a pas
     *(on voit le pion descendre jusqu'a sa position finale)*/
	public void avance(){
        if (col!=-1){
            pret = false;
            jeu.joue(col,aff);
            col = -1;
            gagne = jeu.gagne();
            if (gagne==-1) {
                pret = true;
                setMessage();
            }
            else {
                setMessage("Félicitations ! " + jeu.getCouleurGagnant() + " a gagné !");
            }
        }
	}

	/**Methode qui permet l'affichage du panneau*/
	public  void affiche(){
        texte.setText(message);
        repaint();
	}

	/**Methode qui permet de lancer une nouvelle partie
     * (effacer le panneau)*/
	public void effacer(){
        jeu.effacer();
        setMessage();
        pret = true;
        col = -1;
        gagne = -1;
    }

	public void paintComponent(Graphics g){
		super.paintComponent(g);
        jeu.affiche(g,largeur,hauteur);
   }

    public void mouseClicked(MouseEvent e) {}

    /**Methode qui permet de gerer l'evenement de clic qui un tour du jeu*/
    public void mousePressed(MouseEvent ev) {
        int lc = largeur/jeu.getLargeur(); //largeur cellule
        int j = ev.getX() / lc;
		if (jeu.colonneInvalide(j))
		    setMessage("La colonne doit etre entre 0 et "+(jeu.getLargeur()-1)+" !");
        else{
            if (jeu.colonnePleine(j))
                setMessage("La colonne "+j+" est deja pleine !");
            else{
                if (pret) {
                    col = j;
                }
            }
        }
    }

    public void mouseReleased(MouseEvent e) {}

    public void mouseEntered(MouseEvent e) {}

    public void mouseExited(MouseEvent e) {}





    private void setMessage(){
        this.message = "Au "+ jeu.getCouleurJoueur()+" de jouer... ";
    }

    private void setMessage(String message){
        this.message = message;
    }

    private Thread aff = new Thread(){
        @Override
        public void run() {
            while (!interrupted()) {
                affiche();
            }
        }
    };

    private Thread avc = new Thread(){
        @Override
        public void run() {
            while (!interrupted())
                if(pret)
                    avance();
        }
    };




}
