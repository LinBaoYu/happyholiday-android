package life.happyholiday.models;

import android.support.annotation.NonNull;

import java.util.Random;

import io.realm.Realm;
import timber.log.Timber;

/**
 * CRUD for Realm
 *
 * Created by liuyang on 10/7/2017.
 */

public class RealmDataHelper {

    // Home event
    public static void addOrUpdateEvent(Realm realm, final EventModel event) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                // temp id
                if (event.getId() == -1) event.setId((int) System.currentTimeMillis());

                // random a attending count for now
                event.setAttendingCount(new Random().nextInt(event.getVacancy()));

                realm.copyToRealmOrUpdate(event);
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
    public static void addOrUpdateActivity(Realm realm, final ActivityModel act, final EventModel eventModel) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                // set sequence for new activity
                if (act.getSequence() < 0) {
                    Number maxSequenceNumber = eventModel.getActivities().max("sequence");
                    act.setSequence(maxSequenceNumber == null ? 0 : maxSequenceNumber.intValue() + 1);
                }

                // temp id
                if (act.getId() == -1) { // new Activity
                    act.setId((int) System.currentTimeMillis());
                    eventModel.getActivities().add(act);
                } else {
                    realm.copyToRealmOrUpdate(act);
                }
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
