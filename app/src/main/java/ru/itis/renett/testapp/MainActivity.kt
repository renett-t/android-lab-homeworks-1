package ru.itis.renett.testapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import ru.itis.renett.testapp.databinding.ActivityMainBinding
import ru.itis.renett.testapp.extention.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var isEditButtonPressed: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }

        with(binding) {
            btnEdit.setOnClickListener {
                // if edit button was pressed for the first time
                if (!isEditButtonPressed) {
                    tvName.hide()
                    etName.show()
                    isEditButtonPressed = true

                    // if user pressed enter on keyboard instead of pressing edit button, saving text and updating view
                    etName.setOnKeyListener(object : View.OnKeyListener {
                        override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
                            // if the event is a key down event on the enter button
                            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER
                            ) {
                                tvName.text = etName.text
                                hideSoftKeyboard()
                                etName.hide()
                                tvName.show()
                                isEditButtonPressed = false

                                return true
                            }
                            return false
                        }
                    })
                }
                // if edit button was pressed for the second time, saving text and updating view
                else {
                    tvName.text = etName.text
                    hideSoftKeyboard()
                    etName.hide()
                    tvName.show()
                    isEditButtonPressed = false
                }
            }
        }
    }
}
