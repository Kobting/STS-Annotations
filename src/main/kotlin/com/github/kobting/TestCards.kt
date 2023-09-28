package com.github.kobting

import basemod.abstracts.CustomCard
import com.github.kobting.annotations.Card
import com.github.kobting.annotations.CardString
import com.github.kobting.annotations.data.Language
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster

private const val PREFIX = "kobting"

@CardString(prefix = PREFIX, name = "cardOne", language = Language.ENGLISH, description = "This is card One!")
@CardString(prefix = PREFIX, name = "cardOne", description = "This is card One! Spanish", language = Language.SPANISH)
@CardString(prefix = PREFIX, name = "cardOne", description = "This is card One! Spanish", language = Language.GERMAN)
@Card
class Card1: CustomCard(
    "",
    "",
    "",
    1,
    "",
    CardType.ATTACK,
    CardColor.RED,
    CardRarity.COMMON,
    CardTarget.ENEMY
) {
    override fun upgrade() {

    }

    override fun use(p0: AbstractPlayer?, p1: AbstractMonster?) {

    }


}

@CardString(prefix = PREFIX, name = "cardTwo", description = "This is card Two!", upgradeDescription = "Upgraded", language = Language.ENGLISH)
@CardString(prefix = PREFIX, name = "cardTwo", description = "This is card Two! Spanish", extendedDescriptions = ["One", "Two", "Three!"], language = Language.SPANISH)
class Card2()

@CardString(prefix = PREFIX, name = "cardThree", description = "This is card Three!", language = Language.ENGLISH)
@CardString(prefix = PREFIX, name = "cardThree", description = "This is card Three! Spanish", language = Language.SPANISH)
class Card3()