package org.mrlem.sample.cleanarch

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import org.mrlem.sample.arch.BaseApplication
import org.mrlem.sample.cleanarch.ui.main.hud.HudViewModel
import org.mrlem.sample.data.repositories.SomethingRepositoryImpl
import org.mrlem.sample.domain.repositories.SomethingRepository

@Suppress("unused")
class Application : BaseApplication() {

    override val modules = listOf(
        module {
            // data -> domain
            single<SomethingRepository> { SomethingRepositoryImpl() }

            // domain -> presentation
            viewModel { HudViewModel() }
        }
    )

}
