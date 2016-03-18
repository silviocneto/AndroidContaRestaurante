package silvio.com.contarestaurante;

/**
 * Created by Silvio on 3/16/2016.
 */
public class Integrante {
    private int id;
    private String name;
    private boolean selected;

    public Integrante(){}

    public Integrante (int id, String name){
        this.id = id;
        this.name = name;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setSelected(boolean isChecked){
        this.selected = isChecked;
    }

    public boolean isSelected(){
        return selected;
    }


    @Override
    public String toString() {
        /*return "Item{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", preco=" + preco +
                '}';
                */
        return name;

    }
}
