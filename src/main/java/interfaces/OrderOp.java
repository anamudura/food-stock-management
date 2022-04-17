package interfaces;

import bll.ClientBLL;
import bll.OrderBLL;
import bll.ProductBLL;
import connection.ConnectionFactory;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class OrderOp extends JPanel {
    JLabel newO, prod,quan,prompter,status,order;
    JTextField c4,c5,c6,s,c1;
    JButton place;

    public OrderOp(int height,int width)
    {
        setLayout(null);
        setBackground(new Color(160, 238, 163, 255));
        setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLUE));
        setPreferredSize(new Dimension(width, height));
        newO = new JLabel("SELECT CLIENT");
        prod = new JLabel("SELECT PRODUCT");
        order = new JLabel("ORDER ID");
        quan = new JLabel("ENTER QUANTITY");
        status = new JLabel("ORDER STATUS");
        prompter = new JLabel("ORDER DATABASE");
        c4 = new JTextField("Enter Client's ID");
        c5 = new JTextField("Enter Product's ID");
        c6 = new JTextField("Enter Product's quantity");
        c1 = new JTextField("Enter Order ID");
        s = new JTextField();

        place = new JButton("PLACE ORDER");


        add(newO);
        add(prod);
        add(quan);

        add(prompter);



        add(c4);
        add(c5);
        add(c6);
        add(place);
        add(status);
        add(order);
        add(c1);
        add(s);

        prompter.setBounds(180, height - 480, width - 250, 23);
        newO.setBounds(25, height - 430, width - 250, 23);
        c4.setBounds(25, height - 400, width - 250, 23);
        prod.setBounds(25, height - 370, width - 250, 23);
        c5.setBounds(25, height - 340, width - 250, 23);
        quan.setBounds(25, height - 310, width - 250, 23);
        c6.setBounds(25, height - 280, width - 250, 23);
        place.setBounds(25, height - 180, width - 250, 23);
        order.setBounds(25, height - 250, width - 250, 23);
        c1.setBounds(25, height - 220, width - 250, 23);
        status.setBounds(25, height - 150, width - 250, 23);
        s.setBounds(25, height - 120, width - 250, 23);

        place.addActionListener(new ActionListener() {
            @Override
            /**
            @param e : preia datele de la interfata, creeaza o comanda dupa rezultatul testarii, creeaza factura
             */
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(c4.getText());
                int pid = Integer.parseInt(c5.getText());
                int q = Integer.parseInt(c6.getText());
                int ord = Integer.parseInt(c1.getText());
                OrderBLL o = new OrderBLL();
                ProductBLL p = new ProductBLL();
                Connection connection = null;
                PreparedStatement statement = null;
                PreparedStatement statement1 = null;
                ResultSet res = null;
                ResultSet resc = null;
                String query = "SELECT * FROM product";
                String queryc = "SELECT * FROM client";
                try{
                    connection = ConnectionFactory.getConnection();
                    statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                    statement1 = connection.prepareStatement(queryc,Statement.RETURN_GENERATED_KEYS);
                    res = statement.executeQuery();
                    resc = statement1.executeQuery();
                    int i = 0;
                    while (res.next() && resc.next()) {
                        int idi = res.getInt("ID");
                        int quan = res.getInt("quantity");
                        int price = res.getInt("price");
                        String prodname = res.getString("nume");
                        String name = resc.getString("name");
                        if(idi == id) {
                            if (quan - q < 0)
                                s.setText("understock - order failed");
                            else {
                                int x = o.AddProduct(pid, id, q, ord);
                                p.UpdateProduct(id, "", quan - q, 0);
                                FileWriter f = new FileWriter("D:/Lab PT/PT2022_30221_Mudura_Ana_assigment_3/bill.txt");
                                f.write("ORDER NUMBER "+ ord +"\n"+"Name: "+name+"\n"+"Product name: "+prodname+"\n"+"Price: "+price+"\n"+"Total amount to pay: "+q*price);
                                s.setText("order completed");
                                f.close();
                            }
                        }
                        i++;
                    }

                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }

        });





    }
}
