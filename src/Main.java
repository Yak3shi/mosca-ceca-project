public class Main{
    public static void main (String args[]) throws InterruptedException{
        Scacchiera s = new Scacchiera();
        Game g = new Game(60000, s);
        
        g.execTurn();
    }
}