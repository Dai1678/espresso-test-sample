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


import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import com.google.samples.apps.sunflower.GardenActivity;
import jp.jun_nama.samples.androidtestingbible.pages.MyGardenPage;
import jp.jun_nama.samples.androidtestingbible.pages.PlantDetailPage;
import jp.jun_nama.samples.androidtestingbible.pages.PlantListPage;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class Step7 {

    @Rule
    public ActivityTestRule<GardenActivity> mActivityTestRule = new ActivityTestRule<>(GardenActivity.class);

    @Test
    public void step7() {
        new MyGardenPage().goPlantList();

        new PlantListPage().showPlantDetail("Avocado");

        new PlantDetailPage().assertAddButtonVisibility(true);

        new PlantDetailPage().addToMyGarden();

        new PlantDetailPage().assertAddButtonVisibility(false);

        new PlantDetailPage().goBackPlantList();

        new PlantListPage().goBackMyGarden();

        new MyGardenPage().assertPlanted("Avocado");
    }

}
