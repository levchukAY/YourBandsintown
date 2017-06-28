package com.artiomlevchuk.yourbandsintown;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.artiomlevchuk.yourbandsintown.data.DAO;
import com.artiomlevchuk.yourbandsintown.entities.Artist;

public abstract class ArtistInfoActivity extends AppCompatActivity {

    protected EventAdapter mEventAdapter;
    protected DAO dao;

    protected Artist mArtist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dao = new DAO(this);
        dao.open();

        mArtist = dao.getArtist(getIntent().getStringExtra("EXTRA_NAME"));

        getSupportActionBar().setTitle(mArtist.getName());

        ((TextView) findViewById(R.id.text_trackers)).setText(getString(
                R.string.title_trackers, mArtist.getTrackerCount()));
        ((TextView) findViewById(R.id.text_tours)).setText(getString(
                R.string.title_upcoming_tour_dates, mArtist.getUpcomingEventCount()));

        View facebookImage = findViewById(R.id.image_facebook);
        final String facebookUrl = mArtist.getFacebookPageUrl();
        if (facebookUrl.isEmpty()) {
            facebookImage.setVisibility(View.GONE);
        } else {
            facebookImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(facebookUrl)));
                }
            });
        }

        findViewById(R.id.image_browser).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(mArtist.getUrl())));
            }
        });

        RecyclerView eventsRecyclerView = (RecyclerView) findViewById(R.id.list_events);
        eventsRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        mEventAdapter = new EventAdapter(this);
        eventsRecyclerView.setAdapter(mEventAdapter);
        eventsRecyclerView.addItemDecoration(new ItemDivider(this));

        if (mArtist.getUpcomingEventCount() == 0)
            eventsRecyclerView.setVisibility(View.GONE);

        loadEvents();
        loadAvatar();
    }

    protected abstract void loadEvents();

    protected abstract void loadAvatar();

    private class ItemDivider extends RecyclerView.ItemDecoration {

        private final Drawable divider;

        ItemDivider(Context context) {
            int[] attrs = {android.R.attr.listDivider};
            divider = context.obtainStyledAttributes(attrs).getDrawable(0);
        }

        @Override
        public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDrawOver(c, parent, state);

            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();

            for (int i = 0; i < parent.getChildCount() - 1; ++i) {
                View item = parent.getChildAt(i);

                int top = item.getBottom() + ((RecyclerView.LayoutParams)
                        item.getLayoutParams()).bottomMargin;
                int bottom = top + divider.getIntrinsicHeight();

                divider.setBounds(left, top, right, bottom);
                divider.draw(c);
            }
        }
    }

}
