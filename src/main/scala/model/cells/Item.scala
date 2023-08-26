package model.cells

/** Enumeration representing the items that can be on top of a cell */
enum Item:
  /** A box is an element which can be moved in the room by the player */
  case Box

  /** When there is nothing on the cell */
  case Empty
