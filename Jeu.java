import java.util.*;

/**
 *
 * @author louise
 */
public class Jeu {
    
    //Attributs
    private Plateau plateau;
    private int niveau;
    private int dLig;
    private int dCol;
    private int nbMines;

    //Afficher un message et lire un entier entre deux valeurs jusqu'à ce que l'entrée soit correctre
    public int lireOptions(String msg, int minI, int maxI){
        Scanner sc = new Scanner(System.in);
        boolean Ok = false;
        int nb = 0;
        while(!Ok){
            System.out.print(msg);
            nb = sc.nextInt();
            if(nb>=minI && nb<=maxI)
                Ok=true;
            else 
                System.out.println("Le nombre doit etre compris entre " + minI + " et " + maxI +". Recommencez.");
        }
        return nb;
    }

    //Methode pour les Options choisies en début de partie
    public void options(){
        niveau = lireOptions("Choisissez un niveau (1-9) : ",1,9);
        dLig = lireOptions("Choisissez le nombre de ligne (3-10) : ",2,10);
        dCol = lireOptions("Choisissez le nombre de colones (3-10) : ",2,10);
        //Calcul du nombre de mines
        nbMines = (dLig*dCol*niveau)/10;
        //Affichage des options choisies
        System.out.println("Niveau : " + niveau);
        System.out.println("Nombres de Mines : " + nbMines);
    }

    //Méthode initialisation du plateau, appel au constructeur de plateau
    public void initPlateau(){
        plateau = new Plateau(dLig, dCol,nbMines);
    }

    //Méthode pour commencer à jouer
    public void joue(){
        plateau.affichePlateauTest();
        plateau.setToutVisible();
        plateau.affichePlateau();//affichage du plateau
        int etat = 1;//1 pour continuer, 2 pour gagné, 3 pour perdu
        int nbCoups = 0;
        while(etat==1){
            System.out.println("Nombres de cases cachées "+ plateau.nbCcachees);
            //choix de la case
            int lig = lireOptions("Choisissez la ligne de la case : ", 0, dLig-1);
            int col = lireOptions("Choisissez la colonne de la case : ", 0, dCol-1);
            nbCoups++;
            etat = plateau.traitementCoup(lig,col);//appel de la méthode pour traiter le coup
            if(etat != 1){// si on gagne ou perd
                plateau.toutVisible();// affichage de tout le plateau
                if(etat == 2)//si Gagné, afficahge avec nombre de coups
                    System.out.println("Vous avez gagné en "+ nbCoups + " coups.");
                if(etat == 3) // si perdu 
                    System.out.println("Vous avez perdu après "+ nbCoups + " coups.");
            }
            plateau.affichePlateau();//si on continue on affiche le plateau modifié après le traitement du coup
        }
    }
}
