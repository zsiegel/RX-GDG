package com.zsiegel.gdg.rx.app.service;

import android.os.SystemClock;
import android.util.Log;
import com.zsiegel.gdg.rx.app.ThreadUtils;
import com.zsiegel.gdg.rx.app.model.Note;
import rx.Observable;
import rx.Subscriber;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * An example API service that simulates remote network calls
 *
 * @author zsiegel (zac.s@akta.com)
 */
public class ApiService {

    public static final String TAG = ApiService.class.getName();

    /**
     * @param authToken an auth token to get our notes from the server
     * @return an observable that will emit a list of notes - might be retrieved from the server
     */
    public Observable<List<Note>> getNotes(String authToken) {
        return Observable.create(new Observable.OnSubscribe<List<Note>>() {
            @Override
            public void call(Subscriber<? super List<Note>> subscriber) {

                ThreadUtils.errorOnUIThread();

                Log.d(TAG, "Getting notes from the api server");
                List<Note> newNotes = new ArrayList<Note>();
                for (int idx = 0; idx < 10; idx++) {
                    Note note = new Note();
                    note.setId(idx);
                    note.setName("Note #" + idx);
                    note.setCreatedDate(new Date());
                    newNotes.add(note);
                }

                SystemClock.sleep(1000);
                subscriber.onNext(newNotes);
                subscriber.onCompleted();
            }
        });
    }
}
