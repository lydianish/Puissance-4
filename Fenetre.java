import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Hashtable;

/**
 * Classe decrivant la fenetre gaphique
 */
public class Fenetre extends JFrame {

	/**Constructeur de la fenetre
     * @param jeu le plateau de jeu*/
    public Fenetre(Plateau jeu){
        super();
        this.jeu = jeu;

		largeurP = jeu.getLargeur()*pixels;
		hauteurP = jeu.getHauteur()*pixels;

        setTitle("Puissance 4");
        setSize(largeurP,hauteurP + 2*hauteurM);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        contenu = getContentPane();
        contenu.setLayout(new BoxLayout(contenu, BoxLayout.Y_AXIS));
 		  
        top = new JPanel();
        top.setPreferredSize( new Dimension(largeurP, hauteurM ) );
        texte = new JLabel();
        top.add(texte);

        middle = new Panneau(this.jeu,largeurP,hauteurP,texte);
 		  
        bottom = new JPanel();
        bottom.setPreferredSize( new Dimension(largeurP, hauteurM ) );
        slider = mySlider();
        bottom.add(slider);
        newgame = new JButton("Nouvelle Partie");
		bottom.add(newgame);
		quit = new JButton("Quitter");
        bottom.add(quit);

        contenu.add(top);
        contenu.add(middle);
        contenu.add(bottom);
 		  
        pack();
        setVisible(true);

		newgame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				middle.effacer();
			}
		});

        quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                jeu.setAttente(((JSlider)e.getSource()).getValue());
            }
        });

    }
    
    
    private Container contenu;
	 private JPanel top;
	 private Panneau middle;
	 private JPanel bottom;
	 private Plateau jeu;
    
    private int pixels = 100;
	private int largeurP;
    private int hauteurP;
	private int hauteurM = 50;

    private JButton newgame;
    private JButton quit;
    private JLabel texte;
    private JSlider slider;

	private JSlider mySlider(){
		JSlider s = new JSlider(JSlider.HORIZONTAL,10,100,100);
		//Create the label table
		Hashtable labelTable = new Hashtable();
		labelTable.put( new Integer( 10), new JLabel("Rapide") );
		labelTable.put( new Integer( 100), new JLabel("Lent") );
		s.setLabelTable( labelTable );
		s.setPaintLabels(true);
		return s;
	}

}
