package main.work.braintrainercompose.game.domain.models

import main.work.braintrainercompose.R
import main.work.braintrainercompose.utils.DataUtils.Companion.DIFFICULTY_COEF_ADDITION
import main.work.braintrainercompose.utils.DataUtils.Companion.DIFFICULTY_COEF_DIVISION
import main.work.braintrainercompose.utils.DataUtils.Companion.DIFFICULTY_COEF_MULTIPLICATION
import main.work.braintrainercompose.utils.DataUtils.Companion.DIFFICULTY_COEF_SUBTRACTION

enum class ExpressionType(val id: Int, val symbol: String,val calculationDifCoef: Int) {
    ADDITION(
        id = R.string.addition,
        symbol = "+",
        calculationDifCoef = DIFFICULTY_COEF_ADDITION
    ) {
        override fun execute(number1: Int, number2: Int): Int = number1 + number2
    },
    SUBTRACTION(
        id = R.string.subtraction,
        symbol = "-",
        calculationDifCoef = DIFFICULTY_COEF_SUBTRACTION
    ) {
        override fun execute(number1: Int, number2: Int): Int = number1 - number2
    },
    MULTIPLICATION(
        id = R.string.multiplication,
        symbol = "x",
        calculationDifCoef = DIFFICULTY_COEF_MULTIPLICATION
    ) {
        override fun execute(number1: Int, number2: Int): Int = number1 * number2
    },
    DIVISION(
        id = R.string.division,
        symbol = ":",
        calculationDifCoef = DIFFICULTY_COEF_DIVISION
    ) {
        override fun execute(number1: Int, number2: Int): Int = number1 / number2
    };

    abstract fun execute(number1: Int, number2: Int): Int
}