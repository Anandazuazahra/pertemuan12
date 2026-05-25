package id.anandazuazahra.pertemuan_123;

import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class DialogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String note = getIntent().getStringExtra("note_text");
        String displayedNote = (note != null) ? note : "";

        new AlertDialog.Builder(this)
                .setTitle("Catatan Harian")
                .setMessage("Apakah catatan ini sudah dibaca?\n\n" + displayedNote)
                .setPositiveButton("Sudah", (dialog, which) -> finish())
                .setNegativeButton("Belum", (dialog, which) -> finish())
                .setCancelable(false)
                .show();
    }
}
