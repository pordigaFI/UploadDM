package com.porfirio.uploaddm.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitHelper {
//Hacemos un singleton
    companion object{
        fun getRetrofit(): Retrofit =
            Retrofit.Builder()
                .baseUrl("https://www.serverbpw.com/")  //Aqui definimos que servidor queremos usar
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

}
