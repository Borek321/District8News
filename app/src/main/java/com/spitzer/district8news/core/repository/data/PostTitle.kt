package com.spitzer.district8news.core.repository.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PostTitle(
    @SerializedName("rendered")
    var rendered: String = ""
) : Parcelable