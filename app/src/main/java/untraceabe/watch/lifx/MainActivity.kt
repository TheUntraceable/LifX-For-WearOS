package untraceabe.watch.lifx

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Button
import untraceabe.watch.lifx.databinding.ActivityMainBinding
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class MainActivity : Activity() {

private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "LifX"
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val button: Button = findViewById(R.id.toggle)
        button.setOnClickListener {
            buttonClicked(it)
        }

    }

    fun buttonClicked(view: View) {
        val requestQueue = Volley.newRequestQueue(this)
        callEndpoint(requestQueue)
    }

    private fun callEndpoint(requestQueue: RequestQueue) {
        val url = "https://api.lifx.com/v1/lights/all/toggle"
        val request = object: StringRequest(Request.Method.POST, url, Response.Listener { _ ->
            val button: Button = findViewById(R.id.toggle)
            button.setText("Worked!")
        }, Response.ErrorListener { error: VolleyError ->
            println("Call to api.lifx.com failed with status code ${error.networkResponse.statusCode}.")
        }) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Authorization"] = "Bearer TOKEN"
                return headers
            }
        }
        requestQueue.add(request)
    }
}