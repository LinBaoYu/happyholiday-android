package life.happyholiday.adapters;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;
import life.happyholiday.R;
import life.happyholiday.models.EventModel;
import life.happyholiday.utils.StringHelper;

/**
 * Adapter for Event list in home screen.
 * <p>
 * Created by tliy916e on 16/9/17.
 */

public class HomeEventsAdapter extends RealmRecyclerViewAdapter<EventModel, RecyclerView.ViewHolder> {
    private ItemClickListener mClickListener;
    private Context mContext;

    public HomeEventsAdapter(OrderedRealmCollection<EventModel> data) {
        super(data, true);
        setHasStableIds(true); // For optimization purpose
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.layout_event_card, parent, false);

        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        EventModel event = getItem(position);
        EventViewHolder eventViewHolder = (EventViewHolder) holder;

        eventViewHolder.textEventTitle.setText(event.getTitle());
        eventViewHolder.textEventAttendersCount.setText(mContext.getString(R.string.num_of_num_people,event.getAttendingCount(), event.getVacancy()));
        eventViewHolder.textEventDate.setText(StringHelper.getDateString(event.getStartDate()));
        eventViewHolder.textEventDuration.setText("1d");
    }

    // For optimization purpose
    @Override
    public long getItemId(int index) {
        //noinspection ConstantConditions
        return getItem(index).getId();
    }

    public void setOnItemClickListener(ItemClickListener itemClickListener) {
        mClickListener = itemClickListener;
    }

    class EventViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.card_view)
        View viewCard;
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

//            ViewCompat.setTransitionName(view, "" + getAdapterPosition());

            viewCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mClickListener.onItemClick(getAdapterPosition(), view);
                }
            });

            viewCard.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    mClickListener.onItemLongClick(getAdapterPosition(), view);
                    return true; // indicate that the touch event is consumed
                }
            });
        }
    }

    public interface ItemClickListener {
        void onItemClick(int position, View v);

        void onItemLongClick(int position, View v);
    }
}
