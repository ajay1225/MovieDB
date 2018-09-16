package com.example.lenovo.moviedb3;

import android.annotation.SuppressLint;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    String selectedOptionItem="popular";
    @SuppressLint("StaticFieldLeak")
    public static RecyclerView recyclerView;
    public static int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null) {
            selectedOptionItem = savedInstanceState.getString("state");
            if(selectedOptionItem!="favourites") {
                if(isOnline()) {
                    Log.i("is in Online ","online");
                    JsonParse jsonParse = new JsonParse(selectedOptionItem, this);
                    jsonParse.execute();
                }
                else
                    try {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                        alertDialog.setTitle("Info");
                        alertDialog.setMessage("Check the Mobile network connectivity");
                        alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
                        alertDialog.setPositiveButton("retry", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = getIntent();
                                finish();
                                startActivity(intent);
                            }
                        });
                        alertDialog.show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }else
            {
                selectedOptionItem="favourites";
                favSetting();
            }
        }
        if (selectedOptionItem == "favourites") {
            selectedOptionItem="favourites";
            favSetting();
        } else {
            if (isOnline()) {
                recyclerView = findViewById(R.id.id_recyclerView);
                setTitle(selectedOptionItem);
                JsonParse jsonParse = new JsonParse(selectedOptionItem, this);
                jsonParse.execute();
            } else
                try {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                    alertDialog.setTitle("Info");
                    alertDialog.setMessage("Check the Mobile network connectivity");
                    alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
                    alertDialog.setPositiveButton("retry", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = getIntent();
                            finish();
                            startActivity(intent);
                        }
                    });
                    alertDialog.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("state",selectedOptionItem);
        index=JsonParse.gridLayoutManager.findFirstVisibleItemPosition();
        outState.putInt("index",index);
        super.onSaveInstanceState(outState);
    }
    private boolean isOnline() {
        ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
        return !(netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable());
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.popular){
            selectedOptionItem="popular";
            JsonParse jsonParse=new JsonParse(selectedOptionItem,this);
            jsonParse.execute();
            setTitle("Popular");
        }
        if(item.getItemId()==R.id.top_rated) {
            selectedOptionItem = "top_rated";
            JsonParse jsonParse=new JsonParse(selectedOptionItem,this);
            jsonParse.execute();
            setTitle("Top Rated");
        }
        if(item.getItemId()==R.id.favourites){
            selectedOptionItem="favourites";
            favSetting();
        }
        return  false;
    }
    public void favSetting(){
       setTitle("Favourite(s)");
        Cursor cursor=getContentResolver().query(MovieContentContrat.MovieEntry.CONTENT_URI,null,null,null,null);
        ArrayList<MovieDetails> favList=new ArrayList<>();
        if((cursor != null ? cursor.getCount() : 0) ==0){
            Toast.makeText(this, R.string.fav_error, Toast.LENGTH_SHORT).show();
        }
        assert cursor != null;
        cursor.moveToFirst();
        for(int i=0;i<cursor.getCount();i++,cursor.moveToNext()){
            MovieDetails value=new MovieDetails();
            value.setTitle(cursor.getString(1));
            value.setPoster_path(cursor.getString(2));
            favList.add(value);
        }
        RecyclerView rv= findViewById(R.id.id_recyclerView);
        cursor.close();
            rv.setLayoutManager(new GridLayoutManager(this,2));
        rv.setAdapter(new FavAdapter(this,favList));
    }
}
