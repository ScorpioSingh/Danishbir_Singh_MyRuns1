package com.example.myruns1


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.*
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import java.io.File
import java.security.AccessController.getContext

class MainActivity : AppCompatActivity() {

    private lateinit var img_view: ImageView
    private lateinit var text_View: TextView
    private lateinit var img_uri: Uri
    private val ImgeName = "profile_photo.jpg"
    private lateinit var cameraResult: ActivityResultLauncher<Intent>

    private lateinit var radio_one : RadioButton;
    private lateinit var radio_two: RadioButton;
    private lateinit var gender: RadioGroup

    private lateinit var sharedpreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        sharedpreferences = getSharedPreferences("radio_name",0)
        val genderCircle: Int = sharedpreferences.getInt("genderCircle",3)// first time login the app no gender is selected
        editor = sharedpreferences.edit()

        gender = findViewById(R.id.gender_group_button)
        radio_one = findViewById(R.id.male_radio)
        radio_two = findViewById(R.id.female_radio)
        if(genderCircle == 1){
            radio_one.isChecked = true
        }else if(genderCircle ==0) {
            radio_two.isChecked = true
        }


        loadProfile()

        img_view = findViewById(R.id.real_photo)
        Util.checkPermissions(this)
        val imagefile = File(getExternalFilesDir(null),ImgeName)
        img_uri = FileProvider.getUriForFile(this,"com.example.myruns1",imagefile)

        if (imagefile.exists()){
            val bitmap = Util.getBitmap(this, img_uri)
            img_view.setImageBitmap(bitmap)
        }
        cameraResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { func: ActivityResult ->
            if (func.resultCode == RESULT_OK) {
                val bitmap = Util.getBitmap(this, img_uri)
                img_view.setImageBitmap(bitmap)
            }
        }
    }


    fun changeButton(view: View) {
        val intention = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intention.putExtra(MediaStore.EXTRA_OUTPUT, img_uri)
        cameraResult.launch(intention)
        //allows the user to click photo with the camera
    }

    fun genderClick(view: View){
       if(radio_one.isChecked){
           editor.putInt("genderCircle",1)
       }else if (radio_two.isChecked){
           editor.putInt("genderCircle",0)
       }
        editor.commit()
    }

    fun saveButton(view: View){
    saveProfile()
    }
    private fun loadProfile(){
        val SharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val SharedEmail = getSharedPreferences("sharedEmail", Context.MODE_PRIVATE)
        val SharedNumber = getSharedPreferences("sharedNumber", Context.MODE_PRIVATE)
        val SharedClass = getSharedPreferences("sharedClass",Context.MODE_PRIVATE)
        val Sharedmajor = getSharedPreferences("sharedMajor",Context.MODE_PRIVATE)


        val savedEmail = SharedEmail.getString("STRING_KEY","")
        val savedName = SharedPreferences.getString("STRING_KEY","")
        val savedNumber = SharedNumber.getString("STRING_KEY","")
        val savedClassApp =  SharedClass.getString("STRING_KEY","")
        val savedmajor = Sharedmajor.getString("STRING_KEY","")

        val nameText = findViewById<TextView>(R.id.nameText)
        nameText.text = savedName


        val emailText = findViewById<TextView>(R.id.EmailText)
        emailText.text = savedEmail

        val numberText = findViewById<TextView>(R.id.phone)
        numberText.text = savedNumber

        val classApp = findViewById<TextView>(R.id.edit_class)
        classApp.text = savedClassApp

        val majorSaved = findViewById<TextView>(R.id.major_class)
        majorSaved.text = savedmajor

    }

    private fun saveProfile(){
        //name saving
        val nameText = findViewById<TextView>(R.id.nameText)
        val insertedName = nameText.text.toString()
        nameText.text = insertedName



        //email saving
        val emailText = findViewById<TextView>(R.id.EmailText)
        val insertedEmail = emailText.text.toString()
        emailText.text = insertedEmail

        //numbersaving
        val numbertext = findViewById<TextView>(R.id.phone)
        val insertedNumber = numbertext.text.toString()
        numbertext.text = insertedNumber

        val majortext = findViewById<TextView>(R.id.major_class)
        val insertedMajor = majortext.text.toString()
        majortext.text = insertedMajor




        val Sharedmajor = getSharedPreferences("sharedMajor", Context.MODE_PRIVATE)
        val editmajor = Sharedmajor.edit()
        editmajor.apply(){
            putString("STRING_KEY", insertedMajor)
        }.apply()


        val SharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val editName = SharedPreferences.edit()
        editName.apply(){
            putString("STRING_KEY", insertedName)

        }.apply()

        val SharedEmail = getSharedPreferences("sharedEmail", Context.MODE_PRIVATE)
        val editEmail = SharedEmail.edit()
        editEmail.apply(){
            putString("STRING_KEY", insertedEmail)

        }.apply()


        val SharedNumber = getSharedPreferences("sharedNumber", Context.MODE_PRIVATE)
        val editNumber = SharedNumber.edit()
        editNumber.apply(){
            putString("STRING_KEY", insertedNumber)

        }.apply()

        val classApp = findViewById<TextView>(R.id.edit_class)
        val insertedClassApp = classApp.text.toString()
        classApp.text = insertedClassApp


        val SharedClass = getSharedPreferences("sharedClass", Context.MODE_PRIVATE)
        val editClass = SharedClass.edit()
        editClass.apply(){
            putString("STRING_KEY", insertedClassApp)

        }.apply()







        Toast.makeText(this,"Data has been saved", Toast.LENGTH_LONG).show()


    }


    fun cancelButton(view: View) {
        finish();
    }

}

