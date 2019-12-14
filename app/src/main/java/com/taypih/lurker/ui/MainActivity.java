package com.taypih.lurker.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.taypih.lurker.R;
import com.taypih.lurker.ui.posts.PostsFragment;

public class MainActivity extends AppCompatActivity {
    private static final String KEY_PREF = "pref_data_source";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (intent.hasExtra(Intent.EXTRA_TEXT)) {
            boolean loadFromApi = intent.getBooleanExtra(Intent.EXTRA_TEXT, true);
            SharedPreferences sharedPref = getSharedPreferences(KEY_PREF, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putBoolean(KEY_PREF, loadFromApi);
            editor.apply();
        }
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, PostsFragment.newInstance(), PostsFragment.class.getName())
                    .commitNow();
        }
    }
}
