import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author louise
 */
public class Plateau {
    //Attributs
    private Case[][] plateau;
    private int dLig;
    private int dCol;
    private int nbMines;
    private ArrayList<Coord> voisins[];
    public int nbCcachees;

    //Accesseurs
     public int getDLig(){
        return this.dLig;
    }
    public int getDCol(){
        return this.dCol;        
    }
    public int getNbMines(){
        return this.nbMines;
    }
    
    //méthode pour rendre visible une case
    public void setVisible(int l, int c){
        Case macase = plateau[l][c];
        if(!macase.isVisible())
            nbCcachees--;
        macase.setVisible(true);
    }
    public boolean isVisible(int l, int c){
        Case macase = plateau[l][c];
        return macase.isVisible();
    }
    
    public void toutVisible(){
        for(int i=0; i<dLig ; i++){
            for(int j=0; j<dCol; j++){
               Case c = plateau[i][j];
               c.setVisible(true);
            }
        }
    }
    
    public void setToutVisible(){
        for(int i=0; i<dLig ; i++){
            for(int j=0; j<dCol; j++){
               Case c = plateau[i][j];
               c.setVisible(false);
            }
        }
    }
    

    //Constructeur Standart avec 3 paramètres
    public Plateau(int dl, int dc, int nbm){
        //Initialisation des attributs
        this.dLig=dl;
        this.dCol=dc;
        this.nbMines=nbm;
        this.nbCcachees= dl * dc; //nombres de cases cachées
        this.plateau= new Case[this.dLig][this.dCol];
        //Initialisation du plateau
        //On place les mines
        int m=0;
        while(m<this.nbMines){
            int l=(int)(this.dLig*Math.random());
            int c=(int)(this.dCol*Math.random());
            if(plateau[l][c]==null){
                plateau[l][c]=new Mine();
                m++;
            }
        }
        //On initialise le reste des cases en case Vide
        for(int i=0; i<dLig; i++){
            for(int j=0; j<dCol; j++){
                if(plateau[i][j]==null)
                    plateau[i][j]=new Vide();
            }
        }
        initAideVoisinage();
        for(int i=0; i<dLig; i++){
            for(int j=0; j<dCol; j++){
                initVoisins(i,j);
            }
        }
        
    }

    //Méthode pour l'affichage du Plateau
    public void affichePlateau(){
        String s="";
        for(int i=0; i<dLig ; i++){
            for(int j=0; j<dCol; j++){
               Case c = plateau[i][j];
               s += c.afficheCase(i,j);
            }
            s += "\n";
        }
        System.out.println(s);
            
    }
    
    public void affichePlateauTest(){
        toutVisible();
        String s="";
        for(int i=0; i<dLig ; i++){
            for(int j=0; j<dCol; j++){
               Case c = plateau[i][j];
               s += c.afficheCase(i,j);
            }
            s += "\n";
        }
        System.out.println(s);
    }
    
    //Méthode pour traiter le cas de la case choisie
    public int traitementCoup(int i, int j){
        Case macase = plateau[i][j];
        //on vérifie que le case n'a pas déjà etait jouée
        if(macase.isVisible()){
            System.out.println("Choisissez une case non-jouée");
            return 1;
        }
        // on affiche la case
        setVisible(i,j);
        //si il y a une mine dans la case, on retourne 3 pour perdu
        if(!macase.isVide()){
            ((Mine)macase).setMineJouee(true);
            return 3;
        }
        //si elle n'a pas de mines en voisin, on entraine la propagation sur ses mines voisines
        if(macase.getNbVMines()==0)
            propagation(i,j);
        if(nbCcachees <= nbMines)
            return 2;
        return 1; 
    }
    
    //Méthode pour la propagation sur une case
    public void propagation(int i, int j){
        Case caseP = plateau[i][j];
        //La case est definie en propagé, pour éviter de propage 2 fois.
        caseP.setPropage(true);
        //On récupére la liste de voisins de la case
        ArrayList<Coord> lstv= getVoisins(i,j);
        //On utilise une boucle sur la liste des voisins sur ligne et colonne
        for(Coord cl:lstv){
            int l = i + cl.l;
            int c = j + cl.c;
            Case v = plateau[l][c];//on récupère le voisin au coordonnées l, c
            //si c'est vide, et pas encore propagée
            if(v.isVide() && !v.isPropage()){
                //on definie en propagée
                setVisible(l,c);
                //si ce voisin n'a pas de mines en voisins, on appelle la propagation sur cette case
                if (v.getNbVMines() == 0)
                    propagation(l,c);
            }
        }
    }

    
    //j'aurai du créer une classe Voisinage....
    public void initVoisins(int i,int j){
        ArrayList<Coord> lstv= getVoisins(i,j);
        int nbVoisins=0;
        for(Coord cl:lstv){
            int l= i+cl.l;
            int c = j+cl.c;
            Case v = plateau[l][c];
            if(!v.isVide())
                nbVoisins++;
        }
        Case macase=plateau[i][j];
        macase.setNbVMines(nbVoisins);
    }
    
    public ArrayList<Coord> getVoisins(int i, int j){
        int casL;
        if(i==0)
            casL=0;
        else if(i==dLig-1)
            casL=2;
        else 
            casL=1;
        
        int casC;
        if(j==0)
            casC=0;
        else if(j==dCol-1)
            casC=2;
        else 
            casC=1;
        
        int casLC= 3*casL+casC;
        return voisins[casLC];
    }
    
    public void initAideVoisinage(){
        ArrayList lst;
        voisins = new ArrayList[9];
        lst = new ArrayList(3);
        lst.add(new Coord(0,1));
        lst.add(new Coord(1,0));
        lst.add(new Coord(1,1));
        voisins[0]=lst;
        
        lst = new ArrayList(5);
        lst.add(new Coord(0,-1));
        lst.add(new Coord(0,1));
        lst.add(new Coord(1,-1));
        lst.add(new Coord(1,0));
        lst.add(new Coord(1,1));
        voisins[1]=lst;
        
        lst = new ArrayList(3);
        lst.add(new Coord(0,-1));
        lst.add(new Coord(1,-1));
        lst.add(new Coord(1,0));
        voisins[2]=lst;
        
        lst = new ArrayList(5);
        lst.add(new Coord(-1,0));
        lst.add(new Coord(-1,1));
        lst.add(new Coord(0,1));
        lst.add(new Coord(1,1));
        lst.add(new Coord(1,0));
        voisins[3]=lst;
        
        lst = new ArrayList(8);
        lst.add(new Coord(-1,-1));
        lst.add(new Coord(-1,0));
        lst.add(new Coord(-1,1));
        lst.add(new Coord(0,-1));
        lst.add(new Coord(0,1));
        lst.add(new Coord(1,-1));
        lst.add(new Coord(1,0));
        lst.add(new Coord(1,1));
        voisins[4]=lst;
        
        lst = new ArrayList(5);
        lst.add(new Coord(-1,0));
        lst.add(new Coord(-1,-1));
        lst.add(new Coord(0,-1));
        lst.add(new Coord(1,-1));
        lst.add(new Coord(1,0));
        voisins[5]=lst;
        
        lst = new ArrayList(3);
        lst.add(new Coord(-1,0));
        lst.add(new Coord(-1,1));
        lst.add(new Coord(0,1));
        voisins[6]=lst;
        
        lst = new ArrayList(5);
        lst.add(new Coord(0,-1));
        lst.add(new Coord(-1,-1));
        lst.add(new Coord(-1,0));
        lst.add(new Coord(-1,1));
        lst.add(new Coord(0,1));
        voisins[7]=lst;
        
        lst = new ArrayList(3);
        lst.add(new Coord(0,-1));
        lst.add(new Coord(-1,-1));
        lst.add(new Coord(-1,0));
        voisins[8]=lst;
    }
}
