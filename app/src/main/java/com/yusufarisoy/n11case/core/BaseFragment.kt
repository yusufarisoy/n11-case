package com.yusufarisoy.n11case.core

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

abstract class BaseFragment : Fragment() {

    protected fun <T> Flow<T>.collectIn(
        owner: LifecycleOwner = viewLifecycleOwner,
        lifecycleState: Lifecycle.State = Lifecycle.State.STARTED,
        action: suspend CoroutineScope.(T) -> Unit = {}
    ) {
        owner.lifecycleScope.launch {
            repeatOnLifecycle(lifecycleState) {
                collect { action(it) }
            }
        }
    }
}
