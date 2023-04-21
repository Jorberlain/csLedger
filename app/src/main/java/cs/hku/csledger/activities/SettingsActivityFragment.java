package cs.hku.csledger.activities;

import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;

import androidx.annotation.RequiresApi;

import java.util.List;

import cs.hku.csledger.LedgerDatabaseHelper;
import cs.hku.csledger.R;
import cs.hku.csledger.model.Account;

public class SettingsActivityFragment extends PreferenceFragment {
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);

        LedgerDatabaseHelper dbHelper = new LedgerDatabaseHelper(getContext());

        List<Account> accounts = dbHelper.getAccounts();

        if (accounts.size() < 1) {
            accounts.add(dbHelper.getOrCreateAccount("Expenses:User1", "First Account", "User1"));
        }

        CharSequence[] accountNames = new CharSequence[accounts.size()];
        CharSequence[] accountValues = new CharSequence[accounts.size()];

        for (int i = 0; i < accounts.size(); i++) {
            Account a = accounts.get(i);
            accountNames[i] = a.getAliasOrName();
            accountValues[i] = String.valueOf(a.getId());
        }

        ListPreference defaultAccount1 = (ListPreference) findPreference("pref_default_account1");
        defaultAccount1.setDefaultValue("1");
        defaultAccount1.setEntries(accountNames);
        defaultAccount1.setEntryValues(accountValues);


        /*ListPreference defaultAccount2 = (ListPreference) findPreference("pref_default_account2");
        defaultAccount2.setDefaultValue("2");
        defaultAccount2.setEntries(accountNames);
        defaultAccount2.setEntryValues(accountValues);*/
    }
}
