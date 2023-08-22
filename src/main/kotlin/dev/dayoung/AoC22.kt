package dev.dayoung

import io.micronaut.configuration.picocli.PicocliRunner

import picocli.CommandLine.Command
import picocli.CommandLine.Option

@Command(
    name = "aoc-22", description = ["Advent of Code 2022"],
    mixinStandardHelpOptions = true
)
class AoC22 : Runnable {
    @Option(names = ["-v", "--version"], description = ["What version of the application this is"])
    private var version: Boolean = false
    @Option(names = ["-s", "--sample"], description = ["Enable sample mode"])
    private var sampleMode: Boolean = false

    override fun run() {
        if(version) {
            println("Version 10.2")
            return
        }
        if(sampleMode) { println("Running in sample mode") }
        One(sampleMode).solve()
        Two(sampleMode).solve()
        Three(sampleMode).solve()
        Four(sampleMode).solve()
        Five(sampleMode).solve()
        Six(sampleMode).solve()
        Seven(sampleMode).solve()
        Eight(sampleMode).solve()
        Nine(sampleMode).solve()
        Ten(sampleMode).solve()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            PicocliRunner.run(AoC22::class.java, *args)
        }
    }
}
