# poor-accessibility-apps

This set of app samples contains a number of accessibility issues that need to be fixed.

This repository is a partial fork of [basic-android-accessibility](https://github.com/googlecodelabs/android-accessibility), with the addition of a test project folder for the Counter app.

## What to do

To add automated accessibility tests for the Counter app, follow these instructions:

1. Clone this repository and open Counter app in Android Studio.

2. Add it in your root `build.gradle` file, at the end of repositories.

```groovy
  allprojects {
    repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```

3. Configure your app-level `build.gradle` file for Robolectric and AATK testing by updating the `testOptions` and adding the necessary `dependencies`. After making these changes, sync your project to ensure they take effect.

```groovy
android{
    ...
    testOptions {
        // Used for Unit testing Android dependent elements in test folder
        unitTests.includeAndroidResources  = true
        unitTests.returnDefaultValues = true
    }
}

dependencies {
    ...
    testImplementation 'org.robolectric:robolectric:4.9'
    testImplementation 'com.github.andersongarcia:android-accessibility-test-kit:v1.0.0'
    ...
}
```

4. Create your own Java test file class (or edit `MainActivityTest`) and set it up as follows:

```java
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
```

5. To write your first test, you can start by testing the contrast ratio. Here's an example test that you can use as a starting point:

```java
  @Test
    public void mustUseAdequateContrastRatio(){
        runner.runAccessibilityTest(rootView, new TestAdequateContrastRatio());
  }
```

See the documentation of [Automated Accessibility Test Kit (AATK)](https://github.com/andersongarcia/android-accessibility-test-kit) to know all available tests.
