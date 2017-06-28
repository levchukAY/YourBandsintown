package com.artiomlevchuk.yourbandsintown;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.artiomlevchuk.yourbandsintown.data.DAO;
import com.artiomlevchuk.yourbandsintown.data.DatabaseDescription.Artists;

class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ViewHolder> {

    private int mSize;
    private Activity mActivity;
    private DAO dao;

    ArtistAdapter(Activity activity) {
        this.mActivity = activity;

        dao = new DAO(mActivity);
        dao.open();

        mSize = dao.getSize(Artists.TABLE_NAME);

        if (mSize != 0)
            ((SearchHistoryActivity) mActivity).hideTextView();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(
                parent.getContext()).inflate(R.layout.item_artist, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent artistInfoIntent = new Intent(mActivity, CacheArtistInfoActivity.class);
                artistInfoIntent.putExtra("EXTRA_NAME",
                        dao.getArtistNameById((mSize - holder.getAdapterPosition()) + ""));
                mActivity.startActivity(artistInfoIntent);
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String name = dao.getArtistNameById((mSize - position) + "");
        byte[] image = dao.getAvatar(name);
        holder.name.setText(name);
        holder.avatar.setImageBitmap(BitmapFactory.decodeByteArray(image, 0, image.length));
    }

    @Override
    public int getItemCount() {
        return mSize;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView avatar;

        ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.text_name);
            avatar = (ImageView) itemView.findViewById(R.id.image_avatar);
        }
    }
}
