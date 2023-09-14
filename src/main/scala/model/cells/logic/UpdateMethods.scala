package model.cells.logic

import model.cells.*
import CellExtension.updateItem
import utils.PositionExtension.+

object UpdateMethods:
  /** set he button pressable state to "Pressed" and the corresponding blocks
    * @param cells
    *   the set of the cells that may be changed
    * @param color
    *   the color of the button block cells that needs to change
    * @return
    *   the set of changed cells
    */
  private[logic] def pressed(cells: Set[Cell], color: Color): Set[Cell] =
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
  private[logic] def updatePressurePlate(cells: Set[Cell], pressableState: PressableState): Set[Cell] =
    Set.from(
      cells
        .collect { case c: PressurePlateBlockCell => c }
        .map(c => c.copy(pressableState = pressableState, cellItem = Item.Empty))
    )

  /** update the hole cell
    * @param hCell
    *   the hole cell
    * @param newItem
    *   the item
    * @return
    *   the set of changed cells
    */
  private[logic] def updateHoleItem(hCell: HoleCell, newItem: Item): Set[Cell] = newItem match
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
  private[logic] def updateCoveredHoleItem(chCell: CoveredHoleCell, newItem: Item): Set[Cell] = newItem match
    case Item.Box =>
      if chCell.filled then Set(chCell.copy(cellItem = newItem))
      else Set(chCell.copy(cellItem = Item.Empty, cover = false, filled = true))
    case _ => Set(chCell.copy(cellItem = Item.Empty))

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
  private[logic] def updateTeleportItem(cells: Set[Cell], newItem: Item, direction: Direction): Set[Cell] =
    cells.collectFirst { case c: TeleportDestinationCell => c } match
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
  private[logic] def updateRockItem(rCell: RockCell, newItem: Item): Set[Cell] = newItem match
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
  private[logic] def updatePlantItem(pCell: PlantCell, newItem: Item): Set[Cell] = newItem match
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
  private[logic] def updateDoorItem(dCell: LockCell, newItem: Item): Set[Cell] = newItem match
    case Item.Key =>
      if !dCell.open then Set(dCell.copy(cellItem = Item.Empty, open = true))
      else Set(dCell)
    case item => Set(dCell.copy(cellItem = item))

  /** Find the [[TeleportDestinationCell]] in the provided [[Set]]
    * @param cells
    *   the [[Set]][ [[Cell]] ] in which [[TeleportDestinationCell]] should be found
    * @return
    *   an [[Option]] of [[TeleportDestinationCell]]
    */
  private[logic] def findTeleportDestination(cells: Set[Cell]): Option[TeleportDestinationCell] =
    TeleportFinder.findDestination(cells) match
      case Some(value) => Option(TeleportDestinationCell(value))
      case None        => Option.empty
