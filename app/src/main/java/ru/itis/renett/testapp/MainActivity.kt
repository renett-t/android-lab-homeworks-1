package ru.itis.renett.testapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock
import android.provider.ContactsContract
import com.google.android.material.snackbar.Snackbar
import ru.itis.renett.testapp.databinding.ActivityMainBinding

const val REQUEST_SELECT_CONTACT = 1

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }

        with(binding) {
            btnSelectContactIntent.setOnClickListener {
                selectContact()
            }

            btnGoto2activity.setOnClickListener {
                startActivity(
                    Intent(
                        this@MainActivity,
                        SecondActivity::class.java
                    ).apply {
                        putExtra("messageFromMainActivity", "Activity started by main activity. GO BACK!!")
                    }
                )
            }

            btnForsymmetry.setOnClickListener {
                val message = "Test alarm to demonstrate how SecondActivity can be started by intent thanks to intent-filters"
                val hour = "7"
                val minutes = "30"
                val testIntent = Intent(AlarmClock.ACTION_SET_ALARM).apply {
                    putExtra(AlarmClock.EXTRA_MESSAGE, message)
                    putExtra(AlarmClock.EXTRA_HOUR, hour)
                    putExtra(AlarmClock.EXTRA_MINUTES, minutes)
                }
                if (testIntent.resolveActivity(packageManager) != null) {
                    startActivity(testIntent)
                }
            }
        }
    }

    private fun selectContact() {
        val intentToGetContact = Intent(Intent.ACTION_PICK).apply {
            type = ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE
        }
        if (intentToGetContact.resolveActivity(packageManager) != null) {
            startActivityForResult(intentToGetContact, REQUEST_SELECT_CONTACT)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        var message: String = ""

        if (resultCode == RESULT_OK) {
            when (requestCode) {
                REQUEST_SELECT_CONTACT -> {
                    val contactUri: Uri = data?.data ?: return

                    // Let's pretend that this piece of code works properly =) Actually not and i dunno why :((((
                    val projection: Array<String> = arrayOf(ContactsContract.CommonDataKinds.Phone.NUMBER)
                    contentResolver.query(contactUri, projection, null, null, null).use { cursor ->
                        if (cursor?.moveToFirst() == true) {
                            val numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                            val number = cursor.getString(numberIndex)
                            binding.tvContactNumber.text = number
                        }
                    }

                    message += " Congrats, you have chosen a contact: ${contactUri.toString()} ^^"
                    // fake number :0
                    binding.tvContactNumber.text = "8-800-5353535";
                }
                else -> {
                    message = "Okay, but wrong intent accepted, no realization :0"
                    super.onActivityResult(requestCode, resultCode, data)
                }
            }
        } else {
            message = "Contact wasn't picked. Try again"
            super.onActivityResult(requestCode, resultCode, data)
        }

        Snackbar.make(
            binding.root,
            message,
            Snackbar.LENGTH_LONG
        ).show()
    }

}
