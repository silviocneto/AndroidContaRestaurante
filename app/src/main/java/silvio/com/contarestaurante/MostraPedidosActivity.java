package silvio.com.contarestaurante;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MostraPedidosActivity extends Activity {

    ListView OrderList_listview;
    Button deleteAll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ver_pedidos);

        OrderList_listview = (ListView)findViewById(R.id.viewOrders_ListView);
        deleteAll = (Button)findViewById(R.id.deleteAll_orders_button);

        final DBHelper DBHelper = new DBHelper(this);
        List<Item> ItensList = DBHelper.selectTodosOsItens();
        final List<Pedido> OrderList = new ArrayList<>();

        for(int i=1 ; i<=ItensList.size() ; i++){
            String id_item = Integer.toString(i);
            Cursor c = DBHelper.getReadableDatabase().rawQuery("SELECT pedido_number, id_item, name_item FROM PedidosIntegrantes " +
                    "INNER JOIN Pedidos " +
                    "ON (PedidosIntegrantes.id_PI_pedido = Pedidos.id_pedido) " +
                    "INNER JOIN Cardapio " +
                    "ON (Pedidos.id_pedido_item = Cardapio.id_item) " +
                    "WHERE id_item = '" + id_item + "'", null);

            Pedido Pedido = new Pedido(); //O numero de vezes que pediu o item i é igual ao numero de linhas do resultado da SQL
            if(c.moveToFirst()){
                double x = 0;
                Pedido.setName(c.getString(2)); //Pega a string correspondente ao nome do item que esta na coluna 2 (lembrando que as colunas são 0, 1, 2,...
                do{
                    x += 1.00/c.getInt(0);     //Gets the right quantity of the item, even if it was shared
                    //1.00 is used so the ratio is double/int type instead of int/int
                }while(c.moveToNext());

                Pedido.setNumber(x);
                OrderList.add(Pedido);
            }
        }

        ArrayAdapter<Pedido> Adapter = new ArrayAdapter<Pedido>(this, R.layout.listview_layout, OrderList);
        OrderList_listview.setAdapter(Adapter);

        deleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper.DropPedidosTable();
                DBHelper.DropPITable();
                OrderList.clear();
                finish();
            }
        });
    }
}
