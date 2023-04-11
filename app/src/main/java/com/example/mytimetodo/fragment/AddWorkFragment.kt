package com.example.mytimetodo.fragment

import android.app.TimePickerDialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.domain.model.Work
import com.example.mytimetodo.R
import com.example.mytimetodo.adapter.WorkColorList
import com.example.mytimetodo.adapter.WorkColorsAdapter
import com.example.mytimetodo.adapter.changeBackgroundColor
import com.example.mytimetodo.databinding.FragmentAddWorkBinding
import com.example.mytimetodo.utility.customOnBackPressed
import com.example.mytimetodo.utility.showTopSnackBar
import com.example.mytimetodo.viewModel.HomeViewModel
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class AddWorkFragment : Fragment() {

    private var _binding: FragmentAddWorkBinding? = null
    private val binding: FragmentAddWorkBinding
        get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    private lateinit var adapter: WorkColorsAdapter
    private val calendar = Calendar.getInstance()

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

        setUpColorRecycler()
        setUpOnClickListeners()
        requireActivity().customOnBackPressed(viewLifecycleOwner)
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
            saveWork()
        }
    }

    private fun saveWork() {
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
                if (isRoutine) {
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
                        },
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        true
                    ).show()
                } else {
                    //other works
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

    private fun successfulWorkSave() {
        requireActivity().showTopSnackBar(binding.btnSave, "Work saved")

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


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}