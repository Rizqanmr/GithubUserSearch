package com.rizqanmr.githubusersearch

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario.launch
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.pressImeActionButton
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.rizqanmr.githubusersearch.presentation.main.MainActivity
import com.rizqanmr.githubusersearch.presentation.userdetail.UserDetailActivity
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class SearchFlowTest {

    @Before
    fun setup() {
        Intents.init()
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    @Test
    fun searchUser_showList_and_openDetail() {

        // Launch MainActivity
        launch(MainActivity::class.java)

        // 1. Klik icon search di toolbar
        onView(withId(R.id.menu_search))
            .perform(click())

        // 2. Input text ke SearchView
        onView(withId(androidx.appcompat.R.id.search_src_text))
            .perform(
                typeText("john"),
                pressImeActionButton()
            )

        // 3. Loading muncul
        onView(withId(R.id.layoutLoading))
            .check(matches(isDisplayed()))

        // 4. Tunggu data masuk (sementara)
        Thread.sleep(2500)

        // 5. RecyclerView tampil
        onView(withId(R.id.rvUserResults))
            .check(matches(isDisplayed()))

        // 6. Klik item pertama
        onView(withId(R.id.rvUserResults))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0,
                    click()
                )
            )

        // 7. Verifikasi pindah ke DetailActivity
        Intents.intended(
            hasComponent(UserDetailActivity::class.java.name)
        )
    }
}