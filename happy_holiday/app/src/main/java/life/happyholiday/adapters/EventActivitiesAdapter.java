package life.happyholiday.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;
import life.happyholiday.R;
import life.happyholiday.models.ActivityModel;

/**
 * Adapter for Activity list in event detail.
 * <p>
 * Created by tliy916e on 5/10/17.
 */

public class EventActivitiesAdapter extends RealmRecyclerViewAdapter<ActivityModel, RecyclerView.ViewHolder> implements ItemTouchHelperAdapter {

    private EventActivitiesListener mListener;

    public EventActivitiesAdapter(EventActivitiesListener listener, OrderedRealmCollection<ActivityModel> data) {
        super(data, true);
        mListener = listener;
        setHasStableIds(true); // For optimization purpose
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_activity_card, parent, false);
        return new ActivityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ActivityViewHolder) {
            final ActivityModel activity = getItem(position);
            final ActivityViewHolder activityViewHolder = (ActivityViewHolder) holder;

            activityViewHolder.viewMarginTop.setVisibility(position == 0 ? View.VISIBLE : View.GONE);
            activityViewHolder.textActivityTime.setText("10:00 am");
            activityViewHolder.textActivityTitle.setText(activity.getTitle());
            activityViewHolder.textUpVote.setText(String.format(Locale.getDefault(), "%d", activity.getVoteUp()));
            activityViewHolder.textDownVote.setText(String.format(Locale.getDefault(), "%d", activity.getVoteDown()));

            activityViewHolder.textUpVote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.voteUpActivity(activity);
                }
            });

            activityViewHolder.textDownVote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.voteDownActivity(activity);
                }
            });
        }
    }

    // For optimization purpose
    @Override
    public long getItemId(int index) {
        //noinspection ConstantConditions
        return getItem(index).getId();
    }

    @Override
    public void onItemDismiss(int position) {
        mListener.deleteActivity(getItem(position));
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        mListener.swapActivitySequence(getItem(fromPosition), getItem(toPosition));
    }

    class ActivityViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.view_margin_top)
        View viewMarginTop;
        @BindView(R.id.activity_time)
        TextView textActivityTime;
        @BindView(R.id.activity_title)
        TextView textActivityTitle;
        @BindView(R.id.up_vote)
        TextView textUpVote;
        @BindView(R.id.down_vote)
        TextView textDownVote;

        ActivityViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface EventActivitiesListener {

        void deleteActivity(ActivityModel activityModel);

        void voteUpActivity(ActivityModel activityModel);

        void voteDownActivity(ActivityModel activityModel);

        void swapActivitySequence(ActivityModel act1, ActivityModel act2);
    }
}
