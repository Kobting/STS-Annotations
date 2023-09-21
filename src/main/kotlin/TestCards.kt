import com.github.kobting.annotations.CardString
import com.github.kobting.annotations.data.Language

private const val PREFIX = "kobting"

@CardString(prefix = PREFIX, name = "cardOne", description = "This is card One!", language = Language.ENGLISH)
@CardString(prefix = PREFIX, name = "cardOne", description = "This is card One! Spanish", language = Language.SPANISH)
//@CardString(prefix = PREFIX, name = "cardOne", description = "This is card One! Spanish", language = Language.GERMAN)
class Card1()

@CardString(prefix = PREFIX, name = "cardTwo", description = "This is card Two!", language = Language.ENGLISH)
@CardString(prefix = PREFIX, name = "cardTwo", description = "This is card Two! Spanish", language = Language.SPANISH)
class Card2()

@CardString(prefix = PREFIX, name = "cardThree", description = "This is card Three!", language = Language.ENGLISH)
@CardString(prefix = PREFIX, name = "cardThree", description = "This is card Three! Spanish", language = Language.SPANISH)
class Card3()