package com.zsiegel.gdg.rx.app.fragment;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.zsiegel.gdg.rx.app.R;
import com.zsiegel.gdg.rx.app.RXGDGApp;
import com.zsiegel.gdg.rx.app.activity.MainActivity;
import com.zsiegel.gdg.rx.app.model.Note;
import com.zsiegel.gdg.rx.app.service.AppService;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

import javax.inject.Inject;

/**
 * @author zsiegel (zac.s@akta.com)
 */
public class MainFragment extends Fragment {

    public static final String TAG = MainFragment.class.getName();

    @Inject AppService appService;

    private ProgressDialog progressDialog;

    private Subscription subscription = Subscriptions.empty();
    private Observable<Note> notesObservable;

    public MainFragment() {
        super();
        setRetainInstance(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RXGDGApp.get().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
        subscription.unsubscribe();
        dismissProgressDialog();
    }

    /**
     * Creates and starts our observable and cache the response for when we rotate
     */
    @OnClick(R.id.start_work)
    public void startWork() {

        notesObservable = appService.getNotes()
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .cache();

        subscribe();
    }

    /**
     * Resets our cached observable and does all of the work over again
     */
    @OnClick(R.id.start_over)
    public void startOver() {
        notesObservable = null;
        startWork();
    }

    /**
     * If an observable has been made be sure to subscribe to the work it does
     */
    private void subscribe() {
        if (notesObservable != null) {
            showProgressDialog();
            subscription = notesObservable.subscribe(new Subscriber<Note>() {
                @Override
                public void onCompleted() {
                    Log.d(TAG, "Our work is done here");
                    dismissProgressDialog();
                }

                @Override
                public void onError(Throwable e) {
                    Log.e(TAG, "An error occurred", e);
                    dismissProgressDialog();
                }

                @Override
                public void onNext(Note note) {
                    Log.d(TAG, "Update UI with note id " + note.getId());
                }
            });
        }
    }

    private void showProgressDialog() {
        progressDialog = ProgressDialog.show(getActivity(), "Please Wait", "I am working really hard for you", true, false);
    }

    private void dismissProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        notesObservable = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
