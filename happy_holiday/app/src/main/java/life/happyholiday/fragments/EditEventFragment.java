package life.happyholiday.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import life.happyholiday.R;
import life.happyholiday.models.EventModel;
import life.happyholiday.models.RealmDataHelper;
import life.happyholiday.utils.SoftKeyboardHelper;
import life.happyholiday.utils.StringHelper;
import timber.log.Timber;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditEventFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditEventFragment extends Fragment {

    @BindView(R.id.dialog_title) TextView dialogTitle;
    @BindView(R.id.input_title) EditText editTitle;
    @BindView(R.id.start_date) TextView startDate;
    @BindView(R.id.end_date) TextView endDate;
    @BindView(R.id.number) TextView textNumber;

    private Realm realm;
    private EventModel mEvent;

    public EditEventFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment HomeFriendsFragment.
     */
    public static EditEventFragment newInstance() {
        return new EditEventFragment();
    }

    public static EditEventFragment newInstance(int eventId) {
        EditEventFragment fragment = new EditEventFragment();
        Bundle args = new Bundle();
        args.putInt("EVENT_ID", eventId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realm = Realm.getDefaultInstance();

        if (getArguments() != null) {
            int eventId = getArguments().getInt("EVENT_ID", -1);
            Timber.e("event id " + eventId);
            mEvent = realm.where(EventModel.class).equalTo("id", eventId).findFirst();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_event, container, false);
        ButterKnife.bind(this, view);

        if (mEvent == null) {
            dialogTitle.setText(R.string.add_new_event);
            mEvent = new EventModel(editTitle.getText().toString(),
                    1,
                    2,
                    new Date(),
                    new Date());
        } else {
            dialogTitle.setText(R.string.edit_event);
            editTitle.setText(mEvent.getTitle());
        }

        startDate.setText(StringHelper.getDateString(mEvent.getStartDate()));
        endDate.setText(StringHelper.getDateString(mEvent.getEndDate()));
        textNumber.setText(String.valueOf(mEvent.getVacancy()));

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
        realm.close();
        SoftKeyboardHelper.hideSoftKeyboard(getActivity());
    }

    @OnClick(R.id.reduce)
    void reduceExpectingPeople() {
        textNumber.setText(StringHelper.updateIntString(textNumber.getText().toString(), -1));
    }

    @OnClick(R.id.increase)
    void increaseExpectingPeople() {
        textNumber.setText(StringHelper.updateIntString(textNumber.getText().toString(), 1));
    }

    @OnClick(R.id.btn_save)
    void saveBtnTapped() {
        // Use an unmanaged object to edit values outside Realm transaction
        final EventModel event = new EventModel(editTitle.getText().toString(),
                mEvent.getAttendingCount(),
                StringHelper.getIntFromString(textNumber.getText().toString()),
                StringHelper.getDateFromString(startDate.getText().toString()),
                StringHelper.getDateFromString(endDate.getText().toString()));
        event.setId(mEvent.getId());

        RealmDataHelper.addOrUpdateEvent(realm, event);
        getActivity().onBackPressed();
    }

    @OnClick(R.id.fragment_background)
    void backgroundTapped() {
        getActivity().onBackPressed();
    }
}
