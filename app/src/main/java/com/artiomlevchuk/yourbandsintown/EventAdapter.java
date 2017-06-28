package com.artiomlevchuk.yourbandsintown;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.artiomlevchuk.yourbandsintown.entities.Event;

import java.util.ArrayList;
import java.util.List;

class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {

    private List<Event> mEvents;
    private Activity mActivity;

    EventAdapter(Activity activity) {
        this.mEvents = new ArrayList<>();
        this.mActivity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(
                parent.getContext()).inflate(R.layout.item_event, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        /*view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(mEvents.get(holder.getAdapterPosition()).getUrl()));
                mActivity.startActivity(intent);
            }
        });*/

        holder.locationMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Event event = mEvents.get(holder.getAdapterPosition());
                Intent intent = new Intent(mActivity, MapsActivity.class);
                intent.putExtra("EXTRA_LONGITUDE", event.getVenue().getLongitude());
                intent.putExtra("EXTRA_LATITUDE", event.getVenue().getLatitude());
                intent.putExtra("EXTRA_TITLE", event.getVenue().getName());
                mActivity.startActivity(intent);
            }
        });

        holder.tickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(mEvents.get(holder.getAdapterPosition()).getOffers().get(0).getUrl()));
                mActivity.startActivity(intent);
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Event event = mEvents.get(position);

        holder.name.setText(event.getVenue().getName());
        holder.location.setText(event.getVenue().getLocation());
        holder.month.setText(event.getMonth());
        holder.date.setText(event.getDate());

        if (event.getOffers().size() == 0) {
            holder.tickets.setVisibility(View.GONE);
        } else {
            holder.tickets.setVisibility(View.VISIBLE);
            String imageName = "ic_tickets_gray";
            if (event.getOffers().get(0).getStatus().equals("available"))
                imageName = "ic_tickets";
            holder.tickets.setImageDrawable(ContextCompat.getDrawable(
                        mActivity,  mActivity.getResources().getIdentifier(
                                imageName, "drawable", mActivity.getPackageName())));
        }
    }

    @Override
    public int getItemCount() {
        return mEvents.size();
    }

    void addAll(List<Event> data) {
        mEvents.addAll(data);
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView location;
        TextView month;
        TextView date;
        ImageView tickets;
        ImageView locationMap;

        ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.title_name);
            location = (TextView) itemView.findViewById(R.id.title_location);
            month = (TextView) itemView.findViewById(R.id.title_month);
            date = (TextView) itemView.findViewById(R.id.title_date);
            tickets = (ImageView) itemView.findViewById(R.id.image_tickets);
            locationMap = (ImageView) itemView.findViewById(R.id.image_location);
        }
    }
}

/*

Clickable items
ProgressBar
Scrolling RecyclerView | Hidden mode
Clear history
App_id

 */
