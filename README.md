# STS-Annotations

This project is aimed at adding annotations to remove the boilerplate code required when creating a STS mod.

## Adding to your project

https://github.com/Kobting/STS-Annotations/wiki/Adding-to-your-project  
Example project using this project (with gradle and kotlin): https://github.com/Kobting/STS-ThePain

## Current features

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
4. `@Card` for auto generating the code to add the card using `BaseMod`  
   Example:
   ```kotlin
   @Card()RelicType.BLUE
   class Card1(): CustomCard(...)
   @Card(unlocked = true)
   class Card2(): CustomCard(...)
   ```
5. `@Relic` and `@BaseRelic` for auto generating the code to add the relic using `BaseMod`  
   Example:
   ```kotlin
   @Relic(color = MyCustomCardColor)
   class Relic1(): CustomRelic(...)
   @BaseRelic(type = RelicType.BLUE)
   class Relic2(): CustomRelic(...)
   ```
7. `@AutoSpireInitializer` for generating a class that implements basemod subscribers. Currently only supports `EditStringsSubscriber` when there exists classes annotated with one of the String annotations.   
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


https://github.com/Kobting/STS-Annotations/assets/40338395/7c9ecff6-300c-413f-bd3c-e226bdf81fb7


## Building

Update [sts-dependencies.dependencies.gradle.kts](https://github.com/Kobting/STS-Annotations/blob/main/buildSrc/src/main/kotlin/sts-dependencies.dependencies.gradle.kts) to point to your steam installation path. After that you should be able to build the project.

