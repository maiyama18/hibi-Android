package com.muijp.hibi.ui.memosearch

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.muijp.hibi.database.memo.Memo
import com.muijp.hibi.repository.MemoRepository
import com.muijp.hibi.ui.utils.MainCoroutineRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.ZonedDateTime

class MemoSearchViewModelUnitTest {
    @get:Rule
    val testRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @MockK
    lateinit var memoRepository: MemoRepository

    @InjectMockKs
    lateinit var viewModel: MemoSearchViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun searchMemos_queryEmpty() {
        // given

        // when
        viewModel.onQueryChanged("")

        // then
        assertThat(viewModel.memos.size).isEqualTo(0)

        coVerify(exactly = 0) { memoRepository.search(any(), any()) }
    }

    @Test
    fun searchMemos_queryInputted() {
        // given
        coEvery {
            memoRepository.search("a", any())
        } returns listOf(
            Memo("dummyId1", "abc", ZonedDateTime.now(), ZonedDateTime.now()),
            Memo("dummyId2", "edf", ZonedDateTime.now(), ZonedDateTime.now()),
        )

        // when
        viewModel.onQueryChanged("a")

        // then
        assertThat(viewModel.memos.size).isEqualTo(2)
        assertThat(viewModel.memos[0].id).isEqualTo("dummyId1")
        assertThat(viewModel.memos[0].text).isEqualTo("abc")
        assertThat(viewModel.memos[1].id).isEqualTo("dummyId2")
        assertThat(viewModel.memos[1].text).isEqualTo("edf")

        coVerify(exactly = 1) { memoRepository.search("a", any()) }
        confirmVerified()
    }
}