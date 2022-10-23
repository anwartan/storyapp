package com.example.storyapp.home

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.storyapp.R
import com.example.storyapp.base.BaseFragment
import com.example.storyapp.databinding.FragmentHomeBinding
import com.example.storyapp.helper.ViewModelFactory
import com.example.storyapp.ui.LoadingStateAdapter
import com.example.storyapp.ui.PagingStoryAdapter


class HomeFragment : BaseFragment(){
    private val homeViewModel: HomeViewModel by viewModels {
        ViewModelFactory(requireContext())
    }
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
        val storyAdapter = PagingStoryAdapter()

        storyAdapter.onItemClick={data,imageView,title,description->
            data?.let {
                val extras = FragmentNavigatorExtras(
                    imageView to "image_big",
                    title to "title",
                    description to "description"
                )
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailFragment(data), extras)
            }
        }
        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_addStoryFragment)
        }

        with(binding.rvMovie){
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = storyAdapter.withLoadStateFooter(
                footer = LoadingStateAdapter{
                    storyAdapter.retry()
                }
            )
        }
        homeViewModel.getStories()

        homeViewModel.stories.observe(viewLifecycleOwner){
            it?.let {
                storyAdapter.submitData(lifecycle,it)
            }
        }

        val menuHost : MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_detail,menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId){
                    R.id.menu_map->{
                        findNavController().navigate(R.id.action_HomeFragment_to_mapFragment)
                    }
                }
                return true
            }
        },viewLifecycleOwner, Lifecycle.State.RESUMED)


    }

    override fun onResume() {
        super.onResume()
        homeViewModel.getStories()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}