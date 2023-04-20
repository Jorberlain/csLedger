package cs.hku.csledger.activities;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import cs.hku.csledger.LedgerDatabaseHelper;
import cs.hku.csledger.R;

public class EditAccountActivity extends AppCompatActivity {
    private LedgerDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);

        dbHelper = new LedgerDatabaseHelper(this);

        EditText valueView = (EditText) findViewById(R.id.edit_account_alias);
        valueView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER))
                        || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    saveItem();
                    return true;
                }
                return false;
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.edit_accounts_toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.save_item:
                saveItem();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    public void saveItem() {

        EditText nameBox = (EditText) this.findViewById(R.id.edit_account_name);
        EditText aliasBox = (EditText) this.findViewById(R.id.edit_account_alias);

        dbHelper.createAccount(nameBox.getText().toString(), "", aliasBox.getText().toString());

        Toast toast = Toast.makeText(this, "Saved successfully.", Toast.LENGTH_LONG);
        toast.show();

        finish();
    }
}
