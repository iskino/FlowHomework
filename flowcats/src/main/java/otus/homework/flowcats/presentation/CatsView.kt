package otus.homework.flowcats.presentation

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import otus.homework.flowcats.R
import otus.homework.flowcats.data.Fact

class CatsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), ICatsView {

    override fun populate(fact: Fact) {
        findViewById<TextView>(R.id.fact_textView).text = fact.text
    }

    override fun populate(text: String) {
        findViewById<TextView>(R.id.fact_textView).text = text
    }
}

interface ICatsView {

    fun populate(fact: Fact)
    fun populate(text: String)
}