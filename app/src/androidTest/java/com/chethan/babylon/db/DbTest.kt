package com.chethan.babylon

import androidx.arch.core.executor.testing.CountingTaskExecutorRule
import androidx.room.Room
import androidx.test.InstrumentationRegistry
import com.chethan.babylon.db.AppDatabase
import org.junit.After
import org.junit.Before
import org.junit.Rule
import java.util.concurrent.TimeUnit

/**
 * Created by Chethan on 7/30/2019.
 */
abstract class DbTest {
    @Rule
    @JvmField
    val countingTaskExecutorRule = CountingTaskExecutorRule()
    private lateinit var _db: AppDatabase
    val db: AppDatabase
        get() = _db

    @Before
    fun initDb() {
        _db = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getContext(),
            AppDatabase::class.java
        ).build()
    }

    @After
    fun closeDb() {
        countingTaskExecutorRule.drainTasks(10, TimeUnit.SECONDS)
        _db.close()
    }
}
