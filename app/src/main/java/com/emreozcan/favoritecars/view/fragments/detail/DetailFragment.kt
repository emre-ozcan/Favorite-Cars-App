package com.emreozcan.favoritecars.view.fragments.detail

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
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.emreozcan.favoritecars.R
import com.emreozcan.favoritecars.data.models.CarModel
import com.emreozcan.favoritecars.data.viewmodel.SharedViewModel
import com.emreozcan.favoritecars.databinding.FragmentDetailBinding
import com.emreozcan.favoritecars.view.fragments.coloradapter.ColorAdapter
import java.util.ArrayList


class DetailFragment : Fragment() {
    private val args by navArgs<DetailFragmentArgs>()
    private val detailFragmentViewModel : DetailFragmentViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by viewModels()

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    val READ_PERMISSION = 1
    val GALLERY_CODE = 2

    private var selectedImageUri : Uri?=null
    private var selectedImageBitmap : Bitmap?=null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDetailBinding.inflate(inflater,container,false)
        binding.car = args.carModel
        binding.detailFragmentViewModel = detailFragmentViewModel

        val carModel = args.carModel



        selectedImageBitmap = carModel.image

        val carAnim = AnimationUtils.loadAnimation(requireContext(),R.anim.alpha_anim)
        binding.detailImageView.startAnimation(carAnim)

        textInputLayoutFunctions()

        binding.detailImageView.setOnClickListener {
            if (ContextCompat.checkSelfPermission(requireContext(),android.Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),READ_PERMISSION)
            }else{
                val galleryIntent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(galleryIntent,GALLERY_CODE)
            }
        }

        binding.buttonSave.setOnClickListener {
            updateCar()
        }

        return binding.root
    }
    private fun updateCar() {
        var name = "a"
        var check = true
        if (binding.detailNameEt.text!!.isEmpty()){
            binding.detailTextInputLayoutName.error = "It could not be empty !"
            check = false
        }else{
            name = binding.detailNameEt.text.toString()
        }
        var hp = 0

        if (binding.detailHpEt.text!!.isEmpty()){
            binding.detailTextInputLayoutHp.error = "It is Empty !"
            check = false
        }else{
            hp= binding.detailHpEt.text.toString().toInt()
        }

        var maxspeed = 0
        if (binding.detailMaxSpeedEt.text!!.isEmpty()){
            binding.detailTextInputLayoutMaxSpeed.error= "It is Empty !"
            check = false
        }else{
            maxspeed = binding.detailMaxSpeedEt.text.toString().toInt()
        }
        var year = 0
        if (binding.detailYearEt.text!!.isEmpty()){
            binding.detailTextInputYear.error = "It is Empty !"
            check = false
        }else{
            year = binding.detailYearEt.text.toString().toInt()
        }


        val favorite = binding.detailCheckBoxFavorite.isChecked
        val color = binding.detailAutoCompleteTextView.text.toString()

        if (selectedImageBitmap==null){
            check = false
            binding.detailImageView.setImageResource(R.drawable.ic_image_error)
        }
        if (check){
            val car = CarModel(args.carModel.id,name,sharedViewModel.parseColor(color),hp,maxspeed,year,favorite,selectedImageBitmap!!)
            detailFragmentViewModel.updateCar(car)
            Toast.makeText(requireContext(),"Car Saved",Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_detailFragment_to_homeFragment)
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
                    binding.detailImageView.setImageBitmap(selectedImageBitmap)
                    println(source)

                }else{
                    selectedImageBitmap = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver,selectedImageUri)
                    binding.detailImageView.setImageBitmap(selectedImageBitmap)
                }

            }
        }
    }
    private fun textInputLayoutFunctions() {
        binding.detailNameEt.doOnTextChanged { text, start, before, count ->
            binding.detailTextInputLayoutName.error = null
        }
        binding.detailHpEt.doOnTextChanged { text, start, before, count ->
            binding.detailTextInputLayoutHp.error = null
        }
        binding.detailMaxSpeedEt.doOnTextChanged { text, start, before, count ->
            binding.detailTextInputLayoutMaxSpeed.error = null
        }

        binding.detailYearEt.doOnTextChanged { text, start, before, count ->
            if (text!!.length!=4){
                binding.detailTextInputYear.error="It should be a year !"
            }else{
                binding.detailYearEt.error = null
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val colors = resources.getStringArray(R.array.colors).toCollection(ArrayList())
        val colorAdapter = ColorAdapter(requireContext(),colors)
        binding.detailAutoCompleteTextView.setText(args.carModel.color.toString())

        binding.detailAutoCompleteTextView.setAdapter(colorAdapter)


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }


}