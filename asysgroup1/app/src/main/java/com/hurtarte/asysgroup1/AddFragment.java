package com.hurtarte.asysgroup1;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddFragment extends Fragment {

    private EditText edtcountryCode;
    private EditText edtcountryName;
    private Button btnAdd,btnSearch,btnDelete,btnEdit;

    RequestQueue requestQueue;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View rootview = inflater.inflate(R.layout.fragment_add,container,false);

        edtcountryCode=rootview.findViewById(R.id.edittext_code);
        edtcountryName=rootview.findViewById(R.id.editext_name);
        btnAdd=rootview.findViewById(R.id.button_add);
        btnSearch = rootview.findViewById(R.id.button_search);
        btnDelete=rootview.findViewById(R.id.button_delete);
        btnEdit=rootview.findViewById(R.id.button_edit);

        /*Dentro de este fragment estan todas las acciones hacea la base de datos utilice la libreria VOLLEY para consumir la base de datos en mysql
         * las acciones se hicieron mediante webservice atrevez de php */




        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCountry("http://192.168.0.11:80/asysgroup/insertar_pais.php");

            }
        });


        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCountry("http://192.168.0.11:80/asysgroup/buscar_pais.php?pais=" +edtcountryCode.getText()+"");
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCountry("http://192.168.0.11:80/asysgroup/editar_pais.php");

            }
        });


        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCountry("http://192.168.0.11:80/asysgroup/eliminar_pais.php");

            }
        });








        return rootview;
    }

    private void addCountry(String URL){

        StringRequest stringRequest= new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getContext(), "Operacion Exitosa", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> parametros = new HashMap<String,String>();






                parametros.put("pais",edtcountryCode.getText().toString());
                parametros.put("nompais",edtcountryName.getText().toString());







                return parametros;
            }
        };

       requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);





    }

    /*este metodo lo utilize para agregar pais y para modificar pues se utilizan los mirsmos parametros, lo unico que se modifico fue el archivo en php */

    private void getCountry(String URL){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        edtcountryCode.setText(jsonObject.getString("Pais"));
                        edtcountryName.setText(jsonObject.getString("NomPais"));
                    } catch (JSONException e) {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Error de conexion", Toast.LENGTH_SHORT).show();

            }
        }
        );

        requestQueue=Volley.newRequestQueue(getContext());
        requestQueue.add(jsonArrayRequest);

    }

    private void deleteCountry(String URL){

        StringRequest stringRequest= new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getContext(), "Pais Eliminado", Toast.LENGTH_SHORT).show();
                cleanForm();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> parametros = new HashMap<String,String>();






                parametros.put("pais",edtcountryCode.getText().toString());








                return parametros;
            }
        };

        requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);





    }

    private void cleanForm(){
        edtcountryCode.setText("");
        edtcountryName.setText("");

    }



}
