
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 *
 * @author vento
 */
public class GraphDrawing extends JFrame implements MouseListener, MouseMotionListener, KeyListener {

    public static void main(String[] args) {
        try {
            GraphDrawing gui = new GraphDrawing();
        } catch (Exception ex) {

        }
    }
    //Scanner
    Scanner scan;

    // set color
    int r;
    int g;
    int b;
    public Color color = new Color(r = 255, g = 255, b = 255); /// default is white

    //Data of graph
    ArrayList<Vertex> Vertexs = new ArrayList<>();
    ArrayList<Edge_> Edge_s = new ArrayList<>();
    ArrayList<Integer> degree = new ArrayList<>();

    Object selected = null;
    TempEdge TempEdge = null; //TempEdge edge

    //UI 
    Canvas c;
    String mode = "Vertex"; //Vertex or Edge_

    //set font 
    Font sanSerifFont = new Font("SanSerif", Font.PLAIN, 24);

    //find size monitor
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    //JFrame
    JFrame frameHelp = new JFrame("Help");
    JFrame chooseColorFrame = new JFrame("Choose Color"); /// add component

    //Jpanel
    JPanel boxSave = new JPanel();
    JPanel boxOpen = new JPanel();
    JPanel boxHelp = new JPanel();
    JPanel menubar = new JPanel();
    JPanel boxColor = new JPanel(); /// add component

    //JButton
    JButton saveButt = new JButton();
    JButton openButt = new JButton();
    JButton helpButt = new JButton();
    JButton findpathButt = new JButton(); /// add component
    JButton clearButt = new JButton(); /// add component
    JButton grayButt = new JButton(); /// add component
    JButton whiteButt = new JButton(); /// add component
    JButton orangeButt = new JButton(); /// add component
    JButton pinkButt = new JButton(); /// add component
    JButton chooseButt = new JButton(); /// add component

    //JFileChooser
    JFileChooser pathSave = new JFileChooser();
    JFileChooser pathOpen = new JFileChooser();

    //JLabel
    JLabel helpString = new JLabel();

    //Integer
    int shift = 50;
    int min_weight = 0;

    //////////////////////////////// Backup ////////////////////////////////
    class Backup {

        ArrayList<Vertex> VertexsBackup;
        ArrayList<Edge_> Edge_sBackup;

        public Backup() {
            this.VertexsBackup = Vertexs;
            this.Edge_sBackup = Edge_s;
        }

    }

    GraphDrawing() {
        super("canvas");
        this.degree = new ArrayList<>();

        // create a empty canvas 
        c = new Canvas() {
            @Override
            public void paint(Graphics g) {
            }
        };
        c.setBackground(color); /// canvas color

        // add mouse listener 
        c.addMouseListener(this);
        c.addMouseMotionListener(this);

        // add keyboard listener 
        c.addKeyListener(this);

        // System close
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                System.exit(0);
            }
        });

        // add Scanner
        scan = new Scanner(System.in);

        // SetFont
        saveButt.setFont(sanSerifFont);
        openButt.setFont(sanSerifFont);
        helpButt.setFont(sanSerifFont);
        findpathButt.setFont(sanSerifFont); /// add component
        clearButt.setFont(sanSerifFont); /// add component
        grayButt.setFont(sanSerifFont); /// add component
        whiteButt.setFont(sanSerifFont); /// add component
        orangeButt.setFont(sanSerifFont); /// add component
        pinkButt.setFont(sanSerifFont); /// add component
        chooseButt.setFont(sanSerifFont); /// add component

        // Set background
        boxSave.setBackground(Color.white);
        boxOpen.setBackground(Color.white);
        boxHelp.setBackground(Color.white);

        frameHelp.add(boxHelp);
        ///
        boxColor.setLayout(new FlowLayout());
        chooseColorFrame.add(boxColor);

        /////
        ////
        ///
        //------------------------------------Button-----------------------------------------------//
        saveButt.setText("save");
        saveButt.setBounds((screenSize.width - getWidth()) - 400 + shift, 100, 150, 23);
        getContentPane().add(saveButt);
        saveButt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    saveButtAction(e);
                } catch (IOException ex) {
                    Logger.getLogger(GraphDrawing.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        openButt.setText("open");
        openButt.setBounds((screenSize.width - getWidth()) - 400 + 150 + shift, 100, 150, 23);
        getContentPane().add(openButt);
        openButt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    openButtAction(e);
                } catch (IOException ex) {
                    Logger.getLogger(GraphDrawing.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        helpButt.setText("Help");
        helpButt.setBounds((screenSize.width - getWidth()) - 400 + shift, 40, 300, 23);
        getContentPane().add(helpButt);
        helpButt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                helpButtAction(e);
            }
        });

        chooseButt.setText("Choose Canvas Color");
        chooseButt.setBounds((screenSize.width - getWidth()) - 400 + shift, 250, 300, 23);
        getContentPane().add(chooseButt);
        chooseButt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chooseButtAction(e);
            }
        });

        findpathButt.setText("Find Shortest Path");
        findpathButt.setBounds((screenSize.width - getWidth()) - 400 + shift, 150, 300, 23);
        getContentPane().add(findpathButt);
        findpathButt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                findshortestpath(e);
            }
        });

        clearButt.setText("Clear Canvas");
        clearButt.setBounds((screenSize.width - getWidth()) - 400 + shift, 200, 300, 23);
        getContentPane().add(clearButt);
        clearButt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clear(e);
            }
        });

        grayButt.setText("Gray");
        grayButt.setBounds((screenSize.width - getWidth()) - 400 + shift, 900, 150, 23);
        boxColor.add(grayButt);
        grayButt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                set_color_blue(e);
            }
        });

        whiteButt.setText("White");
        whiteButt.setBounds((screenSize.width - getWidth()) - 400 + 150 + shift, 900, 150, 23);
        boxColor.add(whiteButt);
        whiteButt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                set_color_white(e);
            }
        });

        orangeButt.setText("Orange");
        orangeButt.setBounds((screenSize.width - getWidth()) - 400 + 150 + shift, 870, 150, 23);
        boxColor.add(orangeButt);
        orangeButt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                set_color_orange(e);
            }
        });

        pinkButt.setText("Pink");
        pinkButt.setBounds((screenSize.width - getWidth()) - 400 + shift, 870, 150, 23);
        boxColor.add(pinkButt);
        pinkButt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                set_color_pink(e);
            }
        });

        //-----###e
        menubar.setBackground(Color.gray); /// menu bar color
        menubar.setBounds((screenSize.width - getWidth()) - 400, 0, 400, (screenSize.height - getHeight()));
        c.setBounds(0, 0, (screenSize.width - getWidth()) - 400, (screenSize.height - getHeight()));
        setBounds(0, 0, (screenSize.width - getWidth()), (screenSize.height - getHeight()));
        //setUndecorated(true);
        //setVisible(true);
        add(c);
        add(menubar);
        // setSize(1500, 1000);
        show();
    }

    //
    void chooseButtAction(ActionEvent e) {
        chooseColorFrame.setBounds(screenSize.width / 2 - 500, screenSize.height / 2 - 200, 480, 100);
        chooseColorFrame.setVisible(true);
    }

    void helpButtAction(ActionEvent e) {
        String help = "<html>";
        help += "Double click for create Vertex<br>";
        help += "Click on Vertex then type for rename<br>";
        help += "Click on Vertex or center of edge then it is blue you can edit etc move , rename , delete <br>";
        help += "Click on Vertex then press delete for remove Vertex<br>";
        help += "Press and hold spacebar with drag mouse for create edge<br>";
        help += "Click on character on edge then type for rename<br>";
        help += "Click on character on edge then drag mouse for move edge<br>";
        help += "Click on character on edge then press delete for remove edge<br>";
        help += "Press Button save for save Graph on canvas to json file<br>";
        help += "Press Button open for open Graph json file to canvas<br>";
        helpString.setText(help);
        helpString.setFont(sanSerifFont);
        boxHelp.add(helpString);
        boxHelp.setAutoscrolls(true);

        frameHelp.setBounds(screenSize.width / 2 - 500, screenSize.height / 2 - 200, 1000, 400);
        frameHelp.setVisible(true);
    }

    void saveButtAction(ActionEvent e) throws IOException {
        pathSave.setBounds(60, 120, 750, 450);
        boxSave.add(pathSave);

        int ret = pathSave.showDialog(null, "save");
        String path = "";

        if (ret == JFileChooser.APPROVE_OPTION) {
            File filePath = pathSave.getSelectedFile();
            path = filePath.getPath();
            save(path);
        }
    }

    void openButtAction(ActionEvent e) throws IOException {
        pathOpen.setBounds(60, 120, 750, 450);
        boxOpen.add(pathOpen);

        int ret = pathOpen.showDialog(null, "open");
        String path = "";

        if (ret == JFileChooser.APPROVE_OPTION) {
            File filePath = pathOpen.getSelectedFile();
            path = filePath.getPath();
            open(path);
            draw();
        }
    }

    public void save(String path) throws IOException {

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        FileWriter writer = new FileWriter(path);

        Backup backup = new Backup();
        writer.write(gson.toJson(backup));
        writer.close();
    }

    public void open(String path) throws FileNotFoundException {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        BufferedReader bufferedReader = new BufferedReader(new FileReader(path));

        Backup backup = gson.fromJson(bufferedReader, Backup.class);

        Vertexs = backup.VertexsBackup;
        Edge_s = backup.Edge_sBackup;

        //bind object reference
        for (Edge_ e : Edge_s) {
            if (e.vertexA != null) {
                int id = e.vertexA.id;
                for (Vertex v : Vertexs) {
                    if (v.id == id) {
                        e.vertexA = v;
                        break;
                    }
                }
            }
            if (e.vertexB != null) {
                int id = e.vertexB.id;
                for (Vertex v : Vertexs) {
                    if (v.id == id) {
                        e.vertexB = v;
                        break;
                    }
                }
            }
        }
    }

    //set canvas is white
    public void clear() {
        Graphics2D g = (Graphics2D) c.getGraphics();
        g.setColor(color);
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    /// Overload Function 
    public void clear(ActionEvent e) {
        Graphics2D g = (Graphics2D) c.getGraphics();
        g.setColor(color);
        g.fillRect(0, 0, getWidth(), getHeight());
        Vertexs.clear();
        Edge_s.clear();
    }

    public void selected(int x, int y) {
        Object obj = null;
        for (Vertex s : Vertexs) {
            if (s.inCircle(x, y)) {
                s.isSelect = true;
                obj = s;
                break;
            }
        }
        if (obj == null) {
            for (Edge_ t : Edge_s) {
                if (t.inLine(x, y)) {
                    t.isSelect = true;
                    obj = t;
                    break;
                }
            }
        }
        if (obj == null) {
            if (selected == null) {
                return;
            } else {
                if (selected instanceof Vertex) {
                    Vertex s = (Vertex) selected;
                    s.isSelect = false;
                } else {
                    Edge_ t = (Edge_) selected;
                    t.isSelect = false;
                }
                selected = null;
            }
        } else {
            if (selected == null) {
                selected = obj;
            } else {
                if (obj == selected) {
                    return;
                } else {
                    if (selected instanceof Vertex) {
                        Vertex s = (Vertex) selected;
                        s.isSelect = false;
                    } else {
                        Edge_ t = (Edge_) selected;
                        t.isSelect = false;
                    }
                    selected = obj;
                }
            }
        }
    }

    public void draw() {
        clear();

        Graphics2D g = (Graphics2D) c.getGraphics();
        g.setFont(sanSerifFont);

        for (Edge_ t : Edge_s) {
            t.draw(g);
        }
        if (TempEdge != null) {
            TempEdge.line(g);
        }
        for (Vertex s : Vertexs) {
            s.draw(g, color);
        }
        finddegreeButtAction();
    }

////////////////////////////////  UI EVENT  //////////////////////////////////
    // 3.1 mouse listener and mouse motion listener mehods 
    // keyboard listener and keyboard motion listener mehods 
    @Override
    public void keyTyped(KeyEvent ke) {
        //System.out.println("key " + ke.getKeyChar() + " = " + (int) ke.getKeyChar());
        if ((int) ke.getKeyChar() == 19) {
            try {
                //ctrl + S
                save("backup.json");
            } catch (IOException ex) {

            }
        } else if ((int) ke.getKeyChar() == 15) {
            try {
                //ctrl + O 
                open("backup.json");
            } catch (IOException ex) {

            }
        } else if ((int) ke.getKeyChar() == 14) {
            //ctrl + N 

        } else if ((int) ke.getKeyChar() == 12) {
            //ctrl + L

        } else if ((int) ke.getKeyChar() == 18) {
            //ctrl + R 

        } else if ((int) ke.getKeyChar() == 9) {

        } else if ((int) ke.getKeyChar() == 1) {
            //ctrl + A 

        }

        if (selected instanceof Vertex) {
            Vertex s = (Vertex) selected;
            int status = (int) ke.getKeyChar();
            if (status == 8) { //delete
                if (s.name.length() > 1) {
                    s.name = s.name.substring(0, s.name.length() - 1).trim();
                } else {
                    s.name = "".trim();
                }

            } else if (status == 127) { // space
                ArrayList<Edge_> TempEdge = new ArrayList<>();
                for (Edge_ t : Edge_s) {
                    if (t.vertexA == selected || t.vertexB == selected) {
                        TempEdge.add(t);
                    }
                }
                for (Edge_ t : TempEdge) {
                    Edge_s.remove(t);
                }
                Vertexs.remove(selected);
                selected = null;

            } else {
                s.name += ke.getKeyChar();
                s.name = s.name.trim();
            }

        } else if (selected instanceof Edge_) {
            Edge_ t = (Edge_) selected;
            int status = (int) ke.getKeyChar();
            if (status == 8) {
                if (t.weight.length() > 1) {
                    t.weight = t.weight.substring(0, t.weight.length() - 1).trim();
                } else {
                    t.weight = "".trim();
                }
            } else if (status == 127) {
                Edge_s.remove(selected);
                selected = null;

            } else {
                if (ke.getKeyChar() == ' ') {
                    return;
                }
                t.weight += ke.getKeyChar();
                t.weight = t.weight.trim();
            }

        }
        draw();
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        if ((int) ke.getKeyChar() == 32) {
            //press space bar
            mode = "Edge_";
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        //release space bar
        mode = "Vertex";
    }

    @Override
    public void mouseMoved(MouseEvent me) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        selected(x, y);
        if (e.getClickCount() == 2 && !e.isConsumed()) {
            e.consume();
            if (!Vertexs.contains(selected)) {
                Vertex TempVertex = new Vertex(x, y);
                Vertexs.add(TempVertex);
            }
        }
        draw();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        if (mode.equals("Vertex")) {
            if (selected != null) {
                if (selected instanceof Vertex) {
                    Vertex s = (Vertex) selected;
                    for (Edge_ t : Edge_s) {
                        if (t.vertexA == s || t.vertexB == s) {
                            int difx = x - s.x;
                            int dify = y - s.y;
                            if (t.vertexA != t.vertexB) {
                                if (t.vertexA != null) {
                                    t.x_center = (t.vertexA.x + t.vertexB.x) / 2;
                                    t.y_center = (t.vertexA.y + t.vertexB.y) / 2;
                                }
                            } else {
                                t.x_center += difx;
                                t.y_center += dify;
                            }
                        }
                    }
                    s.x = x;
                    s.y = y;
                } else {
                    Edge_ t = (Edge_) selected;
                    t.x_center = x;
                    t.y_center = y;
                }
            }

        } else if (mode.equals("Edge_")) {
            try {
                Vertex Vertex = null;
                for (Vertex s : Vertexs) {
                    if (s.inCircle(x, y)) {
                        Vertex = s;
                    }
                }
                if (Vertex != null) {
                    if (Vertex != TempEdge.vertexA) {
                        double angle = Math.atan2(TempEdge.vertexA.y - Vertex.y, TempEdge.vertexA.x - Vertex.x);
                        double dx = Math.cos(angle);
                        double dy = Math.sin(angle);
                        TempEdge.x1 = Vertex.x + (int) (Vertex.r * dx);
                        TempEdge.y1 = Vertex.y + (int) (Vertex.r * dy);
                    } else {
                        TempEdge.x1 = x;
                        TempEdge.y1 = y;
                    }
                } else {
                    TempEdge.x1 = x;
                    TempEdge.y1 = y;
                }
            } catch (Exception ex) {

            }
        }
        draw();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        if (mode.equals("Vertex")) {
            TempEdge = null;
        } else if (mode.equals("Edge_")) {
            try {
                TempEdge.x1 = x;
                TempEdge.y1 = y;
                Vertex vertexB = null;
                for (Vertex s : Vertexs) {
                    if (s.inCircle(x, y)) {
                        TempEdge.x1 = s.x;
                        TempEdge.y1 = s.y;
                        vertexB = s;
                        Edge_ edge = new Edge_(TempEdge.vertexA, vertexB);

                        if (s != TempEdge.vertexA) {
                            edge.x_center = (TempEdge.vertexA.x + s.x) / 2;
                            edge.y_center = (TempEdge.vertexA.y + s.y) / 2;
                        } else {
                            double angle = Math.atan2(y - TempEdge.vertexA.y, x - TempEdge.vertexA.x);
                            double dx = Math.cos(angle);
                            double dy = Math.sin(angle);

                            int rc = 3 * TempEdge.vertexA.r;

                            edge.x_center = TempEdge.vertexA.x + (int) (dx * rc);
                            edge.y_center = TempEdge.vertexA.y + (int) (dy * rc);
                        }
                        Edge_s.add(edge);
                        break;
                    }
                }
                TempEdge = null;
            } catch (Exception ex) {
            }
        }
        draw();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        if (mode.equals("Edge_")) {
            TempEdge = new TempEdge(x, y);
            for (Vertex s : Vertexs) {
                if (s.inCircle(x, y)) {
                    TempEdge.setA(s);
                    break;
                }
            }
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    /// add componet ///
    public void finddegreeButtAction() {
        Graphics2D g = (Graphics2D) c.getGraphics();
        g.setFont(sanSerifFont);
        degree.clear();
        int i = 0;
        while (i < Vertexs.size()) {
            int j = 0, count = 0;
            while (j < Edge_s.size()) {
                if ((Vertexs.get(i).id == Edge_s.get(j).vertexA.id && Vertexs.get(i).id == Edge_s.get(j).vertexB.id)) {
                    count += 2;
                } else if (Vertexs.get(i).id == Edge_s.get(j).vertexA.id || Vertexs.get(i).id == Edge_s.get(j).vertexB.id) {
                    count++;
                }
                j++;
            }
            degree.add(count);
            Vertex v = new Vertex(Vertexs.get(i).x, Vertexs.get(i).y);
            v.printdegree(degree.get(i), g);
            i++;
        }
    }

    public void findshortestpath(ActionEvent e) {
        ArrayList<Vertex> Q = new ArrayList<Vertex>();
        Q.clear();
        min_weight = 0;
        String Source, Sink;
        Source = JOptionPane.showInputDialog("Source Node");
        Sink = JOptionPane.showInputDialog("Sink Node");
        for (int i = 0; i < Vertexs.size(); i++) {
            if (Vertexs.get(i).name.matches(Source)) {
                //System.out.println("Start Vertes " + Vertexs.get(i).name); ///check
                Vertexs.get(i).dist = 0;
                Vertexs.get(i).prev = " ";
                Vertexs.get(i).visited = true;
                Q.add(Vertexs.get(i));
            } else {
                //System.out.println("Other Vertex " + Vertexs.get(i).name); /// check
                Vertexs.get(i).dist = Integer.MAX_VALUE;
            }
        }
        while (Q.isEmpty() == false) {
            int min = Integer.MAX_VALUE, min_index = 0;
            for (int i = 0; i < Q.size(); i++) {
                if (Integer.min(min, Q.get(i).dist) == Q.get(i).dist) {
                    min = Q.get(i).dist;
                    min_index = i;
                    //System.out.println("loop Q number " + i); /// chech
                    //System.out.println("min value = " + Q.get(min_index).name + "=" + min); /// check
                }
            }
            Vertex U = Q.get(min_index);
            Q.remove(Q.get(min_index));
            if (U.name.matches(Sink)) {
                min_weight = U.dist;
                break;
            }
            for (int i = 0; i < Edge_s.size(); i++) {
                if (Edge_s.get(i).vertexA.name.matches(U.name) && Edge_s.get(i).vertexA.prev != Edge_s.get(i).vertexB.name) { /// from a to b
                    //System.out.println(Edge_s.get(i).vertexA.name + " " + Edge_s.get(i).vertexB.name); /// check
                    int alt = U.dist + Integer.parseInt(Edge_s.get(i).weight);
                    //System.out.println("alt for " + Edge_s.get(i).vertexB.name + " = " + alt); /// check
                    if (alt < Edge_s.get(i).vertexB.dist) {
                        Edge_s.get(i).vertexB.dist = alt;
                        Edge_s.get(i).vertexB.prev = U.name;
                        if (Q.contains(Edge_s.get(i).vertexB) == false) {
                            Q.add(Edge_s.get(i).vertexB);
                        }
                    }
                } else if (Edge_s.get(i).vertexB.name.matches(U.name) && Edge_s.get(i).vertexB.prev != Edge_s.get(i).vertexA.name) { /// from b to a
                    //System.out.println(Edge_s.get(i).vertexB.name + " " + Edge_s.get(i).vertexA.name); /// check
                    int alt = U.dist + Integer.parseInt(Edge_s.get(i).weight);
                    if (alt < Edge_s.get(i).vertexA.dist) {
                        //System.out.println("alt for " + Edge_s.get(i).vertexA.name + " = " + alt); ///check
                        Edge_s.get(i).vertexA.dist = alt;
                        Edge_s.get(i).vertexA.prev = U.name;
                        if (Q.contains(Edge_s.get(i).vertexA) == false) {
                            Q.add(Edge_s.get(i).vertexA);
                        }
                    }
                }
            }
        }
        JFrame output = new JFrame();
        String path = " ";
        String pre = " ";
        /// for show each vertex in shortest path 
        /*
        for (int i = 0; i < Vertexs.size(); i++) {
            System.out.println("Vertexs = " + Vertexs.get(i).name + " Prev = " + Vertexs.get(i).prev);
        }
         */
        boolean finish = false;
        for (int k = Vertexs.size() - 1; k >= 0; k--) {
            if (Vertexs.get(k).name.matches(Sink)) {
                path = " -> " + Vertexs.get(k).name + path;
                pre = Vertexs.get(k).prev;
            } else if (Vertexs.get(k).name.matches(pre)) {
                path = " -> " + Vertexs.get(k).name + path;
                pre = Vertexs.get(k).prev;
            }
            for (int j = Vertexs.size() - 1; j >= 0; j--) {
                if (Vertexs.get(j).name.matches(pre)) {
                    if (pre.matches(Source)) {
                        path = Vertexs.get(j).name + path;
                        finish = true;
                    } else {
                        path = " -> " + Vertexs.get(j).name + path;
                        pre = Vertexs.get(j).prev;
                    }
                }
            }
            if (finish == true) {
                break;
            }
        }
        if (path.matches(" ")) {
            path = "No Input Or Graph!!";
        }
        String text = "Shortest Path (Min Sum Weight) = " + String.valueOf(min_weight) + " = " + path;
        JOptionPane.showMessageDialog(output, text);
    }

    /// Set Backgroud Color
    public void set_color_blue(ActionEvent e) {
        color = new Color(r = 192, g = 192, b = 192);
        draw();
    }

    public void set_color_white(ActionEvent e) {
        color = new Color(r = 255, g = 255, b = 255);
        draw();
    }

    public void set_color_orange(ActionEvent e) {
        color = new Color(r = 255, g = 200, b = 0);
        draw();
    }

    public void set_color_pink(ActionEvent e) {
        color = new Color(r = 255, g = 175, b = 175);
        draw();
    }
}
