import java.util.ArrayList;
import java.util.Scanner;

public class Agente extends Thread implements Ag_Interface {
    Scanner sc = new Scanner(System.in);
    String nome;// nome dell'Agente
    private int energia;// energia dell'agente
    private int stoffa;// stoffa posseduta dall'agente
    Scacchiera scacchiera;// riferimento all'istanza della scacchiera a cui puntano tutti gli altri agenti
    Casella posizione;// riferimento all'istanza della casella in cui si trova l'agente
    Casella ricarica;// riferimento all'istanza della casella con stazione di ricarica più vicina
    Casella[] adiacenti;// array di rifermenti alle istanze delle caselle adiacenti alla casella in cui
                        // si trova l'agente
    ArrayList<Casella> territori;// lista di riferimenti alle istanze delle caselle facenti parte del territorio
                                 // dell'agente

    boolean moveMade; // true se l'agente ha fatto la sua mossa
    String move; //L'agente usa questa variabile per comunicare la sua azione alla classe gioco 
    

    public Agente(String nome, Scacchiera scacchiera, Casella posizione, Casella ricarica, Casella[] adiacenti) {
        this.nome = nome;
        energia = 100;
        stoffa = 40;
        moveMade = false;
        this.scacchiera = scacchiera;
        this.posizione = posizione;
        this.ricarica = ricarica;
        this.adiacenti = adiacenti;
        territori = new ArrayList<>();
        moveMade = false;
        move = "";
    }

    // avvia le azioni degli agenti
    public void run() {
        System.out.println(getNome());
        System.out.println(adiacenti[1].getStato() + "(" + adiacenti[1].getOccupazione() + ")" + "   "
                + adiacenti[2].getStato() + "(" + adiacenti[2].getOccupazione() + ")" + "   " + adiacenti[3].getStato()
                + "(" + adiacenti[3].getOccupazione() + ")\n"
                + adiacenti[0].getStato() + "(" + adiacenti[0].getOccupazione() + ")" + "   " + posizione.getStato()
                + "(" + posizione.getOccupazione() + ")" + "   " + adiacenti[4].getStato() + "("
                + adiacenti[4].getOccupazione() + ")\n"
                + adiacenti[7].getStato() + "(" + adiacenti[7].getOccupazione() + ")" + "   " + adiacenti[6].getStato()
                + "(" + adiacenti[6].getOccupazione() + ")" + "   " + adiacenti[5].getStato() + "("
                + adiacenti[5].getOccupazione() + ")");
        System.out.println("Ricarica più vicina: [" + ricarica.getX() + ", " + ricarica.getY() + "]");
        System.out.println("Territori:");
        for (Casella c : territori)
            System.out.println("[" + c.getX() + ", " + c.getY() + "]");
    }

    public void askMove(){
        int scelta = 1;
        if (nome.equals("A0")) {
            System.out.println("Che cosa vuoi effettuare?\n1.Pianta bandiera\n2.Spostati");
            while (scelta != 1 && scelta != 2) {
                scelta = sc.nextInt();
                switch (scelta) {
                    case 1:
                        pianta();
                        break;
                    case 2:
                        int mossa = 5;
                        System.out.println(
                                "1.basso a sinistra\n2.basso\n3.basso a destra\n4.sinistra\n5.fermo\n6.destra\n7.alto a sinistra\n8.alto\n9.alto a destra");
                        while (mossa < 1 || mossa > 9) {
                            mossa = sc.nextInt();
                            switch (mossa) {
                                case 1:
                                    basso_sinistra();
                                    break;
                                case 2:
                                    basso();
                                    break;
                                case 3:
                                    basso_destra();
                                    break;
                                case 4:
                                    sinistra();
                                    break;
                                case 6:
                                    destra();
                                    break;
                                case 7:
                                    alto_sinistra();
                                    break;
                                case 8:
                                    alto();
                                    break;
                                case 9:
                                    alto_destra();
                                    break;
                                default:
                                    break;
                            }
                        }
                        break;
                    default:
                        break;
                }
            }
        } else {
            scelta = (int) ((Math.random() * 1) + 1);
            switch (scelta) {
                case 1:
                    pianta();
                    break;
                case 2:
                    int mossa = 5;
                    System.out.println(
                            "1.basso a sinistra\n2.basso\n3.basso a destra\n4.sinistra\n5.fermo\n6.destra\n7.alto a sinistra\n8.alto\n9.alto a destra");
                    mossa = (int) ((Math.random() * 8) + 1);
                    switch (mossa) {
                        case 1:
                            basso_sinistra();
                            break;
                        case 2:
                            basso();
                            break;
                        case 3:
                            basso_destra();
                            break;
                        case 4:
                            sinistra();
                            break;
                        case 6:
                            destra();
                            break;
                        case 7:
                            alto_sinistra();
                            break;
                        case 8:
                            alto();
                            break;
                        case 9:
                            alto_destra();
                            break;
                        default:
                            break;
                    }
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    // metodo per compiere l'azione di piantare la bandiera ed entrare in possesso
    // della casella in cui è posizionato
    public void pianta() {
        if (scacchiera.pianta(this.posizione.getX(), this.posizione.getY(), this)) {
            energia--;
            territori.add(posizione);
        }
    }

    @Override
    // getter del nome dell'agente
    public String getNome() {
        return nome;
    }

    @Override
    public int getX() {
        return this.posizione.getX();
    }

    @Override
    public int getY() {
        return this.posizione.getY();
    }

    // getter della stoffa posseduta dall'agente
    public int getStoffa() {
        return stoffa;
    }

    // getter dell'energia dell'agente
    public int getEnergia() {
        return energia;
    }

    // getter del riferimento alla casella in cui si trova l'agente
    public Casella getPosizione() {
        return posizione;
    }

    // setter della posizione dell'agente per quando questo si sposta
    public void setPosizione(Casella posizione) {
        this.posizione = posizione;
    }

    // setter della stazione di ricarica più vicina per quando l'agente si sposta
    public void setRicarica(Casella ricarica) {
        this.ricarica = ricarica;
    }

    // setter delle caselle adiacenti alla posizione dell'agente per quando l'agente
    // si sposta
    public void setAdiacenti(Casella[] adiacenti) {
        this.adiacenti = adiacenti;
    }

    // setter per aggiungere o sottrarre la stoffa posseduta dall'agente quando
    // questo nè raccoglie o nè utilizza
    public void setStoffa(int stoffa) {
        this.stoffa += stoffa;
    }

    // metodo richiamato dalle stazioni di ricarica per ricaricare l'energia
    // dell'agente
    public void addEnergia() {
        energia += 10;
    }

    // spostamento a sinistra
    public void sinistra() {
        /*if (scacchiera.accesso(posizione.getX(), posizione.getY() - 1, posizione, this))
            energia--;
            */
        setMove("sinistra");
        setMoveMade(true);
    }

    // spostamento a alto a sinistra
    public void alto_sinistra() {
        /*if (scacchiera.accesso(posizione.getX() - 1, posizione.getY() - 1, posizione, this))
            energia--;
            setMoveMade(true);*/
        setMove("alto_sinistra");
        setMoveMade(true);
    }

    // spostamento in alto
    public void alto() {
        /*if (scacchiera.accesso(posizione.getX() - 1, posizione.getY(), posizione, this))
            energia--;*/
        setMove("alto");
        setMoveMade(true);
            
    }

    // spostamento in alto a destra
    public void alto_destra() {
        /*if (scacchiera.accesso(posizione.getX() - 1, posizione.getY() + 1, posizione, this))
            energia--;
            */
        setMove("alto_destra");
        setMoveMade(true);
    }

    // spostamento a destra
    public void destra() {
        /*if (scacchiera.accesso(posizione.getX(), posizione.getY() + 1, posizione, this))
            energia--;
            setMoveMade(true);*/
        setMove("destra");
        setMoveMade(true);
    }

    // spostamento in basso a destra
    public void basso_destra() {
        /*if (scacchiera.accesso(posizione.getX() + 1, posizione.getY() + 1, posizione, this))
            energia--;*/
        setMove("basso_destra");
        setMoveMade(true);
    }

    // spostamento in basso
    public void basso() {
        /*if (scacchiera.accesso(posizione.getX() + 1, posizione.getY(), posizione, this))
            energia--;*/
        setMove("basso");
        setMoveMade(true);
    }

    // spostamento in basso a sinistra
    public void basso_sinistra() {
        /*if (scacchiera.accesso(posizione.getX() + 1, posizione.getY() - 1, posizione, this))
            energia--;*/
        setMove("basso_sinistra");
        setMoveMade(true);
    }

    public boolean isMoveMade() {//metodo usato dalla classe gioco per verificare se l'agente ha fatto la sua mossa
        return moveMade;
    }

    //imposta il valore a true o false se necessario
    //se true, indica che l'agente ha deciso la sua mossa e attende di sapere dalla classe Gioco se può farla o meno
    public void setMoveMade(boolean moveMade) {
        this.moveMade = moveMade;
    }

    //restituisce la mossa che l'agente ha scelto di fare
    //serve alla classe gioco per sapere in quale casella l'agente vuole andare, per vedere se essa entra in conflitto con quella di un altro agente
    public String getMove() {
        return move;
    }

    //modifica il valore della stringa contenente la mossa che vuole fare l'agente
    public void setMove(String move) {
        this.move = move;
    }

    

}