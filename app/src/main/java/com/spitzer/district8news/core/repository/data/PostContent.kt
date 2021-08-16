package com.spitzer.district8news.core.repository.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PostContent(
    @SerializedName("rendered")
    var rendered: String = "",
    @SerializedName("protected")
    var protected: Boolean = false
) : Parcelable