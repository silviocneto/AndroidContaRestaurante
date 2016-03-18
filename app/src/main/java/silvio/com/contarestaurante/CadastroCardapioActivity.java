package silvio.com.contarestaurante;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.NumberFormat;

public class CadastroCardapioActivity extends Activity {

    EditText nome_item_EditText;
    EditText preco_item_EditText;
    Button add_item_button;
    NumberFormat nf;
    boolean isUpdating;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastrar_cardapio);

        nome_item_EditText = (EditText)findViewById(R.id.nome_item_EditText);
        preco_item_EditText = (EditText)findViewById(R.id.preco_item_EditText);
        add_item_button = (Button)findViewById(R.id.cadastra_item_button);

        preco_item_EditText.setInputType(InputType.TYPE_CLASS_NUMBER);
        nf = NumberFormat.getCurrencyInstance();
        isUpdating = false;
        preco_item_EditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(isUpdating){
                    isUpdating = false;
                    return; //This statement prevent entering in a loop
                }
                isUpdating = true;
                String str = s.toString();  //Gets the current text, with R$ and comma
                if(str.contains(",")){
                    str = str.replaceAll("[R$]","").replaceAll("[,]","");   //Removes R$ and comma, because we can only work with numbers
                    Log.e("str = ", str);
                }
                try{
                    preco_item_EditText.setText(nf.format(Double.parseDouble(str)/100));    //Divides by 100 and inserts the R$ and comma again
                    preco_item_EditText.setSelection(preco_item_EditText.getText().toString().length()); //Moves the cursor do the last number
                }catch(Exception e){
                    Log.e("ERRO","O erro foi: " + e);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        final Item item = new Item();
        final DBHelper dbHelper = new DBHelper(this);

        add_item_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String item_name = nome_item_EditText.getText().toString();
                String price_string = preco_item_EditText.getText().toString();//First we need to remove the R$ and the "," of the text
                price_string = price_string.replaceAll("[R$]","").replaceAll("[,]","."); //Removes the R$ and replaces the , by .
                // O [] indicates exactly the character that must be replaced
                if(item_name.equals("")||price_string.equals("")){
                    Toast.makeText(getApplicationContext(), "EXISTEM CAMPOS EM BRANCO", Toast.LENGTH_SHORT).show();
                }
                else {
                    Double price_item = Double.parseDouble(price_string);
                    item.setNome(item_name);
                    item.setPreco(price_item);

                    dbHelper.insertItem(item);

                    nome_item_EditText.setText("");
                    preco_item_EditText.setText("");
                    finish(); //Prevents MostraCardapioActivity of starting this activity again when back button is hit
                }
            }
        });
    }
}
