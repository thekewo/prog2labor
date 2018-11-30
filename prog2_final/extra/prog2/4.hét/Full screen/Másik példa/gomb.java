import java.awt.*;
import java.awt.event.ActionEvent;

import javax.swing.*;


public class gomb{

    public static void main(String[] args){
        
        JFrame frame = new JFrame("gomb");
        //frame.getContentPane().setBackground(Color.PINK);


        //JPanel panel = new JPanel();
        //panel.setLayout(new FlowLayout());
        //frame.setLayout(new FlowLayout());
        frame.setLayout(new GridBagLayout());
        
        //private int clickCount = 0;

        JButton button = new JButton(new ImageIcon("meh.png"));
        button.setPreferredSize(new Dimension(400, 400));
        button.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                //clickCount++;
                //if(clickCount == 10){
                    button.setIcon(new ImageIcon("halt.png"));
                //}
            }
        });

        //panel.add(button);

        frame.add(button, new GridBagConstraints());
        //frame.add(button);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        



        GraphicsDevice d = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        if(d.isFullScreenSupported()){
            frame.setUndecorated(true);
            frame.setResizable(true);
            d.setFullScreenWindow(frame);
        } else {
            frame.setVisible(true);
        }

    }
    
}