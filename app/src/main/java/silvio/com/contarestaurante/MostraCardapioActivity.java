package silvio.com.contarestaurante;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.List;

public class MostraCardapioActivity extends Activity {

    ListView ListCardapio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ver_cardapio);

        Button AbreCadastroButton = (Button) findViewById(R.id.abrecadastro_cardapio);
        ListCardapio = (ListView) findViewById(R.id.listacardapio);
        ImageButton GoPeople = (ImageButton)findViewById(R.id.menu_people_button);
        ImageButton GoOrders = (ImageButton)findViewById(R.id.menu_order_button);
        ImageButton GoBill = (ImageButton)findViewById(R.id.menu_bill_button);

        AbreCadastroButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MostraCardapioActivity.this, CadastroCardapioActivity.class);
                startActivity(it);
            }
        });

        GoPeople.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent people = new Intent(MostraCardapioActivity.this, MostraIntegranteActivity.class);
                startActivity(people);
                finish();
            }
        });

        GoOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent orders = new Intent(MostraCardapioActivity.this, PedidoActivity.class);
                startActivity(orders);
                finish();
            }
        });

        GoBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bill = new Intent(MostraCardapioActivity.this, ContaActivity.class);
                startActivity(bill);
                finish();
            }
        });
    }

    @Override
    protected void onResume() { //Sempre que voltar pra essa activity ele roda essa parte
        super.onResume();

        final DBHelper dbHelper = new DBHelper(this);
        final List<Item> listItens = dbHelper.selectTodosOsItens(); //Esse método retorna uma lista, logo tem que ser igual a um tipo de lista

        //Para passar a lista pro ListView é preciso adaptar ela usando esse método a seguir
        final ArrayAdapter<Item> adaptador = new ArrayAdapter<Item>(this,R.layout.menu_listview_layout, listItens);

        ListCardapio.setAdapter(adaptador);

        Button Delete_all = (Button)findViewById(R.id.deleteAllitem_button);
        Delete_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.DropCardapioTable();
                dbHelper.DropPedidosTable();
                dbHelper.DropPITable();
                listItens.clear();
                ListCardapio.setAdapter(adaptador);
            }
        });

    }
}
