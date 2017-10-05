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
    private List<ActivityModel> activityModelList;

    public EventActivitiesAdapter() {
        activityModelList = new ArrayList<>();
    }

    public EventActivitiesAdapter(List<ActivityModel> activityModelList) {
        this.activityModelList = activityModelList;
    }

    @Override
    public ActivityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_activity_card, parent, false);

        return new ActivityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ActivityModel activity = activityModelList.get(position);
        final ActivityViewHolder activityViewHolder = (ActivityViewHolder) holder;

        activityViewHolder.textActivityTime.setText("10:00 am");
        activityViewHolder.textActivityTitle.setText(activity.getTitle());
        activityViewHolder.textUpVote.setText(String.format(Locale.getDefault(),"%d", activity.getVoteUp()));
        activityViewHolder.textDownVote.setText(String.format(Locale.getDefault(),"%d", activity.getVoteDown()));
    }

    @Override
    public int getItemCount() {
        return activityModelList.size();
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
}
