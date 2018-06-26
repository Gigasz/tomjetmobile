package com.arthurvalle.tomjet.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.arthurvalle.tomjet.R;
import com.arthurvalle.tomjet.model.Assento;
import com.arthurvalle.tomjet.model.Voo;

import java.util.List;

public class PassagesAdapter extends BaseAdapter {

    private Context ctx;
    private LayoutInflater layout;
    private final List<Assento> assentos;

    public PassagesAdapter(Context context, List<Assento> assentos){
        ctx = context;
        layout = LayoutInflater.from(context);
        this.assentos = assentos;
    }

    @Override
    public int getCount() {
        return assentos.size();
    }

    @Override
    public Object getItem(int i) {
        return assentos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return assentos.get(i).getAssento();
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


        Assento a = (Assento) getItem(position);

        textData.setText("Data: "+a.getDataVoo());

        textItinerario.setText("Origem: "+ a.getOrigem() + "  || Destino: "+a.getDestino());

        textInfo.setText("Aviao: "+ a.getAviao()+"  || Assento: "+a.getAssento());

        return view;
    }

    @Nullable
    @Override
    public CharSequence[] getAutofillOptions() {
        return new CharSequence[0];
    }
}
