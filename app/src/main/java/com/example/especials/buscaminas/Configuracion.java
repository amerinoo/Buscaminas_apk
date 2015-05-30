package com.example.especials.buscaminas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


public class Configuracion extends Activity  implements RadioGroup.OnCheckedChangeListener {

    private String alias;
    private int tamañoParrilla;
    private boolean tiempo;
    private int minas;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);
        RadioGroup radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
        minas = Integer.parseInt(((RadioButton)findViewById( radioGroup.getCheckedRadioButtonId())).getText().toString());
        radioGroup.setOnCheckedChangeListener(this);
    }
    public void goDesarroloJuego(View v){
        editText = (EditText) findViewById(R.id.editTextAlias);
        if(!editText.getText().toString().trim().isEmpty()) {
            Intent in = new Intent(this, DesarrolloJuego.class);
            Bundle b = new Bundle();

            alias = ((EditText) findViewById(R.id.editTextAlias)).getText().toString();
            tamañoParrilla = Integer.parseInt(((EditText) findViewById(R.id.editTextTamañoParrilla)).getText().toString());
            tiempo = ((CheckBox) findViewById(R.id.checkBoxTiempo)).isChecked();
            b.putString("alias", alias);
            b.putInt("tamañoParrilla", tamañoParrilla);
            b.putBoolean("tiempo", tiempo);
            b.putInt("minas", minas);
            in.putExtras(b);
            startActivity(in);
            finish();
        }else{
            showToast("Has de poner un alias para empezar!");
        }

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        minas = Integer.parseInt(((RadioButton)findViewById(checkedId)).getText().toString());
    }

    private void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}
