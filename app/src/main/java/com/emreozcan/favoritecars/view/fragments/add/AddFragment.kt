package com.emreozcan.favoritecars.view.fragments.add

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.emreozcan.favoritecars.R
import com.emreozcan.favoritecars.data.models.CarModel
import com.emreozcan.favoritecars.data.viewmodel.SharedViewModel
import com.emreozcan.favoritecars.databinding.FragmentAddBinding
import com.emreozcan.favoritecars.view.fragments.coloradapter.ColorAdapter
import kotlinx.android.synthetic.main.fragment_add.*
import java.util.ArrayList


class AddFragment : Fragment() {

    private val addFragmentViewModel: AddFragmentViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by viewModels()

    private var _binding: FragmentAddBinding?=null
    private val binding get() = _binding!!
    val READ_PERMISSION = 1
    val GALLERY_CODE = 2

    private var selectedImageUri : Uri?=null
    private var selectedImageBitmap :Bitmap?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBinding.inflate(inflater,container,false)

        binding.imageView.setOnClickListener{
            if (ContextCompat.checkSelfPermission(requireContext(),android.Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),READ_PERMISSION)
            }else{
                val galleryIntent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(galleryIntent,GALLERY_CODE)
            }
        }

        textInputLayoutFunctions()

        binding.buttonAdd.setOnClickListener {
            insertCar()
        }

        return binding.root
    }

    private fun textInputLayoutFunctions() {
        binding.nameEt.doOnTextChanged { text, start, before, count ->
            binding.textInputLayoutName.error = null
        }
        binding.hpEt.doOnTextChanged { text, start, before, count ->
            binding.textInputLayoutHp.error = null
        }
        binding.maxSpeedEt.doOnTextChanged { text, start, before, count ->
            binding.textInputLayoutMaxSpeed.error = null
        }

        binding.yearEt.doOnTextChanged { text, start, before, count ->
            if (text!!.length!=4){
                binding.textInputYear.error="It should be a year !"
            }else{
                binding.textInputYear.error = null
            }
        }
    }

    private fun insertCar() {
        var name = "a"
        var check = true
        if (binding.nameEt.text!!.isEmpty()){
            binding.textInputLayoutName.error = "It could not be empty !"
            check = false
        }else{
            name = binding.nameEt.text.toString()
        }
        var hp = 0

        if (binding.hpEt.text!!.isEmpty()){
            binding.textInputLayoutHp.error = "It is Empty !"
            check = false
        }else{
            hp= binding.hpEt.text.toString().toInt()
        }

        var maxspeed = 0
        if (binding.maxSpeedEt.text!!.isEmpty()){
            binding.textInputLayoutMaxSpeed.error= "It is Empty !"
            check = false
        }else{
            maxspeed = binding.maxSpeedEt.text.toString().toInt()
        }
        var year = 0
        if (binding.yearEt.text!!.isEmpty()){
            binding.textInputYear.error = "It is Empty !"
            check = false
        }else{
            year = binding.yearEt.text.toString().toInt()
        }


        val favorite = checkBoxFavorite.isChecked
        val color = binding.autoCompleteTextView.text.toString()

        if (selectedImageBitmap==null){
            check = false
            imageView.setImageResource(R.drawable.ic_image_error)
        }
        if (check){
            val car = CarModel(0,name,sharedViewModel.parseColor(color),hp,maxspeed,year,favorite,selectedImageBitmap!!)
            addFragmentViewModel.insertCar(car)
            Toast.makeText(requireContext(),"Car Saved",Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_addFragment_to_homeFragment)
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode==READ_PERMISSION){
            if (grantResults.size>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                val galleryIntent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(galleryIntent,GALLERY_CODE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GALLERY_CODE && resultCode == Activity.RESULT_OK && data!=null){
            selectedImageUri = data.data
            if (selectedImageUri!=null){

                if (Build.VERSION.SDK_INT>=28){
                    val source = ImageDecoder.createSource(requireActivity().contentResolver,selectedImageUri!!)
                    selectedImageBitmap = ImageDecoder.decodeBitmap(source)
                    binding.imageView.setImageBitmap(selectedImageBitmap)
                    println(source)

                }else{
                    selectedImageBitmap = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver,selectedImageUri)
                    binding.imageView.setImageBitmap(selectedImageBitmap)
                }

            }
        }
    }

    override fun onResume() {
        super.onResume()
        //Color Adapter
        val colors = resources.getStringArray(R.array.colors).toCollection(ArrayList())
        val colorAdapter = ColorAdapter(requireContext(),colors)

        binding.autoCompleteTextView.setAdapter(colorAdapter)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}