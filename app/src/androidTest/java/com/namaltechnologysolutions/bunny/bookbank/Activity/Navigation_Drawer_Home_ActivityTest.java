package com.namaltechnologysolutions.bunny.bookbank.Activity;

import android.support.test.rule.ActivityTestRule;
import android.view.View;
import com.namaltechnologysolutions.bunny.bookbank.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import static org.junit.Assert.*;

public class Navigation_Drawer_Home_ActivityTest {

    @Rule
    public ActivityTestRule<Navigation_Drawer_Home_Activity> rule = new ActivityTestRule<>(Navigation_Drawer_Home_Activity.class);
    private Navigation_Drawer_Home_Activity navigation_drawer_home_activity;
    @Before
    public void setUp() throws Exception {
        navigation_drawer_home_activity = rule.getActivity();
    }

    @Test
    public void PostRequest_Icon_Test() {
        View view = navigation_drawer_home_activity.findViewById(R.id.postRequest_Icon);
        assertNotNull(view);
    }
    @After
    public void tearDown() throws Exception {
        navigation_drawer_home_activity=null;
    }
}