package model.cells.logic

import model.cells.*
import model.room.ItemHolder
import utils.PositionExtension.*

object CellExtension:
  /** extension for adding new methods for interacting with cellItems and the player
    */
  extension (cell: Cell)

    /** Perform the necessary operations wen the player walks in a cell
      * @param cells
      *   The set of all cells in the current room
      * @return
      *   the set of the modified cells in the room and the [[Position]] of the cell in which the player is now
      */
    def moveIn(cells: Set[Cell]): (Set[Cell], Position) =
      cell match
        case cell: ButtonCell =>
          (pressed(cells, cell.color) + cell.copy(pressableState = PressableState.Pressed), cell.position)
        case _: TeleportCell =>
          (
            Set.empty,
            findTeleportDestination(cells) match
              case Some(value) => value.position
              case None        => cell.position
          )
        case _ => (Set.empty, cell.position)

    /** Perform the necessary operations wen the player walks out of a cell
      * @param cells
      *   The set of all cells in the current room
      * @return
      *   A set of modified cells after the player walks out
      */
    def moveOut(cells: Set[Cell]): Set[Cell] =
      cell match
        case cell: CoveredHoleCell => Set(cell.copy(cover = false))
        case _                     => Set.empty

    /** Updates the item in the cell and returns a set of modified cells based on the rules of the game.
      *
      * @param cells
      *   The set of all cells in the current room
      * @param newItem
      *   The new item to be placed in the cell
      * @param direction
      *   The direction in which the update is happening
      * @return
      *   A set of modified cells after the update
      */
    def updateItem(cells: Set[Cell], newItem: Item, direction: Direction): Set[Cell] =
      cell match
        case cell: BasicCell               => Set(cell.copy(cellItem = newItem))
        case cell: ButtonBlockCell         => Set(cell.copy(cellItem = newItem))
        case cell: CliffCell               => Set(cell.copy(cellItem = newItem))
        case cell: PressurePlateBlockCell  => Set(cell.copy(cellItem = newItem))
        case cell: TeleportDestinationCell => Set(cell.copy(cellItem = newItem))
        case cell: HoleCell                => updateHoleItem(cell, newItem)
        case cell: CoveredHoleCell         => updateCoveredHoleItem(cell, newItem)
        case cell: RockCell                => updateRockItem(cell, newItem)
        case cell: PlantCell               => updatePlantItem(cell, newItem)
        case cell: DoorCell                => updateDoorItem(cell, newItem)
        case _: TeleportCell               => updateTeleportItem(cells, newItem, direction)
        case cell: ButtonCell =>
          newItem match
            case Item.Box =>
              pressed(cells, cell.color) + cell.copy(cellItem = newItem, pressableState = PressableState.Pressed)
            case _ => Set(cell.copy(cellItem = newItem))
        case cell: PressurePlateCell =>
          val pressableState = newItem match
            case Item.Box => PressableState.Pressed
            case _        => PressableState.NotPressed
          updatePressurePlate(cells, pressableState) + cell.copy(
            cellItem = newItem,
            pressableState = pressableState
          )
        case _ => Set.empty[Cell]

    /** Removes the item from the cell and adds it to the [[ItemHolder]].
      *
      * @param cellWithItem
      *   The specific cell with the item
      * @param itemHolder
      *   the itemHolder
      * @return
      *   A set of modified cells without the item
      */
    def gatherItem(cellWithItem: Cell, itemHolder: ItemHolder): (Set[Cell], ItemHolder) = cellWithItem match
      case c if c.cellItem != Item.Empty =>
        (c.updateItem(Set(c), Item.Empty, Direction.Up), itemHolder.addItem(c.cellItem))

    /** set he button pressable state to "Pressed" and the corresponding blocks
      * @param cells
      *   the set of the cells that may be changed
      * @param color
      *   the color of the button block cells that needs to change
      * @return
      *   the set of changed cells
      */
    private def pressed(cells: Set[Cell], color: Color): Set[Cell] =
      val pos = ButtonBlockFinder.positionToRevert(cells, color)
      pos.map(p => ButtonBlockCell(p, color = color, PressableState.Pressed))

    /** update the plate pressable state and the corresponding blocks
      * @param cells
      *   the set of the cells that may be changed
      * @param pressableState
      *   the state to be set
      * @return
      *   the set of changed cells
      */
    private def updatePressurePlate(cells: Set[Cell], pressableState: PressableState): Set[Cell] =
      Set.from(
        cells
          .collect { case c: PressurePlateBlockCell => c }
          .map(c => c.copy(pressableState = pressableState))
      )

    /** update the hole cell
      * @param hCell
      *   the hole cell
      * @param newItem
      *   the item
      * @return
      *   the set of changed cells
      */
    private def updateHoleItem(hCell: HoleCell, newItem: Item): Set[Cell] = newItem match
      case Item.Box =>
        if hCell.filled then Set(hCell.copy(cellItem = newItem))
        else Set(hCell.copy(cellItem = Item.Empty, filled = true))
      case _ => Set(hCell.copy(cellItem = Item.Empty))

    /** update the covered hole cell
      * @param chCell
      *   the cell to be updated
      * @param newItem
      *   the item
      * @return
      *   the set of changed cells
      */
    private def updateCoveredHoleItem(chCell: CoveredHoleCell, newItem: Item): Set[Cell] = newItem match
      case Item.Box =>
        if chCell.filled then Set(chCell.copy(cellItem = newItem))
        else Set(chCell.copy(cellItem = Item.Empty, cover = false, filled = true))
      case _ => Set.empty[Cell]

    /** update the teleport cell
      * @param cells
      *   the set of the cells that may be changed
      * @param newItem
      *   the item
      * @param direction
      *   the direction in which the update is done
      * @return
      *   the set of changed cells
      */
    private def updateTeleportItem(cells: Set[Cell], newItem: Item, direction: Direction): Set[Cell] =
      cells.collectFirst { case c: TeleportDestinationCell =>
        c
      } match
        case Some(tdCell) =>
          cells.collectFirst {
            case dCell if dCell.position == tdCell.position + direction.coordinates => dCell
          } match
            case Some(destCell) => destCell.updateItem(cells, newItem, direction)
            case None           => Set.empty[Cell]
        case None => Set.empty[Cell]

    /** update the rock cell
      * @param rCell
      *   the cell to be updated
      * @param newItem
      *   the item
      * @return
      *   the set of changed cells
      */
    private def updateRockItem(rCell: RockCell, newItem: Item): Set[Cell] = newItem match
      case Item.Box =>
        if rCell.broken then Set(rCell.copy(cellItem = newItem))
        else Set(rCell.copy(cellItem = Item.Empty, broken = false))
      case Item.Pick => Set(rCell.copy(cellItem = Item.Empty, broken = true))
      case _         => Set(rCell.copy(cellItem = Item.Empty))

    /** update the plant cell
      * @param pCell
      *   the cell to be updated
      * @param newItem
      *   the item
      * @return
      *   the set of changed cells
      */
    private def updatePlantItem(pCell: PlantCell, newItem: Item): Set[Cell] = newItem match
      case Item.Box =>
        if pCell.cut then Set(pCell.copy(cellItem = newItem))
        else Set(pCell.copy(cellItem = Item.Empty, cut = false))
      case Item.Axe => Set(pCell.copy(cellItem = Item.Empty, cut = true))
      case _        => Set(pCell.copy(cellItem = Item.Empty))

    /** update the door cell
      * @param dCell
      *   the cell to be updated
      * @param newItem
      *   the item
      * @return
      *   the set of changed cells
      */
    private def updateDoorItem(dCell: DoorCell, newItem: Item): Set[Cell] = newItem match
      case Item.Key =>
        if !dCell.open then Set(dCell.copy(cellItem = Item.Empty, open = true))
        else Set(dCell)
      case _ => Set(dCell.copy(cellItem = Item.Empty))

    /** Find the [[TeleportDestinationCell]] in the provided [[Set]]
      * @param cells
      *   the [[Set]][ [[Cell]] ] in which [[TeleportDestinationCell]] should be found
      * @return
      *   an [[Option]] of [[TeleportDestinationCell]]
      */
    private def findTeleportDestination(cells: Set[Cell]): Option[TeleportDestinationCell] =
      TeleportFinder.findDestination(cells) match
        case Some(value) => Option(TeleportDestinationCell((value._1, value._2)))
        case None        => Option.empty
