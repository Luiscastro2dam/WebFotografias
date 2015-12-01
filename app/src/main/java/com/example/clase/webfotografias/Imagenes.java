package com.example.clase.webfotografias;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Clase on 30/11/2015.
 */
public class Imagenes extends AppCompatActivity {
    private ImageView iv;
    private  Bitmap bitmap;
    private TextView tvMostrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mostrar);

        this.iv = (ImageView)findViewById(R.id.imageView);
        this.tvMostrar = (TextView)findViewById(R.id.tvMostrar);

        bitmap = getIntent().getParcelableExtra("bitmap");
         System.out.println("luiss" + bitmap.toString());
         iv.setImageBitmap(bitmap);

       String nombre= getIntent().getExtras().getString("nombre");
      /*  // Texto que vamos a buscar
        String sTextoBuscado = ".jpg";
        // Contador de ocurrencias
        int contador = 0;

        while (nombre.indexOf(sTextoBuscado) > -1) {
            nombre = nombre.substring(nombre.indexOf(
                    sTextoBuscado)+sTextoBuscado.length(),nombre.length());
            contador++;
        }
        System.out.println("kiraa"+contador);
//        String sSubCadena = nombre.substring(40,-4);
     //   System.out.println("kiraa"+sSubCadena);*/
        System.out.println("nombre"+nombre);
        tvMostrar.setText(nombre);
    }
}
