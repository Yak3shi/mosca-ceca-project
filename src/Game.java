import java.util.ArrayList;

public class Game {

    Scacchiera scacchiera = new Scacchiera();
    int timeLimit;  //Segna il limite di tempo di una partita (millisecondi)
    
    ArrayList <Agente> playerList = new ArrayList<>(); //Lista a cui attingere per comunicare con i vari giocatori

    //AGGIUNTA AGENTI ALLA PARTITA
    //per il momento è una cosa fatta automaticamente, poi penseremo dopo a come adattarlo al multiplayer online 
    public void generateAgents(){ 
        for (int i = 0; i < 20; i++) {
            int x = scacchiera.xy();
            int y = scacchiera.xy();
            while (true)
                if (!scacchiera.scacchiera[x][y].occupato()) {
                    playerList.add(new Agente("A" + i, this, scacchiera, scacchiera.scacchiera[x][y], scacchiera.trovaRicarica(x, y), scacchiera.getNeighborhood(x, y)));
                    break;
                } else {
                    x = scacchiera.xy();
                    y = scacchiera.xy();
                }
        }
    }

    Agente Classifica[];  //Classifica agenti 

    //VENGONO PASSATI TEMPO TOTALE e IL RIFERIMENTO ALLA SCACCHIERA
    public Game(int tempo, Scacchiera partita) {
        timeLimit = tempo;
        scacchiera = partita;
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

    public void execTurn(){
        generateAgents();
        while(!checkIfNoTime()){
            for (Agente a: Classifica){
                System.out.println(a.getName());
            }
        }
    }

    public void verifyMove(Agente a, String mossa){
        switch(mossa){
            case "sinistra":        // RICHIESTA DI MOSSA A SINISTRA
                if (scacchiera.accesso(a.posizione.getX(), a.posizione.getY() - 1, a.posizione, a)) // se mossa è possibile
                    a.setEnergia(a.getEnergia() - 1);   // diminuisci l'energia
                else
                    a.askMove();                        //se la mossa non è disponibile, ne chiede un'altra
                
                break;
            
            case "alto_sinistra":   // RICHIESTA DI MOSSA IN ALTO A SINISTRA
                if (scacchiera.accesso(a.posizione.getX() - 1, a.posizione.getY() - 1, a.posizione, a)) // se mossa è possibile
                    a.setEnergia(a.getEnergia() - 1);   // diminuisci l'energia
                else
                    a.askMove();                        //se la mossa non è disponibile, ne chiede un'altra
                
                break;

            case "alto":            // RICHIESTA DI MOSSA IN ALTO
                if (scacchiera.accesso(a.posizione.getX() - 1, a.posizione.getY() - 1, a.posizione, a)) // se mossa è possibile
                    a.setEnergia(a.getEnergia() - 1);   // diminuisci l'energia
                else
                    a.askMove();                        //se la mossa non è disponibile, ne chiede un'altra
                
                break;

            case "alto_destra":     // RICHIESTA DI MOSSA IN ALTO A DESTRA
            if (scacchiera.accesso(a.posizione.getX() - 1, a.posizione.getY() + 1, a.posizione, a)) // se mossa è possibile
                    a.setEnergia(a.getEnergia() - 1);   // diminuisci l'energia
                else
                    a.askMove();                        //se la mossa non è disponibile, ne chiede un'altra
                
                break; 
                
            case "destra":          // RICHIESTA DI MOSSA A DESTRA
            if (scacchiera.accesso(a.posizione.getX(), a.posizione.getY() + 1, a.posizione, a)) // se mossa è possibile
                    a.setEnergia(a.getEnergia() - 1);   // diminuisci l'energia
                else
                    a.askMove();                        //se la mossa non è disponibile, ne chiede un'altra
                
                break; 
                
            case "basso_destra":    // RICHIESTA DI MOSSA IN BASSO A DESTRA
            if (scacchiera.accesso(a.posizione.getX() + 1, a.posizione.getY() + 1, a.posizione, a)) // se mossa è possibile
                    a.setEnergia(a.getEnergia() - 1);   // diminuisci l'energia
                else
                    a.askMove();                        //se la mossa non è disponibile, ne chiede un'altra
                
                break;  
                
                
            case "basso":           // RICHIESTA DI MOSSA IN BASSO
            if (scacchiera.accesso(a.posizione.getX() + 1, a.posizione.getY(), a.posizione, a)) // se mossa è possibile
                    a.setEnergia(a.getEnergia() - 1);   // diminuisci l'energia
                else
                    a.askMove();                        //se la mossa non è disponibile, ne chiede un'altra
                
                break;      

            case "basso_sinistra":  // RICHIESTA DI MOSSA IN BASSO A SINISTRA
            if (scacchiera.accesso(a.posizione.getX() + 1, a.posizione.getY() - 1, a.posizione, a)) // se mossa è possibile
                    a.setEnergia(a.getEnergia() - 1);   // diminuisci l'energia
                else
                    a.askMove();                        //se la mossa non è disponibile, ne chiede un'altra
                
                break;

            case "pianta":          // RICHIESTA PIANTARE BANDIERA
                if (scacchiera.pianta(a.posizione.getX(), a.posizione.getY(), a)) {
                    a.setEnergia(a.getEnergia() - 4);
                    a.territori.add(a.posizione);
                }    
                break;
        }
    }

    // Fa partire l'esecuzione degli agenti e li fa joinare
    public void esegui() throws InterruptedException {
        for(Agente a: playerList)
            a.start();
        for(Agente a: playerList)
            a.join();
    }

    // Aggiorna la classifica in base a
            // - Territori conquistati
            // - (in caso di pari territori) Stoffa (territori potenzialmente conquistabili)
            // - (in caso di pari stoffa) Energia (potenziale di movimento)
    public void updateRanking(){
        Agente temp = new Agente();

        for (int i = Classifica.length() - 1; i > 0; i--){
            for (int j = 0; j < i; j++){
                if (Classifica[j].territori.size > Classifica [j + 1].territori.size){ // Swappa due agenti nella classifica in base a chi dei due ha più territori conquistati
                    temp = Classifica[j];
                    Classifica[j] = Classifica[j+1];
                    Classifica[j+1] = temp;
                } else if (Classifica[j].territori.size = Classifica [j + 1].territori.size){ // Se due agenti hanno gli stessi territori, controlla chi hai più stoffa
                    if(Classifica[j].stoffa > Classifica[j+1].stoffa){                              // Swappa due agenti in base a chi dei due ha più stoffa
                        temp = Classifica[j];
                        Classifica[j] = Classifica[j+1];
                        Classifica[j+1] = temp;
                    } else if (Classifica[j].stoffa = Classifica[j+1].stoffa){                // Se due agenti hanno anche la stessa stoffa
                        if(Classifica[j].energia > Classifica[j+1].energia){                        // Swappa due agenti in base a chi dei due ha più energia
                            temp = Classifica[j];
                            Classifica[j] = Classifica[j+1];
                            Classifica[j+1] = temp;
                        }
                    }
                }
            }
        } 
    }
}