package com.porfirio.uploaddm.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import com.porfirio.uploaddm.databinding.ActivityMainBinding
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            val fileName = "djokovic.jpg"
            val fileExt = fileName.substringAfterLast(".", "")  //extensión del archivo a subir

            //crear una copia temporal del archivo en el directorio cache
            val file = File(cacheDir, "PorfirioDiaz.$fileExt")

            file.outputStream().use { fos -> //Con use, se cierra automáticamente el fos
                assets.open(fileName).copyTo(fos)
            }

            mainViewModel.uploadImage(file){ progress ->
                binding.progressBar.post{
                    binding.progressBar.visibility = View.VISIBLE
                    binding.progressBar.progress = progress
                }

                binding.tvProgress.post{
                    binding.tvProgress.visibility = View.VISIBLE
                    binding.tvProgress.text = "$progress %"
                }
            }

            //Nos suscribimos a los cambios del livedata message
            mainViewModel.message.observe(this, Observer{ message ->

                binding.button.isEnabled = false
                binding.progressBar.visibility = View.INVISIBLE
                binding.tvProgress.visibility = View.INVISIBLE

                AlertDialog.Builder(this)
                    .setTitle("Aviso")
                    .setMessage(message)
                    .setPositiveButton("Aceptar"){ dialog, _ ->
                        dialog.dismiss()
                    }
                    .create()
                    .show()

            })
        }

    }


}