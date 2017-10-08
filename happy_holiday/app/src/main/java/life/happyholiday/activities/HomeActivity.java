package life.happyholiday.activities;

import android.graphics.Color;
import android.os.Bundle;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import life.happyholiday.R;
import life.happyholiday.fragments.EditEventFragment;
import life.happyholiday.fragments.HomeEventsFragment;
import life.happyholiday.fragments.HomeFriendsFragment;
import life.happyholiday.fragments.HomeMapFragment;
import life.happyholiday.fragments.HomeProfileFragment;
import life.happyholiday.models.EventModel;
import life.happyholiday.utils.ColorConfigHelper;

public class HomeActivity extends BaseActivity implements HomeEventsFragment.FragmentListener {
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
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.menu_map, R.drawable.ic_map_white_24dp, R.color.colorPrimaryDark);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.menu_events, R.drawable.ic_event_note_white_24dp, R.color.colorPrimaryDark);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.menu_friends, R.drawable.ic_people_white_24dp, R.color.colorPrimaryDark);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem(R.string.menu_profile, R.drawable.ic_account_circle_white_24dp, R.color.colorPrimaryDark);

        // Set background color
        ahBottomNavigation.setDefaultBackgroundColor(Color.WHITE);

        // Add items
        ahBottomNavigation.addItem(item1);
        ahBottomNavigation.addItem(item2);
        ahBottomNavigation.addItem(item3);
        ahBottomNavigation.addItem(item4);

        // Change colors
        ahBottomNavigation.setAccentColor(ColorConfigHelper.getDarkPrimaryColor(this));
        ahBottomNavigation.setInactiveColor(Color.GRAY);
        ahBottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);

        ahBottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int i, boolean b) {
                switch (i) {
                    case 0:
                        getSupportFragmentManager().beginTransaction().replace(R.id.layout_home_main, mapFragment).commit();
                        break;
                    case 1:
                        getSupportFragmentManager().beginTransaction().replace(R.id.layout_home_main, eventsFragment).commit();
                        break;
                    case 2:
                        getSupportFragmentManager().beginTransaction().replace(R.id.layout_home_main, friendsFragment).commit();
                        break;
                    case 3:
                        getSupportFragmentManager().beginTransaction().replace(R.id.layout_home_main, profileFragment).commit();
                        break;
                }
                return true;
            }
        });

        // Set current item programmatically
        ahBottomNavigation.setCurrentItem(1);
    }

    @Override
    public void showEditEventDialog(EventModel event) {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, event == null ? EditEventFragment.newInstance() : EditEventFragment.newInstance(event.getId()))
                // Add this transaction to the back stack
                .addToBackStack(null)
                .commit();
    }
}
