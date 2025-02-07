package blackjack

import blackjack.domain.card.CardDeck
import blackjack.domain.card.RandomCardDeck
import blackjack.domain.game.BlackJack
import blackjack.domain.player.Dealer
import blackjack.domain.player.Player
import blackjack.domain.score.PlayerScore
import blackjack.domain.score.Score
import blackjack.domain.winning.WinningStat
import blackjack.dto.BlackJackRequest
import blackjack.view.GameView
import blackjack.view.InputView
import blackjack.view.ResultView
import blackjack.view.WinningStatView

fun main() {
    val inputView = InputView()
    val resultView = ResultView()

    val inputPlayers = inputView.inputPlayers()
    resultView.players(inputPlayers)

    startBlackJack(resultView, inputPlayers)
}

fun startBlackJack(resultView: ResultView, inputPlayers: List<String>) {
    val dto: BlackJackRequest = BlackJackRequest.of(inputPlayers)
    val cardDeck: CardDeck = RandomCardDeck()
    val blackJack = BlackJack(dto, cardDeck)

    val players: List<Player> = dto.players
    val dealer: Dealer = dto.dealer
    val gameView = GameView(blackJack, dealer, players)
    gameView.firstRoundState()
    gameView.run()

    calculateScore(resultView, players, dealer)
}

fun calculateScore(resultView: ResultView, players: List<Player>, dealer: Dealer) {
    val scores: List<PlayerScore> = players.map { player -> Score.calculatePlayerScore(player) }
    val dealerScore: PlayerScore = Score.calculatePlayerScore(dealer)
    resultView.dealerScore(dealerScore)
    resultView.playerScore(scores)

    winningStat(WinningStat(scores, dealerScore))
}

fun winningStat(winingStat: WinningStat) {
    val winningStatView = WinningStatView(winingStat)
    winningStatView.title()
    winningStatView.indicator()
}
