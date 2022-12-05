package dev.dayoung

import io.micronaut.configuration.picocli.PicocliRunner

import picocli.CommandLine.Command
import picocli.CommandLine.Option

@Command(
    name = "aoc-22", description = ["..."],
    mixinStandardHelpOptions = true
)
class AoC22 : Runnable {
    /*
    @Option(names = ["-v", "--verbose"], description = ["..."])
    private var verbose: Boolean = false
     */
    @Option(names = ["-s", "--sample"], description = ["Enable sample mode"])
    private var sampleMode: Boolean = false

    override fun run() {
        if(sampleMode) { println("Running in sample mode") }
        One(sampleMode).solve()
        Two(sampleMode).solve()
        Three(sampleMode).solve()
        Four(sampleMode).solve()
        Five(sampleMode).solve()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            PicocliRunner.run(AoC22::class.java, *args)
        }
    }
}
