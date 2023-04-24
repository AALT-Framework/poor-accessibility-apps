package com.example.android.accessibility.counter;

import android.view.View;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import br.usp.icmc.ppgccmc.accessibility_tests.mars.TestAdequateContrastRatio;
import br.usp.icmc.ppgccmc.accessibility_tests.mars.TestMustHaveAlternativeText;
import br.usp.icmc.ppgccmc.accessibility_tests.mars.TestTouchTargetSize;
import br.usp.icmc.ppgccmc.accessibility_tests.runners.AccessibilityTestRunner;

@RunWith(RobolectricTestRunner.class)
public class MainActivityTest {
    private View rootView;
    private AccessibilityTestRunner runner;

    @Rule
    public ErrorCollector collector = new ErrorCollector();

    @Before
    public void setUp() {
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).create().get();

        // Get the root node of the view hierarchy
        rootView = activity.getWindow().getDecorView().getRootView();
        runner = new AccessibilityTestRunner(collector);
    }
}
