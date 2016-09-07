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
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import ru.codemine.pos.entity.Store;
import ru.codemine.pos.entity.User;
import ru.codemine.pos.entity.device.KkmDevice;
import ru.codemine.pos.service.StoreService;
import ru.codemine.pos.service.UserService;
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
    private static final Logger log = Logger.getLogger("Main");
    
    @Autowired private UserService userService;
    @Autowired private StoreService storeService;
    @Autowired private KkmService kkmService;
    
    @Autowired private LoginScreen loginScreen;
    @Autowired private MainWindow mainWindow;
    
    private ApplicationContext appContext;
    private User activeUser;
    private Kkm activeKkm;
    
    /**
     * Возвращает контекст приложения
     * @return контекст приложения
     */
    public ApplicationContext getAppContext()
    {
        return appContext;
    }
    
    /**
     * Возвращает текущего пользователя
     * @return текуший пользователь
     */
    public User getActiveUser()
    {
        return activeUser;
    }
    
    /**
     * Делает заданного пользователя текущим
     * @param user
     */
    public void setActiveUser(User user)
    {
        this.activeUser = user;
    }
    
    /**
     * Возвращает теущую настроеную ККМ
     * @return
     */
    public Kkm getCurrentKkm()
    {
        return activeKkm;
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
        
        //Загрузка контекста Spring
        try
        {
            loadingScreen.setLoadingStatus("Загрузка контекста", 20);
            appContext = new ClassPathXmlApplicationContext("applicationContext.xml");
            
        } 
        catch (Exception e)
        {
            WebOptionPane.showMessageDialog(null, 
                    "<html><p style='width: 300px;'> Для решения проблемы обратитесь к разработчику.<br><br>" 
                            + e.getLocalizedMessage(), 
                    "Ошибка при загрузке контекста!", WebOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            System.exit(1);
            
        }
        Application app = appContext.getBean(Application.class);
        
        //Загрузка настроек
        loadingScreen.setLoadingStatus("Загрузка настроек", 50);
        
        //Инициализация пользователей
        loadingScreen.setLoadingStatus("Загрузка пользователей", 70);
        app.initUsers();
        
        //Инициализация складов
        loadingScreen.setLoadingStatus("Загрузка складов", 80);
        app.initStores();
        
        //Инициализация ККМ
        loadingScreen.setLoadingStatus("Загрузка касс", 90);
        app.initKkm();
        
        //Окончание загрузки
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
        System.exit(0);
    }
    
    
    private  void initUsers()
    {
        try
        {
            List<User> allusers = userService.getAllUsers();
            if(allusers.isEmpty())
            {
                WebOptionPane.showMessageDialog(null,  
                        "Будет создан системный пользователь Администратор с паролем admin\n"
                       + "Не забудьте поменять пароль на вкладке настроек.", 
                        "Не найдено пользователей", WebOptionPane.INFORMATION_MESSAGE);
            
                User defUser = new User("Администратор", "Администратор");
                defUser.setPassword("admin");
            
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
        activeKkm = kkmService.getCurrentKkm();
        if(activeKkm == null)
        {
            WebOptionPane.showMessageDialog(null,  
                        "После загрузки перейдите в Настройки -> Кассовые аппараты\n"
                       + "и настройте подключенный кассовый аппарат.", 
                        "Не найдено настроенных кассовых аппаратов", WebOptionPane.INFORMATION_MESSAGE);
            
            activeKkm = new SyslogChequePrinter();
            activeKkm.setDevice(new KkmDevice());
        }
    }
    
}
