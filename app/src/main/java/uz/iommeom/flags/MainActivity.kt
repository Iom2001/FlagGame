package uz.iommeom.flags

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.view.marginLeft
import kotlinx.android.synthetic.main.activity_main.*
import uz.pdp.flagquiz.models.Flag

class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var list: ArrayList<Flag>
    lateinit var buttonList: ArrayList<Button>
    lateinit var anwListAdd: ArrayList<Button>
    private var count = 0
    var checkAnswer = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadFlag()
        createButton()
        setData()
    }

    @SuppressLint("ResourceType")
    private fun setData() {
        if (count < list.size) {
            imageView.setImageResource(list[count].image!!)
            val randomCountryList = randomCountry()
            for (i in 0 until 18) {
                buttonList[i].visibility = View.VISIBLE
                buttonList[i].text = randomCountryList[i]
            }
            anwListAdd = ArrayList()
            for (i in 0 until list[count].country?.length!!) {
                var button = Button(this)
                button.id = 100
                button.setBackgroundResource(R.drawable.backbut)
                button.layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT, 1.0f
                )
                val param = button.layoutParams as ViewGroup.MarginLayoutParams
                param.setMargins(10, 0, 10, 0)
                button.setOnClickListener(this)
                button.visibility = View.VISIBLE
                anwListAdd.add(button)
                linear1.addView(button)
            }
        } else {
            Toast.makeText(this, "You win!!!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun randomCountry(): List<String> {
        var stringList = ArrayList<String>()
        var str = list[count].country?.toUpperCase()
        var a = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        if (str != null) {
            for (i in str.indices) {
                stringList.add(str[i].toString())
            }
        }
        val b = 18 - (str?.length ?: 0)
        for (i in 0 until b) {
            stringList.add(a[i].toString())
        }
        stringList.shuffle()
        return stringList
    }

    @SuppressLint("ResourceType")
    private fun createButton() {
        buttonList = ArrayList()
        for (i in 0 until 18) {
            var button = Button(this)
            button.id = i
            button.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT, 1.0f
            )
            val param = button.layoutParams as ViewGroup.MarginLayoutParams
            param.setMargins(10, 0, 10, 0)
            button.setBackgroundResource(R.drawable.backbut)
            button.setOnClickListener(this)
            buttonList.add(button)
            if (i < 9) linear3.addView(button)
            else linear4.addView(button)
        }
    }

    private fun loadFlag() {
        list = ArrayList()
        list.add(Flag(R.drawable.ukraine, "Ukraine"))
        list.add(Flag(R.drawable.uzb, "Uzbekistan"))
        list.add(Flag(R.drawable.tonga, "Tonga"))
        list.add(Flag(R.drawable.singapore, "Singapore"))
        list.add(Flag(R.drawable.italy, "Italy"))
        list.add(Flag(R.drawable.indonesia, "Indonesia"))
        list.add(Flag(R.drawable.china, "China"))
        list.add(Flag(R.drawable.chile, "Chile"))
        list.add(Flag(R.drawable.belarus, "Belarus"))
        list.add(Flag(R.drawable.bahrain, "Bahrain"))
        list.add(Flag(R.drawable.austria, "Austria"))
        list.add(Flag(R.drawable.argentina, "Argentina"))
    }

    @SuppressLint("ResourceType")
    override fun onClick(v: View?) {
        val buttonClicked = v as Button
        when {
            buttonClicked.id > 100 -> {
                for (i in buttonList.indices) {
                    if (buttonList[i].id == buttonClicked.id - 101) {
                        buttonList[i].visibility = View.VISIBLE
                        buttonList[i].text = buttonClicked.text.toString()
                        buttonClicked.id = 100
                        buttonClicked.text = ""
                        checkAnswer = ""
                        for (j in anwListAdd.indices) {
                            checkAnswer += anwListAdd[j].text.toString()
                        }
                    }
                }
            }
            buttonClicked.id < 18 -> {
                if (checkAnswer.length < list[count].country?.length ?: 0) {
                    for (j in anwListAdd.indices) {
                        if (anwListAdd[j].text == "") {
                            anwListAdd[j].text = buttonClicked.text.toString()
                            anwListAdd[j].id = 101 + buttonClicked.id
                            break
                        }
                    }
                    checkAnswer = ""
                    for (j in anwListAdd.indices) {
                        checkAnswer += anwListAdd[j].text.toString()
                    }
                    buttonClicked.visibility = View.INVISIBLE
                    if (checkAnswer.equals(list[count].country, ignoreCase = true)) {
                        linear1.removeAllViews()
                        linear2.removeAllViews()
                        anwListAdd.clear()
                        checkAnswer = ""
                        count++
                        setData()
                    } else if (checkAnswer.length == list[count].country?.length) {
                        Toast.makeText(this, "Your answer incorrect!!!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}