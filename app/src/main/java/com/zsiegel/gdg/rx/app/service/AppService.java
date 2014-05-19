package com.zsiegel.gdg.rx.app.service;

/**
 * @author zsiegel (zac.s@akta.com)
 */
public class AppService {

    private ApiService apiService;
    private AuthService authService;
    private DatabaseService databaseService;

    public AppService(ApiService apiService, AuthService authService, DatabaseService databaseService) {
        super();
    }
}
