package life.happyholiday.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import life.happyholiday.R;
import life.happyholiday.adapters.ScreenSlidePagerAdapter;
import life.happyholiday.fragments.EditEventFragment;
import life.happyholiday.models.EventModel;
import life.happyholiday.utils.ColorConfigHelper;
import life.happyholiday.utils.SoftKeyboardHelper;

public class EventDetailsActivity extends BaseActivity {
    @BindView(R.id.btn_back)
    View btnBack;
    @BindView(R.id.toolbar_title)
    TextView textToolbarTitle;
    @BindView(R.id.pager)
    ViewPager mPager;

    private Realm realm;
    private EventModel mEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        ButterKnife.bind(this);
        realm = Realm.getDefaultInstance();

//        findViewById(R.id.toolbar).setBackgroundColor(ColorConfigHelper.getPrimaryColor(this));

        findViewById(R.id.btn_edit).setVisibility(View.VISIBLE);
        btnBack.setVisibility(View.VISIBLE);

        // Get event data
        Bundle b = getIntent().getExtras();

        try {
            int eventId = b.getInt("EVENT_ID");
            mEvent = realm.where(EventModel.class).equalTo("id", eventId).findFirst();
            mEvent.addChangeListener(new RealmChangeListener<EventModel>() {
                @Override
                public void onChange(@NonNull EventModel event) {
                    // Update toolbar title
                    textToolbarTitle.setText(mEvent.getTitle());
                }
            });
        } catch (NullPointerException ignored) {
            finish(); // Exit if null
        }

        // Update toolbar title
        textToolbarTitle.setText(mEvent.getTitle());

        PagerAdapter pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(pagerAdapter);
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) { // select activity view
                    SoftKeyboardHelper.hideSoftKeyboard(EventDetailsActivity.this);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
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
    }

    @OnClick(R.id.btn_edit)
    void editTapped() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, mEvent == null ? EditEventFragment.newInstance() : EditEventFragment.newInstance(mEvent.getId()))
                // Add this transaction to the back stack
                .addToBackStack(null)
                .commit();
    }

    @OnClick(R.id.btn_back)
    void backPressed() {
        finish();
    }
}
