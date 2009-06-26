package cx.ath.jbzdak.jpaGui.ui.query;

import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

public class FulltextFilter extends GeneralFilter {

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    private final FullTextQuery textQuery;

	public FulltextFilter(int[] columns, boolean fuzzy, String query) {
		super(columns);
        textQuery = new FullTextQuery(support);
        textQuery.setQuery(query);
        textQuery.setFuzzy(fuzzy);
	}

	public FulltextFilter(int... columns) {
		super(columns);
        textQuery = new FullTextQuery(support);
	}

    @Override
	protected boolean include(
			javax.swing.RowFilter.Entry<? extends Object, ? extends Object> value,
			int index) {
	    return  textQuery.matches(value.getStringValue(index));
    }

    public boolean isFuzzy() {
        return textQuery.isFuzzy();
    }

    public String[] getWords() {
        return textQuery.getWords();
    }

    public String getQuery() {
        return textQuery.getQuery();
    }

    public int getLevensteinDST() {
        return textQuery.getLevensteinDST();
    }

    public void setFuzzy(boolean fuzzy) {
        textQuery.setFuzzy(fuzzy);
    }

    public void setLevensteinDST(int levensteinDST) {
        textQuery.setLevensteinDST(levensteinDST);
    }

    public void setQuery(String query) {
        textQuery.setQuery(query);
    }

    public boolean addActionListener(ActionListener actionListener) {
        return textQuery.addActionListener(actionListener);
    }

    public List<ActionListener> getActionListeners() {
        return textQuery.getActionListeners();
    }

    public ActionListener removeActionListener(int index) {
        return textQuery.removeActionListener(index);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }

    public PropertyChangeListener[] getPropertyChangeListeners() {
        return support.getPropertyChangeListeners();
    }

    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        support.addPropertyChangeListener(propertyName, listener);
    }

    public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        support.removePropertyChangeListener(propertyName, listener);
    }

    public PropertyChangeListener[] getPropertyChangeListeners(String propertyName) {
        return support.getPropertyChangeListeners(propertyName);
    }

    public boolean hasListeners(String propertyName) {
        return support.hasListeners(propertyName);
    }
}
