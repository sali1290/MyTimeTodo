package com.example.mytimetodo.fragment

import android.app.TimePickerDialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.domain.model.Work
import com.example.mytimetodo.R
import com.example.mytimetodo.adapter.WorkColorList
import com.example.mytimetodo.adapter.WorkColorsAdapter
import com.example.mytimetodo.adapter.changeBackgroundColor
import com.example.mytimetodo.databinding.FragmentEditWorkBinding
import com.example.mytimetodo.viewmodel.HomeViewModel
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class EditWorkFragment : Fragment() {

    private var _binding: FragmentEditWorkBinding? = null
    private val binding: FragmentEditWorkBinding
        get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var adapter: WorkColorsAdapter

    private val calendar = Calendar.getInstance()
    private lateinit var work: Work

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentEditWorkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onBackPressed()
        setUpColorRecycler()
        getWorkForEdit()
        setUpOnClicks()
        observe()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getWorkForEdit() {
        setFragmentResultListener("requestKey") { _, bundle ->
            //any type that can be put in a Bundle is supported
            //maybe in future change this line to parcelable
            work = bundle.getSerializable("workKey") as Work
            //do something with the result
            binding.chbDaily.isChecked = work.time != null

            binding.etWorkTitle.setText(work.title)
            binding.etWorkBody.setText(work.body)
            binding.etWorkBody.setBackgroundColor(
                work.color.toInt()
            )
        }
    }

    private fun setUpOnClicks() {
        binding.btnCancel.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        binding.btnSave.setOnClickListener {
            val title = binding.etWorkTitle.text.toString()
            val body = binding.etWorkBody.text.toString()
            val isRoutine = binding.chbDaily.isChecked
            var date: Date?


            if (title.isEmpty()) {
                binding.etWorkTitle.error = "Work Title shouldn't be empty"
            } else {
                if (body.isEmpty()) {
                    binding.etWorkBody.error = "Work body shouldn't be empty"
                } else {
                    //check if work is daily or other
                    if (isRoutine && work.time != null) {
                        //daily works
                        //show TimePickerDialog
                        TimePickerDialog(
                            requireActivity(),
                            { _, hourOfDay, minute ->
                                calendar.set(
                                    calendar.get(Calendar.YEAR),
                                    calendar.get(Calendar.MONTH),
                                    calendar.get(Calendar.DAY_OF_MONTH),
                                    hourOfDay,
                                    minute
                                )
                                date = calendar.time


                                viewModel.updateWork(
                                    Work(
                                        id = work.id,
                                        title = title,
                                        body = body,
                                        time = date,
                                        color = (binding.etWorkBody.background as (ColorDrawable)).color.toString()
                                    )
                                )
                                //navigate to daily routine fragment and pop this fragment
//                                successfulWorkEdit()
                            },
                            calendar.get(Calendar.HOUR_OF_DAY),
                            calendar.get(Calendar.MINUTE),
                            true
                        ).show()
                    } else {
                        //other works
                        viewModel.updateWork(
                            Work(
                                id = work.id,
                                title = title,
                                body = body,
                                time = null,
                                color = (binding.etWorkBody.background as (ColorDrawable)).color.toString()
                            )
                        )
                        //navigate to daily routine fragment and pop this fragment
//                        successfulWorkEdit()
                    }
                }
            }
        }

    }

    private fun setUpColorRecycler() {
        //set first color of list to EditText
        binding.etWorkBody.setBackgroundColor(
            ContextCompat.getColor(
                requireContext(),
                WorkColorList.colorList[0]
            )
        )

        adapter = WorkColorsAdapter(requireContext(), WorkColorList.colorList)
        adapter.changeBackgroundColor(requireActivity(), binding.etWorkBody)
        binding.recyclerColors.adapter = adapter
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

    private fun observe() {

        viewModel.updateResult.observe(viewLifecycleOwner) {
            if (it) {
                successfulWorkEdit()
            } else {
                Toast.makeText(
                    requireActivity(),
                    "Something went wrong! please try again",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }


    }

    private fun successfulWorkEdit() {
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
        if (binding.chbDaily.isChecked) {
            findNavController().navigate(R.id.dailyRoutineFragment)
        } else {
            findNavController().navigate(R.id.otherWorksFragment)
        }

    }

}