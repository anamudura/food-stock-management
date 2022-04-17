package interfaces;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class All extends JPanel {
    JButton b1,b2,b3;
    public All(int width,int length)
    {
        b1 = new JButton("CLIENT DATABASE");
        b2 = new JButton("PRODUCT DATABASE");
        b3 = new JButton("ORDER DATABASE");
        add(b1);
        add(b2);
        add(b3);
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame window = new JFrame("Bloop");
                ClientOp o = new ClientOp(500,500);
                window.setContentPane(o);
                window.pack();
                window.setResizable(true);
                window.setLocation(150, 100);
                window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                window.setVisible(true);
            }
        });
       b2.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               JFrame window = new JFrame("Bloop");
               ProductOp o = new ProductOp(500,500);
               window.setContentPane(o);
               window.pack();
               window.setResizable(true);
               window.setLocation(150, 100);
               window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
               window.setVisible(true);

           }
       });
       b3.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               JFrame window = new JFrame("Bloop");
               OrderOp o = new OrderOp(500,500);
               window.setContentPane(o);
               window.pack();
               window.setResizable(true);
               window.setLocation(150, 100);
               window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
               window.setVisible(true);
           }
       });
    }


}
