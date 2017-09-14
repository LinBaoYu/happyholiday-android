package life.happyholiday.activities;

import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.OnClick;
import life.happyholiday.R;

public class LoginActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_login)
    public void login() {

    }
}
