package com.taypih.lurker.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.taypih.lurker.R;
import com.taypih.lurker.ui.posts.PostsFragment;
import com.taypih.lurker.utils.PreferenceUtils;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handleIntentExtra();
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, PostsFragment.newInstance(), PostsFragment.class.getName())
                    .commitNow();
        }
    }

    private void handleIntentExtra() {
        Intent intent = getIntent();
        if (intent.hasExtra(Intent.EXTRA_TEXT)) {
            boolean loadFromApi = intent.getBooleanExtra(Intent.EXTRA_TEXT, true);
            PreferenceUtils.saveListTypePref(getApplicationContext(), loadFromApi);
        }
    }
}
