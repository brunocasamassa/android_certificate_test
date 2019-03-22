package com.example.brunogerassicasamassa.testroompersistence;


import android.app.Activity;
import android.support.annotation.IdRes;
import android.support.annotation.UiThread;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.brunogerassicasamassa.testroompersistence.activities.MainActivity;
import com.example.brunogerassicasamassa.testroompersistence.activities.SongListActivity;
import com.google.common.collect.Iterables;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.regex.Matcher;

import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.BoundedMatcher;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import androidx.test.runner.lifecycle.Stage;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressBack;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.hasToString;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ExampleInstrumentedTest {
    private String TAG = "_ESPRESSO_TEST";

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule
            = new ActivityScenarioRule<>(MainActivity.class);


    @Rule
    public ActivityScenarioRule<SongListActivity> songActivityScenarioRule
            = new ActivityScenarioRule<>(SongListActivity.class);

    @Test
    public void test_one_user_add() {

        //clean database
        onView(withId(R.id.delete_all)).perform(click());


        onView(withId(R.id.name)).perform(typeText("User"));
        onView(withId(R.id.age)).perform(typeText("10"));
        onView(withId(R.id.mail)).perform(typeText("user@example.com"));

        onView(withId(R.id.save)).perform(click());


        onView(withId(R.id.list)).perform(click());
        onView(withId(R.id.lv_userlist)).check(ViewAssertions.matches(Matchers.withListSize(1)));

    }

    @Test
    public void test_delete_all_users() {

        onView(withId(R.id.delete_all)).perform(click());

        onView(withId(R.id.list)).perform(click());
        onView(withId(R.id.lv_userlist)).check(ViewAssertions.matches(Matchers.withListSize(0)));

    }

    @Test
    public void test_music_flow() {
        onView(withId(R.id.song)).perform(click());

        try {
            SongListActivity activity = (SongListActivity) getCurrentActivity();
            if (activity.getArrayList().size() > 0) {
                onData(hasToString(startsWith(activity.getArrayList().get(0).getArtist())))
                        .inAdapterView(withId(R.id.song_list)).atPosition(0)
                        .perform(click());

                onData(hasToString(startsWith(activity.getArrayList().get(1).getArtist())))
                        .inAdapterView(withId(R.id.song_list)).atPosition(1)
                        .perform(click());

                if (activity.getMusicPlayer().getCurrentPosition() > 0) {
                    Log.d(TAG, "music not stopped after click in another one");
                    assert false;
                } else {
                    Log.d(TAG, "PASSED");
                    Toast.makeText(getCurrentActivity(), "PASSED", Toast.LENGTH_SHORT).show();
                    assert true;
                }

            }


        } catch (Exception e) {
            e.printStackTrace();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }


    }


    Activity getCurrentActivity() throws Throwable {
        getInstrumentation().waitForIdleSync();
        final Activity[] activity = new Activity[1];

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                java.util.Collection<Activity> activities = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED);
                activity[0] = Iterables.getOnlyElement(activities);

            }
        });

        return activity[0];
    }
}

class Matchers {
    public static TypeSafeMatcher<View> withListSize(final int size) {
        return new TypeSafeMatcher<View>() {
            @Override
            public boolean matchesSafely(final View view) {
                return ((ListView) view).getCount() == size;
            }

            @Override
            public void describeTo(final Description description) {
                description.appendText("ListView should have " + size + " items");
            }
        };
    }

}
