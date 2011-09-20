package org.lantern;

import java.util.Map;

import org.eclipse.swt.widgets.Display;

public class LanternHub {

    private volatile static TrustedContactsManager trustedContactsManager;
    private volatile static Display display;
    private volatile static SystemTray systemTray;
    
    private volatile static StatsTracker statsTracker;
    private volatile static LanternKeyStoreManager proxyKeyStore;
    
    public synchronized static TrustedContactsManager getTrustedContactsManager() {
        if (trustedContactsManager == null) {
            trustedContactsManager = new DefaultTrustedContactsManager();
        } 
        return trustedContactsManager;
    }

    public synchronized static Display display() {
        if (display == null) {
            display = new Display();
        }
        return display;
    }

    public synchronized static SystemTray systemTray() {
        if (systemTray == null) {
            if (LanternUtils.runWithUi()) {
                systemTray = new SystemTrayImpl(display());
                systemTray.createTray();
            } else {
                return new SystemTray() {
                    @Override
                    public void createTray() {}
                    @Override
                    public void activate() {}
                    @Override
                    public void addUpdate(Map<String, String> updateData) {}
                };
            }
        }
        return systemTray;
    }

    public synchronized static StatsTracker statsTracker() {
        if (statsTracker == null) {
            statsTracker = new StatsTracker();
        }
        return statsTracker;
    }

    public synchronized static LanternKeyStoreManager getKeyStoreManager() {
        if (proxyKeyStore == null) {
            proxyKeyStore = new LanternKeyStoreManager(true);
        }
        return proxyKeyStore;
    }

}
