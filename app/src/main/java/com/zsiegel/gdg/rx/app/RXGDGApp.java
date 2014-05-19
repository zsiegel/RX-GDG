package com.zsiegel.gdg.rx.app;

import android.app.Application;
import com.zsiegel.gdg.rx.app.module.AppModule;
import dagger.ObjectGraph;

/**
 * @author zsiegel (zac.s@akta.com)
 */
public class RXGDGApp extends Application {

    private static ObjectGraph objectGraph;
    private static RXGDGApp appContext;

    private AppModule appModule;

    public RXGDGApp() {
        super();

        appContext = (RXGDGApp) getApplicationContext();
        appModule = new AppModule(this);
        objectGraph = ObjectGraph.create(appModule);
    }

    public static RXGDGApp get() {
        return appContext;
    }

    public void inject(Object object) {
        objectGraph.inject(object);
    }
}
