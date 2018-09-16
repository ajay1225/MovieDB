package com.example.lenovo.moviedb3;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.MyHolder> {
    private Context context;
    private ArrayList<VideoJson.Trailers> video;
    VideoAdapter(Context context, ArrayList<VideoJson.Trailers> video)
    {
        this.context=context;
        this.video=video;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context=parent.getContext();
        int id=R.layout.trailer;
        LayoutInflater inflater=LayoutInflater.from(context);
        final View view=inflater.inflate(id,parent, false);
        return new MyHolder(view);

    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        holder.videoTextView.setText(video.get(position).getName()+","+video.get(position).getType());
    }

    @Override
    public int getItemCount() {
        return video.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder implements AdapterView.OnClickListener
    {
        TextView videoTextView;
        MyHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            videoTextView=itemView.findViewById(R.id.id_textView_trailer);
        }


        @Override
        public void onClick(View v) {
             Uri uri=Uri.parse("http://www.youtube.com/watch?v="+video.get(getAdapterPosition()).getKey());
         Toast.makeText(context, ""+videoTextView.getText()+"\n"+uri, Toast.LENGTH_LONG).show();
            Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(String.valueOf(uri)));
            context.startActivity(intent);
        }
    }
}
