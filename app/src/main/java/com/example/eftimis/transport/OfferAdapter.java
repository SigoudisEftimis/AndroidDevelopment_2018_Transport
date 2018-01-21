package com.example.eftimis.transport;


import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class OfferAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] itemname;
    private final Integer[] imgid;



    public OfferAdapter(Activity context, String[] itemname, Integer[] imgid) {
        super(context, R.layout.activity_results, itemname);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.itemname=itemname;
        this.imgid=imgid;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.offer_item, null,true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.companyName);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.companyImage);
        TextView extratxt = (TextView) rowView.findViewById(R.id.price);
        TextView logoTxt = rowView.findViewById(R.id.logoTxt);
        TextView phone = rowView.findViewById(R.id.phone);


        txtTitle.setText(itemname[position]);
        imageView.setImageResource(imgid[position]);
       extratxt.setText(String.valueOf(CalPrice(position)));
       logoTxt.setText(Results.logo[position]);
       phone.setText(Results.phones[position]);
        return rowView;
    }

    private int CalPrice(int position) {
        int totalPrice;
        if (Results.getKilo() <= 10 ) {
            totalPrice =Results.getMinCost()[position];

        }else{
            totalPrice = Results.getMinCost()[position] + (Results.getKilo() - 10)*Results.getKiloCost()[position];

        }

        if(Results.breakAble) totalPrice = totalPrice + Results.getBreakable()[position];
        if(Results.fast)  totalPrice = totalPrice + Results.getFastDelivery()[position];
        return  totalPrice;
    }
}


