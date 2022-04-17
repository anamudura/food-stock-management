package dao;

import connection.ConnectionFactory;
import model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

public class ProductDAO extends AbstractDAO<Product>{
    private String createSelectQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append("product ");
        sb.append(" WHERE " + field + " =?");
        System.out.println(sb.toString());
        return sb.toString();
    }
    @Override
    public Product findById(int id) {
        Product prod = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet res = null;
        String query = createSelectQuery("id");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            res = statement.executeQuery();
            while(res.next())
            {
                int idi = res.getInt("ID");
                String nom = res.getString("nume");
                int quan = res.getInt("quantity");
                int p = res.getInt("price");
                prod = new Product(idi,nom,quan,p);
            }
            return prod;
        } catch (SQLException e) {

        } finally {
            ConnectionFactory.close(res);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }
}
