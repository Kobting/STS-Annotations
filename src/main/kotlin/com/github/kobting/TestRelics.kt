package com.github.kobting

import basemod.abstracts.CustomRelic
import basemod.helpers.RelicType
import com.github.kobting.annotations.BaseRelic
import com.github.kobting.annotations.Relic
import com.github.kobting.annotations.RelicString
import com.github.kobting.annotations.data.Language
import com.megacrit.cardcrawl.cards.AbstractCard

private const val PREFIX = "kobting"

@RelicString(prefix = PREFIX, name = "relicOne", flavor = "relic One flavor.", descriptions = ["This is relic One!", "Description Two"], language = Language.ENGLISH)
@RelicString(prefix = PREFIX, name = "relicOne", flavor = "relic One flavor. Spanish", descriptions = ["This is relic One! Spanish"], language = Language.SPANISH)
@Relic(color = AbstractCard.CardColor.COLORLESS)
class Relic1(): CustomRelic(
    "", "", RelicTier.COMMON, LandingSound.CLINK
) {

}

@RelicString(prefix = PREFIX, name = "relicTwo", flavor = "relic Two flavor.", descriptions = ["This is relic Two!"], language = Language.ENGLISH)
@RelicString(prefix = PREFIX, name = "relicTwo", flavor = "relic Two flavor. Spanish", descriptions = ["This is relic Two! Spanish"], language = Language.SPANISH)
@BaseRelic(type = RelicType.BLUE)
class Relic2(): CustomRelic(
    "", "", RelicTier.COMMON, LandingSound.CLINK
) {

}

@RelicString(prefix = PREFIX, name = "relicThree", flavor = "relic Three flavor.", descriptions = ["This is relic Three!"], language = Language.ENGLISH)
@RelicString(prefix = PREFIX, name = "relicThree", flavor = "relic Three flavor. Spanish", descriptions = ["This is relic Three! Spanish"], language = Language.SPANISH)
class Relic3()