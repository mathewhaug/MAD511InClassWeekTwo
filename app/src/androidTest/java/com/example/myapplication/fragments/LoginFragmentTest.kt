package com.example.myapplication.fragments

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.example.myapplication.R
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginFragmentTest {

    @Test
    fun typingEmailAndPasswordAndClickingLogin_worksAsExpected() {
        // Launch fragment inside an EmptyFragmentActivity using ours app's theme defined in the manifest
        launchFragmentInContainer<LoginFragment>(
            themeResId = com.google.android.material.R.style.Theme_Material3_DayNight_NoActionBar
        )



        // Type email and password
        onView(withId(R.id.etEmail))
            .perform(typeText("student@stclaircollege.ca"), closeSoftKeyboard())
        onView(withId(R.id.etPassword))
            .perform(typeText("password123"), closeSoftKeyboard())

        // Click login button
        onView(withId(R.id.btnLogin)).perform(click())

        // Simple assertion — confirm button still visible
        onView(withId(R.id.btnLogin)).check(matches(isDisplayed()))
    }
}
