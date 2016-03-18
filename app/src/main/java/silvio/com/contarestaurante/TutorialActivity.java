package silvio.com.contarestaurante;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TutorialActivity extends Activity {
    Button Startapp;
    TextView Tutorial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutorial);

        Startapp = (Button) findViewById(R.id.finishTutorial_button);
        Tutorial = (TextView) findViewById(R.id.tutorial_tv);

        String text = "COMO USAR O APP:" + '\n'
                + '\n' + "1) Adicione os itens a serem consumidos e divididos" + '\n'
                + '\n' + "2) Adicione os integrantes que irão consumir e dividir os itens" + '\n'
                + '\n' + "3) Faça um pedido de um item, marcando quem irá consumi-lo. Se clicar em DIVIDIR, esse item será" +
                " dividido igualmente entre os selecionados. Se não clicar em DIVIDIR, será contabilizado um item por pessoa." +
                " É apresentado no inferior da tela a quantidade de itens que serão contabilizados no total." + '\n'
                + '\n' + " 4) Pronto! Clique em CONTA para saber quanto cada um deverá pagar.";

        Tutorial.setText(text);
        Tutorial.setMovementMethod(new ScrollingMovementMethod());

        Startapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(TutorialActivity.this, MostraCardapioActivity.class);
                startActivity(it);
                finish();
            }
        });
    }
}
