package model.cells.properties

import utils.constants.ImageManager

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

  /** the goal gem is an item that, once piked up, make the game finish */
  case GoalGem

  /** When there is nothing on the cell */
  case Empty
