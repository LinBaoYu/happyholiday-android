package life.happyholiday.activities;

import android.os.Bundle;

import butterknife.ButterKnife;
import life.happyholiday.R;

public class HomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
    }
}
