package com.example.mytimetodo.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import com.example.domain.model.Work
import com.example.mytimetodo.adapter.WorkColorList
import com.example.mytimetodo.adapter.WorkColorsAdapter
import com.example.mytimetodo.adapter.changeBackgroundColor
import com.example.mytimetodo.databinding.FragmentEditWorkBinding
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
        getWorkForEdit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getWorkForEdit() {
        setFragmentResultListener("requestKey") { _, bundle ->
            //any type that can be put in a Bundle is supported
            //maybe in future change this line to parcelable
            val work = bundle.getSerializable("workKey") as Work
            //do something with the result
            binding.etWorkTitle.setText(work.title)
            binding.etWorkBody.setText(work.body)
            binding.etWorkBody.setBackgroundColor(
                work.color.toInt()
            )
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
        binding.recyclerEditWorkColor.adapter = adapter
    }

}