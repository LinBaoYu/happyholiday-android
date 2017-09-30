package life.happyholiday.activities;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import life.happyholiday.R;
import life.happyholiday.adapters.ScreenSlidePagerAdapter;
import life.happyholiday.models.EventModel;

public class EventDetailsActivity extends BaseActivity {
    @BindView(R.id.btn_back)
    View btnBack;
    @BindView(R.id.toolbar_title)
    TextView textToolbarTitle;
    @BindView(R.id.pager)
    ViewPager mPager;

    private PagerAdapter mPagerAdapter;
    private EventModel mEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        ButterKnife.bind(this);

        btnBack.setVisibility(View.VISIBLE);

        // Get event data
        Bundle b = getIntent().getExtras();
        mEvent = (EventModel) b.getSerializable("event");
        if (mEvent == null) finish(); // Finish activity if event data not available

        // Update toolbar title
        textToolbarTitle.setText(mEvent.getTitle());

        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
    }

    @OnClick(R.id.btn_back)
    void backPressed() {
        finish();
    }
}
