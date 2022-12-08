package ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import core.Game
import core.GameMaster
import kotlinx.coroutines.delay
import org.jetbrains.compose.web.attributes.*
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*

@Composable
fun GameBorad(game: Game, gameMaster: GameMaster) {
    Div(
        attrs = {
            style {
                property("margin", "0 auto")
            }
        }
    ) {
        when {
            gameMaster.isPlaying -> {
                LaunchedEffect(Unit) {
                    while (true) {
                        delay(180)
                        game.update()
                    }
                }
                for (column in Game.COLUMNS - 1 downTo 0) {
                    Div(
                        attrs = {
                            style {
                                property("display", "flex")
//                                property("justify-content", "center")
                                property("height", "50px")
                                property("width", "50px")
                            }
                        }
                    ) {
                        for (row in 0 until Game.ROWS) {
                            val isMyUnit = gameMaster.myUnit == row
                                    && 0 == column
                            val enemy = gameMaster.enemyUnit.find {
                                it.x == row && it.y == column
                            }
                            when {
                                isMyUnit -> {
                                    Img(
                                        src = "./img/kinoko.png",
                                        attrs = {
                                            attr("onerror", "this.src='./img/white.png'")
                                        }
                                    )
                                }
                                enemy != null -> {
                                    Img(
                                        src = "./img/takenoko.png",
                                        attrs = {
                                            attr("onerror", "this.src='./img/white.png'")
                                        }
                                    )
                                }
                                else -> {
                                    Img(
                                        src = "./img/white.png",
                                        attrs = {
                                            attr("onerror", "this.src='./img/white.png'")
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
            gameMaster.isGameOver -> {
                H2 {
                    Text("Game Over")
                }
                Button(
                    attrs = {
                        onClick {
                            game.gameStart()
                        }
                    }
                ) {
                    Text("Restart Game!")
                }
            }
            else -> {
                P {
                    Text("これはPC用のゲームです。")
                }
                P {
                    Button(
                        attrs = {
                            onClick {
                                game.gameStart()
                            }
                        }
                    ) {
                        Text("Start Game!")
                    }
                }
            }
        }
    }
    if (gameMaster.isPlaying) {
        Div(
            attrs = {
                style {
                    property("margin", "auto")
                }
            }
        ) {
            Button(
                attrs = {
                    onClick {
                        game.keyEventLeft()
                    }
                }
            ) {
                Text("←")
            }
            Button(
                attrs = {
                    onClick {
                        game.keyEventRight()
                    }
                }
            ) {
                Text("→")
            }
        }
    }
}