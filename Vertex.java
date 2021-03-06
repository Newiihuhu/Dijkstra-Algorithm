
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author vento
 */
public class Vertex {

    int x;
    int y;
    String name;
    int r;
    int shift;
    boolean isSelect;

    int dist; /// add component
    String prev; /// add component
    boolean visited; /// add component

    int id;
    static int idGen = 0;

    Vertex(int x, int y) {
        this.id = idGen;
        idGen++;
        this.r = 36;
        this.shift = 30;
        this.x = x;
        this.y = y;
        this.name = "";
        this.isSelect = false;
    }

    boolean inCircle(int x0, int y0) {
        return ((x0 - x) * (x0 - x) + (y0 - y) * (y0 - y)) <= r * r;
    }

    void draw(Graphics2D g, Color color) {
        g.setColor(isSelect ? Color.BLUE : Color.BLACK);
        g.setStroke(new BasicStroke(2));

        g.fillOval(x - r, y - r, r * 2, r * 2);

        g.setColor(color);
        g.fillOval(x - r + (r - shift) / 2, y - r + (r - shift) / 2, r * 2 - (r - shift), r * 2 - (r - shift));

        g.setColor(isSelect ? Color.BLUE : Color.BLACK);
        g.drawString(name, x - 10, y + 10);

        g.drawString("Degree = ", x - 50, y - 50); /// add component
    }

    void printdegree(int d, Graphics2D g) { /// add fuction
        String strnum = Integer.toString(d).toUpperCase();
        g.drawString(strnum, x + 55, y - 50);
    }
}
