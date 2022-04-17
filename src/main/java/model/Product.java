package model;

public class Product {
    private int id,quantity,price;
    private String nume;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public Product(int id,String nume,int quantity, int price) {

        this.id = id;
        this.nume = nume;
        this.quantity = quantity;
        this.price = price;

    }
    public String toString() {
        return "Client [id=" + id +", quantity= "+quantity + "price="+price+"nume"+nume+"]";
    }
}
