package com.example.a201619060353.atividadeextra2;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;

public class ListAdapterGastos extends ArrayAdapter<Gasto> {

    private int vg;
    private ArrayList<Gasto> gastosList;
    private Context context;

    public ListAdapterGastos (Context context, int vg, int id, ArrayList<Gasto> gastosList) {
        super(context, vg, id, gastosList);
        this.context = context;
        this.vg = vg;
        this.gastosList = gastosList;
    }

    static class ViewHolder {
        public TextView txtTipo;
        public TextView txtData;
        public TextView txtValor;
    }

    public View getView (int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        if (rowView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(vg, parent, false);
            ViewHolder holder = new ViewHolder();
            holder.txtTipo = rowView.findViewById(R.id.txtTipo);
            holder.txtData = rowView.findViewById(R.id.txtDataGasto);
            holder.txtValor = rowView.findViewById(R.id.txtValor);
            rowView.setTag(holder);
        }
        Calendar hoje = Calendar.getInstance();
        int dia = hoje.get(Calendar.DAY_OF_MONTH);
        int mes = hoje.get(Calendar.MONTH) + 1;
        int ano = hoje.get(Calendar.YEAR);
        int posVencido = -1;
        Gasto bkpTotal = null;
        int corTotal = ContextCompat.getColor(context, R.color.corTotalGastos);
        for (Gasto g : gastosList){
            if (g.getTipo().equals("Total")){
                bkpTotal = g;
                gastosList.remove(g);
                continue;
            }
            String[] data = g.getData().split("/");
            int d = Integer.parseInt(data[0]);
            int m = Integer.parseInt(data[1]);
            int a = Integer.parseInt(data[2]);
            int corVencido = ContextCompat.getColor(context, R.color.corVencido);
            int corPago = ContextCompat.getColor(context, R.color.corPago);
            int corVenceHoje = ContextCompat.getColor(context, R.color.corVenceHoje);
            if (d < dia && m <= mes && a <= ano){
                posVencido = gastosList.indexOf(g);
                if (position == posVencido){
                    if (g.getPago() == 0)
                        rowView.setBackgroundColor(corVencido);
                    else
                        rowView.setBackgroundColor(corPago);
                }
            }
            else if (d == dia && m == mes && a == ano){
                posVencido = gastosList.indexOf(g);
                if (position == posVencido){
                    if (g.getPago() == 0)
                        rowView.setBackgroundColor(corVenceHoje);
                    else
                        rowView.setBackgroundColor(corPago);
                }
            }
            else if (m < mes && a <= ano){
                posVencido = gastosList.indexOf(g);
                if (position == posVencido){
                    if (g.getPago() == 0)
                        rowView.setBackgroundColor(corVencido);
                    else
                        rowView.setBackgroundColor(corPago);
                }
            }
            else if (a < ano){
                posVencido = gastosList.indexOf(g);
                if (position == posVencido){
                    if (g.getPago() == 0)
                        rowView.setBackgroundColor(corVencido);
                    else
                        rowView.setBackgroundColor(corPago);
                }
            }
        }
        gastosList.sort(new Comparator<Gasto>() {
            @Override
            public int compare(Gasto g1, Gasto g2) {
                String[] data1 = g1.getData().split("/");
                if (Integer.parseInt(data1[0]) < 10){
                    data1[0] = "0" + data1[0];
                }
                if (Integer.parseInt(data1[1]) < 10){
                    data1[1] = "0" + data1[1];
                }
                int d1 = Integer.parseInt(data1[0]);
                int m1 = Integer.parseInt(data1[1]);
                int a1 = Integer.parseInt(data1[2]);

                String[] data2 = g2.getData().split("/");
                int d2 = Integer.parseInt(data2[0]);
                int m2 = Integer.parseInt(data2[1]);
                int a2 = Integer.parseInt(data2[2]);

                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                Date aux_data1 = null;
                try {
                    aux_data1 = df.parse(a1 + "-" + m1 + "-" + d1);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Date aux_data2 = null;
                try {
                    aux_data2 = df.parse(a2 + "-" + m2 + "-" + d2);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if (aux_data1 != null && aux_data2 != null){
                    return aux_data1.compareTo(aux_data2);
                }
                return 0;
            }
        });
        if (bkpTotal != null)
            gastosList.add(bkpTotal);
        if (position == gastosList.size() - 1){
            String[] items = gastosList.get(position).toString().split("_");
            rowView.setBackgroundColor(corTotal);
            ViewHolder holder = (ViewHolder) rowView.getTag();
            holder.txtTipo.setText(items[0]);
            holder.txtData.setText("");
            holder.txtValor.setText(items[2]);
            holder.txtTipo.setTextSize(15);
            holder.txtTipo.setTypeface(holder.txtTipo.getTypeface(), Typeface.BOLD);
            holder.txtValor.setTextSize(15);
            holder.txtValor.setTypeface(holder.txtValor.getTypeface(), Typeface.BOLD);
        }
        else {
            String[] items = gastosList.get(position).toString().split("_");
            ViewHolder holder = (ViewHolder) rowView.getTag();
            holder.txtTipo.setText(items[0]);
            holder.txtData.setText(items[1]);
            holder.txtValor.setText(items[2]);
        }
        return rowView;
    }

}
