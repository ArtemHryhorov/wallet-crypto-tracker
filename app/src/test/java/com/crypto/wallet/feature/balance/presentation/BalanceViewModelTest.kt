package com.crypto.wallet.feature.balance.presentation

import app.cash.turbine.test
import com.crypto.wallet.feature.balance.domain.model.Balance
import com.crypto.wallet.feature.balance.domain.usecase.ChangeBalance
import com.crypto.wallet.feature.balance.domain.usecase.CreateUserBalance
import com.crypto.wallet.feature.balance.domain.usecase.GetBtcRate
import com.crypto.wallet.feature.balance.domain.usecase.GetUserBalance
import com.crypto.wallet.feature.balance.domain.usecase.IsValidAmount
import com.crypto.wallet.feature.balance.presentation.model.AmountUiModel
import com.crypto.wallet.feature.transaction.domain.usecase.AddTransaction
import com.crypto.wallet.feature.transaction.domain.usecase.GetAllTransactions
import com.crypto.wallet.ui.common.TextUiModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
@ExperimentalCoroutinesApi
class BalanceViewModelTest {

  private val testDispatcher = StandardTestDispatcher()
  private val getUserBalance: GetUserBalance = mockk(relaxed = true)
  private val getBtcRate: GetBtcRate = mockk(relaxed = true)
  private val isValidAmount: IsValidAmount = mockk()
  private val changeBalance: ChangeBalance = mockk()
  private val createUserBalance: CreateUserBalance = mockk()
  private val addTransaction: AddTransaction = mockk()
  private val getAllTransactions: GetAllTransactions = FakeGetAllTransactions()

  private val loadingState = BalanceState.loading

  private val viewModel: BalanceViewModel by lazy {
    BalanceViewModel(
      dispatcher = testDispatcher,
      getAllTransactions = getAllTransactions,
      getUserBalance = getUserBalance,
      getBtcRate = getBtcRate,
      isValidAmount = isValidAmount,
      changeBalance = changeBalance,
      createUserBalance = createUserBalance,
      addTransaction = addTransaction
    )
  }

  @Before
  fun setup() {
    Dispatchers.setMain(testDispatcher)
  }

  @After
  fun tearDown() {
    Dispatchers.resetMain()
  }

  @Test
  fun `initial state is loading`() = runTest {
    val expected = loadingState

    coEvery { getUserBalance() } returns flowOf(Result.success(Balance(100.0)))
    coEvery { getBtcRate() } returns flowOf(Result.success("91000.00000"))

    viewModel.state.test {
      assertEquals(expected, awaitItem())
    }
  }

  @Test
  fun `when top up click, then show top-up dialog`() = runTest {
    val expected = loadingState.copy(
      showTopUpDialog = true,
    )

    coEvery { getUserBalance() } returns flowOf(Result.success(Balance(100.0)))
    coEvery { getBtcRate() } returns flowOf(Result.success("91000.00000"))

    viewModel.state.test {
      skipItems(1)
      viewModel.onEvent(BalanceEvent.ShowTopUpDialog)
      assertEquals(expected, awaitItem())
    }
  }

  @Test
  fun `when top up dismiss, then dismiss top-up dialog`() = runTest {
    val expectedShowDialog = loadingState.copy(
      showTopUpDialog = true,
    )

    coEvery { getUserBalance() } returns flowOf(Result.success(Balance(100.0)))
    coEvery { getBtcRate() } returns flowOf(Result.success("91000.00000"))

    viewModel.state.test {
      skipItems(1)
      viewModel.onEvent(BalanceEvent.ShowTopUpDialog)
      assertEquals(expectedShowDialog, awaitItem())
      viewModel.onEvent(BalanceEvent.DismissTopUpDialog)
      assertEquals(loadingState, awaitItem())
    }
  }

  @Test
  fun `given input is correct, when validate top up, then validation is valid`() = runTest {
    val input = "100.0"
    val expected = loadingState.copy(
      isTopUpDialogValueValid = true,
      topUpDialogValue = input,
    )

    coEvery { getUserBalance() } returns flowOf(Result.success(Balance(100.0)))
    coEvery { getBtcRate() } returns flowOf(Result.success("91000.00000"))
    coEvery { isValidAmount(input) } returns true

    viewModel.state.test {
      skipItems(1)
      viewModel.onEvent(BalanceEvent.ValidateTopUpValue(input))
      assertEquals(expected, awaitItem())
    }
  }

  @Test
  fun `given input is incorrect, when validate top up, then validation is valid`() = runTest {
    val input = "100.0.0"
    val expected = loadingState.copy(
      isTopUpDialogValueValid = false,
      topUpDialogValue = input,
    )

    coEvery { getUserBalance() } returns flowOf(Result.success(Balance(100.0)))
    coEvery { getBtcRate() } returns flowOf(Result.success("91000.00000"))
    coEvery { isValidAmount(input) } returns false

    viewModel.state.test {
      skipItems(1)
      viewModel.onEvent(BalanceEvent.ValidateTopUpValue(input))
      assertEquals(expected, awaitItem())
    }
  }

  @Test
  fun `given BTC rate available, when fetch rate, then rate updated`() = runTest {
    val expected = loadingState.copy(
      btcRate = "91000.00000",
      balance = AmountUiModel(value= TextUiModel("100.00000 BTC"))
    )

    coEvery { getUserBalance() } returns flowOf(Result.success(Balance(100.0)))
    coEvery { getBtcRate() } returns flowOf(Result.success("91000.00000"))

    viewModel.state.test {
      skipItems(2)
      assertEquals(expected, awaitItem())
    }
  }
}