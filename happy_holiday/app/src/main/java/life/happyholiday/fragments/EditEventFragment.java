package life.happyholiday.fragments;

import android.animation.Animator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import life.happyholiday.R;
import life.happyholiday.models.EventModel;
import life.happyholiday.models.RealmDataHelper;
import life.happyholiday.utils.SoftKeyboardHelper;
import life.happyholiday.utils.StringHelper;

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
    @BindView(R.id.date_start) DatePicker datePickerStart;
    @BindView(R.id.date_end) DatePicker datePickerEnd;

    private Realm realm;
    private EventModel mEvent;

    private Calendar mCalendarStart;
    private Calendar mCalendarEnd;

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

        // Initialize calendar
        mCalendarStart = Calendar.getInstance(Locale.getDefault());
        mCalendarEnd = Calendar.getInstance(Locale.getDefault());
        mCalendarStart.setTime(mEvent.getStartDate());
        mCalendarStart.setTime(mEvent.getEndDate());

        // Initialize date picker
        datePickerStart.updateDate(mCalendarStart.get(Calendar.YEAR), mCalendarStart.get(Calendar.MONTH), mCalendarStart.get(Calendar.DAY_OF_MONTH));
        datePickerEnd.updateDate(mCalendarEnd.get(Calendar.YEAR), mCalendarEnd.get(Calendar.MONTH), mCalendarEnd.get(Calendar.DAY_OF_MONTH));

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

    @OnClick(R.id.layout_start_date)
    void showDatePickerStart() {
        SoftKeyboardHelper.hideSoftKeyboard(getActivity());
//        datePickerStart.setVisibility(View.VISIBLE);

        // get the center for the clipping circle
        int cx = (int) (datePickerStart.getWidth() * 0.36f);
        int cy = datePickerStart.getHeight() / 2;

        // get the final radius for the clipping circle
        float finalRadius = (float) Math.hypot(cx, cy) * 1.2f;

        // create the animator for this view (the start radius is zero)
        Animator anim =
                ViewAnimationUtils.createCircularReveal(datePickerStart, cx, cy, 20, finalRadius);

        // make the view visible and start the animation
        datePickerStart.setVisibility(View.VISIBLE);
        anim.start();
    }

    @OnClick(R.id.btn_set_1)
    void setStartDate() {
        mCalendarStart.set(Calendar.YEAR, datePickerStart.getYear());
        mCalendarStart.set(Calendar.MONTH, datePickerStart.getMonth());
        mCalendarStart.set(Calendar.DAY_OF_MONTH, datePickerStart.getDayOfMonth());

        startDate.setText(StringHelper.getDateString(mCalendarStart.getTime()));

        // Update end date if start date is later than end date
        if (mCalendarStart.getTimeInMillis() > mCalendarEnd.getTimeInMillis()) {
            mCalendarEnd = mCalendarStart;
            endDate.setText(StringHelper.getDateString(mCalendarEnd.getTime()));
            datePickerEnd.updateDate(mCalendarEnd.get(Calendar.YEAR), mCalendarEnd.get(Calendar.MONTH), mCalendarEnd.get(Calendar.DAY_OF_MONTH));
        }

        datePickerStart.setVisibility(View.GONE);
    }

    @OnClick(R.id.layout_end_date)
    void showDatePickerEnd() {
        SoftKeyboardHelper.hideSoftKeyboard(getActivity());
        datePickerEnd.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.btn_set_2)
    void setEndDate() {
        mCalendarEnd.set(Calendar.YEAR, datePickerEnd.getYear());
        mCalendarEnd.set(Calendar.MONTH, datePickerEnd.getMonth());
        mCalendarEnd.set(Calendar.DAY_OF_MONTH, datePickerEnd.getDayOfMonth());

        endDate.setText(StringHelper.getDateString(mCalendarEnd.getTime()));

        datePickerEnd.setVisibility(View.GONE);
    }

    @OnClick(R.id.btn_save)
    void saveBtnTapped() {
        // Use an unmanaged object to edit values outside Realm transaction
        final EventModel event = new EventModel(editTitle.getText().toString(),
                mEvent.getAttendingCount(),
                StringHelper.getIntFromString(textNumber.getText().toString()),
                mCalendarStart.getTime(),
                mCalendarEnd.getTime());
        event.setId(mEvent.getId());

        RealmDataHelper.addOrUpdateEvent(realm, event);
        getActivity().onBackPressed();
    }

    @OnClick(R.id.fragment_background)
    void backgroundTapped() {
        getActivity().onBackPressed();
    }
}
