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
 *
 */
package com.bumptech.glide.load.engine.executor;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.test.espresso.idling.concurrent.IdlingThreadPoolExecutor;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;

import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

@GlideModule
public class IdlingGlideModule extends AppGlideModule {

    @SuppressLint("VisibleForTests")
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        builder.setDiskCacheExecutor(new GlideExecutor(
                new IdlingThreadPoolExecutor("GlideDiskCacheExecutor",
                        1,
                        1,
                        0,
                        TimeUnit.MICROSECONDS,
                        new PriorityBlockingQueue<>(),
                        Executors.defaultThreadFactory())
        ));
        builder.setSourceExecutor(new GlideExecutor(
                new IdlingThreadPoolExecutor("GlideSourceExecutor",
                        GlideExecutor.calculateBestThreadCount(),
                        GlideExecutor.calculateBestThreadCount(),
                        0,
                        TimeUnit.MICROSECONDS,
                        new PriorityBlockingQueue<>(),
                        Executors.defaultThreadFactory())
        ));
    }
}
