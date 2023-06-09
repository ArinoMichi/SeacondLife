package com.team.seacondlife

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.ContentValues
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.team.UserDataBase.UserSQLiteHelper
import com.team.seacondlife.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding:ActivitySignUpBinding

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dbhelpe=UserSQLiteHelper(applicationContext)

        binding.buttonSignUp.setOnClickListener{
            var username_new=binding.editUsername.text.toString()
            var email_new=binding.editEmail.text.toString()
            var password_new=binding.editSetPassword.text.toString()
            var confirm_password=binding.editConfirmPassword.text.toString()
            var DataAdded=Dialog(this)
            DataAdded.setContentView(R.layout.dialog_style)

            if(username_new.isNotEmpty() && email_new.isNotEmpty() && password_new.isNotEmpty() && confirm_password.isNotEmpty()){
                if(password_new==confirm_password&&dbhelpe.VerifyUser(username_new,password_new)!=true){
                    dbhelpe.addNewUser(username_new,email_new,password_new)

                    DataAdded.findViewById<TextView>(R.id.DialogMessage).setText(R.string.AlertDg_message_SuccSignUp)
                    DataAdded.findViewById<Button>(R.id.DialogButton).setOnClickListener{
                        ToMain()
                    }
                    DataAdded.show()
                    binding.editUsername.text?.clear()
                    binding.editEmail.text?.clear()
                    binding.editSetPassword.text?.clear()
                    binding.editConfirmPassword.text?.clear()
                }else{
                    /** mostrar toast con error si el nombre de usuario y contraseña ya existen */
                    DataAdded.findViewById<TextView>(R.id.DialogMessage).setText(R.string.AlertDg_message_ErrSignUp)
                    DataAdded.findViewById<Button>(R.id.DialogButton).setOnClickListener{
                        DataAdded.dismiss()
                    }
                    DataAdded.show()
                }
            }else{
                DataAdded.findViewById<TextView>(R.id.DialogMessage).setText(R.string.AlertDg_message_EmptySignUp)
                DataAdded.findViewById<Button>(R.id.DialogButton).setOnClickListener{
                    DataAdded.dismiss()
                }
                DataAdded.show()
            }
        }
    }

    fun ToMain(){
        val intent=Intent(this,LoginActivity::class.java)
        startActivity(intent)
    }
}