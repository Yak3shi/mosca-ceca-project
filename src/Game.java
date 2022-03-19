import java.util.ArrayList;

public class Game {

    int timeLimit;  //Segna il limite di tempo di una partita (millisecondi)
    int turnlimit;  //Contiene il numero massimo di turni in una partita. Viene decrementato ogni volta che finisce il turno;

    ArrayList <Agente> playerList = new ArrayList<>(); //Lista a cui attingere per comunicare con i vari giocatori

    public void addAgent(){
        //AGGIUNTA AGENTE
    }

    Agente Classifica[];  //Classifica agenti 

    public Game(int tempo, int turni) {
        timeLimit = tempo;
        turnlimit = turni;
    }

    public void checkIfNoTime(){
        if(System.currentTimeMillis() < timeLimit){
            endGame("FINE TEMPO");
        }
    }

    public void checkIfAllTurnsEnded(){
        if(turnlimit == 0)
            endGame("TURNI TERMINATI");
    }

    public void endGame(String endmsg){
        System.out.println(endmsg);
        System.out.println("IL VINCITORE E' --> " + Classifica[0].toString()
                            + "\n \nCLASSIFICA FINALE");
        for(Agente a: Classifica){
            System.out.println(a.toString());
        }
    }



    
}