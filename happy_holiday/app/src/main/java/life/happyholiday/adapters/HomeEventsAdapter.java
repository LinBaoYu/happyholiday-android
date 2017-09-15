package life.happyholiday.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import life.happyholiday.R;
import life.happyholiday.models.EventModel;

/**
 * Created by tliy916e on 16/9/17.
 */

public class HomeEventsAdapter extends RecyclerView.Adapter {

    private List<EventModel> eventModelList;

    public HomeEventsAdapter() {
        eventModelList = new ArrayList<EventModel>();
    }

    public HomeEventsAdapter(List<EventModel> eventModelList) {
        this.eventModelList = eventModelList;
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_event_card, parent, false);

        final Context context = parent.getContext();

        // TODO remove this, just for testing
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "What??", Toast.LENGTH_SHORT).show();
            }
        });

        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        EventModel event = eventModelList.get(position);
        final EventViewHolder eventViewHolder = (EventViewHolder) holder;

        eventViewHolder.textEventTitle.setText(event.getTitle());
        eventViewHolder.textEventAttendersCount.setText(event.getAttendingCount() + "/" + event.getVacancy() + " persons");
        eventViewHolder.textEventDate.setText("Sep 18, 2017");
        eventViewHolder.textEventDuration.setText("1d");
    }

    @Override
    public int getItemCount() {
        return eventModelList.size();
    }

    public void setEventModelList(List<EventModel> eventModelList) {
        this.eventModelList = eventModelList;
    }

    class EventViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.event_title)
        TextView textEventTitle;
        @BindView(R.id.event_attenders_count)
        TextView textEventAttendersCount;
        @BindView(R.id.event_date)
        TextView textEventDate;
        @BindView(R.id.event_duration)
        TextView textEventDuration;

        EventViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
