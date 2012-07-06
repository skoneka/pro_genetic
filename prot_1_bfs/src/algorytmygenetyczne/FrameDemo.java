/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package algorytmygenetyczne;

/**
 *
 * @author Tomek
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.JRadioButton;

    

    
     
    
/* FrameDemo.java requires no other files. */
public class FrameDemo extends JFrame {
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void showbestAutobus(Autobus auto, Panel panel){
        
        for(int i = 0; i < auto.zwroc_dlugosc();i++){
             for(int j = 0; j < auto.zwroc_szerokosc(); j++)
                 if(auto.zwroc_wartosc(i, j)==0){
                     Point p = new Point(10*(i), 10*j);
                     p.setLocation(10*(i*2), 10*(2*j));
                     panel.addFigura(new Krzeslo(p));
                 }
                 else if(auto.zwroc_wartosc(i, j)==1){
                     Point p = new Point(10*i, 10*j);
                     p.setLocation(10*(i*2), 10*(2*j));
                     panel.addFigura(new Ludzik(p));
                 }
                 else {
                     Point p = new Point(10*i, 10*j);
                     p.setLocation(10*(i*2), 10*(2*j));
                     panel.addFigura(new Wyjscie(p));
                 }
                     
         }
    }
    private static int showAutobus(Populacja pop, Panel panel, int tmp){
        Autobus auto = pop.zwroc_autobus(0);
        if(auto.zwroc_ocene()>tmp){
            pop.buduj_najlepszego(auto);
            System.out.println("Zbudowanego najlepszego"+ auto.zwroc_ocene());
            tmp = auto.zwroc_ocene();
        }
        
        for(int i = 0; i < auto.zwroc_dlugosc();i++){
             for(int j = 0; j < auto.zwroc_szerokosc(); j++)
                 if(auto.zwroc_wartosc(i, j)==0){
                     Point p = new Point(10*(i), 10*j);
                     p.setLocation(10*(i*2), 10*(2*j));
                     panel.addFigura(new Krzeslo(p));
                 }
                 else if(auto.zwroc_wartosc(i, j)==1){
                     Point p = new Point(10*i, 10*j);
                     p.setLocation(10*(i*2), 10*(2*j));
                     panel.addFigura(new Ludzik(p));
                 }
                 else {
                     Point p = new Point(10*i, 10*j);
                     p.setLocation(10*(i*2), 10*(2*j));
                     panel.addFigura(new Wyjscie(p));
                 }
                     
         }
       //System.out.print(auto.zwroc_ocene());
       return tmp;
    }
    
    private static void createAndShowGUI() {
        
        //Create and set up the window.
        final JFrame frame = new JFrame("FrameDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(3, 2));

        //JLabel emptyLabel = new JLabel("Ja pierdzeile Label");
        //emptyLabel.setPreferredSize(new Dimension(175, 100));
        //frame.getContentPane().add(emptyLabel, BorderLayout.CENTER);

        //Display the window.
        frame.setPreferredSize(new Dimension(500, 700));
        frame.pack();
        frame.createBufferStrategy(4);
        final Panel panel = new Panel(200,240);
        
         class ButtonListener2 implements ActionListener{

            @Override
            public void actionPerformed(ActionEvent e) {
                panel.clear();
                panel.updateCanvas(0, 0, 0);
            }
            
        }
         ButtonListener2 bl2 = new ButtonListener2();
         
         
         panel.setVisible(true);
         panel.setLayout(new FlowLayout(FlowLayout.LEFT, 180, 200));
         JButton start = new JButton("Start/stop");
         JLabel krzyzowanie = new JLabel("Metoda krzyzowania:");
         final JRadioButton rb1 = new JRadioButton("Losowo kazdy gen", true);
         final JRadioButton rb2 = new JRadioButton("Krzyzuj rzedami", false);
         ButtonGroup g = new ButtonGroup();    
         g.add(rb1);
         g.add(rb2);
         JButton reset = new JButton("Reset");
         reset.addActionListener(bl2);
         JButton dobry = new JButton("Wyswietl najlepszego");
         JLabel size = new JLabel("Rozmiar populacji:");
         JLabel gene = new JLabel("Liczba generacji:");
         JLabel elitism = new JLabel("Opóźnienie czasu:");
         JLabel mutation = new JLabel("Mutacja(%):");
         JLabel szerokosc = new JLabel("Szerokosc autobusu:");
         JLabel time = new JLabel("Time:");
         time.setForeground(Color.WHITE);
         JSpinner spin = new JSpinner(new SpinnerNumberModel(800, 16, 100000, 8));
         final JFormattedTextField ftf1 = ((JSpinner.DefaultEditor)spin.getEditor()).getTextField();
         JSpinner spin2 = new JSpinner(new SpinnerNumberModel(300, 1, 100000, 1));
         final JFormattedTextField ftf2 = ((JSpinner.DefaultEditor)spin2.getEditor()).getTextField();
         JSpinner spin3 = new JSpinner(new SpinnerNumberModel(0, 0, 5, 0.01));
         final JFormattedTextField ftf3 = ((JSpinner.DefaultEditor)spin3.getEditor()).getTextField();
         JSpinner spin4 = new JSpinner(new SpinnerNumberModel(20, 0, 100, 1));
         final JFormattedTextField ftf4 = ((JSpinner.DefaultEditor)spin4.getEditor()).getTextField();
         JSpinner spin5 = new JSpinner(new SpinnerNumberModel(6, 3, 9, 1));
         final JFormattedTextField ftf5 = ((JSpinner.DefaultEditor)spin5.getEditor()).getTextField();
         final JPanel panel2 = new JPanel(true);
         final JPanel panel5 = new JPanel(true);
         class ButtonListener implements ActionListener{
            private final Autobus auto=new Autobus(10, Integer.parseInt(ftf5.getText()));
            public void actionPerformed(ActionEvent e) {
                int tmp = 0;
                String s = ftf1.getText();
                String a= ftf1.getText().substring(2);
                String b=s;
                if(ftf1.getText().codePointAt(1) == 160)
                   b = ""+ftf1.getText().charAt(0) +a;
                //System.out.println(a+"to a" +b+"to b" +ftf1.getText().codePointAt(1));
                final Populacja population = new Populacja(Integer.parseInt((b)), Integer.parseInt(ftf5.getText()));
                s = ftf2.getText();
                if(s.length()>1)
                    a = ftf2.getText().substring(2);
                b = s;
                if((s.length()>1)&&(ftf2.getText().codePointAt(1) == 160))
                   b = ""+ftf2.getText().charAt(0) +a;
                int i = 0;
                long nowy = System.currentTimeMillis();
                boolean metoda;
                if(rb1.isSelected()==true){
                    metoda = true;
                    System.out.println("true");
                }
                else 
                    metoda = false;
                while(i < Integer.parseInt(b)){
                    population.krzyzuj(Integer.parseInt(ftf4.getText()), metoda);
                    panel.clear();
                    int tmp2 = showAutobus(population, panel, tmp);
                    tmp = tmp2;
                    i++;
                    long cos = System.currentTimeMillis();
                    long wynik = (cos - nowy)/1000;
                    panel.updateCanvas(i, wynik, tmp2);
                    Graphics g = panel5.getGraphics();
                    //Graphics2D g2=(Graphics2D)g;
                    //g2.drawString(""+ i, 220, 40);
                    panel5.paint(g);
                    long start = System.currentTimeMillis();
                    long stop;
                    Double czas = 1000*Double.parseDouble(ftf3.getText().replace(',', '.'));
                    while((stop= (System.currentTimeMillis()-start))<czas)
                        ;
                    //panel.clear();
                }
                auto.nadaj_wszystko(population.zwroc_najlepsz_autobus().zwroc_dlugosc(),population.zwroc_najlepsz_autobus().zwroc_szerokosc(),population.zwroc_najlepsz_autobus().zwroc_siedzenia());
                System.out.println("Najlepszy autobus: " + tmp);
            }
        }
         final ButtonListener bl = new ButtonListener();
         start.addActionListener(bl);
        
         class Najlepszego implements ActionListener{

            @Override
            public void actionPerformed(ActionEvent e) {
                panel.clear();
                showbestAutobus(bl.auto ,panel);
            }
             
         }
         final Najlepszego br = new Najlepszego();
         dobry.addActionListener(br);
         start.setBounds(50,10,90, 30);
         size.setBounds(50, 100-10, 120, 20);
         reset.setBounds(50, 60-10, 90, 30);
         spin.setBounds(100, 125-10, 60, 30);
         gene.setBounds(50, 160-10, 100, 20);
         spin2.setBounds(100, 185-10, 60, 30);
         elitism.setBounds(50, 60, 120, 20);
         spin3.setBounds(100, 94, 40, 30);
         dobry.setBounds(50, 0, 160, 30);
         krzyzowanie.setBounds(50, 40, 140, 20);
         rb1.setBounds(50, 70, 150, 20);
         rb2.setBounds(50, 100, 150, 20);
         spin4.setBounds(100, 160, 40, 30);
         mutation.setBounds(50, 125, 90, 30);
         szerokosc.setBounds(50, 0, 140, 20);
         spin5.setBounds(100, 25, 40, 30);
         time.setBounds(50, 200, 40, 20);
         //showAutobus(pop, panel);
         panel2.setLayout(null);
         JPanel panel3 = new JPanel();
         panel3.setLayout(null);
         JPanel panel4 = new JPanel();
         panel4.setLayout(null);
         
         panel5.setLayout(null);
         panel2.add(start);
         panel2.add(reset);
         panel2.add(size);
         panel2.add(spin);
         panel2.add(gene);
         panel2.add(spin2);
         
         panel3.add(dobry);
         panel3.add(krzyzowanie);
         panel3.add(rb1);
         panel3.add(rb2);
         panel4.add(szerokosc);
         panel4.add(spin5);
         panel4.add(elitism);
         panel4.add(spin3);
         panel4.add(mutation);
         panel4.add(spin4);
         panel5.add(time);
         frame.add(panel);
         frame.add(panel2);
         frame.add(panel3);
         frame.add(panel4);
         frame.add(panel5);
         //frame.setLayout(null);       
        frame.setVisible(true);
        
        
        
        
    }
        public static void menu() {
        //Schedule a job for the event-dispatching thread:uto
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
        }
}