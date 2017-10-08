package life.happyholiday.fragments;

import android.content.Context;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import life.happyholiday.R;
import life.happyholiday.activities.EventDetailsActivity;
import life.happyholiday.adapters.HomeEventsAdapter;
import life.happyholiday.models.EventModel;
import life.happyholiday.models.RealmDataHelper;
import life.happyholiday.utils.ColorConfigHelper;
import me.samthompson.bubbleactions.BubbleActions;
import me.samthompson.bubbleactions.Callback;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeEventsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeEventsFragment extends Fragment {
    @BindView(R.id.toolbar_title)
    TextView textToolbarTitle;
    @BindView(R.id.btn_add)
    View btnAdd;

    @BindView(R.id.list_event)
    RecyclerView recyclerView;

    private Realm realm;
    private HomeEventsAdapter adapter;
    private FragmentListener mListener;

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
        return new HomeEventsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_events, container, false);
        ButterKnife.bind(this, view);

        view.findViewById(R.id.toolbar).setBackgroundColor(ColorConfigHelper.getPrimaryColor(getContext()));

        // Initialize Realm database
        realm = Realm.getDefaultInstance();

        // Update UI
        textToolbarTitle.setText(R.string.menu_events);
        btnAdd.setVisibility(View.VISIBLE);

        // Set up RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter = new HomeEventsAdapter(realm.where(EventModel.class).findAll().sort("startDate"));

        // Add bubble actions to item in the list
        adapter.setOnItemClickListener(new HomeEventsAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Intent intent = new Intent(getActivity(), EventDetailsActivity.class);
                Bundle b = new Bundle();
                b.putInt("EVENT_ID", adapter.getItem(position).getId());
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
                                mListener.showEditEventDialog(adapter.getItem(position));
                            }
                        })
                        .addAction("Delete", R.drawable.ic_highlight_off_black_24dp, new Callback() {
                            @Override
                            public void doAction() {
                                RealmDataHelper.deleteEvent(realm, adapter.getItem(position));
                            }
                        })
                        .show();
            }
        });
        recyclerView.setAdapter(adapter);

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
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mListener = (FragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @OnClick(R.id.btn_add)
    void addEvent() {
        mListener.showEditEventDialog(null);
    }

    // Container Activity must implement this interface
    public interface FragmentListener {
        void showEditEventDialog(EventModel event);
    }
}
