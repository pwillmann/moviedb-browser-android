package com.pwillmann.moviediscovery.features.dadjoke

import android.annotation.SuppressLint
import android.os.Parcelable
import android.support.v4.app.FragmentActivity
import com.airbnb.mvrx.Async
import com.airbnb.mvrx.BaseMvRxViewModel
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.MvRxViewModelFactory
import com.airbnb.mvrx.Uninitialized
import com.airbnb.mvrx.fragmentViewModel
import com.pwillmann.moviediscovery.core.BaseFragment
import com.pwillmann.moviediscovery.core.MvRxViewModel
import com.pwillmann.moviediscovery.core.simpleController
import com.pwillmann.moviediscovery.models.jokes.Joke
import com.pwillmann.moviediscovery.network.DadJokeService
import com.pwillmann.moviediscovery.views.basicRow
import com.pwillmann.moviediscovery.views.loadingRow
import com.pwillmann.moviediscovery.views.marquee
import kotlinx.android.parcel.Parcelize
import org.koin.android.ext.android.inject

@SuppressLint("ParcelCreator")
@Parcelize
data class DadJokeDetailArgs(val id: String) : Parcelable

data class DadJokeDetailState(val id: String, val joke: Async<Joke> = Uninitialized) : MvRxState {
    /**
     * This secondary constructor will automatically called if your Fragment has
     * a parcelable in its arguments at key [com.airbnb.mvrx.MvRx.KEY_ARG]
     */
    constructor(args: DadJokeDetailArgs) : this(id = args.id)
}

class DadJokeDetailViewModel(
    initialState: DadJokeDetailState,
    private val dadJokeService: DadJokeService
) : MvRxViewModel<DadJokeDetailState>(initialState) {

    init {
        fetchJoke()
    }

    private fun fetchJoke() = withState { state ->
        if (!state.joke.shouldLoad) return@withState
        dadJokeService.fetch(state.id).execute { copy(joke = it) }
    }

    companion object : MvRxViewModelFactory<DadJokeDetailState> {
        @JvmStatic
        override fun create(
            activity: FragmentActivity,
            state: DadJokeDetailState
        ): BaseMvRxViewModel<DadJokeDetailState> {
            val service: DadJokeService by activity.inject()
            return DadJokeDetailViewModel(state, service)
        }
    }
}

class DadJokeDetailFragment : BaseFragment() {
    private val viewModel: DadJokeDetailViewModel by fragmentViewModel()

    override fun epoxyController() = simpleController(viewModel) { state ->
        marquee {
            id("marquee")
            title("Dad Joke")
        }

        /**
         * Async overrides the invoke operator so we can just call it. It will return the value if
         * it is Success or null otherwise.
         */
        val joke = state.joke()
        if (joke == null) {
            loadingRow {
                id("loading")
            }
            return@simpleController
        }

        basicRow {
            id("joke")
            title(joke.joke)
        }
    }
}