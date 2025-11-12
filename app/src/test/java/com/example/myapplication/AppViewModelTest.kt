package com.example.myapplication

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Rule
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class AppViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun setUsername_updatesLiveDataValue() {
        val vm = AppViewModel()
        vm.setUsername("Matt")
        assertEquals("Matt", vm.username.value)
    }
}