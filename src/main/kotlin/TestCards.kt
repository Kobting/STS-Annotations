import com.github.kobting.annotations.CardString
import com.github.kobting.annotations.data.Language

private const val PREFIX = "kobting"

@CardString(prefix = PREFIX, name = "cardOne", language = Language.ENGLISH, description = "This is card One!")
@CardString(prefix = PREFIX, name = "cardOne", description = "This is card One! Spanish", language = Language.SPANISH)
@CardString(prefix = PREFIX, name = "cardOne", description = "This is card One! Spanish", language = Language.GERMAN)
class Card1()

@CardString(prefix = PREFIX, name = "cardTwo", description = "This is card Two!", upgradeDescription = "Upgraded", language = Language.ENGLISH)
@CardString(prefix = PREFIX, name = "cardTwo", description = "This is card Two! Spanish", extendedDescriptions = ["One", "Two", "Three!"], language = Language.SPANISH)
class Card2()

@CardString(prefix = PREFIX, name = "cardThree", description = "This is card Three!", language = Language.ENGLISH)
@CardString(prefix = PREFIX, name = "cardThree", description = "This is card Three! Spanish", language = Language.SPANISH)
class Card3()