package com.zsiegel.gdg.rx.app.service;

import com.zsiegel.gdg.rx.app.model.Note;
import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import java.util.List;

/**
 * An app service for the application
 *
 * @author zsiegel (zac.s@akta.com)
 */
public class AppService {

    private ApiService apiService;
    private AuthService authService;
    private DatabaseService databaseService;

    public AppService(ApiService apiService, AuthService authService, DatabaseService databaseService) {
        super();
        this.apiService = apiService;
        this.authService = authService;
        this.databaseService = databaseService;
    }

    /**
     * An observable that does all operations needed to get a list of notes
     * <p/>
     * 1) Get an auth token
     * 2) Get the notes from the server with the auth token
     * 3) Save the notes to the database
     */
    public Observable<Note> getNotes() {

        //Get our auth token first
        return authService.getCachedToken().flatMap(new Func1<String, Observable<? extends List<Note>>>() {
            @Override
            public Observable<? extends List<Note>> call(String authToken) {

                //We use our auth token to then get the notes from the server
                return apiService.getNotes(authToken).subscribeOn(Schedulers.io());
            }
        }).flatMap(new Func1<List<Note>, Observable<? extends Note>>() {
            @Override
            public Observable<? extends Note> call(List<Note> notes) {

                //Get an array of save operations
                Observable<Note>[] saveOperations = new Observable[notes.size()];
                int idx = 0;
                for (Note note : notes) {
                    saveOperations[idx] = databaseService.save(note).subscribeOn(Schedulers.io());
                    idx++;
                }

                //Return an observable that merges and emits each save operation
                return Observable.merge(saveOperations);
            }
        });
    }
}
