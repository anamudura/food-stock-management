package model;

public class Orders {
    private int id,product_ID,quantity,client_id;

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProduct_ID() {
        return product_ID;
    }

    public void setProduct_ID(int product_ID) {
        this.product_ID = product_ID;
    }

    public Orders(int id, int product_ID, int quantity, int client_id) {
        this.id = id;
        this.product_ID = product_ID;
        this.quantity = quantity;
        this.client_id = client_id;
    }

    @Override
    public String toString() {
        return "Client [id=" + id +", client_id="+client_id +", product_ID= "+product_ID +"]";
    }

}
