package com.gtech.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.gtech.MainActivity;
import com.gtech.R;
import com.gtech.helperclass.Validation;
import com.gtech.retrofit.Response.ProductRes.Vijayan;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductAdapter extends RecyclerView.Adapter {

    ArrayList<Vijayan> arrayList;
    Activity activity;
    int count=0;

    public ProductAdapter(Activity activity, ArrayList<Vijayan> arrayList) {
        this.activity = activity;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.product_adap, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyViewHolder viewHolder = (MyViewHolder) holder;
        Vijayan vijayan = arrayList.get(position);

        if (!vijayan.getImgUrl().isEmpty()) {
            Glide.with(activity).load(vijayan.getImgUrl()).into(viewHolder.image_product);
        }

        if (vijayan.getType().equals("0")){
            viewHolder.veg_icon.setBackgroundColor(Color.parseColor(Validation.getString(activity,R.color.text_red)));
        }else {
            viewHolder.veg_icon.setBackgroundColor(Color.parseColor(Validation.getString(activity,R.color.green)));
        }

        viewHolder.or_price_txt.setPaintFlags(viewHolder.or_price_txt.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        viewHolder.text_title.setText(vijayan.getProductName());
        viewHolder.count_txt.setText(String.valueOf(count));

        // Variant false show the demo amount and price
        if (vijayan.getVariation().equals("false")) {
            viewHolder.discount_txt.setText("र "+vijayan.getPrice());
            viewHolder.or_price_txt.setText("र "+vijayan.getDemoamt());
        } else {
            //Product list empty i cannot change it
            viewHolder.discount_txt.setText(vijayan.getPrice());
            viewHolder.or_price_txt.setText(vijayan.getDemoamt());

        }


        viewHolder.like_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Validation.toast(activity, "Clicked in like");
            }
        });
        viewHolder.minus_icon_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count>0){
                    count = count-1;
                    viewHolder.count_txt.setText(String.valueOf(count));
                }else {
                    Validation.toast(activity,"minimum one item need");
                }

            }
        });
        viewHolder.add_icon_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count = count+1;
                viewHolder.count_txt.setText(String.valueOf(count));
            }
        });


        ArrayList<String > list_spin = new ArrayList<>();
        list_spin.add("Variant");
        list_spin.add("Large");
        list_spin.add("Medium");
        list_spin.add("Small");
        ArrayAdapter arrayAdapter = new ArrayAdapter(activity,R.layout.spin_textview,R.id.textview,list_spin);
        viewHolder.spinner_variant.setAdapter(arrayAdapter);

        viewHolder.spinner_variant.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String check  = adapterView.getItemAtPosition(i).toString();
                if (!check.equals("Variant")){

                    ((MainActivity) activity).click_spin("You are selected in "+check);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image_product)
        ImageView image_product;
        @BindView(R.id.like_icon)
        ImageButton like_icon;
        @BindView(R.id.minus_icon_btn)
        ImageButton minus_icon_btn;
        @BindView(R.id.add_icon_btn)
        ImageButton add_icon_btn;

        @BindView(R.id.veg_icon)
        View veg_icon;

        @BindView(R.id.text_title)
        TextView text_title;
        @BindView(R.id.discount_txt)
        TextView discount_txt;
        @BindView(R.id.or_price_txt)
        TextView or_price_txt;
        @BindView(R.id.count_txt)
        TextView count_txt;

        @BindView(R.id.spinner_variant)
        Spinner spinner_variant;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
