package com.example.emv_nfc_test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.devnied.emvnfccard.exception.CommunicationException;
import com.github.devnied.emvnfccard.model.EmvCard;
import com.github.devnied.emvnfccard.parser.EmvTemplate;
import com.github.devnied.emvnfccard.parser.IProvider;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tv = findViewById(R.id.tv);
        Button bt = findViewById(R.id.button);

        bt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                tv.setText(read());
            }
        });

    }



    private String read() {
        // Create provider
        IProvider provider = new Provider();
// Define config
        EmvTemplate.Config config = EmvTemplate.Config()
                .setContactLess(true) // Enable contact less reading (default: true)
                .setReadAllAids(true) // Read all aids in card (default: true)
                .setReadTransactions(true) // Read all transactions (default: true)
                .setReadCplc(false) // Read and extract CPCLC data (default: false)
                .setRemoveDefaultParsers(false) // Remove default parsers for GeldKarte and EmvCard (default: false)
                .setReadAt(true) // Read and extract ATR/ATS and description
                ;
// Create Parser
        EmvTemplate parser = EmvTemplate.Builder() //
                .setProvider(provider) // Define provider
                .setConfig(config) // Define config
                //.setTerminal(terminal) (optional) you can define a custom terminal implementation to create APDU
                .build();

// Read card
        try {
            EmvCard emvCard = parser.readEmvCard();
            return emvCard + "";
        } catch (CommunicationException e) {
            e.printStackTrace();
        }
        /*String card = "0";
        try {
            card = parser.readEmvCard() + "";
        } catch (CommunicationException e) {
            e.printStackTrace();
        }*/
        return null;
    }
}