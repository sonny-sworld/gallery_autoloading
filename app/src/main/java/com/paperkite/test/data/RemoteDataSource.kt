package com.paperkite.test.data

import com.paperkite.test.model.Cat
import com.paperkite.test.model.DataSourceInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource(apiClient : ApiClient): DataSourceInterface {
    private val apiService  = apiClient.buildService()
    var call: Call<List<Cat>>? = null

    override fun retrieveRemoteData(callback : OperationCallback<Cat>) {
        call = apiService.getCat()
        call?.enqueue(object: Callback<List<Cat>>{
            override fun onResponse(call: Call<List<Cat>>, response: Response<List<Cat>>) {
                response.body()?.run {
                    if(response.isSuccessful ) callback.onSuccess(response.body())
                    else callback.onError(response.message())
                }
            }

            override fun onFailure(call: Call<List<Cat>>, t: Throwable) {
                callback.onError(t.message)
            }
        })
    }
}