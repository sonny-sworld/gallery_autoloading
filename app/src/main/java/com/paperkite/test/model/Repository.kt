package com.paperkite.test.model

import com.paperkite.test.data.OperationCallback


class Repository(private val mDataSource: DataSourceInterface) {

    fun fetchCat(callback: OperationCallback<Cat>) {
        mDataSource.retrieveRemoteData(callback)
    }

}