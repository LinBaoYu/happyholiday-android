package life.happyholiday.models;

import android.support.annotation.NonNull;

import java.util.Date;

import io.realm.Realm;

/**
 * CRUD for Realm
 *
 * Created by liuyang on 10/7/2017.
 */

public class RealmDataHelper {

    // Home event
    public static void addEvent(Realm realm) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                EventModel event = new EventModel("Singapore", 1, 5, new Date(), new Date());

                event.setId((int) System.currentTimeMillis());
                realm.insert(event);
            }
        });
    }

    public static void deleteEvent(Realm realm, final EventModel event) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                event.deleteFromRealm();
            }
        });
    }

    // Event - Activities
    public static void addActivity(Realm realm) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                ActivityModel act = new ActivityModel("Singapore", "Blah blah..", "@Mall", new Date(), new Date(), 0, 0);

                act.setId((int) System.currentTimeMillis());

                Number maxSequenceNumber = realm.where(ActivityModel.class).max("sequence");
                act.setSequence(maxSequenceNumber == null ? 0 : maxSequenceNumber.intValue() + 1);

                realm.insert(act);
            }
        });
    }

    public static void deleteActivity(Realm realm, final ActivityModel activity) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                activity.deleteFromRealm();
            }
        });
    }

    public static void voteUpActivity(Realm realm, final ActivityModel activity) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                activity.setVoteUp(activity.getVoteUp() + 1);
            }
        });
    }

    public static void voteDownActivity(Realm realm, final ActivityModel activity) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                activity.setVoteDown(activity.getVoteDown() + 1);
            }
        });
    }

    public static void swapActivitySequence(Realm realm, final ActivityModel activity1, final ActivityModel activity2) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                int tempSeq = activity1.getSequence();
                activity1.setSequence(activity2.getSequence());
                activity2.setSequence(tempSeq);
            }
        });
    }
}
