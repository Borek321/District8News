package com.spitzer.district8news.core.repository.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
    @SerializedName("id")
    var id: Int,
    @SerializedName("name")
    var name: String = "",
    @SerializedName("description")
    var description: String = "",
    @SerializedName("link")
    var link: String = "",
    @SerializedName("slug")
    var slug: String = ""
) : Parcelable
