package ru.itis.renett.testapp.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import ru.itis.renett.testapp.R
import ru.itis.renett.testapp.databinding.FragmentThirdBinding
import ru.itis.renett.testapp.viewpager.FlowerCardAdapter
import ru.itis.renett.testapp.viewpager.FlowerCardRepository

class ThirdFragment : Fragment(R.layout.fragment_third) {
    private var binding: FragmentThirdBinding? = null
    private var cardsAdapter: FlowerCardAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentThirdBinding.bind(view)

        cardsAdapter = FlowerCardAdapter(FlowerCardRepository.flowerCards) {
            showMessage(it)
        }

        view.findViewById<RecyclerView>(R.id.rv_flower_cards).run {
            adapter = cardsAdapter
            addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL))
        }
    }

    private fun showMessage(cardId: Int) {
        FlowerCardRepository.getFlowerCardById(cardId)?.title?.let {
            Snackbar.make(
                requireActivity().findViewById(R.id.fragment_container),
                it,
                Snackbar.LENGTH_LONG
            ).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
