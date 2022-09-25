package com.example.storyapp.addStory

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.storyapp.R
import com.example.storyapp.base.BaseFragment
import com.example.storyapp.databinding.FragmentAddStoryBinding
import com.example.storyapp.utils.rotateBitmap
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
        binding.previewImageView.setOnClickListener { startCameraX() }

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