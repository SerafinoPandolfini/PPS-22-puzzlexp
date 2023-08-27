package model.cells

/** Enumeration representing the items that can be on top of a cell */
enum Item:
  /** A box is an element which can be moved in the room by the player */
  case Box

  /** the pick is able to break the rocks */
  case Pick

  /** the axe is able to cut the plants */
  case Axe

  /** the key is able to open a door */
  case Key

  /** When there is nothing on the cell */
  case Empty
