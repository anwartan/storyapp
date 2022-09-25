package com.example.storyapp.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.storyapp.R
import com.example.storyapp.base.BaseFragment
import com.example.storyapp.databinding.FragmentHomeBinding
import com.example.storyapp.ui.StoryAdapter


class HomeFragment : BaseFragment(){
    private val homeViewModel: HomeViewModel by viewModels ()
    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val storyAdapter = StoryAdapter()

        storyAdapter.onItemClick={data,imageView,title,description->
            val extras = FragmentNavigatorExtras(
                imageView to "image_big",
                title to "title",
                description to "description"
            )


            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailFragment(data), extras)
        }
        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_addStoryFragment)
        }
        homeViewModel.stories.observe(viewLifecycleOwner){
            storyAdapter.setListData(it)
        }
        homeViewModel.isLoading.observe(viewLifecycleOwner){
            showLoading(it)
        }
        homeViewModel.getStories()
//        binding.ivImage.setOnClickListener {
//            val extras = FragmentNavigatorExtras(binding.ivImage to "imageView1")
//            findNavController().navigate(
//                R.id.action_HomeFragment_to_detailFragment,
//                null,
//                null,
//                extras
//            )
//        }
        with(binding.rvMovie){
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = storyAdapter
        }
        homeViewModel.message.observe(viewLifecycleOwner){
            it.getContentIfNotHandled()?.let { message ->
                showToast(message)
            }
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}