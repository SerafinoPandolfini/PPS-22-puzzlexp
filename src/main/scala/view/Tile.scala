package view

import utils.ImageManager

import java.awt.*
import javax.swing.*

trait Tile:
  def backgroundImage_(path: String): Unit
  def TileLabel: JLabel
  def placeCharacter(direction: String): Unit
  def isCharacterHere: Boolean
  def unplaceCharacter(): Unit

object Tile:
  private class TileImpl(panel: JPanel, cellSize: Int) extends Tile:
    private val bgLabel: JLabel = JLabel()
    private var characterInTheTile: Boolean = false
    val characterLabel: JLabel = JLabel()

    override def backgroundImage_(path: String): Unit =
      this.bgLabel.setPreferredSize(Dimension(cellSize, cellSize))
      this.bgLabel.setLayout(new BorderLayout())
      this.bgLabel.setIcon(new ImageIcon(path))
      panel.add(this.bgLabel)

    override def TileLabel: JLabel = this.bgLabel

    override def isCharacterHere: Boolean = this.characterInTheTile

    override def placeCharacter(path: String): Unit =
      this.characterLabel.setIcon(new ImageIcon(path))
      this.bgLabel.add(characterLabel)
      this.characterInTheTile = true

    override def unplaceCharacter(): Unit =
      this.bgLabel.remove(characterLabel)
      this.characterInTheTile = false

  def apply(panel: JPanel, cellSize: Int): Tile = TileImpl(panel: JPanel, cellSize: Int)
