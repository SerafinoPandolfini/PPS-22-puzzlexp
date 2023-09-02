package model.cells

import utils.ImageManager

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

  /** the coin is a small treasure. It increases the score of the player */
  case Coin

  /** the coin is a medium treasure. It increases the score of the player */
  case Bag

  /** the coin is a big treasure. It increases the score of the player */
  case Trunk

  /** When there is nothing on the cell */
  case Empty
