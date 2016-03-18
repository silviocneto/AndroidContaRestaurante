package silvio.com.contarestaurante;

/**
 * Created by Silvio on 3/16/2016.
 */
public class Item {
    private int id;
    private String nome;
    private double preco;

    public Item(){}

    public Item(int id, String nome, double preco){
        this.id = id;
        this.nome = nome;
        this.preco = preco;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getNome(){
        return nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public double getPreco(){
        return preco*100/100;
    }

    public void setPreco(double preco){
        this.preco = preco*100/100;
    }

    @Override
    public String toString() {
        /*return "Item{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", preco=" + preco +
                '}';
                */
        return nome +"    " + "R$ " + preco;

    }
}
