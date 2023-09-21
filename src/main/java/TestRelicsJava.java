import com.github.kobting.annotations.RelicString;
import com.github.kobting.annotations.data.Language;

public class TestRelicsJava {

    private static final String PREFIX = "kobting";

    @RelicString(prefix = PREFIX, name = "relicOneJava", flavor = "relic One flavor.", descriptions = { "This is relic One!" }, language = Language.ENGLISH)
    @RelicString(prefix = PREFIX, name = "relicOneJava", flavor = "relic One flavor. Spanish", descriptions = { "This is relic One! Spanish" }, language = Language.SPANISH)
    class Relic1Java {}

    @RelicString(prefix = PREFIX, name = "relicTwoJava", flavor = "relic Two flavor.", descriptions = { "This is relic Two!" }, language = Language.ENGLISH)
    @RelicString(prefix = PREFIX, name = "relicTwoJava", flavor = "relic Two flavor. Spanish", descriptions = { "This is relic Two! Spanish" }, language = Language.SPANISH)
    class Relic2Java {}

    @RelicString(prefix = PREFIX, name = "relicThreeJava", flavor = "relic Three flavor.", descriptions = { "This is relic Three!" }, language = Language.ENGLISH)
    @RelicString(prefix = PREFIX, name = "relicThreeJava", flavor = "relic Three flavor. Spanish", descriptions = { "This is relic Three! Spanish" }, language = Language.SPANISH)
    class Relic3Java {}

}
