import java.util.ArrayList;

public class Scacchiera implements Scac_Interface {
    public Casella[][] scacchiera;// array bidimensionale di oggetti Casella che compone la scacchiera
    Agente[] agenti;// array contenente i riferimenti agli agenti
    ArrayList<Casella> ricariche;// lista delle caselle con stazione di ricarica

    public Scacchiera() {
        scacchiera = new Casella[100][100];
        agenti = new Agente[20];
        ricariche = new ArrayList<>();
        generate();
    }

    @Override
    // generazione della scacchiera di oggetti Casella
    public void generate() {
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                String stato = stato();
                scacchiera[i][j] = new Casella(i, j, stato);
                if (scacchiera[i][j].getStato().equals("ricarica")) {
                    ricariche.add(scacchiera[i][j]);
                }
            }
        }
        for (int i = 0; i < agenti.length; i++) {
            int x = xy();
            int y = xy();
            while (true)
                if (!scacchiera[x][y].occupato()) {
                    agenti[i] = new Agente("A" + i, this, scacchiera[x][y], trovaRicarica(x, y), getNeighborhood(x, y));
                    scacchiera[x][y].occupa(agenti[i]);
                    break;
                } else {
                    x = xy();
                    y = xy();
                }
        }
    }

    @Override
    // metodo che ritorna un array contenente i riferimenti alle caselle adiacenti
    // alla casella posizione dell'agente
    public Casella[] getNeighborhood(int x, int y) {
        Casella[] adiacenti = new Casella[] {
                scacchiera[x][y - 1],
                scacchiera[x - 1][y - 1],
                scacchiera[x - 1][y],
                scacchiera[x - 1][y + 1],
                scacchiera[x][y + 1],
                scacchiera[x + 1][y + 1],
                scacchiera[x + 1][y],
                scacchiera[x + 1][y - 1]
        };
        return adiacenti;
    }

    @Override
    public String getStato(int x,int y){
        return this.getCasella(x, y).stato;
    }
    
    @Override
    synchronized public boolean pianta(int x, int y, Agente agente) {
        // controllo, con l'utilizzo del boolean 'b', della presenza di caselle
        // possedute adiacenti
        boolean b = false;
        for (Casella c : getNeighborhood(agente.posizione.getX(), agente.posizione.getX())) { // adiacenti
            if (c.getProprietà().equals(agente.nome)) {
                b = true;
                break;
            }
        }
        // caselle possedute adiacenti (costo 4)
        if (b) {
            // controllo che la casella non sia già proprietà di qualcun'altro e che la
            // stoffa dell'agente sia sufficiente
            if (agente.posizione.getProprietà().equals("") && agente.getStoffa() >= 4) {
                // set della proprietà della casella
                agente.getPosizione().setProprietà(agente.nome);
                // set della stoffa dell'agente
                agente.setStoffa(-4);
                return true;
            } else {
                // avviso di impossibilità di piantare la bandiera
                System.out.println("Casella già posseduta in territorio altrui o stoffa insufficiente");
                return false;
            }
            // caselle possedute non adiacenti (costo 8)
        } else {
            // controllo che la casella non sia già proprietà di qualcun'altro e che la
            // stoffa dell'agente sia sufficiente
            if (agente.posizione.getProprietà().equals("") && agente.getStoffa() >= 8) {
                // set della proprietà della casella
                agente.posizione.setProprietà(agente.getNome());
                // set della stoffa dell'agente
                agente.setStoffa(-8);
                return true;
            } else {
                // avviso di impossibilità di piantare la bandiera
                System.out.println("Casella già posseduta in territorio altrui o stoffa insufficiente");
                return false;
            }
        }
    }

    public Casella getCasella(int x, int y) {
        return scacchiera[x][y];
    }

    // ritorna stato casuale per la creazione di una nuova casella
    private String stato() {
        switch ((int) Math.random() * 2) {
            case 1:
                return "risorsa";
            case 2:
                return "ricarica";
            default:
                return "vuota";
        }
    }

    private int xy() {
        return (int) Math.random() * 99;
    }

    // metodo che ritorna il riferimento alla casella con stazione di ricarica più
    // vicina alla posizione dell'agente
    private Casella trovaRicarica(int x, int y) throws IndexOutOfBoundsException {
        Casella ricarica = ricariche.get(0);
        for (Casella c : ricariche) {
            if ((Math.abs(c.getX() - x) + Math.abs(c.getY() - y)) < (Math.abs(ricarica.getX() - x)
                    + Math.abs(ricarica.getY() - y)))
                ricarica = c;
        }
        return ricarica;
    }
    
    

    // metodo ad accesso limitato che gestisce lo spostamento di un agente da una
    // casella ad un'altra
    synchronized public boolean accesso(int x, int y, Casella posizione, Agente agente) {
        // controllo che l'agente possieda abbastanza energia per spostarsi
        if (agente.getEnergia() > 0) {
            // controllo che la casella in cui si vuole spostare l'agente sia libera
            if (!scacchiera[x][y].occupato()) {
                // assegnazione dell'agente alla nuova casella
                scacchiera[x][y].occupa(agente);
                // liberazione della casella da cui si è appena spostato l'agente
                posizione.libera();
                // cambio dei riferimenti(ricarica più vicina, caselle adiacenti, riferimento
                // alla casella di posizione)
                agente.setRicarica(trovaRicarica(x, y));
                agente.setAdiacenti(getNeighborhood(x, y));
                agente.setPosizione(scacchiera[x][y]);
                return true;
            } else {
                // altrimenti avviso di mancato spostamento perché la casella è occupata
                System.out.println(agente.getNome() + ": spostamento negato");
                return false;
            }
        } else {
            // altrimenti avviso di mancato spostamento perché l'energia è insufficiente
            System.out.println("Energia insufficiente");
            return false;
        }
    }

    
    // metodo con accesso limitato per piantare una bandiera utilizzando la stoffa
    // posseduta e ottenere il territorio

    // avvia il funzionamento degli agenti, delle caselle e della scacchiera
    public void esegui() throws InterruptedException {
        for (int i = 0; i < agenti.length; i++)
            agenti[i].start();
        for (int i = 0; i < agenti.length; i++)
            agenti[i].join();
    }

}