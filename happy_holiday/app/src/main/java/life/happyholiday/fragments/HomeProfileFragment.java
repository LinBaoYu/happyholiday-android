package life.happyholiday.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import life.happyholiday.R;
import life.happyholiday.activities.LoginActivity;
import life.happyholiday.utils.ColorConfigHelper;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeProfileFragment extends Fragment {
    @BindView(R.id.toolbar_title)
    TextView textToolbarTitle;

    public HomeProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment HomeProfileFragment.
     */
    public static HomeProfileFragment newInstance() {
        HomeProfileFragment fragment = new HomeProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_profile, container, false);
        ButterKnife.bind(this, view);

        view.findViewById(R.id.toolbar).setBackgroundColor(ColorConfigHelper.getPrimaryColor(getContext()));
        view.findViewById(R.id.layout_avatar).setBackgroundColor(ColorConfigHelper.getPrimaryColor(getContext()));

        textToolbarTitle.setText(R.string.menu_profile);

        return view;
    }

    @OnClick(R.id.btn_update)
    void checkUpdate() {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.dropbox.com/sh/3u3x54r3ro8qutb/AADkdWcSur7TOhxRtx1Rs-mCa?dl=0"));
        startActivity(browserIntent);
    }

    @OnClick(R.id.btn_logout)
    public void logout() {
        startActivity(new Intent(getContext(), LoginActivity.class));
    }
}
