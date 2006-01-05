package e.gui;

import e.ptextarea.*;
import e.util.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * A "Debug" menu for any Java application.
 */
public class DebugMenu {
    public static JMenu makeJMenu() {
        JMenu menu = new JMenu("Debug");
        menu.add(new ShowEnvironmentAction());
        menu.add(new ShowSystemPropertiesAction());
        return menu;
    }
    
    private DebugMenu() {
    }
    
    private static void showTextWindow(String title, String content) {
        // FIXME: fixed font?
        PTextArea textArea = new PTextArea();
        textArea.setText(content);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(null);
        
        // FIXME: use a better default size. 80x40?
        JFrame frame = new JFrame(title);
        frame.setContentPane(scrollPane);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
    
    private static String sortedStringOfMap(Map<String, String> hash) {
        StringBuilder builder = new StringBuilder();
        String[] keys = hash.keySet().toArray(new String[hash.size()]);
        Arrays.sort(keys);
        for (String key : keys) {
            builder.append(key + "=" + hash.get(key) + "\n");
        }
        return builder.toString();
    }
    
    private static class ShowEnvironmentAction extends AbstractAction {
        public ShowEnvironmentAction() {
            super("Show Environment");
        }
        
        public void actionPerformed(ActionEvent e) {
            showTextWindow("Environment", getEnvironmentAsString());
        }
        
        private String getEnvironmentAsString() {
            return sortedStringOfMap(System.getenv());
        }
    }
    
    private static class ShowSystemPropertiesAction extends AbstractAction {
        public ShowSystemPropertiesAction() {
            super("Show System Properties");
        }
        
        public void actionPerformed(ActionEvent e) {
            // FIXME: we can edit the system properties; should we expose this?
            showTextWindow("System Properties", getSystemPropertiesAsString());
        }
        
        private String getSystemPropertiesAsString() {
            return sortedStringOfMap(getSystemProperties());
        }
        
        private Map<String, String> getSystemProperties() {
            HashMap<String, String> result = new HashMap<String, String>();
            Properties properties = System.getProperties();
            Enumeration<?> propertyNames = properties.propertyNames();
            while (propertyNames.hasMoreElements()) {
                String key = (String) propertyNames.nextElement();
                result.put(key, StringUtilities.escapeForJava(properties.getProperty(key)));
            }
            return result;
        }
    }
}
