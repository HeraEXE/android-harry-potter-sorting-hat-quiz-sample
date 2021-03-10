package hera.com.sortinghatquiz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import hera.com.sortinghatquiz.databinding.FragmentTitleBinding

class TitleFragment : Fragment() {
    private lateinit var binding: FragmentTitleBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_title, container, false)
        (activity as AppCompatActivity).supportActionBar?.title = "Sorting Hat Quiz"

        binding.startQuizButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_titleFragment_to_quizFragment)
        }

        return binding.root
    }
}