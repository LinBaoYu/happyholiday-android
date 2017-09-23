package life.happyholiday.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import life.happyholiday.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeMapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeMapFragment extends Fragment {

    public HomeMapFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment HomeMapFragment.
     */
    public static HomeMapFragment newInstance() {
        HomeMapFragment fragment = new HomeMapFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_map, container, false);
    }

}
