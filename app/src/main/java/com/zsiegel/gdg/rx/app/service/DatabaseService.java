package com.zsiegel.gdg.rx.app.service;

import android.util.Log;
import com.zsiegel.gdg.rx.app.ThreadUtils;
import com.zsiegel.gdg.rx.app.model.Note;
import rx.Observable;
import rx.Subscriber;

/**
 * A database service to simulate saving data to the database
 *
 * @author zsiegel (zac.s@akta.com)
 */
public class DatabaseService {

    public static final String TAG = DatabaseService.class.getName();

    public Observable<Note> save(final Note note) {
        return Observable.create(new Observable.OnSubscribe<Note>() {
            @Override
            public void call(Subscriber<? super Note> subscriber) {

                ThreadUtils.errorOnUIThread();

                Log.d(TAG, "Saving note with id " + note.getId());

                //Hard work below!
                note.save();

                //Notify the subscriber
                subscriber.onNext(note);
                subscriber.onCompleted();
            }
        });
    }
}
