package com.example.android.whitecaps;

import android.content.Context;
import android.support.test.rule.ActivityTestRule;
import android.test.AndroidTestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static android.support.test.InstrumentationRegistry.getInstrumentation;

/**
 * Created by Pramod on 12/05/17.
 */
@RunWith(JUnit4.class)
public class TakeAttendanceTest extends AndroidTestCase{

   @Rule
    public ActivityTestRule<TakeAttendance> take = new ActivityTestRule<TakeAttendance>(TakeAttendance.class);
    RoutineMgr mRoutineMgr , mMgr;
    Teacher mTeacher;
    Context context = getInstrumentation().getTargetContext().getApplicationContext();
    Subject mSubject;
    @Before
    public void setUp() throws Exception {
    mRoutineMgr = new RoutineMgr(context , mTeacher,4.4555,4.555);
     //RoutineMgr = new RoutineMgr(context , mTeacher,4.4555,4.555);
     mRoutineMgr.setSelectedStream("CSE");
     mRoutineMgr.setSelectedSemester("1");
     mRoutineMgr.setSelectedSection("A");
     mRoutineMgr.setSelectedPeriod("1");;
     mRoutineMgr.setSelectedDay("Tuesday");

     mSubject = new Subject();
     mSubject.settCode("TC");
    }

    @Test
    public  void launch()
    {
       // onView(withId(R.id.spinner1)).perform(click());

    assertEquals("Tamal Chakraborty",mRoutineMgr.getTeacherName(mSubject.gettCode()));
     assertEquals(true, mRoutineMgr.checkEmptyFields(mSubject));

     mRoutineMgr.setSelectedSection("Section");
     assertEquals(false , mRoutineMgr.checkEmptyFields(mSubject));
    }

    public void testOTP()
    {
     DigitalAttendanceMgr attendanceMgr = new DigitalAttendanceMgr();
     //attendanceMgr.attenMgr.generateOTP("CSE1011","6","Tuesday",context,mTeacher,mRoutineMgr);
     //assertEquals("1234",attendanceMgr.attenMgr.getOTP("CSE1011","6","Tuesday"));

    }

    @After
    public void tearDown() throws Exception {

    }

}