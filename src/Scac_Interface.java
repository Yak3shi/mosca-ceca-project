public abstract interface Scac_Interface{

    public void generate();

    public Casella[] getNeighborhood(int x, int y);

    public boolean pianta(int x, int y, Agente agg);

    public String getStato(int x,int y);

    public Casella getCasella(int x, int y);
}