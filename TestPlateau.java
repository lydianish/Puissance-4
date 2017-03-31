import java.util.Scanner;

public class TestPlateau{
	public static void main(String[] args){
		Plateau p = new Plateau();
		Scanner sc = new Scanner(System.in);
		while(true){
			if(!p.estPlein()){
				System.out.println("Au joueur " + p.getJoueurCourant() + " de jouer ");
				System.out.println("Saisissez le numero de la colonne dans la quelle vous souhaitez jouer (vous avez le choix entre 0 et 6) : ");
	    			int i = sc.nextInt();
				while((i<0) || (i>6)){
					System.out.println("Erreur " + i + " n'est pas une colonne valide! Choisissez une colonne entre 0 et 6");
					System.out.println();
					System.out.println("Au joueur " + p.getJoueurCourant() + " de jouer ");
					System.out.println("Saisissez le numero de la colonne dans la quelle vous souhaitez jouer (vous avez le choix entre 0 et 6) : ");
					i = sc.nextInt();
				}
				try{
					p.joue(i);
				}catch(ColonnePleineException e){
					System.out.println(e.getMessage());
					System.out.println("recommencez : ");
				}
				p.setPret(true);
				try{
					p.imprime();
				}catch (InterruptedException e) {
					e.printStackTrace();
    				}
				int c = p.gagne();
				if(c != -1){
					System.out.println("Le joueur " + c + " a gagné ! ");
					System.out.println("Souhaitez_vous continuer ? (Oui/Non)");
					sc.nextLine();
					String str = sc.nextLine();
					System.out.println("Votre choix est  : " + str);
					if((str.equals("Oui")) || (str.equals("OUI")) || (str.equals("O")) || (str.equals("oui")) || (str.equals("o"))){
						System.out.println("Nouvelle partie : ");
						p.initialise();
					}else{
						if((str.equals("Non")) || (str.equals("NON")) || (str.equals("N")) || (str.equals("non")) || (str.equals("n"))){
							System.out.println("Fin de la partie! Au revoir!");
							return;
						}
					}
				}
			}else{
				System.out.println("Fin de la partie! Le plateau est plein!");
				System.out.println("Souhaitez_vous entammer une nouvelle partie ? (Oui/Non)");
				String str = sc.nextLine();
				System.out.println("Votre choix est  : " + str);
				if((str.equals("Oui")) || (str.equals("OUI")) || (str.equals("O")) || (str.equals("oui")) || (str.equals("o"))){
					System.out.println("Nouvelle partie : ");
					p.initialise();;
				}else{
					if((str.equals("Non")) || (str.equals("NON")) || (str.equals("N")) || (str.equals("non")) || (str.equals("n"))){
						System.out.println("Fin de la partie! Au revoir!");
						return;
					}

				}
			}
		}
	}
}
