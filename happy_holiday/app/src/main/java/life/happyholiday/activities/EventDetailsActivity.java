package life.happyholiday.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import life.happyholiday.R;
import life.happyholiday.models.EventModel;

public class EventDetailsActivity extends BaseActivity {
    @BindView(R.id.btn_back)
    View btnBack;
    @BindView(R.id.toolbar_title)
    TextView textToolbarTitle;

    private EventModel mEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        ButterKnife.bind(this);

        btnBack.setVisibility(View.VISIBLE);

        //
        Bundle b = getIntent().getExtras();
        mEvent = (EventModel) b.getSerializable("event");
        if (mEvent == null) finish(); // Finish activity if event data not available

        textToolbarTitle.setText(mEvent.getTitle());
    }

    @OnClick(R.id.btn_back)
    void backPressed() {
        finish();
    }
}
