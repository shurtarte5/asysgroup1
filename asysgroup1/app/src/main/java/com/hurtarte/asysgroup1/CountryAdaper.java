package com.hurtarte.asysgroup1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CountryAdaper extends  RecyclerView.Adapter<CountryAdaper.CountryViewHolder> {


    /*Este es el adaptador que despliega mi informacion en forma de cardview dentro de un recycler view */


    private Context context;

    private List<Country> countryList;

    private OnItemClickListener mListener;


    public interface OnItemClickListener {

        void onItemClick(int position);
        void onDeleteClick(String codigo, int position);


    }

    public void setOnItemClickListener(OnItemClickListener listener) {

        mListener = listener;


    }





    public CountryAdaper(Context context, List<Country> countryList) {
        this.context = context;
        this.countryList = countryList;
    }

    @NonNull
    @Override
    public CountryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.cardview_country,null);
        CountryViewHolder holder = new CountryViewHolder(view,mListener);



        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CountryViewHolder holder, int position) {


        Country country = countryList.get(position);

        holder.countryCode.setText(country.getCodeCountry());
        holder.countryName.setText(country.getNameCountry());









    }

    @Override
    public int getItemCount() {
        return countryList.size();
    }

    public static class CountryViewHolder extends RecyclerView.ViewHolder{

        TextView countryCode;
        TextView countryName;
        ImageView deleteButton;


        public CountryViewHolder(View itemView,final OnItemClickListener listener) {
            super(itemView);

            countryCode=itemView.findViewById(R.id.txtCode);
            countryName=itemView.findViewById(R.id.txtName);
           // deleteButton=itemView.findViewById(R.id.btnDelete);



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(listener!=null){

                        int position = getAdapterPosition();
                        if (position!=RecyclerView.NO_POSITION) {

                            listener.onItemClick(position);
                        }

                    }



                }
            });


           /* deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(listener!=null){

                        String code=countryCode.getText().toString();
                        int position=getAdapterPosition();

                        listener.onDeleteClick(code,position);

                    }


                }
            });*/
        }
    }





}
