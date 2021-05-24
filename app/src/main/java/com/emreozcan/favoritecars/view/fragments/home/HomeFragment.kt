package com.emreozcan.favoritecars.view.fragments.home

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.emreozcan.favoritecars.R
import com.emreozcan.favoritecars.data.models.CarModel
import com.emreozcan.favoritecars.data.viewmodel.HomeFragmentViewModel
import com.emreozcan.favoritecars.databinding.FragmentHomeBinding
import com.emreozcan.favoritecars.view.fragments.home.adapter.RecyclerRowAdapter
import com.google.android.material.snackbar.Snackbar
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
        binding.lifecycleOwner = this
        binding.homeFragmentViewModel = homeFragmentViewModel



        createRecycler()

        homeFragmentViewModel.getAllData.observe(viewLifecycleOwner, Observer {
            homeFragmentViewModel.checkDatabaseEmpty(it)
            adapter.setDataToRecycler(it as ArrayList<CarModel>)
            binding.recyclerView.scheduleLayoutAnimation()
        })


        return binding.root
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

        swipeToDelete()



    }

    private fun swipeToDelete() {
        val swipeToDeleteCallback = object : SwipeToDelete(){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val deletedItem = adapter.dataList[viewHolder.adapterPosition]
                homeFragmentViewModel.deleteCar(deletedItem)
                adapter.notifyItemRemoved(viewHolder.adapterPosition)

                restoreDeleted(viewHolder.itemView,deletedItem,viewHolder.adapterPosition)
            }

        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
    }
    private fun restoreDeleted(view: View,deletedItem: CarModel,position: Int){
        val snackbar = Snackbar.make(view,"Car Deleted ${deletedItem.name}",Snackbar.LENGTH_LONG)
        snackbar.setAction("UNDO"){
            homeFragmentViewModel.insertCar(deletedItem)
        }
        snackbar.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}