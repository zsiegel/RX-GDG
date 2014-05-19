package com.zsiegel.gdg.rx.app.module;

import android.app.Application;
import com.zsiegel.gdg.rx.app.fragment.MainFragment;
import com.zsiegel.gdg.rx.app.service.ApiService;
import com.zsiegel.gdg.rx.app.service.AppService;
import com.zsiegel.gdg.rx.app.service.AuthService;
import com.zsiegel.gdg.rx.app.service.DatabaseService;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

/**
 * @author zsiegel (zac.s@akta.com)
 */
@Module(injects = { MainFragment.class })
public class AppModule {

    private Application application;

    public AppModule(Application application) {
        super();
        this.application = application;
    }

    /**
     * @return the main service the application will talk to
     */
    @Provides
    @Singleton
    AppService appService(ApiService apiService, AuthService authService, DatabaseService databaseService) {
        return new AppService(apiService, authService, databaseService);
    }

    /**
     * @return an api service for talking to the network
     */
    @Provides
    @Singleton
    ApiService apiService() {
        return new ApiService();
    }

    /**
     * @return an authentication service
     */
    @Provides
    @Singleton
    AuthService authService() {
        return new AuthService();
    }

    /**
     * @return a database service
     */
    @Provides
    @Singleton
    DatabaseService databaseService() {
        return new DatabaseService();
    }
}
