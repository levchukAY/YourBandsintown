package com.artiomlevchuk.yourbandsintown;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

public class CacheArtistInfoActivity extends ArtistInfoActivity {

    protected void loadEvents() {
        mEventAdapter.addAll(dao.getEvents(mArtist.getName()));
    }

    protected void loadAvatar() {
        ImageView avatarImageView = (ImageView) findViewById(R.id.image_avatar);
        byte[] byteImage = dao.getAvatar(mArtist.getName());
        Bitmap avatar = BitmapFactory.decodeByteArray(byteImage, 0, byteImage.length);
        avatarImageView.setImageBitmap(avatar);
        dao.close();
    }

}
