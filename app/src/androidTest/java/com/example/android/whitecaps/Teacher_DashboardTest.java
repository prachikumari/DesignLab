package com.example.android.whitecaps;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Pramod on 11/05/17.
 */
@RunWith(JUnit4.class)
public class Teacher_DashboardTest {
   Teacher mTeacher;
    @Rule
    public ActivityTestRule<Teacher_Dashboard> tdr = new ActivityTestRule<Teacher_Dashboard>(Teacher_Dashboard.class,true,false);
    @Before
    public void setUp() throws Exception {
     mTeacher = new Teacher("Tamal Chakraborty","tamal@gmail.com","TC","9876543210","1");
        Intent intent = new Intent();
        intent.putExtra("m",mTeacher);
        tdr.launchActivity(intent);
    }
     @Test

     public  void launch()
     {   //onView(withId(R.id.textView2)).check(matches(isNotNull()));
         onView(withId(R.id.textView2)).check(matches(withText("Tamal Chakraborty")));
         onView(withId(R.id.upload)).perform(click());


     }

    @After
    public void tearDown() throws Exception {

    }

}