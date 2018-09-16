package com.example.lenovo.moviedb3;

import android.net.Uri;
import android.provider.BaseColumns;

class MovieContentContrat {
    static final String AUTHORITY = "com.example.lenovo.moviedb3";
    static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
     static final String PATH_TASKS = "favourite1";
    static final class MovieEntry implements BaseColumns {

        static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TASKS).build();
        static final String Tablename = "favourite1";
        static final String COL_0 = "id";
        static final String COL_1 = "title";
        static final String COL_2 = "movie_poster";
    }

}
