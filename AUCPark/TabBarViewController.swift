//
//  TabBarViewController.swift
//  AUCPark
//
//  Created by Feras Awaga on 1/18/22.
//

import UIKit

class TabBarViewController: UIViewController {

    @IBOutlet weak var showMessage: UITabBarItem!
    override func viewDidLoad() {
        super.viewDidLoad()
            // create the alert
            let alert = UIAlertController(title: "Message", message: "Checked-out from parking successfully", preferredStyle: .alert)
            // add an action (button)
            //alert.addAction(UIAlertAction(title: "OK", style: .default, handler: nil))
        
        
            // show the alert
            self.present(alert, animated: true, completion: nil)
        
//
//
            let defaultAction = UIAlertAction(title: "Ok", style: .default, handler: { _ in
            let VC = self.storyboard?.instantiateViewController(withIdentifier: "tab") as! UITabBarController //The file that controls the view
            //self.navigationController?.popViewController(animated: true)
            VC.modalPresentationStyle = .fullScreen
            //self.willMove(toParent: ViewController?)
            self.present(VC, animated: false, completion: nil)
                let Url2 = String(format:"http://localhost:3000/checkout/900162227")
                guard let serviceUrl = URL(string: Url2) else { return }

                var request2 = URLRequest(url: serviceUrl)
                request2.httpMethod = "POST"
                request2.setValue("Application/json", forHTTPHeaderField: "Content-Type")
                request2.timeoutInterval = 20
                let session2 = URLSession.shared
                session2.dataTask(with: request2) { (data, response, error) in
                    if let response = response {
                        print(response)
                    }
                   
                }.resume()
        })

        alert.addAction(defaultAction)
        // Do any additional setup after loading the view.
    }
    

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
    }
    */

}
