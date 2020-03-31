/*
 * Copyright (C) 2018 TOYAMA Sumio <jun.nama@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package jp.jun_nama.samples.androidtestingbible;


import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import com.google.samples.apps.sunflower.GardenActivity;
import com.google.samples.apps.sunflower.R;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItem;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class Step6 {

    @Rule
    public ActivityTestRule<GardenActivity> mActivityTestRule = new ActivityTestRule<>(GardenActivity.class);

    @Test
    public void step6() {
        goPlantList();

        String plantName = "Avocado";
        showPlantDetail(plantName);

        assertAddButtonVisibility(true);

        addToMyGarden();

        assertAddButtonVisibility(false);

        goBackPlantList();

        goBackMyGarden();

        String plantName2 = "Avocado";
        assertPlanted(plantName2);
    }

    private void assertPlanted(String plantName) {
        ViewInteraction textView = onView(
                allOf(withId(R.id.plant_date), withText(Matchers.startsWith(plantName)),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.garden_list),
                                        0),
                                1)));
        textView.check(matches(isDisplayed()));
    }

    private void addToMyGarden() {
        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.fab),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.garden_nav_fragment),
                                        0),
                                2),
                        isDisplayed()));
        floatingActionButton.perform(click());
    }

    private void goBackMyGarden() {
        ViewInteraction appCompatImageButton3 = onView(
                allOf(withContentDescription("上へ移動"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withId(R.id.appbar),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton3.perform(click());
    }

    private void goBackPlantList() {
        ViewInteraction appCompatImageButton2 = onView(
                allOf(withContentDescription("上へ移動"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withId(R.id.appbar),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton2.perform(click());
    }

    private void assertAddButtonVisibility(boolean visible) {
        ViewInteraction imageButton = onView(withId(R.id.fab));
        if (visible) {
            imageButton.check(matches(isDisplayed()));
        } else {
            imageButton.check(matches(not(isDisplayed())));

        }
    }

    private void showPlantDetail(String plantName) {
        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.plant_list),
                        childAtPosition(
                                withId(R.id.garden_nav_fragment),
                                0)));
        recyclerView.perform(actionOnItem(hasDescendant(withText(plantName)), click()));
    }

    private void goPlantList() {
        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("上へ移動"),
                        childAtPosition(
                                Matchers.allOf(ViewMatchers.withId(R.id.toolbar),
                                        childAtPosition(
                                                withId(R.id.appbar),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction navigationMenuItemView = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.design_navigation_view),
                                childAtPosition(
                                        withId(R.id.navigation_view),
                                        0)),
                        2),
                        isDisplayed()));
        navigationMenuItemView.perform(click());
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
