package Modelo;

public class Nivel {
    
    private int nivel;
    private int num_pares_cartas;
    private int col;
    private int fil;
    
    public Nivel (int n, int npc, int c, int f){
        nivel = n;
        num_pares_cartas = npc;
        col = c;
        fil = f;
    }
    
    public int getNivel(){
        return nivel;
    }
    
    public int getNumParesCartas(){
        return num_pares_cartas;
    }
    
    public int getColumnas(){
        return col;
    }
    
    public int getFilas(){
        return fil;
    }
}
