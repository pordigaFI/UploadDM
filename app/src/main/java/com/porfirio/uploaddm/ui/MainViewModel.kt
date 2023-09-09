package com.porfirio.uploaddm.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.porfirio.uploaddm.data.FileRepository
import com.porfirio.uploaddm.data.remote.ProgressRequestBody
import com.porfirio.uploaddm.data.remote.model.UploadResponseDto
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class MainViewModel(private val repository: FileRepository = FileRepository()): ViewModel() {

    private val _message = MutableLiveData<String>() //es la versión interna y mutable
    val message: LiveData<String> = _message

    fun uploadImage(file: File, progressListener: (Int) -> Unit){
        viewModelScope.launch {

            val requestBody = file.asRequestBody()

            val progressRequestBody = ProgressRequestBody(requestBody){ progress ->
                //notificamos al listener del progreso
                progressListener(progress)
            }

            val imagePart = MultipartBody.Part.createFormData(
                "image",
                file.name,
                progressRequestBody
            )

            val call: Call<UploadResponseDto> = repository.uploadImage(imagePart)

            call.enqueue(object: Callback<UploadResponseDto>{
                override fun onResponse(
                    call: Call<UploadResponseDto>,
                    response: Response<UploadResponseDto>
                ) {
                    Log.d("LOGS", "Mensaje del servidor ${response.body()?.message}")
                    _message.postValue(response.body()?.message)
                }

                override fun onFailure(call: Call<UploadResponseDto>, t: Throwable) {
                    //Manejo el error en la conexión
                }

            })

        }
    }

}
