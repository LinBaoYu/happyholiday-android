package life.happyholiday.fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import life.happyholiday.R;
import life.happyholiday.models.ActivityModel;
import life.happyholiday.models.EventModel;
import life.happyholiday.models.RealmDataHelper;
import life.happyholiday.utils.SoftKeyboardHelper;
import life.happyholiday.utils.StringHelper;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditActivityFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditActivityFragment extends Fragment {

    @BindView(R.id.dialog_title) TextView dialogTitle;
    @BindView(R.id.input_title) EditText editTitle;
    @BindView(R.id.input_description) EditText editDescription;
    @BindView(R.id.input_location) EditText editLocation;
    @BindView(R.id.time) TextView textTime;
    @BindView(R.id.time_picker) TimePicker timePicker;

    private Realm realm;
    private ActivityModel mActivity;
    private EventModel mEvent;
    private Calendar mCalendar;

    public EditActivityFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment HomeFriendsFragment.
     */
    public static EditActivityFragment newInstance(int actId, int eventId) {
        EditActivityFragment fragment = new EditActivityFragment();
        Bundle args = new Bundle();
        args.putInt("ACTIVITY_ID", actId);
        args.putInt("EVENT_ID", eventId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realm = Realm.getDefaultInstance();

        if (getArguments() != null) {
            int actId = getArguments().getInt("ACTIVITY_ID", -1);
            int eventId = getArguments().getInt("EVENT_ID", -1);
            mActivity = realm.where(ActivityModel.class).equalTo("id", actId).findFirst();
            mEvent = realm.where(EventModel.class).equalTo("id", eventId).findFirst();
            if (mEvent == null) getActivity().finish(); // Exit if no Event data
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_activity, container, false);
        ButterKnife.bind(this, view);

        if (mActivity == null) {
            dialogTitle.setText(R.string.add_new_activity);
            mActivity = new ActivityModel("", "", "", new Date(), new Date(), 0, 0);
        } else {
            dialogTitle.setText(R.string.edit_activity);
            editTitle.setText(mActivity.getTitle());
            editDescription.setText(mActivity.getDescription());
            editLocation.setText(mActivity.getLocation());
        }

        mCalendar = Calendar.getInstance(Locale.getDefault());
        mCalendar.setTime(mActivity.getStartDate());
        textTime.setText(StringHelper.getDateHourMinuteString(mActivity.getStartDate()));

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

    @OnClick(R.id.layout_time)
    void showTimePicker() {
        SoftKeyboardHelper.hideSoftKeyboard(getActivity());
        timePicker.setVisibility(View.VISIBLE);

        if (Build.VERSION.SDK_INT >= 23 ) {
            timePicker.setHour(mCalendar.get(Calendar.HOUR));
            timePicker.setMinute(mCalendar.get(Calendar.MINUTE));
        } else {
            timePicker.setCurrentHour(mCalendar.get(Calendar.HOUR));
            timePicker.setCurrentMinute(mCalendar.get(Calendar.MINUTE));
        }
    }

    @OnClick(R.id.btn_set)
    void setTime() {
        if (Build.VERSION.SDK_INT >= 23 ) {
            mCalendar.set(Calendar.HOUR, timePicker.getHour());
            mCalendar.set(Calendar.MINUTE, timePicker.getMinute());
        } else {
            mCalendar.set(Calendar.HOUR, timePicker.getCurrentHour());
            mCalendar.set(Calendar.MINUTE, timePicker.getCurrentMinute());
        }
        textTime.setText(StringHelper.getDateHourMinuteString(mCalendar.getTime()));

        timePicker.setVisibility(View.GONE);
    }

    @OnClick(R.id.btn_save)
    void saveBtnTapped() {
        // Use an unmanaged object to edit values outside Realm transaction
        final ActivityModel act = new ActivityModel(editTitle.getText().toString(),
                editDescription.getText().toString(),
                editLocation.getText().toString(),
                mCalendar.getTime(),
                mCalendar.getTime(),
                mActivity.getVoteUp(),
                mActivity.getVoteDown());

        act.setId(mActivity.getId());
        act.setSequence(mActivity.getSequence());

        RealmDataHelper.addOrUpdateActivity(realm, act, mEvent);
        getActivity().onBackPressed();
    }

    @OnClick(R.id.fragment_background)
    void backgroundTapped() {
        getActivity().onBackPressed();
    }
}
