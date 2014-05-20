package com.zsiegel.test.gdg.rx;

import com.zsiegel.gdg.rx.app.model.Note;
import com.zsiegel.gdg.rx.app.service.ApiService;
import org.mockito.Mock;
import rx.Observable;
import rx.Observer;

import java.util.List;

import static org.mockito.Mockito.*;

/**
 * A basic test to demonstrate the ease of testing functions which are used
 * asynchronously during production but synchronously during testing
 *
 * @author zsiegel (zac.s@akta.com)
 */
public class ApiServiceTest extends BaseInstrumentationTest {

    ApiService apiService;

    @Mock private Observer<List<Note>> notesObserver;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        apiService = new ApiService();
    }

    public void testGetNotes() {

        //We get an observable - this runs in the same thread as it was created!
        Observable<List<Note>> getNotes = apiService.getNotes("TEST AUTH TOKEN");

        //We give our mocked subscriber so we can check what happened to it
        getNotes.subscribe(notesObserver);

        //Verify that we got a list of notes back
        verify(notesObserver).onNext(anyList());

        //Verify no errors occured
        verify(notesObserver, never()).onError(any(Throwable.class));

        //Verify that on complete was called when done
        verify(notesObserver).onCompleted();
    }
}
