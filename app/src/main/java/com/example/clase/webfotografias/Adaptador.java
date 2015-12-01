package com.example.clase.webfotografias;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by 2dam on 05/10/2015.
 */
public class Adaptador extends ArrayAdapter<String>{

    private int res;
    private LayoutInflater lInflator;
    private ArrayList<String> valores;
    private Context con;

    static class ViewHolder{
        public TextView tv1;
        public ImageView iv2;
    }


    public Adaptador(Context context, int resource, ArrayList<String> objects) {
        super(context, resource, objects);
        this.res= resource; // LAYOUT
        this.valores= objects; // LISTA DE VALORES
        this.con= context;
        lInflator=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder gv= new ViewHolder();
        if(convertView==null){
            convertView= lInflator.inflate(res, null);


            TextView tv1= (TextView) convertView.findViewById(R.id.tvItem);
            gv.tv1=tv1;
            ImageView iv2= (ImageView)convertView.findViewById(R.id.imageView2);
            gv.iv2=iv2;
            convertView.setTag(gv);

        }else{
            gv= (ViewHolder) convertView.getTag();
        }
        gv.tv1.setText(valores.get(position).toString());
        gv.iv2.setImageResource(R.drawable.ic_action);
        return convertView;
    }

}
