package com.hurtarte.asysgroup1;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CountryFragment extends Fragment {

    private List<Country> countryList;

    private RecyclerView recyclerView;
    private CountryAdaper adapter;
    private  RecyclerView.LayoutManager mLayoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_country,container,false);








        countryList=new ArrayList<>();



        recyclerView=rootView.findViewById(R.id.recyclerView_country);
        recyclerView.setHasFixedSize(true);

        mLayoutManager= new LinearLayoutManager(getContext());
       // adapter=new CountryAdaper(getContext(),countryList);


        loadCountrys("http://192.168.0.11:80/asysgroup/buscar_paises.php");
        recyclerView.setLayoutManager(mLayoutManager);
       // adapter=new CountryAdaper(getContext(),countryList);
        //recyclerView.setAdapter(adapter);









        //recyclerView.setAdapter(adapter);

       /* adapter.setOnItemClickListener(new CountryAdaper.OnItemClickListener() {
            @Override
            public void onDeleteClick(String codigo, int position) {

                deleteCountry("http://192.168.0.11:80/asysgroup/eliminar_pais.php",codigo);
                countryList.remove(position);
                adapter.notifyItemRemoved(position);





            }
        });*/





        return rootView;
    }

    private void loadCountrys(String URL){

        StringRequest stringRequest=new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray paises=new JSONArray(response);
                    for(int i=0; i<paises.length();i++){
                        JSONObject paisObject= paises.getJSONObject(i);

                        String codigo= paisObject.getString("Pais");
                        String nomPais=paisObject.getString("NomPais");

                        Country country = new Country(codigo,nomPais);

                        countryList.add(country);


                    }

                    adapter=new CountryAdaper(getContext(),countryList);
                   recyclerView.setAdapter(adapter);



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        Volley.newRequestQueue(getContext()).add(stringRequest);
    }


    public void deleteCountry(String URL, final String countryCode){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros = new HashMap<String,String>();
                parametros.put("pais", countryCode);



                return parametros;
            }
        };

        Volley.newRequestQueue(getContext()).add(stringRequest);


    }
}
