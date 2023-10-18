package com.github.kobting.annotations

import basemod.helpers.RelicType
import com.megacrit.cardcrawl.cards.AbstractCard

annotation class Relic(
    val color: AbstractCard.CardColor
)

annotation class BaseRelic(
    val type: RelicType
)
