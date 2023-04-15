package com.example.mytimetodo.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.domain.model.Work
import com.example.mytimetodo.R
import com.example.mytimetodo.adapter.WorksAdapter
import com.example.mytimetodo.databinding.FragmentOtherWorksBinding
import com.example.mytimetodo.utility.Result
import com.example.mytimetodo.utility.showTopSnackBar
import com.example.mytimetodo.viewmodel.HomeViewModel
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
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

        onBackPressed()
        setUpOnClickListeners()
        viewModel.getOtherWorks()
        observe()
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
        viewModel.otherWorks.observe(viewLifecycleOwner) {
            when (it) {

                is Result.Success -> {
                    if (it.data.isEmpty()) {
                        binding.apply {
                            recyclerOtherWorks.visibility = View.GONE
                            tvEmpty.visibility = View.VISIBLE
                        }
                    } else {
                        val adapter = WorksAdapter(it.data, requireActivity())
                        binding.recyclerOtherWorks.adapter = adapter
                        setUpAdapterRecyclerOnClickListener(adapter)
                    }
                }

                is Result.Loading -> {

                }

                is Result.Error -> {
                    requireActivity().showTopSnackBar(
                        binding.tvEmpty,
                        it.message
                    )
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

    private fun setUpOnClickListeners() {
        binding.btnSortByColor.setOnClickListener {
            viewModel.getSortedOtherWorksByColor()
        }

        binding.btnSortByTitle.setOnClickListener {
            viewModel.getSortedOtherWorksByTitle()
        }

        binding.btnSortByTime.setOnClickListener {
            viewModel.getSortedOtherWorksByTime()
        }
    }

    private fun setUpAdapterRecyclerOnClickListener(adapter: WorksAdapter) {

        val fab: FloatingActionButton = requireActivity().findViewById(R.id.fab)
        val bottomAppBar: BottomAppBar = requireActivity().findViewById(R.id.bottom_app_bar)

        adapter.setOnEditClickListener(object : WorksAdapter.OnEditIconClickListener {
            override fun onClick(position: Int, work: Work) {
                setFragmentResult("requestKey", bundleOf("workKey" to work))
                bottomAppBar.visibility = View.GONE
                fab.visibility = View.GONE
                findNavController().navigate(R.id.editWorkFragment)
            }
        })
        adapter.setOnDeleteClickListener(object : WorksAdapter.OnDeleteIconClickListener {
            override fun onClick(position: Int, work: Work) {
                viewModel.deleteWork(work)
                observeDeleteResult(adapter, position)
            }
        })
    }

    private fun observeDeleteResult(adapter: WorksAdapter, position: Int) {
        viewModel.deleteResult.observe(viewLifecycleOwner) {
            if (it) {
                adapter.notifyItemRemoved(position)
                viewModel.getOtherWorks()
            } else {
                Toast.makeText(
                    requireActivity(),
                    "Something went wrong! please try again",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }

}