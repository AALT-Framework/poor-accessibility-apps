# poor-accessibility-apps
Set of apps samples to improve accessibility

This repository is a partial fork of [basic-android-accessibility](https://github.com/googlecodelabs/android-accessibility), just adding test project folder to the Counter app. 

Follow the instructions to add automated accessibility tests for the Counter app:

1 - Clone this repository and open Counter app in Android Studio
2 - Add it in your root build.gradle at the end of repositories:
```
allprojects {
  repositories {
	...
	maven { url 'https://jitpack.io' }
  }
}
```
3 - Change app build.gradle to set testOptions and add test dependencies for Roboletric and AATK and sync 
```
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
4 - Create your own Java Test File class (or edit MainActivityTest), and set up like this:
```java
@RunWith(RobolectricTestRunner.class)
public class MainActivityTest {
    private View rootView;
    private AccessibilityTestRunner runner;

    @Rule
    public ErrorCollector collector = new ErrorCollector();

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setUp() {
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).create().get();

        // Get the root node of the view hierarchy
        rootView = activity.getWindow().getDecorView().getRootView();
        runner = new AccessibilityTestRunner(collector);
    }
}
```
5 - Write your first test, for example, to test contrast ratio
```java
  @Test
    public void mustUseAdequateContrastRatio(){
        runner.runAccessibilityTest(rootView, new TestAdequateContrastRatio());
  }
```

See the documentation of [Automated Accessibility Test Kit (AATK)](https://github.com/andersongarcia/android-accessibility-test-kit) to know all available tests.
