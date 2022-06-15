## Robolectric

Running tests on an Android emulator or device is slow! Building, deploying, and launching the app
often takes a minute or more. That’s no way to do TDD. There must be a better way.

Robolectric is a framework that brings fast and reliable unit tests to Android. Tests run inside the
JVM on your workstation in seconds. With Robolectric you can write tests like this:

```kotlin
@RunWith(RobolectricTestRunner::class)
class MyActivityTest {

    @Test
    fun clickingButton_shouldChangeMessage() {
        val activity = Robolectric.setupActivity(MyActivity::class.java)

        activity.button.performClick()

        assertThat(activity.message.getText(), equalTo(EqualTo("Robolectric Rocks!")))
    }
}

```

See more [here](http://robolectric.org/)

## AndroidX Test

Robolectric is intended to be fully compatible with Android’s official testing libraries since
version 4.0. As such we encourage you to try these new APIs and provide feedback. At some point the
Robolectric equivalents will be deprecated and removed. Using the AndroidX Test APIs reduces the
cognitive load for you as a developer, with just one set of APIs to learn for the same Android
concept, no matter if you are writing an Robolectric test or an instrumentation test. Furthermore it
will make your tests more portable and compatible with our future plans. 

**TestRunner**
It is now possible to use the AndroidX test runner in Robolectric tests. If you require a custom
test runner currently, please check out the new configuration and plugin API and let us know if
there are any extension points missing that you require.

See more [here](http://robolectric.org/androidx_test/)
