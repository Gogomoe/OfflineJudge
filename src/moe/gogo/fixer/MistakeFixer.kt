package moe.gogo.fixer

import moe.gogo.evualator.EvaluatorState
import moe.gogo.evualator.QuestionResult
import moe.gogo.evualator.append

abstract class MistakeFixer {

    abstract fun haveMistake(prevResult: QuestionResult, state: EvaluatorState): Boolean

    fun tryToFix(
        prevResult: QuestionResult,
        state: EvaluatorState
    ): Pair<QuestionResult, EvaluatorState>? {
        val fixedState = fix(state.copy())
        fixedState.outputFile =
            fixedState.outputFile.append("_" + this::class.simpleName!!.removeSuffix("Fixer"))

        val currentResult = fixedState.evaluate()
        if (fixed(prevResult, currentResult)) {
            addMistake(fixedState)
            return currentResult to fixedState
        }
        fixedState.clear()
        return null
    }

    protected abstract fun fix(state: EvaluatorState): EvaluatorState

    protected abstract fun fixed(prevResult: QuestionResult, currentResult: QuestionResult): Boolean

    protected abstract fun addMistake(state: EvaluatorState)

}