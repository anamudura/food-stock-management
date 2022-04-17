package start;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import bll.ClientBLL;
import interfaces.All;
import interfaces.ClientOp;
import interfaces.OrderOp;
import model.Client;

import javax.swing.*;


public class Start {
	protected static final Logger LOGGER = Logger.getLogger(Start.class.getName());

	public static void main(String[] args) throws SQLException {

		ClientBLL studentBll = new ClientBLL();

		Client student1 = null;

		try {
			student1 = studentBll.findStudentById(1245);

		} catch (Exception ex) {
			LOGGER.log(Level.INFO, ex.getMessage());
		}

		JFrame window = new JFrame("SELECTION PANEL");
		All a = new All(500,500);
		window.setContentPane(a);
		window.pack();
		window.setResizable(true);
		window.setLocation(150, 100);
		window.setVisible(true);


	}

}
