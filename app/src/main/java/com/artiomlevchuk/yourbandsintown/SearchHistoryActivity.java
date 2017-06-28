package com.artiomlevchuk.yourbandsintown;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class SearchHistoryActivity extends AppCompatActivity {

    RecyclerView mSearchHistoryRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_history);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mSearchHistoryRecyclerView = (RecyclerView) findViewById(R.id.list_artists);
        mSearchHistoryRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        mSearchHistoryRecyclerView.setAdapter(new ArtistAdapter(this));
    }

    public void hideTextView() {
        findViewById(R.id.text_nothing_to_show).setVisibility(View.GONE);
    }

}
