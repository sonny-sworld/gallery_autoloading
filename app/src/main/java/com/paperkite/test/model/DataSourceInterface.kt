package com.paperkite.test.model

import com.paperkite.test.data.OperationCallback


interface DataSourceInterface {
    fun retrieveRemoteData(callback: OperationCallback<Cat>)
}