package life.happyholiday.activities;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import life.happyholiday.R;

public class LoginActivity extends BaseActivity {
    @BindView(R.id.logo)
    View logo;

    @BindView(R.id.layout_login)
    View layoutLogin;
    @BindView(R.id.edit_username)
    EditText editUsername;
    @BindView(R.id.edit_password)
    EditText editPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;

        ObjectAnimator moveUpLogo = ObjectAnimator.ofFloat(logo, "translationY", height * -0.14f);
        ObjectAnimator moveDownLoginLayout = ObjectAnimator.ofFloat(layoutLogin, "translationY", height * 0.08f);
        ObjectAnimator fadeInLoginLayout = ObjectAnimator.ofFloat(layoutLogin, "alpha", 0, 1);
        fadeInLoginLayout.setInterpolator(new AccelerateInterpolator());
        AnimatorSet animatorSet = new AnimatorSet().setDuration(600);
        animatorSet.setStartDelay(400);
        animatorSet.playTogether(moveUpLogo, moveDownLoginLayout, fadeInLoginLayout);
        animatorSet.start();

        // Test configurable color
//        final ViewGroup viewGroup = (ViewGroup) ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);
//        viewGroup.setBackgroundColor(ColorConfigHelper.getPrimaryColor(this));
//        btnLogin.setBackgroundColor(ColorConfigHelper.getDarkPrimaryColor(this));
    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    @OnClick(R.id.logo)
    void logoTapped() {
        startActivity(new Intent(this, DebugActivity.class));
    }

    @OnClick(R.id.btn_login)
    public void login(View btnLogin) {
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
