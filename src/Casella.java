public class Casella {
    int x;//coordinata x
    int y;//coordinata y
    String stato;//stato della casella("risorsa", "ricarica", "vuota")
    int stoffa;//numero di unità di stoffa presenti
    Ricarica ricarica;//riferimento a un'eventuale stazione di ricarica(solo se lo stato è "ricarica")
    boolean occupato;//boolean per segnalare se la casella è occupata da un agente oppure no
    Agente agente_occupante;//riferimento all'agente che occupa la casella
    String proprietà;//nome dell'agente che occupa la casella("" se non è occupata)

    public Casella(int x, int y, String stato){
        this.x = x;
        this.y = y;
        this.stato = stato;
        if(stato.equals("stoffa")) //Se la casella deve contenere stoffa
            stoffa = (int)(Math.random()*11); //Genera un numero casuale di stoffa da 1 a 11
        else
            stoffa = 0;
        if(stato.equals("ricarica"))
            ricarica = new Ricarica(x, y, agente_occupante);
        else
            ricarica = null;
        occupato = false;
        agente_occupante = null;
        proprietà = "";
    }
    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public String getStato(){
        return stato;
    }

    public String getProprietà(){
        return proprietà;
    }

    public void setProprietà(String nome){
        proprietà = nome;
    }

    public boolean occupato(){
        return occupato;
    }

    public String getOccupazione(){
        if(occupato())
            return "occupato";
        else
            return "libero";
    }

    //metodo per l'occupazione di una casella
    public void occupa(Agente agente){
        occupato = true;
        agente_occupante = agente;
        switch(stato){
            //se la casella ha stoffa viene data all'agente occupante
            case "stoffa":
                if((agente_occupante.getStoffa()+stoffa)>100){
                    agente_occupante.setStoffa(stoffa - (100-agente_occupante.getStoffa()));
                    stoffa -= (100-agente_occupante.getStoffa());
                }else{
                    agente_occupante.setStoffa(stoffa);
                    stoffa = 0;
                }
            break;
            //se la casella ha una stazione di ricarica viene richiamato il metodo di ricarica dell'oggetto Ricarica
            case "ricarica":
                ricarica.setAgente(agente_occupante);
                ricarica.start();
            break;
            default:
            break;
        }
    }

    //metodo per la liberzione di una casella
    public void libera(){
        occupato = false;
        agente_occupante = null;
    }
}