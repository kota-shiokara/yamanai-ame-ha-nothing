package core

import Debug
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf

class Game {
    companion object {
        const val COLUMNS = 10
        const val ROWS = 5
        const val LIFE = 3
    }

    private val _gameMaster: MutableState<GameMaster> by lazy {
        mutableStateOf(GameMaster())
    }
    val gameMaster: State<GameMaster> = _gameMaster

    fun gameStart() {
        val enemyUnit = listOf(Chara((0 until ROWS).random(), COLUMNS - 1))
        _gameMaster.value = gameMaster.value.copy(
            score = 0,
            life = LIFE,
            isPlaying = true,
            isGameOver = false,
            myUnit = 0,
            enemyUnit = enemyUnit
        )
    }

    fun update() {
        Debug("Game#update", "update")
        val myUnit = gameMaster.value.myUnit
        val enemyUnit = gameMaster.value.enemyUnit.map { it.copy(y = it.y - 1) }
        val attackEnemy = enemyUnit.find { it.y <= -1 }

        if (attackEnemy != null) {
            val nextLife = gameMaster.value.life - 1
            val nextEnemyUnit = enemyUnit.dropWhile {
                it == attackEnemy
            }
            if (nextLife == -1) {
                _gameMaster.value = gameMaster.value.copy(
                    isGameOver = true,
                    isPlaying = false
                )
                return
            } else {
                _gameMaster.value = gameMaster.value.copy(
                    life = nextLife
                )
            }
            checkBattle(myUnit, nextEnemyUnit)
        } else {
            checkBattle(myUnit, enemyUnit)
        }

        val nextLastUpdate = gameMaster.value.lastUpdate + 1
        if (nextLastUpdate % (1..10).random() == 0) {
            val nextEnemyUnit =
                gameMaster.value.enemyUnit + listOf(
                    Chara((0 until ROWS).random(), COLUMNS - 1)
                )
            _gameMaster.value = gameMaster.value.copy(
                enemyUnit = nextEnemyUnit,
                lastUpdate = nextLastUpdate
            )
        } else {
            _gameMaster.value = gameMaster.value.copy(
                lastUpdate = nextLastUpdate
            )
        }
    }

    fun checkBattle(myUnit: Int, enemyUnit: List<Chara>) {
        val newEnemyUnit = enemyUnit.dropWhile {
            myUnit == it.x && 0 == it.y
        }
        val newScore = gameMaster.value.score + enemyUnit.size - newEnemyUnit.size
        val topScore = gameMaster.value.topScore
        if (topScore < newScore) {
            _gameMaster.value = gameMaster.value.copy(
                score = newScore,
                topScore = newScore,
                myUnit = myUnit,
                enemyUnit = newEnemyUnit
            )
        } else {
            _gameMaster.value = gameMaster.value.copy(
                score = newScore,
                myUnit = myUnit,
                enemyUnit = newEnemyUnit
            )
        }
    }

    fun keyEventRight() {
        Debug("Game#keyEventRight", gameMaster.value)
        if (gameMaster.value.myUnit >= ROWS - 1) return
        val nextChara = gameMaster.value.myUnit + 1
        checkBattle(nextChara, gameMaster.value.enemyUnit)
    }

    fun keyEventLeft() {
        Debug("Game#keyEventLeft", gameMaster.value)
        if (gameMaster.value.myUnit == 0) return
        val nextChara = gameMaster.value.myUnit - 1
        checkBattle(nextChara, gameMaster.value.enemyUnit)
    }

    fun moveEvent(nextPos: Int) {
        if(nextPos < 0 || ROWS - 1 < nextPos) return
        checkBattle(nextPos, gameMaster.value.enemyUnit)
    }
}