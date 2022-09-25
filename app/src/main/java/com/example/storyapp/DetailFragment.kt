package com.example.storyapp

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.storyapp.base.BaseFragment
import com.example.storyapp.databinding.FragmentDetailBinding
import com.example.storyapp.model.StoryModel
import com.example.storyapp.utils.load

class DetailFragment : BaseFragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(this.context).inflateTransition(android.R.transition.move)
        sharedElementReturnTransition =  TransitionInflater.from(this.context).inflateTransition(android.R.transition.move)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        val story = arguments?.getParcelable<StoryModel>("story")!!
        binding.tvTitle.text=story.name
        binding.tvDescription.text=story.description
        binding.ivImage.load(story.photoUrl){
            startPostponedEnterTransition()
        }

    }


}

