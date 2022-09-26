package com.example.storyapp.addStory

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.storyapp.R
import com.example.storyapp.base.BaseFragment
import com.example.storyapp.databinding.FragmentAddStoryBinding
import com.example.storyapp.utils.rotateBitmap
import com.example.storyapp.utils.uriToFile
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.io.File

class AddStoryFragment : BaseFragment() {


    private var _binding: FragmentAddStoryBinding? = null
    private val addStoryViewModel: AddStoryViewModel by viewModels()
    private var imageFile: File? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddStoryBinding.inflate(inflater, container, false)

        return binding.root
    }

    @SuppressLint("InflateParams")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFragmentResultListener(CAMERA_RESULT) { _, bundle ->
            imageFile = bundle.get(CAMERA_RESULT) as File
            imageFile?.let { file ->
                val result = rotateBitmap(
                    BitmapFactory.decodeFile(file.path),
                    true
                )
                binding.previewImageView.setImageBitmap(result)
            }
        }


        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }
        binding.previewImageView.setOnClickListener {
            val dialog = BottomSheetDialog(requireContext())
            val viewBottomSheet = layoutInflater.inflate(R.layout.bottom_sheet_take_picture,null)

            val camera = viewBottomSheet.findViewById<LinearLayout>(R.id.camera_photo)
            val gallery = viewBottomSheet.findViewById<LinearLayout>(R.id.gallery_photo)
            camera.setOnClickListener {
                startCameraX()
                dialog.dismiss()
            }
            gallery.setOnClickListener {
                startGallery()
                dialog.dismiss()
            }

            dialog.setCancelable(true)
            dialog.setContentView(viewBottomSheet)
            dialog.show()

        }


        binding.btnAddStory.setOnClickListener {
            if(binding.etDescription.isValid()) {
                val description = binding.etDescription.text.toString()
                when (imageFile) {
                    null -> {
                        Toast.makeText(context, getString(R.string.ambil_foto), Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        addStoryViewModel.addStory(
                            description,
                            null,
                            null,
                            imageFile!!
                        )
                    }
                }
            }
        }

        addStoryViewModel.added.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().popBackStack()
            }
        }
        addStoryViewModel.message.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { message ->
                showToast(message)
            }
        }
        addStoryViewModel.isLoading.observe(viewLifecycleOwner){
            showLoading(it)
        }


    }


    private fun startCameraX() {
        findNavController().navigate(R.id.action_addStoryFragment_to_cameraFragment)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    context,
                    getString(R.string.permission),
                    Toast.LENGTH_SHORT
                ).show()

            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
    }



    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
            val myFile = uriToFile(selectedImg, requireContext())
            imageFile=myFile
            binding.previewImageView.setImageURI(selectedImg)
        }
    }
    private fun startGallery() {
        val intent = Intent()
        intent.action = ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val CAMERA_RESULT = "CAMERA_RESULT"
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }

}