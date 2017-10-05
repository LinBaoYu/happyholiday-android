package life.happyholiday.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import life.happyholiday.R;
import life.happyholiday.activities.EventDetailsActivity;
import life.happyholiday.adapters.EventActivitiesAdapter;
import life.happyholiday.adapters.HomeEventsAdapter;
import life.happyholiday.models.ActivityModel;
import life.happyholiday.models.EventModel;
import life.happyholiday.utils.ColorConfigHelper;
import life.happyholiday.utils.SimpleItemTouchHelperCallback;
import life.happyholiday.utils.SoftKeyboardHelper;
import life.happyholiday.viewmodels.EventActivitiesViewModel;
import life.happyholiday.viewmodels.HomeEventsViewModel;
import me.samthompson.bubbleactions.BubbleActions;
import me.samthompson.bubbleactions.Callback;

public class EventActivitiesFragment extends Fragment implements EventActivitiesViewModel.OnResponseListener {
    @BindView(R.id.list_activity)
    RecyclerView recyclerView;
    @BindView(R.id.btn_join)
    Button btnJoin;

    EventActivitiesViewModel mViewModel;
    EventActivitiesAdapter mAdapter;

    public EventActivitiesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment HomeEventsFragment.
     */
    public static EventActivitiesFragment newInstance() {
        EventActivitiesFragment fragment = new EventActivitiesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new EventActivitiesViewModel(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event_activities, container, false);
        ButterKnife.bind(this, view);

        btnJoin.setBackgroundColor(ColorConfigHelper.getDarkPrimaryColor(getContext()));

        // Initialize list and adapter
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new EventActivitiesAdapter();
        recyclerView.setAdapter(mAdapter);

        // Drag and Drop
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);

        // Load data
        mViewModel.getActivities();

        return view;
    }

    @Override
    public void loadActivitiesSuccessful(List<ActivityModel> activityModelList) {
        mAdapter.setActivityModelList(activityModelList);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void deleteActivitySuccessful() {
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void error() {
    }

    @OnClick(R.id.btn_join)
    void joinBtnTapped() {
        btnJoin.setText(getString(R.string.not_sure).equals(btnJoin.getText().toString()) ? R.string.join : R.string.not_sure);
    }
}
