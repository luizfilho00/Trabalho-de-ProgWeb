package com.example.a201619060353.atividadeextra2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.a201619060353.atividadeextra2.dados.Gasto;
import com.example.a201619060353.atividadeextra2.modelo.Alert;
import com.example.a201619060353.atividadeextra2.modelo.AlertReceiver;
import com.example.a201619060353.atividadeextra2.modelo.GastosDAO;

import java.util.Calendar;

public class CadastroGastos extends AppCompatActivity {
    private EditText edtTipo, edtValor;
    private DatePicker dtPickData;
    private GastosDAO bdGastos;
    private CheckBox flagPago;
    private int idGastoSelecionado;
    private Calendar c;
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
        c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, dtPickData.getDayOfMonth());
        c.set(Calendar.MONTH, dtPickData.getMonth()-1);
        c.set(Calendar.YEAR, dtPickData.getYear());
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        dtPickData.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int year, int month, int dayOfMonth) {
                data = dayOfMonth + "/" + (month+1) + "/" + year;
                c = Calendar.getInstance();
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                c.set(Calendar.MONTH, month-1);
                c.set(Calendar.YEAR, year);

            }
        });
//        dtPickData.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
//            @Override
//            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                data = dayOfMonth + "/" + (monthOfYear+1) + "/" + year;
//                c = Calendar.getInstance();
//                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//                c.set(Calendar.MONTH, monthOfYear-1);
//                c.set(Calendar.YEAR, year);
//            }
//        });
        bdGastos = new GastosDAO(this);
        preencheForm();
    }

    public void startAlarm(Calendar c){
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
        assert alarmManager != null;
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
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
        /// Se a despesa cadastrada já foi paga, não há necessidade de criar uma notificação para a data de vencimento.
        if (!flagPago.isChecked())
            startAlarm(c);
    }

    private boolean isNull(){
        return edtValor.getText().toString().isEmpty() || edtTipo.getText().toString().isEmpty();
    }


}
