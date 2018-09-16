package com.example.lenovo.moviedb3;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

import android.support.v7.widget.LinearLayoutManager;




import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class VideoJson extends AsyncTask <Void,Void,Void> {
    private String movieId;
    private URL url;
    private String videoJSON = "", reviewJSON = "";
    private ArrayList<Trailers> video;
    private ArrayList<Review> review;
    @SuppressLint("StaticFieldLeak")
    private Context context;
    private static final String MY_key= BuildConfig.API_KEY;


    public VideoJson(String id, Context context) {
        movieId = String.valueOf(id);
    }

    public String makeVideoSearchQuery() {

        final String SEARCH_URL = "https://api.themoviedb.org/3/movie";
        final String API_KEY = "api_key";
        Uri uri = Uri.parse(SEARCH_URL)
                .buildUpon()
                .appendPath(movieId)
                .appendPath("videos")
                .appendQueryParameter(API_KEY, MY_key).build();

        return String.valueOf(uri);
    }

    public String makeReviewSearchQuery() {

        final String SEARCH_URL = "https://api.themoviedb.org/3/movie";
        final String API_KEY = "api_key";
        Uri uri = Uri.parse(SEARCH_URL)
                .buildUpon()
                .appendPath(movieId)
                .appendPath("reviews")
                .appendQueryParameter(API_KEY, MY_key).build();
        return String.valueOf(uri);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        MovieDetailsActivity.videoRecyclerView.setLayoutManager(new LinearLayoutManager(context));////trailer Adapter
        MovieDetailsActivity.videoRecyclerView.setAdapter(new VideoAdapter(context, video));
        MovieDetailsActivity.commentRecyclerView.setLayoutManager(new LinearLayoutManager(context));//review/comment Adapter
        MovieDetailsActivity.commentRecyclerView.setAdapter(new CommentAdapter(context, review));

    }

    @Override
    protected Void doInBackground(Void... voids) {
        String makeQuery = makeVideoSearchQuery();
        try {
            url = new URL(makeQuery);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while (line != null) {
                line = bufferedReader.readLine();
                videoJSON = videoJSON + line;
            }
            JSONObject jsonObject = new JSONObject(videoJSON);
            JSONArray jsonArray = jsonObject.getJSONArray("results");

            video = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                Trailers result = new Trailers();
                JSONObject videoKeys = jsonArray.getJSONObject(i);
                result.setKey(videoKeys.getString("key"));
                result.setName(videoKeys.getString("name"));
                result.setType(videoKeys.getString("type"));
                video.add(result);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        makeQuery = makeReviewSearchQuery();
        try {
            url = new URL(makeQuery);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while (line != null) {
                line = bufferedReader.readLine();
                reviewJSON = reviewJSON + line;
            }
            JSONObject jsonObject = new JSONObject(reviewJSON);
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            review = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject revi = (JSONObject) jsonArray.get(i);
                Review result = new Review();
                result.setAuthor(revi.getString("author"));
                result.setComment(revi.getString("content"));
                review.add(result);


            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public class Trailers {
        String key, type, name;

        public Trailers() {
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public class Review {
        String author, comment;

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public Review() {

        }
    }

}



