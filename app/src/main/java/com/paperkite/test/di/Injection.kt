package com.paperkite.test.di

import com.paperkite.test.data.ApiClient
import com.paperkite.test.data.RemoteDataSource
import com.paperkite.test.model.DataSourceInterface
import com.paperkite.test.model.Repository
import com.paperkite.test.viewmodel.ViewModelFactory


object Injection {

    private var catDataSource: DataSourceInterface? = null
    private var mRepository: Repository? = null
    private var viewModelFactory: ViewModelFactory? = null

    private fun createCatDataSource(): DataSourceInterface {
        val dataSource = RemoteDataSource(ApiClient)
        catDataSource = dataSource
        return dataSource
    }

    private fun createCatRepository(): Repository {
        val repository = Repository(provideDataSource())
        mRepository = repository
        return repository
    }

    private fun createFactory(): ViewModelFactory {
        val factory = ViewModelFactory(providerRepository())
        viewModelFactory = factory
        return factory
    }

    private fun provideDataSource() = catDataSource ?: createCatDataSource()
    private fun providerRepository() = mRepository ?: createCatRepository()

    fun provideViewModelFactory() = viewModelFactory ?: createFactory()

    fun destroy() {
        catDataSource = null
        mRepository = null
        viewModelFactory = null
    }
}