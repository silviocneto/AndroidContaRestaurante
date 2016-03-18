package silvio.com.contarestaurante;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ContaActivity extends Activity {

    ListView ListConta;
    TextView total_tv;
    CheckBox service_cb;
    public double total;
    ImageButton GoMenu;
    ImageButton GoPeople;
    ImageButton GoOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ver_conta);
        ListConta = (ListView)findViewById(R.id.conta_listview);
        total_tv = (TextView)findViewById(R.id.total_textView);
        service_cb = (CheckBox)findViewById(R.id.service_checkbox);
        GoPeople = (ImageButton)findViewById(R.id.bill_people_button);
        GoMenu = (ImageButton)findViewById(R.id.bill_menu_button);
        GoOrder = (ImageButton)findViewById(R.id.bill_order_button);
    }

    @Override
    protected void onResume() { //Sempre que voltar pra essa activity ele roda essa parte
        super.onResume();

        try{// Declares the DataBaseHelper that is gonna be used
            final DBHelper DBHelper = new DBHelper(this);
            // Get the PeopleList present on the table
            final List<Integrante> People = DBHelper.selectTodosOsIntegrantes();
            // Creates a List with objects(People) to be insert into the Listview
            final List<Objeto_conta> Bill = new ArrayList<>();  //Bill it's a list with Objeto_conta, which are the "person-price" objects
            // Calculates the Bill with no tip
            GetBill(DBHelper,People,Bill,1.0);

            service_cb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bill.clear();
                    if(service_cb.isChecked()){
                        GetBill(DBHelper,People,Bill,1.1); //Calculates de bill with 10% tip
                    }
                    else {
                        GetBill(DBHelper, People, Bill, 1.0); // Calculates the Bill with no tip
                    }
                }
            });

            GoMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent menu = new Intent(ContaActivity.this, MostraCardapioActivity.class);
                    startActivity(menu);
                    finish();
                }
            });

            GoPeople.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent people = new Intent(ContaActivity.this, MostraIntegranteActivity.class);
                    startActivity(people);
                    finish();
                }
            });

            GoOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent order = new Intent(ContaActivity.this, PedidoActivity.class);
                    startActivity(order);
                    finish();
                }
            });

        } catch (Exception e){
            Log.i("ERRO", "ERROU" + e);
        }
    }

    // This method calculates how much each person have to pay and insert it into the listview
    // It must be informed the DataBaseHelper used to access the Database, the list of present people, the list of objects (person,value) and the service tip + 1
    private void GetBill(DBHelper DataBaseHelper, List<Integrante> PeopleList, List<Objeto_conta> Bill, Double ServicePercentage){
        total = 0;
        for(int i = 1; i <= PeopleList.size(); i++){
            String id_integ = Integer.toString(i);
            Cursor c = DataBaseHelper.getReadableDatabase().rawQuery("SELECT name_integrante, pedido_number, price_item FROM PedidosIntegrantes " +
                    "INNER JOIN Integrantes " +
                    "ON (PedidosIntegrantes.id_PI_integrante = Integrantes.id_integrante) " +
                    "INNER JOIN Pedidos " +
                    "ON (PedidosIntegrantes.id_PI_pedido = Pedidos.id_pedido) " +
                    "INNER JOIN Cardapio " +
                    "ON (Pedidos.id_pedido_item = Cardapio.id_item) " +
                    "WHERE Integrantes.id_integrante = '" + id_integ + "'", null); // Copiei da net este excesso de aspas no id_integ
            if(c.moveToFirst()) {
                double x = 0;
                Objeto_conta objeto = new Objeto_conta();
                objeto.setIntegName(c.getString(0));
                do {
                    x += c.getDouble(2) / c.getInt(1);    //corresponds to x = x + c.getDouble(2)/c.getInt(1);
                } while (c.moveToNext());

                objeto.setTotal(x * ServicePercentage);

                total = total + x * ServicePercentage;
                Bill.add(objeto);
            }
            //String total_text2 = Double.toString(total);
            //Log.i("VALOR TOTAL", total_text2);
        }
        ArrayAdapter<Objeto_conta> adapter = new ArrayAdapter<>(this, R.layout.listview_layout, Bill);
        ListConta.setAdapter(adapter);
        String total_text = Double.toString(total);
        total_tv.setText("Total: R$ " + total_text);
    }
}
