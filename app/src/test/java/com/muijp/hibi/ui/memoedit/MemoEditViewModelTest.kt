package com.muijp.hibi.ui.memoedit

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

class MemoEditViewModelTest {
    companion object {
        val mockMemo = Memo("mockMemoId", "existing memo", ZonedDateTime.now(), ZonedDateTime.now())
    }

    @get:Rule
    val testRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @MockK
    lateinit var memoRepository: MemoRepository

    @InjectMockKs
    lateinit var viewModel: MemoEditViewModel

    @Before
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun retrieveMemo_newMemo() {
        // given

        // when
        viewModel.retrieveMemo(null)

        // then
        assertThat(viewModel.title).isEqualTo("メモ作成")
        assertThat(viewModel.isNewMemo).isEqualTo(true)

        coVerify(exactly = 0) { memoRepository.find(any()) }
        confirmVerified()
    }

    @Test
    fun retrieveMemo_existingMemo() {
        // given
        coEvery { memoRepository.find(mockMemo.id) } returns mockMemo

        // when
        viewModel.retrieveMemo(mockMemo.id)

        // then
        assertThat(viewModel.title).isEqualTo("メモ編集")
        assertThat(viewModel.isNewMemo).isEqualTo(false)
        assertThat(viewModel.inputText).isEqualTo(mockMemo.text)

        coVerify(exactly = 1) { memoRepository.find(mockMemo.id) }
        confirmVerified()
    }

    @Test
    fun onMemoTextUpdated_memoInputted() {
        // given
        coEvery { memoRepository.find(mockMemo.id) } returns mockMemo
        coEvery { memoRepository.upsert(any()) } returns Unit

        viewModel.retrieveMemo(mockMemo.id)

        // when
        viewModel.onMemoTextUpdated("updated memo")

        // then
        assertThat(viewModel.inputText).isEqualTo("updated memo")

        coVerify(exactly = 1) {
            memoRepository.upsert(mockMemo.copy(text = "updated memo"))
        }
        confirmVerified()
    }

    @Test
    fun onMemoTextUpdated_memoEmptied() {
        // given
        coEvery { memoRepository.find(mockMemo.id) } returns mockMemo
        coEvery { memoRepository.upsert(any()) } returns Unit
        coEvery { memoRepository.delete(any()) } returns Unit

        viewModel.retrieveMemo(mockMemo.id)

        // when
        viewModel.onMemoTextUpdated("")

        // then
        coVerify(exactly = 0) {
            memoRepository.upsert(any())
        }
        coVerify(exactly = 1) {
            memoRepository.delete(mockMemo)
        }
        confirmVerified()
    }

    @Test
    fun onMemoDeleted() {
        // given
        coEvery { memoRepository.find(mockMemo.id) } returns mockMemo
        coEvery { memoRepository.delete(any()) } returns Unit

        viewModel.retrieveMemo(mockMemo.id)

        // when
        viewModel.onMemoDeleted()

        // then
        assertThat(viewModel.navToBack).isEqualTo(true)

        coVerify(exactly = 1) {
            memoRepository.delete(mockMemo)
        }
        confirmVerified()
    }
}