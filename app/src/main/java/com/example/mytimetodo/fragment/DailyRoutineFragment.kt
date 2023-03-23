package com.example.mytimetodo.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.mytimetodo.databinding.FragmentDailyRoutineBinding
import com.example.mytimetodo.utility.Result
import com.example.mytimetodo.viewmodel.HomeViewModel

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observe() {
        viewModel.dailyWorks.observe(viewLifecycleOwner) {
            when (it) {

                is Result.Success -> {
                    Toast.makeText(requireContext(), "Success", Toast.LENGTH_LONG).show()
                }

                is Result.Loading -> {
                    Toast.makeText(requireContext(), "Loading", Toast.LENGTH_LONG).show()
                }

                is Result.Error -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                }

            }
        }
    }

}