package org.mrlem.sample.cleanarch.features.main

import io.reactivex.rxjava3.kotlin.addTo
import org.mrlem.sample.arch.BaseViewModel
import org.mrlem.sample.domain.repositories.SomethingRepository

class MainViewModel(
    private val repository: SomethingRepository
) : BaseViewModel<MainState>(MainState()) {

    override fun onStart() {
        repository.findSomething()
            .subscribe { something -> updateState { copy(data = something.data) } }
            .addTo(disposeOnStop)
    }

}
