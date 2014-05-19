package com.zsiegel.gdg.rx.app.fragment;

import android.app.Fragment;
import android.os.Bundle;
import com.zsiegel.gdg.rx.app.RXGDGApp;
import com.zsiegel.gdg.rx.app.service.AppService;

import javax.inject.Inject;

/**
 * @author zsiegel (zac.s@akta.com)
 */
public class MainFragment extends Fragment {

    public static final String TAG = MainFragment.class.getName();

    @Inject AppService appService;

    public MainFragment() {
        super();
        setRetainInstance(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RXGDGApp.get().inject(this);
    }
}
