# STS-Annotations

This project is aimed at adding annotations to remove the boilerplate code required when creating a STS mod.

## TODO

Figure out how to create dependencies and host them on GitHub packages, so it is easier to use this.

## Building

This is currently setup with gradle and using my project [sts-dependencies](https://github.com/Kobting/sts-dependencies) for generating dependencies used in these gradle files. 
You would have to pull that and build it to your maven local repository to build this project.
Once that is done, and you run gradle sync it should generate a file called `stsdependencies.config` that needs 1 line of text that is
the path to your installation of Steam.

## Current features

**Note: None of this has been tested yet**

1. `@CardString` for auto generating the card strings file.  
    Example:
    ```kotlin
   import com.github.kobting.annotations.CardString
   import com.github.kobting.annotations.data.Language
   
    @CardString(prefix = PREFIX, name = "cardOne", description = "This is card One!", language = Language.ENGLISH)
    @CardString(prefix = PREFIX, name = "cardOne", description = "This is card One! Spanish", language = Language.SPANISH)
   class Card()
   ```
2. `@RelicString` for auto generating the relic strings file.  
    Example:
    ```kotlin
    import com.github.kobting.annotations.RelicString
    import com.github.kobting.annotations.data.Language
   
    @RelicString(prefix = PREFIX, name = "relicOne", flavor = "relic One flavor.", descriptions = ["This is relic One!", "Description Two"], language = Language.ENGLISH)
    @RelicString(prefix = PREFIX, name = "relicOne", flavor = "relic One flavor. Spanish", descriptions = ["This is relic One! Spanish"], language = Language.SPANISH)
    class Relic()
    ```
3. `@PowerString` for auto generating the power strings file
    Example:
    ```kotlin
    import com.github.kobting.annotations.PowerString
    import com.github.kobting.annotations.data.Language
   
    @PowerString(prefix = PREFIX, name = "powerOne", descriptions = ["This is power One!"], language = Language.ENGLISH)
    @PowerString(prefix = PREFIX, name = "powerOne", descriptions = ["This is power One! Spanish"], language = Language.SPANISH)
    class Power1()
    ```
4. `@AutoSpireInitializer` for generating a class that implements basemod subscribers. Currently only supports `EditStringsSubscriber` when there exists classes annotated with one of the String annotations.   
    Example:
    ```kotlin
    import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer
    import com.github.kobting.annotations.AutoSpireInitializer
    
    @SpireInitializer //Required or @AutoSpireInitializer will not work
    @AutoSpireInitializer
    class TestInitializer : AutoInitialize() {
        //The generated clas AutoInitialize has all the code for loading the string files based on the games current set language.
    }
    ```


https://github.com/Kobting/STS-Annotations/assets/40338395/0ec106ac-d22a-4131-a61c-f2c13ce90d88

