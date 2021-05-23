package com.emreozcan.favoritecars.view.fragments.add

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.emreozcan.favoritecars.R
import com.emreozcan.favoritecars.databinding.FragmentAddBinding
import com.emreozcan.favoritecars.view.fragments.coloradapter.ColorAdapter
import java.util.ArrayList


class AddFragment : Fragment() {
    private var _binding: FragmentAddBinding?=null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBinding.inflate(inflater,container,false)

        val colors = resources.getStringArray(R.array.colors).toCollection(ArrayList())
        val colorAdapter = ColorAdapter(requireContext(),colors)

        binding.autoCompleteTextView.setAdapter(colorAdapter)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}