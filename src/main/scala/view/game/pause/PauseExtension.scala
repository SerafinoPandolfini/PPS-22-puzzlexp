package view.game.pause

import controller.game.GameController
import model.cells.properties.Direction
import model.game.CurrentGame
import model.gameMap.MinimapElement
import utils.constants.ImageManager
import utils.givens.DirectionMapping.given_Conversion_Direction_URL_String
import java.awt.{BorderLayout, Dimension, GridLayout}
import java.net.URL
import javax.swing.{ImageIcon, JLabel, JPanel}

object PauseExtension:

  extension (pauseGamePanel: PauseGamePanel)

    private def roomHeight = (pauseGamePanel.roomIcon.getIconHeight * pauseGamePanel.scale).toInt

    /** popolate the minimap in the pause menu
      * @return
      *   the [[JPanel]] containing the minimap
      */
    private[pause] def popolateMap(): JPanel =
      val mapPanel = JPanel()
      mapPanel.setLayout(GridLayout(pauseGamePanel.rows, pauseGamePanel.cols))
      for
        l <- pauseGamePanel.list
        r = defaultRoomPanel()
        d <- l.directions
      yield paintRooms(l, r, d, mapPanel)
      mapPanel

    /** paint a room of the minimap and its links
      * @param l
      *   the [[MinimapElement]] to paint
      * @param r
      *   the [[JPanel]] in which it is to paint
      * @param tup
      *   a tuple of [[URL]] and [[String]] which represent the link of the room and its direction
      * @param mapPanel
      *   the [[JPanel]] in which the [[JPanel]] representing the room and its links is inserted
      */
    private def paintRooms(l: MinimapElement, r: JPanel, tup: (URL, String), mapPanel: JPanel): Unit =
      val (img, dir) = tup
      val room =
        if l.name.equals(GameController.getCurrentRoomName) then ImageIcon(ImageManager.CurrentRoom.path)
        else pauseGamePanel.roomIcon
      val (roomLabel, linkLabel) =
        if l.visited && l.existing then
          (
            JLabel(
              ImageIcon(
                room.getImage
                  .getScaledInstance(pauseGamePanel.roomWidth, pauseGamePanel.roomHeight, java.awt.Image.SCALE_SMOOTH)
              )
            ),
            JLabel(
              ImageIcon(
                ImageIcon(img).getImage.getScaledInstance(
                  (ImageIcon(img).getIconWidth * pauseGamePanel.scale).toInt,
                  (ImageIcon(img).getIconHeight * pauseGamePanel.scale).toInt,
                  java.awt.Image.SCALE_SMOOTH
                )
              )
            )
          )
        else (JLabel(""), JLabel(""))
      roomLabel.setPreferredSize(Dimension(pauseGamePanel.roomWidth, pauseGamePanel.roomHeight))
      r.add(roomLabel, BorderLayout.CENTER)
      linkLabel.setPreferredSize(Dimension(pauseGamePanel.linkWidth, pauseGamePanel.linkWidth))
      r.add(linkLabel, dir)
      mapPanel.add(r)

    /** a basic [[JPanel]] in which the room and ints links will be inserted
      * @return
      *   a [[JPanel]]
      */
    private def defaultRoomPanel(): JPanel =
      val panel = JPanel(BorderLayout())
      Direction.values
        .foreach(nd => {
          val (_, borderPosition): (URL, String) = given_Conversion_Direction_URL_String(nd)
          val emptyLabel = JLabel("")
          emptyLabel.setPreferredSize(Dimension(pauseGamePanel.linkWidth, pauseGamePanel.linkWidth))
          panel.add(emptyLabel, borderPosition)
        })
      panel.setOpaque(false)
      panel
