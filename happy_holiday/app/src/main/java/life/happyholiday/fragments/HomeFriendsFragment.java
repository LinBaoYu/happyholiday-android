package life.happyholiday.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import life.happyholiday.R;
import life.happyholiday.utils.ColorConfigHelper;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFriendsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFriendsFragment extends Fragment {
    @BindView(R.id.toolbar_title)
    TextView textToolbarTitle;
    @BindView(R.id.btn_add)
    View btnAdd;

    public HomeFriendsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment HomeFriendsFragment.
     */
    public static HomeFriendsFragment newInstance() {
        HomeFriendsFragment fragment = new HomeFriendsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_friends, container, false);
        ButterKnife.bind(this, view);

        view.findViewById(R.id.toolbar).setBackgroundColor(ColorConfigHelper.getPrimaryColor(getContext()));

        textToolbarTitle.setText(R.string.menu_friends);
        btnAdd.setVisibility(View.VISIBLE);

        return view;
    }

}
