package cx.ath.jbzdak.jpaGui.ui.autoComplete;

import cx.ath.jbzdak.jpaGui.ui.autoComplete.adapter.MockAdaptor;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-10-14
 */
public class AutoComboBoxTest {

   AutocompleteComboBox box;

   @Before
   public void before(){
       box = new AutocompleteComboBox(new MockAdaptor());


   }

   @Test
   public void testSetFilter(){
      Set<String> expected = new HashSet<String>(Arrays.asList("AAA", "ABA", "AAB"));
      box.getEditor().getEditorComponent().setText("A");
      Assert.assertEquals(expected, new HashSet(box.getModel().getItems()));
   }

   @Test
   public void testSetElement() {
      box.setSelectedItem("AAB");
      Assert.assertEquals("AAB", box.getEditor().getEditorComponent().getText()
      );
   }
}
