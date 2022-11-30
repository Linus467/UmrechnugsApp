package com.example.umrechnugsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.Menu
import android.view.View
import android.widget.*

private const val TAG = "MainActivity"
private var cur :String = ""

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val output: TextView = findViewById(R.id.textOutput)
        val input: EditText = findViewById(R.id.editTextNumberDecimal)
        val spinner: Spinner = findViewById(R.id.CurrencySelector)
        val spinnerout: Spinner = findViewById(R.id.spinnerout)
        val buttonConvert: Button = findViewById(R.id.button)
        val con: CurrencyOP = CurrencyOP()

        if (savedInstanceState != null) {
            spinner.setSelection(savedInstanceState.getInt("curr", 1))
            input.setText(savedInstanceState.getString("input", "23").toInt())
            if(con.checkConvert(input, spinner, output,spinnerout)){
                MyDialogFragment().show(supportFragmentManager,MyDialogFragment.TAG)
            }
            Log.v("ONCREATED", "LemonadeLoadingLastSaveInstance")
        }


        spinner.adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val x = position
                if(con.checkConvert(input, spinner, output,spinnerout)){
                    MyDialogFragment().show(supportFragmentManager,MyDialogFragment.TAG)
                }
            }
        }
        buttonConvert.setOnClickListener {
            con.checkConvert(input, spinner, output,spinnerout)
        }
        input.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s.isNullOrBlank()){

                }
                else{
                    if(s.contains('$') || s.contains('£') || s.contains('€')){

                    }
                    else{
                        var text = s.toString() + " " + cur
                        input.setText(text)
                        input.setSelection(text.length-2)
                    }
                }
            }
        })
        /*input.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                if(con.checkConvert(input, spinner, output,spinnerout)){
                    MyDialogFragment().show(supportFragmentManager,MyDialogFragment.TAG)
                }
                return@OnKeyListener true
            }
            false
        })*/
        ArrayAdapter.createFromResource(
            this,
            R.array.Conversions_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Sagt dem layout wann der Dropdown ausgefahren wird
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Adapter
            spinner.adapter = adapter
        }
        ArrayAdapter.createFromResource(
            this,
            R.array.Conversions_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Sagt dem layout wann der Dropdown ausgefahren wird
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Adapter
            spinnerout.adapter = adapter
        }

        //input.addTextChangedListener {
        //    con.checkConvert(input,spinner,output)
        //}
    }
    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart Called")
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart Called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop Called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause Called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy Called")
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        val in2: TextView = findViewById(R.id.editTextNumberDecimal);
        val change: Spinner = findViewById(R.id.CurrencySelector);
        val text = in2.text.toString() + change.selectedItem
        outState.putString("input", in2.text.toString())
        outState.putInt("curr", change.selectedItemPosition)
        super.onSaveInstanceState(outState, outPersistentState)
        Log.e("Saved", "Instance saved")
        Log.e("Saved: ", "$text")

    }

    override fun onPanelClosed(featureId: Int, menu: Menu) {
        super.onPanelClosed(featureId, menu)
    }
}

class CurrencyOP() {

    fun checkConvert(input: EditText, spinner: Spinner, output: TextView,outputspin : Spinner) : Boolean {
        var inputDouble = 0.0
        if (input.text.toString() != "") {
            inputDouble = input.text.toString().toDouble()
        }
        Log.d("Convert","Converter")
        if (spinner.selectedItemPosition == 0 && outputspin.selectedItemPosition == 1) { // Dollar -> Euro
            output.text = convert(1.01, inputDouble, "€")
            cur = "€"
        } else if (spinner.selectedItemPosition == 0 && outputspin.selectedItemPosition ==2) { // Dollar -> Pfund
            output.text = convert(0.87, inputDouble, "£")
        } else if (spinner.selectedItemPosition == 1 && outputspin.selectedItemPosition ==0) { // Euro -> Dollar
            output.text = convert(0.99, inputDouble, "$")
        } else if (spinner.selectedItemPosition == 1 && outputspin.selectedItemPosition ==2) { // Euro -> Pfund
            output.text = convert(0.86, inputDouble, "£")
        } else if (spinner.selectedItemPosition == 2 && outputspin.selectedItemPosition ==0) { // Pfund -> Dollar
            output.text = convert(1.15, inputDouble, "$")
        } else if (spinner.selectedItemPosition == 2 && outputspin.selectedItemPosition ==1) { // Pfund -> Euro
            output.text = convert(1.16, inputDouble, "€")

        } else {
            println(Log.ERROR)
            output.text = "?" // gibt string zurück
            return true
        }
        return false
    }

    fun checkCurrency(spinner: Spinner): String {
        if (spinner.selectedItem.toString() == "Dollar -> Euro") { // Dollar -> Euro
            return "$"
        } else if (spinner.selectedItem.toString() == "Dollar -> Pfund") { // Dollar -> Pfund
            return "£"
        } else if (spinner.selectedItem.toString() == "Euro -> Dollar") { // Euro -> Dollar
            return "$"
        } else if (spinner.selectedItem.toString() == "Euro -> Pfund") { // Euro -> Pfund
            return "£"
        } else if (spinner.selectedItem.toString() == "Pfund -> Dollar") { // Pfund -> Dollar
            return "$";
        } else if (spinner.selectedItem.toString() == "Pfund -> Euro") { // Pfund -> Euro
            return "€"
        }
        Log.d(TAG,"CheckCurrency")
        return ""
    }

    fun convert(rate: Double, inputDouble: Double, currency: String): String {
        val outputDouble = (inputDouble * rate)
        Log.d(TAG,"Converting")
        return String.format(outputDouble.toString()) + currency
    }

    fun getApiConversion(from: String): Double {
        /*
        OkHttpClient client = new OkHttpClient().newBuilder().build();

        Request request = new Request.Builder()
            .url("https://api.apilayer.com/fixer/convert?to={to}&from={from}&amount={amount}")
            .addHeader("apikey", "pZRXcUOljScsyQTGybYryIRwN3x6iIKh")
            .method(
                "GET",
        )
        .build();
        Response response = client.newCall(request).execute();
        System.out .println(response.body().string());
        */
        return 0.0
    }

    fun onSelectedItem(spinner: Spinner, input: EditText, output: TextView,outspinner: Spinner) {
        Log.d(TAG,"OnSelectedItem")
        if (input.text.toString() == "") {
            if (spinner.selectedItem.toString() == "Dollar -> Euro") { // Dollar -> Euro
                input.hint = "0.00$";
                cur = "$"
                output.text = "0.00€"
            } else if (spinner.selectedItem.toString() == "Dollar -> Pfund") { // Dollar -> Pfund
                input.hint = "0.00$"
                cur = "$"
                output.text = "0.00£"
            } else if (spinner.selectedItem.toString() == "Euro -> Dollar") { // Euro -> Dollar
                input.hint = "0.00€"
                cur = "€"
                output.text = "0.00$"
            } else if (spinner.selectedItem.toString() == "Euro -> Pfund") { // Euro -> Pfund
                input.hint = "0.00€"
                cur = "€"
                output.text = "0.00£"
            } else if (spinner.selectedItem.toString() == "Pfund -> Dollar") { // Pfund -> Dollar
                input.hint = "0.00£"
                cur = "£"
                output.text = "0.00$"
            } else if (spinner.selectedItem.toString() == "Pfund -> Euro") { // Pfund -> Euro
                input.hint = "0.00£"
                cur = "£"
                output.text = "0.00€"
            }
        } else {
            checkConvert(input, spinner, output, outspinner)  //Ausgabe anhand engegebendes im input
        }
    }
}


