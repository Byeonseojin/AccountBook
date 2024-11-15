package kr.ac.kopo.please02

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kr.ac.kopo.please02.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = getSharedPreferences("user_settings", MODE_PRIVATE)
        val userName = sharedPreferences.getString("user_name", "사용자")
        val goalAmount = sharedPreferences.getFloat("goal_amount", 0.0f)

        binding.tvUserGoal.text = "$userName" + "의 이번달 목표금액\n"+"❣"+ "${goalAmount.toInt()}원❣"

        binding.btnOpenCalculator.setOnClickListener {
            val intent = Intent(this, CalculatorActivity::class.java)
            startActivity(intent)
        }

        binding.btnOpenExpense.setOnClickListener {
            val intent = Intent(this, ExpenseActivity::class.java)
            startActivity(intent)
        }

        binding.btnSelectMonth.setOnClickListener {
            showMonthPicker()
        }

        binding.btnFortune.setOnClickListener {
            val intent = Intent(this, FortuneActivity::class.java)
            startActivity(intent)
        }

        binding.btnYearlyExpenses.setOnClickListener {
            showYearPicker()
        }

        binding.btnDailyExpenses.setOnClickListener {
            val intent = Intent(this, DailyExpensesActivity::class.java)
            startActivity(intent)
        }

        binding.btnSettings.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showMonthPicker() {
        val dialog = MonthYearPickerDialog()
        dialog.setListener { year, month ->
            val selectedMonth = SimpleDateFormat("yyyy-MM", Locale.getDefault()).format(Calendar.getInstance().apply {
                set(Calendar.YEAR, year)
                set(Calendar.MONTH, month)
            }.time)
            val intent = Intent(this, MonthlyExpensesActivity::class.java)
            intent.putExtra("SELECTED_MONTH", selectedMonth)
            startActivity(intent)
        }
        dialog.show(supportFragmentManager, "MonthYearPickerDialog")
    }

    private fun showYearPicker() {
        val dialog = YearPickerDialog()
        dialog.setListener { year ->
            val intent = Intent(this, YearlyExpensesActivity::class.java)
            intent.putExtra("SELECTED_YEAR", year.toString())
            startActivity(intent)
        }
        dialog.show(supportFragmentManager, "YearPickerDialog")
    }
}