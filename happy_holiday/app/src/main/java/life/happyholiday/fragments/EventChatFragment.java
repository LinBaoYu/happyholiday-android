package life.happyholiday.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stfalcon.chatkit.messages.MessageInput;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;

import java.util.Date;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.Sort;
import life.happyholiday.R;
import life.happyholiday.models.Author;
import life.happyholiday.models.EventModel;
import life.happyholiday.models.Message;
import life.happyholiday.models.RealmDataHelper;
import life.happyholiday.utils.ColorConfigHelper;

public class EventChatFragment extends Fragment{
    @BindView(R.id.messagesList)
    MessagesList mMessagesList;
    @BindView(R.id.input)
    MessageInput mInputView;

    private MessagesListAdapter<Message> mAdapter;
    private Realm realm;
    private EventModel mEvent;

    public EventChatFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment HomeEventsFragment.
     */
    public static EventChatFragment newInstance(int eventId) {
        EventChatFragment fragment = new EventChatFragment();
        Bundle args = new Bundle();
        args.putInt("EVENT_ID", eventId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realm = Realm.getDefaultInstance();

        // retrieve Event
        if (getArguments() != null) {
            mEvent = realm.where(EventModel.class).equalTo("id", getArguments().getInt("EVENT_ID", -1)).findFirst();
            if (mEvent == null) getActivity().finish(); // Exit if no Event data
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event_chat, container, false);
        ButterKnife.bind(this, view);

//        view.setBackgroundColor(ColorConfigHelper.getChatBgColor(getContext()));

        // ********** Second param is imageLoader, null make avatar invisible
        mAdapter = new MessagesListAdapter<>("0", null);
        mAdapter.addToEnd(mEvent.getMessages().sort("createdAt", Sort.DESCENDING), false);
        mMessagesList.setAdapter(mAdapter);

        mInputView.setInputListener(new MessageInput.InputListener() {
            @Override
            public boolean onSubmit(CharSequence input) {
                //validate and send message
                String id = new Random().nextInt(2) + "";
                checkAndCreateAuthor(id);
                Author author = realm.where(Author.class).equalTo("id", id).findFirst();
                Message message = new Message(input.toString().trim(), author, new Date());
                addMessage(message);
                mAdapter.addToStart(message, true);
                return true;
            }
        });

        return view;
    }

    /*
     * It is good practice to null the reference from the view to the adapter when it is no longer needed.
     * Because the <code>RealmRecyclerViewAdapter</code> registers itself as a <code>RealmResult.ChangeListener</code>
     * the view may still be reachable if anybody is still holding a reference to the <code>RealmResult>.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    private void checkAndCreateAuthor(String id) {
        RealmDataHelper.checkAndCreateAuthor(realm, id);
    }

    private void addMessage(Message message) {
        RealmDataHelper.addMessage(realm, mEvent, message);
    }
}
