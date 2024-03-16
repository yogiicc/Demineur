/**
 *
 * @author louise
 */
public abstract class Case {
    //Attributs
    int nbVMines;
    boolean visible;
    boolean propage;
    
    //Accesseurs
    public boolean isVisible(){
        return visible;
    }
    public void setVisible(boolean v){
        visible = v;
    }
    
    public boolean isPropage(){
        return propage;
    }
    public void setPropage(boolean p){
        propage = p;
    }
    
    public abstract boolean isVide();
    
    public int getNbVMines(){
        return nbVMines;
    }
    public void setNbVMines(int vm){
        nbVMines=vm;
    }
    
    
    public abstract String afficherCaseVisible();
    
    //Méthode pour aafficher une case en fonction de son état, traitement
    public String afficheCase(int i,int j){
        String s="";
        //si la case est visible
        if(isVisible()){
           s = afficherCaseVisible();
        }
        else//sinon, la case n'est pas visible et on affiche les coordonnées de la case 
            s= "[" + i + ":" + j + "]";
        return s;//on retourne l'affichage de la case
    }
}
