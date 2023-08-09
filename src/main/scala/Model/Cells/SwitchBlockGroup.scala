package Model.Cells

/** Enumeration representing the possible characteristics of a switch block */
enum SwitchBlockGroup:
  /** Blocks that are obstacles when the switch state is on */
  case ObstacleWhenOn

  /** Blocks that are obstacles when the switch state is off */
  case ObstacleWhenOff
