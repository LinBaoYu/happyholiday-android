package life.happyholiday.viewmodels;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import life.happyholiday.models.EventModel;

/**
 * Created by tliy916e on 16/9/17.
 */

public class HomeEventsViewModel {

    private OnResponseListener mListener;

    private List<EventModel> eventModelList;

    public HomeEventsViewModel(OnResponseListener listener) {
        mListener = listener;
    }

    public void getEvents() {
        eventModelList = new ArrayList<>();

        EventModel eventModel = new EventModel("Johor Bahru", 2, 4, new Date(), new Date());
        EventModel eventModel2 = new EventModel("Taiwan", 3, 4, new Date(), new Date());

        eventModelList.add(eventModel);
        eventModelList.add(eventModel2);

        mListener.loadEventsSuccessful(eventModelList);
    }

    public void addEvent() {
        eventModelList.add(new EventModel("Singapore", 1, 5, new Date(), new Date()));

        mListener.loadEventsSuccessful(eventModelList);
    }

    public interface OnResponseListener {
        void loadEventsSuccessful(List<EventModel> eventModelList);

        void error();
    }
}
