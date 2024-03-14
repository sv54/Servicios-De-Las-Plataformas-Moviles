//
//  AppDelegate.swift
//  ToDoList
//
//  Created by Domingo on 10/5/15.
//  Copyright (c) 2015 Universidad de Alicante. All rights reserved.
//

import UIKit

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate, UNUserNotificationCenterDelegate {

    var window: UIWindow?

    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        // Override point for customization after application launch.
        UNUserNotificationCenter.current()
            .requestAuthorization(options: [.alert, .sound, .badge])
            { (granted, error) in print(granted)}

        
        UNUserNotificationCenter.current().delegate = self
        
        return true
    }
    

    func userNotificationCenter(_ center: UNUserNotificationCenter, didReceive response: UNNotificationResponse, withCompletionHandler completionHandler: @escaping () -> Void) {
         print("En userNotificationCenter didReceive response")
         print("Acción seleccionada: \(response.actionIdentifier)")
         switch response.actionIdentifier {
         case "accion1":
             showAlert(message: "El usuario seleccionó la accion de Aceptar")
         case "accion2":
             showAlert(message: "El usuario seleccionó la accion de Cancelar")
         default:
             break
         
         }

         completionHandler()
     }

    
    
    func showAlert(message: String) {
        let alertController = UIAlertController(title: "Acción seleccionada", message: message, preferredStyle: .alert)
        alertController.addAction(UIAlertAction(title: "OK", style: .default, handler: nil))
        UIApplication.shared.keyWindow?.rootViewController?.present(alertController, animated: true, completion: nil)
    }

    
    func application(_ application: UIApplication,
                     didRegisterForRemoteNotificationsWithDeviceToken deviceToken: Data) {
        var token = ""
        for i in 0..<deviceToken.count {
            token = token + String(format: "%02.2hhx", arguments: [deviceToken[i]])
        }
        print(token)
    }

    func application(_ application: UIApplication,
                     didFailToRegisterForRemoteNotificationsWithError error: Error) {
        print("Failed to register:", error)
    }

    func applicationWillResignActive(_ application: UIApplication) {
        // Sent when the application is about to move from active to inactive state. This can occur for certain types of temporary interruptions (such as an incoming phone call or SMS message) or when the user quits the application and it begins the transition to the background state.
        // Use this method to pause ongoing tasks, disable timers, and throttle down OpenGL ES frame rates. Games should use this method to pause the game.
    }

    func applicationDidEnterBackground(_ application: UIApplication) {
        // Use this method to release shared resources, save user data, invalidate timers, and store enough application state information to restore your application to its current state in case it is terminated later.
        // If your application supports background execution, this method is called instead of applicationWillTerminate: when the user quits.
    }

    func applicationWillEnterForeground(_ application: UIApplication) {
        // Called as part of the transition from the background to the inactive state; here you can undo many of the changes made on entering the background.
        let navigationController = self.window!.rootViewController as! UINavigationController
        let toDoTableViewController = navigationController.viewControllers[0] as! ToDoTableViewController
        toDoTableViewController.borraItems()
        
        print("Voy a pedir los settigs")
        UNUserNotificationCenter.current().getNotificationSettings(completionHandler:
                {(settings: UNNotificationSettings) in
                    if (settings.alertSetting == UNNotificationSetting.enabled) {
                        print("Alert enabled")
                    } else {
                        print("Alert not enabled")
                    }
                    if (settings.badgeSetting == UNNotificationSetting.enabled) {
                        print("Badge enabled")
                    } else {
                        print("Badge not enabled")
                    }})
    }

    func applicationDidBecomeActive(_ application: UIApplication) {
        // Restart any tasks that were paused (or not yet started) while the application was inactive. If the application was previously in the background, optionally refresh the user interface.
    }

    func applicationWillTerminate(_ application: UIApplication) {
        // Called when the application is about to terminate. Save data if appropriate. See also applicationDidEnterBackground:.
    }


}

