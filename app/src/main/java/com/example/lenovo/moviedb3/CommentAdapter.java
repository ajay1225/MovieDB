package com.example.lenovo.moviedb3;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentHolder> {
    private Context context;
    private ArrayList<VideoJson.Review> reviews;
    CommentAdapter(Context context, ArrayList<VideoJson.Review> reviews) {
        this.context=context;
        this.reviews=reviews;
    }

    @Override
    public CommentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context=parent.getContext();
        int id=R.layout.review;
        LayoutInflater inflater=LayoutInflater.from(context);
        final View view=inflater.inflate(id,parent, false);
        return new CommentHolder(view);
    }

    @Override
    public void onBindViewHolder(CommentHolder holder, int position) {
        holder.author.setText(reviews.get(position).getAuthor());
        holder.comment.setText(reviews.get(position).getComment());
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public class CommentHolder extends RecyclerView.ViewHolder {
        TextView author,comment;
        CommentHolder(View itemView) {
            super(itemView);
            author=itemView.findViewById(R.id.id_review_author);
            comment=itemView.findViewById(R.id.id_review_comment);
        }

    }
}
