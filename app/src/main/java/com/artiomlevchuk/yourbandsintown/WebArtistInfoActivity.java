package com.artiomlevchuk.yourbandsintown;

import android.graphics.Bitmap;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.artiomlevchuk.yourbandsintown.api.ApiConstants;
import com.artiomlevchuk.yourbandsintown.api.BITClient;
import com.artiomlevchuk.yourbandsintown.api.ServiceGenerator;
import com.artiomlevchuk.yourbandsintown.entities.Event;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WebArtistInfoActivity extends ArtistInfoActivity {

    protected void loadEvents() {
        BITClient client = ServiceGenerator.createService(BITClient.class);
        client.getArtistEvents(mArtist.getName(), ApiConstants.APP_ID).enqueue(new Callback<List<Event>>() {

            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                if (response.isSuccessful()) {
                    List<Event> events = response.body();
                    mEventAdapter.addAll(events);
                    dao.putEvents(mArtist.getName(), events);
                } else {
                    Toast.makeText(WebArtistInfoActivity.this,
                            "Bad response", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                Toast.makeText(WebArtistInfoActivity.this,
                        "No connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void loadAvatar() { // need to close dao
        final TopCropImageView avatarImageView = (TopCropImageView) findViewById(R.id.image_avatar);
        Glide
                .with(this)
                .load(getIntent().getStringExtra("EXTRA_IMAGE_URL"))
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap bitmap, GlideAnimation glideAnimation) {
                        avatarImageView.setImageBitmap(bitmap);
                        dao.putAvatar(mArtist.getName(), bitmap);
                        //dao.close();
                    }
                });
    }

}
