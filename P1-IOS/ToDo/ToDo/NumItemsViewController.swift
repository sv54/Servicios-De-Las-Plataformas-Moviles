//
//  NumItemsViewController.swift
//  ToDoList
//
//  Created by Domingo on 12/5/15.
//  Copyright (c) 2015 Universidad de Alicante. All rights reserved.
//

import UIKit

class NumItemsViewController: UIViewController {

    var terminados = 0
    @IBOutlet weak var numItems: UILabel!

    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
    }
    
    @IBAction func lanzarNotificacion(_ sender: Any) {
        print("lanzando notificacion")
        let content = UNMutableNotificationContent()
            content.title = "Notificacion pasado 10 segundos!"
            content.body = "Pos eso"
            content.sound = UNNotificationSound.default
            
            
        if let imageURL = Bundle.main.url(forResource: "elGatooo", withExtension: "jpg") {
            do {
                let attachment = try UNNotificationAttachment(identifier: "imagenAttachment", url: imageURL, options: nil)
                content.attachments = [attachment]
            } catch {
                print("Error al cargar la imagen como adjunto de notificación: \(error.localizedDescription)")
            }
        } else {
            print("No se encontró la imagen en el bundle de la aplicación")
        }
            
        let action1 = UNNotificationAction(identifier: "accion1", title: "Aceptar", options: [.foreground])
        let action2 = UNNotificationAction(identifier: "accion2", title: "Cancelar", options: [.foreground])
        let category = UNNotificationCategory(identifier: "category", actions: [action1, action2], intentIdentifiers: [], options: [])
        UNUserNotificationCenter.current().setNotificationCategories([category])
        
        content.categoryIdentifier = "category"
        
        let trigger = UNTimeIntervalNotificationTrigger(timeInterval: 10, repeats: false)
        
        let request = UNNotificationRequest(identifier: "notificationID", content: content, trigger: trigger)
        
        UNUserNotificationCenter.current().add(request) { (error) in
            if let error = error {
                print("Error al programar la notificación: \(error.localizedDescription)")
            } else {
                print("Notificación programada exitosamente")
            }
        }
    }
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        
        numItems.text = "Se han completado \(terminados) ítems."
    }
    

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

}
