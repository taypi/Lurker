package com.taypih.lurker.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.taypih.lurker.R;
import com.taypih.lurker.ui.list.ListFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, ListFragment.newInstance(), ListFragment.class.getName())
                    .commitNow();
        }
    }
}
