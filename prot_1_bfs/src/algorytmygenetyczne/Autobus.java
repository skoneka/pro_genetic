/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package algorytmygenetyczne;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Random;

/**
 *
 * @author Tomek
 */
public class Autobus {
    private int szerokosc;
    private int dlugosc;
    
    private int[][] siedzenia;
    private int ocena;
    private int wygoda;
    private int koszt;
    Random random = new Random();
    public boolean zakres(int a, int b){
        if((a>=0)&&(a<=dlugosc-1)&&(b>=0)&&(b<=szerokosc-1))
            return true;
        return false;
    }
    public int bfs(){
       String s = ""+(dlugosc-1)+","+0;
       Queue<String> queue = new LinkedList<String>();
       queue.offer(s);
       ListySasiedztwa listy = new ListySasiedztwa(dlugosc);
       listy.dodaj_polaczenie(dlugosc-1, 0);
       while(queue.isEmpty()!=true){
           String ciag = queue.remove();
           int pier = Integer.parseInt(ciag.substring(0, ciag.indexOf(",")));
           int grug = Integer.parseInt(ciag.substring(ciag.indexOf(",")+1));
           pier=pier-1;
           if(zakres(pier, grug)==true)
               if(listy.Czy_jest_krawedz(pier, grug)==false){
                   listy.dodaj_polaczenie(pier, grug);
                   if(((zwroc_wartosc(pier,grug))!=0)&&((zwroc_wartosc(pier,grug))!=-1)){
                       queue.offer(""+pier+","+grug);
                   }
               }
           pier=pier+1;
           grug=grug-1;
           if(zakres(pier, grug)==true)
               if(listy.Czy_jest_krawedz(pier, grug)==false){
                   listy.dodaj_polaczenie(pier, grug);
                   if(((zwroc_wartosc(pier,grug))!=0)&&((zwroc_wartosc(pier,grug))!=-1)){
                       queue.offer(""+pier+","+grug);
                   }
               }
           grug=grug+2;
           if(zakres(pier, grug)==true)
               if(listy.Czy_jest_krawedz(pier, grug)==false){
                   listy.dodaj_polaczenie(pier, grug);
                   if(((zwroc_wartosc(pier,grug))!=0)&&((zwroc_wartosc(pier,grug))!=-1)){
                       queue.offer(""+pier+","+grug);
                   }
               }
           pier=pier+1;
           grug=grug-1;
           if(zakres(pier, grug)==true)
               if(listy.Czy_jest_krawedz(pier, grug)==false){
                   listy.dodaj_polaczenie(pier, grug);
                   if(((zwroc_wartosc(pier,grug))!=0)&&((zwroc_wartosc(pier,grug))!=-1)){
                       queue.offer(""+pier+","+grug);
                   }
               }
       }
       return listy.ile_ma()-1;
    }
    public int zwroc_dlugosc(){
        return dlugosc;
    }
    public int zwroc_szerokosc(){
        return szerokosc;
    }
    public int zwroc_ocene(){
        return ocena;
    }
    public void mutacja(){
        int dl = random.nextInt(dlugosc-1);
        int sz = random.nextInt(szerokosc-1);
        while((dl==dlugosc-1)&&(sz==0)){
            dl = random.nextInt(dlugosc-1);
            sz = random.nextInt(szerokosc-1);
        }
        if(siedzenia[dl][sz]==0){
            siedzenia[dl][sz] = 1;
            //System.out.println("Zmutowalo zero na 1");
        }        
        else 
            siedzenia[dl][sz] = 0;
    }
               
    public void funkcja_oceny(){
        int bfs = bfs();
        wygoda = 0;
        koszt = 0;
        for(int i = 0; i < dlugosc; i++)
            for(int j = 0; j < szerokosc; j++){
                if((siedzenia[i][j]==-1)&&((i!=dlugosc-1)||(j!=0)))
                    siedzenia[i][j] = random.nextInt(1);
                if(siedzenia[i][j]==0)
                    wygoda+=7;
                else
                    wygoda+=3;
                if(siedzenia[i][j]==0)
                    koszt+=3;
                else
                    koszt+=1;
            }
        ocena = (wygoda-koszt)*bfs/dlugosc;
    }
    public void losuj_miejsca(){
        
        for(int i = 0; i < dlugosc;i++)
            for(int j = 0; j < szerokosc;j++){
                if(siedzenia[i][j]==-1);
                
                
                else if(random.nextBoolean())
                    siedzenia[i][j] = 0;
                else
                    siedzenia[i][j] = 1;
            }
    }
    public void nadaj_wartosc(int dlug, int szer, int wart){
        siedzenia[dlug][szer] = wart;
    }
    public int zwroc_wartosc(int dlug, int szer){
        return siedzenia[dlug][szer];
    }
    public void wyswietl_autobus(){
        for(int i = 0; i < dlugosc; i++){
            if(i!=0)
                System.out.println("");
            for(int j = 0; j < szerokosc; j++)
                System.out.print(siedzenia[i][j]);
        }
        System.out.println("");
    }
        public Autobus(int dlugosc, int szerokosc){
        this.dlugosc = dlugosc;
        this.szerokosc = szerokosc;
        siedzenia = new int[dlugosc][szerokosc];
        siedzenia[dlugosc-1][0] = -1;
        losuj_miejsca();
        //Tutaj znajdowala sie funkcja do wypisywania autobusu
    }
    public void nadaj_wszystko(int dlug, int szer, int[][] siedzenia){
        this.dlugosc= dlug;
        this.szerokosc=szer;
        this.siedzenia = siedzenia;
    }
    public int[][] zwroc_siedzenia(){
        return siedzenia;
    }
}
