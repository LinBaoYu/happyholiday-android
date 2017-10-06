package life.happyholiday.adapters;

/**
 * Work as listener for ItemTouchHelper
 *
 * Created by liuyang on 10/5/2017.
 */

public interface ItemTouchHelperAdapter {

    void onItemMove(int fromPosition, int toPosition);

    void onItemDismiss(int position);
}
