id: applying-aatk-to-counter-app
summary: This pratice involves setting up the necessary dependencies and writing accessibility tests using the AATK framework. By running these tests and analyzing the results, you can identify and fix any accessibility issues in your app.
authors: Anderson Canale Garcia
categories: Android
tags: beginner, developers, testers

# Apply the Automated Accessibility Test Kit (AATK) to the Counter App Project
<!-- ------------------------ -->

## Introduction

Duration: 1

Mobile device applications can help people perform everyday tasks. However, people with disabilities may face various barriers when using the features of these devices if they do not provide adequate accessibility.

Software developers play a crucial role in promoting digital accessibility improvements, and automated tests can help them.

The **Automated accessibility tests kit for Android apps (AATK)** consists of a collection of automated accessibility tests designed to run with Robolectric. This enables them to be executed as local tests, without the need for a physical or emulated device.

This kit was developed focusing on the most common accessibility issues and the most frequently used widgets, where many accessibility problems tend to arise.

### What You’ll Learn 
This codelab intended to lead you to:
- configure an existing project to use the AATK
- write some accessibility tests with few lines
- run tests, identifying and fixing accessibility issues

### Prerequisites
No prior knowledge of accessibility or automated testing is required to perform this codelab. However, we assume that you:

- have Android Studio installed in your development environment
- be able to download project from GitHub and open in Android Studio

<!-- ------------------------ -->
## Setting up
Duration: 5

### The Counter App
In this codelab, you'll be working with an existing app, Counter, forked from [Google Codelabs](https://developer.android.com/codelabs/starting-android-accessibility). This app allows users to track, increment, and decrement a numerical count. Even though the app is simple, you'll discover that it has some accessibility issues that make it hard for many users to properly interact with it.

We'll guide you to run thre tests from AATK to identify these issues quickly, and then fix them. Additionally, you can write and run other tests by your own.

### Clone and open project
You can get the source code for the starting version of the app from GitHub [here](https://github.com/andersongarcia/poor-accessibility-apps). Clone the repo, and open Counter in Android Studio.

<aside>
You'll work in the <strong>master</strong> branch throughout this codelab. Try to follow all steps to understand how to set up your project. If you can't do this properly, then checkout <strong>Including_AATK</strong> branch and skip to writing test.
</aside>

### Set it up to use AATK
To add automated accessibility tests for the Counter app, follow these instructions:

1. Add it in your root **build.gradle** file, at the end of repositories.

    <aside>
    <strong>Where is it?</strong> When opening the project in Android Studio, in the Android view (Left pane), there is a Gradle Scripts section. Inside, there is a file called <span style="color: blue;">build.gradle (Project: Counter)</span>.
    </aside>

    ```groovy
    allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
    }
    ```

2. Configure your app-level **build.gradle** file for Robolectric and AATK testing by updating the **testOptions** and adding the necessary **dependencies**.

    <aside>
    <strong>Where is it?</strong> When opening the project in Android Studio, in the Android view (Left pane), there is a Gradle Scripts section. Inside, there is a file called \textcolor{red}{red} <i>build.gradle (Module: Counter.app)</i>, or something like this.
    </aside>

    First, add these two lines inside **testOptions** inside **android**, like this:

    ```groovy
    android{
        ...
        testOptions {
            // Used for Unit testing Android dependent elements in test folder
            unitTests.includeAndroidResources  = true
            unitTests.returnDefaultValues = true
        }
    }
    ```

    Then, add these two `testImplementation` dependencies:

    ```groovy
    dependencies {
        ...
        testImplementation 'org.robolectric:robolectric:4.9'
        testImplementation 'com.github.andersongarcia:android-accessibility-test-kit:v1.0.0'
        ...
    }
    ```

<aside><strong>Tip:</strong> Read more about <a href="https://developer.android.com/studio/test/test-in-android-studio">Test in Android Studio</a></aside>


3. After making these changes, sync your project to ensure they take effect.
<!-- ------------------------ -->
## Create a test class for the main screen 
Duration: 5
In this task, you'll write your first accessible tests with AATK.
### Create the Test Class
1. In Android Studio, open up the Project pane and find this folder:
* **com.example.android.accessibility.counter (test)**.

2. Right click the **counter** folder and select **New** > **Java Class**

3. Name it `MainActivityTest`. So you will know that this test class refers to MainActivity.

### Set up the Test Class 
With `MainActivityTest` class generated and opened, start to set it up to run AATK tests.

1. Annotate the class scope to run with `RoboletricTestRunner`.

2. Declare private field to keep de rootView and the `AccessibilityTestRunner`.

3. Declare a public property to the `ErrorCollector`.

4. Add a **setUp** method as follow:

    ```java
    @Before
    public void setUp() {
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).create().get();

        // Get the root node of the view hierarchy
        rootView = activity.getWindow().getDecorView().getRootView();
        runner = new AccessibilityTestRunner(collector);
    }
    ```

    <aside>The method with the <strong>@Before</strong> annotation always runs before the execution of each test case. This annotation is commonly used to develop necessary preconditions for each <strong>@Test</strong> method.</aside>

5. At this point, your MainActivityTest should to look like this:

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
<!-- ------------------------ -->
## Write your first test
Duration: 5

Add a test method to each accessibility test you want to run. We'll start from the color contrast ratio check.

### Create a test to check color contrast ratio

Proper contrast helps users better identify the content of the application. A contrast ratio of at least 4.5:1 should be used.

You can run the **AATK** `TestAdequateContrastRatio` as follow:

1. Add a test method.

2. Call the method **runAccessibilityTest** from the runner, passing as parameter the root view and a new instance of desired test.

    ```java
    @Test
        public void mustUseAdequateContrastRatio(){
            runner.runAccessibilityTest(rootView, new TestAdequateContrastRatio());
    }
    ```

3. Run your test. Right click on it and select **Run MainActivityTest.mustUseAdequateContrastRatio**

4. In **Run panel**, double-click **mustUseAdequateContrastRatio** to see the the results. You'll notice the message error, the `View` identification, the expected ratio and the current ratio.

    <aside>
    <strong>What it means?</strong> In Counter, the color contrast is straightforward to improve. The TextView displaying the count uses a light grey background and a grey text color. You can remove the background, pick a lighter background, or pick a darker text color. In this codelab, you'll pick a darker text color.
    </aside>

5. Open **res/layout/activity_main.xml**, find the **TextView** and change **android:textColor="@color/grey"** to **android:textColor="@color/darkGrey"**.

    <aside>
    This colors names are a preset of this sample project. To see all colors defined, check <strong>res/values/colors.xml</strong> file.
    </aside>

6. Go back to item **3** to rerun the test and see it pass.