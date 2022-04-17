package interfaces;

import bll.ProductBLL;
import connection.ConnectionFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class ProductOp extends JPanel {
    JLabel newP, editP, deleteP, viewP, prompter;
    JTextField c1, c2, c5, c6, c7;
    JButton add, edit, delete, view;
    JTable table;

    public ProductOp(int height, int width) {
        setLayout(null);
        setBackground(new Color(160, 238, 163, 255));
        setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLUE));
        setPreferredSize(new Dimension(width, height));
        newP = new JLabel("ADD NEW PRODUCT");
        editP = new JLabel("EDIT PRODUCT");
        deleteP = new JLabel("DELETE CLIENT");
        viewP = new JLabel("VIEW ORDER");
        prompter = new JLabel("PRODUCT DATABASE");
        c1 = new JTextField("ID");
        c2 = new JTextField("Nume");
        c6 = new JTextField("Quantity");
        c7 = new JTextField("Price");

        add = new JButton("ADD");
        edit = new JButton("EDIT");
        delete = new JButton("DELETE");
        view = new JButton("VIEW");

        table = new JTable(10, 10);

        add(newP);
        add(editP);
        add(deleteP);
        add(viewP);
        add(prompter);

        add(add);
        add(edit);
        add(delete);
        add(view);

        add(c1);
        add(c2);
        add(c6);
        add(c7);


        prompter.setBounds(180, height - 480, width - 250, 23);
        newP.setBounds(25, height - 430, width - 250, 23);
        add.setBounds(180, height - 430, width - 250, 23);
        c1.setBounds(25, height - 400, width - 330, 23);
        c2.setBounds(25, height - 380, width - 330, 23);
        c6.setBounds(250, height - 400, width - 330, 23);
        c7.setBounds(250, height - 380, width - 330, 23);
        editP.setBounds(25, height - 320, width - 250, 23);
        edit.setBounds(180, height - 320, width - 250, 23);
        deleteP.setBounds(25, height - 250, width - 250, 23);
        delete.setBounds(180, height - 250, width - 250, 23);
        viewP.setBounds(25, height - 180, width - 250, 23);
        view.setBounds(180, height - 180, width - 250, 23);
        table.setBounds(180, height - 150, width - 250, 23);
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int pID = Integer.parseInt(c1.getText());
                String nume = c2.getText();
                int q = Integer.parseInt(c6.getText());
                int price = Integer.parseInt(c7.getText());
                ProductBLL p = new ProductBLL();
                p.AddProduct(pID, nume, q, price);

            }
        });
        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(c1.getText());
                String nume = c2.getText();
                int q = Integer.parseInt(c6.getText());
                int price = Integer.parseInt(c7.getText());
                ProductBLL p = new ProductBLL();
                p.UpdateProduct(id, nume, q, price);
            }
        });
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(c1.getText());
                ProductBLL p = new ProductBLL();
                p.DeleteClient(id);
            }
        });
        view.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame f = new JFrame("PRODUCT TABLE");
                Connection connection = null;
                PreparedStatement statement = null;
                ResultSet res = null;
                String data[][] = new String[5][4];
                String columns[]={"ID","nume","pret","cantitate"};
                String query = "SELECT * FROM product";
                try {
                    connection = ConnectionFactory.getConnection();
                    statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                    res = statement.executeQuery();
                    int i = 0;
                    while (res.next()) {
                        int id = res.getInt("ID");
                        String nom = res.getString("nume");
                        int quan = res.getInt("quantity");
                        int p = res.getInt("price");
                        data[i][0] = id + "";
                        data[i][1] = nom;
                        data[i][2] = quan + "";
                        data[i][3] = p + "";
                        i++;
                    }
                    DefaultTableModel model = new DefaultTableModel(data, columns);
                    JTable table = new JTable(model);
                    table.setShowGrid(true);
                    table.setShowVerticalLines(true);
                    JScrollPane pane = new JScrollPane(table);
                    JPanel panel = new JPanel();
                    panel.add(pane);
                    f.add(panel);
                    f.setSize(500, 250);
                    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    f.setVisible(true);

                } catch (Exception exception) {
                    exception.printStackTrace();
                }


            }
        });
    }

}
