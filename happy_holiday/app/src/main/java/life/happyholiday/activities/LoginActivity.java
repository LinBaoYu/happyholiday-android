package life.happyholiday.activities;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.SyncCredentials;
import io.realm.SyncUser;
import life.happyholiday.R;
import life.happyholiday.utils.auth.GoogleAuth;
import timber.log.Timber;

public class LoginActivity extends BaseActivity {
    private static final int RC_SIGN_IN = 9001;

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

    private GoogleAuth googleAuth;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        // Setup Google Authentication
        googleAuth = new GoogleAuth((SignInButton) findViewById(R.id.sign_in_button), this) {
            @Override
            public void onRegistrationComplete(GoogleSignInResult result) {
//                UserManager.setAuthMode(UserManager.AUTH_MODE.GOOGLE);
                GoogleSignInAccount acct = result.getSignInAccount();
                Toast.makeText(LoginActivity.this, acct.getDisplayName() + " " + acct.getEmail() + " " + acct.getIdToken(), Toast.LENGTH_LONG).show();
//                SyncCredentials credentials = SyncCredentials.google(acct.getIdToken());
//                SyncUser.loginAsync(credentials, AUTH_URL, SignInActivity.this);
            }

            @Override
            public void onError(String s) {
                super.onError(s);
            }
        };


        // Prepare for animations
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;

        // Animation
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

        hideProgressDialog();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("loading");
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        googleAuth.onActivityResult(requestCode, resultCode, data);
    }
}
