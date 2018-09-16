package com.example.lenovo.moviedb3;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.widget.GridLayoutManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static com.example.lenovo.moviedb3.MainActivity.index;
import static com.example.lenovo.moviedb3.MainActivity.recyclerView;


public class JsonParse extends AsyncTask<Void,Void,Void> {
    private String selectedOption="";
    public static GridLayoutManager gridLayoutManager;
    private String data="";
    @SuppressLint("StaticFieldLeak")
    private Context context;
    private ArrayList<MovieDetails> arrayList;
    private ProgressDialog progressDialog;
    private static final String MY_KEY=BuildConfig.API_KEY;

    JsonParse(String selectedOption, Context context) {
        this.selectedOption = selectedOption;
        this.context=context;
    }

     private String makeSearchQuery(String val) {

        final String SEARCH_URL="https://api.themoviedb.org/3/movie";
        final String API_KEY="api_key";
        final String value=val;
        Uri uri=Uri.parse(SEARCH_URL)
                .buildUpon()
                .appendPath(value)
                .appendQueryParameter(API_KEY,MY_KEY).build();
         return String.valueOf(uri);
    }

    @Override
    public Void doInBackground(Void... voids) {

        String makeQuery = makeSearchQuery(selectedOption);
        try {
            URL url = new URL(makeQuery);
            HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
            InputStream inputStream=httpURLConnection.getInputStream();
            BufferedReader bufferedReader= new BufferedReader(new InputStreamReader(inputStream));
            String line="";
            while (line!=null){
                line=bufferedReader.readLine();
                data=data+line;
            }

            JSONObject jsonObject=new JSONObject(data);
            JSONArray jsonArray=jsonObject.getJSONArray("results");

            arrayList=new ArrayList<>();
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject1=(JSONObject) jsonArray.get(i);
                MovieDetails movieDetails = new MovieDetails();
                movieDetails.setPopularity( jsonObject1.getDouble("popularity"));
                movieDetails.setTitle( jsonObject1.getString("title"));
                movieDetails.setOriginal_title( jsonObject1.getString("original_title"));
                movieDetails.setOverview( jsonObject1.getString("overview"));
                movieDetails.setVote_average( jsonObject1.getDouble("vote_average"));
                movieDetails.setOverview((String) jsonObject1.get("overview"));
                movieDetails.setRelease_date((String) jsonObject1.get("release_date"));
                movieDetails.setPoster_path(jsonObject1.getString("poster_path"));
                movieDetails.setBackdrop_path(jsonObject1.getString("backdrop_path"));
                movieDetails.setId(jsonObject1.getDouble("id"));
                arrayList.add(movieDetails);

            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        gridLayoutManager=new GridLayoutManager(context, 2);
        if(getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            recyclerView.setLayoutManager(gridLayoutManager);

        }
        else{
            recyclerView.setLayoutManager(gridLayoutManager);
        }

        recyclerView.setAdapter(new Adapter(context,arrayList));
        recyclerView.scrollToPosition(MainActivity.index);
        index=0;
        progressDialog.dismiss();

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog=new ProgressDialog(context);
        progressDialog.setMessage("Loading Please Wait....");
        progressDialog.show();
    }
    private Activity getActivity() {
        Context context2 = context;
        while (context2 instanceof ContextWrapper) {
            if (context2 instanceof Activity) {
                return (Activity) context2;
            }
            context2 = ((ContextWrapper) context2).getBaseContext();
        }
        return null;
    }
}
