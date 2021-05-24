package com.emreozcan.favoritecars.view.fragments.home

import android.opengl.Visibility
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.emreozcan.favoritecars.R
import com.emreozcan.favoritecars.data.models.CarModel
import com.emreozcan.favoritecars.data.viewmodel.HomeFragmentViewModel
import com.emreozcan.favoritecars.databinding.FragmentHomeBinding
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import java.util.ArrayList


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding?=null
    private val binding get() = _binding!!

    private val homeFragmentViewModel: HomeFragmentViewModel by viewModels()

    private val adapter: RecyclerRowAdapter by lazy { RecyclerRowAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)

        _binding = FragmentHomeBinding.inflate(inflater,container,false)

        createRecycler()

        homeFragmentViewModel.getAllData.observe(viewLifecycleOwner, Observer {
            homeFragmentViewModel.checkDatabaseEmpty(it)
            adapter.setDataToRecycler(it as ArrayList<CarModel>)
        })

        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_addFragment)
        }

        homeFragmentViewModel.emptyDatabase.observe(viewLifecycleOwner, Observer {
            showEmptyDatabase(it)
        })



        return binding.root
    }

    private fun showEmptyDatabase(emptyDatabase: Boolean) {
        if (emptyDatabase){
            binding.imageViewEmpty.visibility = View.VISIBLE
            binding.textViewEmpty.visibility = View.VISIBLE
            binding.recyclerView.visibility = View.INVISIBLE
        }else{
            binding.imageViewEmpty.visibility = View.INVISIBLE
            binding.textViewEmpty.visibility = View.INVISIBLE
            binding.recyclerView.visibility = View.VISIBLE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu,menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_delete->homeFragmentViewModel.deleteDatabase()
        }

        return super.onOptionsItemSelected(item)
    }
    private fun createRecycler(){
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}