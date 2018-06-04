package com.example.a201619060353.atividadeextra2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Comparator;

public class ListAdapterGastos extends ArrayAdapter<Double> {

    int vg;
    ArrayList<Double> gastosList;
    Context context;

    public ListAdapterGastos (Context context, int vg, int id,  ArrayList<Double> gastosList) {
        super(context, vg, id, gastosList);
        this.context = context;
        this.vg = vg;
        this.gastosList = gastosList;
    }

    static class ViewHolder {
        public TextView txtTipo;
        public TextView txtSalario;
    }

    public View getView (int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        if (rowView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(vg, parent, false);
            ViewHolder holder = new ViewHolder();
            holder.txtTipo = rowView.findViewById(R.id.txtCargo);
            holder.txtSalario = rowView.findViewById(R.id.txtSalario);
            rowView.setTag(holder);
        }

        FuncionarioDAO dbHelperFunc = new FuncionarioDAO(context);
        ArrayList<Funcionario> listFunc = dbHelperFunc.selectAll();
        listFunc.sort(new Comparator<Funcionario>() {
            @Override
            public int compare(Funcionario f1, Funcionario f2) {
                return f1.getNome().compareToIgnoreCase(f2.getNome());
            }
        });
        String[] items = listFunc.get(position).toString().split("_");
        ViewHolder holder = (ViewHolder) rowView.getTag();
        holder.txtTipo.setText(items[1]);
        holder.txtSalario.setText(items[2]);
        return rowView;
    }

}
