package com.example.victorovdv;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.Random;
import java.util.stream.IntStream;

public class CongratuationActivity extends AppCompatActivity {

    private boolean isWin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO: сделать показ почты для получения инструкций, а потом забирать эту информацию в mainactivity с помощью activityresult api и высвечивать тост или снек бар с тем что инструкция отправлена
        // TODO: сделать класс ticket с номером билетом, и тиражем, и реализовать в нем parcelable, и не забыть сделать скрины всего
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congratuation);

        Bundle args = getIntent().getExtras();
        isWin = args.getBoolean("isWin");
        TextView congratulationTextView = findViewById(R.id.congrate_text);
        LinearLayout emailLayout = findViewById(R.id.email_layout);
        if (isWin){
            int priceValue = new Random().nextInt(10000000);
            congratulationTextView.setText(getString(R.string.win_title, priceValue));
        }
        else{
            congratulationTextView.setText(R.string.fall_title);
            emailLayout.setVisibility(View.GONE);
        }

        Ticket ticket = args.getParcelable("ticket");
        TextView numberTicket = findViewById(R.id.ticket_number);
        TextView circulationTicket = findViewById(R.id.circulation_number);

        numberTicket.setText(String.valueOf(ticket.number));
        circulationTicket.setText(String.valueOf(ticket.circulation));

        Button button = findViewById(R.id.back_button);
        button.setOnClickListener((x) -> changeIntent());
    }

    public boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    protected void changeIntent(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        if (isWin){
            EditText emailField = findViewById(R.id.email_field);
            String email = emailField.getText().toString();
            if (!isValidEmail(email)){
                emailField.setError("Введите адрес электронной почты");
                return;
            }
            intent.putExtra("email", email);
            setResult(RESULT_OK, intent);
        }
        else{
            setResult(RESULT_CANCELED);
        }
        finish();
    }

}