package com.paularolim.countdown.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

class PairMediatorLiveData<F, S>(first: LiveData<F>, second: LiveData<S>) :
    MediatorLiveData<Pair<F?, S?>>() {
        init {
            addSource(first) { first: F -> value = first to second.value }
            addSource(second) { second: S -> value = first.value to second }
        }
}