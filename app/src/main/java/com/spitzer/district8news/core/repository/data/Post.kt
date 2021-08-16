package com.spitzer.district8news.core.repository.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Post(
    @SerializedName("id")
    var id: Int,
    @SerializedName("link")
    var link: String = "",
    @SerializedName("slug")
    var slug: String = "",
    @SerializedName("date")
    var date: Date,
    @SerializedName("featured_media")
    var featuredMediaId: Int = 0,
    @SerializedName("wp:featuredmedia")
    var featuredMedia: ArrayList<PostFeaturedMedia> = arrayListOf(),
    @SerializedName("title")
    var title: PostTitle,
    @SerializedName("content")
    var postContent: PostContent,
    @SerializedName("categories")
    var categories: ArrayList<Int> = arrayListOf(),
    @SerializedName("tags")
    var tags: ArrayList<Int> = arrayListOf()
) : Parcelable
