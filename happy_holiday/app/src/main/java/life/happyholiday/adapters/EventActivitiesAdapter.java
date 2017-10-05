package life.happyholiday.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import life.happyholiday.R;
import life.happyholiday.models.ActivityModel;

/**
 * Adapter for Activity list in event detail.
 * <p>
 * Created by tliy916e on 5/10/17.
 */

public class EventActivitiesAdapter extends RecyclerView.Adapter implements ItemTouchHelperAdapter {

    private static final int FOOTER_VIEW = 1;

    private NewActivityClickedListener mListener;
    private List<ActivityModel> activityModelList;

    public EventActivitiesAdapter(NewActivityClickedListener listener) {
        activityModelList = new ArrayList<>();
        mListener = listener;
    }

    public EventActivitiesAdapter(List<ActivityModel> activityModelList) {
        this.activityModelList = activityModelList;
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
            final ActivityModel activity = activityModelList.get(position);
            final ActivityViewHolder activityViewHolder = (ActivityViewHolder) holder;

            activityViewHolder.textActivityTime.setText("10:00 am");
            activityViewHolder.textActivityTitle.setText(activity.getTitle());
            activityViewHolder.textUpVote.setText(String.format(Locale.getDefault(), "%d", activity.getVoteUp()));
            activityViewHolder.textDownVote.setText(String.format(Locale.getDefault(), "%d", activity.getVoteDown()));

            activityViewHolder.textUpVote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    activity.setVoteUp(activity.getVoteUp() + 1);
                    activityViewHolder.textUpVote.setText(String.format(Locale.getDefault(), "%d", activity.getVoteUp()));
                }
            });

            activityViewHolder.textDownVote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    activity.setVoteDown(activity.getVoteDown() - 1);
                    activityViewHolder.textDownVote.setText(String.format(Locale.getDefault(), "%d", activity.getVoteDown()));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (activityModelList == null) {
            return 0;
        }

        if (activityModelList.size() == 0) {
            //Return 1 here to show nothing
            return 1;
        }

        // Add extra view to show the footer view
        return activityModelList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == activityModelList.size()) {
            // This is where we'll add footer.
            return FOOTER_VIEW;
        }

        return super.getItemViewType(position);
    }

    public void setActivityModelList(List<ActivityModel> activityModelList) {
        this.activityModelList = activityModelList;
    }

    public List<ActivityModel> getActivityModelList() {
        return activityModelList;
    }

    @Override
    public void onItemDismiss(int position) {
        activityModelList.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(activityModelList, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(activityModelList, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
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
        }
    }

    class FooterViewHolder extends RecyclerView.ViewHolder {
        public FooterViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onNewActivityClicked();
                }
            });
        }
    }

    public interface NewActivityClickedListener {
        void onNewActivityClicked();
    }
}
