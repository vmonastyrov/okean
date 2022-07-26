import kotlin.random.Random

class Ocean(private val limits: Limits) : IOcean {
    private val cells: MutableMap<Coordinates, ICell> = mutableMapOf()

    init {
        allElements { row, col ->
            cells[Coordinates(row, col)] = EmptyCell
        }
        generate(limits.obstacles) { coords -> cells[coords] = Obstacle }
        generate(limits.creatures.preys) { coords -> cells[coords] = Prey(limits.movesUntilReproduce.preys) }
        generate(limits.creatures.predators) { coords -> cells[coords] = Predator(limits.movesUntilReproduce.predators) }
        generate(limits.creatures.jellyfish) { coords -> cells[coords] = Jellyfish(limits.movesUntilReproduce.jellyfish) }
    }

    fun tick() {
        allElements { row, col ->
            val coords = Coordinates(row, col)
            val cell = cells.getValue(coords)
            if (cell is Movable) {
                val cellsToMove = getCellsAround(coords).map { it to cells.getValue(it) }
                val newCoords = cell.getNewCoordinate(cellsToMove)
                if (newCoords != null) {
                    doMove(coords, newCoords)
                }
                if (cell.isTimeToReproduce()) {
                    cells[coords] = cell.doBorn()
                }
            }
        }
    }

    fun show() {
        (1..limits.rows).forEach { row ->
            (1..limits.cols).forEach { col ->
                print(cells.getValue(Coordinates(row, col)).getRepresentation())
            }
            println()
        }
    }

    override fun getCellsAround(cs: Coordinates): List<Coordinates> {
        return Directions.asList()
            .map { Coordinates(cs.row + it.first, cs.col + it.second) }
            .filter { cells[it] != null }
    }

    override fun doMove(from: Coordinates, to: Coordinates) {
        val cell = cells.getValue(from)
        cells[from] = EmptyCell
        cells[to] = cell
    }

    private fun generate(amount: Int, f: (coords: Coordinates) -> Unit) {
        var generated = 0
        while (generated < amount) {
            val coords = Coordinates(Random.nextInt(1, limits.rows), Random.nextInt(1, limits.cols))
            if (cells.getValue(coords) == EmptyCell) {
                f(coords)
                generated += 1
            }
        }
    }

    private fun allElements(f: (row: Int, col: Int) -> Unit) {
        (1..limits.rows).forEach { row ->
            (1..limits.cols).forEach { col ->
                f(row, col)
            }
        }
    }
}
