package com.muijp.hibi.ui.memolist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.google.common.truth.Truth.assertThat
import com.jraska.livedata.test
import com.muijp.hibi.database.memo.Memo
import com.muijp.hibi.repository.MemoRepository
import com.muijp.hibi.ui.utils.MainCoroutineRule
import io.mockk.MockKAnnotations
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.ZonedDateTime

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
        val today = ZonedDateTime.now()
        val yesterday = ZonedDateTime.now().minusDays(1)
        // given
        every {
            memoRepository.liveDataByLimit(10)
        } returns MutableLiveData(
            (1..10).map { i ->
                Memo(
                    "memoId$i",
                    "this is memo $i",
                    if (i >= 8) yesterday else today,
                    if (i >= 8) yesterday else today
                )
            }
        )

        // when
        val viewModel = MemoListViewModel(memoRepository)

        // then
        val memos = viewModel.memos.test()
            .value()

        assertThat(memos.keys.size).isEqualTo(2)
        assertThat(memos[today.toLocalDate()]?.size).isEqualTo(7)
        assertThat(memos[yesterday.toLocalDate()]?.size).isEqualTo(3)

        confirmVerified()
    }
}