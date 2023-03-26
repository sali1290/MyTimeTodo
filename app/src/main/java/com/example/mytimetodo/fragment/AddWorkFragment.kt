package com.example.mytimetodo.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mytimetodo.adapter.AddWorkColorsAdapter
import com.example.mytimetodo.adapter.WorkColorList
import com.example.mytimetodo.databinding.FragmentAddWorkBinding

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

        adapter = AddWorkColorsAdapter(requireContext(), WorkColorList.colorList)
        binding.recyclerAddWorkColor.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}