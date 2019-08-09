package com.chethan.babylon.util

import com.chethan.babylon.AppExecutors
import java.util.concurrent.Executor

/**
 * Created by Chethan on 7/30/2019.
 */

class InstantAppExecutors : AppExecutors(instant, instant, instant) {
    companion object {
        private val instant = Executor { it.run() }
    }
}
