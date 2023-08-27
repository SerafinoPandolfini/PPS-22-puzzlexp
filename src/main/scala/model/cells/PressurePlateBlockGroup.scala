package model.cells

/** Enumeration representing the possible characteristics of a plate block */
enum PressurePlateBlockGroup:
  /** Blocks that are obstacles when the plate state is on */
  case ObstacleWhenPressed

  /** Blocks that are obstacles when the plate state is off */
  case ObstacleWhenNotPressed
