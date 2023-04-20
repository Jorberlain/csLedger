package cs.hku.csledger.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cs.hku.csledger.LedgerDatabaseHelper;
import cs.hku.csledger.R;
import cs.hku.csledger.adapter.AccountsAdapter;
import cs.hku.csledger.model.Account;

public class AccountsOverview extends AppCompatActivity {
    private AccountsAdapter adapter;
    private LedgerDatabaseHelper dbHelper;

    public void gotoAddView(View view) {
        Intent intent = new Intent(this, EditAccountActivity.class);
        startActivityForResult(intent, 10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.adapter.notifyDataSetChanged();
    }

    public void selectCheckbox(View view) {
        CheckBox box = (CheckBox) view;
        int size = 0;

        if (box.isChecked()) {
            size = adapter.getSelected().size();
            adapter.setSelected((Integer) box.getTag());
        } else {
            adapter.unsetSelected((Integer) box.getTag());
            size = adapter.getSelected().size();
        }

        if (size < 1) {
            invalidateOptionsMenu();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts_overview);


        Toolbar toolbar = (Toolbar) findViewById(R.id.accounts_toolbar);
        setSupportActionBar(toolbar);

        dbHelper = new LedgerDatabaseHelper(this);
        adapter = new AccountsAdapter(this, dbHelper);

        final ListView listView = (ListView) findViewById(R.id.list_accounts);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_accounts_overview, menu);
        return true;
    }

    public void deleteItems() {

        Map<Integer, Boolean> map = adapter.getSelected();
        List<Account> accounts = new ArrayList<>(map.keySet().size());
        for (int key : map.keySet()) {
            if (map.get(key)) {
                accounts.add(adapter.getItem(key));
            }
        }

        String out = dbHelper.deleteAccounts(accounts);
        if (out == null) {
            adapter.getSelected().clear();
            adapter.notifyDataSetChanged();
            out = getResources().getString(R.string.accounts_deleted);
        }
        Toast toast = Toast.makeText(this,
                out,
                Toast.LENGTH_LONG);
        toast.show();
        invalidateOptionsMenu();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.accounts_delete_item:
                deleteItems();
                break;
            case R.id.accounts_toggle_all:
                adapter.setAll();
                invalidateOptionsMenu();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.accounts_delete_item).setEnabled(adapter.hasSelection());

        return true;
    }
}
