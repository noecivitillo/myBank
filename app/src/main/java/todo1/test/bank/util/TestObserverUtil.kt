package todo1.test.bank.util

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.Observer

/**
 * Only used for test..
 * An utility class that implement lifecycle in order to observe changes only one time
 */
class TestObserverUtil<T>(private val handler: (T) -> Unit) : Observer<T>, LifecycleOwner {
    private val lifecycle = LifecycleRegistry(this)

    init {
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
    }

    override fun getLifecycle(): Lifecycle = lifecycle

    override fun onChanged(t: T) {
        handler(t)
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    }
}