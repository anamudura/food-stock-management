package bll;

import dao.ClientDAO;
import dao.OrderDAO;
import dao.ProductDAO;
import model.Orders;

public class OrderBLL {
    private OrderDAO orderDao;
    private ClientDAO clientDAO;
    private ProductDAO productDao;
    public OrderBLL ()
    {
        orderDao = new OrderDAO();
        clientDAO = new ClientDAO();
        productDao = new ProductDAO();
    }
    /**
     @param id,Product_ID,quantity: id-ul clientului si a produsul pentru comanda si cantitatea dorita
     @return : succesul plasarii comenzii
     */
    public int AddProduct(int ID_product,int id,int quantity,int oid)
    {

        Orders p = new Orders(oid,ID_product,quantity,id);
        int st = 0;
        int ok = 0;
        if((clientDAO.findById(id)!=null) && (productDao.findById(ID_product)!=null))
        {
            st = orderDao.insert(p);
            System.out.println(st);
            ok = 1;
        }
        if(ok == 1)
            return 0;
        else
            return -1;
    }

}
