public class Ricarica extends Thread{
    int x;//coordinata x
    int y;//coordinata y
    Agente agente;//riferimento all'agente che si trova nella casella della stazione di ricarica

    public Ricarica(int x, int y, Agente agente){
        this.x = x;
        this.y = y;
        this.agente = agente;
    }

    //cambia il rifermento 'agente' all'agente che si trova nella casella della stazione di ricarica
    public void setAgente(Agente agente){
        this.agente = agente;
    }

    //avvia l'azione di ricarica
    public void run(){
        while(agente.getPosizione().getX()==x && agente.getPosizione().getY()==y){
            agente.addEnergia();
        }
    }

}