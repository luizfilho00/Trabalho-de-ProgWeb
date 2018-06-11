package com.example.a201619060353.atividadeextra2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;

public class CadastroGastos extends AppCompatActivity {
    private EditText edtTipo, edtValor;
    private DatePicker dtPickData;
    private GastosDAO bdGastos;
    private CheckBox flagPago;
    String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_gastos);
        edtTipo = findViewById(R.id.edtTipoGasto);
        dtPickData = findViewById(R.id.datePickerGasto);
        edtValor = findViewById(R.id.edtValorDespesa);
        flagPago = findViewById(R.id.checkBoxPago);
        data = dtPickData.getDayOfMonth() + "/" + (dtPickData.getMonth()+1) + "/" + dtPickData.getYear();
        dtPickData.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                data = dayOfMonth + "/" + (monthOfYear+1) + "/" + year;
            }
        });
        bdGastos = new GastosDAO(this);
    }

    public void onClickCadastrarGasto(View view) {
        String tipo = edtTipo.getText().toString();
        int pago;
        if (flagPago.isChecked())
            pago = 1;
        else
            pago = 0;
        double valor = Double.valueOf(edtValor.getText().toString());
        double retorno = bdGastos.inserirNoBanco(new Gasto(tipo, data, pago, valor));
        if (retorno != -1)
            Alert.print(this, "Despesa cadastrada com sucesso!");
        else
            Alert.print(this, "Ocorreu um erro ao cadastrar esta despesa, por favor tente novamente.");
        finish();
    }


}
