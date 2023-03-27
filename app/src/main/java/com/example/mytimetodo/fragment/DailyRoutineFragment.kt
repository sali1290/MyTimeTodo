package com.example.mytimetodo.fragment

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.mytimetodo.adapter.DailyRoutineAdapter
import com.example.mytimetodo.databinding.FragmentDailyRoutineBinding
import com.example.mytimetodo.utility.Result
import com.example.mytimetodo.viewmodel.HomeViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DailyRoutineFragment : Fragment() {

    private var _binding: FragmentDailyRoutineBinding? = null
    private val binding: FragmentDailyRoutineBinding
        get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDailyRoutineBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onBackPressed()
        viewModel.getDailyRoutineWorks()
        observe()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().finish()
                }
            })
    }

    private fun observe() {
        viewModel.dailyWorks.observe(viewLifecycleOwner) {
            when (it) {

                is Result.Success -> {
                    if (it.data.isEmpty()) {
                        binding.apply {
                            recyclerDailyRoutine.visibility = View.GONE
                            tvEmpty.visibility = View.VISIBLE
                        }
                    } else {
                        val adapter = DailyRoutineAdapter(it.data)
                        binding.recyclerDailyRoutine.adapter = adapter
                    }
                }

                is Result.Loading -> {

                }

                is Result.Error -> {
                    val snackBar = Snackbar.make(
                        requireContext(),
                        binding.tvEmpty,
                        it.message,
                        Snackbar.LENGTH_SHORT
                    )
                    (snackBar.view.layoutParams as (FrameLayout.LayoutParams)).gravity =
                        Gravity.TOP
                    snackBar.setAction("Ok") {
                        snackBar.dismiss()
                    }
                    snackBar.show()


                    binding.apply {
                        recyclerDailyRoutine.visibility = View.GONE
                        tvEmpty.visibility = View.VISIBLE
                    }
                }

            }
        }
    }

}