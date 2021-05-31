package com.example.adhan


import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {
    /*  override fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)
         setContentView(R.layout.activity_main)



     }*/
     var textViewResult: TextView? = null
      override fun onCreate(savedInstanceState: Bundle?) {
          super.onCreate(savedInstanceState)
          setContentView(R.layout.activity_main)
          textViewResult = findViewById(R.id.text_view_result)
          val retrofit = Retrofit.Builder()
              .baseUrl("https://jsonplaceholder.typicode.com/")
              .addConverterFactory(GsonConverterFactory.create())
              .build()
          val jsonPlaceHolderApi = retrofit.create(
              JsonPlaceHolderApi::class.java
          )
          val call = jsonPlaceHolderApi.posts
          call!!.enqueue(object : Callback<kotlin.collections.List<Post>> {
              override fun onResponse(
                  call: Call<kotlin.collections.List<Post>>,
                  response: Response<kotlin.collections.List<Post>>
              ) {
                  if (!response.isSuccessful) {
                      text_view_result.setText("Code: " + response.code())
                      return
                  }
                  val posts = response.body()!!
                  for (post in posts) {
                      var content = ""
                      content += """
                      ID: ${post.id}
 
                      """.trimIndent()
                      content += """
                      User ID: ${post.userId}
 
                      """.trimIndent()
                      content += """
                      Title: ${post.title}
 
                      """.trimIndent()
                      content += """
                      Text: ${post.text}
 
 
                      """.trimIndent()
                      text_view_result.append(content)
                  }
              }

              override fun onFailure(call: Call<kotlin.collections.List<Post>>, t: Throwable) {
                  text_view_result.setText(t.message)
              }
          })
      }
}

private fun <T> Call<T>.enqueue(callback: Callback<List<Post>>) {

}






