package com.example.android.whitecaps;

import android.content.ComponentName;
import android.content.Intent;
import android.support.test.espresso.intent.rule.IntentsTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

/**
 * Created by Pramod on 15/05/17.
 */
@RunWith(JUnit4.class)
public class GiveAttendanceTest {
   Student mStudent;
   @Rule
   public IntentsTestRule<GiveAttendance> gatr = new IntentsTestRule<GiveAttendance>(GiveAttendance.class,true,false);
    @Before
    public void setUp() throws Exception {
     mStudent= new Student("1","1040113001","1","Ayush","ayush123@gmail.com","9876543212");
        Intent intent = new Intent();
        intent.putExtra("student",mStudent);
        intent.putExtra("latitude",4.55);
        intent.putExtra("longitude",4.56);
        gatr.launchActivity(intent);
    }
@Test
public  void launch()

{   // onView(withId(R.id.otpvalue)).perform(typeText("56"));
    //onView(withId(R.id.submit)).perform(click());


   // onView(withText("Check OTP")).inRoot(withDecorView(not(is(gatr.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
    onView(withId(R.id.otpvalue)).perform(typeText("5678"));
    onView(withId(R.id.submit)).perform(click());
    onView(withText("Attendance Taken Successfully. Thank you!")).inRoot(withDecorView(not(is(gatr.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
    intended(hasComponent(new ComponentName(getTargetContext(), Student_Dashboard.class)));
}    @After
    public void tearDown() throws Exception {

    }

}