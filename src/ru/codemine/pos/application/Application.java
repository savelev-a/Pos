/*
 * Copyright (C) 2016 Alexander Savelev
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

package ru.codemine.pos.application;

import com.alee.laf.WebLookAndFeel;
import com.alee.laf.optionpane.WebOptionPane;
import java.util.List;
import java.util.logging.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import ru.codemine.pos.entity.Settings;
import ru.codemine.pos.entity.Shop;
import ru.codemine.pos.entity.Store;
import ru.codemine.pos.entity.User;
import ru.codemine.pos.entity.device.BarcodeScannerDevice;
import ru.codemine.pos.entity.device.KkmDevice;
import ru.codemine.pos.exception.DuplicateDataException;
import ru.codemine.pos.service.SettingsService;
import ru.codemine.pos.service.ShopService;
import ru.codemine.pos.service.StoreService;
import ru.codemine.pos.service.UserService;
import ru.codemine.pos.service.device.barcodescanner.BarcodeScannerService;
import ru.codemine.pos.service.kkm.Kkm;
import ru.codemine.pos.service.kkm.KkmService;
import ru.codemine.pos.service.kkm.SyslogChequePrinter;
import ru.codemine.pos.ui.LoadingScreen;
import ru.codemine.pos.ui.LoginScreen;
import ru.codemine.pos.ui.MainWindow;

/**
 *
 * @author Alexander Savelev
 */

@Component
public class Application 
{
    private static final Logger log = Logger.getLogger("Application");
    
    @Autowired private SettingsService settingsService;
    @Autowired private UserService userService;
    @Autowired private StoreService storeService;
    @Autowired private KkmService kkmService;
    @Autowired private BarcodeScannerService barcodeScannerService;
    @Autowired private ShopService shopService;
    
    @Autowired private LoginScreen loginScreen;
    @Autowired private MainWindow mainWindow;
    
    private ApplicationContext appContext;
    private Settings settings;
    private User activeUser;
    private Kkm currentKkm;
    private BarcodeScannerDevice currentScanner;
    private Shop currentShop;
    
    public ApplicationContext getAppContext()
    {
        return appContext;
    }
    
    public User getActiveUser()
    {
        return activeUser;
    }
    
    public void setActiveUser(User user)
    {
        this.activeUser = user;
    }

    public Kkm getCurrentKkm()
    {
        return currentKkm;
    }
    
    public void setCurrentKkm(Kkm kkm)
    {
        this.currentKkm = kkm;
        settings.setCurrentKkmDevice(kkm.getDevice());
        settingsService.saveSettings(settings);
    }
    
    public BarcodeScannerDevice getCurrentScanner()
    {
        return currentScanner;
    }
    
    public void setCurrentScanner(BarcodeScannerDevice scanner)
    {
        this.currentScanner = scanner;
        settings.setCurrentScannerDevice(currentScanner);
        settingsService.saveSettings(settings);
        barcodeScannerService.initDevice(scanner);
    }
    
    public Shop getCurrentShop()
    {
        return currentShop;
    }
    
    public void setCurrentShop(Shop shop)
    {
        this.currentShop = shop;
    }
    
    
    /**
     * Инициализация приложения
     */
    public void init()
    {
        log.info("Начало загрузки приложения");

        WebLookAndFeel.install();
        
        LoadingScreen loadingScreen = new LoadingScreen();
        loadingScreen.show();
        loadingScreen.setLoadingStatus("Запуск программы", 0);
        
        try
        {
            loadingScreen.setLoadingStatus("Загрузка контекста", 20);
            appContext = new ClassPathXmlApplicationContext("applicationContext.xml");
            
        } 
        catch (Exception e)
        {
            WebOptionPane.showMessageDialog(null, 
                    "<html><p style='width: 600px;'> Для решения проблемы обратитесь к разработчику.<br><br>" 
                            + e.getLocalizedMessage(), 
                    "Ошибка при загрузке контекста!", WebOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            System.exit(1);
            
        }
        Application app = appContext.getBean(Application.class);
        log.info("Контекст приложения загружен");
        
        loadingScreen.setLoadingStatus("Загрузка настроек", 50);
        app.initSettings();

        loadingScreen.setLoadingStatus("Загрузка пользователей", 60);
        app.initUsers();

        loadingScreen.setLoadingStatus("Загрузка складов", 70);
        app.initStores();

        loadingScreen.setLoadingStatus("Загрузка касс", 80);
        app.initKkm();
        
        loadingScreen.setLoadingStatus("Загрузка устройств", 90);
        app.initDevices();

        loadingScreen.setLoadingStatus("Загрузка завершена", 100);

        loadingScreen.close();
        app.showLoginScreen();
        
        log.info("Загрузка завершена");
    }
    
    public void showLoginScreen()
    {
        loginScreen.show();
    }
    
    public void showMainWindow()
    {
        mainWindow.showMainWindow();
    }
    
    public void close()
    {
        log.info("Завершение работы\n");
        System.exit(0);
    }
    
    private void initSettings()
    {
        settings = settingsService.getSettings();
        if(settings == null)
        {
            log.info("Настройки не найдены. Создаются настройки по умолчанию.");
            settings = new Settings();
            settingsService.saveSettings(settings);
        }
        
        currentShop = settings.getCurrentShop();
        if(currentShop == null)
        {
            log.info("Текущий магазин не найден. Создается магазин по умолчанию.");
            currentShop = new Shop();
            try
            {
                shopService.create(currentShop);
            }
            catch (DuplicateDataException ex)
            {
                WebOptionPane.showMessageDialog(null, 
                    "<html><p style='width: 300px;'> Для решения проблемы обратитесь к разработчику.<br><br>" 
                            + ex.getLocalizedMessage(), 
                    "Ошибка при инициализации текущей торговой точки!", WebOptionPane.ERROR_MESSAGE);
            
            System.exit(1);
            }
            settings.setCurrentShop(currentShop);
            settingsService.saveSettings(settings);
        }
        
        log.debug("Загружены настройки: " + settings);
    }
    
    private void initUsers()
    {
        try
        {
            List<User> allusers = userService.getAllUsers();
            if(allusers.isEmpty())
            {
                log.info("Пользователи не найдены. Создается пользователь Администратор.");
                WebOptionPane.showMessageDialog(null,  
                        "Будет создан системный пользователь Администратор с паролем admin\n"
                       + "Не забудьте поменять пароль на вкладке настроек.", 
                        "Не найдено пользователей", WebOptionPane.INFORMATION_MESSAGE);
            
                User defUser = new User("Администратор", "Администратор");
                defUser.setPassword("admin");
                defUser.addRole(User.Role.ROLE_SYSADMIN);
            
                userService.create(defUser);
            }
        } 
        catch (Exception e)
        {
            WebOptionPane.showMessageDialog(null, 
                    "<html><p style='width: 300px;'> Для решения проблемы обратитесь к разработчику.<br><br>" 
                            + e.getLocalizedMessage(), 
                    "Ошибка при инициализации пользователей!", WebOptionPane.ERROR_MESSAGE);
            
            System.exit(1);
        }
        

    }
    
    private void initStores()
    {
        try
        {
            Store retailStore = storeService.getByName("Розница");
            if(retailStore == null)
            {
                log.info("Склад Розница не найден. Создаются новый склад.");
                retailStore = new Store("Розница");
                storeService.create(retailStore);
            }
        } 
        catch (Exception e)
        {
            WebOptionPane.showMessageDialog(null, 
                    "<html><p style='width: 300px;'> Для решения проблемы обратитесь к разработчику.<br><br>" 
                            + e.getLocalizedMessage(), 
                    "Ошибка при инициализации складов!", WebOptionPane.ERROR_MESSAGE);
            
            System.exit(1);
        }
        
    }

    private void initKkm()
    {
        currentKkm = kkmService.createKkm(settings.getCurrentKkmDevice());
        if(currentKkm == null)
        {
            log.info("Настроенная ККМ не найдена. Создается ККМ по умолчанию.");
            WebOptionPane.showMessageDialog(null,  
                        "После загрузки перейдите в Настройки -> Кассовые аппараты\n"
                       + "и настройте подключенный кассовый аппарат.", 
                        "Не найдено настроенных кассовых аппаратов", WebOptionPane.INFORMATION_MESSAGE);
            
            currentKkm = new SyslogChequePrinter();
            currentKkm.setDevice(new KkmDevice());
        }
        
        log.info("Используется ККМ: " + currentKkm.getDevice().getName());
    }

    private void initDevices()
    {
        currentScanner = settings.getCurrentScannerDevice();
        if(currentScanner == null)
        {
            log.info("Настроенный сканер ШК не обнаружен. Создается сканер ШК по умолчанию.");
            currentScanner = new BarcodeScannerDevice();
        }
        
        barcodeScannerService.initDevice(currentScanner);
        
        log.info("Используется сканер ШК: " + currentScanner.getName());
    }

}
