package com.example.victorovdv;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            (result) ->
            {
                if(result.getResultCode() == Activity.RESULT_OK){
                    Intent intent = result.getData();
                    if (intent != null){
                        String email = intent.getStringExtra("email");
                        String message = String.format("Письмо с инструкцией отпрвленно на почту %s", email);
                        Snackbar.make(
                                        findViewById(android.R.id.content),
                                        message,
                                        Snackbar.LENGTH_LONG)
                                .show();
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.check_button);
        button.setOnClickListener((x) -> changeIntent());
    }

    protected void changeIntent(){
        EditText ticketInput = findViewById(R.id.ticket_input);
        String ticketText = ticketInput.getText().toString();
        EditText circulationInput = findViewById(R.id.circulation_input);
        String circulationText = circulationInput.getText().toString();

        if (ticketText.equals("")){
            ticketInput.setError("Введите номер билета");
            return;
        }
        if (circulationText.equals("")){
            circulationInput.setError("Введите тираж билета");
            return;
        }
        int ticketNumber = Integer.parseInt(ticketText);
        int circulationNumber = Integer.parseInt(circulationText);

        Ticket ticket = new Ticket(ticketNumber ,circulationNumber);
        boolean isWin = checkTicket(ticket);


        Intent intent = new Intent(this, CongratuationActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("isWin", isWin);
        intent.putExtra("ticket", ticket);
        mStartForResult.launch(intent);
    }

    protected boolean checkTicket (Ticket ticket) {
        return ticket.number == 13 && ticket.circulation == 7;
    }
}