package life.happyholiday.viewmodels;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import life.happyholiday.models.ActivityModel;

/**
 * ViewModel for Activities in event details
 *
 * Created by tliy916e on 16/9/17.
 */

public class EventActivitiesViewModel {

    private OnResponseListener mListener;

    private List<ActivityModel> activityModelList;

    public EventActivitiesViewModel(OnResponseListener listener) {
        mListener = listener;
    }

    public void getActivities() {
        activityModelList = new ArrayList<>();

        ActivityModel act1 = new ActivityModel("Breakfast: din tai fung", "desc", "@JBCC", new Date(), new Date(), 3, 2);
        ActivityModel act2 = new ActivityModel("Transformer 8", "desc2", "@KL", new Date(), new Date(), 2, 2);

        activityModelList.add(act1);
        activityModelList.add(act2);

        mListener.loadActivitiesSuccessful(activityModelList);
    }

    public void addActivity() {
        activityModelList.add(new ActivityModel("McDonalds", "eat whata?", "McDonalds", new Date(), new Date(), 0, 10));

        mListener.loadActivitiesSuccessful(activityModelList);
    }

    public void deleteActivity(ActivityModel act) {
        activityModelList.remove(act);

        mListener.deleteActivitySuccessful();
    }

    public interface OnResponseListener {
        void loadActivitiesSuccessful(List<ActivityModel> activityModelList);

        void deleteActivitySuccessful();

        void error();
    }
}
