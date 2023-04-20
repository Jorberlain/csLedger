package cs.hku.csledger.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import cs.hku.csledger.LedgerDatabaseHelper;
import cs.hku.csledger.R;
import cs.hku.csledger.model.LedgerEntry;

public class LedgerAdapter extends GenericAdapter<LedgerEntry>{
    public LedgerAdapter(Context context, LedgerDatabaseHelper dbHelper) {
        super(context, dbHelper);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);

        LedgerEntry entry = entries.get(position);

        if (convertView == null) {
            convertView = View.inflate(context, R.layout.ledger_entry, null);
        }

        TextView date = (TextView) convertView.findViewById(R.id.list_date);
        date.setText(dateFormat.format(entry.getDate()));
        TextView name = (TextView) convertView.findViewById(R.id.list_name);
        name.setText(entry.getName());
        TextView value = (TextView) convertView.findViewById(R.id.list_value);
        value.setText(entry.getValueString() + " "
                + sharedPreferences.getString("pref_currency_symbol", ""));

        CheckBox box = (CheckBox) convertView.findViewById(R.id.checkBox);
        box.setChecked(isChecked(position));
        box.setTag(position);

        return convertView;
    }

    @Override
    public List<LedgerEntry> getEntries() {
        return dbHelper.getLedgerEntries();
    }
}
