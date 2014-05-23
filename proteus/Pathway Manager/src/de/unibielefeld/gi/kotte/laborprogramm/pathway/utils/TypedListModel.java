package de.unibielefeld.gi.kotte.laborprogramm.pathway.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;


/**
 * ListModel which allows to replace it's underlying List.
 *
 * @author Konstantin Otte
 */
public class TypedListModel<T> implements ListModel {
    
    List<T> model = Collections.emptyList();
    List<ListDataListener> listeners = new ArrayList<ListDataListener>();

    @Override
    public int getSize() {
        return model.size();
    }

    @Override
    public T getElementAt(int index) {
        return model.get(index);
    }

    @Override
    public void addListDataListener(ListDataListener l) {
        listeners.add(l);
    }

    @Override
    public void removeListDataListener(ListDataListener l) {
        listeners.remove(l);
    }
    
    public void setList(List<T> list) {
        model = list;
        for (ListDataListener l : listeners) {
            l.contentsChanged(new ListDataEvent(this, ListDataEvent.CONTENTS_CHANGED, 0, model.size()-1));
        }
    }
    
    public List<T> getList() {
        return model;
    }
}
