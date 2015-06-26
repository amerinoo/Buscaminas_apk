package com.example.especials.buscaminas;


import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import java.util.List;

public class CasillaAdapter extends BaseAdapter {

    private final List<Casilla> casillas;
    private final Context context;
    private Button firstButton;

    FragmentParrilla fgpar;

    public CasillaAdapter(Context context, List<Casilla> casillas) {
        this.casillas = casillas;
        this.context=context;
        fgpar = (FragmentParrilla) ((Activity)context).getFragmentManager().findFragmentById(R.id.fragmentParrilla);
        createFirtsButton();
    }

    private void createFirtsButton(){
        Casilla c = getItem(0);
        firstButton = new Button(context);
        firstButton.setPadding(8, 8, 8, 8);
        c.setButton(firstButton);
        c.setContexto(new Contexto(firstButton));
        c.setPosition(0);
        c.setBlockAsDisabled(false);
        firstButton.setOnClickListener(new OnClickListenerCasilla(c));
        firstButton.setOnLongClickListener(new OnLongClickListener(c));
        reload(c);
    }
    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getCount() {
        return casillas.size();
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */
    @Override
    public Casilla getItem(int position) {
        return casillas.get(position);
    }

    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Get a View that displays the data at the specified position in the data set. You can either
     * create a View manually or inflate it from an XML layout file. When the View is inflated, the
     * parent View (GridView, ListView...) will apply default layout parameters unless you use
     * {@link android.view.LayoutInflater#inflate(int, android.view.ViewGroup, boolean)}
     * to specify a root view and to prevent attachment to the root.
     *
     * @param position    The position of the item within the adapter's data set of the item whose view
     *                    we want.
     * @param view The old view to reuse, if possible. Note: You should check that this view
     *                    is non-null and of an appropriate type before using. If it is not possible to convert
     *                    this view to display the correct data, this method can create a new view.
     *                    Heterogeneous lists can specify their number of view types, so that this View is
     *                    always of the right type (see {@link #getViewTypeCount()} and
     *                    {@link #getItemViewType(int)}).
     * @param parent      The parent that this view will eventually be attached to
     * @return A View corresponding to the data at the specified position.
     */
    @Override
    public View getView(int position, View view, ViewGroup parent) {

        Button btn;
        Casilla c = getItem(position);
        if (view == null && position != 0) {
            btn = new Button(context);
            btn.setPadding(8, 8, 8, 8);

            c.setButton(btn);
            c.setContexto(new Contexto(btn));
            c.setPosition(position);
            c.setBlockAsDisabled(false);
            btn.setOnClickListener(new OnClickListenerCasilla(c));
            btn.setOnLongClickListener(new OnLongClickListener(c));
            reload(c);
        }else if(position == 0){ btn = firstButton;}
        else {
            btn = (Button) view;
            System.out.println(position);
        }
        return btn;
    }

    private void reload(Casilla c){
        if(c.isFlagged()) c.putFlag();
        else if (c.isQuestionMarked()) c.putQuestionMarked();
        else if (!c.isCovered()) c.putMinesInSurrounding();
    }

    private class OnClickListenerCasilla implements View.OnClickListener{
        private  Casilla c;

        private OnClickListenerCasilla(Casilla c) {
            this.c = c;
        }

        /**114
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {
            fgpar.onItemClick(c,false);
        }
    }


    private class OnLongClickListener implements View.OnLongClickListener{
        Casilla c;

        private OnLongClickListener(Casilla c) {
            this.c = c;
        }

        /**
         * Called when a view has been clicked and held.
         *
         * @param v The view that was clicked and held.
         * @return true if the callback consumed the long click, false otherwise.
         */
        @Override
        public boolean onLongClick(View v) { //Estados casilla "vacio" "bandera" "?"
            fgpar.onItemClick(c,true);
            return true;
        }
    }


}

