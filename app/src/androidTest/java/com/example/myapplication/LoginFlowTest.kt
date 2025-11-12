package com.example.myapplication

import android.view.View
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginFlowTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun typingEmailAndPasswordAndClickingLogin_worksAsExpected() {
        // Type text in the email field
        onView(withId(R.id.etEmail))
            .perform(typeText("student@stclaircollege.ca"), closeSoftKeyboard())
        // Type text in the password field
        onView(withId(R.id.etPassword))
            .perform(typeText("password123"), closeSoftKeyboard())
        // Click the login button
        onView(withId(R.id.btnLogin)).perform(click())
        // Wait briefly for fragment hierarchy to stabilize
        onView(isRoot()).perform(waitFor(500))
        // Verify that the fragment and login button are visible
        onView(withId(R.id.loginLayout)).check(matches(isDisplayed()))
        onView(withId(R.id.btnLogin)).check(matches(isDisplayed()))
    }
    /**
     * Helper to safely pause Espresso for a given duration while keeping the main thread active.
     * This avoids race conditions when fragments are loading or transitioning.
     */
    private fun waitFor(millis: Long): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> = isRoot()
            override fun getDescription() = "Wait for $millis milliseconds."
            override fun perform(uiController: UiController, view: View?) {
                uiController.loopMainThreadForAtLeast(millis)
            }
        }
    }
}
