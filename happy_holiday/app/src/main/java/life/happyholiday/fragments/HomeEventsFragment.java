package life.happyholiday.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import life.happyholiday.R;
import life.happyholiday.adapters.HomeEventsAdapter;
import life.happyholiday.models.EventModel;
import life.happyholiday.viewmodels.HomeEventsViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeEventsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeEventsFragment extends Fragment implements HomeEventsViewModel.OnResponseListener {

    @BindView(R.id.list_event)
    RecyclerView recyclerView;

    HomeEventsViewModel viewModel;
    HomeEventsAdapter adapter;

    public HomeEventsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment HomeEventsFragment.
     */
    public static HomeEventsFragment newInstance() {
        HomeEventsFragment fragment = new HomeEventsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new HomeEventsViewModel(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_events, container, false);
        ButterKnife.bind(this, view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter = new HomeEventsAdapter();
        recyclerView.setAdapter(adapter);

        // Load data
        viewModel.getEvents();

        return view;
    }

    @Override
    public void loadEventsSuccessful(List<EventModel> eventModelList) {
        adapter.setEventModelList(eventModelList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void error() {

    }
}
