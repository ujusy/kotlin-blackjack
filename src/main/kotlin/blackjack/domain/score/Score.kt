package blackjack.domain.score

import blackjack.domain.card.Card
import blackjack.domain.card.CardDeckImpl.Companion.ACE
import blackjack.domain.card.CardDeckImpl.Companion.JACK
import blackjack.domain.card.CardDeckImpl.Companion.KING
import blackjack.domain.card.CardDeckImpl.Companion.QUEEN
import blackjack.domain.player.Player

class Score {
    private var _playerScores: MutableList<PlayerScore> = mutableListOf()
    val playerScore: List<PlayerScore> = _playerScores

    fun run(players: List<Player>) {
        players.forEach { player ->
            val sum = sum(player.cards)
            _playerScores.add(PlayerScore(player, sum))
        }
    }

    private fun sum(cards: List<Card>): Int {
        var sum = 0
        cards.forEach { card ->
            sum += when (card.number) {
                ACE -> ACE_DEFAULT_SCORE
                QUEEN -> QUEEN_SCORE
                JACK -> JACK_SCORE
                KING -> KING_SCORE
                else -> card.number.toInt()
            }
        }

        if (isBurst(sum) && containAce(cards)) {
            sum = sum - ACE_DEFAULT_SCORE + ACE_SCORE
        }

        return sum
    }

    private fun isBurst(score: Int): Boolean {
        if (score > BLACK_JACK) return true
        return false
    }

    private fun containAce(cards: List<Card>): Boolean {
        return cards.any { it.number === ACE }
    }

    companion object {
        const val BLACK_JACK = 21
        const val ACE_DEFAULT_SCORE = 11
        const val ACE_SCORE = 1
        const val JACK_SCORE = 10
        const val QUEEN_SCORE = 10
        const val KING_SCORE = 10
    }
}
