package com.chethan.babylon.util

import org.mockito.ArgumentCaptor
import org.mockito.Mockito

/**
 *
 * Created by Chethan on 7/30/2019.
 * a kotlin friendly mock that handles generics
 */
inline fun <reified T> mock(): T = Mockito.mock(T::class.java)

inline fun <reified T> argumentCaptor(): ArgumentCaptor<T> = ArgumentCaptor.forClass(T::class.java)