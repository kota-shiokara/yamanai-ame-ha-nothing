package core

data class GameMaster (
    val score: Int = 0,
    val life: Int = 3,
    val isPlaying: Boolean = false,
    val isGameOver: Boolean = false,
    val topScore: Int = 0,
    val myUnit: Int = 0,
    val enemyUnit: List<Chara> = emptyList(),
    val lastUpdate: Int = 0
)
