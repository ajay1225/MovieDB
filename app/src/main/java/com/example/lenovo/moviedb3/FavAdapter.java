package com.example.lenovo.moviedb3;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class FavAdapter extends RecyclerView.Adapter<FavAdapter.Holder> {
    private Context context;
    private ArrayList<MovieDetails> movieDetails = new ArrayList<>();

    FavAdapter(Context context, ArrayList<MovieDetails> movieDetails) {
        this.context = context;
        this.movieDetails = movieDetails;
    }

    @Override
    public FavAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        int id = R.layout.fav_movie_list;
        LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(id, parent, false);
        return new FavAdapter.Holder(view);

    }

    @Override
    public void onBindViewHolder(final FavAdapter.Holder holder, final int position) {
        Picasso.with(context).load(movieDetails.get(position).getPoster_path()).into(holder.imageView);
        holder.title.setText(movieDetails.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return movieDetails.size();
    }


    class Holder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title;

        Holder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.id_iv_fav_poster);
            title= itemView.findViewById(R.id.id_tv_fav_title);
        }

    }
}
