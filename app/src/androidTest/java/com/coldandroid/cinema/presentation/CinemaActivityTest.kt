package com.coldandroid.cinema.presentation


import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.scrollTo
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.coldandroid.cinema.R
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@LargeTest
@RunWith(AndroidJUnit4::class)
class CinemaActivityTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(HomeActivity::class.java)

    @Test
    fun cinemaActivityTest() {
        testAppTitle()

        testFirstMovie()

        testMovieInMiddle()
    }

    private fun testAppTitle() {
        val textView = onView(
            allOf(
                withText(title_app),
                withParent(
                    allOf(
                        withId(R.id.action_bar),
                        withParent(withId(R.id.action_bar_container))
                    )
                ),
                isDisplayed()
            )
        )
        textView.check(matches(withText(title_app)))
    }

    private fun testFirstMovie() {
        val textView2 = onView(
            allOf(
                withId(R.id.title), withText(title_dilwale),
                withParent(withParent(withId(R.id.recyclerView))),
                isDisplayed()
            )
        )
        textView2.check(matches(withText(title_dilwale)))

        val textView3 = onView(
            allOf(
                withId(R.id.description),
                withText(desc_dilwale),
                withParent(withParent(withId(R.id.recyclerView))),
                isDisplayed()
            )
        )
        textView3.check(matches(withText(desc_dilwale)))
    }

    private fun testMovieInMiddle() {
        scrollToItemWithText(title_parasite)
        onView(withText(title_parasite)).check(matches(isDisplayed()))
    }

    private fun scrollToItemWithText(text: String) {
        // Attempt to scroll to an item that contains the special text.
        onView(withId(R.id.recyclerView))
            .perform(
                scrollTo<RecyclerView.ViewHolder>(
                    hasDescendant(withText(text))
                )
            )
    }

    companion object {
        const val title_app= "Cinema"
        const val title_dilwale = "Dilwale Dulhania Le Jayenge"
        const val desc_dilwale = "Raj is a rich, carefree, happy-go-lucky second generation NRI. Simran is the daughter of Chaudhary Baldev Singh, who in spite of being an NRI is very strict about adherence to Indian values. Simran has left for India to be married to her childhood fiancé. Raj leaves for India with a mission at his hands, to claim his lady love under the noses of her whole family. Thus begins a saga."
        const val title_parasite = "Parasite"
    }
}
