package silvio.com.contarestaurante;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class PedidoActivity extends Activity {

    Spinner Listaitens;
    ArrayAdapter<Item> adapter;
    MyCustomAdapter dataAdapter;
    public Item selecteditem;
    public int n;               //It's the number of people splitting the order
    public int p;               //It's the actual number that enters the tables.
                                // It's necessary because we must not change the value of n when pedido_button is clicked
    TextView item_quantity;
    CheckBox split_checkBox;
    CheckBox selectAll_checkbox;
    Button viewOrders;
    MyCustomAdapter.ViewHolder holder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fazer_pedido);

        Listaitens = (Spinner)findViewById(R.id.spinner_itens);
        item_quantity = (TextView)findViewById(R.id.quantity_TextView); //Textview que mostra o numero de itens
        viewOrders = (Button)findViewById(R.id.viewOrders_button);
        split_checkBox = (CheckBox)findViewById(R.id.split_checkBox);   //Checkbox que diz se divide ou nao o preco do item
        //selectAll_checkbox = (CheckBox)findViewById(R.id.checkBox_All);  //Select all de participants to buy the item(s)
        ImageButton GoPeople = (ImageButton)findViewById(R.id.order_people_button);
        ImageButton GoMenu = (ImageButton)findViewById(R.id.order_menu_button);
        ImageButton GoBill = (ImageButton)findViewById(R.id.order_bill_button);

        viewOrders.setOnClickListener(new View.OnClickListener() { // Comando para abrir a Activity que mostrará os pedidos ja feitos
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PedidoActivity.this, MostraPedidosActivity.class);
                startActivity(i);
            }
        });

        GoPeople.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent people = new Intent(PedidoActivity.this, MostraIntegranteActivity.class);
                startActivity(people);
                finish();
            }
        });

        GoMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menu = new Intent(PedidoActivity.this, MostraCardapioActivity.class);
                startActivity(menu);
                finish();
            }
        });

        GoBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bill = new Intent(PedidoActivity.this, ContaActivity.class);
                startActivity(bill);
                finish();
            }
        });

        n=0;

        split_checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(split_checkBox.isChecked()){
                    item_quantity.setText("QUANTIDADE: 1");
                }
                else{
                    item_quantity.setText("QUANTIDADE: " + Integer.toString(n));
                }
            }
        });

        DBHelper dbHelper_pedido = new DBHelper(this);
        List<Item> listItens_pedido = dbHelper_pedido.selectTodosOsItens();

        //adapter = new ArrayAdapter<Item>(this, android.R.layout.simple_spinner_item, listItens_pedido); Android's Default layout
        adapter = new ArrayAdapter<Item>(this,R.layout.pedido_item_layout, listItens_pedido);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);   Android's Default layout
        adapter.setDropDownViewResource(R.layout.menu_listview_layout);
        Listaitens.setAdapter(adapter);
        Listaitens.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Pegando o item selecionado para (no OrderButtonClick) inserir a id dele na tabela Pedidos (ao clicar no botao FAZER PEDIDO)
                selecteditem = (Item) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        displayListView();
        OrderButtonClick();

    }

    private void displayListView() {
        //Array de integrantes
        DBHelper DB_Integrantes = new DBHelper(this);
        List<Integrante> participList = DB_Integrantes.selectTodosOsIntegrantes(); // Converti a List que a DB cospe em uma ArrayList que
        ArrayList<Integrante> participArrayList = new ArrayList<Integrante>(participList); // o MyCustomAdapter usa

        //create an ArrayAdapter from the String Array
        dataAdapter = new MyCustomAdapter(this, R.layout.fazer_pedido_checkbox, participArrayList);
        ListView participListView = (ListView) findViewById(R.id.quem_consome_listview);
        // Assign adapter to ListView
        participListView.setAdapter(dataAdapter);
    }

    private class MyCustomAdapter extends ArrayAdapter<Integrante> {

        private ArrayList<Integrante> participList2;

        public MyCustomAdapter(Context context, int textViewResourceId, ArrayList<Integrante> participList) { //Método contrutor da classe - Possui o mesmo nome da classe
            super(context, textViewResourceId, participList);
            this.participList2 = new ArrayList<Integrante>();
            this.participList2.addAll(participList);
        }

        private class ViewHolder {
            CheckBox name;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            //ViewHolder holder = null;
            Log.v("ConvertView", String.valueOf(position));

            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.fazer_pedido_checkbox, null);

                holder = new ViewHolder();
                holder.name = (CheckBox) convertView.findViewById(R.id.checkBox);
                convertView.setTag(holder);

                holder.name.setOnClickListener( new View.OnClickListener() {
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v ;
                        Integrante participante = (Integrante) cb.getTag();
                        participante.setSelected(cb.isChecked());
                        if(cb.isChecked()){
                            n++;
                        }
                        else{
                            n--;
                        }

                        if(split_checkBox.isChecked()){
                            item_quantity.setText("QUANTIDADE: 1");
                        }
                        else{
                            item_quantity.setText("QUANTIDADE: " + Integer.toString(n));
                        }
                    }
                });
            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }

            Integrante participante = participList2.get(position);
            holder.name.setText(participante.getName());
            holder.name.setChecked(participante.isSelected());
            holder.name.setTag(participante);

            return convertView;

        }
    }

    private void OrderButtonClick(){
        Button pedido_button = (Button) findViewById(R.id.salvarpedido_button);
        final DBHelper dbHelper = new DBHelper(this);

        pedido_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Determinando o valor de n
                if(!split_checkBox.isChecked()){  //With the variable p we don't need to change the value of n if
                    p = 1;                        //split_checkBox is not checked
                }else{
                    p = n;                        //Because if we change the value of n, we can make another order after this one
                    //since n may be 1
                }
                //Inserindo o id do selecteditem na tabela Pedidos
                dbHelper.insertPedido(selecteditem, p);

                //Inserindo o id do pedido recem adicionado (aqui em cima) e os id dos integrantes na tabela PedidosIntegrantes
                ArrayList<Integrante> ArrayList = dataAdapter.participList2;
                for(int i=0;i<ArrayList.size();i++){
                    Integrante particip_add = ArrayList.get(i);
                    if(particip_add.isSelected()){
                        int IdLastPedido = dbHelper.getIdLastPedido();
                        dbHelper.insertPI(IdLastPedido,particip_add);

                        String idPedido = Integer.toString(IdLastPedido);
                        String idInteg = Integer.toString(particip_add.getId());
                        String number = Integer.toString(p);
                        Log.e("TAG",idPedido + "  " + idInteg + "  " + number);

                    }
                }
                Toast.makeText(getApplicationContext(), "Pedido Salvo", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
