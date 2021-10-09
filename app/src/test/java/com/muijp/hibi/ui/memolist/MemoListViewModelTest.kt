package com.muijp.hibi.ui.memolist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.jraska.livedata.test
import com.muijp.hibi.MainCoroutineRule
import com.muijp.hibi.database.memo.Memo
import com.muijp.hibi.repository.MemoRepository
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.ZonedDateTime
import java.util.concurrent.TimeUnit

class MemoListViewModelTest {
    @get:Rule
    val testRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @MockK
    lateinit var memoRepository: MemoRepository

    @Before
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun init() {
        // given
        every {
            memoRepository.liveDataByLimit(10)
        } returns MutableLiveData(
            (1..10).map { i-> Memo("memoId$i", "this is memo $i", ZonedDateTime.now(), ZonedDateTime.now()) }
        )

        // when
        val viewModel = MemoListViewModel(memoRepository)

        // then
        viewModel.items.test()
            .assertHasValue()
            .assertValue { it.size == 11 }
            .assertValue { it.last().id == "memoId10" }

        confirmVerified()
    }

    @Test
    fun onScrolledToBottom_allMemosAlreadyShown() {
        // given
        every {
            memoRepository.liveDataByLimit(10)
        } returns MutableLiveData(
            (1..10).map { i-> Memo("memoId$i", "this is memo $i", ZonedDateTime.now(), ZonedDateTime.now()) }
        )

        coEvery {
            memoRepository.count()
        } returns 10

        val viewModel = MemoListViewModel(memoRepository)

        viewModel.items.test()
            .awaitValue()
            .assertHasValue()
            .assertValue { it.size == 11 }

        // when
        viewModel.onScrolledToBottom()

        // then
        viewModel.items.test()
            .assertHasValue()
            .assertValue { it.size == 11 }

        verify(exactly = 1) { memoRepository.liveDataByLimit(10) }
        verify(exactly = 0) { memoRepository.liveDataByLimit(20) }
        coVerify(exactly = 1) { memoRepository.count() }
        confirmVerified()
    }

    @Test
    fun onScrolledToBottom_readMoreMemos() {
        // given
        every {
            memoRepository.liveDataByLimit(10)
        } returns MutableLiveData(
            (1..10).map { i-> Memo("memoId$i", "this is memo $i", ZonedDateTime.now(), ZonedDateTime.now()) }
        )

        coEvery {
            memoRepository.count()
        } returns 100

        every {
            memoRepository.liveDataByLimit(20)
        } returns MutableLiveData(
            (1..20).map { i-> Memo("memoId$i", "this is memo $i", ZonedDateTime.now(), ZonedDateTime.now()) }
        )

        val viewModel = MemoListViewModel(memoRepository)

        viewModel.items.test()
            .awaitValue()
            .assertHasValue()
            .assertValue { it.size == 11 }

        // when
        viewModel.onScrolledToBottom()

        // then
        viewModel.items.test()
            .awaitNextValue(1, TimeUnit.SECONDS)
            .assertHasValue()
            .assertValue { it.size == 21 }

        verify(exactly = 1) {
            memoRepository.liveDataByLimit(10)
            memoRepository.liveDataByLimit(20)
        }
        coVerify(exactly = 1) { memoRepository.count() }
        confirmVerified()
    }
}