package life.happyholiday.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.OnClick;
import life.happyholiday.R;
import life.happyholiday.activities.LoginActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeProfileFragment extends Fragment {

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

        return view;
    }

    @OnClick(R.id.btn_logout)
    public void logout() {
        startActivity(new Intent(getContext(), LoginActivity.class));
    }
}
