package com.github.kobting

import com.github.kobting.annotations.PowerString
import com.github.kobting.annotations.data.Language

private const val PREFIX = "kobting"

@PowerString(prefix = PREFIX, name = "powerOne", descriptions = ["This is power One!"], language = Language.ENGLISH)
@PowerString(prefix = PREFIX, name = "powerOne", descriptions = ["This is power One! Spanish"], language = Language.SPANISH)
class Power1()

@PowerString(prefix = PREFIX, name = "powerTwo", descriptions = ["This is power Two!"], language = Language.ENGLISH)
@PowerString(prefix = PREFIX, name = "powerTwo", descriptions = ["This is power Two! Spanish"], language = Language.SPANISH)
class Power2()

@PowerString(prefix = PREFIX, name = "powerThree", descriptions = ["This is power Three!"], language = Language.ENGLISH)
@PowerString(prefix = PREFIX, name = "powerThree", descriptions = ["This is power Three! Spanish"], language = Language.SPANISH)
class Power3()