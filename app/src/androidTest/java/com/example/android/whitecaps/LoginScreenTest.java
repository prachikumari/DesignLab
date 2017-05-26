package com.example.android.whitecaps;

import android.content.Context;
import android.support.test.espresso.intent.rule.IntentsTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.Assert.assertEquals;

/**
 * Created by Pramod on 16/05/17.
 */
@RunWith(JUnit4.class)
public class LoginScreenTest {
    LoginMgr lmr;
    Context context = getInstrumentation().getTargetContext().getApplicationContext();
    @Rule
    public IntentsTestRule<LoginScreen>latr = new IntentsTestRule<LoginScreen>(LoginScreen.class);
    @Before
    public void setUp() throws Exception {
    lmr = new LoginMgr();
        lmr.setTable("teacher");
    }
@Test
public  void launch()
{
    onView(withId(R.id.editText_user)).perform(typeText("tamal@gmail.com"),closeSoftKeyboard());
    onView(withId(R.id.editText_password)).perform(typeText("tamal123"),closeSoftKeyboard());
//    lmr.login(context);
    onView(withId(R.id.button_login)).perform(click());
    lmr.setEmailid("tamal@gmail.com");
    lmr.setPassword("tamal123");
    lmr.setTable("teacher");

    assertEquals("tamal@gmail.com",lmr.getEmailid());
    //intended(hasComponent(new ComponentName(getTargetContext(), Teacher_Dashboard.class)));

}    @After
    public void tearDown() throws Exception {

    }

}