package com.porfirio.uploaddm.data.remote

import com.porfirio.uploaddm.data.remote.model.UploadResponseDto
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Streaming

interface FileApi {
    //aqui se declaran los endpoints
    @Multipart  // Para que el archivo se pueda enviar por partes
    @POST("cm/games/upload.php")
    @Streaming      //No usa toda la memoria ram, se hace por partes

    fun uploadImage(
        @Part image: MultipartBody.Part //le indicamos a retrofit  que esto es una parte de un request multipart
    ): Call<UploadResponseDto>
}