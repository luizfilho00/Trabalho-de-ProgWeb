package com.example.a201619060353.atividadeextra2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;

public class CadastroGastos extends AppCompatActivity {
    private EditText edtTipo, edtValor;
    private DatePicker dtPickData;
    private GastosDAO bdGastos;
    private CheckBox flagPago;
    private int idGastoSelecionado;
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

        preencheForm();
    }

    private void preencheForm(){
        boolean auxFlagPago;
        String nomeGasto, dataPgto;
        double valorGasto;
        if (getIntent().getStringExtra("funcao") != null){
            if(getIntent().getStringExtra("funcao").equals("atualizar")){
                Bundle b = getIntent().getExtras();
                Gasto g = (Gasto) b.getSerializable("chave_gasto");
                if (g.getTipo().equals("Salários")) {
                    findViewById(R.id.txtViewTipo).setVisibility(View.INVISIBLE);
                    edtTipo.setVisibility(View.INVISIBLE);
                }
                idGastoSelecionado = g.getId();
                nomeGasto = g.getTipo();
                dataPgto = g.getData();
                if (g.getPago() == 0)
                    auxFlagPago = false;
                else
                    auxFlagPago = true;
                valorGasto = g.getValor();
                edtTipo.setText(nomeGasto);
                edtValor.setText(String.valueOf(valorGasto));
                flagPago.setChecked(auxFlagPago);
                String[] dataFormatada = dataPgto.split("/");
                dtPickData.updateDate(Integer.parseInt(dataFormatada[2]),
                        Integer.parseInt(dataFormatada[1]) - 1, Integer.parseInt(dataFormatada[0]));
                ((Button) findViewById(R.id.btnCadastrarGasto)).setText("Alterar");
            }
        }
    }

    public void onClickCadastrarGasto(View view) {
        if (isNull()){
            Alert.print(this, "Por favor preencha todos os campos!");
            return;
        }
        String tipo = edtTipo.getText().toString();
        int pago;
        if (flagPago.isChecked())
            pago = 1;
        else
            pago = 0;
        double valor = Double.valueOf(edtValor.getText().toString());
        if (getIntent().getStringExtra("funcao") != null){
            if (getIntent().getStringExtra("funcao").equals("atualizar")){
                double retorno = bdGastos.alterarNoBanco(new Gasto(idGastoSelecionado, tipo, data, pago, valor));
                if (retorno != -1)
                    Alert.print(this, "Despesa alterada com sucesso!");
                else
                    Alert.print(this, "Ocorreu um erro ao alterar esta despesa, por favor tente novamente.");
            }
        }
        else{
            double retorno = bdGastos.inserirNoBanco(new Gasto(tipo, data, pago, valor));
            if (retorno != -1)
                Alert.print(this, "Despesa salva com sucesso!");
            else
                Alert.print(this, "Ocorreu um erro ao salvar esta despesa, por favor tente novamente.");
        }

        finish();
    }

    private boolean isNull(){
        return edtValor.getText().toString().isEmpty() || edtTipo.getText().toString().isEmpty();
    }


}
