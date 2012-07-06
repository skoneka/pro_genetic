/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package algorytmygenetyczne;

/**
 *
 * @author Tomek
 */

class Lista{
    
    private int[] lista = new int[8];
    private int counter = 0;
    Lista(){
        for(int i = 0; i < 8; i++)
            lista[i] = -1;
    }
    public boolean sprawdz(int wezel){
        for(int i = 0; i < counter; i++)
            if(wezel == lista[i])
                return true;
        return false;
    }
    public int ile(int index){
        int tmp = 0;
        for(int i = 0; i < counter; i++)
            if(lista[i] != -1){
                tmp+= 1;
            }
        return tmp;
        
    }
    public void dodaj(int wezel){
        if(lista.length==(counter)){
            int[] we = new int[4+lista.length];
            int i = 0;
            for(i = 0; i < lista.length; i++)
                we[i]=lista[i];
            lista = we;    
            lista[i] = -1;
            lista[i+1] = -1;
            lista[i+2] = -1;
            lista[i+3] = -1;
        }
        lista[counter++] = wezel;
    }
    public int[] zwroc(){
        return lista;
    }
    public String wypisz(int index){
        String s = "Indeksy wezlow bezposrednio sasiadujacych z wezlem o indeksie "+index+" to: ";
        for(int i = 0; i < counter;i++)
               s+=lista[i]+ " ";
        return s; 
    }
}

public class ListySasiedztwa {
    private Lista[] sas;
    public ListySasiedztwa(int dlugosc){
        sas = new Lista[dlugosc];
        for(int i = 0; i < dlugosc;i++)
            sas[i] = new Lista(); 
    }
    public boolean Czy_jest_krawedz(int jeden, int dwa){
        return sas[jeden].sprawdz(dwa);
    }
    public void dodaj_polaczenie(int jeden, int dwa){
        sas[jeden].dodaj(dwa);
    }
    public String wypisz_wezly(int index){
        return sas[index].wypisz(index);
    }
    public int ile_ma(){
        int tmp = 0;
        for(int i = 0; i < sas.length;i++)
            tmp+=sas[i].ile(i);
        return tmp;
    }
    public void wypisz(){
        System.out.println(sas.length);
        for(int i = 0; i < sas.length; i++)
            if(sas != null)
                System.out.println(sas[i].wypisz(i+1));
            else
                System.out.println("null");
    }
}


