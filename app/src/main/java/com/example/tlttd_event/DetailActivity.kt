package com.example.tlttd_event

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.IntentCompat
import com.example.tlttd_event.databinding.ActivityDetailBinding
import com.example.tlttd_event.model.StudentModel

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val student = IntentCompat.getSerializableExtra(intent, "STUDENT_DATA", StudentModel::class.java)

        student?.let {
            val info = """
                - Họ và tên: ${it.fullName}
                - Email: ${it.email}
                - Số điện thoại: ${it.phone}
                - Giới tính: ${it.gender}
                - Mạng xã hội: ${it.socialNetworks}
            """.trimIndent()
            binding.tvDetailInfo.text = info
        }

        binding.btnBack.setOnClickListener {
            finish()
        }
    }
}