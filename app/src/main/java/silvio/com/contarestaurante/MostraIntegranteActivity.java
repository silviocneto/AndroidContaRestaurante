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

public class MostraIntegranteActivity extends Activity {

    ListView ListParticipantes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ver_integrantes);

        Button AbreCadastroButton_P = (Button) findViewById(R.id.abrecadastro_participantes);
        ListParticipantes = (ListView) findViewById(R.id.listaparticipantes);
        ImageButton GoMenu = (ImageButton)findViewById(R.id.people_menu_button);
        ImageButton GoOrders = (ImageButton)findViewById(R.id.people_order_button);
        ImageButton GoBill = (ImageButton)findViewById(R.id.people_bill_button);

        AbreCadastroButton_P.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MostraIntegranteActivity.this, CadastroIntegranteActivity.class);
                startActivity(intent);
            }
        });

        GoMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menu = new Intent(MostraIntegranteActivity.this, MostraCardapioActivity.class);
                startActivity(menu);
                finish();
            }
        });
        GoOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent orders = new Intent(MostraIntegranteActivity.this, PedidoActivity.class);
                startActivity(orders);
                finish();
            }
        });
        GoBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bill = new Intent(MostraIntegranteActivity.this, ContaActivity.class);
                startActivity(bill);
                finish();
            }
        });
    }

    @Override
    protected void onResume() { //Sempre que voltar pra essa activity ele roda essa parte
        super.onResume();

        final DBHelper dbhelper = new DBHelper(this);
        final List<Integrante> listIntegrantes = dbhelper.selectTodosOsIntegrantes(); //Esse método retorna uma lista, logo tem que ser igual a um tipo de lista

        //Para passar a lista pro ListView é preciso adaptar ela usando esse método a seguir
        final ArrayAdapter<Integrante> adaptador = new ArrayAdapter<Integrante>(this,R.layout.menu_listview_layout, listIntegrantes);

        ListParticipantes.setAdapter(adaptador);

        Button Delete_people = (Button)findViewById(R.id.deletePeople_button);
        Delete_people.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!listIntegrantes.isEmpty()) {
                    dbhelper.DropIntegrantesTable();
                    dbhelper.DropPITable();
                    listIntegrantes.clear();
                    ListParticipantes.setAdapter(adaptador);
                }
            }
        });



    }
}
