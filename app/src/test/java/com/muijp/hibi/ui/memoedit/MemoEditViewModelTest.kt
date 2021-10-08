package com.muijp.hibi.ui.memoedit

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.jraska.livedata.test
import com.muijp.hibi.MainCoroutineRule
import com.muijp.hibi.R
import com.muijp.hibi.database.memo.Memo
import com.muijp.hibi.extension.formattedDateTime
import com.muijp.hibi.provider.StringProvider
import com.muijp.hibi.repository.MemoRepository
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.ZonedDateTime

class MemoEditViewModelTest {
    companion object {
        val memo = Memo("memoId", "existing memo", ZonedDateTime.now(), ZonedDateTime.now())
    }

    @get:Rule
    val testRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @MockK
    lateinit var memoRepository: MemoRepository

    @MockK
    lateinit var stringProvider: StringProvider

    @MockK
    lateinit var state: SavedStateHandle

    @InjectMockKs
    lateinit var viewModel: MemoEditViewModel

    @Before
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun retrieveMemo_newMemo() {
        // given
        every { state.get<String>("id") } returns null
        every { stringProvider.getString(R.string.new_memo) } returns "new memo"

        // when
        viewModel.retrieveMemo()

        // then
        viewModel.title.test()
            .assertHasValue()
            .assertValue("new memo")

        confirmVerified()
    }

    @Test
    fun retrieveMemo_existingMemo() {
        // given
        every { state.get<String>("id") } returns "memoId"
        coEvery { memoRepository.find("memoId") } returns memo

        // when
        viewModel.retrieveMemo()

        // then
        viewModel.title.test()
            .assertHasValue()
            .assertValue(memo.createdAt.formattedDateTime)

        viewModel.memoText.test()
            .assertHasValue()
            .assertValue(memo.text)

        confirmVerified()
    }

    @Test
    fun onMemoTextUpdated_memoInputted() {
        // given
        every { state.get<String>("id") } returns "memoId"
        coEvery { memoRepository.find("memoId") } returns memo
        coEvery { memoRepository.upsert(any()) } returns Unit

        viewModel.retrieveMemo()

        viewModel.memoText.value = "updated memo"

        // when
        viewModel.onMemoTextUpdated()

        // then
        coVerify(exactly = 1) {
            memoRepository.upsert(memo.copy(text = "updated memo"))
        }
        confirmVerified()
    }

    @Test
    fun onMemoTextUpdated_memoEmptied() {
        // given
        every { state.get<String>("id") } returns "memoId"
        coEvery { memoRepository.find("memoId") } returns memo
        coEvery { memoRepository.upsert(any()) } returns Unit

        viewModel.retrieveMemo()

        viewModel.memoText.value = ""

        // when
        viewModel.onMemoTextUpdated()

        // then
        coVerify(exactly = 0) {
            memoRepository.upsert(any())
        }
        confirmVerified()
    }

    @Test
    fun onMemoDeleted() {
        // given
        every { state.get<String>("id") } returns "memoId"
        coEvery { memoRepository.find("memoId") } returns memo
        coEvery { memoRepository.delete(memo) } returns Unit

        viewModel.retrieveMemo()

        // when
        viewModel.onMemoDeleted()

        // then
        viewModel.backToPrevious.test()
            .assertHasValue()
            .assertValue(true)

        coVerify(exactly = 1) {
            memoRepository.delete(memo)
        }
        confirmVerified()
    }
}