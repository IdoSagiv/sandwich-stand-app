package idosa.huji.postpc.sandwich_stand;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class OrderReadyActivity extends AppCompatActivity {
    // in tests can inject value
    @VisibleForTesting
    public LocalDb db = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_ready);

        if (db == null) {
            db = SandwichStandApp.getLocalDb();
        }

        Button finishOrderBtn = findViewById(R.id.buttonFinishOrder);
        finishOrderBtn.setOnClickListener(v -> {
            SandwichOrder order = db.getCurrentOrder();
            order.changeStatus(SandwichOrder.OrderStatus.DONE);
            db.updateCurrentOrder(order);
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });
    }

    @Override
    public void onBackPressed() {
        DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE: {
                    super.onBackPressed();
                    break;
                }
                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Close the app?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

}