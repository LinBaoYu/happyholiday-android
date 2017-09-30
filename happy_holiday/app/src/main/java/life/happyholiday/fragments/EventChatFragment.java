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
import android.widget.Button;
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

public class EventChatFragment extends Fragment{

    public EventChatFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment HomeEventsFragment.
     */
    public static EventChatFragment newInstance() {
        EventChatFragment fragment = new EventChatFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event_chat, container, false);
        ButterKnife.bind(this, view);

        return view;
    }
}
