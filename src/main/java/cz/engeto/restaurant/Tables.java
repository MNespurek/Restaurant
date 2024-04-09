package cz.engeto.restaurant;

public class Tables {

    private Order order;
    private Integer number;


    public Tables(Integer number, Order order)
    {
        this.number = number;
        this.order = order;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
