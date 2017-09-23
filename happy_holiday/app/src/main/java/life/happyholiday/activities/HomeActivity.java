package life.happyholiday.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import life.happyholiday.R;
import life.happyholiday.fragments.HomeEventsFragment;
import life.happyholiday.fragments.HomeFriendsFragment;
import life.happyholiday.fragments.HomeMapFragment;
import life.happyholiday.fragments.HomeProfileFragment;

public class HomeActivity extends BaseActivity {
    @BindView(R.id.toolbar_title)
    TextView textToolbarTitle;
    @BindView(R.id.btn_add)
    View btnAdd;

    @BindView(R.id.bottom_navigation)
    AHBottomNavigation ahBottomNavigation;

    private HomeMapFragment mapFragment;
    private HomeEventsFragment eventsFragment;
    private HomeFriendsFragment friendsFragment;
    private HomeProfileFragment profileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        initFragments();
        createBottomNavBar();
    }

    private void initFragments() {
        mapFragment = HomeMapFragment.newInstance();
        eventsFragment = HomeEventsFragment.newInstance();
        friendsFragment = HomeFriendsFragment.newInstance();
        profileFragment = HomeProfileFragment.newInstance();
    }

    private void createBottomNavBar() {
        // Create items
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.menu_map, R.drawable.ic_map_white_24dp, R.color.colorPrimary);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.menu_events, R.drawable.ic_event_note_white_24dp, R.color.colorPrimary);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.menu_friends, R.drawable.ic_people_white_24dp, R.color.colorPrimary);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem(R.string.menu_profile, R.drawable.ic_account_circle_white_24dp, R.color.colorPrimary);

        // Set background color
        ahBottomNavigation.setDefaultBackgroundColor(Color.WHITE);

        // Add items
        ahBottomNavigation.addItem(item1);
        ahBottomNavigation.addItem(item2);
        ahBottomNavigation.addItem(item3);
        ahBottomNavigation.addItem(item4);

        // Change colors
        ahBottomNavigation.setAccentColor(ContextCompat.getColor(this, R.color.colorPrimary));
        ahBottomNavigation.setInactiveColor(Color.GRAY);
        ahBottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);

        ahBottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int i, boolean b) {
                switch (i) {
                    case 0:
                        textToolbarTitle.setText(R.string.menu_map);
                        btnAdd.setVisibility(View.GONE);
                        getSupportFragmentManager().beginTransaction().replace(R.id.layout_home_main, mapFragment).commit();
                        break;
                    case 1:
                        textToolbarTitle.setText(R.string.menu_events);
                        btnAdd.setVisibility(View.VISIBLE);
                        getSupportFragmentManager().beginTransaction().replace(R.id.layout_home_main, eventsFragment).commit();
                        break;
                    case 2:
                        textToolbarTitle.setText(R.string.menu_friends);
                        btnAdd.setVisibility(View.VISIBLE);
                        getSupportFragmentManager().beginTransaction().replace(R.id.layout_home_main, friendsFragment).commit();
                        break;
                    case 3:
                        textToolbarTitle.setText(R.string.menu_profile);
                        btnAdd.setVisibility(View.GONE);
                        getSupportFragmentManager().beginTransaction().replace(R.id.layout_home_main, profileFragment).commit();
                        break;
                }
                return true;
            }
        });

        // Set current item programmatically
        ahBottomNavigation.setCurrentItem(1);
    }
}
