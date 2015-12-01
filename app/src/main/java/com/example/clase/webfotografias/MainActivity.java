package com.example.clase.webfotografias;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private Bitmap bmp;
    private ListView lv;
    private Adaptador ad;
    private EditText et;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.lv = (ListView) findViewById(R.id.listView);
        this.et = (EditText) findViewById(R.id.evWeb);
    }

    public void btBuscar(View v){
        init();
    }

    public void init() {
        String pagina=et.getText().toString();
        String ini="http://";
        Tarea t = new Tarea();
        //t.execute(ini+pagina);
        t.execute("http://www.istockphoto.com/photos/nature");
       // t.execute("http://haciendofotos.com/category/las-mejores-fotos");

    }

    public class Tarea extends AsyncTask<String, Integer, ArrayList<String>> {

        int myProgress;
        ArrayList<Bitmap> bmps = new ArrayList<>();
      //  ArrayList<InputStream> rutas = new ArrayList<>();
        @Override
        protected ArrayList<String> doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                String linea, out = "", imagen;
                ArrayList<String> imgs = new ArrayList<>();
                while ((linea = in.readLine()) != null) {
                    out += linea + " ";
                }
                in.close();
                int pos = 0, pos1, pos2;
                // <img src="http://i.istockimg.com/fotografia.jpg">
                //http://www.w3schools.com/jsref/jsref_indexof.asp
                //buscamos comienzo <img
                //indexof devuelve la posición de la primera aparición de un valor especificado en una cadena.
                pos = out.indexOf("<img", pos + 1);
                pos1 = out.indexOf("src=\"", pos + 1);
                pos2 = out.indexOf("\"", pos1 + 5);//5 para buscar src=
                imagen = out.substring(pos1 + 5, pos2);
                int cont=0;//contador para mostrar menos iamgenes sino no termina
                while ((pos = out.indexOf("<img", pos + 1)) != -1) {
                    cont = cont +1;
                    if(cont<6) {
                        imgs.add(imagen);
                        pos1 = out.indexOf("src=\"", pos + 1);
                        pos2 = out.indexOf("\"", pos1 + 5);
                        imagen = out.substring(pos1 + 5, pos2);//guardamos la ruta la imagen
                    }
                }
                imgs.remove(imgs.size() - 1);
                for (String str : imgs) {
                    try {
                        URL urlimg = new URL(str);
                        //rutas.add(urlimg.openConnection().getInputStream());
                        bmps.add(BitmapFactory.decodeStream(urlimg.openConnection().getInputStream()));
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                return imgs;
            } catch (MalformedURLException ex) {
            } catch (IOException ex) {
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog (MainActivity.this);
            progressDialog.setTitle("Procesando ...");
            progressDialog.setMessage("Por favor, espere.");
            progressDialog.setCancelable(false);
            // Este diálogo no es indeterminado
            progressDialog.setIndeterminate (false);
            progressDialog.setMax (100);
            progressDialog.setProgress (0);
            // Muestra el diálogo de progreso
            progressDialog.show ();

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressDialog.setProgress (values [0]);

        }

        @Override
        protected void onPostExecute(final ArrayList<String> lista) {
            progressDialog.dismiss ();//cierra el progressDialog Cuando entra aqui
            System.out.println(lista +"luis");
//            Log.v("luis",lista.toString());
            ad = new Adaptador(MainActivity.this,R.layout.item,(ArrayList<String>)lista);
            lv.setAdapter(ad);
            lv.setOnItemClickListener(
                    //si selecionamos algun item del listview
                    new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent i = new Intent(MainActivity.this, Imagenes.class);
                            System.out.println("luiss1"+bmps.get(position));
                            i.putExtra("bitmap", bmps.get(position));
                            i.putExtra("nombre",lista.get(position).toString());
                            System.out.println("kira"+lista.get(position).toString());
                            startActivity(i);
                        }
                    }
            );
        }
    }

}

