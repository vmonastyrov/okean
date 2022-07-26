import java.util.*

interface IOcean {
    fun getCellsAround(cs: Coordinates): List<Coordinates>
    fun doMove(from: Coordinates, to: Coordinates)
}

interface ICell {
    fun getRepresentation(): String
}

interface Movable {
    var movesCount: Int
    val movesUntilReproduce: Int
    fun filterCellsToMove(cells: List<Pair<Coordinates, ICell>>): List<Coordinates>
    fun getNewCoordinate(cells: List<Pair<Coordinates, ICell>>): Coordinates? {
        val possibleCoords = filterCellsToMove(cells)
        if (possibleCoords.isEmpty())
            return null
        movesCount += 1
        return possibleCoords.random()
    }
    fun doBorn(): ICell
    fun isTimeToReproduce(): Boolean = movesCount == movesUntilReproduce
}
