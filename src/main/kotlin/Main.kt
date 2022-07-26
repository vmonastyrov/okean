fun main(args: Array<String>) {
    val limits = Limits(
        23, 35, 60,
        Creatures(predators = 15, preys = 100, jellyfish = 7),
        movesUntilReproduce = Creatures(10, 10, 35)
    )
    val ocean = Ocean(limits)
    ocean.show()
    (1..250).forEach {
        ocean.tick()
    }
    println()
    ocean.show()
}
