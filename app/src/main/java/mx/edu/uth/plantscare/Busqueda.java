package mx.edu.uth.plantscare;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import mx.edu.uth.plantscare.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Busqueda extends Fragment {

    public Busqueda() {
    }

    private class CargarDatos extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            // params comes from the execute() call: params[0] is the url.
            try {
                return downloadUrl(urls[0]);
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }

        protected void onPostExecute(String result) {
            //Toast.makeText(getApplicationContext(), "Se actualizaron los datos correctamente", Toast.LENGTH_LONG).show();
        }
    }

    class ConsultarDatos extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            // params comes from the execute() call: params[0] is the url.
            try {
                return downloadUrl(urls[0]);
            } catch (IOException e) {
                return "No hay conexión a internet";
            }
        }

        protected void onPostExecute(String result) {
            JSONArray ja = null;
            try {
                TextView lblResultado = (TextView) getView().findViewById(R.id.lblResultado);
                JSONObject object = new JSONObject(result);
                ja =object.getJSONArray("plantas");
                LinearLayout linearLayout = new LinearLayout(getContext());
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                for (int i = 0; i < ja.length(); i++)
                {
                    JSONObject jo = ja.getJSONObject(i);
                    lblResultado.setText(jo.getString("Nombre"));
                    //TextView tv = new TextView(getContext());
                    //tv.setText(jo.getString("Nombre"));
                    //linearLayout.addView(tv);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private String downloadUrl(String myurl) throws IOException {
        InputStream is = null;
        // Only display the first 500 characters of the retrieved
        // web page content.
        int len = 500;

        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            Log.d("respuesta", "La respuesta es: " + response);
            is = conn.getInputStream();

            // Convert the InputStream into a string
            String contentAsString = readIt(is, len);
            return contentAsString;

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_busqueda, container, false);
        new ConsultarDatos().execute("http://192.168.1.104/PlantsCare/consulta.php?idPlanta=2");
        return v;
    }

}
