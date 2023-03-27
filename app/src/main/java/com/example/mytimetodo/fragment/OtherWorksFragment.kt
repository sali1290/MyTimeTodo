package com.example.mytimetodo.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.mytimetodo.adapter.DailyRoutineAdapter
import com.example.mytimetodo.databinding.FragmentOtherWorksBinding
import com.example.mytimetodo.utility.Result
import com.example.mytimetodo.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OtherWorksFragment : Fragment() {

    private var _binding: FragmentOtherWorksBinding? = null
    private val binding: FragmentOtherWorksBinding
        get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentOtherWorksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getOtherWorks()
        observe()
    }

    private fun observe() {
        viewModel.otherWorks.observe(viewLifecycleOwner) {
            when (it) {

                is Result.Success -> {
                    if (it.data.isEmpty()) {
                        binding.apply {
                            recyclerOtherWorks.visibility = View.GONE
                            tvEmpty.visibility = View.VISIBLE
                        }
                    } else {
                        val adapter = DailyRoutineAdapter(it.data)
                        binding.recyclerOtherWorks.adapter = adapter
                    }
                }

                is Result.Loading -> {

                }

                is Result.Error -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                    binding.apply {
                        recyclerOtherWorks.visibility = View.GONE
                        tvEmpty.visibility = View.VISIBLE
                    }
                }

            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}