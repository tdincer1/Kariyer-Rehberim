package com.squad.testdeneme.tercih_robotu;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squad.testdeneme.R;

import java.util.List;

public class TrAdapter extends RecyclerView.Adapter<TrAdapter.TrViewHolder>{

    private Context mCtx;
    private List<Bilgi> bilgiList;


    public TrAdapter(Context mCtx, List<Bilgi> bilgiList){
        this.mCtx = mCtx;
        this.bilgiList = bilgiList;
    }

    @NonNull
    @Override
    public TrViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.item, null);
        return new TrViewHolder(view);
    }

    public void onBindViewHolder(@NonNull TrViewHolder holder,int i){
        Bilgi bilgi = bilgiList.get(i);

        holder.textViewPrKodu.setText(String.valueOf(bilgi.getPr_kodu()));
        holder.tvSehir.setText(bilgi.getSehir());
        holder.tvUni.setText(bilgi.getUniversite());
        holder.tvFak.setText(bilgi.getFakulte());
        holder.tvBolum.setText(bilgi.getBolum());
        holder.tvDil.setText(bilgi.getDil());
        holder.tvBolumTuru.setText(bilgi.getBolum_turu());
        holder.tvPuanturu.setText(bilgi.getPuan_turu());
        holder.tvTabanPuani.setText(bilgi.getTaban_puani());
        holder.tvSiralama.setText(String.valueOf(bilgi.getSiralama()));
        holder.tvKontenjan.setText(String.valueOf(bilgi.getKontenjan()));

    }

    @Override
    public int getItemCount(){
        return bilgiList.size();
    }

    class TrViewHolder extends RecyclerView.ViewHolder{
        TextView textViewPrKodu, tvSehir, tvUni, tvFak, tvBolum;
        TextView tvDil, tvBolumTuru, tvPuanturu, tvTabanPuani;
        TextView tvSiralama, tvKontenjan;

        public TrViewHolder(@NonNull View itemView){
            super(itemView);

            textViewPrKodu = itemView.findViewById(R.id.progKoduId);
            tvSehir = itemView.findViewById(R.id.sehirId);
            tvUni = itemView.findViewById(R.id.uniId);
            tvFak = itemView.findViewById(R.id.fakulteId);
            tvBolum = itemView.findViewById(R.id.bolumId);
            tvDil = itemView.findViewById(R.id.dilID);
            tvBolumTuru = itemView.findViewById(R.id.bolumTuruId);
            tvPuanturu = itemView.findViewById(R.id.puanTuruId);
            tvTabanPuani = itemView.findViewById(R.id.tabanId);
            tvSiralama = itemView.findViewById(R.id.siralamaId);
            tvKontenjan = itemView.findViewById(R.id.kontenjanId);
        }

    }
}
