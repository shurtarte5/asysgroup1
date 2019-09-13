package com.hurtarte.asysgroup1;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



/*Este es la asignacion al main activty  de la barra inferior que nos ayuda a navegar
  entre los 3 fragmenst que utiliza la aplicacion */

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new AddFragment()).commit();
    }


    /*Con este segmento de codigo obtenos el id asignado a cada elemento de navegacion de l bottonaigation en esta aplicacion
    * solo utilizamos el main activity como contenedor para los fragments*/

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment=null;

                    switch (item.getItemId()){

                        case R.id.nav_add:
                            selectedFragment=new AddFragment();
                            break;

                        case R.id.nav_country_list:
                            selectedFragment=new CountryFragment();
                            break;


                        case R.id.nav_dep_list:
                            selectedFragment=new DepFragment();
                            break;



                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();

                    return true;
                }
            };
}
