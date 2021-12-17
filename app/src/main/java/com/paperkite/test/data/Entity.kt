package com.paperkite.test.data

import com.paperkite.test.model.Cat

data class ImageResponse(val status: Int?, val msg: String?, val data: List<Cat>?) {
    fun isSuccess(): Boolean = (status == 200)
}