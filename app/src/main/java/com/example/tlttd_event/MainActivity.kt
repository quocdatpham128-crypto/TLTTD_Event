package com.example.tlttd_event

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.example.tlttd_event.databinding.ActivityMainBinding
import com.example.tlttd_event.model.StudentModel
import com.example.tlttd_event.validator.FormValidator

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRealtimeValidation()
        setupListeners()
    }

    private fun setupRealtimeValidation() {
        binding.etFullName.doAfterTextChanged { binding.tilFullName.error = null }
        binding.etEmail.doAfterTextChanged { binding.tilEmail.error = null }
        binding.etPhone.doAfterTextChanged { binding.tilPhone.error = null }
    }

    private fun setupListeners() {
        // Sự kiện gõ phím EditText hiển thị Toast tương tác theo yêu cầu đề bài
        binding.etFullName.doAfterTextChanged {
            if (!it.isNullOrEmpty()) {
                Toast.makeText(this, "Đang nhập họ tên...", Toast.LENGTH_SHORT).show()
            }
        }

        // Bắt sự kiện RadioGroup Giới tính
        binding.rgGender.setOnCheckedChangeListener { _, checkedId ->
            val genderText = when (checkedId) {
                R.id.rbMale -> "Nam"
                R.id.rbFemale -> "Nữ"
                else -> ""
            }
            Toast.makeText(this, "Đã chọn giới tính: $genderText", Toast.LENGTH_SHORT).show()
        }

        // Bắt sự kiện CheckBox Mạng xã hội
        binding.cbFacebook.setOnCheckedChangeListener { _, isChecked ->
            val status = if (isChecked) "Đã chọn Facebook" else "Đã bỏ chọn Facebook"
            Toast.makeText(this, status, Toast.LENGTH_SHORT).show()
        }

        binding.cbGithub.setOnCheckedChangeListener { _, isChecked ->
            val status = if (isChecked) "Đã chọn GitHub" else "Đã bỏ chọn GitHub"
            Toast.makeText(this, status, Toast.LENGTH_SHORT).show()
        }

        // Xử lý nút Đăng ký -> Kiểm tra form và chuyển màn hình chi tiết
        binding.btnSubmit.setOnClickListener {
            if (validateForm()) {
                val name = binding.etFullName.text.toString().trim()
                val email = binding.etEmail.text.toString().trim()
                val phone = binding.etPhone.text.toString().trim()

                val gender = if (binding.rbMale.isChecked) "Nam" else if (binding.rbFemale.isChecked) "Nữ" else "Chưa chọn"

                val socials = mutableListOf<String>()
                if (binding.cbFacebook.isChecked) socials.add("Facebook")
                if (binding.cbGithub.isChecked) socials.add("GitHub")
                val socialStr = if (socials.isNotEmpty()) socials.joinToString(", ") else "Không có"

                val student = StudentModel(name, email, phone, gender, socialStr)

                val intent = Intent(this, DetailActivity::class.java).apply {
                    putExtra("STUDENT_DATA", student)
                }
                startActivity(intent)
            }
        }
    }

    private fun validateForm(): Boolean {
        var isValid = true

        val name = binding.etFullName.text.toString()
        if (!FormValidator.isValidName(name)) {
            binding.tilFullName.error = "Họ tên tối thiểu 3 ký tự"
            isValid = false
        }

        val email = binding.etEmail.text.toString()
        if (!FormValidator.isValidEmail(email)) {
            binding.tilEmail.error = "Email không đúng định dạng"
            isValid = false
        }

        val phone = binding.etPhone.text.toString()
        if (!FormValidator.isValidPhone(phone)) {
            binding.tilPhone.error = "Số điện thoại không hợp lệ (10 số)"
            isValid = false
        }

        return isValid
    }
}