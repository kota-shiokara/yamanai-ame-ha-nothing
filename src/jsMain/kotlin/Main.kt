import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import core.Game
import core.GameMaster
import kotlinx.browser.document
import org.jetbrains.compose.web.attributes.*
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import org.jetbrains.compose.web.renderComposable
import org.w3c.dom.HTMLElement
import org.w3c.dom.events.KeyboardEvent
import org.w3c.dom.get
import ui.GameBorad

// Composableが書けない
fun main() {
    val game = Game()

    val body = document.getElementsByTagName("body")[0] as HTMLElement
    // key event
    body.addEventListener("keyup", {
        when ((it as KeyboardEvent).keyCode) {
            37, 65 -> { // 37 is arrow left, 65 is 'A'
                // Left
                game.keyEventLeft()
            }
            39, 68 -> { // 39 is arrow right, 68 is 'D'
                // Right
                game.keyEventRight()
            }
            81 -> { // 'Q'
                game.moveEvent(0)
            }
            87 -> { // 'W'
                game.moveEvent(1)
            }
            69 -> { // 'E'
                game.moveEvent(2)
            }
            82 -> { // 'R'
                game.moveEvent(3)
            }
            84 -> { // 'T'
                game.moveEvent(4)
            }
        }
    })

    renderComposable(rootElementId = "root") {
        Div(
            attrs = { style { property("text-align", "center") } }
        ) {
            val gameMaster by game.gameMaster

            Header(gameMaster)

            Debug("GameMaster", gameMaster)

            GameBorad(game, gameMaster)
        }
    }
}

@Composable
fun Header(gameMaster: GameMaster) {
    H1 {
        Text("Yamanai Ame Ha Nothing")
    }
    Div {
        Text("TopScore: ${gameMaster.topScore}")
        if (gameMaster.isPlaying) {
            Text(" | Score: ${gameMaster.score} | Life: ${gameMaster.life}")
        }
    }
}

fun Debug(message: Any) {
    Debug("No Tag", message)
}

fun Debug(tag: String, message: Any) {
    console.log("$tag: $message")
}