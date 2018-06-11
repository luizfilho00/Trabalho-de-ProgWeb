package com.example.a201619060353.atividadeextra2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Comparator;

public class ListAdapter extends ArrayAdapter<Funcionario> {
    int vg;
    ArrayList<Funcionario> funcList;
    Context context;

    public ListAdapter(Context context, int vg, int id, ArrayList<Funcionario> funcList) {
        super(context, vg, id, funcList);
        this.context = context;
        this.funcList = funcList;
        this.vg = vg;
    }

    static class ViewHolder{
        public TextView txtNome;
        public TextView txtCargo;
        public TextView txtDataInicio;
        public TextView txtSalario;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        View rowView = convertView;
        if (rowView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(vg, parent, false);
            ViewHolder holder = new ViewHolder();
            holder.txtNome = rowView.findViewById(R.id.txtNome);
            holder.txtCargo = rowView.findViewById(R.id.txtCargo);
            holder.txtDataInicio = rowView.findViewById(R.id.txtInicioTrabalho);
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
        holder.txtNome.setText(items[0]);
        holder.txtCargo.setText(items[1]);
        holder.txtDataInicio.setText(items[2]);
        holder.txtSalario.setText(items[3]);
        return rowView;
    }

}
