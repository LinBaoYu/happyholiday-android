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
import life.happyholiday.models.EventModel;
import life.happyholiday.utils.StringHelper;

/**
 * Adapter for Activity list in event detail.
 * <p>
 * Created by tliy916e on 5/10/17.
 */

public class EventActivitiesAdapter extends RealmRecyclerViewAdapter<ActivityModel, RecyclerView.ViewHolder> implements ItemTouchHelperAdapter {

    private static final int FOOTER_VIEW = 1;

    private EventActivitiesListener mListener;

    public EventActivitiesAdapter(EventActivitiesListener listener, OrderedRealmCollection<ActivityModel> data) {
        super(data, true);
        mListener = listener;
        setHasStableIds(true); // For optimization purpose
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        if (viewType == FOOTER_VIEW) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_activity_card_footer, parent, false);
            return new FooterViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_activity_card, parent, false);
            return new ActivityViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ActivityViewHolder) {
            final ActivityModel activity = getItem(position);
            final ActivityViewHolder activityViewHolder = (ActivityViewHolder) holder;

            activityViewHolder.textActivityTime.setText(StringHelper.getDateHourMinuteString(activity.getStartDate()));
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
        // return -1 for footer
        if (index == getData().size()) return -1;

        //noinspection ConstantConditions
        return getItem(index).getId();
    }

    @Override
    public int getItemCount() {
        // Add extra view to show the footer view
        return getData().size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getData().size()) {
            // This is where we'll add footer.
            return FOOTER_VIEW;
        }

        return super.getItemViewType(position);
    }


    @Override
    public void onItemDismiss(int position) {
        mListener.deleteActivity(getItem(position));
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        // Do not move footer
        if (toPosition == getData().size()) return;

        mListener.swapActivitySequence(getItem(fromPosition), getItem(toPosition));
    }

    class ActivityViewHolder extends RecyclerView.ViewHolder {
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

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.addOrEditActivity(getData().get(getAdapterPosition()));
                }
            });
        }
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {
        FooterViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.addOrEditActivity(null);
                }
            });
        }
    }


    public interface EventActivitiesListener {

        void addOrEditActivity(ActivityModel act);

        void deleteActivity(ActivityModel activityModel);

        void voteUpActivity(ActivityModel activityModel);

        void voteDownActivity(ActivityModel activityModel);

        void swapActivitySequence(ActivityModel act1, ActivityModel act2);
    }
}
