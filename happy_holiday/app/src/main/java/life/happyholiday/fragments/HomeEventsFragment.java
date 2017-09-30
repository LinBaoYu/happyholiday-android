package life.happyholiday.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import life.happyholiday.R;
import life.happyholiday.activities.EventDetailsActivity;
import life.happyholiday.adapters.HomeEventsAdapter;
import life.happyholiday.models.EventModel;
import life.happyholiday.viewmodels.HomeEventsViewModel;
import me.samthompson.bubbleactions.BubbleActions;
import me.samthompson.bubbleactions.Callback;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeEventsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeEventsFragment extends Fragment implements HomeEventsViewModel.OnResponseListener {
    @BindView(R.id.toolbar_title)
    TextView textToolbarTitle;
    @BindView(R.id.btn_add)
    View btnAdd;

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

        textToolbarTitle.setText(R.string.menu_events);
        btnAdd.setVisibility(View.VISIBLE);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter = new HomeEventsAdapter();
        adapter.setOnItemClickListener(new HomeEventsAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Intent intent = new Intent(getActivity(), EventDetailsActivity.class);
                Bundle b = new Bundle();
                b.putSerializable("event", adapter.getEventModelList().get(position));
                intent.putExtras(b);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(final int position, View v) {
                BubbleActions.on(v)
                        .addAction("Join", R.drawable.ic_favorite_black_24dp, new Callback() {
                            @Override
                            public void doAction() {
                                Toast.makeText(getContext(), "Join the event!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addAction("Edit", R.drawable.ic_mode_edit_black_24dp, new Callback() {
                            @Override
                            public void doAction() {
                                Toast.makeText(getContext(), "Edit the event!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addAction("Delete", R.drawable.ic_highlight_off_black_24dp, new Callback() {
                            @Override
                            public void doAction() {
                                viewModel.deleteEvent(adapter.getEventModelList().get(position));
                            }
                        })
                        .show();
            }
        });
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
    public void deleteEventSuccessful() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void error() {

    }

    @OnClick(R.id.btn_add)
    void addEvent() {
        viewModel.addEvent();
    }
}
