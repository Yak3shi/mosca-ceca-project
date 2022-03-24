import java.util.ArrayList;

public class Game {

    Scacchiera game = new Scacchiera();

    int turnTimeLimit; //Limite di tempo di attesa perché un thread comunichi la propria scelta. In caso la scelta non arrivi, il thread viene ignorato
    
    boolean allResponded = false; //valore che controlla se tutti gli agenti hanno risposto

    int timeLimit;  //Segna il limite di tempo di una partita (millisecondi)
    
    ArrayList <Agente> playerList = new ArrayList<>(); //Lista a cui attingere per comunicare con i vari giocatori

    //AGGIUNTA AGENTI ALLA PARTITA
    //per il momento è una cosa fatta automaticamente, poi penseremo dopo a come adattarlo al multiplayer online 
    public void generateAgents(){ 
        for (int i = 0; i < 20; i++) {
            int x = game.xy();
            int y = game.xy();
            while (true)
                if (!game.scacchiera[x][y].occupato()) {
                    playerList.add(new Agente("A" + i, game, game.scacchiera[x][y], game.trovaRicarica(x, y), game.getNeighborhood(x, y)));
                    break;
                } else {
                    x = game.xy();
                    y = game.xy();
                }
        }
    }

    Agente Classifica[];  //Classifica agenti 

    //VENGONO PASSATI TEMPO TOTALE E TURNI DESIDERATI, PIU' IL RIFERIMENTO ALLA SCACCHIERA
    public Game(int tempo, Scacchiera partita) {
        timeLimit = tempo;
        game = partita;
    }

    public boolean checkIfNoTime(){
        if(System.currentTimeMillis() < timeLimit){
            endGame("FINE TEMPO");
            return true;
        }
        return false;
    }

    public void endGame(String endmsg){
        System.out.println(endmsg);
        System.out.println("IL VINCITORE E' --> " + Classifica[0].toString()
                            + "\n \nCLASSIFICA FINALE");
        for(Agente a: Classifica){
            System.out.println(a.toString());
        }
    }

    public boolean allMovesReceived(){//Controlla che tutti gli agenti abbiano inviato le loro mosse
        return false;
    }

    public void execTurn(){
        generateAgents();
        while(!checkIfNoTime()){
            // Chiedere agli agenti quali sono le loro mosse
            for (Agente a: playerList){
                a.askMove();                //Chiedo all'agente quale mossa vuole fare.
                checkMove(a);               //Controlla se l'agente ha fornito la sua mossa.
            }

            for (Agente a: playerList){
                switch(a.move){
                    case "": 
                }
            }

            

            //Controlla eventuale concorrenza.  
        }
    }

    public void checkMove(Agente a){
        if(!a.isMoveMade() || a.getMove().equals("")){
            playerList.remove(a);
            System.out.println("AGENT " + a.getName() + " GOT REMOVED FROM THE GAME - ERROR: NO ANSWER GIVEN - DEV TO CHECK WHAT MAY HAVE CAUSED THE ERROR");
        }
    }

    public void updateRanking(){

    }
}