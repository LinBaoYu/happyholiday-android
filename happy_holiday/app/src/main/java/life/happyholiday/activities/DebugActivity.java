package life.happyholiday.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import life.happyholiday.R;
import life.happyholiday.utils.ColorConfigHelper;

public class DebugActivity extends BaseActivity {
    @BindView(R.id.main_color)
    EditText editMainColor;
    @BindView(R.id.dark_main_color)
    EditText editDarkMainColor;
    @BindView(R.id.chat_bg_color)
    EditText editChatBg;

    @BindView(R.id.view_main_color)
    View viewMainColor;
    @BindView(R.id.view_dark_main_color)
    View viewDarkMainColor;
    @BindView(R.id.view_chat_bg_color)
    View viewChatBgColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debug);
        ButterKnife.bind(this);

        editMainColor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    viewMainColor.setBackgroundColor(Color.parseColor(editable.toString()));
                } catch (Exception ignored) {}
            }
        });

        editDarkMainColor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    viewDarkMainColor.setBackgroundColor(Color.parseColor(editable.toString()));
                } catch (Exception ignored) {}
            }
        });

        editChatBg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    viewChatBgColor.setBackgroundColor(Color.parseColor(editable.toString()));
                } catch (Exception ignored) {}
            }
        });

        editMainColor.setText(ColorConfigHelper.getPrimaryColorString(this));
        editDarkMainColor.setText(ColorConfigHelper.getDarkPrimaryColorString(this));
        editChatBg.setText(ColorConfigHelper.getChatBgColorString(this));
    }

    @OnClick(R.id.btn_apply)
    void applyTapped() {
        boolean allCorrect = true;

        try {
            Color.parseColor(editMainColor.getText().toString());
            Color.parseColor(editDarkMainColor.getText().toString());
            Color.parseColor(editChatBg.getText().toString());
        } catch (Exception e) {
            allCorrect = false;
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Warning");
            alertDialog.setMessage("Something wrong with color format :( Please amend!");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }

        if (allCorrect) {
            ColorConfigHelper.setPrimaryColor(this, editMainColor.getText().toString());
            ColorConfigHelper.setDarkPrimaryColor(this, editDarkMainColor.getText().toString());
            ColorConfigHelper.setChatBgColor(this, editChatBg.getText().toString());

            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}
