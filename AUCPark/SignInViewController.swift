//
//  SignInViewController.swift
//  AUCPark
//
//  Created by Feras Awaga on 1/18/22.
//

import UIKit

class SignInViewController: UIViewController {
   var x = 0
    var ff: Bool = false
    @IBOutlet weak var signup: UIButton!
    @IBOutlet weak var signin: UIButton!
  

    override func viewDidLoad() {
        super.viewDidLoad()
    
        signin.layer.cornerRadius = 10
        signin.layer.maskedCorners = [.layerMinXMinYCorner, .layerMinXMaxYCorner, .layerMaxXMinYCorner, .layerMaxXMaxYCorner]
        
        signup.layer.cornerRadius = 10
        signup.layer.maskedCorners = [.layerMinXMinYCorner, .layerMinXMaxYCorner, .layerMaxXMinYCorner, .layerMaxXMaxYCorner]

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
