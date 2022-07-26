data class Coordinates(val row: Int, val col: Int)
data class Limits(val rows: Int, val cols: Int, val obstacles: Int, val creatures: Creatures, val movesUntilReproduce: Creatures)
data class Creatures(val preys: Int, val predators: Int, val jellyfish: Int)

object Directions {
    val west = Pair(0, -1)
    val ost = Pair(0, 1)
    val north = Pair(-1, 0)
    val south = Pair(1, 0)

    fun asList() = listOf(west, ost, north, south)
}

object EmptyCell : ICell {
    override fun getRepresentation(): String {
        return "-"
    }
}

object Obstacle : ICell {
    override fun getRepresentation(): String {
        return "#"
    }
}

class Prey(override val movesUntilReproduce: Int) : ICell, Movable {
    override var movesCount = 0
    override fun filterCellsToMove(cells: List<Pair<Coordinates, ICell>>): List<Coordinates> {
        return cells.filter { it.second is EmptyCell }.map { it.first }
    }

    override fun getRepresentation(): String {
        return "f"
    }

    override fun doBorn(): ICell {
        movesCount = 0
        return Prey(movesUntilReproduce)
    }
}

class Predator(override val movesUntilReproduce: Int) : ICell, Movable {
    override var movesCount = 0
    override fun filterCellsToMove(cells: List<Pair<Coordinates, ICell>>): List<Coordinates> {
        return cells.filter { it.second is EmptyCell || it.second is Prey }.map { it.first }
    }

    override fun getRepresentation(): String {
        return "S"
    }

    override fun doBorn(): ICell {
        movesCount = 0
        return Predator(movesUntilReproduce)
    }
}

class Jellyfish(override val movesUntilReproduce: Int) : ICell, Movable {
    override var movesCount = 0

    override fun filterCellsToMove(cells: List<Pair<Coordinates, ICell>>): List<Coordinates> {
        return cells.filter { it.second is EmptyCell || it.second is Prey }.map { it.first }
    }

    override fun getRepresentation(): String {
        return "@"
    }

    override fun doBorn(): ICell {
        movesCount = 0
        return Jellyfish(movesUntilReproduce)
    }
}
