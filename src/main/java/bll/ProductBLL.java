package bll;

import java.util.NoSuchElementException;
import java.util.Objects;

import dao.ProductDAO;

import model.Product;
public class ProductBLL {

    private ProductDAO productDao;

    public ProductBLL() {

        productDao = new ProductDAO();
    }
    /**
     @param  id:id-ul produsul de cautat
     @return  id-ul gasit
     @throws NoSuchElementException daca nu exista produsul in baza de date
     */
    public Product findProductbyId(int id) {
        Product st = productDao.findById(id);
        if (st == null) {
            throw new NoSuchElementException("The product with id =" + id + " was not found!");
        }
        return st;
    }
    /**
     @param ID_product,nume,quantity,price: campurile tabelei in care se insereaza
     @return  noul produs inserat
     */
    public int AddProduct(int ID_product,String nume,int quantity,int price)
    {
        Product p = new Product(ID_product,nume,quantity,price);
        int st = productDao.insert(p);
        return st;
    }
    /**
     @param id,nume,quantity,price: campurile tabelei care pot fi actualizate
     @return noul produs actualizat
     */
    public Product UpdateProduct(int id, String nume, int quantity, int price)
    {

        if(!Objects.equals(nume,""))
            productDao.update(findProductbyId(id),"nume",nume, id);
        if(quantity !=0)
            productDao.update(findProductbyId(id),"quantity",quantity, id);
        if(price !=0)
            productDao.update(findProductbyId(id),"price",price, id);
        return new Product(-1, null,0,0);
    }
    /**
     @param id: id-ului clientului care se sterge
     */    public void DeleteClient(int id)
    {
        productDao.delete(id);
        System.out.println("am sters clientul");
    }


}
