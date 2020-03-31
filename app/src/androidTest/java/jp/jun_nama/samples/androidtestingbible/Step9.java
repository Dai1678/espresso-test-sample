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
public class Step9 {

    @Rule
    public ActivityTestRule<GardenActivity> mActivityTestRule = new ActivityTestRule<>(GardenActivity.class);


    /**
     * まだ「植えていない」植物は、対応する「Plant details」画面で「＋」ボタンが表示されること
     */
    @Test
    public void plant_not_listed_in_my_garden_shows_add_button() {
        //exercise: Sunflowerの「Plant detail」画面へ行く。
        PlantDetailPage detailPage = new MyGardenPage()
                .goPlantList()
                .showPlantDetail("Sunflower");

        // verify
        detailPage.assertAddButtonVisibility(true);
    }

    /**
     * 「Plant details」画面の「＋」ボタンを押して「植えた」植物は「My garden」画面に表示されること
     */
    @Test
    public void plant_can_be_added_to_my_garden() {
        // setup: sunflowerを植えた状態にする
        PlantDetailPage plantDetail = new MyGardenPage().goPlantList()
                .showPlantDetail("Sunflower")
                .addToMyGarden();

        // exercise: 「My garden」画面に戻る
        MyGardenPage myGarden = plantDetail
                .goBackPlantList()
                .goBackMyGarden();

        // verify: sunflowerが表示されていること。
        myGarden.assertPlanted("Sunflower");
    }

    /**
     * 既に「植えた」植物は、対応する「Plant details」画面で「＋」ボタンが表示されないこと
     */
    @Test
    public void plant_can_not_be_added_to_my_garden_twice() {
        // setup: Avocadoを植えた状態にする
        PlantListPage plantList = new MyGardenPage().goPlantList()
                .showPlantDetail("Avocado")
                .addToMyGarden()
                .goBackPlantList();

        // exercise: 再度Avocadoを選ぶ
        PlantDetailPage plantDetail = plantList.showPlantDetail("Avocado");

        // verify: 植えるためのボタンが表示されないこと
        plantDetail.assertAddButtonVisibility(false);
    }
}
