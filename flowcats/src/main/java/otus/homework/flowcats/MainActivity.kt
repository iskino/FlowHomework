package otus.homework.flowcats

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.launch
import otus.homework.flowcats.presentation.CatsView
import otus.homework.flowcats.presentation.CatsViewModel
import otus.homework.flowcats.presentation.CatsViewModelFactory
import otus.homework.flowcats.presentation.Result

class MainActivity : AppCompatActivity() {

    private val catsViewModel by viewModels<CatsViewModel> {
        CatsViewModelFactory()
    }

    @SuppressLint("InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = layoutInflater.inflate(R.layout.activity_main, null) as CatsView
        setContentView(view)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                catsViewModel.catsState.collect { state ->
                    when (state) {
                        Result.Loading -> view.populate("-")
                        is Result.Success -> view.populate(state.data)
                        is Result.Error ->
                            Toast.makeText(
                                this@MainActivity.baseContext,
                                state.throwable?.message ?: "Unknown error",
                                Toast.LENGTH_SHORT
                            ).show()
                    }
                }
            }
        }
    }
}