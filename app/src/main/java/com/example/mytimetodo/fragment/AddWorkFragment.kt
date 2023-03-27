package com.example.mytimetodo.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.mytimetodo.R
import com.example.mytimetodo.adapter.AddWorkColorsAdapter
import com.example.mytimetodo.adapter.WorkColorList
import com.example.mytimetodo.databinding.FragmentAddWorkBinding
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddWorkFragment : Fragment() {

    private var _binding: FragmentAddWorkBinding? = null
    private val binding: FragmentAddWorkBinding
        get() = _binding!!

    private lateinit var adapter: AddWorkColorsAdapter

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


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}