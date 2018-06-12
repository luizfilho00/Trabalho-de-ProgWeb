package com.example.a201619060353.atividadeextra2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.a201619060353.atividadeextra2.dados.Cargo;
import com.example.a201619060353.atividadeextra2.modelo.CargoDAO;

import java.util.ArrayList;
import java.util.Comparator;

public class ListAdapterCargos extends ArrayAdapter<Cargo> {
    private int vg;
    private ArrayList<Cargo> cargoList;
    private Context context;

    public ListAdapterCargos(Context context, int vg, int id, ArrayList<Cargo> cargoList) {
        super(context, vg, id, cargoList);
        this.context = context;
        this.cargoList = cargoList;
        this.vg = vg;
    }

    static class ViewHolderCargo{
        public TextView txtCargoGC;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        View rowView = convertView;
        if (rowView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(vg, parent, false);
            ViewHolderCargo holder = new ViewHolderCargo();
            holder.txtCargoGC = rowView.findViewById(R.id.txtCargoGC);
            rowView.setTag(holder);
        }

        CargoDAO dbHelperCargo = new CargoDAO(context);
        ArrayList<Cargo> listCargo = dbHelperCargo.selectAll();
        listCargo.sort(new Comparator<Cargo>() {
            @Override
            public int compare(Cargo cargo, Cargo t1) {
                return cargo.getNomeDoCargo().compareToIgnoreCase(t1.getNomeDoCargo());
            }
        });
        ViewHolderCargo holder = (ViewHolderCargo) rowView.getTag();
        holder.txtCargoGC.setText(listCargo.get(position).toString());
        return rowView;
    }

}
