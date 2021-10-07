package com.muijp.hibi

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.jraska.livedata.test
import com.muijp.hibi.database.memo.Memo
import com.muijp.hibi.extension.formattedDate
import com.muijp.hibi.repository.MemoRepository
import com.muijp.hibi.ui.memosearch.MemoSearchViewModel
import com.muijp.hibi.ui.recyclerview.memolist.MemoListItem
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description
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
        viewModel.query.value = ""
        val itemsObserver = viewModel.items.test()

        // when
        viewModel.searchMemos()

        // then
        itemsObserver
            .assertHasValue()
            .assertValue { it.isEmpty() }
            .assertHistorySize(1)

        coVerify(exactly = 0) { memoRepository.search(any()) }
    }

    @Test
    fun searchMemos_queryInputted() {
        // given
        coEvery {
            memoRepository.search("a")
        } returns listOf(Memo("dummyId", "abc", ZonedDateTime.now(), ZonedDateTime.now()))

        val itemsObserver = viewModel.items.test()

        // when
        viewModel.query.value = ""
        viewModel.searchMemos()
        viewModel.query.value = "a"
        viewModel.searchMemos()

        // then
        val history = itemsObserver.valueHistory()
        assertThat(history.size).isEqualTo(2)
        assertThat(history[0].isEmpty())

        val items = history[1]
        assertThat(items.size).isEqualTo(2)
        assertThat((items[0] as MemoListItem.HeaderItem).formattedDate).isEqualTo(ZonedDateTime.now().toLocalDate().formattedDate)
        val memoItem = items[1] as MemoListItem.MemoItem
        assertThat(memoItem.id).isEqualTo("dummyId")
        assertThat(memoItem.text).isEqualTo("abc")

        coVerify(exactly = 1) { memoRepository.search("a") }
        confirmVerified()
    }
}

@ExperimentalCoroutinesApi
class MainCoroutineRule : TestWatcher() {
    override fun starting(description: Description?) {
        super.starting(description)
        Dispatchers.setMain(TestCoroutineDispatcher())
    }

    override fun finished(description: Description?) {
        super.finished(description)
        Dispatchers.resetMain()
    }
}