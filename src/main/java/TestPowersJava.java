import com.github.kobting.annotations.PowerString;
import com.github.kobting.annotations.data.Language;

public class TestPowersJava {

    private static final String PREFIX = "kobting";

    @PowerString(prefix = PREFIX, name = "powerOneJava", descriptions = { "This is power One!" }, language = Language.ENGLISH)
    @PowerString(prefix = PREFIX, name = "powerOneJava", descriptions = { "This is power One! Spanish" }, language = Language.SPANISH)
    class Power1Java {}

    @PowerString(prefix = PREFIX, name = "powerTwoJava", descriptions = { "This is power Two!" }, language = Language.ENGLISH)
    @PowerString(prefix = PREFIX, name = "powerTwoJava", descriptions = { "This is power Two! Spanish" }, language = Language.SPANISH)
    class Power2Java {}

    @PowerString(prefix = PREFIX, name = "powerThreeJava", descriptions = { "This is power Three!" }, language = Language.ENGLISH)
    @PowerString(prefix = PREFIX, name = "powerThreeJava", descriptions = { "This is power Three! Spanish" }, language = Language.SPANISH)
    class Power3Java {}

}
