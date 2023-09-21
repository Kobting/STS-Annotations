import com.github.kobting.annotations.RelicString
import com.github.kobting.annotations.data.Language

private const val PREFIX = "kobting"

@RelicString(prefix = PREFIX, name = "relicOne", flavor = "relic One flavor.", descriptions = ["This is relic One!", "Description Two"], language = Language.ENGLISH)
@RelicString(prefix = PREFIX, name = "relicOne", flavor = "relic One flavor. Spanish", descriptions = ["This is relic One! Spanish"], language = Language.SPANISH)
class Relic1()

@RelicString(prefix = PREFIX, name = "relicTwo", flavor = "relic Two flavor.", descriptions = ["This is relic Two!"], language = Language.ENGLISH)
@RelicString(prefix = PREFIX, name = "relicTwo", flavor = "relic Two flavor. Spanish", descriptions = ["This is relic Two! Spanish"], language = Language.SPANISH)
class Relic2()

@RelicString(prefix = PREFIX, name = "relicThree", flavor = "relic Three flavor.", descriptions = ["This is relic Three!"], language = Language.ENGLISH)
@RelicString(prefix = PREFIX, name = "relicThree", flavor = "relic Three flavor. Spanish", descriptions = ["This is relic Three! Spanish"], language = Language.SPANISH)
class Relic3()