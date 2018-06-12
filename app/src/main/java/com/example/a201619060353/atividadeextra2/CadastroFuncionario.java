package com.example.a201619060353.atividadeextra2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.a201619060353.atividadeextra2.dados.Cargo;
import com.example.a201619060353.atividadeextra2.dados.Datas;
import com.example.a201619060353.atividadeextra2.dados.Funcionario;
import com.example.a201619060353.atividadeextra2.dados.Gasto;
import com.example.a201619060353.atividadeextra2.modelo.Alert;
import com.example.a201619060353.atividadeextra2.modelo.CargoDAO;
import com.example.a201619060353.atividadeextra2.modelo.FuncionarioDAO;
import com.example.a201619060353.atividadeextra2.modelo.GastosDAO;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;

public class CadastroFuncionario extends AppCompatActivity {
    private int idFunc = -1;
    private String dataInicio;
    private EditText nomeFunc;
    private EditText salarioFunc;
    private DatePicker dtPickData;
    private Spinner spinnerCargo;
    private FuncionarioDAO dbHelperFunc;
    private CargoDAO dbHelperCargo;
    private GastosDAO dbHelperGasto;
    private double salarioAntigo;
    private String novoCargo = "Novo cargo...";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_funcionario);
        dbHelperCargo = new CargoDAO(this);
        dbHelperFunc = new FuncionarioDAO(this);
        dbHelperGasto = new GastosDAO(this);
        nomeFunc = findViewById(R.id.edtNomeFunc);
        salarioFunc = findViewById(R.id.edtSalario);
        dtPickData = findViewById(R.id.dtPickFunc);
        spinnerCargo = findViewById(R.id.spinCargo);
        dataInicio = dtPickData.getDayOfMonth() + "/" + (dtPickData.getMonth()+1) + "/" + dtPickData.getYear();
        carregarDados();
    }

    private void carregarDados(){
        final ArrayList<Cargo> listCargos = dbHelperCargo.selectAll();
        listCargos.sort(new Comparator<Cargo>() {
            @Override
            public int compare(Cargo cargo, Cargo t1) {
                return cargo.getNomeDoCargo().compareToIgnoreCase(t1.getNomeDoCargo());
            }
        });
        listCargos.add(0, new Cargo(" "));
        listCargos.add(new Cargo(novoCargo));
        ArrayAdapter<Cargo> listCargoAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, listCargos);
        listCargoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCargo.setAdapter(listCargoAdapter);
        spinnerCargo.setSelection(0);
        spinnerCargo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == listCargos.size() - 1){
                    Intent intent = new Intent(CadastroFuncionario.this, CadastroCargo.class);
                    startActivity(intent);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        dtPickData.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int year, int month, int dayOfMonth) {
                dataInicio = dayOfMonth + "/" + (month+1) + "/" + year;

            }
        });
//        dtPickData.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
//            @Override
//            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                dataInicio = dayOfMonth + "/" + (monthOfYear+1) + "/" + year;
//            }
//        });
        if (getIntent().getStringExtra("funcao") != null){
            if(getIntent().getStringExtra("funcao").equals("atualizar")){
                Bundle b = getIntent().getExtras();
                Funcionario f = (Funcionario) b.getSerializable("chave_func");
                idFunc = f.getId();
                nomeFunc.setText(f.getNome());
                Cargo cargo = f.getCargo();
                spinnerCargo.setSelection(listCargoAdapter.getPosition(cargo));
                salarioFunc.setText(String.valueOf(f.getSalario()));
                salarioAntigo = f.getSalario();
                ((Button) findViewById(R.id.btnVariavel)).setText("Alterar");
            }
        }
    }

    public void onClickCadastrarFunc(View view) {
        if (spinnerCargo == null || spinnerCargo.getSelectedItem().toString().equals(" ")){
            Alert.print(this, "Por favor selecione um Cargo!");
            return;
        }
        if (isNull()){
            Alert.print(this, "Por favor preencha todos os campos!");
            return;
        }
        String nome = nomeFunc.getText().toString();
        double salario = Double.valueOf(salarioFunc.getText().toString());
        String cargoNome = spinnerCargo.getSelectedItem().toString();
        int cargoId = dbHelperCargo.buscarCargo(cargoNome);
        Cargo cargo = new Cargo(cargoId, cargoNome);
        Funcionario f;
        long result;

        if (getIntent().getStringExtra("funcao") != null){
            if (getIntent().getStringExtra("funcao").equals("atualizar")){
                f = new Funcionario(idFunc, nome, cargo, dataInicio, salario);
                result = dbHelperFunc.alterarNoBanco(f);
                if(result != -1){
                    double difSalarios = modulo(salario - salarioAntigo);
                    if (salario < salarioAntigo)
                        dbHelperGasto.decrementarGasto(new Gasto("Salários"), difSalarios);
                    else if (salario > salarioAntigo)
                        dbHelperGasto.incrementarGasto(new Gasto("Salários"), difSalarios);
                    Alert.print(this,"Funcionário alterado com sucesso!");
                }else{
                    Alert.print(this, "Ocorreu um erro, por favor tente novamente.");
                }
            }
        }
        else{
            f = new Funcionario(nome, cargo, dataInicio, salario);
            result = dbHelperFunc.inserirNoBanco(f);
            if(result != -1){
                dbHelperGasto.incrementarGasto(new Gasto("Salários", Datas.PAGAMENTO_FUNC, 0, salario), salario);
                Alert.print(this, "Funcionário cadastrado com sucesso!");
            }else{
                Alert.print(this, "Ocorreu um erro, por favor tente novamente.");
            }
        }
        finish();
    }

    private double modulo(double valor){
        if (valor < 0) return -valor;
        else return valor;
    }

    private boolean isNull(){
        return nomeFunc.getText().toString().isEmpty() ||
                salarioFunc.getText().toString().isEmpty();
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarDados();
    }
}
