package cmars.share;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.ActionBarOverlayLayout;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by cmars on 10/19/16.
 */

public class CustomDialogFragment extends DialogFragment implements TextView.OnEditorActionListener {

    public CustomDialogFragment() {}

    private EditText editText;

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if(actionId == EditorInfo.IME_ACTION_DONE) {
            listener.onFinishEditing(editText.getText().toString());
            this.dismiss();
            return true;
        }

        return false;
    }

    public interface DialogListener {
        void onFinishEditing(String text);
    }

    private DialogListener listener;

    @Override
    public void onAttach(Context context) {
        listener = (DialogListener) context;
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        listener=null;
        super.onDetach();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setTitle("Dialog");
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.dialog_fragment, container);
        editText= (EditText) view.findViewById(R.id.edit);

        editText.requestFocus();
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        editText.setOnEditorActionListener(this);
        return view;
    }
}
