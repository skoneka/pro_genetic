/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package algorytmygenetyczne;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author Tomek
 */
public class Panel extends JPanel{
        private static final long serialVersionUID = 4797180680181137952L;
        private int width;
        private int height;
        private int ocena;
        private long time;
        private int generation;
        private ArrayList<Figura> figury;
        public Panel(int width, int height) {
                super(true);
                this.width = width;
                this.height = height;
                ocena = 0;
                generation = 0;
                time = 0;
                figury=new ArrayList<Figura>();
        }
        public void addFigura(Figura f){
                figury.add(f);
                repaint();
        }
        public void clear(){
                figury.clear();
                repaint();
        }
        public void updateCanvas(int i, long wynik, int ocena)
        {
                this.ocena = ocena;
                generation = i;
                time = wynik;
                Graphics g = getGraphics();
                paintComponent(g, i, wynik, ocena);
        }
        public void updateCanvas()
        {
                Graphics g = getGraphics();
                paintComponent(g);
        }
        protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2=(Graphics2D)g;
                g2.setColor(Color.BLACK);
                g2.drawString(""+generation, 134, 200);
                if(time<10)
                    g2.drawString("0"+time, 34, 200);
                else 
                    g2.drawString(""+time, 34, 200);
                g2.setColor(Color.BLUE);
                g2.setBackground(Color.WHITE);
                
                for (int i = 0; i <=width-20; i+=20) {
                        g2.drawLine(0,i, height, i);
                }
                for (int i = 0; i <=height; i+=20) {
                        g2.drawLine(i,0,i,  width-20);
                }
                g2.setColor(Color.RED);
                for (int i = 0; i < figury.size(); i++) {
                        
                        figury.get(i).draw(g2);
                }
                g2.setColor(Color.BLACK);
                g2.drawString("Generacja:", 70, 200);
                g2.drawString("Czas:", 4, 200);
                g2.drawString("Ocena:", 170, 200);
                g2.drawString(""+ocena, 210, 200);
        }
        protected void paintComponent(Graphics g, int j, long wynik, int ocena) {
                super.paintComponent(g);
                Graphics2D g2=(Graphics2D)g;
                
                
                
                
                g2.setBackground(Color.WHITE);
               g2.setColor(Color.BLUE);
             //g2.drawString(""+12, 180, 200);
                
                //g2.setColor(Color.BLUE);
               for (int i = 0; i <=width-20; i+=20) {
                        g2.drawLine(0,i, height, i);
                       //g2.drawString(""+12, 180, 200);
                }
                for (int i = 0; i <=height; i+=20) {
                        g2.drawLine(i,0,i,  width-20);
                      //g2.drawString(""+12, 180, 200); 
                }
                g2.setColor(Color.RED);
                for (int i = 0; i < figury.size(); i++) {
                        figury.get(i).draw(g2);
                }
                g2.setColor(Color.BLACK);
                g2.drawString("Generacja:", 70, 200);
                g2.drawString("Czas:", 4, 200);
                g2.drawString("Ocena:", 170, 200);
                g2.drawString(""+j, 134, 200);
                if(wynik<10)
                    g2.drawString("0"+wynik, 34, 200);
                else 
                    g2.drawString(""+wynik, 34, 200);
                g2.drawString(""+ocena, 210, 200);
                
        }
        /*public boolean contains(Point p){
                boolean result=false;
                for (int i = 0; i < figury.size()&&!result; i++) {
                        if(figury.get(i).contains(p.x, p.y)){
                                result=true;
                        }
                }
                return result;
        }*/
}
