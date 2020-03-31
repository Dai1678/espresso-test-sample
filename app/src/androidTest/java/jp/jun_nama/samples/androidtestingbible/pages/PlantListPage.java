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

package jp.jun_nama.samples.androidtestingbible.pages;

import androidx.test.espresso.ViewInteraction;
import com.google.samples.apps.sunflower.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItem;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.Matchers.allOf;

public class PlantListPage {

    public PlantDetailPage showPlantDetail(String plantName) {
        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.plant_list),
                        PageUtils.childAtPosition(
                                withId(R.id.garden_nav_fragment),
                                0)));
        recyclerView.perform(actionOnItem(hasDescendant(withText(plantName)), click()));
        return new PlantDetailPage();
    }

    public MyGardenPage goBackMyGarden() {
        ViewInteraction appCompatImageButton3 = onView(
                allOf(withContentDescription("上へ移動"),
                        PageUtils.childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        PageUtils.childAtPosition(
                                                withId(R.id.appbar),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton3.perform(click());
        return new MyGardenPage();
    }
}
