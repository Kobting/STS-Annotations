
//Modify to match your system
val steamPath = when(Platform.currentPlatform()) {
    Platform.WINDOWS -> "D:/steam"
    Platform.LINUX -> "${System.getProperty("user.home")}/.steam/debian-installation/steamapps"
}

val workshopLocation = "$steamPath/workshop/content/646570"
val SlayTheSpire = files("$steamPath/common/SlayTheSpire/desktop-1.0.jar")
val ModTheSpire = files("$workshopLocation/1605060445/ModTheSpire.jar")
val BaseMod = files("$workshopLocation/1605833019/BaseMod.jar")

extensions.add("SlayTheSpire", SlayTheSpire)
extensions.add("ModTheSpire", ModTheSpire)
extensions.add("BaseMod", BaseMod)

enum class Platform {
    WINDOWS,
    LINUX;

    companion object {

        fun currentPlatform(): Platform {
            val os = System.getProperty("os.name").toLowerCase()
            return if (os.contains("win", true)) {
                WINDOWS
            } else if (os.contains("nux", true)) {
                LINUX
            } else {
                error("No platform configuration for: $os")
            }
        }

    }
}

