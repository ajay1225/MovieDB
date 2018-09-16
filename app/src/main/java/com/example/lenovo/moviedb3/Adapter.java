package com.example.lenovo.moviedb3;

import android.content.Context;

import android.content.DialogInterface;
import android.content.Intent;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.Toast;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.Holder>  {
    private Context context;
    private ArrayList<MovieDetails> movieDetails=new ArrayList<>();
    Adapter(Context context, ArrayList<MovieDetails> movieDetails) {
      this.context=context;
        this.movieDetails=movieDetails;

    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        context=parent.getContext();
        int id=R.layout.movie_list;
        LayoutInflater inflater=LayoutInflater.from(context);
        final View view=inflater.inflate(id,parent, false);
        return new Holder(view);

    }
    @Override
    public void onBindViewHolder(final Holder holder, final int position) {
        Picasso.with(context).load("http://image.tmdb.org/t/p/w300/"+movieDetails.get(position).getPoster_path()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return movieDetails.size();
    }



    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        Holder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            imageView= itemView.findViewById(R.id.id_iv_poster);


        }


        @Override
        public void onClick(View v) {
            if (isOnline()) {
                int pos = getAdapterPosition();
                String[] resultMovie = new String[7];
                resultMovie[0] = (movieDetails.get(pos).getTitle());
                resultMovie[1] = (movieDetails.get(pos).getPoster_path());
                resultMovie[2] = (movieDetails.get(pos).getBackdrop_path());
                resultMovie[3] = String.valueOf((movieDetails.get(pos).getVote_average()));
                resultMovie[4] = (movieDetails.get(pos).getRelease_date());
                resultMovie[5] = (movieDetails.get(pos).getOverview());
                resultMovie[6] = String.valueOf((movieDetails.get(pos).getId()));
                Intent intent = new Intent(context, MovieDetailsActivity.class);
                intent.putExtra("movieDetails", resultMovie);
                context.startActivity(intent);
            }
            else{
                Toast.makeText(context, R.string.error_internet, Toast.LENGTH_SHORT).show();
            }
        }

    }
    private boolean isOnline() {
        ConnectivityManager conMgr = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
        return !(netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable());
    }


}
