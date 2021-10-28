package ru.itis.renett.testapp.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import ru.itis.renett.testapp.R
import ru.itis.renett.testapp.databinding.FragmentFirstBinding

class FirstFragment : Fragment(R.layout.fragment_first) {

    private var binding: FragmentFirstBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFirstBinding.bind(view)


    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}
