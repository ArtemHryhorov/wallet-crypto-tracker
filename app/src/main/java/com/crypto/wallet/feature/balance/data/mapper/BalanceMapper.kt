package com.crypto.wallet.feature.balance.data.mapper

import com.crypto.wallet.feature.balance.data.entity.BalanceEntity
import com.crypto.wallet.feature.balance.domain.model.Balance

fun BalanceEntity.toDomain(): Balance = Balance(amount)

fun Balance.toEntity(): BalanceEntity = BalanceEntity(amount = value)
