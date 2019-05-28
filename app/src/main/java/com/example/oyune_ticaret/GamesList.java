package com.example.oyune_ticaret;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class GamesList extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games_list);

        JsonApi myApi = RetrofitClientInstance.getRetrofitInstance().create(JsonApi.class);
        Call<List<Game>> call = myApi.getGames();
        call.enqueue(new Callback<List<Game>>() {
            @Override
            public void onResponse(Call<List<Game>> call, Response<List<Game>> response) {
                populateListView(response.body());
            }

            @Override
            public void onFailure(Call<List<Game>> call, Throwable t) {


            }
        });

    }

    class Game {

        private String imageUrl;
        private String name;
        private String developer;
        private String price;
        private String discountPrice;
        private List<Platform> Platforms = null;


        public Game(String image, String name,String developer, String price, String discount ,List<Platform> platforms) {

            this.imageUrl = image;
            this.name = name;
            this.developer=developer;
            this.price = price;
            this.discountPrice = discount;
            this.Platforms=platforms;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDeveloper() {
            return developer;
        }

        public void setDeveloper(String developer) {
            this.developer = developer;
        }

        public List<Platform> getPlatforms() {
            return Platforms;
        }

        public void setPlatforms(List<Platform> platforms) {
            Platforms = platforms;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getDiscountPrice() {
            return discountPrice;
        }

        public void setDiscountPrice(String discountPrice) {
            this.discountPrice = discountPrice;
        }
    }

    public class Platform implements Serializable {

        private String name;
        private int NumberOfSales;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getNumberOfSales() {
            return NumberOfSales;
        }

        public void setNumberOfSales(int numberOfSales) {
            this.NumberOfSales = numberOfSales;
        }

    }

    interface JsonApi {
        @GET("/games")
        Call<List<Game>> getGames();

    }

    static class RetrofitClientInstance {
        private static Retrofit retrofit;
        private static final String BASE_URL = "https://private-bd6398-appcent.apiary-mock.com/";

        public static Retrofit getRetrofitInstance() {
            if (retrofit == null) {
                retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

            }
            return retrofit;
        }

    }

    class ListAdapter extends ArrayAdapter<Game> {

        private List<Game> games;
        private Context context;

        public ListAdapter(Context context, List<Game> games) {

            super(context, 0, games);

            this.games = games;
            this.context = context;

        }


        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = LayoutInflater.from(context).inflate(R.layout.game_item, viewGroup, false);

            }
            final Game game = games.get(position);
            ImageView imageView = view.findViewById(R.id.ivGameLogo);
            Picasso.get().load(game.getImageUrl()).into(imageView);
            TextView textName = (TextView) view.findViewById(R.id.tvGameName);
            TextView textPrice = (TextView) view.findViewById(R.id.tvPrice);
            TextView textDiscount = (TextView) view.findViewById(R.id.tvDiscount);

            textName.setText(game.getName());
            textPrice.setText(game.getPrice());
            if (game.getDiscountPrice() != null){
                textDiscount.setText(game.getDiscountPrice());
            }
            else {textDiscount.setText("No Discount");}




            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(GamesList.this,GameDetails.class);
                    intent.putExtra("Name",game.getName());
                    intent.putExtra("Price",game.getPrice());
                    intent.putExtra("Discount",game.getDiscountPrice());
                    intent.putExtra("Developer",game.getDeveloper());
                    intent.putExtra("ImageURL",game.getImageUrl());

                    intent.putExtra("PlatformsNumber",game.getPlatforms().size());

                    for (int i=0;i<game.getPlatforms().size();i++){
                        intent.putExtra("Platform"+i,game.getPlatforms().get(i).getName() + "\n");
                        intent.putExtra("Platform"+i+"sales"," " + game.getPlatforms().get(i).getNumberOfSales() +"\n");

                    }



                    startActivity(intent);

                }
            });
            return view;
        }
    }

    private ListAdapter adapter;
    private ListView glistView;

    private void populateListView(List<Game> games) {
        glistView = findViewById(R.id.lvMain);
        adapter = new ListAdapter(this, games);
        glistView.setAdapter(adapter);

    }


}

