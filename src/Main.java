public class Main{
    public static void main (String args[]){
        Scacchiera s = new Scacchiera();
        Game g = new Game(60000, s);
        
        while(!g.checkIfNoTime()){
            g.esegui();
        }
    }
}