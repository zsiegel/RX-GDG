package com.zsiegel.test.gdg.rx;

import android.test.InstrumentationTestCase;
import org.mockito.MockitoAnnotations;

/**
 * @author zsiegel (zac.s@akta.com)
 */
public class BaseInstrumentationTest extends InstrumentationTestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        System.setProperty("dexmaker.dexcache", getInstrumentation().getTargetContext().getCacheDir().getPath());
        MockitoAnnotations.initMocks(this);
    }
}
