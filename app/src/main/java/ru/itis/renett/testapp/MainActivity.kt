package ru.itis.renett.testapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
                        putExtra("SOME_EXTRA_KEY", "EXTRA_VALUE")
                    }
                )
            }
        }
    }


    private fun selectContact() {
        val intentToGetContact = Intent(Intent.ACTION_PICK).apply {
            type = ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE
//            type = "${ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE}|${ContactsContract.CommonDataKinds.Email.CONTENT_TYPE}"
        }
        if (intentToGetContact.resolveActivity(packageManager) != null) {
            startActivityForResult(intentToGetContact, REQUEST_SELECT_CONTACT)
        }
    }


    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?) {

        var message: String = ""

        if (resultCode == RESULT_OK) {
            when (requestCode) {
                REQUEST_SELECT_CONTACT -> {
                    val contactUri: Uri? = data?.data
                    val projection: Array<String> = arrayOf(ContactsContract.CommonDataKinds.Phone.NUMBER)
                    if (contactUri != null) {
                        contentResolver.query(contactUri, projection, null, null, null).use { cursor ->
                            if (cursor?.moveToFirst() == true) {
                                val numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                                val number = cursor.getString(numberIndex)
                                with(binding) {
                                    tvContactNumber.text = number
                                }
                                message = number
                            }
                        }
                    }

//                    val contactUri: Uri? = data?.data
//
//                    val projection = arrayOf(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
//                        ContactsContract.CommonDataKinds.Phone.NUMBER)
//                    val cursor = contactUri?.let {
//                        contentResolver.query(it, projection, null, null, null)
//                    }
//
//                    if (cursor != null && cursor.moveToFirst()) {
//                        val nameIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
//                        val numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
//                        val name = cursor.getString(nameIndex)
//                        val number = cursor.getString(numberIndex)
//                        with(binding) {
//                            tvContactName.text = name
//                            tvContactNumber.text = number
//                            tvContactEmail.text = "email.."
//                        }
//                    }
//                    cursor?.close()

                    message += " Yeah, see, here we have chosen contact's info! ^^"
                }
                else -> {
                    message = "Sorry, i dunno what to do with intent...."
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
