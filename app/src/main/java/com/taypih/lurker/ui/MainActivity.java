package com.taypih.lurker.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.taypih.lurker.R;
import com.taypih.lurker.ui.posts.PostsFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, PostsFragment.newInstance(), PostsFragment.class.getName())
                    .commitNow();
        }
    }
}
