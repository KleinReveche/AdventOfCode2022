package com.kleinreveche.adventofcode.twentytwo

import com.kleinreveche.adventofcode.Solver
import com.kleinreveche.adventofcode.Utils

/** --- Day 2: Rock Paper Scissors --- https://adventofcode.com/2022/day/2 */
class DayTwo : Solver {

    data class DayTwoMoves(val opponent: Char, val player: Char)

    override fun solve() {
        val moves = parseData()
        val scores = mutableListOf<Int>()
        val secondScores = mutableListOf<Int>()

        moves.forEach { it ->
            it.forEach {
                scores.add(calculateScore(it))
                secondScores.add(calculateScoreWithAltStrategy(it))
            }
        }

        println(" --- 2022 Day 2: Rock Paper Scissors ---\n")
        println("   Your score is ${scores.sum()}")
        println("   After the new instructions, your new score is ${secondScores.sum()}\n")
    }

    override fun parseData(): MutableList<List<DayTwoMoves>> {
        val moves = mutableListOf<List<DayTwoMoves>>()
        var currentMovesList = mutableListOf<DayTwoMoves>()
        val lines = Utils.readInput("twentytwo", "day02")!!.trim().lines()

        lines.forEach { line ->
            if (line.isBlank()) {
                moves.add(currentMovesList.toList())
                currentMovesList = mutableListOf()
            } else {
                val (opponent, own) = line.split(" ")
                val movesList = DayTwoMoves(opponent.trim().toCharArray()[0], own.trim().toCharArray()[0])
                currentMovesList.add(movesList)
            }

        }

        if (currentMovesList.isNotEmpty()) {
            moves.add(currentMovesList.toList())
        }

        return moves
    }


    private fun calculateScore(moves: DayTwoMoves): Int {
        val playerChoice = moves.player
        val opponentChoice = moves.opponent

        // Part 1
        val playerWinScore = when (playerChoice) {
            'X' -> if (opponentChoice == 'C') 6 else if (opponentChoice == 'A') 3 else 0
            'Y' -> if (opponentChoice == 'A') 6 else if (opponentChoice == 'B') 3 else 0
            'Z' -> if (opponentChoice == 'B') 6 else if (opponentChoice == 'C') 3 else 0
            else -> throw IllegalArgumentException("Wrong Input, Check Hard-Coded Data")
        }
        val playScore = when (playerChoice) {
            'X' -> 1
            'Y' -> 2
            'Z' -> 3
            else -> throw IllegalArgumentException("Wrong Input, Check Hard-Coded Data")
        }

        return playerWinScore + playScore
    }

    private fun calculateScoreWithAltStrategy(moves: DayTwoMoves): Int {
        val strategy = moves.player
        val opponentChoice = moves.opponent

        val playerMove = when (strategy) {
            'X' -> if (opponentChoice == 'A') 'Z' else if (opponentChoice == 'B') 'X' else 'Y'
            'Y' -> if (opponentChoice == 'A') 'X' else if (opponentChoice == 'B') 'Y' else 'Z'
            'Z' -> if (opponentChoice == 'A') 'Y' else if (opponentChoice == 'B') 'Z' else 'X'
            else -> throw IllegalArgumentException("Wrong Input, Check Hard-Coded Data")
        }

        return calculateScore(DayTwoMoves(moves.opponent, playerMove))
    }

    override fun parseData(input: Int): Any {
        return 0
    }
}
