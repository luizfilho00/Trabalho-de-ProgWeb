package com.example.a201619060353.atividadeextra2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Comparator;

public class ListAdapterGastos extends ArrayAdapter<Gasto> {

    int vg;
    ArrayList<Gasto> gastosList;
    Context context;

    public ListAdapterGastos (Context context, int vg, int id, ArrayList<Gasto> gastosList) {
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
            holder.txtTipo = rowView.findViewById(R.id.txtTipo);
            holder.txtSalario = rowView.findViewById(R.id.txtValor);
            rowView.setTag(holder);
        }

        GastosDAO bdGastos = new GastosDAO(context);
        ArrayList<Gasto> listaGasto = bdGastos.selectAll();
        listaGasto.sort(new Comparator<Gasto>() {
            @Override
            public int compare(Gasto g1, Gasto g2) {
                return Double.compare(g1.getValor(), g2.getValor());
            }
        });
        if (listaGasto.size() > 0){
            String[] items = listaGasto.get(position).toString().split("_");
            ViewHolder holder = (ViewHolder) rowView.getTag();
            holder.txtTipo.setText(items[0]);
            holder.txtSalario.setText(items[1]);
        }
        return rowView;
    }

}
