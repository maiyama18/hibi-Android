package com.muijp.hibi.ui.memoedit

//class MemoEditViewModelTest {
//    companion object {
//        val memo = Memo("memoId", "existing memo", ZonedDateTime.now(), ZonedDateTime.now())
//    }
//
//    @get:Rule
//    val testRule = InstantTaskExecutorRule()
//
//    @ExperimentalCoroutinesApi
//    @get:Rule
//    val mainCoroutineRule = MainCoroutineRule()
//
//    @MockK
//    lateinit var memoRepository: MemoRepository
//
//    @MockK
//    lateinit var stringProvider: StringProvider
//
//    @MockK
//    lateinit var state: SavedStateHandle
//
//    @InjectMockKs
//    lateinit var viewModel: MemoEditViewModel
//
//    @Before
//    fun setUp() = MockKAnnotations.init(this)
//
//    @Test
//    fun retrieveMemo_newMemo() {
//        // given
//        every { state.get<String>("id") } returns null
//        every { stringProvider.getString(R.string.new_memo) } returns "new memo"
//
//        // when
//        viewModel.retrieveMemo()
//
//        // then
//        viewModel.title.test()
//            .assertHasValue()
//            .assertValue("new memo")
//
//        confirmVerified()
//    }
//
//    @Test
//    fun retrieveMemo_existingMemo() {
//        // given
//        every { state.get<String>("id") } returns "memoId"
//        coEvery { memoRepository.find("memoId") } returns memo
//
//        // when
//        viewModel.retrieveMemo()
//
//        // then
//        viewModel.title.test()
//            .assertHasValue()
//            .assertValue(memo.createdAt.formattedDateTime)
//
//        viewModel.inputText.test()
//            .assertHasValue()
//            .assertValue(memo.text)
//
//        confirmVerified()
//    }
//
//    @Test
//    fun onMemoTextUpdated_memoInputted() {
//        // given
//        every { state.get<String>("id") } returns "memoId"
//        coEvery { memoRepository.find("memoId") } returns memo
//        coEvery { memoRepository.upsert(any()) } returns Unit
//
//        viewModel.retrieveMemo()
//
//        viewModel.inputText.value = "updated memo"
//
//        // when
//        viewModel.onMemoTextUpdated()
//
//        // then
//        coVerify(exactly = 1) {
//            memoRepository.upsert(memo.copy(text = "updated memo"))
//        }
//        confirmVerified()
//    }
//
//    @Test
//    fun onMemoTextUpdated_memoEmptied() {
//        // given
//        every { state.get<String>("id") } returns "memoId"
//        coEvery { memoRepository.find("memoId") } returns memo
//        coEvery { memoRepository.upsert(any()) } returns Unit
//
//        viewModel.retrieveMemo()
//
//        viewModel.inputText.value = ""
//
//        // when
//        viewModel.onMemoTextUpdated()
//
//        // then
//        coVerify(exactly = 0) {
//            memoRepository.upsert(any())
//        }
//        confirmVerified()
//    }
//
//    @Test
//    fun onMemoDeleted() {
//        // given
//        every { state.get<String>("id") } returns "memoId"
//        coEvery { memoRepository.find("memoId") } returns memo
//        coEvery { memoRepository.delete(memo) } returns Unit
//
//        viewModel.retrieveMemo()
//
//        // when
//        viewModel.onMemoDeleted()
//
//        // then
//        viewModel.backToPrevious.test()
//            .assertHasValue()
//            .assertValue(true)
//
//        coVerify(exactly = 1) {
//            memoRepository.delete(memo)
//        }
//        confirmVerified()
//    }
//}