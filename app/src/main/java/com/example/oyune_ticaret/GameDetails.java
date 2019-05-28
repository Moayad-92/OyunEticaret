package com.example.oyune_ticaret;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class GameDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_details);

        TextView tvName=findViewById(R.id.tvName1);
        TextView tvPrice=findViewById(R.id.tvPrice1);
        TextView tvDiscount=findViewById(R.id.tvDiscount1);
        TextView tvDeveloper=findViewById(R.id.tvDeveloper);
        TextView tvPlatformName=findViewById(R.id.tvPlatformName);
        TextView tvPlatformSales=findViewById(R.id.tvPlatformSales);
        ImageView ivLogo=findViewById(R.id.ivLogo);

        Intent intent=getIntent();
        tvName.setText(intent.getStringExtra("Name"));
        tvPrice.setText(intent.getStringExtra("Price"));

        if (intent.getStringExtra("Discount")!= null){
            tvDiscount.setText(intent.getStringExtra("Discount"));
        }
        else {tvDiscount.setText("No Discount");}


        tvDeveloper.setText(intent.getStringExtra("Developer"));

        String imageUrl=intent.getStringExtra("ImageURL");
        Picasso.get().load(imageUrl).into(ivLogo);

        int PlatformsNumber= intent.getIntExtra("PlatformsNumber",0);

        String PlatformsName="";

        String PlatformsSales="";

        for(int i=0;i<PlatformsNumber;i++){

            PlatformsName+=intent.getStringExtra("Platform"+i);

            PlatformsSales+=intent.getStringExtra("Platform"+i+"sales");




        }

        tvPlatformName.setText(PlatformsName);
        tvPlatformSales.setText(PlatformsSales);



    }
}