package silvio.com.contarestaurante;

public class Pedido {
    private String name;
    private double number;

    public Pedido(){}

    public Pedido(String name, double number){
        this.name = name;
        this.number = number;
    }

    public void setName(String name){
        this.name = name;
    }
    public void setNumber(double number){
        this.number = number;
    }

    @Override
    public String toString() {
        return name + " x " + number;
    }
}
