import java.util.ArrayList;

public class Game {

    Scacchiera game = new Scacchiera();

    int turnTimeLimit; //Limite di tempo di attesa perché un thread comunichi la propria scelta. In caso la scelta non arrivi, il thread viene ignorato
    
    boolean allResponded = false; //valore che controlla se tutti gli agenti hanno risposto

    int timeLimit;  //Segna il limite di tempo di una partita (millisecondi)
    int turnlimit;  //Contiene il numero massimo di turni in una partita. Viene decrementato ogni volta che finisce il turno;

    ArrayList <Agente> playerList = new ArrayList<>(); //Lista a cui attingere per comunicare con i vari giocatori

    public void addAgent(){
        //AGGIUNTA AGENTE
    }

    Agente Classifica[];  //Classifica agenti 

    //VENGONO PASSATI TEMPO TOTALE E TURNI DESIDERATI, PIU' IL RIFERIMENTO ALLA SCACCHIERA
    public Game(int tempo, int turni, Scacchiera partita) {
        timeLimit = tempo;
        turnlimit = turni;
        game = partita;
    }

    public boolean checkIfNoTime(){
        if(System.currentTimeMillis() < timeLimit){
            endGame("FINE TEMPO");
            return true;
        }
        return false;
    }

    public boolean checkIfAllTurnsEnded(){
        if(turnlimit == 0){
            endGame("TURNI TERMINATI");
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

    public void exclude(){ // metodo per escludere un agente dal gioco

    }

    public boolean allMovesReceived(){//Controlla che tutti gli agenti abbiano inviato le loro mosse
        return false;
    }

    public void execTurn(){
        if(!checkIfAllTurnsEnded() && !checkIfNoTime()){
            while(!allMovesReceived()/*tutti hanno dato la loro mossa*/){
                for(Agente a: playerList){ // per ogni agente in game
                    //chiama il metodo per conoscere la risposta dell'agente
                    if(!a.isMoveMade() && timeOut()/*nessuna risposta da questo agente && limite di tempo superato */){
                        playerList.remove(a); // agente escluso
                        System.out.println(a.nome + " ELIMINATED DUE TO: out of time \n Developer to check if there are some errors that could have stopped it");
                    }
                    else if(a.getMove() != null/*nuova risposta */){
                        //confronta con le altre risposte
                        //se trova che l'agente 'a' è in collisione con un altro agente, restituisce anche la posizione di quel secondo agente 'b' 
                        if(/*collisione tra due risposte */){
                            
                        }
                    }
                }
                //esegui le mosse
            }

            turnlimit--; //diminuisci il conteggio dei turni  
        }else
            break;
        
    }

    public void collisionRemoval(Agente a, Agente b){
        int random = (int)(Math.random()*2) + 1;
        switch(random){
            case 1: 
                //richiedi nuova risposta ad agente a;
                break;
            case 2: 
                //richiedi nuova risposta ad agente b;
                break;    
        }
    }
}