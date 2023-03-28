package com.example.mytimetodo.fragment

import android.app.TimePickerDialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.domain.model.Work
import com.example.mytimetodo.R
import com.example.mytimetodo.adapter.AddWorkColorsAdapter
import com.example.mytimetodo.adapter.WorkColorList
import com.example.mytimetodo.databinding.FragmentAddWorkBinding
import com.example.mytimetodo.utility.getCurrentTime
import com.example.mytimetodo.viewmodel.HomeViewModel
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class AddWorkFragment : Fragment() {

    private var _binding: FragmentAddWorkBinding? = null
    private val binding: FragmentAddWorkBinding
        get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    private lateinit var adapter: AddWorkColorsAdapter

    private lateinit var currentTime: Pair<Int, Int>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAddWorkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        currentTime = getCurrentTime()

        setUpAddColorRecycler()
        setUpOnClickListeners()
        onBackPressed()
    }

    private fun setUpAddColorRecycler() {
        adapter = AddWorkColorsAdapter(requireContext(), WorkColorList.colorList)
        adapter.setOnClickListener(object :
            AddWorkColorsAdapter.OnClickListener {
            override fun onClick(position: Int, colorId: Int) {
                binding.etWorkBody.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        colorId
                    )
                )
            }
        })
        binding.recyclerAddWorkColor.adapter = adapter
    }

    private fun setUpOnClickListeners() {
        binding.btnCancel.setOnClickListener {
            requireActivity().apply {
                findViewById<FloatingActionButton>(R.id.fab).visibility =
                    View.VISIBLE
                findViewById<BottomAppBar>(R.id.bottom_app_bar).visibility =
                    View.VISIBLE
                onBackPressedDispatcher.onBackPressed()
            }
        }

        binding.btnSave.setOnClickListener {
            val title = binding.etWorkTitle.text.toString()
            val body = binding.etWorkBody.text.toString()
            val isRoutine = binding.chbDaily.isChecked
            var date: Date? = null



            if (isRoutine) {
                TimePickerDialog(
                    requireActivity(),
                    { _, hourOfDay, minute ->
                        date = Date(hourOfDay.toLong() + minute.toLong())
                    },
                    currentTime.first,
                    currentTime.second,
                    true
                )
            }

            if (title.isEmpty()) {
                binding.etWorkTitle.error = "Work Title shouldn't be empty"
            } else {
                if (body.isEmpty()) {
                    binding.etWorkBody.error = "Work body shouldn't be empty"
                } else {
                    if (isRoutine) {
                        viewModel.addWork(
                            Work(
                                id = 0,
                                title = title,
                                body = body,
                                time = date,
                                color = (binding.etWorkBody.background as (ColorDrawable)).color.toString()
                            )
                        )
                        //navigate to daily routine fragment and pop this fragment
                        successfulWorkSave()
                    } else {
                        viewModel.addWork(
                            Work(
                                id = 0,
                                title = title,
                                body = body,
                                time = null,
                                color = (binding.etWorkBody.background as (ColorDrawable)).color.toString()
                            )
                        )
                        //navigate to daily routine fragment and pop this fragment
                        successfulWorkSave()
                    }
                }
            }
        }


    }

    private fun onBackPressed() {
        requireActivity()
            .onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    // Do custom work here
                    requireActivity().apply {
                        findViewById<FloatingActionButton>(R.id.fab).visibility =
                            View.VISIBLE
                        findViewById<BottomAppBar>(R.id.bottom_app_bar).visibility =
                            View.VISIBLE
                    }
                    // if you want onBackPressed() to be called as normal afterwards
                    if (isEnabled) {
                        isEnabled = false
                        requireActivity().onBackPressedDispatcher.onBackPressed()
                    }
                }
            }
            )
    }

    private fun successfulWorkSave() {
        val snackBar = Snackbar.make(
            requireContext(),
            binding.btnSave,
            "Work saved",
            Snackbar.LENGTH_SHORT
        )
        (snackBar.view.layoutParams as (FrameLayout.LayoutParams)).gravity =
            Gravity.TOP
        snackBar.setAction("Ok") {
            snackBar.dismiss()
        }
        snackBar.show()
        requireActivity().apply {
            findViewById<FloatingActionButton>(R.id.fab).visibility =
                View.VISIBLE
            findViewById<BottomAppBar>(R.id.bottom_app_bar).visibility =
                View.VISIBLE
        }
        findNavController().navigate(R.id.dailyRoutineFragment)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}