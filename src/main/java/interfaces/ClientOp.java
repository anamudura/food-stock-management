package interfaces;

import bll.ClientBLL;
import connection.ConnectionFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ClientOp extends JPanel {
    JLabel newC,editC,deleteC,viewC,prompter;
    JTextField c1,c2,c5,c6;
    JButton add,edit,delete,view;
    JTable table;
    public ClientOp(int height,int width)
    {
        setLayout(null);
        setBackground(new Color(160, 238, 163, 255));
        setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLUE));
        setPreferredSize(new Dimension(width, height));
        newC = new JLabel("ADD NEW CLIENT");
        editC = new JLabel("EDIT CLIENT");
        deleteC = new JLabel("DELETE CLIENT");
        viewC = new JLabel("VIEW CLIENTS");
        prompter = new JLabel("CLIENT DATABASE");
        c1 = new JTextField("ID");
        c2 = new JTextField("Nume");
        c6 = new JTextField("email");

        add = new JButton("ADD");
        edit = new JButton("EDIT");
        delete = new JButton("DELETE");
        view = new JButton("VIEW");

        table = new JTable(10,10);

        add(newC);
        add(editC);
        add(deleteC);
        add(viewC);
        add(prompter);

        add(add);
        add(edit);
        add(delete);
        add(view);

        add(c1);
        add(c2);
        add(c6);

        prompter.setBounds(180, height - 480, width - 250, 23);
        newC.setBounds(25, height - 430, width - 250, 23);
        add.setBounds(180, height - 430, width - 250, 23);
        c1.setBounds(25, height - 400, width - 250, 23);
        c2.setBounds(25, height - 380, width - 250, 23);
        c6.setBounds(25, height - 360, width - 250, 23);
        editC.setBounds(25, height - 290, width - 250, 23);
        edit.setBounds(180, height - 290, width - 250, 23);
        deleteC.setBounds(25, height - 220, width - 250, 23);
        delete.setBounds(180, height - 220, width - 250, 23);
        viewC.setBounds(25, height - 150, width - 250, 23);
        view.setBounds(180, height - 150, width - 250, 23);
        table.setBounds(180, height - 120, width - 250, 23);
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nume = c2.getText();
                int id = Integer.parseInt(c1.getText());
                String email = c6.getText();
                ClientBLL client = new ClientBLL();
                client.AddClient(id, nume,email);
            }
        });
        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nume = c2.getText();
                String id = c1.getText();
                String email = c6.getText();
                ClientBLL client = new ClientBLL();
                client.UpdateClient(Integer.parseInt(id),nume,email);
            }
        });
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(c1.getText());
                ClientBLL c = new ClientBLL();
                c.DeleteClient(id);
            }
        });
        view.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame f = new JFrame("CLIENT TABLE");
                Connection connection = null;
                PreparedStatement statement = null;
                ResultSet res = null;
                String data[][] = new String[100][4];
                String columns[] = {"ID","nume","email"};
                String query = "SELECT * FROM client";
                try{
                    connection = ConnectionFactory.getConnection();
                    statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                    res = statement.executeQuery();
                    int i = 0;
                    while (res.next()) {
                        int id = res.getInt("ID");
                        String nom = res.getString("name");
                        String email = res.getString("email");
                        data[i][0] = id + "";
                        data[i][1] = nom;
                        data[i][2] = email;
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
