/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package algorytmygenetyczne;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

/**
 *
 * @author Tomek
 */
public abstract class Figura implements Serializable{
        //private Rectangle2D area;
        protected int x;
        protected int y;
        public Figura(Point p){
                this.x=p.x;
                this.y=p.y;
                //area=new Rectangle2D.Float(p.x,p.y,20,20);
        }
 
        /*public boolean contains(int x, int y) {
                return area.contains(x, y);
        }*/
        public int getX(){
                return x; 
        }
        public int getY(){
                return y; 
        }
        abstract public void draw(Graphics2D g2);
}
 
class Kolko extends Figura{
        private static final long serialVersionUID = 7153846813565405611L;
        public Kolko(Point p){
                super(p);
        }
        public void draw(Graphics2D g2) {
                g2.drawOval(x+5, y+5, 10, 10);                
        }
}
 
class Krzyzyk extends Figura{
        private static final long serialVersionUID = -1984051710790995083L;
        public Krzyzyk(Point p){
                super(p);
        }
        public void draw(Graphics2D g2) {
                g2.drawLine(x+5, y+5, x+15, y+15);
                g2.drawLine(x+5, y+15, x+15, y+5);
        }
}
class Wyjscie extends Figura{
        private static final long serialVersionUID = 7153846813565405611L;
        public Wyjscie(Point p){
                super(p);
        }
        public void draw(Graphics2D g2) {
                g2.drawLine(x+5, y+5,x+15, y+15);
                g2.drawOval(x+5, y+5, 10, 10);                  
        }
}
class Ludzik extends Figura{
        private static final long serialVersionUID = 7153846813565405611L;
        public Ludzik(Point p){
                super(p);
        }
        public void draw(Graphics2D g2) {
                g2.drawOval(x+8, y+2,4, 4);
                g2.drawLine(x+10, y+6, x+10, y+10);         
                g2.drawLine(x+17, y+17, x+10, y+10);   
                g2.drawLine(x+3, y+17, x+10, y+10);     
                g2.drawLine(x+10, y+6, x+4, y+8);   
                g2.drawLine(x+10, y+6, x+16, y+8);         
        }
}
class Krzeslo extends Figura{
        private static final long serialVersionUID = 7153846813565405611L;
        public Krzeslo(Point p){
                super(p);
        }
        public void draw(Graphics2D g2) {
                g2.drawLine(x+4, y+2, x+4, y+18);         
                g2.drawLine(x+4, y+9, x+16, y+9);   
                g2.drawLine(x+16, y+9, x+16, y+18);           
        }
}
