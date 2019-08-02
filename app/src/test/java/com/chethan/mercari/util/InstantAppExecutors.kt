package com.chethan.mercari.util

import com.chethan.mercari.AppExecutors
import java.util.concurrent.Executor

/**
 * Created by Chethan on 7/30/2019.
 */

class InstantAppExecutors : AppExecutors(instant, instant, instant) {
    companion object {
        private val instant = Executor { it.run() }
    }
}
