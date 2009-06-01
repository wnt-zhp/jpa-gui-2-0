package cx.ath.jbzdak.zarlok.ui.dzien;

import cx.ath.jbzdak.jpaGui.db.dao.NoopDao;
import cx.ath.jbzdak.jpaGui.ui.form.DAOForm;
import cx.ath.jbzdak.jpaGui.ui.form.DAOFormElement;
import cx.ath.jbzdak.jpaGui.ui.form.FormFactory;
import cx.ath.jbzdak.jpaGui.ui.form.FormPanel;
import cx.ath.jbzdak.jpaGui.ui.formatted.MyFormattedTextField;
import cx.ath.jbzdak.zarlok.entities.IloscOsob;
import cx.ath.jbzdak.zarlok.ui.formatted.formatters.IloscOsobFormatter;
import javax.annotation.Nonnull;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;

public class IloscOsobPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	@Nonnull
	private DAOForm<IloscOsob, DAOFormElement> form;

	@Nonnull
	private FormPanel<MyFormattedTextField>  iloscUczestnikow;

	@Nonnull
	private FormPanel<MyFormattedTextField>  iloscKadry;

	@Nonnull
	private FormPanel<MyFormattedTextField>  iloscInnych;

	public IloscOsobPanel(){
		super(new MigLayout("wrap 1", "[fill]"));
		FormFactory<IloscOsob> factory = new FormFactory<IloscOsob>();
		iloscUczestnikow = factory.decorateFormattedTextField("Ilość uczestników", "iloscUczestnikow", new IloscOsobFormatter());
		iloscKadry = factory.decorateFormattedTextField("Ilość kadry", "iloscKadry", new IloscOsobFormatter());
		iloscInnych = factory.decorateFormattedTextField("Ilość innych", "iloscInnych", new IloscOsobFormatter());
		add(iloscUczestnikow);
		add(iloscKadry);
		add(iloscInnych);
		form = factory.getCreatedForm();
		form.setDao(new NoopDao<IloscOsob>());
	}

	public void commit() {
		form.commit();
	}

	public IloscOsob getEntity() {
		return form.getEntity();
	}

	public void setEntity(IloscOsob entity) {
		form.setEntity(entity);
	}

	public void startEditing() {
		form.startEditing();
	}

	public void startViewing() {
		form.startViewing();
	}

	public void stopEditing() {
		form.rollback();
	}

	public DAOForm<IloscOsob, DAOFormElement> getForm() {
		return form;
	}



}

