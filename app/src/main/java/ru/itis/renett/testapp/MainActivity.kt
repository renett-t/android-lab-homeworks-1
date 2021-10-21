package ru.itis.renett.testapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import ru.itis.renett.testapp.databinding.ActivityMainBinding

private const val TAG_FIRST_FRAGMENT = "FIRST_FRAGMENT"
private const val TAG_SECOND_FRAGMENT = "SECOND_FRAGMENT"
private const val TAG_THIRD_FRAGMENT = "THIRD_FRAGMENT"
private const val DEFAULT_POP_ENTER_ANIM = R.anim.enter_from_left
private const val DEFAULT_POP_EXIT_ANIM = R.anim.exit_to_right

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }

        supportFragmentManager.beginTransaction().run {
            add(R.id.fragment_container, FirstFragment(), TAG_FIRST_FRAGMENT)
            commit()
        }

        with(binding) {
            btnFirst.setOnClickListener {
                changeFragment(FirstFragment(), TAG_FIRST_FRAGMENT,
                    R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
            }

            btnSecond.setOnClickListener {
                changeFragment(SecondFragment(), TAG_SECOND_FRAGMENT,
                    R.anim.card_flip_right_enter, R.anim.card_flip_right_exit, R.anim.card_flip_left_enter, R.anim.card_flip_left_exit)

            }

            btnThird.setOnClickListener {
                changeFragment(ThirdFragment(), TAG_THIRD_FRAGMENT,
                    R.anim.slide_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out)
            }
        }
    }

    private fun changeFragment(
        fragment: Fragment,
        tag: String,
        enterAnim: Int,
        exitAnim: Int,
        popEnterAnim: Int,
        popExitAnim: Int
    ) {
        supportFragmentManager.beginTransaction().run {
            setCustomAnimations(enterAnim, exitAnim, popEnterAnim, popExitAnim)
            replace(R.id.fragment_container, fragment, tag)
            addToBackStack(fragment.toString())
            commit()
        }
    }

    private fun changeFragment(
        fragment: Fragment,
        tag: String,
        enterAnim: Int,
        exitAnim: Int
    ) {
        changeFragment(fragment, tag, enterAnim, exitAnim, DEFAULT_POP_ENTER_ANIM, DEFAULT_POP_EXIT_ANIM)
    }
}
