package com.example.lenovo.moviedb3;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class MovieDetailsActivity extends AppCompatActivity {
    TextView title, rating, overview, date;
    ImageView posterPath, backDrop, favourite;
    DBHelper dbHelper = new DBHelper(this);
    int flag = 0;
    String id;

    @SuppressLint("StaticFieldLeak")
    public static RecyclerView videoRecyclerView, commentRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        //  videoTextView=findViewById(R.id.videoquery);
        title = findViewById(R.id.tv_originalTitle);
        rating = findViewById(R.id.tv_rating);
        overview = findViewById(R.id.tv_overView);
        date = findViewById(R.id.tv_releaseDate);
        videoRecyclerView = findViewById(R.id.id_rv_trailer);
        commentRecyclerView = findViewById(R.id.id_rv_review);
        posterPath = findViewById(R.id.imageview_poster);
        backDrop = findViewById(R.id.imageview_backdrop);
        favourite = findViewById(R.id.id_fav);
        Intent intent = getIntent();
        final String[] result = intent.getExtras().getStringArray("movieDetails");
        assert result != null;
        title.setText(result[0]);
        setTitle(result[0]);
        String rat=result[3]+"/10";
        rating.setText(rat);
        date.setText(result[4]);
        overview.setText(result[5]);
        Picasso.with(MovieDetailsActivity.this).load("http://image.tmdb.org/t/p/w300/"+result[1]).into(posterPath);
        Picasso.with(MovieDetailsActivity.this).load(result[2]).into(backDrop);
        id = result[6];
        VideoJson videoJson = new VideoJson(result[6], this);
        videoJson.execute();
        dbHelper = new DBHelper(this);
        Cursor cursor=getContentResolver().query(MovieContentContrat.MovieEntry.CONTENT_URI,null,id,null,null);
        if((cursor != null ? cursor.getCount() : 0) ==0){
            Toast.makeText(this, R.string.fav_error, Toast.LENGTH_SHORT).show();
        }
        if(cursor.moveToFirst()){
            do{
                if(cursor.getString(0).equals(id)){
                    flag=1;
                }
            }while(cursor.moveToNext());
        }
        if (flag==1) {
            favourite.setImageResource(R.drawable.ic_favorite_black_24dp);
        } else {
            favourite.setImageResource(R.drawable.ic_favorite_border_black_24dp);
        }
        favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(MovieContentContrat.MovieEntry.COL_0, id);
                contentValues.put(MovieContentContrat.MovieEntry.COL_1, result[0]);
                contentValues.put(MovieContentContrat.MovieEntry.COL_2,"https://image.tmdb.org/t/p/w500"+result[1]);
                if (flag==1) {
                    favourite.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                    int deleteValue = getContentResolver().delete(MovieContentContrat.MovieEntry.CONTENT_URI, contentValues.getAsString("id"), null);
                    Toast.makeText(MovieDetailsActivity.this, R.string.toast_delete, Toast.LENGTH_SHORT).show();
                } else {
                    favourite.setImageResource(R.drawable.ic_favorite_black_24dp);
                    Uri uri = getContentResolver().insert(MovieContentContrat.MovieEntry.CONTENT_URI, contentValues);
                    Toast.makeText(MovieDetailsActivity.this, R.string.toast_fav, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
