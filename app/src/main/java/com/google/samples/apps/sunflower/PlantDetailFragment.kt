/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.samples.apps.sunflower

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.core.app.ShareCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.google.samples.apps.sunflower.databinding.FragmentPlantDetailBinding
import com.google.samples.apps.sunflower.utilities.InjectorUtils
import com.google.samples.apps.sunflower.viewmodels.PlantDetailViewModel

/**
 * A fragment representing a single Plant detail screen.
 */
class PlantDetailFragment : Fragment() {

    private lateinit var shareText: String

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val plantId = PlantDetailFragmentArgs.fromBundle(arguments).plantId

        val factory = InjectorUtils.providePlantDetailViewModelFactory(requireActivity(), plantId)
        val plantDetailViewModel = ViewModelProvider(this, factory)
                .get(PlantDetailViewModel::class.java)

        val binding = DataBindingUtil.inflate<FragmentPlantDetailBinding>(
                inflater, R.layout.fragment_plant_detail, container, false).apply {
            viewModel = plantDetailViewModel
            lifecycleOwner = this@PlantDetailFragment
            fab.setOnClickListener { view ->
                plantDetailViewModel.addPlantToGarden()
                Snackbar.make(view, R.string.added_plant_to_garden, Snackbar.LENGTH_LONG).show()
            }
        }

        plantDetailViewModel.plant.observe(viewLifecycleOwner, Observer { plant ->
            shareText = if (plant == null) {
                ""
            } else {
                getString(R.string.share_text_plant, plant.name)
            }
        })

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_plant_detail, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_share -> {
                val shareIntent = ShareCompat.IntentBuilder.from(requireActivity())
                        .setText(shareText)
                        .setType("text/plain")
                        .createChooserIntent()
                        .apply { addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT or Intent.FLAG_ACTIVITY_MULTIPLE_TASK) }
                startActivity(shareIntent)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {

        /**
         * The fragment argument representing the item ID that this fragment
         * represents.
         */
        const val ARG_ITEM_ID = "item_id"

        /**
         * Create a new instance of PlantDetailFragment, initialized with a plant ID.
         */
        fun newInstance(plantId: String): PlantDetailFragment {

            // Supply plant ID as an argument.
            val bundle = Bundle().apply { putString(ARG_ITEM_ID, plantId) }
            return PlantDetailFragment().apply { arguments = bundle }
        }
    }
}
