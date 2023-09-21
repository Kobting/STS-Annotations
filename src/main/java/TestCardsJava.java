import com.github.kobting.annotations.CardString;
import com.github.kobting.annotations.data.Language;

public class TestCardsJava {

    private static final String PREFIX = "kobting";

    @CardString(prefix = PREFIX, name = "cardOneJava", description = "This is card One!", language = Language.ENGLISH)
    @CardString(prefix = PREFIX, name = "cardOneJava", description = "This is card One! Spanish", language = Language.SPANISH)
    class Card1Java {}

    @CardString(prefix = PREFIX, name = "cardTwoJava", description = "This is card Two!", language = Language.ENGLISH)
    @CardString(prefix = PREFIX, name = "cardTwoJava", description = "This is card Two! Spanish", language = Language.SPANISH)
    class Card2Java {}

    @CardString(prefix = PREFIX, name = "cardThreeJava", description = "This is card Three!", language = Language.ENGLISH)
    @CardString(prefix = PREFIX, name = "cardThreeJava", description = "This is card Three! Spanish", language = Language.SPANISH)
    class Card3Java {}

}
