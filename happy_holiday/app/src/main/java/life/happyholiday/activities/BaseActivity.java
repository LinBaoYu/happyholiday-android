package life.happyholiday.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import life.happyholiday.R;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
    }
}
