/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package algorytmygenetyczne;

import java.util.Random;

/**
 *
 * @author Tomek
 */
public class Populacja {
    private Autobus najlepszy;
    private Autobus[] autobusy;
    private int wielkosc_populacji;
    Random random = new Random();
    public String wyswietl_srednia(){
        int tmp = 0;
       for(int i = 0; i < autobusy.length;i++)
           tmp += autobusy[i].zwroc_ocene();
       return "Srednia:" + (tmp/wielkosc_populacji);
    }
    public void buduj_najlepszego(Autobus auto){
        najlepszy = new Autobus(auto.zwroc_dlugosc(), auto.zwroc_szerokosc());
        for(int i = 0; i < najlepszy.zwroc_dlugosc();i++)
            for(int j = 0; j< najlepszy.zwroc_szerokosc();j++)
                najlepszy.nadaj_wartosc(i, j, auto.zwroc_wartosc(i, j));
        najlepszy.funkcja_oceny();
    }
    public Autobus zwroc_najlepsz_autobus(){
        return najlepszy;
    }
    public void sortuj(){
        
        Autobus tmp;
        for(int i = 0; i < autobusy.length;i++)
            for(int j = 0; j < autobusy.length-1;j++){
                if(autobusy[j].zwroc_ocene()<autobusy[j+1].zwroc_ocene()){
                    tmp = autobusy[j+1];
                    autobusy[j+1] = autobusy[j];
                    autobusy[j] = tmp;
                }
                    
            }   
    }
    public void mutuj(int procent){
        for(int i = 0; i < autobusy.length;i++)
            if(random.nextInt(99)<procent){
                autobusy[i].mutacja();
                autobusy[i].funkcja_oceny();
            }
    }
    public Autobus krzyzuj7(Autobus jeden, Autobus dwa){
        int dlug = jeden.zwroc_dlugosc();
        int tmp= 1;
        if(random.nextBoolean()==true){
            dlug = dwa.zwroc_dlugosc();
            tmp = 2;
        }
        int szer = jeden.zwroc_szerokosc();
        Autobus nowy = new Autobus(dlug, szer);
        if((tmp == 1)&&(jeden.zwroc_dlugosc()>dwa.zwroc_dlugosc())){
            for(int i = 0; i < dlug;i++)
                for(int j = 0; j < szer;j++)
                    if((dwa.zwroc_dlugosc()>i)&&(random.nextBoolean()==true))
                        nowy.nadaj_wartosc(i, j, dwa.zwroc_wartosc(i, j));
                    else
                        nowy.nadaj_wartosc(i, j, jeden.zwroc_wartosc(i, j));
        }
        else if(tmp == 1){
            for(int i = 0; i < dlug;i++)
                for(int j = 0; j < szer;j++)
                    if((random.nextBoolean()==true))
                        nowy.nadaj_wartosc(i, j, dwa.zwroc_wartosc(i, j));
                    else
                        nowy.nadaj_wartosc(i, j, jeden.zwroc_wartosc(i, j));
        }
        if((tmp == 2)&&(dwa.zwroc_dlugosc()>jeden.zwroc_dlugosc())){
            for(int i = 0; i < dlug;i++)
                for(int j = 0; j < szer;j++)
                    if((jeden.zwroc_dlugosc()>i)&&(random.nextBoolean()==true))
                        nowy.nadaj_wartosc(i, j, jeden.zwroc_wartosc(i, j));
                    else
                        nowy.nadaj_wartosc(i, j, dwa.zwroc_wartosc(i, j));
        }
        else if(tmp == 2){
            for(int i = 0; i < dlug;i++)
                for(int j = 0; j < szer;j++)
                    if((random.nextBoolean()==true))
                        nowy.nadaj_wartosc(i, j, dwa.zwroc_wartosc(i, j));
                    else
                        nowy.nadaj_wartosc(i, j, jeden.zwroc_wartosc(i, j));
        }
        nowy.funkcja_oceny();
        return nowy;
    }
    public Autobus krzyzuj1(Autobus jeden, Autobus dwa){
        int dlug = jeden.zwroc_dlugosc();
        int tmp= 1;
        if(random.nextBoolean()==true){
            dlug = dwa.zwroc_dlugosc();
            tmp = 2;
        }
        int szer = jeden.zwroc_szerokosc();
        Autobus nowy = new Autobus(dlug, szer);
        if((dlug>=jeden.zwroc_dlugosc())&&(dlug>=dwa.zwroc_dlugosc()))
            tmp += 2;
        if(jeden.zwroc_dlugosc()==dwa.zwroc_dlugosc()){
            for(int i = 0; i < dlug;i++)
                for(int j = 0; j < szer; j++){
                    if(i%2==0)
                        nowy.nadaj_wartosc(i, j, jeden.zwroc_wartosc(i, j));
                    else
                        nowy.nadaj_wartosc(i, j, dwa.zwroc_wartosc(i, j));
                }
        }
        else{for(int i = 0; i < dlug/3;i++)
            for(int j = 0; j < szer-1;j++){
                if((dlug/3>jeden.zwroc_dlugosc())||(dlug/3>dwa.zwroc_dlugosc())){
                    if(tmp%2 == 0)
                        nowy.nadaj_wartosc(i, j, dwa.zwroc_wartosc(i, j));
                    else
                        nowy.nadaj_wartosc(i, j, jeden.zwroc_wartosc(i, j));
                }
                else
                    nowy.nadaj_wartosc(i, j, jeden.zwroc_wartosc(i, j));
            }
        for(int i = dlug/3; i < 2*dlug/3;i++)
            for(int j = 0; j < szer-1;j++){
                if((2*dlug/3>jeden.zwroc_dlugosc())||(2*dlug/3>dwa.zwroc_dlugosc())){
                    if(tmp%2 == 0)
                        nowy.nadaj_wartosc(i, j, dwa.zwroc_wartosc(i, j));
                    else
                        nowy.nadaj_wartosc(i, j, jeden.zwroc_wartosc(i, j));
                }
                else
                    nowy.nadaj_wartosc(i, j, jeden.zwroc_wartosc(i, j));
            }
        for(int i = 2*dlug/3; i < dlug;i++)
            for(int j = 0; j < szer-1;j++){
                if((dlug>jeden.zwroc_dlugosc())||(dlug>dwa.zwroc_dlugosc())){
                    if(tmp%2 == 0)
                        nowy.nadaj_wartosc(i, j, dwa.zwroc_wartosc(i, j));
                    else
                        nowy.nadaj_wartosc(i, j, jeden.zwroc_wartosc(i, j));
                }
                else
                    nowy.nadaj_wartosc(i, j, dwa.zwroc_wartosc(i, j));
            }
        }
        
        nowy.funkcja_oceny();
        return nowy;
    }
    public Autobus krzyzuj2(Autobus jeden, Autobus dwa){
        int dlug = jeden.zwroc_dlugosc();
        int szer = jeden.zwroc_szerokosc();
        int tmp= 1;
        if(random.nextBoolean()==true){
            dlug = dwa.zwroc_dlugosc();
            tmp = 2;
        }
        Autobus nowy = new Autobus(dlug, szer);
        if((dlug>=jeden.zwroc_dlugosc())&&(dlug>=dwa.zwroc_dlugosc()))
            tmp += 2;
        if(jeden.zwroc_dlugosc()==dwa.zwroc_dlugosc()){
            for(int i = 0; i < dlug;i++)
                for(int j = 0; j < szer-1; j++){
                    if(i%2==0)
                        nowy.nadaj_wartosc(i, j, jeden.zwroc_wartosc(i, j));
                    else
                        nowy.nadaj_wartosc(i, j, dwa.zwroc_wartosc(i, j));
                }
        }
        else{
        for(int i = 0; i < dlug/3;i++)
            for(int j = 0; j < szer-1;j++){
                if((dlug/3>jeden.zwroc_dlugosc())||(dlug/3>dwa.zwroc_dlugosc())){
                    if(tmp%2 == 0)
                        nowy.nadaj_wartosc(i, j, dwa.zwroc_wartosc(i, j));
                    else
                        nowy.nadaj_wartosc(i, j, jeden.zwroc_wartosc(i, j));
                }
                else
                    nowy.nadaj_wartosc(i, j, jeden.zwroc_wartosc(i, j));
            }
        for(int i = dlug/3; i < 2*dlug/3;i++)
            for(int j = 0; j < szer;j++){
                if((2*dlug/3>jeden.zwroc_dlugosc())||(2*dlug/3>dwa.zwroc_dlugosc())){
                    if(tmp%2 == 0)
                        nowy.nadaj_wartosc(i, j, dwa.zwroc_wartosc(i, j));
                    else
                        nowy.nadaj_wartosc(i, j, jeden.zwroc_wartosc(i, j));
                }
                else
                    nowy.nadaj_wartosc(i, j, dwa.zwroc_wartosc(i, j));
            }
        for(int i = 2*dlug/3; i < dlug;i++)
            for(int j = 0; j < szer;j++){
                if((dlug>jeden.zwroc_dlugosc())||(dlug>dwa.zwroc_dlugosc())){
                    if(tmp%2 == 0)
                        nowy.nadaj_wartosc(i, j, dwa.zwroc_wartosc(i, j));
                    else
                        nowy.nadaj_wartosc(i, j, jeden.zwroc_wartosc(i, j));
                }
                else
                    nowy.nadaj_wartosc(i, j, jeden.zwroc_wartosc(i, j));
            }
        }
        nowy.funkcja_oceny();
        return nowy;
    }
    public Autobus krzyzuj3(Autobus jeden, Autobus dwa){
        int dlug = jeden.zwroc_dlugosc();
        int szer = jeden.zwroc_szerokosc();
        int tmp= 1;
        if(random.nextBoolean()==true){
            dlug = dwa.zwroc_dlugosc();
            tmp = 2;
        }
        Autobus nowy = new Autobus(dlug, szer);
        if((dlug>=jeden.zwroc_dlugosc())&&(dlug>=dwa.zwroc_dlugosc()))
            tmp += 2;
        if(jeden.zwroc_dlugosc()==dwa.zwroc_dlugosc()){
            for(int i = 0; i < dlug;i++)
                for(int j = 0; j < szer; j++){
                    if(i%2==0)
                        nowy.nadaj_wartosc(i, j, jeden.zwroc_wartosc(i, j));
                    else
                        nowy.nadaj_wartosc(i, j, dwa.zwroc_wartosc(i, j));
                }
        }
        else{
        for(int i = 0; i < dlug/3;i++)
            for(int j = 0; j < szer;j++){
                if((dlug/3>jeden.zwroc_dlugosc())||(dlug/3>dwa.zwroc_dlugosc())){
                    if(tmp%2 == 0)
                        nowy.nadaj_wartosc(i, j, dwa.zwroc_wartosc(i, j));
                    else
                        nowy.nadaj_wartosc(i, j, jeden.zwroc_wartosc(i, j));
                }
                else
                    nowy.nadaj_wartosc(i, j, jeden.zwroc_wartosc(i, j));
            }
        for(int i = dlug/3; i < 2*dlug/3;i++)
            for(int j = 0; j < szer;j++){
                if((2*dlug/3>jeden.zwroc_dlugosc())||(2*dlug/3>dwa.zwroc_dlugosc())){
                    if(tmp%2 == 0)
                        nowy.nadaj_wartosc(i, j, dwa.zwroc_wartosc(i, j));
                    else
                        nowy.nadaj_wartosc(i, j, jeden.zwroc_wartosc(i, j));
                }
                else
                    nowy.nadaj_wartosc(i, j, dwa.zwroc_wartosc(i, j));
            }
        for(int i = 2*dlug/3; i < dlug;i++)
            for(int j = 0; j < szer;j++){
                if((dlug>jeden.zwroc_dlugosc())||(dlug>dwa.zwroc_dlugosc())){
                    if(tmp%2 == 0)
                        nowy.nadaj_wartosc(i, j, dwa.zwroc_wartosc(i, j));
                    else
                        nowy.nadaj_wartosc(i, j, jeden.zwroc_wartosc(i, j));
                }
                else
                    nowy.nadaj_wartosc(i, j, dwa.zwroc_wartosc(i, j));
            }
        }
        nowy.funkcja_oceny();      
        return nowy;
    }
    public Autobus krzyzuj4(Autobus jeden, Autobus dwa){
        int dlug = jeden.zwroc_dlugosc();
        int szer = jeden.zwroc_szerokosc();
        int tmp= 1;
        if(random.nextBoolean()==true){
            dlug = dwa.zwroc_dlugosc();
            tmp = 2;
        }
        Autobus nowy = new Autobus(dlug, szer);
        if((dlug>=jeden.zwroc_dlugosc())&&(dlug>=dwa.zwroc_dlugosc()))
            tmp += 2;
        if(jeden.zwroc_dlugosc()==dwa.zwroc_dlugosc()){
            for(int i = 0; i < dlug;i++)
                for(int j = 0; j < szer; j++){
                    if(i%2==0)
                        nowy.nadaj_wartosc(i, j, dwa.zwroc_wartosc(i, j));
                    else
                        nowy.nadaj_wartosc(i, j, jeden.zwroc_wartosc(i, j));
                }
        }
        else{
        for(int i = 0; i < dlug/3;i++)
            for(int j = 0; j < szer;j++){
                if((dlug/3>jeden.zwroc_dlugosc())||(dlug/3>dwa.zwroc_dlugosc())){
                    if(tmp%2 == 0)
                        nowy.nadaj_wartosc(i, j, dwa.zwroc_wartosc(i, j));
                    else
                        nowy.nadaj_wartosc(i, j, jeden.zwroc_wartosc(i, j));
                }
                else
                    nowy.nadaj_wartosc(i, j, dwa.zwroc_wartosc(i, j));
            }
        for(int i = dlug/3; i < 2*dlug/3;i++)
            for(int j = 0; j < szer;j++){
                if((2*dlug/3>jeden.zwroc_dlugosc())||(2*dlug/3>dwa.zwroc_dlugosc())){
                    if(tmp%2 == 0)
                        nowy.nadaj_wartosc(i, j, dwa.zwroc_wartosc(i, j));
                    else
                        nowy.nadaj_wartosc(i, j, jeden.zwroc_wartosc(i, j));
                }
                else
                    nowy.nadaj_wartosc(i, j, jeden.zwroc_wartosc(i, j));
            }
        for(int i = 2*dlug/3; i < dlug;i++)
            for(int j = 0; j < szer;j++){
                if((dlug>jeden.zwroc_dlugosc())||(dlug>dwa.zwroc_dlugosc())){
                    if(tmp%2 == 0)
                        nowy.nadaj_wartosc(i, j, dwa.zwroc_wartosc(i, j));
                    else
                        nowy.nadaj_wartosc(i, j, jeden.zwroc_wartosc(i, j));
                }
                else
                    nowy.nadaj_wartosc(i, j, jeden.zwroc_wartosc(i, j));
            }
        }
        nowy.funkcja_oceny();       
        return nowy;
    }
    public Autobus krzyzuj5(Autobus jeden, Autobus dwa){
        int dlug = jeden.zwroc_dlugosc();
        int szer = jeden.zwroc_szerokosc();
        int tmp= 1;
        if(random.nextBoolean()==true){
            dlug = dwa.zwroc_dlugosc();
            tmp = 2;
        }
        Autobus nowy = new Autobus(dlug, szer);
        if((dlug>=jeden.zwroc_dlugosc())&&(dlug>=dwa.zwroc_dlugosc()))
            tmp += 2;
        if(jeden.zwroc_dlugosc()==dwa.zwroc_dlugosc()){
            for(int i = 0; i < dlug;i++)
                for(int j = 0; j < szer; j++){
                    if(i%2==0)
                        nowy.nadaj_wartosc(i, j, dwa.zwroc_wartosc(i, j));
                    else
                        nowy.nadaj_wartosc(i, j, jeden.zwroc_wartosc(i, j));
                }
        }
        else{
        for(int i = 0; i < dlug/3;i++)
            for(int j = 0; j < szer;j++){
                if((dlug/3>jeden.zwroc_dlugosc())||(dlug/3>dwa.zwroc_dlugosc())){
                    if(tmp%2 == 0)
                        nowy.nadaj_wartosc(i, j, dwa.zwroc_wartosc(i, j));
                    else
                        nowy.nadaj_wartosc(i, j, jeden.zwroc_wartosc(i, j));
                }
                else
                    nowy.nadaj_wartosc(i, j, dwa.zwroc_wartosc(i, j));
            }
        for(int i = dlug/3; i < 2*dlug/3;i++)
            for(int j = 0; j < szer;j++){
                if((2*dlug/3>jeden.zwroc_dlugosc())||(2*dlug/3>dwa.zwroc_dlugosc())){
                    if(tmp%2 == 0)
                        nowy.nadaj_wartosc(i, j, dwa.zwroc_wartosc(i, j));
                    else
                        nowy.nadaj_wartosc(i, j, jeden.zwroc_wartosc(i, j));
                }
                else
                    nowy.nadaj_wartosc(i, j, jeden.zwroc_wartosc(i, j));
            }
        for(int i = 2*dlug/3; i < dlug;i++)
            for(int j = 0; j < szer;j++){
                if((dlug>jeden.zwroc_dlugosc())||(dlug>dwa.zwroc_dlugosc())){
                    if(tmp%2 == 0)
                        nowy.nadaj_wartosc(i, j, dwa.zwroc_wartosc(i, j));
                    else
                        nowy.nadaj_wartosc(i, j, jeden.zwroc_wartosc(i, j));
                }
                else
                    nowy.nadaj_wartosc(i, j, dwa.zwroc_wartosc(i, j));
            }
        }
        nowy.funkcja_oceny();        
        return nowy;
    }
    public Autobus krzyzuj6(Autobus jeden, Autobus dwa){
        int dlug = jeden.zwroc_dlugosc();
        int szer = jeden.zwroc_szerokosc();
        int tmp= 1;
        if(random.nextBoolean()==true){
            dlug = dwa.zwroc_dlugosc();
            tmp = 2;
        }
        Autobus nowy = new Autobus(dlug, szer);
        if((dlug>=jeden.zwroc_dlugosc())&&(dlug>=dwa.zwroc_dlugosc()))
            tmp += 2;
        if(jeden.zwroc_dlugosc()==dwa.zwroc_dlugosc()){
            for(int i = 0; i < dlug;i++)
                for(int j = 0; j < szer; j++){
                    if(i%2==0)
                        nowy.nadaj_wartosc(i, j, dwa.zwroc_wartosc(i, j));
                    else
                        nowy.nadaj_wartosc(i, j, jeden.zwroc_wartosc(i, j));
                }
        }
        else{
        for(int i = 0; i < dlug/3;i++)
            for(int j = 0; j < szer;j++){
                if((dlug/3>jeden.zwroc_dlugosc())||(dlug/3>dwa.zwroc_dlugosc())){
                    if(tmp%2 == 0)
                        nowy.nadaj_wartosc(i, j, dwa.zwroc_wartosc(i, j));
                    else
                        nowy.nadaj_wartosc(i, j, jeden.zwroc_wartosc(i, j));
                }
                else
                    nowy.nadaj_wartosc(i, j, dwa.zwroc_wartosc(i, j));
            }
        for(int i = dlug/3; i < 2*dlug/3;i++)
            for(int j = 0; j < szer;j++){
                if((2*dlug/3>jeden.zwroc_dlugosc())||(2*dlug/3>dwa.zwroc_dlugosc())){
                    if(tmp%2 == 0)
                        nowy.nadaj_wartosc(i, j, dwa.zwroc_wartosc(i, j));
                    else
                        nowy.nadaj_wartosc(i, j, jeden.zwroc_wartosc(i, j));
                }
                else
                    nowy.nadaj_wartosc(i, j, dwa.zwroc_wartosc(i, j));
            }
        for(int i = 2*dlug/3; i < dlug;i++)
            for(int j = 0; j < szer;j++){
                if((dlug>jeden.zwroc_dlugosc())||(dlug>dwa.zwroc_dlugosc())){
                    if(tmp%2 == 0)
                        nowy.nadaj_wartosc(i, j, dwa.zwroc_wartosc(i, j));
                    else
                        nowy.nadaj_wartosc(i, j, jeden.zwroc_wartosc(i, j));
                }
                else
                    nowy.nadaj_wartosc(i, j, jeden.zwroc_wartosc(i, j));
            }
        }
        nowy.funkcja_oceny();        
        return nowy;
    }
    public void zwroc_oceny(){
        for(int i = 0; i < autobusy.length; i++){
           System.out.print(autobusy[i].zwroc_ocene());
           System.out.println(autobusy[i].toString());
        }
    }
    public void wyswietl_najlepsze_osobniki(){
       int tmp = 0;
       for(int i = 0; i < autobusy.length;i++)
           tmp += autobusy[i].zwroc_ocene();
       System.out.println("Oto najlepsze osobniki!!! Srednia:" + (tmp/wielkosc_populacji));
       System.out.println("Autobus najlepszy. Wartosc funkcji oceny:"+ autobusy[0].zwroc_ocene());
       autobusy[0].wyswietl_autobus();
       System.out.println("Autobus drugi. Wartosc funkcji oceny:"+ autobusy[1].zwroc_ocene());
       autobusy[1].wyswietl_autobus();
       System.out.println("Autobus trzeci. Wartosc funkcji oceny:"+ autobusy[2].zwroc_ocene());
       autobusy[2].wyswietl_autobus();
    }
    public void krzyzuj(int procent, boolean metoda){
        int cos = random.nextInt(wielkosc_populacji/4-1);
        if(metoda == true)
        for(int i = 0; i < (wielkosc_populacji)/8; i++){
            autobusy[6*i+wielkosc_populacji/4] = krzyzuj7(autobusy[i], autobusy[wielkosc_populacji/4-i-1]);//wielkosc_populacji/4-i-1
            autobusy[6*i+wielkosc_populacji/4+1] = krzyzuj7(autobusy[i], autobusy[wielkosc_populacji/4-i-1]);
            autobusy[6*i+wielkosc_populacji/4+2] = krzyzuj7(autobusy[i], autobusy[wielkosc_populacji/4-i-1]);
            autobusy[6*i+wielkosc_populacji/4+3] = krzyzuj7(autobusy[i], autobusy[wielkosc_populacji/4-i-1]);
            autobusy[6*i+wielkosc_populacji/4+4] = krzyzuj7(autobusy[i], autobusy[wielkosc_populacji/4-i-1]);
            autobusy[6*i+wielkosc_populacji/4+5] = krzyzuj7(autobusy[i], autobusy[wielkosc_populacji/4-i-1]);
       }
        else
        for(int i = 0; i < (wielkosc_populacji)/8; i++){
            autobusy[6*i+wielkosc_populacji/4] = krzyzuj1(autobusy[i], autobusy[wielkosc_populacji/4-i-1]);//cos
            autobusy[6*i+wielkosc_populacji/4+1] = krzyzuj2(autobusy[i], autobusy[wielkosc_populacji/4-i-1]);
            autobusy[6*i+wielkosc_populacji/4+2] = krzyzuj3(autobusy[i], autobusy[wielkosc_populacji/4-i-1]);
            autobusy[6*i+wielkosc_populacji/4+3] = krzyzuj4(autobusy[i], autobusy[wielkosc_populacji/4-i-1]);
            autobusy[6*i+wielkosc_populacji/4+4] = krzyzuj5(autobusy[i], autobusy[wielkosc_populacji/4-i-1]);
            autobusy[6*i+wielkosc_populacji/4+5] = krzyzuj6(autobusy[i], autobusy[wielkosc_populacji/4-i-1]);
       }
       mutuj(procent);
       sortuj();
    }
    public void wyswietl_trzy_losowe(){
        System.out.println("Oto losowe osobniki!!!");
        int ind = random.nextInt(wielkosc_populacji-1);
        System.out.println("Autobus pierwszy. Wartosc funkcji oceny:"+ autobusy[ind].zwroc_ocene());
        autobusy[ind].wyswietl_autobus();
        ind = random.nextInt(wielkosc_populacji-1);
        System.out.println("Autobus drugi. Wartosc funkcji oceny:"+ autobusy[ind].zwroc_ocene());
        autobusy[ind].wyswietl_autobus();
        ind = random.nextInt(wielkosc_populacji-1);
        System.out.println("Autobus trzeci. Wartosc funkcji oceny:"+ autobusy[ind].zwroc_ocene());
        autobusy[ind].wyswietl_autobus();
    }
    Populacja(int liczba, int szerokosc){
        wielkosc_populacji = liczba;
       autobusy = new Autobus[liczba];
       for(int i = 0; i < autobusy.length; i++){
           int j, v;
           /*if(i == 0)
               System.out.println("Autobus"+ (i+1) + "-szy");
           else if(i ==1)
               System.out.println("Autobus"+ (i+1) + "-gi");
           else if(i == 2)
               System.out.println("Autobus"+ (i+1) + "-ci");
           else if((i+1)%10 == 8 || (i+1)%10 == 7)
               System.out.println("Autobus"+ (i+1) + "-my");
           else 
               System.out.println("Autobus"+ (i+1) + "-ty");*/
           autobusy[i] = new Autobus(random.nextInt(11)+2, szerokosc);
           autobusy[i].funkcja_oceny();
       }
       sortuj();
    }
    public Autobus zwroc_autobus(int i){
        return autobusy[i];
    }
}
