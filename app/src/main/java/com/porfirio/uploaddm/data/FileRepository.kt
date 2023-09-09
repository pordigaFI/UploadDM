package com.porfirio.uploaddm.data

import com.porfirio.uploaddm.data.remote.FileApi
import com.porfirio.uploaddm.data.remote.RetrofitHelper
import com.porfirio.uploaddm.data.remote.model.UploadResponseDto
import okhttp3.MultipartBody
import retrofit2.Call

class FileRepository {

    private val fileApi: FileApi = RetrofitHelper.getRetrofit().create(FileApi::class.java)

    fun uploadImage(imagePart: MultipartBody.Part): Call<UploadResponseDto> = fileApi.uploadImage(imagePart)

}