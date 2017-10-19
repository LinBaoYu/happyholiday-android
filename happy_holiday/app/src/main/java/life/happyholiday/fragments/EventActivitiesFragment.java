package life.happyholiday.fragments;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.transition.Transition;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import life.happyholiday.R;
import life.happyholiday.adapters.EventActivitiesAdapter;
import life.happyholiday.models.ActivityModel;
import life.happyholiday.models.EventModel;
import life.happyholiday.models.RealmDataHelper;
import life.happyholiday.utils.ColorConfigHelper;
import life.happyholiday.utils.SimpleItemTouchHelperCallback;

public class EventActivitiesFragment extends Fragment implements EventActivitiesAdapter.EventActivitiesListener {
    @BindView(R.id.list_activity)
    RecyclerView recyclerView;
    @BindView(R.id.btn_join)
    Button btnJoin;

    @BindView(R.id.layout_empty_state)
    View layoutEmptyState;

    private Realm realm;
    private EventModel mEvent;

    public EventActivitiesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment HomeEventsFragment.
     */
    public static EventActivitiesFragment newInstance(int eventId) {
        EventActivitiesFragment fragment = new EventActivitiesFragment();
        Bundle args = new Bundle();
        args.putInt("EVENT_ID", eventId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realm = Realm.getDefaultInstance();

        // retrieve Event
        if (getArguments() != null) {
            mEvent = realm.where(EventModel.class).equalTo("id", getArguments().getInt("EVENT_ID", -1)).findFirst();
            if (mEvent == null) getActivity().finish(); // Exit if no Event data
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_event_activities, container, false);
        ButterKnife.bind(this, view);

//        btnJoin.setBackgroundColor(ColorConfigHelper.getDarkPrimaryColor(getContext()));

        // Initialize list and adapter
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        RealmResults<ActivityModel> results = mEvent.getActivities().sort("sequence");
        EventActivitiesAdapter adapter = new EventActivitiesAdapter(this, results);
        recyclerView.setAdapter(adapter);

        // Drag and Drop
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);

        // Check for empty state
        results.addChangeListener(new RealmChangeListener<RealmResults<ActivityModel>>() {
            @Override
            public void onChange(RealmResults<ActivityModel> activityModels) {
                layoutEmptyState.setVisibility(activityModels.size() == 0 ? View.VISIBLE : View.GONE);
            }
        });
        layoutEmptyState.setVisibility(results.size() == 0 ? View.VISIBLE : View.GONE);

        Transition sharedElementEnterTransition = getActivity().getWindow().getSharedElementEnterTransition();
        sharedElementEnterTransition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
                ObjectAnimator fadeIn = ObjectAnimator.ofFloat(view, "alpha", 0, 1);
                fadeIn.setInterpolator(new AccelerateInterpolator());
                fadeIn.setDuration(600);
                fadeIn.start();
            }

            @Override
            public void onTransitionEnd(Transition transition) {
            }

            @Override
            public void onTransitionCancel(Transition transition) {
            }

            @Override
            public void onTransitionPause(Transition transition) {
            }

            @Override
            public void onTransitionResume(Transition transition) {
            }
        });

        return view;
    }

    /*
     * It is good practice to null the reference from the view to the adapter when it is no longer needed.
     * Because the <code>RealmRecyclerViewAdapter</code> registers itself as a <code>RealmResult.ChangeListener</code>
     * the view may still be reachable if anybody is still holding a reference to the <code>RealmResult>.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        recyclerView.setAdapter(null);
        realm.close();
    }

    @Override
    public void addOrEditActivity(ActivityModel act) {
        getActivity().getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, EditActivityFragment.newInstance(act == null ? -1 : act.getId(), mEvent.getId()))
                // Add this transaction to the back stack
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void deleteActivity(ActivityModel activityModel) {
        RealmDataHelper.deleteActivity(realm, activityModel);
    }

    @Override
    public void voteUpActivity(ActivityModel activityModel) {
        RealmDataHelper.voteUpActivity(realm, activityModel);
    }

    @Override
    public void voteDownActivity(ActivityModel activityModel) {
        RealmDataHelper.voteDownActivity(realm, activityModel);
    }

    @Override
    public void swapActivitySequence(ActivityModel act1, ActivityModel act2) {
        RealmDataHelper.swapActivitySequence(realm, act1, act2);
    }

    @OnClick(R.id.btn_join)
    void joinBtnTapped() {
        btnJoin.setText(getString(R.string.not_sure).equals(btnJoin.getText().toString()) ? R.string.join : R.string.not_sure);
    }
}
