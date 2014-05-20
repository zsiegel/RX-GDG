package com.zsiegel.gdg.rx.app.service;

import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import com.zsiegel.gdg.rx.app.ThreadUtils;
import rx.Observable;
import rx.Subscriber;

/**
 * An auth service to demonstrate a basic caching scenario
 *
 * @author zsiegel (zac.s@akta.com)
 */
public class AuthService {

    public static final String TAG = AuthService.class.getName();

    private volatile String cachedToken;

    public Observable<String> getCachedToken() {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {

                ThreadUtils.errorOnUIThread();

                if (!TextUtils.isEmpty(cachedToken)) {

                    Log.d(TAG, "Auth token found in the cache");

                    //Return the cached token
                    subscriber.onNext(cachedToken);
                } else {

                    Log.d(TAG, "Getting auth token from the server");

                    //Get the cache token - this may take awhile to verify with the server
                    SystemClock.sleep(1000);
                    setCachedToken("CACHED_TOKEN");
                    subscriber.onNext(cachedToken);
                }

                subscriber.onCompleted();
            }
        });
    }

    private void setCachedToken(String cachedToken) {
        if (TextUtils.isEmpty(cachedToken)) {
            synchronized (this) {
                if (TextUtils.isEmpty(cachedToken)) {
                    this.cachedToken = cachedToken;
                }
            }
        }
    }
}
