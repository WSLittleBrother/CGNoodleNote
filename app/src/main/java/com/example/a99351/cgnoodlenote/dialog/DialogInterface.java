package com.example.a99351.cgnoodlenote.dialog;

import android.view.View;


public interface DialogInterface {

    interface OnLeftAndRightClickListener<T> {

        void clickLeftButton(T dialog, View view);

        void clickRightButton(T dialog, View view);
    }
    interface OnSingleClickListener<T> {

        void clickSingleButton(T dialog, View view);
    }

    interface OnItemClickListener<T> {

        void onItemClick(T dialog, View button, int position);
    }

   /* interface OnClickEditDialogListener {

        void clickLeftButton(MDEditDialog dialog, View view, String editText);

        void clickRightButton(MDEditDialog dialog, View view, String editText);

    }*/
}
