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
import com.example.mytimetodo.alarmScheduler.AndroidAlarmScheduler
import com.example.mytimetodo.databinding.FragmentDailyRoutineWorksBinding
import com.example.mytimetodo.utility.Result
import com.example.mytimetodo.utility.showTopSnackBar
import com.example.mytimetodo.viewmodel.HomeViewModel
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import java.util.Date


@AndroidEntryPoint
class DailyRoutineWorksFragment : Fragment() {

    private var _binding: FragmentDailyRoutineWorksBinding? = null
    private val binding: FragmentDailyRoutineWorksBinding
        get() = _binding!!

    private lateinit var adapter: WorksAdapter

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDailyRoutineWorksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpOnClickListeners()
        onBackPressed()
        viewModel.getDailyRoutineWorks()
        observe()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUpOnClickListeners() {
        binding.btnSortByColor.setOnClickListener {
            viewModel.getSortedWorksDailyByColor()
        }

        binding.btnSortByTitle.setOnClickListener {
            viewModel.getSortedDailyWorksByTitle()
        }

        binding.btnSortByTime.setOnClickListener {
            viewModel.getSortedDailyWorksByTime()
        }
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
                        adapter = WorksAdapter(it.data, requireActivity())
                        binding.recyclerDailyRoutine.adapter = adapter
                        setUpRecyclerAdapterOnClickListener(adapter)
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
                        recyclerDailyRoutine.visibility = View.GONE
                        tvEmpty.visibility = View.VISIBLE
                    }
                }

            }
        }
    }

    private fun setUpRecyclerAdapterOnClickListener(adapter: WorksAdapter) {

        val fab: FloatingActionButton = requireActivity().findViewById(R.id.fab)
        val bottomAppBar: BottomAppBar = requireActivity().findViewById(R.id.bottom_app_bar)

        val scheduler = AndroidAlarmScheduler(requireActivity())

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

        adapter.setOnSwitchCheckedChangeListener(object :
            WorksAdapter.OnSwitchCheckedChangeListener {
            override fun onChecked(position: Int, work: Work, isChecked: Boolean, date: Date) {
                if (isChecked) {
                    scheduler.schedule(work)
                    viewModel.updateWork(
                        Work(
                            id = work.id,
                            title = work.title,
                            body = work.body,
                            time = date,
                            color = work.color,
                            isAlarmSet = true
                        )
                    )
                    observeUpdateResult(adapter, position)
                } else {
                    scheduler.cancel(work)
                    viewModel.updateWork(
                        Work(
                            id = work.id,
                            title = work.title,
                            body = work.body,
                            time = date,
                            color = work.color,
                            isAlarmSet = false
                        )
                    )
                    observeUpdateResult(adapter, position)
                }
            }
        })

    }

    private fun observeDeleteResult(adapter: WorksAdapter, position: Int) {
        viewModel.deleteResult.observe(viewLifecycleOwner) {
            if (it) {
                adapter.notifyItemRemoved(position)
                viewModel.getDailyRoutineWorks()
            } else {
                Toast.makeText(
                    requireActivity(),
                    "Something went wrong! please try again",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun observeUpdateResult(adapter: WorksAdapter, position: Int) {
        viewModel.deleteResult.observe(viewLifecycleOwner) {
            if (it) {
                adapter.notifyItemChanged(position)
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