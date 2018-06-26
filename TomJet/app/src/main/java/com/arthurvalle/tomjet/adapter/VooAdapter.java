package com.arthurvalle.tomjet.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.arthurvalle.tomjet.R;
import com.arthurvalle.tomjet.model.Voo;

import java.util.List;

public class VooAdapter extends BaseAdapter {

    private Context ctx;
    private LayoutInflater layout;
    private final List<Voo> voos;

    public VooAdapter(Context context, List<Voo> voos){
        ctx = context;
        layout = LayoutInflater.from(context);
        this.voos = voos;
    }

    @Override
    public int getCount() {
        return voos.size();
    }

    @Override
    public Object getItem(int i) {
        return voos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return voos.get(i).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TextView textData;
        TextView textItinerario;
        TextView textInfo;

        View view = layout.inflate(R.layout.linha_item, parent, false);

        textData = view.findViewById(R.id.textData);
        textInfo = view.findViewById(R.id.textInfo);
        textItinerario = view.findViewById(R.id.textItinerario);


        Voo v = (Voo) getItem(position);

        textData.setText("Data: "+v.getDataVoo());

        textItinerario.setText("Origem: "+ v.getOrigem().getCidade() + "  || Destino: "+v.getDestino().getCidade());

        textInfo.setText("Aviao: "+ v.getAviao().getPrefixo()+"  || Valor: R$"+v.getValorPassagem());

        return view;
    }

    @Nullable
    @Override
    public CharSequence[] getAutofillOptions() {
        return new CharSequence[0];
    }
}
