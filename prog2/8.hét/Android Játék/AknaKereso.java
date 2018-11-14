import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;



@SuppressWarnings("serial")
public class AknaKereso extends JPanel 
		implements  ActionListener{
	
	private static final int MAX_AKNA=25;
	private static final int SOROK=20, OSZLOPOK=20, OSSZES=SOROK*OSZLOPOK; 
	private JPanel  pnlMain      = new JPanel(new GridLayout(SOROK, OSZLOPOK));
	private JLabel  lblBombCount  = new JLabel(MAX_AKNA + "");
	private JLabel  lblTimer    = new JLabel("0");
	private JButton ujragomb    = new JButton("Visszaallit!");

	
	
	public static enum Allapot{
		Klikkelt, Jelolt, Alap, RosszulJelolt;		
	}
	
	public static enum JatekAlla{
		NemKezd, Megy, Bef;
	}

	private void Thread() {
	    Thread th = new Thread(new Runnable() {
	      public void run() {
	        while (all == JatekAlla.Megy) {
	          lblTimer.setText((Long.parseLong(lblTimer.getText()) + 1)+"");
	          lblTimer.updateUI();
	          try {
	            Thread.sleep(1000);
	          } catch (InterruptedException e) {
	            e.printStackTrace();
	          }
	        }
	      }
	    });
	    th.start();
	  }
	
	private JatekAlla  all  = JatekAlla.NemKezd;
	
	public AknaKereso() {
	    setLayout(new BorderLayout());
	    add(pnlMain, BorderLayout.CENTER);
	    gombok();
	    iranyit();
	  }

	private void ujraindit() {
	    all = JatekAlla.NemKezd;
	    lblTimer.setText("0");
	    pnlMain.removeAll();
	    gombok();
	    pnlMain.updateUI();
	    lblBombCount.setText("" + MAX_AKNA);
	    lblBombCount.updateUI();
	  }
	
	private void iranyit() {
	    JPanel pnlTimer = new JPanel(new FlowLayout(FlowLayout.RIGHT));

	    pnlTimer.add(lblTimer);

	    JPanel pnl = new JPanel(new FlowLayout(FlowLayout.CENTER));
	    
	    pnl.add(lblBombCount);
	    pnl.add(ujragomb);
	    JPanel pnlN = new JPanel(new GridLayout(1, 3));

	    pnlN.add(lblBombCount);
	    pnlN.add(pnl);
	    pnlN.add(pnlTimer);
	    add(pnlN, BorderLayout.NORTH);
	    ujragomb.addActionListener(this);
	  }
	
	
	private void gombok() {
	    List<Point> aknaHelyek = new ArrayList<Point>();

	    for (int row = 0; row < SOROK; row++) {
	      for (int col = 0; col < OSZLOPOK; col++) {
	        JButton btn = getGomb(aknaHelyek, OSSZES, new Point(row, col) {
	          @Override
	          public String toString() {
	            return (int) getX() + ", " + (int) getY();
	          }

	          @Override
	          public boolean equals(Object obj) {
	            return ((Point) obj).getX() == getX() && ((Point) obj).getY() == getY();
	          }
	        });
	        pnlMain.add(btn);
	      }
	    }
	    while (aknaHelyek.size() < MAX_AKNA) {
	      frissAkna(aknaHelyek, pnlMain.getComponents());
	    }
	    for (Component c : pnlMain.getComponents()) {
	      frissAknaSzam((Gomb) c, pnlMain.getComponents());
	    }
	  }
	
	private void frissAkna(List<Point> aknaHelyLst, Component[] components) {
	    Random r = new Random();
	    for (Component c : components) {
	      Point location = ((Gomb) c).getPosition();
	      int currentPosition = new Double(((location.x) * OSZLOPOK) + location.getY()).intValue();
	      int bombLocation = r.nextInt(OSSZES);
	      if (bombLocation == currentPosition) {
	        ((Gomb) c).setAkna(true);
	        aknaHelyLst.add(((Gomb) c).getPosition());
	        return;
	      }
	    }
	  }
	
	private Gomb getGomb(List<Point> aknaHelyLst, int oszHely, Point hely) {
		Gomb btn = new Gomb(hely);
	    btn.setMargin(new Insets(0, 0, 0, 0));
	    btn.setFocusable(false);
	    if (aknaHelyLst.size() < MAX_AKNA) {
	      if (aknae()) {
	        btn.setAkna(true);
	        aknaHelyLst.add(hely);
	      }
	    }
	    btn.addMouseListener(new MouseAdapter() {
	      @Override
	      public void mouseClicked(MouseEvent mouseEvent) {
	        if (all != JatekAlla.Megy) {
	        	all = JatekAlla.Megy;
	          Thread();
	        }
	        if (((Gomb) mouseEvent.getSource()).isEnabled() == false) {
	          return;
	        }
	        if (mouseEvent.getButton() == MouseEvent.BUTTON1) {
	          if (((Gomb) mouseEvent.getSource()).getAllapot() == Allapot.Jelolt) {
	            ((Gomb) mouseEvent.getSource()).setAllapot(Allapot.Alap);
	            lblBombCount.setText((Long.parseLong(lblBombCount.getText()) + 1) + "");
	            ((Gomb) mouseEvent.getSource()).updateUI();
	            return;
	          }
	          ((Gomb) mouseEvent.getSource()).setAllapot(Allapot.Klikkelt);
	          if (((Gomb) mouseEvent.getSource()).aknae()) {
	            robbanAkna();
	            return;
	          } else {
	            if (((Gomb) mouseEvent.getSource()).getAknaSzam() == 0) {
	              nullaFrissit(((Gomb) mouseEvent.getSource()).getPosition());
	            }
	          }
	          if (!jatekAllNez()) {
	            ((Gomb) mouseEvent.getSource()).setEnabled(false);
	          }
	        } else if (mouseEvent.getButton() == MouseEvent.BUTTON3) {
	          if (((Gomb) mouseEvent.getSource()).getAllapot() == Allapot.Jelolt) {
	            ((Gomb) mouseEvent.getSource()).setAllapot(Allapot.Alap);
	            lblBombCount.setText((Long.parseLong(lblBombCount.getText()) + 1) + "");
	          } else {
	            ((Gomb) mouseEvent.getSource()).setAllapot(Allapot.Jelolt);
	            lblBombCount.setText((Long.parseLong(lblBombCount.getText()) - 1) + "");
	          }
	        }
	        ((Gomb) mouseEvent.getSource()).updateUI();
	      }
	    });
	    return btn;
	  }
	
	private boolean jatekAllNez() {
	    boolean gyozelemE = false;
	    for (Component c : pnlMain.getComponents()) {
	      Gomb b = (Gomb) c;
	      if (b.getAllapot() != Allapot.Klikkelt) {
	        if (b.aknae()) {
	          gyozelemE = true;
	        } else {
	          return false;
	        }
	      }
	    }
	    if (gyozelemE) {
	      all = JatekAlla.Bef;
	      for (Component c : pnlMain.getComponents()) {
	        Gomb b = (Gomb) c;
	        if (b.aknae()) {
	          b.setAllapot(Allapot.Jelolt);
	        }
	        b.setEnabled(false);

	      }
	      JOptionPane.showMessageDialog(this, "Nyertél!! Király,  Időd:  " + lblTimer.getText() + "Mp.","WIN!WIN!WIN!", JOptionPane.INFORMATION_MESSAGE, null);
	    }
	    return gyozelemE;
	  }
	
	private void nullaFrissit(Point currentPoint) {
	    Point[] points = kornyezokFrissit(currentPoint);

	    for (Point p : points) {
	      Gomb b = getGombHely(pnlMain.getComponents(), p);
	      if (b != null && b.getAknaSzam() == 0 && b.getAllapot() != Allapot.Klikkelt && b.getAllapot() != Allapot.Jelolt && b.aknae() == false) {
	        b.setAllapot(Allapot.Klikkelt);
	        nullaFrissit(b.getPosition());
	        b.updateUI();
	      }
	      if (b != null && b.getAknaSzam() > 0 && b.getAllapot() != Allapot.Klikkelt && b.getAllapot() != Allapot.Jelolt && b.aknae() == false) {
	        b.setEnabled(false);
	        b.setAllapot(Allapot.Klikkelt);
	        b.updateUI();
	      }
	    }
	  }
	
	private void robbanAkna() {
	    int blastCount = 0;
	    for (Component c : pnlMain.getComponents()) {
	      ((Gomb) c).setEnabled(false);
	      ((Gomb) c).transferFocus();
	      if (((Gomb) c).aknae() && ((Gomb) c).getAllapot() != Allapot.Jelolt) {
	        ((Gomb) c).setAllapot(Allapot.Klikkelt);
	        ((Gomb) c).updateUI();
	        blastCount++;
	      }
	      if (((Gomb) c).aknae() == false && ((Gomb) c).getAllapot() == Allapot.Jelolt) {
	        ((Gomb) c).setAllapot(Allapot.RosszulJelolt);
	      }
	    }
	    lblBombCount.setText("" + blastCount);
	    lblBombCount.updateUI();
	    all = JatekAlla.Bef;
	    JOptionPane.showMessageDialog(this, "Vesztettél,  Időd:  " + lblTimer.getText() + "Mp.", "SZOMI", JOptionPane.ERROR_MESSAGE, null);
	    for (Component c : pnlMain.getComponents()) {
	    	Gomb b = (Gomb) c;
	      b.setEnabled(false);
	    }
	  }
	
	private boolean aknae() {
	    Random r = new Random();
	    return r.nextInt(SOROK) == 1;
	  }
	
	public static void main(String... args) {
	    JFrame fr = new JFrame("Aknakereso");
	    fr.setLayout(new BorderLayout());
	    fr.add(new AknaKereso());
	    fr.setResizable(true);
	    fr.setSize(600, 600);
	    fr.setLocationRelativeTo(null);
	    fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    fr.setVisible(true);
	  }
	
	class Gomb extends JButton {
	    private boolean  isBomb    = false;
	    private Point  position  = null;
	    private int    bombCount  = 0;
	    private Allapot  all    = Allapot.Alap;

	    public void setAllapot(Allapot state) {
	      this.all = state;
	      if (getAknaSzam() == 0 && !isBomb) {
	        setEnabled(false);
	      }
	    }

	    public Allapot getAllapot() {
	      return all;
	    }

	    public int getAknaSzam() {
	      return bombCount;
	    }

	    public void setAknaSzam(int bombCount) {
	      this.bombCount = bombCount;
	    }

	    public Gomb(Point position) {
	      setPosition(position);
	      setText(position.toString());
	    }

	    public Point getPosition() {
	      return position;
	    }

	    public void setPosition(Point position) {
	      this.position = position;
	    }

	    public boolean aknae() {
	      return isBomb;
	    }

	    public void setAkna(boolean isBomb) {
	      this.isBomb = isBomb;
	    }

	    @Override
	    public String getText() {
	      if (all == Allapot.Alap) {
	        return "";
	      }
	      if (all == Allapot.Jelolt) {
	        return "\u00D8";
	      }
	      if (all == Allapot.Klikkelt) {
	        if (isBomb) {
	          return "*";
	        } else {
	          if (getAknaSzam() > 0)
	            return getAknaSzam() + "";
	          else
	            return "";
	        }
	      }
	      if (all == Allapot.RosszulJelolt) {
	        return "X";
	      }
	      return super.getText();
	    }

	    @Override
	    public Color getBackground() {
	      if (all == Allapot.Klikkelt) {
	        if (isBomb) {
	          return Color.RED;
	        }
	      }
	      if (isEnabled()) {
	        return Color.WHITE.brighter();
	      } else {
	        return super.getBackground();
	      }
	    }
	  }
	
	private Point[] kornyezokFrissit(Point cPoint) {
	    int cX = (int) cPoint.getX();
	    int cY = (int) cPoint.getY();
	    Point[] points = { new Point(cX - 1, cY - 1), new Point(cX - 1, cY), new Point(cX - 1, cY + 1), new Point(cX, cY - 1), new Point(cX, cY + 1), new Point(cX + 1, cY - 1), new Point(cX + 1, cY), new Point(cX + 1, cY + 1) };
	    return points;
	  }

	  private void frissAknaSzam(Gomb btn, Component[] components) {
	    Point[] points = kornyezokFrissit(btn.getPosition());

	    for (Point p : points) {
	      Gomb b = getGombHely(components, p);
	      if (b != null && b.aknae()) {
	        btn.setAknaSzam(btn.getAknaSzam() + 1);
	      }
	    }
	    btn.setText(btn.getAknaSzam() + "");
	  }
	  
	  private Gomb getGombHely(Component[] components, Point position) {
		    for (Component btn : components) {
		      if ((((Gomb) btn).getPosition().equals(position))) {
		        return (Gomb) btn;
		      }
		    }
		    return null;
		  }

	  public void actionPerformed(ActionEvent actionEvent) {
		    if (actionEvent.getSource() == ujragomb) {
		      ujraindit();
		    }
		  }
		}