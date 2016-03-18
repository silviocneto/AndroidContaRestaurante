package silvio.com.contarestaurante;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CadastroIntegranteActivity extends Activity {

    EditText name_EditText;
    Button add_name_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro_integrante);

        name_EditText = (EditText) findViewById(R.id.name_EditText);
        add_name_button = (Button) findViewById(R.id.cadastra_name_button);

        final Integrante participante = new Integrante();
        final DBHelper dbHelper = new DBHelper(this);

        add_name_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String person_name = name_EditText.getText().toString();

                if(person_name.equals("")){
                    Toast.makeText(getApplicationContext(), "INSIRA O NOME DO INTEGRANTE", Toast.LENGTH_SHORT).show();
                }
                else {
                    participante.setName(person_name);
                    dbHelper.insertIntegrante(participante);
                    name_EditText.setText("");
                    finish(); //Prevents MostraParticipanteActivity of starting this activity again when back button is hit
                }
            }
        });
    }
}
