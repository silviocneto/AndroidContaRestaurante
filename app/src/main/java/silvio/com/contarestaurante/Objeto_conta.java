package silvio.com.contarestaurante;

public class Objeto_conta {
    private int idpedido;
    private int idinteg;
    private String IntegName;
    private double TotalPagar;

    public Objeto_conta(){}

    public Objeto_conta(int idpedido, int idinteg, String IntegName){
        this.idpedido = idpedido;
        this.idinteg = idinteg;
        this.IntegName = IntegName;
    }

    public void setIdpedido(int idpedido){
        this.idpedido = idpedido;
    }

    public int getIdpedido(){
        return idpedido;
    }

    public void setTotal(double TotalValue){
        this.TotalPagar = TotalValue;
    }

    public void setIdinteg(int idinteg){
        this.idinteg = idinteg;
    }

    public int getIdinteg(){
        return idinteg;
    }

    public void setIntegName(String name){ this.IntegName = name;}

    @Override
    public String toString() {
        return IntegName + "   R$ " + TotalPagar;
    }
}
