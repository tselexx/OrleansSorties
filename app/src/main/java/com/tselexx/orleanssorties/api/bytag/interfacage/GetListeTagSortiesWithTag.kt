package com.tselexx.orleanssorties.api.bytag.interfacage

import com.tselexx.orleanssorties.api.bytag.mainmodel.ListeTagSorties
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface GetListeTagSortiesWithTag {


    @GET("events") // OK donne des résultats
//    @GET("search/")  OK donne aussi des résultats
//    @GET("search") //ok marche aussi

    fun fungetListeTagSortiesWithTag
        (
        @Query("offset") offset : Int,
        @Query("limit") limit : Int,
        @Query("tags[]") tags : String,
        @Query("from") datestart : String,
        @Query("to") dateend : String,
        @Query("neLat") neLat : Double,
        @Query("neLng") neLng : Double,
        @Query("swLat") swLat : Double,
        @Query("swLng") swLng : Double
        ):Observable<ListeTagSorties>
//    fun fungetListeTagSortiesWithTag
//                (
//        @Query("offset") offset : Int =  0,
//        @Query("limit") limit : Int =  100,
//        @Query("tags[]") tags : String,
//        @Query("from") datestart : String,
//        @Query("to") dateend : String,
//        @Query("neLat") neLat : Double = 48.03764270715237,
//        @Query("neLng") neLng : Double = 2.069944763183571,
//        @Query("swLat") swLat : Double = 47.71525581207238,
//        @Query("swLng") swLng : Double = 1.7300552368163835
//    ):Observable<ListeTagSorties>


    companion object {
        fun create(): GetListeTagSortiesWithTag {

            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(
                            RxJava2CallAdapterFactory.create())
                    .addConverterFactory(
                            GsonConverterFactory.create())
                    .baseUrl("http://sortir.orleans-metropole.fr/api/")
                    .build()
            return retrofit.create(GetListeTagSortiesWithTag::class.java)
        }
    }
}