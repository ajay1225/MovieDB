package com.example.lenovo.moviedb3;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;



public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASENAME="database";
    private static String CREATE_QUERY="";

    private Context context;
    DBHelper(Context context) {
        super(context, DATABASENAME,null,2 );
        CREATE_QUERY="create table "+ MovieContentContrat.MovieEntry.Tablename+ " ("+MovieContentContrat.MovieEntry.COL_0+" text," +MovieContentContrat.MovieEntry.COL_1+" text,"+ MovieContentContrat.MovieEntry.COL_2+" text);";
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_QUERY);
        Toast.makeText(context, "table created", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+MovieContentContrat.MovieEntry.Tablename);
        onCreate(db);
    }
}
