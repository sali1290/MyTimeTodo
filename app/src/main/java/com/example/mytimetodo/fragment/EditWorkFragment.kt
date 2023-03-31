package com.example.mytimetodo.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.mytimetodo.adapter.WorkColorList
import com.example.mytimetodo.adapter.WorkColorsAdapter
import com.example.mytimetodo.adapter.changeBackgroundColor
import com.example.mytimetodo.databinding.FragmentEditWorkBinding
import com.example.mytimetodo.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditWorkFragment : Fragment() {

    private var _binding: FragmentEditWorkBinding? = null
    private val binding: FragmentEditWorkBinding
        get() = _binding!!

    private lateinit var adapter: WorkColorsAdapter

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

        setUpColorRecycler()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
        binding.recyclerEditWorkColor.adapter = adapter
    }

}