package mx.edu.uth.plantscare;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import mx.edu.uth.plantscare.Modelo.Factory;


/**
 * A simple {@link Fragment} subclass.
 */
public class Principal extends Fragment {

    Factory fac = new Factory();
    ImageView imgSolares;

    public Principal() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_principal, container, false);
        imgSolares = (ImageView) v.findViewById(R.id.Solares);
        imgSolares.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //new Factory.ConsultarDatos().execute("http://192.168.1.104/PlantsCare/consulta.php?idPlanta=2");

                IniciarSesion fragment2 = new IniciarSesion();
                android.support.v4.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment2);
                fragmentTransaction.commit();

            }
        });

        return v;

        /* View v = inflater.inflate(R.layout.fragment_principal, container, false);
        imgSolares = (ImageView) v.findViewById(R.id.Solares);
        imgSolares.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //new Factory.ConsultarDatos().execute("http://192.168.1.104/PlantsCare/consulta.php?idPlanta=2");

                Galeria fragment2 = new Galeria();
                android.support.v4.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment2);
                fragmentTransaction.commit();

            }
        });
        return v; */
    }

}
